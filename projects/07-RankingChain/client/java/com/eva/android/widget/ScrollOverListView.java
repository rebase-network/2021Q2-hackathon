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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tang.R;

/**
 * 该类主要是完成 头部部分的功能封装
 * 
 * 一个可以监听ListView是否滚动到最顶部或最底部的自定义控件 只能监听由触摸产生的，如果是ListView本身Flying导致的，则不能监听</br>
 * 如果加以改进，可以实现监听scroll滚动的具体位置等
 * 
 * @author 进
 */

public class ScrollOverListView extends ListView implements OnScrollListener
{

	private int mLastY;

	private int mBottomPosition;

	private static final String TAG = "listview";

	/** 松开更新 **/
	private final static int RELEASE_To_REFRESH = 0;
	/** 下拉更新 **/
	private final static int PULL_To_REFRESH = 1;
	/** 更新中 **/
	private final static int REFRESHING = 2;
	/** 无 **/
	private final static int DONE = 3;
	/** 加载中 **/
	private final static int LOADING = 4;
	/** 实际的padding的距离与界面上偏移距离的比例 **/
	private final static int RATIO = 3;

	private LayoutInflater inflater;
	/** 头部刷新的布局 **/
	private LinearLayout headView;
	/** 头部显示下拉刷新等的控件 **/
	private TextView tipsTextview;
	/** 箭头图标 **/
	private ImageView arrowImageView;
	/** 头部滚动条 **/
	private ProgressBar progressBar;
	/** 显示动画 **/
	private RotateAnimation animation;
	/** 头部回退显示动画 **/
	private RotateAnimation reverseAnimation;
	/** 用于保证startY的值在一个完整的touch事件中只被记录一次 **/
	private boolean isRecored;
	/** 头部高度 **/
	private int headContentHeight;
	/** 开始的Y坐标 **/
	private int startY;
	/** 第一个item **/
	private int firstItemIndex;
	/** 状态 **/
	private int state;

	private boolean isBack;
	/** 是否要使用下拉刷新功能 **/
	public boolean showRefresh = true;

	public static boolean canRefleash = true;

