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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * 一个列表适配置类的扩展实现.
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 */
public abstract class AListAdapter2<T> extends BaseAdapter
{
	/** 
	 * ListView对象所在activity的布局inflater，本类中使用该inflater找到
	 * 列表item的UI资源 */
	protected LayoutInflater layoutInflater;
	/** 
	 * 列表对应的原始数据结构，每个单元（MAP对象）表示的数据相当于列表中的一行 ，每一行数据
	 * 的各列使用key-value方式存放，key即是本ListAdapter关联ListView对象中设定的列信息中的fieldName */
	protected ArrayList<T> listData = null;
	
	/** 相关联的ListView的item对应的UI资源xml文件id */
	protected int itemResId;
	
	protected Context context = null;
	
	/**
	 * 构造方法.
	 * 
	 * @param context
	 * @param itemResId 相关联的ListView的item对应的UI资源xml文件id
	 */
	public AListAdapter2(Context context,int itemResId)
	{
		this.context = context;
		this.layoutInflater = LayoutInflater.from(context);
		this.itemResId = itemResId;
		this.listData = createListData();
	}
	
	/**
	 * 列表adapter数据结构创建方法.
	 * 子类可覆盖此方法用以实现自已的数据结构.
	 * 
	 * @return
	 */
	protected ArrayList<T> createListData()
	{
		return new ArrayList<T>();
	}
	
    /**
     * 替换列表中指定行的所有数据并通知UI刷新界面.
     * 
     * @param itemIndex 行索引值
     * @param itemData 新列表行数据
     */
	public void setItem(int itemIndex, T itemData)
	{
		//原行数据集引用
		T oldItemData = listData.get(itemIndex);
		//将每列值用新值替换
		listData.set(itemIndex, oldItemData);

		//通知UI刷新界面
		this.notifyDataSetChanged();// TODO 改造本方法，使得刷新Ui方法的调用成为可选项（在多行数据同时更新
									//场景里，为提高性能，可能希望在所有数据更新完成后再1次性刷新Ui以便提高性能）
	}
	
	/**
	 * 删除列表中的指定行数据并通知UI刷新界面.
	 * 
	 * @param itemIndex 要删除的行索引值
	 */
	public void removeItem(int itemIndex)
	{
		listData.remove(itemIndex);
		//通知UI刷新界面
		this.notifyDataSetChanged();// TODO 改造本方法，使得刷新Ui方法的调用成为可选项（在多行数据同时更新
									//场景里，为提高性能，可能希望在所有数据更新完成后再1次性刷新Ui以便提高性能）
	}
	
	/**
	 * 给列表增加一行数据并通知UI刷新界面.
	 * 
	 * @param itemData 要增加的新行数据
	 */
	public void addItem(T itemData)
	{
		listData.add(itemData);
		//通知UI刷新界面
		this.notifyDataSetChanged();// TODO 改造本方法，使得刷新Ui方法的调用成为可选项（在多行数据同时更新
									//场景里，为提高性能，可能希望在所有数据更新完成后再1次性刷新Ui以便提高性能）
	}

	/**
	 * 在指定的行索引处给列表增加一行数据并通知UI刷新界面.
	 * 
	 * @param itemIndex 新行所在的行索引值
	 * @param itemData 要增加的新行数据
	 */
	public void addItem(int itemIndex, T itemData)
	{
		listData.add(itemIndex,itemData);
		//通知UI刷新界面
		this.notifyDataSetChanged();// TODO 改造本方法，使得刷新Ui方法的调用成为可选项（在多行数据同时更新
									//场景里，为提高性能，可能希望在所有数据更新完成后再1次性刷新Ui以便提高性能）
	}

	/**
	 * 返回数据行数.
	 */
	public int getCount()
	{
		return listData.size();
	}

	/**
	 * <p>
	 * 返回指定行数据引用.
	 * <b>请特别注意：</b>返回的是该行数据的浅拷贝.
	 * </p>
	 * 
	 * @exception IllegalArgumentException if itemIndex<0
	 */
	public T getItem(int itemIndex)
	{
		if(itemIndex >= 0)
			return listData.get(itemIndex);
		else 
			throw new IllegalArgumentException("无效的参数,rowIndex="+itemIndex);
	}
	
	@Override public long getItemId(int position)
	{
		return position;
	}
	
	/**
	 * 获取ListView对象所在activity的布局inflater对象引用.本类中使
	 * 用该inflater找到列表item的UI资源 
	 * 
	 * @return
	 */
	public LayoutInflater getLayoutInflater()
	{
		return layoutInflater;
	}
	
	/**
	 * 获得相关联的ListView的item对应的UI资源xml文件id.
	 * 
	 * @return
	 */
	public int getItemResId()
	{
		return itemResId;
	}
	/**
	 * 设置相关联的ListView的item对应的UI资源xml文件id.
	 * 
	 * @param ItemResId
	 */
	public void setItemResId(int ItemResId)
	{
		this.itemResId = ItemResId;
	}

	/**
	 * 获得列表对应的原始数据结构，每个单元（MAP对象）表示的数据相当于列表中的一行 ，
	 * 每一行数据 的各列使用key-value方式存放，key即是本ListAdapter关联ListView对象中设定的列信息中的fieldName.
	 * 
	 * @return
	 */
	public ArrayList<T> getListData()
	{
		return listData;
	}
	
	/**
	 * 设置列表对应的原始数据结构，每个单元（MAP对象）表示的数据相当于列表中的一行 ，
	 * 每一行数据 的各列使用key-value方式存放，key即是本ListAdapter关联ListView对象中设定的列信息中的fieldName.
	 * 
	 * @param listData
	 */
	public void setListData(ArrayList<T> listData)
	{
		this.listData = listData;
		//通知UI刷新界面
		this.notifyDataSetChanged();// TODO 改造本方法，使得刷新Ui方法的调用成为可选项（在多行数据同时更新
									//场景里，为提高性能，可能希望在所有数据更新完成后再1次性刷新Ui以便提高性能）
	}

	/**
	 * 清空列表数据并即时刷新UI.
	 */
	public void clear()
	{
		this.listData.clear();
		this.notifyDataSetChanged();// TODO 改造本方法，使得刷新Ui方法的调用成为可选项（在多行数据同时更新
									//场景里，为提高性能，可能希望在所有数据更新完成后再1次性刷新Ui以便提高性能）
	}
	
	public Context getContext()
	{
		return context;
	}

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);

	/**
	 * 检查索引值是否合法（有无超过数据合法索引）。
	 *
	 * @param index 数据所在数组的索引位置
	 * @return
	 */
	public boolean checkIndexValid(int index)
	{
		return (index >=0 && index <= (this.listData.size() - 1));
	}
}
