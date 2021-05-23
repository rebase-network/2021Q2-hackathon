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
import android.content.Intent;
import android.net.Uri;

//import com.eva.android.shortvideo.ShortVideoPlayerActivity;
import com.eva.android.widget.ImageViewActivity;

import java.util.ArrayList;

/**
 * 一个通用Intent的工厂类.
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 */
public class IntentFactory
{
	/**
	 * <p>
	 * 返回一个调用Home键的任务Intent.<br><br>
	 *
	 * startActivity本intent相当于按下了Home键，典型应用场景是：<br>
	 * 不想程序真正退出（按back键默认是调用acticity的finish）只想像按了Home键一样
	 * 使得本activity进入后台，下次点击时数据都还在等等.
	 * </p>
	 *
	 * @return
	 */
	public static Intent createPrssedHomeKeyIntent()
	{
		//实现Home键效果
		Intent i = new Intent(Intent.ACTION_MAIN);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		i.addCategory(Intent.CATEGORY_HOME);
		return i;
	}
	
	/**
	 * 打开ImageViewActivity的Intent构造方法.
	 * 
	 * @param thisActivity
	 * @return
	 */
	public static Intent createImageViewActivityIntent_fromUrl(Context thisActivity, String httpUrl, String saveToDir)
	{
		return createImageViewActivityIntent(thisActivity, ImageViewActivity.ImageDataType.URL
				, httpUrl, ImageViewActivity.class, -1, -1
				, saveToDir, null, null, null, null, null);
	}
	/**
	 * 打开ImageViewActivity的Intent构造方法.
	 * 
	 * @param thisActivity
	 * @return
	 */
	public static Intent createImageViewActivityIntent_fromUri(Context thisActivity, Uri imageUri)
	{
		return createImageViewActivityIntent(thisActivity, ImageViewActivity.ImageDataType.URI
				, imageUri.toString(), ImageViewActivity.class, -1, -1
				, null, null, null, null, null, null);
	}
	/**
	 * 打开ImageViewActivity的Intent构造方法.
	 * 
	 * @param thisActivity
	 * @return
	 */
	public static Intent createImageViewActivityIntent_fromFile(Context thisActivity, String imageFilePath)
	{
		return createImageViewActivityIntent(thisActivity, ImageViewActivity.ImageDataType.FILE_PATH
				, imageFilePath, ImageViewActivity.class, -1, -1
				, null, null, null, null, null, null);
	}
	/**
	 * 打开ImageViewActivity的Intent构造方法.
	 * 
	 * @param thisActivity
	 * @param imageDataType
	 * @param imageDataSrc
	 * @return
	 */
	public static Intent createImageViewActivityIntent(Context thisActivity
			, int imageDataType, String imageDataSrc)
	{
		return createImageViewActivityIntent(thisActivity, imageDataType
				, imageDataSrc, ImageViewActivity.class, -1, -1
				, null, null, null, null, null, null);
	}
	/**
	 * 打开ImageViewActivity的Intent构造方法.
	 * 
	 * @param thisActivity
	 * @param imageDataType
	 * @param imageDataSrc
	 * @param requestWidth 此项将用于计算BitmapFactory.Opts的inSimpleSize值，目的是保
	 * 证加载到内存中的图片不至于过大，此值将会与requestHeight一同计算出最终的inSimpleSize
	 * ，从而使得加载到内存中的Bitmap不至于过大而导致OOM
	 * @param requestHeight 此项将用于计算BitmapFactory.Opts的inSimpleSize值，目的是保
	 * 证加载到内存中的图片不至于过大，此值将会与requestWidth一同计算出最终的inSimpleSize
	 * ，从而使得加载到内存中的Bitmap不至于过大而导致OOM
	 * @return
	 */
	public static Intent createImageViewActivityIntent(Context thisActivity
			, int imageDataType, String imageDataSrc, int requestWidth, int requestHeight)
	{
		return createImageViewActivityIntent(thisActivity, imageDataType, imageDataSrc, ImageViewActivity.class
				, requestWidth, requestHeight, null, null, null, null, null, null);
	}
	/**
	 * 打开ImageViewActivity的Intent构造方法.
	 * 
	 * @param thisActivity
	 * @param imageDataType
	 * @param imageDataSrc
	 * @param requestWidth 此项将用于计算BitmapFactory.Opts的inSimpleSize值，目的是保
	 * 证加载到内存中的图片不至于过大，此值将会与requestHeight一同计算出最终的inSimpleSize
	 * ，从而使得加载到内存中的Bitmap不至于过大而导致OOM
	 * @param requestHeight 此项将用于计算BitmapFactory.Opts的inSimpleSize值，目的是保
	 * 证加载到内存中的图片不至于过大，此值将会与requestWidth一同计算出最终的inSimpleSize
	 * ，从而使得加载到内存中的Bitmap不至于过大而导致OOM
	 * @return
	 */
	public static Intent createImageViewActivityIntent(Context thisActivity
			, int imageDataType, String imageDataSrc, Class destActivityClazz, int requestWidth, int requestHeight
			, String exData1, String exData2, String exData3, String exData4, String exData5, String exData6)
	{
		Intent intent = new Intent(thisActivity, destActivityClazz);
		intent.putExtra("__imageDataType__", imageDataType);
		intent.putExtra("__imageData__", imageDataSrc);
		intent.putExtra("__requestWidth__", requestWidth);
		intent.putExtra("__requestHeight__", requestHeight);
		
		intent.putExtra("__exData1__", exData1);
		intent.putExtra("__exData2__", exData2);
		intent.putExtra("__exData3__", exData3);
		intent.putExtra("__exData4__", exData4);
		intent.putExtra("__exData5__", exData5);
		intent.putExtra("__exData6__", exData6);
		return intent;
	}
	/**
	 * 解析intent传过来给ImageViewActivity的数据.
	 * 
	 * @param intent
	 * @return
	 */
	public static ArrayList parseImageViewActivityIntent(Intent intent)
	{
		ArrayList datas = new ArrayList();
		datas.add(intent.getIntExtra("__imageDataType__", 0));
		datas.add(intent.getStringExtra("__imageData__"));
		datas.add(intent.getIntExtra("__requestWidth__", -1));
		datas.add(intent.getIntExtra("__requestHeight__", -1));
		
		datas.add(intent.getStringExtra("__exData1__"));
		datas.add(intent.getStringExtra("__exData2__"));
		datas.add(intent.getStringExtra("__exData3__"));
		datas.add(intent.getStringExtra("__exData4__"));
		datas.add(intent.getStringExtra("__exData5__"));
		datas.add(intent.getStringExtra("__exData6__"));
		return datas;
	}

    /**
     * 解析intent传过来给ShortVideoPlayerActivity的数据.
     *
     * @param intent
     * @return
     */
    public static ArrayList parseShortVideoPlayerActivityIntent(Intent intent)
    {
        ArrayList datas = new ArrayList();
        datas.add(intent.getIntExtra("__videoDataType__", 0));
        datas.add(intent.getStringExtra("__videoData__"));
        datas.add(intent.getStringExtra("__videoSaveDir__"));
//        datas.add(intent.getLongExtra("__videoTotalSize__", 0));
        return datas;
    }


}
