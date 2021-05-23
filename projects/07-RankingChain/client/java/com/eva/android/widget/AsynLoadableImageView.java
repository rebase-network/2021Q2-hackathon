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

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eva.android.BitmapHelper;
import com.tang.R;

/**
 * <p>
 * 支持异常载入的图片查看组件.本类对象的布局文件是widget_asynloadable_imageview.xml<br><br>
 * 
 * TODO 在此基础上，本类可以实现对图片的放大、缩小等进一步操作的功能，从而方便查看. 2012-06-02 by js.<br><br>
 * 
 * 目前仅支持网络图片的异常载入，本地文件的异步载入更简单有时间再改吧.
 * </p>
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 */
public class AsynLoadableImageView extends FrameLayout
{
	/** 用于显示图片的ImageView组件 */
	private ImageView contentImageView = null;
	/** 用于载入图片时的进度显示组件布局 */
	private LinearLayout progressBarLayout = null;
	/** 载入图片时的进度条组件 */
	private ProgressBar progressBarOfLayout = null;
	/** 载入图片时的进度文本显示信息组件 */
	private TextView viewMsgOfLayout = null;
	
	/** 需要异步载入的图片所在的http URL地址 */
	private String imageResourceURL = null;
	
	public AsynLoadableImageView(Context context, String url)
	{
		this(context, null, url);
	}
	public AsynLoadableImageView(Context context, AttributeSet attrs, String url)
	{
		this(context, attrs, 0, url);
	}
	public AsynLoadableImageView(Context context, AttributeSet attrs, int defStyle, String url)
	{
		super(context, attrs, defStyle);
		this.imageResourceURL = url;
		initGUI();
	}
	
	/**
	 * GUI核心初始化方法.
	 */
	protected void initGUI()
	{
		LayoutInflater.from(this.getContext()).inflate(R.layout.widget_asynloadable_imageview,this,true);
		
		contentImageView = (ImageView)this.findViewById(R.id.widget_asynloadable_imageview_content);
		contentImageView.setVisibility(View.INVISIBLE);
		progressBarLayout = (LinearLayout)this.findViewById(R.id.widget_asynloadable_imageview_pblayout);
		progressBarOfLayout = (ProgressBar)this.findViewById(R.id.widget_asynloadable_imageview_pb);
		viewMsgOfLayout = (TextView)this.findViewById(R.id.widget_asynloadable_imageview_msg);
		
		if(imageResourceURL == null)
		{
			progressBarOfLayout.setVisibility(View.GONE);
			viewMsgOfLayout.setText("图片地址不正确url="+imageResourceURL+"，图片载入失败！");
		}
		else
			new ImageAsynTask().execute();
	}
	
	/**
	 * 异步载入实现类.
	 */
	private class ImageAsynTask extends AsyncTask<Void, Void, Drawable>
	{
		/**
		 * 传进来的参数就是图片的URL.
		 */
		@Override
		protected Drawable doInBackground(Void... params)
		{
			try
			{
				return BitmapHelper.loadHttpDrawable(imageResourceURL);
			}
			catch (Exception e)
			{
				return null;
			}
		}

		@Override
		protected void onPostExecute(Drawable result)
		{
			super.onPostExecute(result);
			
			if(result == null)
			{
				progressBarOfLayout.setVisibility(View.GONE);
				viewMsgOfLayout.setText("图片"+imageResourceURL+"载入失败！");
			}
			//图片载入完成：设置进度组件不可见、设置图片查看组件可见
			else
			{
				//设置图片可见
				contentImageView.setVisibility(View.VISIBLE);
				contentImageView.setImageDrawable(result);
				
				//进度组件不可见
				progressBarLayout.setVisibility(View.GONE);
			}
		}
	}
}
