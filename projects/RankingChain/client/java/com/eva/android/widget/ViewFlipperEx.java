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
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.tang.R;

/**
 * <p>
 * 一个ViewFlipper实现类.<br>
 * 本类在android标准ViewFlipper基本础上实现了左右滑动图片并用动画淡入淡出的功能.
 * </p>
 * 
 * @author 来自互联网
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 */
public class ViewFlipperEx extends ViewFlipper implements OnGestureListener
{
	private GestureDetector gestureDetector = null;

	public ViewFlipperEx(Context context)
	{
		this(context, null);
	}
	public ViewFlipperEx(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		
		// 生成GestureDetector对象，用于检测手势事件
		gestureDetector = new GestureDetector(this);
	}

	public boolean onDown(MotionEvent arg0)
	{
		return false;
	}
	
	public void goToNext()
	{
		// 添加动画
		this.setInAnimation(AnimationUtils.loadAnimation(this.getContext(),
				R.anim.widget_viewflipperex_push_left_in));
		this.setOutAnimation(AnimationUtils.loadAnimation(this.getContext(),
				R.anim.widget_viewflipperex_push_left_out));
		this.showNext();
	}
	public void goToPre()
	{
		this.setInAnimation(AnimationUtils.loadAnimation(this.getContext(),
				R.anim.widget_viewflipperex_push_right_in));
		this.setOutAnimation(AnimationUtils.loadAnimation(this.getContext(),
				R.anim.widget_viewflipperex_push_right_out));
		this.showPrevious();
	}

	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3)
	{
		// 对手指滑动的距离进行了计算，如果滑动距离大于120像素，就做切换动作，否则不做任何切换动作。
		// 从左向右滑动
		if (arg0.getX() - arg1.getX() > 120)
		{
			goToNext();
			return true;
		}
		// 从右向左滑动
		else if (arg0.getX() - arg1.getX() < -120)
		{
			goToPre();
			return true;
		}
		return true;
	}

	public void onLongPress(MotionEvent e)
	{
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	{
		return false;
	}

	public void onShowPress(MotionEvent e)
	{
	}

	public boolean onSingleTapUp(MotionEvent e)
	{
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return this.gestureDetector.onTouchEvent(event);
	}
	
	@Override//此方法用于本组件放置于ScrollView时解决所产生的冲突，否则将无法监听到左右滑动
	public boolean dispatchTouchEvent(MotionEvent event)
	{  
		if(gestureDetector.onTouchEvent(event))
			event.setAction(MotionEvent.ACTION_CANCEL);  
		return super.dispatchTouchEvent(event);  
	} 
}