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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 提供各种操作Bitmap的实用方法辅助类.
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 */
public class BitmapHelper
{
	private final static String TAG = BitmapHelper.class.getSimpleName();
	
	/**
	 * 按百分比压缩Bitmap的尺寸.
	 * 
	 * @param originalBm 要被压缩前的原始Bitmap对象引用
	 * @param decreaseToPercent 要压缩到的百分比，有效值是：> 0 and <=1（等于1意味着不需要压缩哦）
	 * @param recycleOriginalAfterProcess true表示本方法将无条件尝试回收原始bitmap对象的内存，否则什么也不做
	 * @return 成功处理完成将返回压缩后的Bitmap新对象，否则返回null
	 * @throws OutOfMemoryError 如果发生内存溢出则抛出
	 * @see {@link Bitmap#createBitmap(Bitmap, int, int, int, int, Matrix, boolean)}
	 */
	public static Bitmap decreaseBitmapSize(Bitmap originalBm, float decreaseToPercent
			, boolean recycleOriginalAfterProcess) throws OutOfMemoryError
	{
        if(originalBm == null)
        {
            Log.e(TAG, "originalBm is null!");
            return null;
        }

        if(decreaseToPercent < 0 || decreaseToPercent > 1)
        {
            Log.e(TAG, "decreaseToPercent is < 0 or > 1, decreaseToPercent="+decreaseToPercent);
            return null;
        }

        Matrix matrix = new Matrix();
        // 按百分比进行尺寸缩放
        matrix.postScale(decreaseToPercent, decreaseToPercent);

        return decreaseBitmapSize(originalBm, decreaseToPercent, recycleOriginalAfterProcess, matrix, false);
	}
	
	/**
	 * 按百分比压缩Bitmap的尺寸.
	 * 
	 * @param originalBm 要被压缩前的原始Bitmap对象引用
	 * @param decreaseToPercent 要压缩到的百分比，有效值是：> 0 and <=1的浮点值（等于1意味着不需要压缩哦）
	 * @param recycleOriginalAfterProcess true表示本方法将无条件尝试回收原始bitmap对象的内存，否则什么也不做
	 * @param matrix
	 * @param filter
	 * @return 成功处理完成将返回压缩后的Bitmap新对象，否则返回null
	 * @throws OutOfMemoryError 如果发生内存溢出则抛出
	 * @see {@link Bitmap#createBitmap(Bitmap, int, int, int, int, Matrix, boolean)}
	 */
	public static Bitmap decreaseBitmapSize(Bitmap originalBm, float decreaseToPercent
			, boolean recycleOriginalAfterProcess
			, Matrix matrix, boolean filter) throws OutOfMemoryError
	{
		Bitmap bmAfterProcessed = null;
		
		if(originalBm == null)
		{
			Log.e(TAG, "originalBm is null!");
			return null;
		}
		
		if(decreaseToPercent < 0 || decreaseToPercent > 1)
		{
			Log.e(TAG, "decreaseToPercent is < 0 or > 1, decreaseToPercent="+decreaseToPercent);
			return null;
		}
		
		try
		{
			// 仿微信：拍完后缩小至75%，从而降低图片大小
//			bmAfterProcessed = Bitmap.createBitmap(originalBm
//					, 0, 0
//					, (int)(originalBm.getWidth() * decreaseToPercent)
//					, (int)(originalBm.getHeight() * decreaseToPercent)
//					, matrix, filter);

            bmAfterProcessed = Bitmap.createBitmap(originalBm
                    , 0, 0
                    , originalBm.getWidth()
                    , originalBm.getHeight()
                    , matrix, filter);
		}
		catch (Exception ee)
        {
            Log.d(TAG, "decreaseBitmapSize时发生异常了", ee);
            bmAfterProcessed = originalBm;
        }
		catch (OutOfMemoryError e)
		{
            Log.d(TAG, "decreaseBitmapSize时OOM了", e);

			if(bmAfterProcessed != null && !bmAfterProcessed.isRecycled())
				bmAfterProcessed.recycle();
			bmAfterProcessed = null;
			throw e;
		}
		finally
		{
			if(recycleOriginalAfterProcess && originalBm != null && !originalBm.isRecycled())
				originalBm.recycle();
				
		}
		
		return bmAfterProcessed;
	}
	
