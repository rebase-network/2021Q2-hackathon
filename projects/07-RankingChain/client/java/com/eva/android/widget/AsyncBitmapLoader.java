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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.eva.android.BitmapHelper;
import com.eva.android.HttpFileDownloadHelper;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Observer;

/**
 * 一个拥有图片缓存能力的图片读取实用类。
 * <p>
 * 读取图片时，如果指定图片已存在于内存中则直接返回，否则尝试从sd卡中
 * 查找，如果sd卡中仍不存在则尝试从网络下载之（下载成功后会自动保存到
 * sd卡，下次就不需要再次下载了，从而节省流量）。
 * 
 * @author oneRain, Jack Jiang
 * @see #loadBitmap(ImageView, String, String, ImageCallBack, Observer, int, int, boolean)
 * @deprecated by AsyncBitmapLoader2
 */
@Deprecated
public class AsyncBitmapLoader
{
	private final static String TAG = AsyncBitmapLoader.class.getSimpleName();
	
	/** 
	 * 使用在线列表的目的在于防止正在在载中的商品图片被重复下载（同时开启多个线程下载同一图片），
	 * 否则很容易就内存溢出了！
	 */
	private ArrayList<String> mDownloadingGoodPics = new ArrayList<String>();
	
	/** 内存图片软引用缓冲 */
	private LruCache<String, SoftReference<Bitmap>> mImageCache = null;
	
	/** 本地缓存目录 */
 	private String mLocalCacheDir = null;
	
	/**
	 * 构造方法。
	 * 
	 * @param DEFAULT_CACHED_SDCARD_PATH 此路径目录地址末尾是需要"/"的哦！
	 */
	public AsyncBitmapLoader(String DEFAULT_CACHED_SDCARD_PATH)
	{
		this(DEFAULT_CACHED_SDCARD_PATH
				// 默认以系统允许的APP的1/8可用内存作为最大图片缓存内存
				, ((int) (Runtime.getRuntime().maxMemory() / 1024)) / 8); 
	}
	
	/**
	 * 构造方法。
	 * 
	 * @param DEFAULT_CACHED_SDCARD_PATH 此路径目录地址末尾是需要"/"的哦！
	 * @param maxMemory 用于图片缓存的最大内存（单位是KB）
	 * @see LruCache
	 */
	public AsyncBitmapLoader(String DEFAULT_CACHED_SDCARD_PATH, int maxMemory)
	{
		this.mLocalCacheDir = DEFAULT_CACHED_SDCARD_PATH;
	    int appMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
	    Log.d(TAG, "【ABL】程序当前可运行的最大内存是："+appMemory+"KB, 用于图片缓存的内存空间："+maxMemory+"KB.");
	    
	    // LruCache通过构造函数传入缓存值，以KB为单位。  
	    mImageCache = new LruCache<String, SoftReference<Bitmap>>(maxMemory);
	}

    /**
     * 清空指定key的缓存（注意：本方法当前只实现了清除内存缓存，暂未实现同时清除sd卡中的缓存文件哦）。
     *
     * @param key
     * @retrun true表示清除成功，否则不成功（可能是此key的缓存并不存）
     */
    public boolean clearCache(String key)
    {
        if(key != null && mImageCache != null)
        {
            return this.mImageCache.remove(key) != null;
        }
        return false;
    }
	
	/**
	 * 尝试异步加载图片.
	 * <p>
	 * 逻辑可能是优先从内存中读取、内存没找到的情况下再尝试从本地读取，最后才尝试从网络读取.
	 * <p>
	 * 在imageURL == null且imageFileName != null的情况下，本类相当于本地文件缓存实现类。
	 * 
	 * @param imageView 图片成功获取后要显示的目标UI组件
	 * @param imageURL 在没有缓存的情况下尝试从网络加载的地址：本参数可为null，但与参数imageFileName不可同时为
	 * null.本参数为null时即意味着只尝试从本地SD卡中读取图片数据到缓存中（而不从网络读取了）
	 * @param imageCallBack 图片读取完成（成功或失败）后的回调，调用者的图片的显示处理逻辑可在此实现
	 * @return 成功读取Bitmap对象后即返回之，否则返回null
	 */
	public Bitmap loadBitmap(final ImageView imageView, final String imageURL
			, final ImageCallBack imageCallBack)
	{
		return loadBitmap(imageView, imageURL, null, imageCallBack
                , null, -1, -1, false);
	}
	
