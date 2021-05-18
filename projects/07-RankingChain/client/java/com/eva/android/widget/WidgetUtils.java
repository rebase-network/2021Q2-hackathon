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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.Toast;

//import com.eva.android.widget.alert.AlertDialog;
import com.eva.epc.common.util.DoubleHelper;
import com.tang.R;

import java.util.List;
import java.util.Map;

//import net.dmkx.mobi1.R;
//import android.app.AlertDialog;
//import com.eva.android.RHolder;

/**
 * UI组件实用类.
 * 
 * @author Jack Jiang, 2011-08-20
 * @version 1.0
 */
public class WidgetUtils
{
	private final static String TAG = WidgetUtils.class.getSimpleName();
	
	public final static int TOAST_TYPE_NONE = 1;  //    1
	public final static int TOAST_TYPE_OK = 2;    //   10
	public final static int TOAST_TYPE_INFO = 4;  //  100
	public final static int TOAST_TYPE_WARN = 8;  // 1000
	public final static int TOAST_TYPE_ERROR = 16;//10000
	
	public static int toastTypeSurport = 
		TOAST_TYPE_NONE | TOAST_TYPE_OK | TOAST_TYPE_INFO | TOAST_TYPE_WARN | TOAST_TYPE_ERROR;

	/**
	 * 代码里设置View组件的高度。
	 *
	 * @param v 要设置的组件对象
	 * @param height 高度（单位：像素）
	 */
	public static void setHeight (View v, int height)
	{
		if (v.getLayoutParams() instanceof ViewGroup.LayoutParams)
		{
			ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
			layoutParams.height = height;
		}
	}

    /**
     * 代码里设置View组件的margin。
     *
     * @param v 要设置的组件对象
     * @param left 左距（单位：像素）
     * @param top 上距（单位：像素）
     * @param right 右距（单位：像素）
     * @param bottom 下距（单位：像素）
     */
    public static void setMargins (View v, int left, int top, int right, int bottom)
    {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)
        {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            v.requestLayout();
        }
    }
	
	public static void showWithDialog(Context context, String title, String msg)
	{
		new AlertDialog.Builder(context)
		.setTitle(title)  
		.setMessage(msg)
		.setNegativeButton(context.getResources().getString(R.string.general_ok), null)
		.show();  
	}

//	public static void setEditTextReadOnly(TextView view)
//	{
//		view.setTextColor(R.color.light_gray_for_text);   //设置只读时的文字颜色
//		if (view instanceof android.widget.EditText){
//			view.setCursorVisible(false);      //设置输入框中的光标不可见
//			view.setFocusable(false);           //无焦点
//			view.setFocusableInTouchMode(false);     //触摸时也得不到焦点
//		}
//	}

	/**
	 * <p>
	 * 将一个List<Map<String, Object>>样数据集中每一行的keyWillBeSum
	 * 指定单值进行合计，并返回合计结果.
	 * 
	 * <b>注：</b>本方法只对keyWillBeSum列是数字的情况下有意义！！
	 * </p>
	 * 
	 * @param datas 源数据集
	 * @param keyWillBeSum 目标列
	 */
	public static double sumColumn(List<Map<String, Object>> datas, String keyWillBeSum)
	{
		double sum = 0;
		//遍历该列的所有单元值并进行合计
		for(Map<String,Object> adapterData : datas)
			sum = DoubleHelper.add(sum, Double.valueOf(adapterData.get(keyWillBeSum).toString()));
		return sum;
	}