	public ScrollOverListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init(context);
	}

	public ScrollOverListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}

	public ScrollOverListView(Context context)
	{
		super(context);
		init(context);
	}

	/** 初始化控件 **/
	private void init(Context context)
	{
		mBottomPosition = 0;
		setCacheColorHint(0);
		inflater = LayoutInflater.from(context);

		headView = (LinearLayout) inflater.inflate(R.layout.common_pull_down_head, null);
		arrowImageView = (ImageView) headView.findViewById(R.id.head_arrowImageView);
		arrowImageView.setMinimumWidth(70);
		arrowImageView.setMinimumHeight(50);
		progressBar = (ProgressBar) headView.findViewById(R.id.head_progressBar);
		tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);

		measureView(headView);

		headContentHeight = headView.getMeasuredHeight();

		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();

		/** 列表添加头部 **/
		addHeaderView(headView, null, false);

		setOnScrollListener(this);

		animation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);

		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);

		state = DONE;
		
		this.setDivider(null);
	}

	/** 触摸事件的处理 **/
	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{

		final int action = ev.getAction();
		final int y = (int) ev.getRawY();
		cancelLongPress();
		switch (action)
		{
		case MotionEvent.ACTION_DOWN:
		{ // 按下的时候
			if (firstItemIndex == 0 && !isRecored)
			{
				isRecored = true;
				startY = (int) ev.getY();
				Log.v(TAG, "在down时候记录当前位置‘");
			}
			// ===========================
			mLastY = y;
			final boolean isHandled = mOnScrollOverListener.onMotionDown(ev);
			if (isHandled)
			{
				mLastY = y;
				return isHandled;
			}
			break;
		}

		case MotionEvent.ACTION_MOVE:
		{ // 手指正在移动的时候
			int tempY = (int) ev.getY();
			if (showRefresh)
			{

				if (!isRecored && firstItemIndex == 0)
				{
					Log.v(TAG, "在move时候记录下位置");
					isRecored = true;
					startY = tempY;
				}
				if (state != REFRESHING && isRecored && state != LOADING)
				{

					// 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动

					// 可以松手去刷新了
					if (state == RELEASE_To_REFRESH)
					{

						setSelection(0);

						// 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
						if (((tempY - startY) / RATIO < headContentHeight) && (tempY - startY) > 0)
						{
							state = PULL_To_REFRESH;
							changeHeaderViewByState();

						}
						// 一下子推到顶了
						else if (tempY - startY <= 0)
						{
							state = DONE;
							changeHeaderViewByState();

							Log.v(TAG, "由松开刷新状态转变到done状态");
						}
						// 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
						else
						{
							// 不用进行特别的操作，只用更新paddingTop的值就行了
						}
					}
					// 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
					if (state == PULL_To_REFRESH)
					{

						setSelection(0);

						// 下拉到可以进入RELEASE_TO_REFRESH的状态
						if ((tempY - startY) / RATIO >= headContentHeight)
						{
							state = RELEASE_To_REFRESH;
							isBack = true;
							changeHeaderViewByState();

							Log.v(TAG, "由done或者下拉刷新状态转变到松开刷新");
						}
						// 上推到顶了
						else if (tempY - startY <= 0)
						{
							state = DONE;
							changeHeaderViewByState();

							Log.v(TAG, "由DOne或者下拉刷新状态转变到done状态");
						}
					}

					// done状态下
					if (state == DONE)
					{
						if (tempY - startY > 0)
						{
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
					}

					// 更新headView的size
					if (state == PULL_To_REFRESH)
					{
						headView.setPadding(0, -1 * headContentHeight + (tempY - startY) / RATIO, 0, 0);
					}

					// 更新headView的paddingTop
					if (state == RELEASE_To_REFRESH)
					{
						headView.setPadding(0, (tempY - startY) / RATIO - headContentHeight, 0, 0);
					}

				}
			}
			// ==============================================
			final int childCount = getChildCount();
			if (childCount == 0)
				return super.onTouchEvent(ev);
			final int itemCount = getAdapter().getCount() - mBottomPosition;
			final int deltaY = y - mLastY;
			final int lastBottom = getChildAt(childCount - 1).getBottom();
			final int end = getHeight() - getPaddingBottom();

			final int firstVisiblePosition = getFirstVisiblePosition();

			final boolean isHandleMotionMove = mOnScrollOverListener.onMotionMove(ev, deltaY);

			if (isHandleMotionMove)
			{
				mLastY = y;
				return true;
			}

			/** 到达底部 * 到达底部的事件在另外一个类执行 **/
			if (firstVisiblePosition + childCount >= itemCount && lastBottom <= end && deltaY < 0)
			{
				final boolean isHandleOnListViewBottomAndPullDown;
				isHandleOnListViewBottomAndPullDown = mOnScrollOverListener.onListViewBottomAndPullUp(deltaY);
				if (isHandleOnListViewBottomAndPullDown)
				{
					mLastY = y;
					return true;
				}
			}
			break;
		}

		case MotionEvent.ACTION_UP:
		{ // 手指抬起来的时候
			if (state != REFRESHING && state != LOADING)
			{
				if (state == DONE)
				{
					// 什么都不做
				}
				if (state == PULL_To_REFRESH)
				{
					state = DONE;
					changeHeaderViewByState();
					Log.v(TAG, "由下拉刷新状态，到done状态");
				}

				if (state == RELEASE_To_REFRESH)
				{
					state = REFRESHING;
					changeHeaderViewByState();

					canRefleash = true;

					Log.v(TAG, "由松开刷新状态，到done状态");
				}
			}

			isRecored = false;
			isBack = false;

			// /======================
			final boolean isHandlerMotionUp = mOnScrollOverListener.onMotionUp(ev);
			if (isHandlerMotionUp)
			{
				mLastY = y;
				return true;
			}
			break;
		}
		}
		mLastY = y;
		return super.onTouchEvent(ev);
	}

	/** 空的 */
	private OnScrollOverListener mOnScrollOverListener = new OnScrollOverListener()
	{

		@Override
		public boolean onListViewTopAndPullDown(int delta)
		{
			return false;
		}

		@Override
		public boolean onListViewBottomAndPullUp(int delta)
		{
			return false;
		}

		@Override
		public boolean onMotionDown(MotionEvent ev)
		{
			return false;
		}

		@Override
		public boolean onMotionMove(MotionEvent ev, int delta)
		{
			return false;
		}

		@Override
		public boolean onMotionUp(MotionEvent ev)
		{
			return false;
		}

	};

	// =============================== public method
	/**
	 * 可以自定义其中一个条目为头部，头部触发的事件将以这个为准，默认为第一个
	 * 
	 * @param index
	 *            正数第几个，必须在条目数范围之内
	 */
	public void setTopPosition(int index)
	{
		if (getAdapter() == null)
			throw new NullPointerException("You must set adapter before setTopPosition!");
		if (index < 0)
			throw new IllegalArgumentException("Top position must > 0");
	}

	/**
	 * 可以自定义其中一个条目为尾部，尾部触发的事件将以这个为准，默认为最后一个
	 * 
	 * @param index
	 *            倒数第几个，必须在条目数范围之内
	 */
	public void setBottomPosition(int index)
	{
		if (getAdapter() == null)
			throw new NullPointerException("You must set adapter before setBottonPosition!");
		if (index < 0)
			throw new IllegalArgumentException("Bottom position must > 0");

		mBottomPosition = index;
	}

	/**
	 * 设置这个Listener可以监听是否到达顶端，或者是否到达低端等事件</br>
	 * 
	 * @see OnScrollOverListener
	 */
	public void setOnScrollOverListener(OnScrollOverListener onScrollOverListener)
	{
		mOnScrollOverListener = onScrollOverListener;
	}

	/**
	 * 滚动监听接口
	 * 
	 * @see ScrollOverListView#setOnScrollOverListener(OnScrollOverListener)
	 * 
	 */
	public interface OnScrollOverListener
	{
		/**
		 * 到达最顶部触发
		 * 
		 * @param delta
		 *            手指点击移动产生的偏移量
		 * @return
		 */
		boolean onListViewTopAndPullDown(int delta);

		/**
		 * 到达最底部触发
		 * 
		 * @param delta
		 *            手指点击移动产生的偏移量
		 * @return
		 */
		boolean onListViewBottomAndPullUp(int delta);

		/**
		 * 手指触摸按下触发，相当于{@link MotionEvent#ACTION_DOWN}
		 * 
		 * @return 返回true表示自己处理
		 * @see View#onTouchEvent(MotionEvent)
		 */
		boolean onMotionDown(MotionEvent ev);

		/**
		 * 手指触摸移动触发，相当于{@link MotionEvent#ACTION_MOVE}
		 * 
		 * @return 返回true表示自己处理
		 * @see View#onTouchEvent(MotionEvent)
		 */
		boolean onMotionMove(MotionEvent ev, int delta);

		/**
		 * 手指触摸后提起触发，相当于{@link MotionEvent#ACTION_UP}
		 * 
		 * @return 返回true表示自己处理
		 * @see View#onTouchEvent(MotionEvent)
		 */
		boolean onMotionUp(MotionEvent ev);

	}

	@Override
	public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2, int arg3)
	{
		firstItemIndex = firstVisiableItem;

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{

	}

	// 此方法直接照搬自网络上的一个下拉刷新的demo，此处是“估计”headView的width以及height
	private void measureView(View child)
	{
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null)
		{
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0)
		{
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		}
		else
		{
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	public void onRefreshComplete()
	{
		state = DONE;
		changeHeaderViewByState();
	}

	// 当状态改变时候，调用该方法，以更新界面
	private void changeHeaderViewByState()
	{
		switch (state)
		{
		case RELEASE_To_REFRESH:
			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);

			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);

			tipsTextview.setText("Loosen to refresh");

			Log.v(TAG, "当前状态，松开刷新");
			break;
		case PULL_To_REFRESH:
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			// 是由RELEASE_To_REFRESH状态转变来的
			if (isBack)
			{
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);

				tipsTextview.setText("Pull down to refresh");
			}
			else
			{
				tipsTextview.setText("Pull down to refresh");
			}
			Log.v(TAG, "当前状态，下拉刷新");
			break;

		case REFRESHING:

			headView.setPadding(0, 0, 0, 0);
			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			tipsTextview.setText("Refreshing...");

			Log.v(TAG, "当前状态,正在刷新...");
			break;
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);
			progressBar.setVisibility(View.GONE);
			arrowImageView.clearAnimation();
			arrowImageView.setImageResource(R.drawable.common_pull_down_arrow);
			tipsTextview.setText("Pull down to refresh");

			Log.v(TAG, "当前状态，done");
			break;
		}
	}
}
