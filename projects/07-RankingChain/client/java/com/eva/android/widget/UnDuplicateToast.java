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
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tang.R;

//import com.eva.android.RHolder;

/**
 * 可重复显示的Toast实用类.
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 */
public class UnDuplicateToast
{
	public final static int TOAST_TYPE_NONE = 1;  // 2进制：00001
	public final static int TOAST_TYPE_OK = 2;    // 2进制：00010
	public final static int TOAST_TYPE_INFO = 4;  // 2进制：00100
	public final static int TOAST_TYPE_WARN = 8;  // 2进制：01000
	public final static int TOAST_TYPE_ERROR = 16;// 2进制：10000
	
	public static int toastTypeSurport = 
		TOAST_TYPE_NONE | TOAST_TYPE_OK | TOAST_TYPE_INFO | TOAST_TYPE_WARN | TOAST_TYPE_ERROR;
	
	private static Toast mToast = null;

//	public static void show(Activity context, String txt) 
//	{
//		if (mToast == null) 
//		{
//			mToast = Toast.makeText(context, txt, Toast.LENGTH_SHORT); 
//			
//			//
//			LinearLayout toastView = (LinearLayout) mToast.getView();
//			toastView.setOrientation(LinearLayout.HORIZONTAL);
//			ImageView imageCodeProject = new ImageView(context);
//			//
//			imageCodeProject.setImageResource(R.drawable.widget_toast_icon_error);
//			toastView.addView(imageCodeProject, 0);
//		}
//		else 
//		{
//			mToast.setText(txt);
//			mToast.setDuration(Toast.LENGTH_SHORT);  
//		}
//		View v = mToast.getView();
//		if(v != null)
//		{
//			v.setBackgroundResource(R.drawable.bg_common_toast);
//		}
//		mToast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100); 
//		mToast.show();
//	}
	
	
	//--------------------------------------------------------------------
	/**
	 * 显示一个Toast的实用方法（默认duration是  {@link Toast#LENGTH_SHORT} ）.
	 * 
	 * @param context he context to use. Usually your Application or Activity object.
	 * @param text 必须是Integer(包括int原始类型)和CharSequence及各自子类的实例
	 * @param duration
	 * @return
	 * @see #showToast(Context, Object, ToastType)
	 */
	public static Toast showToast(Context context, CharSequence text)
	{
		return showToast(context, text, ToastType.NONE);
	}
	/**
	 * 显示一个Toast的实用方法（默认duration是  {@link Toast#LENGTH_SHORT} ）.
	 * 
	 * @param context he context to use. Usually your Application or Activity object.
	 * @param text 必须是Integer(包括int原始类型)和CharSequence及各自子类的实例
	 * @param duration
	 * @return
	 * @see #showToast(Context, Object, int, ToastType)
	 */
	public static Toast showToast(Context context, CharSequence text,ToastType toastType)
	{
		return showToast(context, text, Toast.LENGTH_SHORT, toastType);
	}
	/**
	 * 显示一个Toast的实用方法（默认duration是  {@link Toast#LENGTH_LONG} ）..
	 * 
	 * @param context he context to use. Usually your Application or Activity object.
	 * @param text 必须是Integer(包括int原始类型)和CharSequence及各自子类的实例
	 * @return
	 * @see #showToastLong(Context, Object, ToastType)
	 */
	public static Toast showToastLong(Context context, CharSequence text)
	{
		return showToastLong(context, text, ToastType.NONE);
	}
	/**
	 * 显示一个Toast的实用方法（默认duration是  {@link Toast#LENGTH_LONG} ）..
	 * 
	 * @param context he context to use. Usually your Application or Activity object.
	 * @param text 必须是Integer(包括int原始类型)和CharSequence及各自子类的实例
	 * @return
	 * @see #showToast(Context, Object, int, ToastType)
	 */
	public static Toast showToastLong(Context context, CharSequence text,ToastType toastType)
	{
		return showToast(context, text, Toast.LENGTH_LONG, toastType);
	}
	/**
	 * <p>
	 * 显示一个Toast的实用方法.<br><br>
	 * 	<ul>
	 * 		<li>当text是CharSequence及其子类的对象时，本方法相当于调用{@link Toast#makeText(Context, CharSequence, int)}；</li>
	 * 		<li>当text是Integer及其子类的对象时，本方法相当于调用{@link Toast#makeText(Context, int, int)}；</li>
	 * 	</ui>
	 * </p>
	 * 
	 * @param context he context to use. Usually your Application or Activity object.
	 * @param text 必须是Integer(包括int原始类型)和CharSequence及各自子类的实例
	 * @param duration How long to display the message. Either LENGTH_SHORT or LENGTH_LONG
	 * @return
	 * @see Toast#makeText(Context, CharSequence, int)
	 * @see Toast#makeText(Context, int, int)
	 */
	public static Toast showToast(Context context, CharSequence text, int duration
			,ToastType toastType)
	{
		if(toastType == ToastType.NONE && ((toastTypeSurport & TOAST_TYPE_NONE) != TOAST_TYPE_NONE))
		{
			Log.v("showToast", ""+text);
			return Toast.makeText(context, ""+text, duration); // 此时返回的toast没有意义，只是为了保证在此判断逻辑下返回的Toast不为null而已
		}
		if(toastType == ToastType.OK && ((toastTypeSurport & TOAST_TYPE_OK) != TOAST_TYPE_OK))
		{
			Log.i("showToast", ""+text);
			return Toast.makeText(context, ""+text, duration); // 此时返回的toast没有意义，只是为了保证在此判断逻辑下返回的Toast不为null而已
		}
		if(toastType == ToastType.INFO && ((toastTypeSurport & TOAST_TYPE_INFO) != TOAST_TYPE_INFO))
		{
			Log.v("showToast", ""+text);
			return Toast.makeText(context, ""+text, duration); // 此时返回的toast没有意义，只是为了保证在此判断逻辑下返回的Toast不为null而已
		}
		if(toastType == ToastType.WARN && ((toastTypeSurport & TOAST_TYPE_WARN) != TOAST_TYPE_WARN))
		{
			Log.w("showToast", ""+text);
			return Toast.makeText(context, ""+text, duration); // 此时返回的toast没有意义，只是为了保证在此判断逻辑下返回的Toast不为null而已
		}
		if(toastType == ToastType.ERROR && ((toastTypeSurport & TOAST_TYPE_ERROR) != TOAST_TYPE_ERROR))
		{
			Log.v("showToast", ""+text);
			return Toast.makeText(context, ""+text, duration); // 此时返回的toast没有意义，只是为了保证在此判断逻辑下返回的Toast不为null而已
		}
		
		//
		if (mToast == null) 
		{
			mToast = Toast.makeText(context, text, duration); 
			
			// 设置icon
			LinearLayout toastView = (LinearLayout) mToast.getView();
			toastView.setOrientation(LinearLayout.HORIZONTAL);
			ImageView imageCodeProject = new ImageView(context);
			imageCodeProject.setImageResource(R.drawable.widget_unduplitoast_icon);
			toastView.addView(imageCodeProject, 0);
			
			//
			mToast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0); 
		}
		else 
			mToast.setText(text);
			mToast.setDuration(duration);  
		
