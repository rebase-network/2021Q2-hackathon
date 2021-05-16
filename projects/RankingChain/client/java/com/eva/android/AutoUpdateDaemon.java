package com.eva.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.URLUtil;

import com.eva.android.widget.Action;
import com.eva.android.widget.WidgetUtils;
import com.eva.android.widget.WidgetUtils.ToastType;
import com.tang.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 客户端程序新版下载支持类.
 * 
 * @author xzj
 */
//waite for refactory!2011-11-08
// TODO 本类有时间还可再重构：异步线路可以改为AsyncTask类、网络文件数据读写方法可细分并重用等
public class AutoUpdateDaemon
{
	/** 日志tag */
	private static final String TAG = AutoUpdateDaemon.class.getSimpleName();
	
	public Activity parentActivity = null;
	/** 
	 * 需要更新到的新版版本号（用于进度
	 * 提示或判断该版本是否已经存在于SD卡中时需要） */
	private int newVersionCode = 0;
	/** 
	 * 需要更新到的新版APK文件http完整下载地址.
	 * 形如：http://61.155.179.201:9090/dmkxm1/dmkx_mobi_a1_c.apk */
	private String newVersionDowloadURL;
	/** 
	 * 需要更新到的新版APK文件大小（当前它主要用于进度提示） */
	private long newVersionFileSize;
	
	/** 下载进度条 */
	private ProgressDialog progressDialog;
	/** 处理器 */
	private ProcessHandle progressHandle = new ProcessHandle();
	/** 下载线程 */
	private Thread theDoawnloadThread = null;
	/** 读写文件时的缓存区大小（1KB） */
	private final int CACHE_SIZE = 1024;

	public AutoUpdateDaemon(Activity activity
			, String newVersionCode, long newVersionFileSize
			, String newVersionDowloadURL)
	{
		this.parentActivity = activity;
		
		//取出服务端的版本码
		this.newVersionCode = Integer.valueOf(newVersionCode);
		this.newVersionFileSize = newVersionFileSize;
		this.newVersionDowloadURL = newVersionDowloadURL;
	}

	/** 执行下载更新 */
	public void doUpdate() throws Exception
	{
		if (checkAPKExsists())
		{
			downloadTheFile(newVersionDowloadURL);
			showDownWaitDialog();
		}
	}
	
	/**
	 * 取出当前要更新的APK程序文件名.
	 * 文件名解析自更新程序完整http下载地址.
	 * 
	 * @return
	 * @see #newVersionDowloadURL
	 */
	private String getNewVersionFileName()
	{
		System.out.println("downURL=="+newVersionDowloadURL);
		if(newVersionDowloadURL != null 
				&& newVersionDowloadURL.lastIndexOf('/') != -1 
				&& newVersionDowloadURL.toLowerCase().endsWith(".apk"))
			return newVersionDowloadURL.substring(newVersionDowloadURL.lastIndexOf('/') + 1);
		else
		{
			WidgetUtils.showToast(parentActivity, "服务端的新版程序配置信息有错，downURL="+newVersionDowloadURL, ToastType.ERROR);
//			WidgetUtils.showToast(parentActivity, "Error, downURL="+newVersionDowloadURL, ToastType.ERROR);
			return "";
		}
	}
	
	/**
	 * 返回要下载的APK文件在File描述对象.
	 * 
	 * @return
	 * @throws Exception
	 */
	private File getTheAPKFile() throws Exception
	{
		//尝试读取之前已经下载过的客户端apk文件
		final File doawnLoadedFile = new File(
				Environment.getExternalStorageDirectory(), getNewVersionFileName());
		if (!doawnLoadedFile.exists())
			doawnLoadedFile.createNewFile();
		return doawnLoadedFile;
	}

	/**
	 * 检查要下载的文件是否已存在于SD卡上，如果已存在则判断它的版本是否与要求的版本相符.
	 * 不相符则返回true，否则返回false.相符则给出选择——重新下载或是直接安装之.
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean checkAPKExsists() throws Exception
	{
		//尝试读取之前已经下载过的客户端apk文件
		final File doawnLoadedFile = getTheAPKFile();
		//读取它的apk文件信息
		PackageManager pm = parentActivity.getPackageManager();
		PackageInfo existsPkgInfo = pm.getPackageArchiveInfo(doawnLoadedFile
				.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
		
		if (existsPkgInfo != null)
		{
			//得到版本信息
			int existsPkgVersion = existsPkgInfo.versionCode; 
			//如果SD卡里该新版APK已经存在则提示是否需要重下载（可能是上次已经下
			//载完还未安装或选择未完装而已，有了这个判断就可以省去必须要重新下载的麻烦）
			if (existsPkgVersion == newVersionCode)
			{
				WidgetUtils.areYouSure(parentActivity
						, "发现新版本已经存在于SD卡上，是否不经下载直接从本地安装？"
//						, "Discover the new version already exists on the SD card, if not by downloading directly from the local installation?"
						, parentActivity.getResources().getString(R.string.general_prompt)
						, new Action() //确认时要执行的动作——直接安装该存在的APK文件
						{
							@Override
							public void actionPerformed(Object dialog){
//								FileSystemHelper.viewFile(doawnLoadedFile, parentActivity);
								OpenFileUtil.openFile(parentActivity, doawnLoadedFile.getAbsolutePath());
							}
						}
						, new Action() //取消时要执行的动作——忽略已存的apk文件重新下载新版程序
						{
							@Override
							public void actionPerformed(Object dialog)
							{
								downloadTheFile(newVersionDowloadURL);
								showDownWaitDialog();
							}
						}
				);
				return false;
			}
		}
		return true;
	}

	/**
	 * 显示下载进度条.
	 */
	public void showDownWaitDialog()
	{
		progressDialog = new ProgressDialog(parentActivity);
		progressDialog.setMessage("正在下载新版 (版本号:" + newVersionCode + ")...");
//		progressDialog.setMessage("Downloading (version:" + newVersionCode + ")...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setMax(100);
		progressDialog.setProgress(20);
		progressDialog.setCancelable(true);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
		progressDialog.setTitle("新版下载中");
//		progressDialog.setTitle("Latest Version downloading");
	}