//	/**
//	 * 为View对象智能设置值.
//	 *
//	 * @param parentView 要进行值设定的View对象所在的父UI容器，因为需要通过它的方法 {@link View#findViewById(int)}来
//	 * 找到该View对象的实例引用（该实例应该是在诸如setContentView时被自动实例化的）
//	 * @param viewResId 要对其进行值设定的资源id
//	 * @param value 要设定的值，如果destView是TextView时则进行值设定时是调用value.toString()进行自动转换实现的
//	 * @see #setViewValue(View, Object)
//	 */
//	public static void setViewValue(View parentView,int viewResId,Object value)
//	{
//		setViewValue(parentView.findViewById(viewResId),value);
//	}
//	/**
//	 * <p>
//	 * 为View对象智能设置值.<br><br>
//	 *
//	 * 本方法目前只支持对TextView、Spinner及其所有直接和非直接子类的值设定——如果View对象是
//	 * TextView及其所有直接和非直接子类则对其调用.setText(value.toString())、如果View对象
//	 * 是Spinner及其所有直接和非直接子类则调用其Adapter的.setSelectedItem(..)进行值调定（
//	 * 前提是Spinner的Adapter对象必是RenderedSpinnerAdapter或其子类，因为它需要高台 用
//	 * RenderedSpinnerAdapter.setSelectedItem(..)进行值设定.）
//	 * </p>
//	 *
//	 * @param destView 要对其进行值设定的View对象
//	 * @param value 要设定的值，如果destView是TextView时则进行值设定时是调用value.toString()进行自动转换实现的
//	 */
//	public static void setViewValue(View destView,Object value)
//	{
//		//		View destView = findViewById(dataMode.getId());
//		if(destView instanceof TextView)//TextView及其所有直接和非直接子类
//			((TextView) destView).setText(String.valueOf(value));//value.toString());
//		else if(destView instanceof Spinner)
//		{
//			Adapter adp = ((Spinner) destView).getAdapter();
//			if(adp instanceof RenderedSpinnerAdapter)
//			{
//				RenderedSpinnerAdapter adapterList=(RenderedSpinnerAdapter)adp;
//				adapterList.setSelectedItem(value);
//			}
//			else
//			{
//				Log.w(TAG, "对Spinner对象进行自动设值时，要求它的Adapter必须是RenderedSpinnerAdapter或其子类，因为" +
//				"它需要高台 用RenderedSpinnerAdapter.setSelectedItem(..)进行值设定.");
//				return;
//			}
//		}
//		else
//			Log.w(TAG, "有一个View组件不支持，目前只支持对TextView、Spinner及其所有直接和非直接子类的值设定."+destView.getClass());
//	}

//	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 以下代码有待重构SSSSS
//	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 以下代码有待重构SSSSS
//	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 以下代码有待重构SSSSS
//	/**
//	 * 创建下拉框辅助类，简化创建下拉框的步骤
//	 * @param idnames
//	 * @param context
//	 * @param spinnerViewId
//	 * @return
//	 */
//	public static Spinner createRenderedSpinner(DataRender idnames,Context context,int spinnerViewId)
//	{
//		return createRenderedSpinner(idnames, context, spinnerViewId, true);
//	}
//	/**
//	 * 创建下拉框辅助类，简化创建下拉框的步骤 默认animate是true
//	 * @param idnames
//	 * @param context
//	 * @param spinnerViewId
//	 * @param event 初始化的时候是否触发监听器,false表示触发、true表示不触发
//	 * @return
//	 */
//	public static Spinner createRenderedSpinner(DataRender idnames,Context context,int spinnerViewId,boolean event)
//	{
//		Spinner spinner=(Spinner)((Activity)context).findViewById(spinnerViewId);
//		RenderedSpinnerAdapter adapter=new RenderedSpinnerAdapter(context,android.R.layout.simple_spinner_item,idnames,spinner);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinner.setAdapter(adapter);
//		if(!event)
//			spinner.setSelection(0,true);
//		return spinner;
//	}
//
	/**
	 * 异步加载render并建立Spinner的实用例.
	 *
	 * <p>
	 * 异步加载render：Android自2.3及以后版后，要求网络交互必需要放在异步线程里处理！
	 * <p>
	 * 在2.3版本以后Android加入了StrictMode类，而在3.0在网络上能感觉到有更加严格的限制，更多的查询API上的StrictMode.
	 *
	 * @author Jack Jiang, 2013-09-23
	 * @version 1.0
	 */
	public static abstract class RenderedSpinnerCreatorAsync extends AsyncTask<Object, Integer, DataRender>
	{
		private Context activity = null;
		private Spinner spinner = null;
		private boolean event = true;

		public RenderedSpinnerCreatorAsync(Activity activity, int spinnerViewId)
		{
			this(activity, spinnerViewId, true);
		}
		public RenderedSpinnerCreatorAsync(Activity activity, int spinnerViewId, boolean event)
		{
			this(activity, (Spinner)activity.findViewById(spinnerViewId), event);
		}
		public RenderedSpinnerCreatorAsync(Context activity, Spinner spinner)
		{
			this(activity, spinner, true);
		}
		public RenderedSpinnerCreatorAsync(Context activity, Spinner spinner, boolean event)
		{
			this.activity = activity;
			this.spinner = spinner;
			this.event = event;
		}

//		/**
//		 * 处理耗时数据前先显示进度条.
//		 */
//		@Override
//		protected void onPreExecute()
//		{
//		}

		/**
		 * 在后台执行查询功能列表描述数据（不包括今日天气信息）.
		 *
		 * @param parems 参数没有意义
		 * @return 查询结果，将传递给onPostExecute(..)方法
		 */
		@Override
		protected DataRender doInBackground(Object... parems)
		{
			return fetchDataRender();
		}

		/** 从网络加载render数据的实现方法 */
		protected abstract DataRender fetchDataRender();

		/**
		 * <p>
		 * 用于处理queryData返回结果，并取消进度条的显示.<br>
		 * 最终的数据结果处理将交由方法 onPostExecute(Object) 处理.
		 * </p>
		 *
		 * @param render
		 */
		@Override
		protected void onPostExecute(DataRender render)
		{
			RenderedSpinnerAdapter adapter=new RenderedSpinnerAdapter(activity
					, android.R.layout.simple_spinner_item, render, spinner);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			if(!event)
				spinner.setSelection(0,true);
		}
	}
