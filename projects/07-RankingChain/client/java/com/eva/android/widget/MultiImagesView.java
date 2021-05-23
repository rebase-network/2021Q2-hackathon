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
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tang.R;

/**
 * <p>
 * 多图片查看组件实现类.可以左右滑动切换图片、并提供按钮完成相似功能等.
 * 本类对象的布局文件是widget_multi_images_view.xml<br><br>
 * 本类是在ViewFlipperEx类的基础上进行的进一步封装和完全善.<br>
 * 
 * <b>特别说明：</b>所有要显示的图片组件必须通过调用方法 {@link #getViewFlipper()}以便加入到
 * ViewFlipperEx中于以显示，再无其它途径.形如：
 * <code>
 * <pre>
 * miv.getViewFlipper().addView(new AsynLoadableImageView(CpxxQueryFormActivity.this
 *		,url), new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
 *</pre>
 *</code>
 * </p>
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 * @see ViewFlipperEx
 */
public class MultiImagesView extends LinearLayout
{
	/** ViewFlipper组件实现对象 */
	protected ViewFlipperEx viewFlipperEx = null;
	/** 向前按钮 */
	protected Button btnPre = null;
	/** 向后按钮 */
	protected Button btnNext = null;
	/** 用于显示图片数量的文本组件 */
	protected TextView viewImagesCountInfo = null;
	
	public MultiImagesView(Context context)
	{
		this(context, null);
	}
	public MultiImagesView(final Context context, AttributeSet attrs)
	{
		super(context, attrs);
		
		LayoutInflater.from(context).inflate(R.layout.widget_multi_images_view,this,true);
		viewFlipperEx = (ViewFlipperEx)this.findViewById(R.id.widget_multi_images_view_vf);
		btnPre = (Button)this.findViewById(R.id.widget_multi_images_view_preBtn);
		btnNext = (Button)this.findViewById(R.id.widget_multi_images_view_nextBtn);
		viewImagesCountInfo = (TextView)this.findViewById(R.id.widget_multi_images_view_pageCountView);
		
		addListeners();
	}
	
	protected void addListeners()
	{
		btnPre.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				viewFlipperEx.goToPre();
			}
		});
		btnNext.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				viewFlipperEx.goToNext();
			}
		});
	}
	
	/**
	 * 返回本组件里的ViewFlipper对象.
	 * 加入本组件里的所有图片组件都必须通此方法加入到ViewFlipperEx中.
	 * 
	 * @return
	 */
	public ViewFlipperEx getViewFlipper()
	{
		return viewFlipperEx;
	}
	
	/**
	 * 设置图片数量信息.
	 * 
	 * @param info
	 * @return
	 */
	public MultiImagesView setImagesCountInfo(String info)
	{
		viewImagesCountInfo.setText(info);
		return this;
	}
}
