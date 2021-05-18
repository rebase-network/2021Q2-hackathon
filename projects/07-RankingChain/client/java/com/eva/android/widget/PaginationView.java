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
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tang.R;

/**
 * 下拉刷新控件 真正实现下拉刷新的是这个控件， ScrollOverListView只是提供触摸的事件等
 * 
 * @author 进
 */

public class PaginationView extends LinearLayout implements ScrollOverListView.OnScrollOverListener
{

	private static final int START_PULL_DEVIATION = 50; // 移动误差
	private static final int WHAT_DID_MORE = 5; // Handler what 已经获取完更多
	private static final int WHAT_DID_REFRESH = 3; // Handler what 已经刷新完
	/** 底部更多的按键 **/
	private RelativeLayout mFooterView;
	/** 底部更多的按键 **/
	private TextView mFooterTextView;
	/** 底部更多的按键 **/
	private ProgressBar mFooterLoadingView;
	/** 已经含有 下拉刷新功能的列表 **/
	private ScrollOverListView mListView;
	/** 刷新和更多的事件接口 **/
	private OnPullDownListener mOnPullDownListener;
	private float mMotionDownLastY; // 按下时候的Y轴坐标
	private boolean mIsFetchMoreing; // 是否获取更多中
	private boolean mIsPullUpDone; // 是否回推完成
	private boolean mEnableAutoFetchMore; // 是否允许自动获取更多
	private boolean mIsNoMoreData = false;

