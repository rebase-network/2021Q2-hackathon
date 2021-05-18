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
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eva.android.BitmapHelper;
import com.eva.android.DataLoadableActivity;
import com.eva.android.HttpFileDownloadHelper;
import com.eva.android.IntentFactory;
import com.eva.android.ToolKits;
import com.eva.android.UriToFileHelper;
import com.eva.android.widget.WidgetUtils.ToastType;
import com.eva.framework.dto.DataFromServer;
import com.tang.R;

import java.io.File;
import java.util.ArrayList;

//import com.eva.android.RHolder;

/**
 * 图片查看可重用Activity实现类.<br>
 * 本类解决了大图片的OOM问题、实现了缩放、旋转以及自动下载和缓存功能.
 * <p>
 * 特别注意，如果单纯用于图片查看时，当{@link #mImageDataType}={@link ImageDataType#URL}
 * 时，保留字段{@link #mExData1}保存的是本地缓存path，继承和得重用时注意不要误覆盖或误用哦。
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 */
public class ImageViewActivity extends DataLoadableActivity
{
	private final static String TAG = ImageViewActivity.class.getSimpleName();
	
	protected int mImageDataType = ImageDataType.FILE_PATH;
	protected String mImageDataSrc = null; 
	// 额外保留字段
	protected String mExData1, mExData2, mExData3, mExData4, mExData5, mExData6;
	
	// 图片显示相关组件
	protected ViewGroup mLayoutImageView = null;
	protected ImageView mViewImage = null;
	/** 图片旋转按钮：顺时针旋转90度 */
	protected Button mBtnRotateNi = null;
	/** 图片旋转按钮：逆时针旋转90度 */
	protected Button mBtnRotateShun = null;

	/** 保存图片到相册 */
	protected Button mBtnSavePicToGalery = null;
	
	// 功能按钮相关组件
	protected Button mBtnFunction1 = null;
	protected ViewGroup mLayoutFunctionBar = null;
	
	/** 当没有图片或图片载入时的ui显示包装类 */
	protected NoImageWrapper mNoImageWrapper = null;
	
	protected Bitmap mBmOriginalForView = null;
	
	// 加载到本界面中的Bitmap的最终尺寸极限以requestWidth*requestHeight为准，比如：原
	// 图是640*640的大图，但用到的地方只需要200*200的图，
	// 那么此值设为200*200为佳，因这将使得返回的Bitmap对象占用的内存为200*200而非640*640
	/**
	 * 此项将用于计算BitmapFactory.Opts的inSimpleSize值，目的是保
	 * 证加载到内存中的图片不至于过大，此值将会与requestHeight一同计算出最终的inSimpleSize
	 * ，从而使得加载到内存中的Bitmap不至于过大而导致OOM. 默认是1000.
	 */
	private int mRequestWidth = 648;//720 * 0.9 = 648
	/**
	 * 此项将用于计算BitmapFactory.Opts的inSimpleSize值，目的是保
	 * 证加载到内存中的图片不至于过大，此值将会与requestWidth一同计算出最终的inSimpleSize
	 * ，从而使得加载到内存中的Bitmap不至于过大而导致OOM. 默认是1000.
	 */
	private int mRequestHeight = 864;//960 * 0.9 = 864
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void initDataFromIntent()
	{
		//解析从intent中传过来的数据
		ArrayList intentDatas = IntentFactory.parseImageViewActivityIntent(getIntent());
		mImageDataType = (Integer)intentDatas.get(0);
		mImageDataSrc = (String)intentDatas.get(1);
		
		// 当且仅当传进来的requestWidth和requestHeight都>0时才表示使用自定义值，否则保持本类中的默认值
		int _requestWidth = (Integer)intentDatas.get(2);
		int _reqHeight = (Integer)intentDatas.get(3);
		
		mExData1 = (String)intentDatas.get(4);
		mExData2 = (String)intentDatas.get(5);
		mExData3 = (String)intentDatas.get(6);
		mExData4 = (String)intentDatas.get(7);
		mExData5 = (String)intentDatas.get(8);
		mExData6 = (String)intentDatas.get(9);
		
		if(_requestWidth > 0 && _reqHeight > 0)
		{
			mRequestWidth = _requestWidth;
			mRequestHeight = _reqHeight;
		}
	}
	
