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
package com.tang.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.eva.android.AppManager;
import com.eva.android.x.XToolKits;
//import com.tang.chainevaluate.MusicApplication;
//import com.tang.chainevaluate.logic.launch.AppStart;
//import com.tang.chainevaluate.utils.IntentFactory;

/**
 * 应用程序Activity的基类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 */
public class BaseActivity extends AppCompatActivity//Activity
{
	private final static String TAG = BaseActivity.class.getSimpleName();

	// 是否允许销毁
	private boolean allowDestroy = true;

	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);

		//----------------------------- APP 异常自救代码 START
//		if(this.appHearthCheckInvalid())
//			return;
		//----------------------------- APP 异常自救代码 END

		// 设置状态栏文字颜色为深色（系统默认是白色）
		configStatusBarTextColorDark();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
//		// for Google Analytics SDK for Android v3
//		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		
//		// for Google Analytics SDK for Android v3
//		EasyTracker.getInstance(this).activityStop(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().removeActivity(this);
	}

	public void setAllowDestroy(boolean allowDestroy) {
		this.allowDestroy = allowDestroy;
	}

	public void setAllowDestroy(boolean allowDestroy, View view) {
		this.allowDestroy = allowDestroy;
		this.view = view;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && view != null) {
			view.onKeyDown(keyCode, event);
			if (!allowDestroy) {
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * @param id
	 * @return
	 * @see {@link #getResources()}.{@link #getString(int)}
	 */
	public String $$(int id)
	{
		return this.getResources().getString(id);
	}

	/**
	 * 设置状态栏文字颜色为深色（系统默认是白色）。
	 * <p>
	 * 说明：之所以要设置为深色，是因为RainbowChat的绝大多数界面，沉浸式效果时的背景用的是白色，
	 * 系统默认的状态栏文字颜色也是白色，这样的话就看不清系统的时间等等内容了，太难看。
	 */
	protected void configStatusBarTextColorDark()
	{
		XToolKits.setStatusBarTextColorDark(this);
	}

//	//----------------------------- APP 异常自救代码 START
//	/**
//	 * 关于APP异常自救的原因、方案、实现逻辑等，请见 {@link MusicApplication#appHearthCheckFlag} 字段的详细说明。
//	 *
//	 * @return true表示APP现场处于非正常状态，否则表示正常状态
//	 */
//	private boolean appHearthCheckInvalid()
//	{
//		try{
//			Class clazz = AppStart.class; // 入口activity
//
//			String className = getClass().getSimpleName();
//
//			// 如果当前类非闪屏类，才需要进行异常判断，否则不需要
//			if(!getClass().equals(clazz))
//			{
//				Log.d(TAG, "【APP异常现场检查-内】当前"+className+"不是入口类，且MyApplication.appHearthCheckFlag="
//						+MusicApplication.appHearthCheckFlag);
//
//				// 处于异常现场了
//				if(MusicApplication.appHearthCheckFlag ==-1)
//				{
//					Log.w(TAG, "【APP异常现场检查-内】已在"+className+"中检测到APP异常现场，马上进" +
//							"入异常重启逻辑（即跳转到PortalActivity主界面【第1/2步】）。。。");
//
//					Context ctx = MusicApplication.getInstance2();
//
//					// 以下逻辑为跳转到PortalActivity主界面
//					Intent i = IntentFactory.createPortalIntent(ctx);
//					// 没有这个flag，是无法借助Application实例启动activity的
//					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					// 前往门户主页
//					ctx.startActivity(i);
//
//					return true;
//				}
//				else
//				{
//					Log.d(TAG, "【APP异常现场检查-内】在"+className+"中未检测到APP异常现场，什么也不需要做！（MyApplication.appHearthCheckFlag="
//							+MusicApplication.appHearthCheckFlag+"）");
//				}
//			}
//			else{
//				Log.d(TAG, "【APP异常现场检查-内】当前"+className+"是入口类自已，什么也不需要做！（MyApplication.appHearthCheckFlag="
//						+MusicApplication.appHearthCheckFlag+"）");
//			}
//		}
//		catch (Exception e){
//			Log.w(TAG, e);
//		}
//
//		return false;
//	}
//	//----------------------------- APP 异常自救代码 END
}