//	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 以下代码有待重构EEEEE
//	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 以下代码有待重构EEEEE
//	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 以下代码有待重构EEEEE


	public static void areYouSure(Context context,
			Object infoText,final Action okAction)
	{
		areYouSure(context,infoText,"操作确认",okAction,null);
	}
	public static void areYouSure(Context context,
                                  Object infoText, final Action okAction, final Action cancelAction)
	{
		areYouSure(context,infoText,"操作确认",okAction,cancelAction);
	}
	public static void areYouSure(Context context,
			Object infoText,
			String title,
			final Action okAction,
			final Action cancelAction
	)
	{
		AlertDialog dlg = new AlertDialog.Builder(context)
		.setTitle(title)
		.setMessage(infoText+"")
		.setPositiveButton(context.getResources().getString(R.string.general_ok), new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which){
				if(okAction!=null)
					okAction.actionPerformed(dialog);
				dialog.dismiss();
			}
		})
		.setNegativeButton(context.getResources().getString(R.string.general_cancel), new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which){
				if(cancelAction!=null)
					cancelAction.actionPerformed(dialog);
				dialog.dismiss();
			}
		})
//		.setIcon(R.drawable.lib_comfirm)
		.create();
		dlg.setCanceledOnTouchOutside(false);
		dlg.show();
	}



	/**
	 * 显示一个Toast的实用方法（默认duration是  {@link Toast#LENGTH_SHORT} ）.
	 *
	 * @param context he context to use. Usually your Application or Activity object.
	 * @param text 必须是Integer(包括int原始类型)和CharSequence及各自子类的实例
	 * @return
	 * @see #showToast(Context, Object, ToastType)
	 */
	public static <T> Toast showToast(Context context, T text)
	{
		return showToast(context, text, ToastType.NONE);
	}
	/**
	 * 显示一个Toast的实用方法（默认duration是  {@link Toast#LENGTH_SHORT} ）.
	 *
	 * @param context he context to use. Usually your Application or Activity object.
	 * @param text 必须是Integer(包括int原始类型)和CharSequence及各自子类的实例
	 * @return
	 * @see #showToast(Context, Object, int, ToastType)
	 */
	public static <T> Toast showToast(Context context, T text,ToastType toastType)
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
	public static <T> Toast showToastLong(Context context, T text)
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
	public static <T> Toast showToastLong(Context context, T text,ToastType toastType)
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
	public static <T> Toast showToast(Context context, T text, int duration
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


		Toast toast = null;
		if(text instanceof CharSequence)
			toast = Toast.makeText(context, (CharSequence)text, duration);
		else if(text instanceof Integer)
			toast = Toast.makeText(context, ((Integer)text).intValue(), duration);
		else
			throw new IllegalArgumentException(
					"无效参数,text不是CharSequence和Integer及各自子类的实例,text.getClass="+text.getClass());

		if(toast!=null)
		{
			if(toastType != ToastType.NONE)
			{
				LinearLayout toastView = (LinearLayout) toast.getView();
				toastView.setOrientation(LinearLayout.HORIZONTAL);
				ImageView imageCodeProject = new ImageView(context);
				int imageResId = R.drawable.widget_toast_icon_info;

				switch(toastType)
				{
					case OK:
						imageResId = R.drawable.widget_toast_icon_ok;
						break;
					case INFO:
						imageResId = R.drawable.widget_toast_icon_info;
						break;
					case WARN:
						imageResId = R.drawable.widget_toast_icon_warn;
						break;
					case ERROR:
						imageResId = R.drawable.widget_toast_icon_error;
						break;
				}

				imageCodeProject.setImageResource(imageResId);
				toastView.addView(imageCodeProject, 0);

				// 增加衬距
				LayoutParams lp = (LayoutParams) imageCodeProject.getLayoutParams();
		        lp.setMargins(0, 0, 15, 0);
		        imageCodeProject.setLayoutParams(lp);
			}

			toast.show();
		}
		return toast;
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