	@Override
	protected void initViews(Bundle savedInstanceState)
	{
		customeTitleBarResId = R.id.common_image_view_layout_titleBar;
		setContentView(R.layout.common_image_view_layout);
		
		mLayoutImageView = (ViewGroup)findViewById(R.id.common_image_view_layout_viewImageFL);
		mViewImage = (ImageView) findViewById(R.id.common_image_view_viewImage);
		mBtnRotateNi = (Button) findViewById(R.id.common_image_view_rotate_btnNi);
		mBtnRotateShun = (Button) findViewById(R.id.common_image_view_rotate_btnShun);

        mBtnSavePicToGalery = (Button) findViewById(R.id.common_image_view_saveToGaleryBtn);
		
		mBtnFunction1 = (Button) findViewById(R.id.common_image_view_layout_btnFunction1);
		mLayoutFunctionBar = (ViewGroup) findViewById(R.id.common_image_view_layout_function_bar_ll);
//		// 默认操作条是不可见滴
//		layoutOpr.setVisibility(View.GONE);
		
		mNoImageWrapper = new NoImageWrapper();
		
		setTitle($$(R.string.chat_sendpic_image_view_title));
	}
	
	public ViewGroup getFunctionBarLayout()
	{
		return mLayoutFunctionBar;
	}
	public Button getFunctionButton1()
	{
		return mBtnFunction1;
	}
	