		//
		LinearLayout toastView = (LinearLayout) mToast.getView();
		if(toastView != null)
		{
//			toastView.setOrientation(LinearLayout.HORIZONTAL);
//			ImageView imageCodeProject = new ImageView(context);
//			int imageResId = RHolder.getInstance().getEva$android$R().drawable("widget_toast_icon_info");
			int backgroundResourceResId = R.drawable.widget_unduplitoast_icon_none_bg;

			switch(toastType)
			{
				case NONE:
//					imageResId = -1;
					backgroundResourceResId = R.drawable.widget_unduplitoast_icon_none_bg;
					break;
				case OK:
//					imageResId =RHolder.getInstance().getEva$android$R().drawable("widget_toast_icon_ok");
					backgroundResourceResId = R.drawable.widget_unduplitoast_icon_ok_bg;
					break;
				case INFO:
//					imageResId = RHolder.getInstance().getEva$android$R().drawable("widget_toast_icon_info");
					backgroundResourceResId = R.drawable.widget_unduplitoast_icon_info_bg;
					break;
				case WARN:
//					imageResId = RHolder.getInstance().getEva$android$R().drawable("widget_toast_icon_warn");
					backgroundResourceResId = R.drawable.widget_unduplitoast_icon_warn_bg;
					break;
				case ERROR:
//					imageResId = RHolder.getInstance().getEva$android$R().drawable("widget_toast_icon_error");
					backgroundResourceResId = R.drawable.widget_unduplitoast_icon_error_bg;
					break;
			}

			// 设置icon
//			imageCodeProject.setImageResource(imageResId);
//			toastView.s(imageCodeProject, 0);
			
			// 设置背景
			toastView.setBackgroundResource(backgroundResourceResId);
		}
		
		mToast.show();

		return mToast;
	}

	/**
	 * 自定义Toast类型.
	 */
	public enum ToastType
	{
		/** 使用系统默认样式 */
		NONE,
		/** 在toast左右增加了Ok图标样式的toast */
		OK,
		/** 在toast左右增加了Info图标样式的toast */
		INFO,
		/** 在toast左右增加了Warn图标样式的toast */
		WARN,
		/** 在toast左右增加了Error图标样式的toast */
		ERROR
	}
}