	/**
	 * 解析Uri指向的图片为Bitmap对象.
	 * 
	 * @param context
	 * @param uri 指向图片资源的Uri
	 * @return 成功则返回Bitmap对象，否则将返回null
	 * @throws OutOfMemoryError 如果内存溢出则抛出此异常
	 */
	public static Bitmap decodeUriAsBitmap(Context context, Uri uri) throws OutOfMemoryError
	{
		return decodeUriAsBitmap(context, uri, null);
	}
	/**
	 * 解析Uri指向的图片为Bitmap对象.
	 * 
	 * @param context
	 * @param uri 指向图片资源的Uri
	 * @return 成功则返回Bitmap对象，否则将返回null
	 * @throws OutOfMemoryError 如果内存溢出则抛出此异常
	 */
	public static Bitmap decodeUriAsBitmap(Context context, Uri uri, BitmapFactory.Options opts) throws OutOfMemoryError
	{
		Bitmap bitmap = null;
		try 
		{
			bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, opts);
		} 
		// 显示处理下OOM使得APP更健壮（OOM只能显示抓取，否则会按系统Error的方式报出从而使APP崩溃哦）
		catch (OutOfMemoryError e)
		{
			Log.e(TAG, "将图片decodeUriAsBitmap到内存时内存溢出了，执行没有继续！", e);
			throw e;
//			WidgetUtils.showToast(parentActivity
//					, parentActivity.getString(R.string.user_info_avatar_upload_faild3)
//					, ToastType.WARN);
//			Log.e(TAG, "【ChangeAvatar】将头像文件数据decodeUriAsBitmap到内存时内存溢出了，上传没有继续！", e);
		}
		catch (FileNotFoundException e) 
		{
			//				e.printStackTrace();
			Log.e(TAG, "将图片decodeUriAsBitmap时出错了，"+e.getMessage(), e);
			return null;
		}
		return bitmap;
	}
	
	/**
	 * 从网络路径读取一个Drawable对象.
	 * 
	 * @param httpURL 对象Drawable对象(一般是一个图片)的http URL地址
	 * @return 如果读取成功则反回Drawable对象，否则返回null(换句话讲，如果返回null则可认为是读取失败了)
	 */
	public static Drawable loadHttpDrawable(String httpURL) throws Exception
	{
		try
		{
			//加载网络图片的方法相当简单，值得在其它地方使用
			return Drawable.createFromStream((InputStream) (new URL(httpURL)).openStream(), "test");
		}
		catch (IOException e)
		{
//			return null;
			throw e;
		}
	}
	
	/**
	 * 从本地文件中载入一个Drawble对象.
	 * 
	 * @param filePath
	 * @return
	 */
	public static Drawable loadDrawble(String filePath)
	{
		//加载图片
		return Drawable.createFromPath(filePath);
	}
	
	/**
	 * 读取本地图片.
	 * 
	 * @param localUrl 本地图片文件物理地址
	 * @return Bitmap 成功读取则返回图片对象，否则返回null
	 */
	public static Bitmap loadLocalBitmap(String localUrl) throws Exception
	{
		return loadLocalBitmap(localUrl, null);
	}
	/**
	 * 读取本地图片.
	 * 
	 * @param localUrl 本地图片文件物理地址
	 * @return Bitmap 成功读取则返回图片对象，否则返回null
	 */
	public static Bitmap loadLocalBitmap(String localUrl, BitmapFactory.Options opts) throws Exception
	{
		try
		{
//			FileInputStream fis = new FileInputStream(localUrl);
//			return BitmapFactory.decodeStream(fis, opts);
			return BitmapFactory.decodeFile(localUrl, opts);
		}
		catch (Exception e)
		{
//			e.printStackTrace();
//			return null;
			throw e;
		}
	}
	/**
	 * 读取网络图片.
	 * 
	 * @param httpURL 网络图片地址
	 * @return Bitmap 成功读取则返回图片对象，否则返回null
	 */
	public static Bitmap loadHttpBitmap(String httpURL) throws Exception
	{
		Bitmap bitmap = null;
		try
		{
			URL myFileUrl = new URL(httpURL);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			conn.setConnectTimeout(30000);// timeout 30 s //0);
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		}
		catch (MalformedURLException e)
		{
//			e.printStackTrace();
			throw e;
		}
		catch (IOException e)
		{
//			e.printStackTrace();
			throw e;
		}
		return bitmap;
	}
	
	/**
	 * 保存（并提供压缩图像质量的能力）Bitmap到SDCard文件中.
	 * 
	 * <p>很多情况下需要将Bitmap保存成文件，但高质量的图片文件大小会很大，
	 * 本方法的目的就是在减小图片质量的情况下达到降低文件大小的目的.
	 * 
	 * @param bp 原图
	 * @param qaulity 图像质量，0~100值，据根经验：小于75后，压缩大小就不明显了
	 * @param outputDestFile 压缩完成后输出的文件路径
	 * @return true表示压缩成功，否则表示失败
	 * @throws Exception 过程中出现任何异常都将抛出
	 */
	public static boolean saveBitmapToFile(Bitmap bp, int qaulity, File outputDestFile) throws Exception
	{
		if(bp == null || outputDestFile == null)
			return false;
		try
		{
			FileOutputStream outputStream = new FileOutputStream(outputDestFile); // 文件输出流

			bp.compress(Bitmap.CompressFormat.JPEG
					// # 据测试，微信将640*640的图片裁剪、压缩后的大小约为34K左右，经测试估计是质量75哦
					// # 调整此值可压缩图像大小，经测试，再小于75后，压缩大小就不明显了
					// # 经Jack Jiang在Galaxy sIII上测试：原拍照裁剪完成的60K左右的头像按75压缩后大小约为34K左右，
					//   从高清图片中选取的裁剪完成时的200K左右按75压缩后大小依然约为34K左右，所以75的压缩比率在头
					//   像质量和文件大小上应是一个较好的平衡点
					, qaulity
					, outputStream);// 将图片压缩到流中

			outputStream.flush();// 输出
			outputStream.close(); // 关闭输出流
			return true;
		}
		catch(Exception e)
		{
			throw e;
//			return false;
		}
	}
	
