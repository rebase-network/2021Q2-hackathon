// Copyright (C) 2020 即时通讯网(52im.net) & Jack Jiang.
// The RainbowChat Project. All rights reserved.
// 
// 【本产品为著作权产品，合法授权后请放心使用，禁止外传！】
// 【本次授权给：<唐林峰（身份证号：360727198911110910）>，授权编号：<NT200909102407>，代码指纹：<A.599618247.472>，技术对接人QQ：<582901605>】
// 【授权寄送：<收件：唐林峰、地址：上海市浦东新区金豫路100号禹州国际3期2号楼、电话：18717758990>】
// 
// 【本系列产品在国家版权局的著作权登记信息如下】：
// 1）国家版权局登记名(简称)和权证号：RainbowChat    （证书号：软著登字第1220494号、登记号：2016SR041877）
// 2）国家版权局登记名(简称)和权证号：RainbowChat-Web（证书号：软著登字第3743440号、登记号：2019SR0322683）
// 3）国家版权局登记名(简称)和权证号：RainbowAV      （证书号：软著登字第2262004号、登记号：2017SR676720）
// 4）国家版权局登记名(简称)和权证号：MobileIMSDK-Web（证书号：软著登字第2262073号、登记号：2017SR676789）
// 5）国家版权局登记名(简称)和权证号：MobileIMSDK    （证书号：软著登字第1220581号、登记号：2016SR041964）
// * 著作权所有人：江顺/苏州网际时代信息科技有限公司
// 
// 【违法或违规使用投诉和举报方式】：
// 联系邮件：jack.jiang@52im.net
// 联系微信：hellojackjiang
// 联系QQ号：413980957
// 授权说明：http://www.52im.net/thread-1115-1-1.html
// 官方社区：http://www.52im.net
package com.eva.android;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ToolKits
{
	private final static String TAG = ToolKits.class.getSimpleName();

	public static void openWithSystemBrowsers(Context c, String url)
	{
		Intent intent = new Intent();
		//Intent intent = new Intent(Intent.ACTION_VIEW,uri);
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse(url);
		intent.setData(content_url);
		c.startActivity(intent);
	}

	/**
	 * 读取配置于AndroidManifest.xml中的Application元素内的META-DATA的值。
	 * 参资料请见：https://www.cnblogs.com/spring87/p/5810381.html
	 *
	 * @param c
	 * @param dataName
	 * @return 如果成功获取则返回配置值，否则返回null
	 */
	public static String getApplicationMetaData(Context c, String dataName)
	{
		String metaValue = null;

		try{
			ApplicationInfo appInfo = c.getPackageManager()
					.getApplicationInfo(c.getPackageName(), PackageManager.GET_META_DATA);
			metaValue = appInfo.metaData.getString(dataName);
		}
		catch(Exception e){
			Log.w(TAG, e);
		}

		return metaValue;
	}

	/**
	 * 获取App的名称。
	 *
	 * @param context 上下文
	 *
	 * @return 名称
	 */
	public static String getAppName(Context context) {
		PackageManager pm = context.getPackageManager();
		//获取包信息
		try {
			PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
			//获取应用 信息
			ApplicationInfo applicationInfo = packageInfo.applicationInfo;
			//获取albelRes
			int labelRes = applicationInfo.labelRes;
			//返回App的名称
			return context.getResources().getString(labelRes);
		} catch (PackageManager.NameNotFoundException e) {
			Log.w(TAG, e);
		}

		return null;
	}

    /**
     * 复制文本内容到剪贴板。
     *
     * @param context
     * @param copiedText
     * @return
     * @since 4.4
     */
    public static boolean copyTextToClipborad(Context context, String copiedText)
    {
        boolean sucess = false;
        try {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(ClipData.newPlainText("text", copiedText));
            sucess = true;
        }
        catch (Exception e) {
            Log.w(TAG, e);
        }

        return sucess;
    }

    /**
     * 获取指定路径视频的第一帧。
     *
     * @param videoPath 视频绝对文件路径
     * @return 如果获取成功则直接返回，否则返回null
     * @since 4.4
     */
	public static Bitmap getFirstFrameForVideo(String videoPath)
    {
        Bitmap firstFrameBitmap = null;
        MediaMetadataRetriever retriever = null;
        try {
            // 获取第一个关键帧
            retriever = new MediaMetadataRetriever();
            retriever.setDataSource(videoPath);
            firstFrameBitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        }
        catch (Exception e) {
            Log.w(TAG, "获取视频"+videoPath+"的第一帧时出错了，原因："+e.getMessage(), e);
        }
        finally {
            if(retriever != null)
                retriever.release();
        }

        return firstFrameBitmap;
    }

    /**
     * 将视频时长转换成"00:00:00"的字符串格式
     *
     * @param seconds 视频时长（单位：秒）
     * @return
     * @since 4.4
     */
    public static String durationFormatToString(int seconds)
    {
        BigDecimal duration = BigDecimal.valueOf(seconds);

        BigDecimal nine = BigDecimal.valueOf(9);
        BigDecimal sixty = BigDecimal.valueOf(60);

        BigDecimal second = duration.divideAndRemainder(sixty)[1];
        BigDecimal minute = duration.subtract(second).divide(sixty).divideAndRemainder(sixty)[1];
        BigDecimal hour = duration.subtract(second).divideToIntegralValue(BigDecimal.valueOf(3600));

        String str = "";

        if(hour.intValue() > 0 )
        {
            if (hour.compareTo(nine)>0)
                str += hour.intValue() + ":";
            else
                str += "0" + hour.intValue() + ":";
        }

//       if(minute.intValue() > 0 )
        {
            if (minute.compareTo(nine)>0)
                str += minute.intValue() + ":";
            else
                str += "0" + minute.intValue() + ":";
        }

        if (second.compareTo(nine)>0)
            str += second.intValue() ;
        else
            str +="0"+ second.intValue();

        return str;
    }

    /**
     * 保存一个bitmap对象的图片到系统相册中的实现方法。
     *
     * @param bmp 获取的bitmap数据
     * @return true表示保存成功，否则失败
     */
    public static boolean saveBmp2Gallery(Context context, Bitmap bmp)
    {
        boolean sucess = false;

        String filePath = null;

        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;

        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;

        try {
            // 创建一个以时间戳为名的文件
            file = new File(galleryPath, System.currentTimeMillis() + ".jpg");

            // 获得文件相对路径
            filePath = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(filePath);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            }

            // 通知相册更新(不然要到重启手机后，图片才能出现在打开的相册预览里)
            MediaStore.Images.Media.insertImage(context.getContentResolver(), bmp, filePath, null);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);

            sucess = true;
        }
        catch (Exception e) {
            Log.w(TAG, e);
        }
        finally {
            try {
                if (outStream != null)
                    outStream.close();
            }
            catch (IOException e) {
                Log.w(TAG, e);
            }
        }

        return sucess;
    }

	/**
	 * 打开扬声器.
	 * 
	 * @param mContext
	 * @return 返回-1即表示扬声器打开失败，否则返回的是之前的系统音量
	 * （调用者应保存此音量，用于关闭扬声器时恢复音量设置哦）
	 */
	public static int openSpeaker(Context mContext)
	{
		int currVolume = -1;
//		boolean sucess = false;
		try
		{
			AudioManager audioManager = (AudioManager) mContext
					.getSystemService(Context.AUDIO_SERVICE);
			// 经实验：MODE_IN_CALL模式下可正常开启外放但在诸如3星T210平板上会因为没有电话模块而导致本代码调用后卡了一下就无法播放声音了
			//        目前初步测度下来AudioManager.STREAM_MUSI模式在现有的机器上都能成功开启外放（在T210平板上也正常有声音！
			audioManager.setMode(AudioManager.STREAM_MUSIC);//MODE_IN_CALL);//AudioManager.ROUTE_SPEAKER);
//			currVolume = audioManager
//					.getStreamVolume(AudioManager.STREAM_VOICE_CALL);

			if (!audioManager.isSpeakerphoneOn())
			{
				audioManager.setSpeakerphoneOn(true);
				audioManager.setStreamVolume(
								AudioManager.STREAM_VOICE_CALL,
								audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL),
								AudioManager.STREAM_VOICE_CALL);
				Log.w(TAG, "播放语音留言前扬声器已成功打开！currVolume="+currVolume);
//				sucess = true;
			}
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			Log.w(TAG, "打开扬声器失败！", e);
		}
		return currVolume;
	}

	// 关闭扬声器
	public static boolean closeSpeaker(Context mContext, int currVolume)
	{
		boolean sucess = false;
		try
		{
			AudioManager audioManager = (AudioManager) mContext
					.getSystemService(Context.AUDIO_SERVICE);
			audioManager.setMode(AudioManager.MODE_NORMAL);
			if (audioManager != null)
			{
				if (audioManager.isSpeakerphoneOn())
				{
					audioManager.setSpeakerphoneOn(false);
//					audioManager.setStreamVolume(
//							AudioManager.STREAM_VOICE_CALL, currVolume < 0? 0 : currVolume,
//							AudioManager.STREAM_VOICE_CALL);
					Log.w(TAG, "播放语音留后前扬声器已成功关闭.currVolume="+currVolume);
					sucess = true;
				}
			}
		}
		catch (Exception e)
		{
			Log.w(TAG, "关闭扬声器失败！", e);
		}
		// Toast.makeText(context,"扬声器已经关闭",Toast.LENGTH_SHORT).show();
		return sucess;
	}
	
	/**
	 * 强制隐藏输入法.
	 * <p>
	 * 某些情况下输入法会盖住某此后ui，不好看.
	 * 
	 * @param view
	 */
	public static void hideInputMethod(Activity activity, View view)
	{
		if(activity != null && view != null)
		{
			// 强制关闭输入法，某些情况下输入法会盖住某些ui，体验不好，所以需要强制关闭
			((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
//                    		activity.getCurrentFocus().getWindowToken(),
							// 使用view.getWindowToken()解决Android 9.0上的崩溃问题，参见：
					        // https://blog.csdn.net/qq_16318981/article/details/53301997
							view.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	//手机设备基本信息
	public static String getMobileFullInfo(Context context)
	{
		StringBuilder info = new StringBuilder();
		TelephonyManager phoneMgr=(TelephonyManager) context
			.getSystemService(Context.TELEPHONY_SERVICE);
		info.append("运营商:"+getProviderName(phoneMgr))
//			.append(" ,手机号:"+phoneMgr.getLine1Number())
			.append(" ,型号:"+Build.MODEL)
			.append(" ,ID:"+Build.ID)
			.append(" ,制造商:"+Build.MANUFACTURER)
			.append(" ,安卓版本:"+Build.VERSION.RELEASE)
			.append(" ,SDK版本:"+Build.VERSION.SDK);
		
//			Product: C8800
//			, CPU_ABI: armeabi
//			, TAGS: ota-rel-keys,release-keys
//			, VERSION_CODES.BASE: 1
//			, MODEL: C8800
//			, SDK: 10
//			, VERSION.RELEASE: 2.3.3
//			, DEVICE: hwc8800
//			, DISPLAY: C8800V100R001C298B833
//			, BRAND: Huawei
//			, BOARD: C8800
//			, FINGERPRINT: Huawei/C8800/hwc8800:2.3.3/HuaweiC8800/C298B833:user/ota-rel-keys,release-keys
//			, ID: HuaweiC8800
//			, MANUFACTURER: HUAWEI
//			, USER: wangyali
		
		return info.toString();
	}
	
	/** 
	 * </p>
	 * 获取手机服务商信息.<BR> 
	 * 
	 * 需要权限{@code <uses-permission android:name=
	 * "android.permission.READ_PHONE_STATE"/>}
	 * </p>
	 */  
	public static String getProviderName(TelephonyManager telephonyManager) 
	{  
        String providerName = null;  
        // //国际移动用户识别码
        String IMSI = telephonyManager.getSubscriberId();  
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。   
        if (IMSI.startsWith("46000") 
        		|| IMSI.startsWith("46002"))
            providerName = "中国移动";  
        else if (IMSI.startsWith("46001")) 
            providerName = "中国联通";  
        else if (IMSI.startsWith("46003")) 
            providerName = "中国电信";  
        return providerName;  
    } 
	
	
	/**
	 * 判断网络是否可用.
	 * 
	 * @param context
	 * @return true表示网络可用，否则表示不可用
	 */
	public static boolean isNetworkAvailable(Context context)
	{
		ConnectivityManager mgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = mgr.getAllNetworkInfo();
		if (info != null)
		{
			for (int i = 0; i < info.length; i++)
			{
				if (info[i].getState() == NetworkInfo.State.CONNECTED)
					return true;
			}
		}
		return false;
	}
	
	public static InputStream getStreamFromURL(String imageURL) throws Exception
	{
		InputStream in = null;
		try
		{
			URL url = new URL(imageURL);
			URLConnection openConnection = url.openConnection();
			if (openConnection != null&&openConnection.getDate()>0) 
			{
				HttpURLConnection connection = (HttpURLConnection) openConnection;
				in = connection.getInputStream();
			}
		} 
		catch (Exception e) 
		{
//			Log.d("getStreamFromURL", e.getMessage(), e);
			throw e;
		}
		return in;
	}
	

}
