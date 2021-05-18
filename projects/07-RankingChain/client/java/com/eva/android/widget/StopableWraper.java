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

public abstract class StopableWraper
{
	private Context context = null;
    
	private Handler handler = null;
	private Runnable runnable = null;
    
	/** 正在执行中 */
    private boolean running = false;
    
    /** 延迟时间 */
    protected long delayMillis = 0;
    
    public StopableWraper(Context context, long delayMillis)
    {
    	this.context = context;
    	this.delayMillis = delayMillis;
    	
    	init();
    }
    
    protected void init()
    {
    	handler = new Handler();
		runnable = new Runnable(){
			@Override
			public void run()
			{
				stopImpl();
				running = false;
			}
		};
    }
	
    // 每次调用start的时候就是保证动画不被停止，否则将在一段延迟后停
    // 止动画（用于没办法预知应用层停止动作的场景下能自动停止）
	public void start()
	{
		if(!running)
		{
			running = true;
			startImpl();
		}
		
		// 先remove
		handler.removeCallbacks(runnable);
		// 无条件
		handler.postDelayed(runnable, delayMillis);
	}
	
	// 强制退出事件循环
	public void enforceStop(boolean needInvodeStopImpl)
	{
		handler.removeCallbacks(runnable);
		running = false;
		if(needInvodeStopImpl)
			stopImpl();
	}
	
	protected abstract void stopImpl();
	
	protected abstract void startImpl();
}
