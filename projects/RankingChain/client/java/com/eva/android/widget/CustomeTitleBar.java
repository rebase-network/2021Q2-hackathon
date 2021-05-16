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

//import net.dmkx.mobi1.R;
//import com.eva.android.RHolder;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tang.R;

/**
 * <p>
 * 一个自定义的通用标题栏实列类.
 * 它常用于activity中用作自定义标题栏.<br><br>
 * 
 * 本自定义标题栏<u>不同于使用“requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);...“
 * 这样的自定义方式</u>（经js评估认为该种方式不利于灵活性，而且并不简洁——很繁琐，且跟本类
 * 比没有什么优势），<u>本类在activity中使用时，是把activity设置成没有默认标题的形式，然后在
 * contentView上加上自定义标题栏组件</u>，从而实现一个所谓的自定义标题栏，这种方式因整个布局由
 * 编程者完全定义，因而非常灵活——想怎么实现、实现成什么样都行，而且主流的像360通讯录等商业
 * 程序都是这种方式（看来这种方式是经过实践检验可行的且是较优的），值得一试.
 * </p>
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 * @see "widget_title_bar.xml"
 */
public class CustomeTitleBar extends LinearLayout
{
	/** 左返回按钮 */
	private Button leftBackButton = null;
	/** 左通用按钮 */
	private Button leftGeneralButton = null;
	/** 中间标题文本按钮 */
	private TextView titleView = null;
	/**
     * 本组件仅用于为标题的显示留白占位（一般在没有左边返回按钮
     时，显示本占位，否则不显示），用这个控件而不在代码中动态设置
     titleView的marginLeft的目的是因为这样更简单实用 */
	private View titleLeftGapView = null;

	/** 右通用按钮 */
	private Button rightGeneralButton = null;
	
	/** 整个标题栏的总体布局 */
	private FrameLayout mainLayout = null;
	/** 左按钮区布局 */
	private LinearLayout leftBtnLayout = null;
	/** 中间标题展示区布局 */
	private LinearLayout titleLayout = null;
	/** 右按钮区布局 */
	private LinearLayout rightBtnLayout = null;
	
	public CustomeTitleBar(Context context)
	{
		this(context, null);
	}
	public CustomeTitleBar(final Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initViews();
		initListeners();
	}
	
	/**
	 * 初始化UI组件.
	 * 默认 {@link #rightGeneralButton}是不可见的哦.
	 */
	protected void initViews()
	{
		if (isInEditMode()) { return;}
		
		LayoutInflater.from(this.getContext()).inflate(R.layout.widget_title_bar
				,this,true);
		
		leftBackButton = (Button)this.findViewById(R.id.widget_title_left_backBtn);
		leftGeneralButton = (Button)this.findViewById(R.id.widget_title_left_generalBtn);
		titleView = (TextView)this.findViewById(R.id.widget_title_textView);
        titleLeftGapView = this.findViewById(R.id.widget_title_leftGapView);
		rightGeneralButton = (Button)this.findViewById(R.id.widget_title_right_generalBtn);
		
		titleLayout = (LinearLayout)this.findViewById(R.id.widget_title_textLayout);
		leftBtnLayout = (LinearLayout)this.findViewById(R.id.widget_title_leftBtnLayout);
		rightBtnLayout = (LinearLayout)this.findViewById(R.id.widget_title_rightBtnLayout);
		mainLayout = (FrameLayout)this.findViewById(R.id.widget_title_bar);
		
		//默认右边的这个操作按钮是隐藏的，留作第3方调用时自行设置成Visible并使用之
//		leftBackButton.setVisibility(View.GONE);
		rightGeneralButton.setVisibility(View.GONE);
		leftGeneralButton.setVisibility(View.GONE);
	}
	
	protected void initListeners()
	{
		leftBackButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				fireBack();
			}
		});
	}
	
	/**
	 * <p>
	 * 点击返回按钮要调用的方法.<br>
	 * 
	 * 如果this.getContext()是Activity的子类则调用其finish方法实列返回（
	 * 相当于按了手机上的back键），否则本方法默认什么也不做.
	 * </p>
	 */
	protected void fireBack()
	{
		if(this.getContext() instanceof Activity)
			((Activity)this.getContext()).finish();
	}
	
	public Button getLeftBackButton()
	{
		return leftBackButton;
	}
	public TextView getTitleView()
	{
		return titleView;
	}
	public Button getRightGeneralButton()
	{
		return rightGeneralButton;
	}
	public Button getLeftGeneralButton()
	{
		return leftGeneralButton;
	}
	
	public void setText(String txt)
	{
		titleView.setText(txt);
	}
	
	public LinearLayout getLeftBtnLayout()
	{
		return leftBtnLayout;
	}
	public LinearLayout getRightBtnLayout()
	{
		return rightBtnLayout;
	}
	public LinearLayout getTitleLayout()
	{
		return titleLayout;
	}
	public FrameLayout getMainLayout()
	{
		return mainLayout;
	}

    /**
     * 设置左边返回按钮的可见性。
     *
     * @param visible true表示可见，否则GONE
     */
	public void setLeftBackButtonVisible(boolean visible)
    {
        this.leftBackButton.setVisibility(visible?View.VISIBLE:View.GONE);
        this.titleLeftGapView.setVisibility(visible?View.GONE:View.VISIBLE);
    }
	
//	/**
//	 * 设置标题下方的立体阴影横线的可见性.
//	 * 
//	 * @param visible true表示设置它可见，否则不可见（View.GONE）
//	 */
//	public void setBottomShadowLineVisible(boolean visible)
//	{
//		((View)this.findViewById(R.id.widget_title_bottomShadowLine_ll))
//			.setVisibility(visible?View.VISIBLE:View.GONE);
//	}
}