//	/**
//	 * 图片圆角处理.
//	 * 
//	 * @param bitmap
//	 * @param roundPX
//	 * @return
//	 */
//	public static Bitmap getRCB(Bitmap bitmap, float roundPX) //RCB means Rounded Corner Bitmap
//	{
//		if(bitmap == null)
//			return null;
//		Bitmap dstbmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
//		Canvas canvas = new Canvas(dstbmp);
//		final int color = 0xff424242;
//		final Paint paint = new Paint();
//		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//		final RectF rectF = new RectF(rect);
//		paint.setAntiAlias(true);
//		canvas.drawARGB(0, 0, 0, 0);
//		paint.setColor(color);
//		canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
//		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//		canvas.drawBitmap(bitmap, rect, rect, paint); 
//		return dstbmp;
//	}
	
	//======================================================================== 计算inSampleSize【算法2】 START
	//********************************** 此算法由国人编写，简单直接，推荐使用
	//http://blog.csdn.net/soldierguard/article/details/9369461
	public static BitmapFactory.Options computeSampleSize2(String filePath, 
			int reqWidth, int reqHeight)
	{
		BitmapFactory.Options opts = new BitmapFactory.Options();
		try
		{
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(filePath, opts);
			
//			float scale = (float) opts.inTargetDensity / opts.inDensity;
//		    int actualW = (int) (opts.outWidth * scale + 0.5f);
//		    int actualH = (int) (opts.outHeight * scale + 0.5f);
//		    Log.d("computeSampleSize", ">> inSampleSize计算｛@｝，actualW="+actualW+", actualH="+actualH);
			
			opts.inSampleSize = computeSampleSize2(opts, reqWidth, reqHeight);  
		}
		catch (Exception e)
		{
			Log.e("computeSampleSize", "计算图片1的inSampleSize时出错.", e.getCause());
		}
		finally
		{
			opts.inJustDecodeBounds = false;
		}
		
		Log.d("computeSampleSize", ">> inSampleSize算法[2]计算完成，计算结果是【"+opts.inSampleSize+"】，reqWidth="+
				reqWidth+", reqHeight="+reqHeight+", filePath="+filePath);
		
		return opts;
	}
	public static int computeSampleSize2(BitmapFactory.Options options, 
			int reqWidth, int reqHeight) 
	{
		// 计算原始图像的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		
		Log.d("computeSampleSize", ">> inSampleSize算法[2]计算中，[原始options.outWidth="+options.outWidth
				+", o原始ptions.outHeight="+options.outHeight
				+"]，目标reqWidth="+reqWidth+", 目标reqHeight="+reqHeight+", options="+options);
		
		int inSampleSize = 1;

		//判定，当原始图像的高和宽大于所需高度和宽度时
		if (height > reqHeight || width > reqWidth) 
		{
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			//算出长宽比后去比例小的作为inSamplesize，保障最后imageview的dimension比request的大
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
			//计算原始图片总像素
			final float totalPixels = width * height;
			// Anything more than 2x the requested pixels we'll sample down further
			//所需总像素*2,长和宽的根号2倍
			final float totalReqPixelsCap = reqWidth * reqHeight * 2;

			//如果遇到很长，或者是很宽的图片时，这个算法比较有用 
			while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap)
			{
				inSampleSize++;
			}
		}
		return inSampleSize;
	}
	//======================================================================== 计算inSampleSize【算法2】 END
	
	//======================================================================== 计算inSampleSize【算法1】 START
	//********************************** 此算法由老外编写，感觉有点复杂，不推荐使用
