package com.eva.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

/**
 * 一个实现了J2SE平台或Android平台的客户端http文件下载功能的实用类.
 * 
 * @author Jack Jiang
 * @since 2.3
 */
public class HttpFileDownloadHelper
{
	private static final int BUFFER_SIZE = 4096;
	
	private final static String TAG = HttpFileDownloadHelper.class.getSimpleName();

	/**
	 * Downloads a file from a URL
	 * 
	 * @param fileURL HTTP URL of the file to be downloaded
	 * @param saveDir path of the directory to save the file

	 * 		http头信息作为文件大小
	 * @param progressObserver 文件在下载过程中，将会按文件下载比便0~100范围作为进度值通知观察者
	 * @return 返回下载完成后文件的保存绝对路径，如果下载失败或服务端返回的数据为0则本返回值为null
	 * @throws IOException
	 */
	public static String downloadFile(String fileURL, String saveDir
			, Observer progressObserver) throws Exception
	{
		return downloadFile(fileURL, saveDir, -1, progressObserver);
	}
	
	/**
	 * Downloads a file from a URL
	 * 
	 * @param fileURL HTTP URL of the file to be downloaded
	 * @param saveDir path of the directory to save the file（目录末尾无需分隔符"/"）
	 * @param fileSize 本次下载文件大小，如果此参数<=0则将在方法内尝试取Content-Length的
	 * 		http头信息作为文件大小
	 * @param progressObserver 文件在下载过程中，将会按文件下载比便0~100范围作为进度值通知观察者
	 * @return 返回下载完成后文件的保存绝对路径，如果下载失败或服务端返回的数据为0则本返回值为null
	 * @throws IOException
	 */
	public static String downloadFile(String fileURL, String saveDir, int fileSize
			, Observer progressObserver) throws Exception
	{
		try
		{
			Object[] ret = downloadFileEx(fileURL, saveDir, fileSize, progressObserver, false);
			if(ret != null && ret.length >=2)
			{
				return (String)ret[0];
			}
			return null;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	/**
	 * Downloads a file from a URL
	 * 
	 * @param fileURL HTTP URL of the file to be downloaded
	 * @param saveDir path of the directory to save the file（目录末尾无需分隔符"/"）
	 * @param fileSize 本次下载文件大小，如果此参数<=0则将在方法内尝试取Content-Length的
	 * 		http头信息作为文件大小
	 * @param progressObserver 文件在下载过程中，将会按文件下载比便0~100范围作为进度值通知观察者
	 * @param ignoreIfServerReturn0File true表示如果服务端正常返回但没有文件数据时属正常情况无需
	 * 报错(此情况发生于如：KChat中的用户头像下载，本地有了用户头像那服务端就无需返回头像数据，此情
	 * 况当然各正常，可忽略之)，否则属不正常现像将抛异常。推荐使用false
	 * @return 返回的是一个Object数据，单元0存放的是文件的保存绝对路径，单元1是该文件的数据大小（单位：byte），
	 * 单元2是该文件的文件名（不含完整路径的单纯文件名）
	 * @throws IOException
	 */
	public static Object[] downloadFileEx(String fileURL, String saveDir, int fileSize
			, Observer progressObserver, boolean ignoreIfServerReturn0File) throws Exception
	{
		File savedFile = null;
		int fileLength = 0;
		String fileName = "";
		
		HttpURLConnection httpConn = null;
		try
		{
			// http连接
			URL url = new URL(fileURL);
			httpConn = (HttpURLConnection) url.openConnection();
			int responseCode = httpConn.getResponseCode();
		
			// always check HTTP response code first
			if (responseCode == HttpURLConnection.HTTP_OK)
			{
				String disposition = httpConn.getHeaderField("Content-Disposition");
				String contentType = httpConn.getContentType();
				// 注意：如果服务端没有设置本http头，则返回值是-1
				int contentLength = httpConn.getContentLength();
				//
				fileLength = contentLength;
				if(fileLength > 0)
				{
					if (disposition != null)
					{
						// extracts file name from header field
						int index = disposition.indexOf("filename=");
						if (index > 0)
							// 注意：此过程中已经去掉了包含在文件名首尾的双引号哦
							// 有时候服务端并不一定返回文件名
							fileName = disposition.substring(index + 10, disposition.length() - 1);
					}
					else
						// extracts file name from URL
						// TODO 有些情况下文件是动态返回数据，URL的末尾不一定是文件名，所以此处不严谨，以后可优化(此种情况下可通过参数传到本方法中即可)
						fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
	
					System.out.println("Content-Type = " + contentType);
					System.out.println("Content-Disposition = " + disposition);
					System.out.println("Content-Length = " + contentLength);
					System.out.println("fileName = " + fileName);
					
					// opens input stream from the HTTP connection
					InputStream inputStream = httpConn.getInputStream();
//					saveFilePath = saveDir + File.separator + fileName;
					savedFile = new File(saveDir + File.separator + fileName);
					// 如果父目录不存在则建立之
					if(savedFile.getParent() != null && !savedFile.getParentFile().exists())
						savedFile.getParentFile().mkdirs();
					
					System.out.println("Dowload saveFilePath="+savedFile.getAbsolutePath());
	
					// opens an output stream to save into file
					FileOutputStream outputStream = new FileOutputStream(savedFile);
					int cumulationSize = 0; // 当前读取的总大小：仅用于进度报告
					int bytesRead = -1;
					byte[] buffer = new byte[BUFFER_SIZE];
					while ((bytesRead = inputStream.read(buffer)) != -1)
					{
						outputStream.write(buffer, 0, bytesRead);
						
						//
						cumulationSize += bytesRead;
						
						//** 通知下载进度观察者
						if(progressObserver != null)
						{
							if(fileSize > 0 || contentLength > 0)
							{
								// 当fileSize<=0取则尝试取contentLength作为文件大小（contentLength不保证由服务端确实设置了）
								progressObserver.update(null
										, (int)((cumulationSize*100)/(fileSize > 0?fileSize:contentLength)));//cumulationSize);
							}
						}
					}
					
					// 如果服务端没有返回数据，这个空文件就不要留了，删除之
					if(cumulationSize <= 0)
					{
						if(savedFile.exists())
							savedFile.delete();
					}
					
					// 关闭连接，释放资源
					outputStream.close();
					inputStream.close();
				}
				else
				{
					if(ignoreIfServerReturn0File)
					{
						// 返回0的可能比如：在RainbowChat的头像下载时，因本地缓存的存在，提交到服务端时叛定
						// 不用下载，那么当然就不用返回了（节约流量和性能等）
						System.out.println("服务端正常返回但文件数据大小是0，忽略之，fileURL="+fileURL);
					}
					else
	//					throw new Exception("地址："+fileURL+"的文件下载失败,responseCode="+responseCode);
						throw new Exception("[提示]服务端正常返回但文件数据大小是0，这应是服务端判定本次无文件需下载" +
								"，本次responseCode="+responseCode+"，fileURL="+fileURL);
				}
			}
		}
		catch (IOException e)
		{
//			Log.w(TAG, "地址："+fileURL+"的文件下载时出错！", e);
//			throw new Exception("地址："+fileURL+"的文件下载时出错！", e.getCause());
			throw new Exception("Dowload "+fileURL+" error.", e.getCause());
		}
		finally
		{
			if(httpConn != null)
				httpConn.disconnect();
		}
		
		return new Object[]{savedFile == null?null:savedFile.getAbsolutePath(), fileLength, fileName};
	}
	
	/**
	 * 一个实现了异步下载的封装类.
	 * 
	 * @author Jack Jiang
	 * @since RainbowChat 2.2
	 */
	public abstract static class DownloadAsyncRoot extends AsyncTask<Object, Integer, Object>
	{
		protected Context activity = null;
		
		//
		protected String fileURL = null;
		protected String saveDir = null;
		protected int fileSize = 0;
		
		/**
		 * 构造方法.
		 * 
		 * @param activity
		 * @param fileURL
		 * @param saveDir 文件保存目录（目录末尾无需分隔符"/"）
		 */
		public DownloadAsyncRoot(Context activity
				, String fileURL, String saveDir)
		{
			this(activity, fileURL, saveDir, -1);
		}
		
		/**
		 * 构造方法.
		 * 
		 * @param activity
		 * @param fileURL
		 * @param saveDir 文件保存目录（目录末尾无需分隔符"/"）
		 * @param fileSize
		 */
		public DownloadAsyncRoot(Context activity
				, String fileURL, String saveDir, int fileSize)
		{
			this.activity = activity;
			
			this.fileURL = fileURL;
			this.saveDir = saveDir;
			this.fileSize = fileSize;
		}

		@Override
		protected Object doInBackground(Object... params)
		{
			String fileSavedPath = null;
			try
			{
				Observer progrssObserver = new Observer(){
					@Override
					public void update(Observable observable, Object data)
					{
						// 传过来的进度值是0~100的值
						int progressOf100 = (Integer)data;
						publishProgress(progressOf100);  
					}
				};
				
				fileSavedPath = HttpFileDownloadHelper.downloadFile(fileURL, saveDir, fileSize, progrssObserver);
				if(fileSavedPath != null)
					return fileSavedPath;
				else
					return new Exception("No data retrun from server or save to SDCard failed (fileSavedPath is null)!");
			}
			catch (Exception e)
			{
				return e;
			}
		}
		
		@Override
		protected void onPostExecute(Object result) 
		{
			// doInBackground执行发生异常了
			if(result instanceof Exception)
				onPostExecute_onException((Exception)result);
			// String 
			else
				// 参数(String)result将被本类保证不为null!（onPostExecute_onSucess的调用者无需判断null情况！）
				onPostExecute_onSucess((String)result);
	    }

		// 下载发生错误后调用本方法
		protected abstract void onPostExecute_onException(Exception exception);

		// 下载完成后调用本方法
		protected abstract void onPostExecute_onSucess(String fileSavedPath);
	}
	
	/**
	 * 一个实现了异步下载、进度条显示和提示的文件下载封装类.
	 */
	public abstract static class DownloadAsync extends DownloadAsyncRoot
	{
		/** 下载进度条 */
		private ProgressDialog progressDialog;
		private String progressTitle = null;
		private String progressContent = null;
		
		/**
		 * 构造方法.
		 * 
		 * @param activity
		 * @param progressTitle
		 * @param progressContent
		 * @param fileURL
		 * @param saveDir 文件保存目录（目录末尾无需分隔符"/"）
		 * @param fileSize
		 */
		public DownloadAsync(Activity activity, String progressTitle, String progressContent
				, String fileURL, String saveDir, int fileSize)
		{
			super(activity, fileURL, saveDir, fileSize);
			this.progressTitle = progressTitle;
			this.progressContent = progressContent;
		}
		
		@Override
	    protected void onPreExecute() 
	    {
			showDownWaitDialog();
	    }

		@Override
		protected void onProgressUpdate(Integer... progress) 
		{
			progressDialog.setProgress(progress[0]);  
	    }
		
		@Override
		protected void onPostExecute(Object result) 
		{
			super.onPostExecute(result);
			progressDialog.dismiss();
	    }
		
		/**
		 * 显示下载进度条.
		 */
		private void showDownWaitDialog()
		{
			progressDialog = new ProgressDialog(activity);
			progressDialog.setMessage(progressContent);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setMax(100);
			progressDialog.setCancelable(true);
			progressDialog.show();
			progressDialog.setTitle(progressTitle);
			// 点击其它地方不退出
			progressDialog.setCanceledOnTouchOutside(false);
		}
	}
}
