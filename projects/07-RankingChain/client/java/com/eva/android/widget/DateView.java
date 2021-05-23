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
import android.util.Log;
import android.widget.EditText;

import com.eva.android.widget.WidgetUtils.ToastType;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 一个日期文本框实现类.<br><br>
 * 
 * 本类中关于日期文本的设置<b>请使用 setDate(..)系列方法</b>，<font color="#FF0000"><strike><b>
 * 禁止使用setText(..)及相似功能的父类方法置文本内容</b></strike></font>，因通用类中setText系列
 * 方法的封装缺陷使得无法完全实现自定义逻辑的覆盖重写，所以本类中只能另辟方法且禁止使用父类的
 * setText(..)系列方法。<font color="#007700">但getText(..)相关方法可正常使用.</font>
 * </p>
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 */
public class DateView extends EditText
{
	private final static String TAG = DateView.class.getSimpleName();
	/** 默认日期样式 */
	public final static String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	/** 日期样式 */
	private String datePattern = DEFAULT_DATE_PATTERN;
	
	/**
	 * @see super class
	 */
	public DateView(Context context)
	{
		this(context, DEFAULT_DATE_PATTERN);
	}
	public DateView(Context context, String datePattern) {
		super(context);
		this.datePattern = datePattern;
	}
	
	/**
	 * @see super class
	 */
	public DateView(Context context, AttributeSet attrs)
	{
		this(context,attrs,DEFAULT_DATE_PATTERN);
	}
	public DateView(Context context, AttributeSet attrs, String datePattern) {
		super(context, attrs);
		this.datePattern = datePattern;
	}
	
	/**
	 * @see super class
	 */
	public DateView(Context context, AttributeSet attrs, int defStyle)
	{
		this(context,attrs,defStyle,DEFAULT_DATE_PATTERN);
	}
	public DateView(Context context, AttributeSet attrs, int defStyle, String datePattern) {
		super(context, attrs, defStyle);
		this.datePattern = datePattern;
	}
	
	
	public String getDatePattern()
	{
		return datePattern;
	}
	public void setDatePattern(String datePattern)
	{
		this.datePattern = datePattern;
	}
	
	protected Date parseToDate(String dateStr)
	{
		String text = dateStr;
		Date d = null;
		try{
			d = new SimpleDateFormat(datePattern).parse(text);
		}
		catch (Exception e)
		{
			Log.e(TAG, "日期解析出错，text="+text+",datePattern="+datePattern);
			WidgetUtils.showToast(this.getContext(), "日期解析出错，text="+text+",datePattern="+datePattern, ToastType.ERROR);
		}
		return d;
	}

	/**
	 * 设置日期。
	 * 
	 * @param text
	 * @see #parseToDate(String)
	 * @see #setDate(Date)
	 */
	public void setDate(String text)
	{
		setDate(parseToDate(text));
	}
	/**
	 * <p>
	 * 设置日期.<br>
	 * 设置完成后将试图通知观察者，并根据观察者的处理结果再决定是否撤消本次日期的更改.
	 * </p>
	 * 
	 * @param d
	 */
	public void setDate(Date d)
	{
		//设置新日期
		java.text.DateFormat format = new SimpleDateFormat(datePattern);
		this.setText(format.format(d));
	}
	public Date getDate()
	{
		return parseToDate(this.getText().toString());
	}
}
