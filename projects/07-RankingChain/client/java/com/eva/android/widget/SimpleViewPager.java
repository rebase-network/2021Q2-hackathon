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

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tang.R;

import java.util.ArrayList;

//import com.eva.android.RHolder;

/**
 * 一个VierPager类的简单实现，方便使用.
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 */
public class SimpleViewPager extends LinearLayout
{
	/** 本面板所在的context所对应的Inflater引用 */
	protected LayoutInflater contextInflater;
	/** 组件的真正view实现 */
	protected View mLayoutInflater;
	
	/** ViewPager实例引用 */
	protected ViewPager mViewPager;
	/** 页面指示器组件将要被放置的父layout实例 */
	private ViewGroup mViewGroupBottomIndicator = null;

	/** 当前被选中的页面所处数据中的索引 */
	private int mCurrentSelectedIndex = 0;
	
	/** 将要被显示的所有view集合（必须与  {@link #mIndicators}中的元严格一对一对应起来 ） */
	private ArrayList<View> mViews = new ArrayList<View>();
	/** 将要被显示的页面对应的指示器组件集合（必须与  {@link #mViews}中的元严格一对一对应起来 ） */
	private ArrayList<PaggerIndicator> mIndicators = new ArrayList<PaggerIndicator>();

	/**
	 * 构造方法(默认用空数据显示).
	 * 
	 * @param context
	 * @param main_layout_res_id
	 * @param indicator_parent_res_id
	 * @param pager_res_id
	 * @see #SimpleViewPager(Activity, int, int, int, ArrayList, ArrayList)
	 */
	public SimpleViewPager(Activity context
			, int main_layout_res_id, int indicator_parent_res_id, int pager_res_id)
	{
		this(context, null
				, main_layout_res_id, indicator_parent_res_id, pager_res_id
				, new ArrayList<View>(), new ArrayList<PaggerIndicator>());
	}
	
	/**
	 * 构造方法.
	 * 
	 * @param context
	 * @param main_layout_res_id
	 * @param indicator_parent_res_id
	 * @param pager_res_id
	 * @param views 将要被显示的所有view集合（必须与  {@link #mIndicators}中的元严格一对一对应起来 ）
	 * @param indicators 将要被显示的页面对应的指示器组件集合（必须与  {@link #mViews}中的元严格一对一对应起来 ） 
	 * #see {@link #SimpleViewPager(Activity, AttributeSet, int, int, int, ArrayList, ArrayList)}
	 */
	public SimpleViewPager(Activity context
			, int main_layout_res_id, int indicator_parent_res_id, int pager_res_id
			, ArrayList<View> views, ArrayList<PaggerIndicator> indicators)
	{
		this(context, null
				, main_layout_res_id, indicator_parent_res_id, pager_res_id
				, views, indicators);
	}

	/**
	 * 构造方法.
	 * 
	 * @param context
	 * @param attrs
	 * @param main_layout_res_id 
	 * @param indicator_parent_res_id
	 * @param pager_res_id
	 * @param views 将要被显示的所有view集合（必须与  {@link #mIndicators}中的元严格一对一对应起来 ）
	 * @param indicators 将要被显示的页面对应的指示器组件集合（必须与  {@link #mViews}中的元严格一对一对应起来 ） 
	 * @see #putDatas(ArrayList, ArrayList)
	 */
	public SimpleViewPager(final Activity context, AttributeSet attrs
			, int main_layout_res_id, int indicator_parent_res_id, int pager_res_id
			, ArrayList<View> views, ArrayList<PaggerIndicator> indicators)
	{
		super(context);
		
		// 
		this.contextInflater = LayoutInflater.from(context);
		mLayoutInflater = contextInflater.inflate(main_layout_res_id, null);//R.layout.chatting_list_view_bottom_waves, null);
		mViewGroupBottomIndicator = (ViewGroup)mLayoutInflater.findViewById(indicator_parent_res_id);//R.id.chatting_list_view_bottom_waves_bottomLL);

		// 加入到外层布局中，以方便重用本面板的activity对Ui的组织
		addView(mLayoutInflater, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		// 
		mViewPager = (ViewPager)mLayoutInflater.findViewById(pager_res_id);//R.id.chatting_list_view_bottom_waves_tabpager);
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		
		// 设置界面数据
		putDatas(views, indicators);
	}
	
	/**
	 * 用新数据更新界面显示.
	 * 
	 * <p>
	 * 此方法将完全清空原先的数据及ui显示，并以新的数据集进行ui展现.
	 * 
	 * @param newVews
	 * @param newIndicators
	 */
	public void putDatas(ArrayList<View> newVews, ArrayList<PaggerIndicator> newIndicators)
	{
		// 先暂存数据集
		this.mViews = newVews;
		this.mIndicators = newIndicators;
		
		// 复位
		mCurrentSelectedIndex = 0;
		
		// 清空可能存在的老指示器组件
		mViewGroupBottomIndicator.removeAllViews();
		// 添加页面指示器组件
		for(PaggerIndicator id : newIndicators)
			mViewGroupBottomIndicator.addView((View)id);

		// set default indicator
		if(newIndicators != null && newIndicators.size()>0)
			newIndicators.get(0).setIndicatorSelected(true);

		// 当小于2个page时，指示器就不用显示了
		if(newIndicators.size() < 2)
			mViewGroupBottomIndicator.setVisibility(View.INVISIBLE);
		else
			mViewGroupBottomIndicator.setVisibility(View.VISIBLE);

		// 设置adapter
		mViewPager.setAdapter(createPagerAdapter(newVews));
	}
	
	/**
	 * 页面的适配器初始化.
	 * 
	 * @param views
	 * @return
	 */
	public static PagerAdapter createPagerAdapter(final ArrayList<View> views)
	{
		//填充ViewPager的数据适配器
		PagerAdapter mPagerAdapter = new PagerAdapter()
		{
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) 
			{
				return arg0 == arg1;
			}
			@Override
			public int getCount() {
				return views.size();
			}
			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(views.get(position));
			}
			//@Override
			//public CharSequence getPageTitle(int position) {
			//return titles.get(position);
			//}
			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(views.get(position));
				return views.get(position);
			}
		};
