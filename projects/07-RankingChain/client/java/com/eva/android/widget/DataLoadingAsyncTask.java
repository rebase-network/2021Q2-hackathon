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
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.eva.android.HttpServiceFactory4A;
import com.eva.framework.dto.DataFromServer;
import com.tang.R;

//import com.eva.android.platf.core.AHttpServiceFactory;

/**
 * <p>
 * 一个拥有无穷进度提示的改进型AsyncTask实现类.<br>
 * 本类一般用于与服务端的数据交互等耗时操作时.<br><br>
 * 当然，本类继承了AsyncTask的所有泛型支持，仍然像父类一样强大.
 * 
 * <p>
 * <b>额外说明：</b>当本类的标识 {@link #showProgress}==false时即表示不显示UI提示，
 * 此时是可以执行于非主线程中的。
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 */
public abstract class DataLoadingAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result>
{
	private final static String TAG = DataLoadingAsyncTask.class.getSimpleName();
	
	protected Context context = null;
	/** 用于与服务端数据交互的等待过程的无穷进度对话框 */
	protected Dialog pd = null;
	/** 进度提示文本信息 */
	protected String showMessage = null;
	/** 是否显示进度提示：true表示提示，否则不提示 */
	protected boolean showProgress = true;

	public DataLoadingAsyncTask(Context context)
	{
		this(context, true);
	}
	public DataLoadingAsyncTask(Context context, boolean showProgress)
	{
//		this(context, "获取数据中,请稍候...");
		this(context, context.getResources().getString(R.string.general_data_loading));
		this.showProgress = showProgress;
	}
	public DataLoadingAsyncTask(Context context, String showMessage)
	{
		this.context = context;
		this.showMessage = showMessage;
	}

	public Activity getParentActivity()
	{
		if(this.context instanceof Activity)
			return (Activity)this.context;
		return null;
	}
	
	public boolean isShowProgress()
	{
		return showProgress;
	}

	/**
	 * 本方法的设置只在 {@link #onPreExecute()}被执行前有意义，即
	 * 在excute(..)被执行前设置才会起效。
	 * 
	 * @param showProgress
	 */
	public DataLoadingAsyncTask setShowProgress(boolean showProgress)
	{
		this.showProgress = showProgress;
		return this;
	}
	
	/**
	 * 子类可重写本方法从而实现自已的进度提示对话框实现.
	 * <p>
	 * 本方法默认返回的是 <code>ProgressDialog.show(context, "",showMessage, true, true)</code>.
	 * 
	 * @return 返回进度提示用的Dialog实例
	 */
	protected Dialog createProgressDialog()
	{
//		return ProgressDialog.show(context, "",showMessage, true, true);
		try
		{
			AProgressDialog pd = new AProgressDialog(context, showMessage);
			if(showProgress)
				pd.show();
			return pd;
		}
		catch (Exception e)
		{
			Log.w(TAG, e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 处理耗时数据前先显示进度条.
	 * 
	 * @see #createProgressDialog()
	 */
	@Override
	protected void onPreExecute()
	{
		pd = createProgressDialog();
	}
	
	/**
	 * <p>
	 * 用于处理queryData返回结果，并取消进度条的显示.<br>
	 * 最终的数据结果处理将交由方法 {@link #onPostExecuteImpl(Object)}处理.<br><br>
	 * 
	 * <b>重要提示：</b>当dfs是DataFromServer的实例时，将会取DataFromServer.getReturnValue作为
	 * onPostExecuteImpl(..)方法的参数，而非dfs本身.
	 * </p>
	 * 
	 * @param result doInBackground里传递过来的就是从服务端返回的处理结果对象
	 * @see #onPostExecuteImpl(Object)
	 */
	@Override
	protected void onPostExecute(Result result) 
	{
		//当result是DataFromServer实例时要进行特殊处理哦，更好地重用此应用场景下的部分代码
		if(result instanceof DataFromServer)
		{
			// 服务端数据成功返回
//			if (result != null	&& AHttpServiceFactory.isSuccess((DataFromServer)result, context))
			if(checkResultValid(result))
			{
				System.out.println("--成功");
				// 取消进度提示
				if(pd != null && pd.isShowing())
					pd.dismiss();

				// 成功后的调用：详细处理结果数据
				onPostExecuteImpl(((DataFromServer)result).getReturnValue());
			} 
			else
			{
				System.out.println("--失败");	
				if(pd != null)
					pd.dismiss();

				// 失败后的调用
				onPostExecuteFailImpl(((DataFromServer)result).getReturnValue());
			}
		}
		//否则直接把结果交由onPostExecuteImpl处理
		else
		{
			if(pd != null)
				pd.dismiss();
			//详细处理结果数据
			onPostExecuteImpl(result);
		}
	}
	
	/**
	 * 检查服务端接口返回值，并返回检查结果。
	 * <p>
	 * 本方法默认会调用 {@link HttpServiceFactory4A#isSuccess(DataFromServer, Context)}用于检查
	 * 服务端返回对象功能状态，并自动在该对象的sucess=false时给出一个提示信息。
	 * <p>如无需在sucess=false时给出提示信息请重写本方法，并用类似实现替换默认实现代码即可：
	 * <code>return (result != null && ((DataFromServer)result).isSucess());</code>
	 * 
	 * @param result 服务端接口处理完成后的返回结果对象封装对象，通常它会是{@link DataFromServer}的对象}
	 * @return true表示服务端成功处理完成本次接口请求，否则表示处理失败
	 */
	protected boolean checkResultValid(Result result)
	{
		// 服务端数据成功返回
		return (result != null && HttpServiceFactory4A.isSuccess((DataFromServer)result, context));
	}
	
	/**
	 * 本方法将在异步任务执行成功后被调用。
	 * <p>
	 * 本方法具体由 {@link #onPostExecute(Result)}调用，用于处理异步任务执行成功后，处理获得的数据结果.
	 * 
	 * @param result 由方法 {@link #onPostExecute(Result)}进行了初始处理后的数据结果对象，本对象的具体意义
	 * 由方法 {@link #doInBackground(Object...)}的返回值DataFromServer.getReturnValue()决定.
	 * <b>请注意：当且仅当其返回值是DataFromServer时，本参数值取的是DataFromServer.getRetrunValue().</b>
	 */
	protected abstract void onPostExecuteImpl(Object result);

	/**
	 * 本方法将在异步任务执行失败后被调用（本方法默认什么也不做）。
	 *
	 * @param result
	 * @since 6.0
	 */
	protected void onPostExecuteFailImpl(Object result) {};
}