	public PaginationView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initHeaderViewAndFooterViewAndListView(context);
	}

	public PaginationView(Context context)
	{
		super(context);
		initHeaderViewAndFooterViewAndListView(context);
	}

	/*
	 * ================================== Public method 外部使用，具体就是用这几个就可以了
	 */

	/**
	 * 刷新和获取更多事件接口
	 */
	public interface OnPullDownListener
	{
		/** 刷新事件接口 这里要注意的是获取更多完 要关闭 刷新的进度条RefreshComplete() **/
		void onRefresh();

		/** 刷新事件接口 这里要注意的是获取更多完 要关闭 更多的进度条 notifyDidMore() **/
		void onMore();
	}

	/**
	 * 通知已经获取完更多了，要放在Adapter.notifyDataSetChanged后面
	 * 当你执行完更多任务之后，调用这个notyfyDidMore() 才会隐藏加载圈等操作
	 */
	public void notifyDidMore()
	{
		mUIHandler.sendEmptyMessage(WHAT_DID_MORE);
	}

	/** 刷新完毕 关闭头部滚动条 **/
	public void RefreshComplete()
	{
		mUIHandler.sendEmptyMessage(WHAT_DID_REFRESH);
	}

	/**
	 * 设置监听器
	 * 
	 * @param listener
	 */
	public void setOnPullDownListener(OnPullDownListener listener)
	{
		mOnPullDownListener = listener;
	}

	/**
	 * 获取内嵌的listview
	 * 
	 * @return ScrollOverListView
	 */
	public ListView getListView()
	{
		return mListView;
	}

	public void setmOnPullDownListener(OnPullDownListener mOnPullDownListener)
	{
		this.mOnPullDownListener = mOnPullDownListener;
	}
	
	/**
	 * 是否开启自动获取更多 自动获取更多，将会隐藏footer，并在到达底部的时候自动刷新
	 * 
	 * @param index
	 *            倒数第几个触发
	 */
	public void enableAutoFetchMore(boolean enable, int index)
	{
		if (enable)
		{
			mListView.setBottomPosition(index);
			mFooterLoadingView.setVisibility(View.VISIBLE);
		}
		else
		{
			mFooterTextView.setText("");
			mFooterLoadingView.setVisibility(View.GONE);
		}
		mEnableAutoFetchMore = enable;
	}

	/*
	 * ================================== Private method 具体实现下拉刷新等操作
	 * 
	 * ==================================
	 */

	/**
	 * 初始化界面
	 */
	private void initHeaderViewAndFooterViewAndListView(Context context)
	{
		setOrientation(LinearLayout.VERTICAL);

		/**
		 * 自定义脚部文件
		 */
		mFooterView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.common_pull_down_footer, null);
		mFooterTextView = (TextView) mFooterView.findViewById(R.id.pulldown_footer_text);
		mFooterLoadingView = (ProgressBar) mFooterView.findViewById(R.id.pulldown_footer_loading);
		mFooterView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (!mIsFetchMoreing && !mIsNoMoreData)
				{
					mIsFetchMoreing = true;
					mFooterTextView.setText("Loading...");
					mFooterLoadingView.setVisibility(View.VISIBLE);
					mOnPullDownListener.onMore();
				}
			}
		});

		/*
		 * ScrollOverListView 同样是考虑到都是使用，所以放在这里 同时因为，需要它的监听事件
		 */
		mListView = new ScrollOverListView(context);
		mListView.setOnScrollOverListener(this);
		mListView.setCacheColorHint(Color.TRANSPARENT);
		addView(mListView, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

//		// 空的listener
//		mOnPullDownListener = new OnPullDownListener()
//		{
//			@Override
//			public void onRefresh()
//			{
//			}
//
//			@Override
//			public void onMore()
//			{
//				System.out.println("adasaasdas");
//			}
//		};

		mListView.addFooterView(mFooterView);
		// mListView.setAdapter(mListView.getAdapter());

	}

	private Handler mUIHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case WHAT_DID_REFRESH:
				{
					mListView.onRefreshComplete();
					return;
				}
	
				case WHAT_DID_MORE:
				{
					mIsFetchMoreing = false;
					mFooterTextView.setText(mIsNoMoreData ? "No more data" : "More");
					mFooterLoadingView.setVisibility(View.GONE);
				}
			}
		}

	};

	/**
	 * 条目是否填满整个屏幕
	 */
	private boolean isFillScreenItem()
	{
		final int firstVisiblePosition = mListView.getFirstVisiblePosition();
		final int lastVisiblePostion = mListView.getLastVisiblePosition() - mListView.getFooterViewsCount();
		final int visibleItemCount = lastVisiblePostion - firstVisiblePosition + 1;
		final int totalItemCount = mListView.getCount() - mListView.getFooterViewsCount();

		if (visibleItemCount < totalItemCount)
			return true;
		return false;
	}

	/*
	 * ================================== 实现 OnScrollOverListener接口
	 */

	@Override
	public boolean onListViewTopAndPullDown(int delta)
	{

		return true;
	}

	@Override
	public boolean onListViewBottomAndPullUp(int delta)
	{
		if (!mEnableAutoFetchMore || mIsFetchMoreing)
			return false;
		// 数量充满屏幕才触发
		if (isFillScreenItem())
		{
			mIsFetchMoreing = true;
			mFooterTextView.setText("Loading...");
			mFooterLoadingView.setVisibility(View.VISIBLE);
			mOnPullDownListener.onMore();
			return true;
		}
		return false;
	}

	@Override
	public boolean onMotionDown(MotionEvent ev)
	{
		mIsPullUpDone = false;
		mMotionDownLastY = ev.getRawY();

		return false;
	}

	@Override
	public boolean onMotionMove(MotionEvent ev, int delta)
	{
		// 当头部文件回推消失的时候，不允许滚动
		if (mIsPullUpDone)
			return true;

		// 如果开始按下到滑动距离不超过误差值，则不滑动
		final int absMotionY = (int) Math.abs(ev.getRawY() - mMotionDownLastY);
		if (absMotionY < START_PULL_DEVIATION)
			return true;

		return false;
	}

	@Override
	public boolean onMotionUp(MotionEvent ev)
	{
		if (ScrollOverListView.canRefleash)
		{
			ScrollOverListView.canRefleash = false;
			mOnPullDownListener.onRefresh();
		}
		return false;
	}

	/** 隐藏头部 禁用下拉更新 **/
	public void setHideHeader()
	{
		mListView.showRefresh = false;
	}

	/** 显示头部 使用下拉更新 **/
	public void setShowHeader()
	{
		mListView.showRefresh = true;
	}

	/** 隐藏底部 禁用上拉更多 **/
	public void setHideFooter()
	{
		mFooterView.setVisibility(View.GONE);
		mFooterTextView.setVisibility(View.GONE);
		mFooterLoadingView.setVisibility(View.GONE);
		enableAutoFetchMore(false, 1);
	}

	/** 显示底部 使用上拉更多 **/
	public void setShowFooter()
	{
		mFooterView.setVisibility(View.VISIBLE);
		mFooterTextView.setVisibility(View.VISIBLE);
		mFooterLoadingView.setVisibility(View.VISIBLE);
		enableAutoFetchMore(true, 1);
	}
	
	/** 显示底部 告知无更多信息 **/
	public void setNoMoreFooter()
	{
		mIsNoMoreData = true;
		enableAutoFetchMore(false, 1);
	}

}
