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

//import com.eva.android.RHolder;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.tang.R;

import java.lang.reflect.Field;

public abstract class ChoiceItemPopWindow extends PopupWindow
{
	private int mResIdForContentView = -1;
	private int mResIdForPopupLayout = -1;
	
	protected OnClickListener mItemsOnClick = null;
	private View mMenuView;
	
	public ChoiceItemPopWindow(Activity context, OnClickListener mItemsOnClick
			, int mResIdForContentView, final int mResIdForPopupLayout)
	{
		super(context);

		// @since5.1：用于解决Android 5.0及以上版本中，popupWindow不能覆盖状态栏的问题（主要是留海屏下太难看）
		fitPopupWindowOverStatusBar(this);
		
		this.mItemsOnClick = mItemsOnClick;
		this.mResIdForContentView = mResIdForContentView;
		this.mResIdForPopupLayout = mResIdForPopupLayout;
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(mResIdForContentView, null);
		
		//
		initContentViewComponents(mMenuView);
		
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.FILL_PARENT);//setWidth(context.getWindowManager().getDefaultDisplay().getWidth());
		// 设置SelectPicPopupWindow弹出窗体的高
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			// @since6.0：用于解决Android 5.0及以上版本中，popupWindow不能覆盖状态栏的问题（主要是留海屏下太难看）
			setHeight(context.getWindowManager().getDefaultDisplay().getHeight()+getStatusBarHeight(context));// 【1/3】
		}
		else {
			this.setHeight(LayoutParams.WRAP_CONTENT);
		}
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.widget_choice_item_popup_AnimBottom);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(createCancelOnTouchOutOfMenuListener());

	}
	
	protected abstract void initContentViewComponents(View mMenuView);
	
	protected OnTouchListener createCancelOnTouchOutOfMenuListener()
	{
		return new OnTouchListener()
		{
			public boolean onTouch(View v, MotionEvent event)
			{
				int height = mMenuView.findViewById(mResIdForPopupLayout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP)
				{
					if (y < height)
					{
						dismiss();
					}
				}
				return true;
			}
		};
	}
	
	protected OnClickListener createCancelClickListener()
	{
		return new OnClickListener()
		{
			public void onClick(View v)
			{
				// 销毁弹出框
				dismiss();
			}
		};
	}


	//------------------ 以下两个方法，用于解决Android 5.0及以上版本中，popupWindow不能覆盖状态栏的问题（主要是留海屏下太难看） START
	// 参考资料：https://blog.csdn.net/CCstar1/article/details/93606034、https://blog.csdn.net/beibaokongming/article/details/88567517
	// 解决此问题需3个配合，否则达不到效果：1）设置setClippingEnabled(false)、2）设置"mLayoutInScreen"参数、3）设置弹出窗的高度要加上状态栏的高度。
	public static void fitPopupWindowOverStatusBar(PopupWindow mPopupWindow)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

			mPopupWindow.setClippingEnabled(false);// 【2/3】

			try {
				Field mLayoutInScreen = PopupWindow.class.getDeclaredField("mLayoutInScreen");// 【3/3】
				mLayoutInScreen.setAccessible(true);
				mLayoutInScreen.set(mPopupWindow, true);
			} catch (NoSuchFieldException e) {
				Log.w(ChoiceItemPopWindow.class.getSimpleName(), e);
			} catch (IllegalAccessException e) {
				Log.w(ChoiceItemPopWindow.class.getSimpleName(), e);
			}
		}
	}

	/**
	 * 获取状态栏高度
	 *
	 * @param context context
	 * @return 状态栏高度
	 */
	public static int getStatusBarHeight(Context context)
	{
		// 获得状态栏高度
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		return context.getResources().getDimensionPixelSize(resourceId);
	}
	//------------------ 以下两个方法，用于解决Android 5.0及以上版本中，popupWindow不能覆盖状态栏的问题 END

}
