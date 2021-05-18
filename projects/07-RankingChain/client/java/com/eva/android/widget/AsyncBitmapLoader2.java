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
package com.eva.android.widget;

import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * 一个拥有图片缓存能力的图片读取实用类（基于Picasso库）。
 * <br>
 * 说明：建议以RainbowChat v6.0及以后的版本开发中，图片缓存优先使用本类，并逐步淘汰原 {@link AsyncBitmapLoader} 类。
 * <br>
 * 详见：https://github.com/square/picasso，api文档：https://square.github.io/picasso/2.x/picasso/
 * 
 * @author ,Jack Jiang
 * @since 6.0
 */
public class AsyncBitmapLoader2
{
	private final static String TAG = AsyncBitmapLoader2.class.getSimpleName();

//	/** 本地缓存目录 */
// 	private String mLocalCacheDir;
//
//	private OkHttpClient httpClient;

	/**
	 * 构造方法。
	 *
	 * @param DEFAULT_CACHED_SDCARD_PATH 此路径目录地址末尾是需要"/"的哦！
	 */
	public AsyncBitmapLoader2(String DEFAULT_CACHED_SDCARD_PATH)
	{
//		this(DEFAULT_CACHED_SDCARD_PATH
//				// 默认以系统允许的APP的1/8可用内存作为最大图片缓存内存
//				, ((int) (Runtime.getRuntime().maxMemory() / 1024)) / 8);
	}

	/**
	 * 构造方法。
	 *
	 * @param DEFAULT_CACHED_SDCARD_PATH 此路径目录地址末尾是需要"/"的哦！
	 * @param maxMemory 用于图片缓存的最大内存（单位是KB）
	 * @see LruCache
	 */
	public AsyncBitmapLoader2(String DEFAULT_CACHED_SDCARD_PATH, int maxMemory)
	{
//		this.mLocalCacheDir = DEFAULT_CACHED_SDCARD_PATH;
//	    int appMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
//	    Log.d(TAG, "【ABL2】程序当前可运行的最大内存是："+appMemory+"KB, 用于图片缓存的内存空间："+maxMemory+"KB.");
//
//	    File cacheDir = new File(this.mLocalCacheDir);
//		if(!FileHelper.isFileExist(this.mLocalCacheDir))
//			cacheDir.mkdirs();
//
//		httpClient = new OkHttpClient.Builder()
//				.cache(new Cache(cacheDir, appMemory))
//				.build();
	}

	/**
	 * 尝试异步加载图片.
	 *
	 * @param imageView 图片成功获取后要显示的目标UI组件
	 * @param imageURL 在没有缓存的情况下尝试从网络加载的地址：本参数可为null，但与参数imageFileName不可同时为
	 * null.本参数为null时即意味着只尝试从本地SD卡中读取图片数据到缓存中（而不从网络读取了）
	 * 参数可为null，为null时本方法将尝试智能从参数 imageURL中取出来本方法中使用的优先级2），但并不绝对，因为url
	 * 中很可能没有存放真正的文件名!本参数可为null，但与参数imageURL不可同时为null.
	 */
	public static void loadBitmap(
			final ImageView imageView
			, final String imageURL
			, final int placeholderImgId
			, final int errorImgId)
	{
		loadBitmap(imageView, imageURL, null, placeholderImgId, errorImgId
				, null, -1, -1);
	}

	/**
	 * 尝试异步加载图片.
	 *
	 * @param imageView 图片成功获取后要显示的目标UI组件
	 * @param imageURL 在没有缓存的情况下尝试从网络加载的地址：本参数可为null，但与参数imageFileName不可同时为
	 * null.本参数为null时即意味着只尝试从本地SD卡中读取图片数据到缓存中（而不从网络读取了）
	 * @param imageFileName 要缓存的文件名（本方法中使用的优先级1），通常情况下就是最终缓存到SD卡中的文件名。本
	 * 参数可为null，为null时本方法将尝试智能从参数 imageURL中取出来本方法中使用的优先级2），但并不绝对，因为url
	 * 中很可能没有存放真正的文件名!本参数可为null，但与参数imageURL不可同时为null.
	 */
	public static void loadBitmap(
			final ImageView imageView
			, final String imageURL
			, final String imageFileName
			, final int placeholderImgId
			, final int errorImgId)
	{
		loadBitmap(imageView, imageURL, imageFileName, placeholderImgId, errorImgId
				, null, -1, -1);
	}
	
