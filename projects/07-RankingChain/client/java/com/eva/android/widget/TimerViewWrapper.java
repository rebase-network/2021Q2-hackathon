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
import android.os.Handler;

/**
 * 计时器包装实现类.
 * 
 * @author Jason Lu, Jack Jiang
 * @version 1.1
 */
public class TimerViewWrapper
{
	public final static int KEEP_ALIVE_INTERVAL = 1000;
	
	protected String res = "00:00:00";
	protected int sec = 0;
	protected int min = 0;
	protected int hour = 0;

	/** 当前线程是否正在执行中 */
	protected boolean keepAliveRunning = false;
	protected Handler handler = null;
	protected Runnable runnable = null;
	
	protected Context context = null;

	public TimerViewWrapper(Context context)
	{
		this.context = context;
		
		//
		init();
	}
	
	/**
	 * 核心初始化方法.
	 * <p>
	 * 理论上本方法在实例的生命周期中只需要调用一次即可.
	 * 
	 * @see #runImpl()
	 */
	protected void init()
	{
		handler = new Handler();
		runnable = new Runnable(){
			@Override
			public void run()
			{
				runImpl();
			}
		};
	}
	/**
	 * 循环执行实现方法.
	 * <p>
	 * 子类可重写本方法实现自定义逻辑哦.
	 */
	protected void runImpl()
	{
		// 
		sec++;
		if (sec == 60)
		{
			min++;
			sec = 0;
			if (min == 60)
			{
				hour++;
				min = 0;
				if (hour == 24)
				{
					sec = 59;
					min = 59;
					hour = 23;
					stop();
				}
			}
		}
		
		res = "" + int2Time(hour) + ":" + int2Time(min) + ":" + int2Time(sec);
		//
		timerChanged(hour, min, sec);
		timerChanged(res);
		
		// 开始下一个心跳循环
		handler.postDelayed(runnable, KEEP_ALIVE_INTERVAL);
	}
	
	/**
	 * 重置计时.
	 */
	protected void resetDuration()
	{
		sec = 0;
		min = 0;
		hour = 0;
		res = "00:00:00";
//		
//		//
//		timerChanged(hour, min, sec);
//		timerChanged(res);
	}
	
	/**
	 * 第3方法可随时调用的开始方法.
	 * <p>
	 * 本方法调用时将会确保之前的循环被重置，所以可放心调用.
	 * @see #stop()
	 * @see #resetDuration()
	 * @see Handler#postDelayed(Runnable, long)
	 */
	public void start()
	{
		//
		stop();
		
		//
		resetDuration();
		
		//
		handler.postDelayed(runnable, KEEP_ALIVE_INTERVAL);
		//
		keepAliveRunning = true;
	}
	
	/**
	 * 第3方可随时调用的停止方法.
	 * 
	 * @see Handler#removeCallbacks(Runnable)
	 */
	public void stop()
	{
		//
		handler.removeCallbacks(runnable);
		//
		keepAliveRunning = false;
	}
	
	/**
	 * 循环是否正在运行中.
	 * 
	 * @return true表示正在，否则不在运行中
	 */
	public boolean isKeepAliveRunning()
	{
		return keepAliveRunning;
	}
	
	/**
	 * 子类请重写此方法，实现计时时间变动的ui展现等.<br>
	 * 本方法默认什么也不做.
	 * 
	 * @param hh_mm_ssStr hh:mm:ss格式的时长字符串
	 */
	protected void timerChanged(String hh_mm_ssStr)
	{
		// default do nothing
	}
	
	/**
	 * 子类请重写此方法，实现计时时间变动的ui展现等.<br>
	 * 本方法默认什么也不做.
	 * 
	 * @param hour 当前时长之“时”
	 * @param minute 当前时长之“分”
	 * @param second 当前时长之“秒”
	 */
	protected void timerChanged(int hour, int minute, int second)
	{
		// default do nothing
	}
	
	//------------------------------------------------------------------------- utility methods
	public static String int2Time(int s)
	{
		if(s < 10)
			return "0" + s;
		return "" + s;
	}
}
