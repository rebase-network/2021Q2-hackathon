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
package com.eva.android.widget.alert;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;

import com.tang.R;

//import com.eva.android.RHolder;

/**
 * [自定义对话框基类]<BR>
 * @author 000979
 * @version [Android MobilyClient_Handset]
 */
public abstract class BaseDialog extends Dialog
{
	protected Context mContext;
	private FrameLayout coverLayerParent;
	private View coverLayer;
	
	public BaseDialog(Context context)
	{
		super(context, R.style.m00_dialog);
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(getDialogLayout());
		initViews();
		bindListener();
	}
	
	@Override
	public void show()
	{
		super.show();
		initCoverLayer();
	}

	@Override
	public void cancel()
	{
		super.cancel();
		if(coverLayer != null && coverLayerParent != null)
		{
			coverLayerParent.removeView(coverLayer);
		}
	}

	/**
	 * [设置对话框位置]<BR>
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y)
	{
		Window win = getWindow();
		LayoutParams params = new LayoutParams();
		params.x = x;
		params.y = y;
		params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
		//params.dimAmount=0.0f;
		win.setAttributes(params);
		setCanceledOnTouchOutside(true);
	}

	/**
	 * [初始化对话框蓝色背景]<BR>
	 */
	private void initCoverLayer()
	{
		if(mContext instanceof Activity)
		{
			coverLayer = LayoutInflater.from(mContext).inflate(R.layout.m00_coverlayer, null);
			View root = ((Activity)mContext).getWindow().getDecorView();
			coverLayerParent = (FrameLayout)root;
			
			coverLayerParent.addView(coverLayer, 
					new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		}
	}
	
	/**
	 * 初始化控件    
	 */
	public abstract void initViews();

	/**
	 * 绑定控件事件    
	 */
	public abstract void bindListener();

	/**
	 * 返回dialog的layout id
	 * @return 布局文件id 
	 */
	public abstract int getDialogLayout();

}