//		mPagerAdapter.notifyDataSetChanged();
		return mPagerAdapter;
	}
	
	/**
	 * 当页面滑动时，调用本方法.
	 * 
	 * <p>
	 * 本方法默认处理指示器组件的选中样式等，并保存当前选中的索引值到 {@link #mCurrentSelectedIndex}.
	 * 
	 * @param newIndex
	 * @see PaggerIndicator
	 */
	protected void onPageSelectedImpl(int newIndex)
	{
		// 更新指示器显示状态
		mIndicators.get(mCurrentSelectedIndex).setIndicatorSelected(false); // 先更新老的
		mIndicators.get(newIndex).setIndicatorSelected(true);              // 理更新新的
		
		// 保存当前被选中的索引
		mCurrentSelectedIndex = newIndex;
	}
	
	public LayoutInflater getContextInflater()
	{
		return contextInflater;
	}

	public View getViewOfMyself()
	{
		return mLayoutInflater;
	}

	public ViewPager getViewPager()
	{
		return mViewPager;
	}

	public ViewGroup getViewGroupBottomIndicator()
	{
		return mViewGroupBottomIndicator;
	}

	public int getCurrentSelectedIndex()
	{
		return mCurrentSelectedIndex;
	}

	public ArrayList<View> getViews()
	{
		return mViews;
	}

	public ArrayList<PaggerIndicator> getIndicators()
	{
		return mIndicators;
	}

	/* 
	 * 页卡切换监听(原作者:D.Winter)
	 */
	private class MyOnPageChangeListener implements OnPageChangeListener
	{
		@Override
		public void onPageSelected(int arg0)
		{
			onPageSelectedImpl(arg0);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) 
		{
		}

		@Override
		public void onPageScrollStateChanged(int arg0) 
		{
		}
	}
	
	/**
	 * 一个页面指示器组件默认实现类.
	 * 
	 * @author Jack Jiang, 2013-07-24
	 * @version 1.0
	 */
	public static class DefaultPaggerIndicatorImageView 
		extends ImageView implements PaggerIndicator
	{
		public DefaultPaggerIndicatorImageView (Context context)
		{
			this(context, null);
		}

		public DefaultPaggerIndicatorImageView (Context context, AttributeSet attrs)
		{
			super(context, attrs);	

			init();
		}

		private void init()
		{
			LayoutParams mlp = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			//this.setMargins(5, 5, 5, 5); // setMargins don't work
			this.setPadding(5, 5, 5, 5);   // but setPadding is ok
			this.setLayoutParams(mlp);
			// default status
			this.setIndicatorSelected(false);
		}

		public void setIndicatorSelected(boolean selected)
		{
			if(selected)
				this.setImageResource(R.drawable.widget_pagger_dot_black);
			else
				this.setImageResource(R.drawable.widget_pagger_dot_white);
		}
	}
	
	/**
	 * 页面指示器接口.
	 */
	public interface PaggerIndicator
	{
		/**
		 * 设置指示器的选中与否状态.
		 * 
		 * @param selected true表示选中当前，否则表示取消选中
		 */
		void setIndicatorSelected(boolean selected);
	}
}