	@Override
	protected void initListeners() 
	{
		super.initListeners();
		
		mBtnFunction1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				fireOpr();
			}
		});
		
		mBtnRotateNi.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				if(mBmOriginalForView != null)
				{
					// 顺时针旋转90度
					rotateBitmapOriginalForView(-90);
				}
			}
		});
		mBtnRotateShun.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				// 逆时针旋转90度
				rotateBitmapOriginalForView(90);
			}
		});

        mBtnSavePicToGalery.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mBmOriginalForView != null && ToolKits.saveBmp2Gallery(ImageViewActivity.this, mBmOriginalForView))
                {
                    WidgetUtils.showToastLong(ImageViewActivity.this, "图片已成功保存到系统相册！", ToastType.OK);
                }
                else
                {
                    WidgetUtils.showToastLong(ImageViewActivity.this, "图片保存失败！", ToastType.WARN);
                }
            }
        });
	}
	
	private boolean rotateBitmapOriginalForView(int degree)
	{
		if(mBmOriginalForView != null)
		{
			try
			{
				Matrix matrix = new Matrix();
				matrix.reset();
				matrix.postRotate(degree);

				// 
				Bitmap afterRotate = Bitmap.createBitmap(mBmOriginalForView, 0, 0, mBmOriginalForView.getWidth()
						, mBmOriginalForView.getHeight(), matrix, true);
				if(afterRotate != null)
				{
					// 先回收老的内存哦
					recycle();
					// 再保存新的
					mBmOriginalForView = afterRotate;
					// 刷新ui显示
					mViewImage.setImageBitmap(mBmOriginalForView);
				}
				
				return true;
			}
			// 当手机内存不足时，捕获此异常，提升程序的健壮性
			catch (OutOfMemoryError e)
			{
				WidgetUtils.showToast(this
						, $$(R.string.chat_sendpic_image_view_oom_for_rotate)
						, ToastType.WARN);
				Log.e(TAG, "旋转图片时时内存溢出了：", e);
			}
		}

		return false;
	}
	
	@Override
	protected DataFromServer queryData(String... arg0)
	{
		return null;
	}

    /**
     * 本方法将从指定文件路径加载bitmap以便显示。
     * 本方法将自动被调用，无需用户动手。
     *
     * @param arg0
     */
	@Override
	protected void refreshToView(Object arg0)
	{
		if(mImageDataSrc == null)
			shitHintForException($$(R.string.chat_sendpic_image_view_file_path_not_valid));
		
		try
		{
			switch(mImageDataType)
			{
				case ImageDataType.FILE_PATH:
				{
					loadImageFromFile(mImageDataSrc);
					break;
				}
				case ImageDataType.URI:
				{
					//
					Uri uri = Uri.parse(mImageDataSrc);
					File filePath = UriToFileHelper.uri2File(this, uri);
					if(filePath != null)
						loadImageFromFile(filePath.getAbsolutePath());
					else
					{
						Log.e(TAG, "图片加载失败：imageDataSrc="+mImageDataSrc
								+", 解析出的uri="+uri+", 最终解析的filePath="+filePath);
						shitHintForException($$(R.string.chat_sendpic_image_view_image_not_valid));
					}
					break;
				}
				case ImageDataType.URL:
				{
					new HttpFileDownloadHelper.DownloadAsyncRoot(this
							, mImageDataSrc
							, mExData1)
					{
						@Override
					    protected void onPreExecute() 
					    {
							mNoImageWrapper
								.setVisible(true, true)
								.setIcon(R.drawable.common_image_view_image_unload_ico_2019)
								.setText($$(R.string.chat_sendpic_image_view_imageloading2));
					    }
						@Override
						protected void onProgressUpdate(Integer... progress) 
						{
							mNoImageWrapper.setProgress(progress[0]);  
					    }
						
						@Override
						protected void onPostExecute_onSucess(String fileSavedPath)
						{
							try
							{
								loadImageFromFile(fileSavedPath);
							}
							catch (Exception e)
							{
								Log.e(TAG, "从网络加载图片时失败：imageDataSrc="+mImageDataSrc
										+", exData1="+mExData1+"：", e);
								shitHintForException($$(R.string.chat_sendpic_image_view_image_not_valid));
							}
						}
						
						@Override
						protected void onPostExecute_onException(Exception exception)
						{
							shitHintForException($$(R.string.chat_sendpic_image_view_image_not_valid));
						}
					}.execute();
					break;
				}
			}
		}
		catch(OutOfMemoryError e2)
		{
			Log.e(TAG, e2.getMessage(), e2);
			mViewImage.setImageDrawable(null);
			shitHintForException($$(R.string.chat_sendpic_image_view_imagebig_for_memery));
		}
		catch (Exception e)
		{
			Log.e(TAG, e.getMessage(), e);
			mViewImage.setImageDrawable(null);
			shitHintForException($$(R.string.chat_sendpic_image_view_imagedata_not_valid));
		}
	}
	
	/**
	 * 从本地文件路径中加载图片.
	 * 
	 * @param imageFilePath
	 * @throws Exception
	 */
	private void loadImageFromFile(String imageFilePath) throws Exception
	{
		// 在尝试覆盖bitmap对象前先努力把老的内存给回收了，防止内存泄漏
		recycle();
		mBmOriginalForView = BitmapHelper.loadLocalBitmap(imageFilePath
				// 调整inSimpleSize值，确保在用户载入巨大尺寸时不致于OOM!
				, BitmapHelper.computeSampleSize2(imageFilePath, mRequestWidth, mRequestHeight));
		// 必须要作非Null判断哦
		if(mBmOriginalForView != null)
		{
			//
			mViewImage.setImageBitmap(mBmOriginalForView);

			//
			mLayoutImageView.setVisibility(View.VISIBLE);
			mNoImageWrapper.setVisible(false);
		}
		else
		{
			shitHintForException($$(R.string.chat_sendpic_image_view_image_not_valid));
		}
	}
	
	/**
	 * bmOriginalForView的内存给回收了，防止内存泄漏.
	 */
	private void recycle()
	{
		if(mBmOriginalForView != null && !mBmOriginalForView.isRecycled())
		{
			mBmOriginalForView.recycle();
			mBmOriginalForView = null;
		}
	}
	
	protected void shitHintForException(String msg)
	{
		mNoImageWrapper
			.setVisible(true, false)
			.setIcon(R.drawable.common_image_view_image_loadfaild_ico_2019)
			.setText(msg);
	}
	
	/**
	 * 点击操作按钮时要调用的方法.
	 * <p>
	 * 本方法默认什么也不做.
	 */
	protected void fireOpr()
	{
		// default do nothing
	}

	/**
	 * 当未成功加载图片时显示的UI.
	 */
	private class NoImageWrapper
	{
		private ViewGroup layoutOfNoImage = null;
		private ImageView viewIcon = null;
		private ProgressBar progressForDownload = null;
		private TextView viewHint = null;
		
		public NoImageWrapper()
		{
			initViews();
		}
		
		private void initViews()
		{
			layoutOfNoImage = (ViewGroup)ImageViewActivity.this.findViewById(
					R.id.common_image_view_layout_noImageLL);
			viewIcon = (ImageView)ImageViewActivity.this.findViewById(
					R.id.common_image_view_layout_noImage_viewHintIco);
			
			progressForDownload = (ProgressBar)ImageViewActivity.this.findViewById(
					R.id.common_image_view_layout_noImage_progressBar);
			viewHint = (TextView)ImageViewActivity.this.findViewById(
					R.id.common_image_view_layout_noImage_viewHintText);
		}
		
		public NoImageWrapper setVisible(boolean visible)
		{
			if(visible)
			{
				ImageViewActivity.this.mLayoutImageView.setVisibility(View.GONE);
				layoutOfNoImage.setVisibility(View.VISIBLE);
			}
			else
				layoutOfNoImage.setVisibility(View.GONE);
			return this;
		}
		public NoImageWrapper setVisible(boolean visible, boolean progressVisible)
		{
			setVisible(visible);
			
			if(progressVisible)
				progressForDownload.setVisibility(View.VISIBLE);
			else
				progressForDownload.setVisibility(View.GONE);
			return this;
		}
		
		public NoImageWrapper setProgress(int progressOf100)
		{
			progressForDownload.setProgress(progressOf100);
			return this;
		}
		
		public NoImageWrapper setText(String text)
		{
			viewHint.setText(text);
			return this;
		}
		
		public NoImageWrapper setIcon(int resId)
		{
			viewIcon.setImageResource(resId);
			return this;
		}
	}
	
	public interface ImageDataType
	{
		/** 图片文件来自本地文件 */
		int FILE_PATH = 0;
		/** 图片文件来自URI */
		int URI = 1;
		/** 图片文件来自Http网络 */
		int URL = 2;
	}
}