//	/**
//	 * 计算BitmapFactory.Options的inSampleSize值，该值为2即意味着输出原图1/2大小图作为bitmap对象.
//	 * <p>
//	 * 该属性的设置将可直接对裁剪Bitmap对象所占内存有效！
//	 * 
//	 * @param filePath
//	 * @param minSideLength
//	 * @param maxNumOfPixels
//	 * @return
//	 * @deprecated
//	 */
//	public static BitmapFactory.Options computeSampleSize(String filePath, int minSideLength, int maxNumOfPixels)
//	{
//		BitmapFactory.Options opts = new BitmapFactory.Options();
//		try
//		{
//			opts.inJustDecodeBounds = true;
//			BitmapFactory.decodeFile(filePath, opts);
//			
////			float scale = (float) opts.inTargetDensity / opts.inDensity;
////		    int actualW = (int) (opts.outWidth * scale + 0.5f);
////		    int actualH = (int) (opts.outHeight * scale + 0.5f);
////		    Log.d("computeSampleSize", ">> inSampleSize计算｛@｝，actualW="+actualW+", actualH="+actualH);
//			
//			opts.inSampleSize = computeSampleSize(opts, minSideLength, maxNumOfPixels);  
//		}
//		catch (Exception e)
//		{
//			Log.e("computeSampleSize", "计算图片1的inSampleSize时出错.", e.getCause());
//		}
//		finally
//		{
//			opts.inJustDecodeBounds = false;
//		}
//		
//		Log.d("computeSampleSize", ">> inSampleSize算法[1]计算完成，计算结果是【"+opts.inSampleSize+"】，minSideLength="+
//				minSideLength+", maxNumOfPixels="+maxNumOfPixels+", filePath="+filePath);
//		
//		return opts;
//	}
//	/**
//	 * 
//	 * @param options
//	 * @param minSideLength
//	 * @param maxNumOfPixels
//	 * @return
//	 * @deprecated
//	 */
//	public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels)
//	{
//		int inSampleSizeForRet = 1;
//		try
//		{
//			int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
//			if (initialSize <= 8)
//			{
//				inSampleSizeForRet = 1;
//				while (inSampleSizeForRet < initialSize)
//					inSampleSizeForRet <<= 1;
//			}
//			else
//				inSampleSizeForRet = (initialSize + 7) / 8 * 8;
//		}
//		catch (Exception e)
//		{
//			Log.e("computeSampleSize", "计算图片2的inSampleSize时出错.", e.getCause());
//		}
//		return inSampleSizeForRet;
//	}
//	/**
//	 * 
//	 * @param options
//	 * @param minSideLength
//	 * @param maxNumOfPixels
//	 * @return
//	 * @deprecated
//	 */
//	private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels)
//	{
//		double w = options.outWidth;
//		double h = options.outHeight;
//		Log.d("computeSampleSize", ">> inSampleSize算法[1]计算中，[原始options.outWidth="+options.outWidth
//				+", o原始ptions.outHeight="+options.outHeight
//				+"]，目标minSideLength="+minSideLength+", 目标maxNumOfPixels="+maxNumOfPixels+", options="+options);
//		int lowerBound = (maxNumOfPixels == -1) ? 1 :
//			(int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
//		int upperBound = (minSideLength == -1) ? 128 :
//			(int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
//
//		if (upperBound < lowerBound)
//			// return the larger one when there is no overlapping zone.
//			return lowerBound;
//		
//		if ((maxNumOfPixels == -1) && (minSideLength == -1))
//			return 1;
//		else if (minSideLength == -1)
//			return lowerBound;
//		else
//			return upperBound;
//	}
	//======================================================================== 计算inSampleSize【算法1】 END

	public static Bitmap drawableToBitmap(Drawable drawable)
	{       
		if(drawable == null)
			return null;
		Bitmap bitmap = Bitmap.createBitmap(
				drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(),
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
						: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		//canvas.setBitmap(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

}