	/**
	 * 下载文件 如果下载线程为空，或者死亡状态，就创建新线程并启动
	 * 
	 * @param strPath
	 */
	private void downloadTheFile(final String strPath)
	{
		if (theDoawnloadThread == null 
				|| !theDoawnloadThread.isAlive())
		{
			theDoawnloadThread = new Thread(new DownloadThread(strPath));
			theDoawnloadThread.start(); //
		}
	}

	/**
	 * 被下载线程调用的 下载方法
	 * 
	 * @param strPath 要下载文件的完整http地址
	 * @throws Exception
	 */
	private void doDownloadTheFile(String strPath)
	{
		InputStream is = null;
		if (!URLUtil.isNetworkUrl(strPath))
			Log.i(TAG, strPath+"不是一个合法的URL地址!");
		else
		{
			URL myURL;
			try
			{
				myURL = new URL(strPath);
				URLConnection conn = myURL.openConnection();
				conn.setReadTimeout(6 * 10 * 1000 ); // One Minute
				conn.connect();
				is = conn.getInputStream();
				if (is == null)
					throw new RuntimeException("stream is null");
				
				File myTempFile = getTheAPKFile();
				FileOutputStream fos = new FileOutputStream(myTempFile);
				byte buf[] = new byte[CACHE_SIZE];
				int i = 0;
				do
				{
					int numread = is.read(buf);
					if (numread <= 0)
						break;
					fos.write(buf, 0, numread);
					Message message = new Message();
					message.arg1 = i;
					i++;
					progressHandle.sendMessage(message);
				} while (true);
				
				Log.i(TAG, "doDownloadTheFile() Download  ok...");
				progressDialog.cancel();
				progressDialog.dismiss();
//				FileSystemHelper.viewFile(myTempFile, parentActivity);
				OpenFileUtil.openFile(parentActivity, myTempFile.getAbsolutePath());
			}
			catch (final MalformedURLException e)
			{
				parentActivity.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						WidgetUtils.showToast(parentActivity, "新版下载时遇到错误，"+e.getMessage(), ToastType.ERROR);
//						WidgetUtils.showToast(parentActivity, "Error at downloading, "+e.getMessage(), ToastType.ERROR);
					}
				});

				Log.d(TAG, "新版下载时遇到错误，"+e.getMessage(), e);
			}
			catch (IOException e)
			{
				progressDialog.cancel();
				progressDialog.dismiss();
				Message message = new Message();
				message.obj = false;
				progressHandle.sendMessage(message);
				
				Log.d(TAG, "新版下载时遇到错误，"+e.getMessage(), e);
			}
			catch (final Exception e)
			{
//				WidgetUtils.showToast(parentActivity, "Error at downloading, "+e.getMessage(), ToastType.ERROR);
				parentActivity.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						WidgetUtils.showToast(parentActivity, "新版下载时遇到错误，"+e.getMessage(), ToastType.ERROR);
//						WidgetUtils.showToast(parentActivity, "Error at downloading, "+e.getMessage(), ToastType.ERROR);
					}
				});
				Log.d(TAG, "新版下载时遇到错误，"+e.getMessage(), e);
			}
			try{
				is.close();
			}
			catch (Exception ex){
				WidgetUtils.showToast(parentActivity, "is.close()遇到错误，"+ex.getMessage(), ToastType.ERROR);
//				Log.d(TAG, "is.close()时遇到错误，"+ex.getMessage(), ex);
			}
		}
	}

	/**
	 * 下载线程
	 */
	private class DownloadThread implements Runnable
	{
		private String strPath;

		public DownloadThread(String strPath)
		{
			this.strPath = strPath;
		}

		public void run()
		{
			doDownloadTheFile(strPath);
		}
	}
	/**
	 * 处理器handle 负责更新进度条
	 * 
	 * @author Administrator
	 */
	private class ProcessHandle extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			if (msg.obj == null)
			{
				int process = ((int) (msg.arg1 * CACHE_SIZE * 1.0 / newVersionFileSize * 100));
				progressDialog.setProgress(process);
			}
			else
			{
				Boolean ok = (Boolean) (msg.obj);
				WidgetUtils.showToast(parentActivity, "出错：请检查sd卡", WidgetUtils.ToastType.ERROR);
//				WidgetUtils.showToast(parentActivity, "Error: Please check SD card.", WidgetUtils.ToastType.ERROR);
				return;
			}
		}
	}
}