	/**
	 * 尝试异步加载图片.
	 * 
	 * @param imageView 图片成功获取后要显示的目标UI组件
	 * @param imageURL 在没有缓存的情况下尝试从网络加载的地址：本参数可为null，但与参数imageFileName不可同时为
	 * null.本参数为null时即意味着只尝试从本地SD卡中读取图片数据到缓存中（而不从网络读取了）
	 * @param imageFileName 要缓存的文件名（本方法中使用的优先级1），通常情况下就是最终缓存到SD卡中的文件名。本
	 * 参数可为null，为null时本方法将尝试智能从参数 imageURL中取出来本方法中使用的优先级2），但并不绝对，因为url
	 * 中很可能没有存放真正的文件名!本参数可为null，但与参数imageURL不可同时为null.
	 * @param imageCallBack 图片读取完成（成功或失败）后的回调，调用者的图片的显示处理逻辑可在此实现
	 * @return 成功读取Bitmap对象后即返回之，否则返回null
	 */
	public static void loadBitmap(
			final ImageView imageView
			, final String imageURL
			, final String imageFileName
			, final int placeholderImgId
			, final int errorImgId
			, final Callback imageCallBack)
	{
		loadBitmap(imageView, imageURL, imageFileName, placeholderImgId, errorImgId
				, imageCallBack, -1, -1);
	}
	
	/**
	 * 尝试异步加载图片.
	 * 
	 * @param imageView 图片成功获取后要显示的目标UI组件
	 * @param imageURL 在没有缓存的情况下尝试从网络加载的地址：本参数可为null，但与参数imageFileName不可同时为
	 * null.本参数为null时即意味着只尝试从本地SD卡中读取图片数据到缓存中（而不从网络读取了）
	 * @param imageFileName 要缓存的文件名（本方法中使用的优先级1），通常情况下就是最终缓存到SD卡中的文件名。本
	 * 参数可为null，为null时本方法将尝试智能从参数 imageURL中取出来本方法中使用的优先级2），但并不绝对，因为url
	 * 中很可能没有存放真正的文件名!本参数可为null，但与参数imageURL不可同时为null.
	 * @param imageCallBack 图片读取完成（成功或失败）后的回调
	 * @param reqWidth 生成的Bitmap对象时的真正图片宽(此值用于减小最终生成的Bitmap内存时有用)，<=0表示不启用
	 * @param reqHeight 生成的Bitmap对象时的真正图片高(此值用于减小最终生成的Bitmap内存时有用)，<=0表示不启用
	 * @return 成功读取Bitmap对象后即返回之，否则返回null
	 */
	public static void loadBitmap(
			final ImageView imageView
			, final String imageURL
			, final String imageFileName
			, final int placeholderImgId
			, final int errorImgId
			, final Callback imageCallBack
//            , final Observer imageFileNameCallBack
			/** 
			 * 要转换成的像素数，比如：原图是640*640的大图，但用到的地方只需要200*200的图，
			 * 那么此值设为200*200为佳，因这将使得返回的Bitmap对象占用的内存为200*200而非640*640 */
			, final int reqWidth, final int reqHeight
//            , final boolean donotLoadFromDisk
			)
	{
		// 此2参数不可同时为null!
		if(imageURL == null && imageFileName == null)
		{
			Log.e(TAG, "【ABL2】imageURL和imageFileName不可同时为null！");
			return;
		}
		
		// 是否要调整图片的BitmapFactory.Options.inSampleSize值(从而节省内存)
		final boolean needSetInSampleSize = (reqWidth > 0 && reqHeight > 0);
		
		Log.d(TAG, "【ABL2】======================图片"+imageURL+"("+imageFileName+")载入中======================");
		
		final String cacheKey = (imageFileName != null ? imageURL : imageURL);

		RequestCreator picasso = Picasso.get().load(imageURL);
		picasso.stableKey(cacheKey);

		if(placeholderImgId != -1)
			picasso.placeholder(placeholderImgId);
		if(errorImgId != -1)
			picasso.error(errorImgId);
		if(needSetInSampleSize)
			picasso.resize(reqWidth, reqHeight);

		if(imageCallBack != null)
			picasso.into(imageView, imageCallBack);
		else
			picasso.into(imageView);
	}
}