	/**
	 * 尝试异步加载图片.
	 * <p>
	 * 逻辑可能是优先从内存中读取、内存没找到的情况下再尝试从本地读取，最后才尝试从网络读取.
	 * <p>
	 * 在imageURL == null且imageFileName != null的情况下，本类相当于本地文件缓存实现类。
	 * 
	 * @param imageView 图片成功获取后要显示的目标UI组件
	 * @param imageURL 在没有缓存的情况下尝试从网络加载的地址：本参数可为null，但与参数imageFileName不可同时为
	 * null.本参数为null时即意味着只尝试从本地SD卡中读取图片数据到缓存中（而不从网络读取了）
	 * @param imageCallBack 图片读取完成（成功或失败）后的回调，调用者的图片的显示处理逻辑可在此实现
	 * @param reqWidth 生成的Bitmap对象时的真正图片宽(此值用于减小最终生成的Bitmap内存时有用)，<=0表示不启用
	 * @param reqHeight 生成的Bitmap对象时的真正图片高(此值用于减小最终生成的Bitmap内存时有用)，<=0表示不启用
	 * @return 成功读取Bitmap对象后即返回之，否则返回null
	 */
	public Bitmap loadBitmap(final ImageView imageView, final String imageURL
			, final ImageCallBack imageCallBack
			/** 
			 * 要转换成的像素数，比如：原图是640*640的大图，但用到的地方只需要200*200的图，
			 * 那么此值设为200*200为佳，因这将使得返回的Bitmap对象占用的内存为200*200而非640*640 */
			, final int reqWidth, final int reqHeight)
	{
		return loadBitmap(imageView, imageURL, null, imageCallBack
                , null, reqWidth, reqHeight, false);
	}
	
	/**
	 * 尝试异步加载图片.
	 * <p>
	 * 逻辑可能是优先从内存中读取、内存没找到的情况下再尝试从本地读取，最后才尝试从网络读取.
	 * <p>
	 * 在imageURL == null且imageFileName != null的情况下，本类相当于本地文件缓存实现类。
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
	public Bitmap loadBitmap(final ImageView imageView, final String imageURL
			, final String imageFileName, final ImageCallBack imageCallBack)
	{
		return loadBitmap(imageView, imageURL, imageFileName, imageCallBack
                , null, -1, -1, false);
	}
	
	/**
	 * 尝试异步加载图片.
	 * <p>
	 * 逻辑可能是优先从内存中读取、内存没找到的情况下再尝试从本地读取，最后才尝试从网络读取.
	 * <p>
	 * 在imageURL == null且imageFileName != null的情况下，本类相当于本地文件缓存实现类。
	 * 
	 * @param imageView 图片成功获取后要显示的目标UI组件
	 * @param imageURL 在没有缓存的情况下尝试从网络加载的地址：本参数可为null，但与参数imageFileName不可同时为
	 * null.本参数为null时即意味着只尝试从本地SD卡中读取图片数据到缓存中（而不从网络读取了）
	 * @param imageFileName 要缓存的文件名（本方法中使用的优先级1），通常情况下就是最终缓存到SD卡中的文件名。本
	 * 参数可为null，为null时本方法将尝试智能从参数 imageURL中取出来本方法中使用的优先级2），但并不绝对，因为url
	 * 中很可能没有存放真正的文件名!本参数可为null，但与参数imageURL不可同时为null.
	 * @param imageCallBack 图片读取完成（成功或失败）后的回调，调用者的图片的显示处理逻辑可在此实现
	 * @param reqWidth 生成的Bitmap对象时的真正图片宽(此值用于减小最终生成的Bitmap内存时有用)，<=0表示不启用
	 * @param reqHeight 生成的Bitmap对象时的真正图片高(此值用于减小最终生成的Bitmap内存时有用)，<=0表示不启用
	 * @return 成功读取Bitmap对象后即返回之，否则返回null
	 */
	public Bitmap loadBitmap(final ImageView imageView, final String imageURL
			, final String imageFileName, final ImageCallBack imageCallBack
			/** 
			 * 要转换成的像素数，比如：原图是640*640的大图，但用到的地方只需要200*200的图，
			 * 那么此值设为200*200为佳，因这将使得返回的Bitmap对象占用的内存为200*200而非640*640 */
			, final int reqWidth, final int reqHeight
			)
	{
		return loadBitmap(imageView, imageURL, imageFileName
				, imageCallBack, null, reqWidth, reqHeight, false);
	}
	
