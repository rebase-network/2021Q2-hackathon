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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.eva.epc.common.dto.IdName;

/**
 * </p>
 * 一个支持DataRender数据项封装的ArrayAdapter子类实现.<br><br>
 *
 * DataRender主要用于复杂显示数据——后台模型数据(id)与前台view数据(name)分开，关键业务值取的是
 * id而显示时是用的仅用于显示的name值，这样便于把显示数据与后台数据分开.
 * </p>
 *
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 * @see DataRender
 */
public class RenderedSpinnerAdapter extends ArrayAdapter
{
	/** 本adapter所对应的Spinner对象引用 */
	private Spinner spinner;
	/** IdName 封装对象 */
	private DataRender render;

	/**
	 * 实例化一个Spinner对象专用的Adapter对象.
	 *
	 * @param context
	 * @param textViewResourceId
	 * @param render 用于Spinner条项显示时的数据项集合封装对象
	 * @param spinner
	 * @see ArrayAdapter#ArrayAdapter(Context , int , Object[])
	 */
	public RenderedSpinnerAdapter(Context context, int textViewResourceId,
                                  DataRender render, Spinner spinner)
	{
		super(context, textViewResourceId, render.getIdAndNames2());
		this.spinner = spinner;
		this.render = render;
	}

	/**
	 * 返回选中项所对应的IdName对象.
	 *
	 * @return
	 * @see #getItem(int)
	 * @see Spinner#getSelectedItemPosition()
	 */
	public IdName getSelectedItem()
	{
		return (IdName)getItem(spinner.getSelectedItemPosition());
	}

	/**
	 * 返回返中项所对应的IdName对象的id值.
	 *
	 * @return
	 * @see #getSelectedItem()
	 * @see IdName#getId()
	 */
	public Object getSelectedKey()
	{
		return getSelectedItem().getId();
	}

	/**
	 * 设置选中项.
	 *
	 * @param key 要选中项对应IdName对象的id值
	 */
	public void setSelectedItem(Object key)
	{
		spinner.setSelection(render.getIds2().indexOf(key));
	}
}