	/**
	 * 尝试异步加载图片.
	 * <p>
	 * 逻辑可能是优先从内存中读取、内存没找到的情况下再尝试从本地读取，最后才尝试从网络读取.
	 * <p>
	 * 在imageURL == null且imageFileName != null的情况下，本类相当于本地文件缓存实现类。
	 * 
	 * @param imageView 图片成功获取后要显示的目标UI组件
	 * @param imageURL 在没有缓存的情况下尝试从网络加载的地址：本参数可为null，但与参数imageFileName不可同时为
	 * null.本参数为null时即意味着只尝试从本地SD卡中读取图片数据到缓存中（而不从网络读取了）
	 * @param imageFileName 要缓存的文件名（本方法中使用的优先级1），通常情况下就是最终缓存到SD卡中的文件名。本
	 * 参数可为null，为null时本方法将尝试智能从参数 imageURL中取出来本方法中使用的优先级2），但并不绝对，因为url
	 * 中很可能没有存放真正的文件名!本参数可为null，但与参数imageURL不可同时为null.
	 * @param imageCallBack 图片读取完成（成功或失败）后的回调，调用者的图片的显示处理逻辑可在此实现
	 * @param imageFileNameCallBack 服务端可能返回的文件名（有些情况下可能不会返回文件名），本回调用于获取服务端
	 * 可能返回来的文件名。本回调的作用在于本例用本方法前完全没办法拿到文件名的情况下，尝试下载服务端文件数据的同时
	 * 也把文件名取下来供调用本方法者存起来后绪使用哦.本回调被调用时会将服务端传过来的文件名作为Observer类的update
	 * 方法的参数data传过来。本参数为null表示不开启回调功能.<br>为何要使用本参数而不干脆让本方法返回一个包含了文件
	 * 的数据组呢（简单又好理解），目的是不想加重对让原来的方法使用的难度，反正需要文件名的地方用本回调就行了，这样
	 * 更合理一些
	 * @param reqWidth 生成的Bitmap对象时的真正图片宽(此值用于减小最终生成的Bitmap内存时有用)，<=0表示不启用
	 * @param reqHeight 生成的Bitmap对象时的真正图片高(此值用于减小最终生成的Bitmap内存时有用)，<=0表示不启用
     * @param donotLoadFromDisk true表示当内存中不存在缓存时不尝试从SD卡读取直接从网络加载，否则将按正常逻辑尝
     *                          试从sd卡加载，一般情况下请用false
	 * @return 成功读取Bitmap对象后即返回之，否则返回null
	 */
	public Bitmap loadBitmap(final ImageView imageView
			, final String imageURL
			, final String imageFileName
			, final ImageCallBack imageCallBack
            , final Observer imageFileNameCallBack
			/** 
			 * 要转换成的像素数，比如：原图是640*640的大图，但用到的地方只需要200*200的图，
			 * 那么此值设为200*200为佳，因这将使得返回的Bitmap对象占用的内存为200*200而非640*640 */
			, final int reqWidth, final int reqHeight
            , final boolean donotLoadFromDisk
			)
	{
		// 此2参数不可同时为null!
		if(imageURL == null && imageFileName == null)
		{
			Log.e(TAG, "【ABL】imageURL和imageFileName不可同时为null！");
			return null;
		}
		
		// 是否要调整图片的BitmapFactory.Options.inSampleSize值(从而节省内存)
		final boolean needSetInSampleSize = (reqWidth > 0 && reqHeight > 0);
		
		Log.d(TAG, "【ABL】======================图片"+imageURL+"("+imageFileName+")载入中======================");
		
		final String cacheKey = (imageURL != null ? imageURL : imageFileName);
		//在内存缓存中，则返回Bitmap对象
		if(mImageCache.get(cacheKey) != null)
		{
//			Log.d(TAG, "【MALL】imageCache.get("+imageURL+") != null!");
			SoftReference<Bitmap> reference = mImageCache.get(cacheKey);
			Bitmap bitmap = (Bitmap)reference.get();
			if(bitmap != null)
			{
				Log.d(TAG, "【ABL】缓存中找到了图片："+imageURL+"直接返回。。。。");
				return bitmap;
			}
		}
		Log.d(TAG, "【ABL】缓存中没有找到图片(或者已找到但SoftReference中实例已被回收并置为null)："+imageURL+"...");
			
		/**
		 * 加上一个对本地SD卡缓存的查找
		 */
		final String bitmapFileName = (imageFileName == null?imageURL.substring(imageURL.lastIndexOf("/") + 1):imageFileName);
			
		Log.d(TAG, "【ABL】imageFileName="+imageFileName+", imageURL="+imageURL+", bitmapFileName="+bitmapFileName);

		if(donotLoadFromDisk)
        {
            Log.i(TAG, "【ABL】donotLoadFromDisk=true，不需要尝试从磁盘加载，将尝试从网络加载！");
        }
        else
        {
            File localCachedFile = new File(mLocalCacheDir + bitmapFileName);
            Log.d(TAG, "【ABL】它存在于本地SD卡" + localCachedFile.getAbsolutePath() + "中吗？" + localCachedFile.exists());
            if (localCachedFile.exists()) {

                Bitmap bb = null;
                // 为了代码的健壮性，在涉及Bitmap操作的地方应习惯性地进行OOM显示捕获，防止在极烂的手机上OOM
                try
                {
                    bb = BitmapFactory.decodeFile(localCachedFile.getAbsolutePath()
                            , needSetInSampleSize ? BitmapHelper.computeSampleSize2(localCachedFile.getAbsolutePath(), reqWidth, reqHeight) : null);
                }
                catch(OutOfMemoryError oom)
                {
                    Log.w(TAG, "A在执行BitmapFactory.decodeFile(..)时OOM了，本次操作没有继续哦。", oom);
                }

                if(bb != null)
                {
                    // 2013-09-28 add by Jack Jiang
                    mImageCache.put(cacheKey, new SoftReference<Bitmap>(bb));
                }

                Log.d(TAG, "【ABL】它存在于本地SD卡中，直接返回了。。。");
                return bb;
            }
        }
		
		Log.d(TAG, "【ABL】》》》》》》》它也不存在于本地SD卡中，从网络异步加载马上开始...");
		// 商品图片已经在”下载中“（用于防止重复下载）
		if(mDownloadingGoodPics.contains(cacheKey))
		{
			Log.d(TAG, "【ABL】"+imageURL+"正在下载中，本次下载请求将被忽略，无需重复下载，否则将OOM!");
		}
		else
		{
			Log.d(TAG, "【ABL】"+imageURL+"还未下载，马上开始异步下载线程...");
			
			final Handler handler = new Handler()
			{
				@Override
				public void handleMessage(Message msg)
				{
					if(msg.what == 0)
					{
						Log.d(TAG, "【ABL】》》》》》》》图片"+imageURL+"从网络加载完成，即将显示到UI上.");
						imageCallBack.imageLoad(imageView, (Bitmap)msg.obj);
					}
					else if(msg.what == -1)
					{
						Log.d(TAG, "【ABL】》》》》》》》图片"+imageURL+"从网络加载失败了！");
						imageCallBack.imageLoadFaild(imageView);
					}
				}
			};

			//如果不在内存缓存中，也不在本地（被jvm回收掉），则开启线程下载图片
			new Thread()
			{
				@Override
				public void run()
				{
					// 将正在下载中的商品图片地址加入到”下载中“列表（用于防止重复下载）
					mDownloadingGoodPics.add(cacheKey);
					
					//
					boolean downSucess = false;
					
					//** 【说明：】为何使用HTTP先把文件下载下来后再decode成bitmap而不是先用bitmap.decodeStream后再将其保存成本地文件呢？
					//** 原因在于后者保存的文件要远大于实际存放于服务器端的文件大小（因Bitmap再转文件时有很多位图元数据等，而且也不能压缩
					//** ，因服力端本身已压缩过，再压缩就质量越来越差了），而前者下下来后还是跟服务端一样的大小，再decode成bitmap一样正常使用
					Bitmap bitmap = null;
					try
					{
//						InputStream bitmapIs = ToolKits.getStreamFromURL(imageURL);
						// 目录不存在则建立之
						File dir = new File(mLocalCacheDir);
						
						String savedFilePath = null;
//						String savedFilePath = HttpFileDownloadHelper.downloadFile(imageURL, dir.getAbsolutePath(), 0, null);
////						bitmap = BitmapFactory.decodeStream(bitmapIs);
						Object[] downloadRet = HttpFileDownloadHelper.downloadFileEx(imageURL
                                , dir.getAbsolutePath(), 0, null, false);
						if(downloadRet != null && downloadRet.length >=3)
						{
							// 0单元存放的是文件最终下载到的绝对路径地址
							savedFilePath = (String)downloadRet[0];
							// 服务端返回的文件名（有时候本AsyncBitmapLoader无法指明要缓存的文件名，而服务端可以返回文件名，那么
							// 本方法中就不需要明确传进来了，节省调用AsyncBitmapLoader类的容易程度）
							String downloadFileName = (String)downloadRet[2];
							if(imageFileNameCallBack != null)
								imageFileNameCallBack.update(null, downloadFileName);
							//
							if(savedFilePath != null)
							{
                                // 为了代码的健壮性，在涉及Bitmap操作的地方应习惯性地进行OOM显示捕获，防止在极烂的手机上OOM
                                try
                                {
								    bitmap = BitmapFactory.decodeFile(savedFilePath
										, needSetInSampleSize? BitmapHelper.computeSampleSize2(savedFilePath, reqWidth, reqHeight): null);
                                }
                                catch(OutOfMemoryError oom)
                                {
                                    Log.w(TAG, "B在执行BitmapFactory.decodeFile(..)时OOM了，本次操作没有继续哦。", oom);
                                }

								if(bitmap != null)
								{
									//
									mImageCache.put(cacheKey, new SoftReference<Bitmap>(bitmap));
									Message msg = handler.obtainMessage(0, bitmap);
									handler.sendMessage(msg);
									downSucess = true;
								}
							}
						}
					}
					catch (Exception e)
					{
						Log.d(TAG, e.getMessage(), e);
					}
					finally
					{
						// 下载或其它处理导致从网络读取没有成功的情况下
						if(!downSucess)
						{
							Message msg = handler.obtainMessage(-1, null);
							handler.sendMessage(msg);
						}
					}
					
					// 将正在下载中的商品图片地址“下载中”列表移除（已经下载完了或者下载出错）
					mDownloadingGoodPics.remove(cacheKey);
				}
			}.start();
		}
		
		return null;
	}
	
	/**
	 * 回调接口.
	 */
	public static abstract class ImageCallBack
	{
		public abstract void imageLoad(ImageView imageView, Bitmap bitmap);
		
		public void imageLoadFaild(ImageView imageView)
		{
			// default do nothing
		}
	}
}