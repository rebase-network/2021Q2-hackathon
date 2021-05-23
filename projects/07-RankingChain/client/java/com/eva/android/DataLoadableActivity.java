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
package com.eva.android;

//import net.dmkx.mobi1.R;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.eva.android.widget.ActivityRoot;
import com.eva.android.widget.DataLoadingAsyncTask;
import com.eva.framework.dto.DataFromServer;
import com.tang.R;

//import com.eva.android.RHolder;
//import com.eva.android.platf.core.AHttpServiceFactory;

/**
 * 一个默认提供了无穷进度提示的可查询和显示数据的activity父类.
 * 
 * @author xzj
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.1
 */
public abstract class DataLoadableActivity extends ActivityRoot
{
	/** 
	 * 用于编辑或修改时的数据提交：对应的Processor ID
	 * @see com.eva.epc.core.ends.Controller */
	protected int processor_id = -1;
	/** 
	 * 用于编辑或修改时的数据提交：对应的JobDispather ID
	 * @see com.eva.epc.core.ends.Controller*/
	protected int job_dispatch_id;
	/** 
	 * 是否在activity被新建时刷新一次界面数据，即调用方
	 * 法 {@link #loadData(String...)}1次，true表示是 ，默认是true*/
	private boolean loadDataOnCreate = true;

	/**
	 * {@inheritDoc}
	 * @see QueryDataWorder
	 * @see #init(Bundle)
	 * @see #isLoadDataOnCreate()
	 * @see #loadData(String...)
	 */
	@Override public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		//核心初始化方法
		init(savedInstanceState);

		if(isLoadDataOnCreate())
			//是否在activity被新建时刷新一次界面数据
			loadData();
	}

	/**
	 * <p>
	 * 刷新界面数据的唯一公开方法.<br>
	 * 子类实现者如要实现自已的逻辑请重写本方法或其调用的方法.<br>
	 * 
	 * 本方法的默认实现是：在独立的处理线程中调用方法 {@link #queryData(String...)}从服务端查询数据
	 * ，成功后再把返回结果传给方法{@link #refreshToView(Object)}来处理.
	 * </p>
	 * 
	 * @param params 将要传给 {@link #queryData(String...)}的参数
	 */
	public void loadData(String... params)
	{
		loadData(true, params);
	}
	/**
	 * <p>
	 * 刷新界面数据的唯一公开方法.<br>
	 * 子类实现者如要实现自已的逻辑请重写本方法或其调用的方法.<br>
	 * 
	 * 本方法的默认实现是：在独立的处理线程中调用方法 {@link #queryData(String...)}从服务端查询数据
	 * ，成功后再把返回结果传给方法{@link #refreshToView(Object)}来处理.
	 * </p>
	 * 
	 * @param showProgress true表示读取网络数据时会显示进度提示，否则不显示
	 * @param params 将要传给 {@link #queryData(String...)}的参数
	 */
	public void loadData(boolean showProgress, String... params)
	{
		new QueryDataWorder(showProgress).execute(params);
	}

	/**
	 * <p>
	 * 典型应用场景下，这个方法在访问服务器线程之前被调用，如访问服务器所需数据是从
	 * 其他界面传过来的需要在这个方法中处理对于intent传过来的值，以及context都必须在
	 * 该方法中完成赋值与初始化。
	 * 
	 * 本类中默认是按照顺序执行几个方法来实现各个初始化任务的.<br>
	 * 执行这些方法的顺序是：<br>
	 * 1) 调用 {@link #initDataFromIntent()}实现可能的Intent中传过来的数据的解析和处理、<br>
	 * 2) 调用 {@link #initViews(Bundle)}实现所有UI部分的初始化、<br>
	 * 3) 调用 {@link #initListeners()}实现UI组事件处理监听器的初始化、<br>
	 * </p>
	 * @see #initDataFromIntent()
	 * @see #initViews(Bundle)
	 * @see #initListeners()
	 */
	protected void init(Bundle savedInstanceState)
	{
		initDataFromIntent();
		initViews(savedInstanceState);
		initListeners();
	}
	
	/**
	 * <p>
	 * 解析从Intent中传过来的可能的数据本方法中实现.
	 * 例如从上一个activity中传过来的数据，本方法被 {@link #init(Bundle)}调用。
	 * 
	 * <b>本方法默认什么也不做.</b>
	 * </p>
	 */
	protected void initDataFromIntent(){}
	
	/**
	 * <p>
	 * UI的初始化在此方法中实现.
	 * 例如设置context对象以及给需要用的组件初始化（例如使用fingviewbyid方法获取所需的对象）等。
	 * 本方法被 {@link #init(Bundle)}调用。
	 * 
	 * <b>本方法默认什么也不做.</b>
	 */
	protected void initViews(Bundle savedInstanceState){};
	
	/**
	 * <p>
	 * UI初始化完成后接着为各UI组件初始化事件监听器.
	 * 本方法被 {@link #init(Bundle)}调用。
	 * 
	 * <b>本方法默认什么也不做.</b>
	 * </p>
	 */
	protected void initListeners(){}
	
	/**
	 * 当  {@link #loadData(String...)}调用后，服务端处理完成时，
	 * 如果处理失败（可能是网络不好、或服务端处理出错等）则调用本方法.
	 * <p>本方法默认是调用 {@link #finish()}方法以便在处理不成功的情
	 * 况下退到上一个Activity.
	 * 
	 * @param result 处理完成后服务端返回的处理结果对象，通常会是{@link DataFromServer}对象
	 */
	protected void noSucessOnPostExecuteImpl(Object result)
	{
		DataLoadableActivity.this.finish();
	}
	
	//------------------------------------------------------------------------------------------------ START
	/**
	 * <p>
	 * 典型应用场景下，用于客户端查询数据的处理线程（提交请求到服务端并处理返回结果）。<br>
	 * 
	 * 处理与服务端进行数据通信（登陆验证）的多线程（多线程提高用户体验防止阻塞主线程）.<br>
	 * 注意：UI的任何操作不能放在多线程里，必须要放在handler里处理，跟swing的单线程原则一样。
	 * </p>
	 * 
	 * @see #loadData(String...)
	 * @see #queryData(String...)
	 */
	//此处的异步线程，2012-05-19前使用的是Thread和Handler，有点麻烦，现被改进了。2012-05-19 by js
	//以后类似的处理都优先使用此法，与SwingWorker基本相同.
	private class QueryDataWorder extends DataLoadingAsyncTask<String, Integer, DataFromServer>
	{
		public QueryDataWorder(boolean showProgress)
		{
			super(DataLoadableActivity.this);
			this.setShowProgress(showProgress);
		}
		
		/**
		 * @param params 外界传进来的参数
		 * @return 查询结果，将传递给onPostExecute(..)方法
		 */
		@Override
		protected DataFromServer doInBackground(String... params)
		{
			// 提交到服务端进行登陆验主并返回用户基本信息
			return queryData(params);
		}
		
		protected void onPostExecuteImpl(Object result)
		{
			// TODO 目前以下额外加的代码处理测试验证阶段，目前为止好像没什么效果！有待再观察！
			//** 由js2012-08-03日加上：目的是为了在从服务端读取数据出错的情况下
			//** 自动退回到上一个activity，而不是像以前一样还是进入了本activity，
			//** 但是此时显示数据获取是失败了，而导致界面数据不对，从而影响体验.
			//** 目前这样改进就是为了在出错了自动回退，给用户重试的机会，要不然进入
			//** 本界面后用户可能就有点慌了（有时候可能会影响操作，比如明细汇总界面里，
			//** 此时可能会导致数据丢失，而自动回退后，因上一个界面的数据还在，因而
			//** 可以明确地再来一次）
			if(result != null && result instanceof DataFromServer)
			{
				if(!((DataFromServer)result).isSuccess())
					noSucessOnPostExecuteImpl(result);
			}
			//** END
			
			// 成功后要做的事--
			refreshToView(result);
		}
	}
	//------------------------------------------------------------------------------------------------ END

	/**
	 * <p>
	 * 本方法将运行于{@link #loadData(String...)}中启动的独立线程中，目的是负责
	 * 收集或组织数据，组织好的数据将会被方法{@link #loadData(String...)}传给
	 *  {@link #refreshToView(Object)}处理以便显示在界面上.<br><br>
	 *  
	 * 典型应用场景下，该类负责连接服务器并且返回结果，但返回结果具体从何而来也可由子类自行决定。<br><br>
	 * 
	 * 典型应用场景下，子类必须实现该方法，并且必须在这个方法里面给ProcessorId，JobDispatchId
	 * ，action_id以及oldData（可以为null）和newData（可以为null）赋值 .<br><br>
	 * 
	 * <b>注意：</b>本方法运行于独立的线程中，严禁访问UI组件，否则android将会报错！
	 * </p>
	 * 
	 * @param params 由#loadData(String...)传过来的参数
	 * @return 典型情况下从服务器端返回的处理对象，子类也可自行封装一个DataFromServer对象
	 * ，但请保证 {@link DataFromServer#equals(Object)}==true，否则结果将不会被传给
	 * {@link #refreshToView(Object)}进行处理.
	 * @see #loadData(String...)
	 */
	abstract protected DataFromServer queryData(String... params);

	/**
	 * <p>
	 * 典型应用场景下，本方法负责把{@link #loadData(String...)}方法中运行的
	 * {@link #queryData(String...)}执行结果对象刷新到UI上。但子类也可自行实现自已的数据展现逻辑。<br><br>
	 * 
	 * 典型应用场景下，本方法用于全新刷新UI数据显示，也是唯一提供给子类进行UI数据设置的方法。
	 * </p>
	 * 
	 * @param dateToView 典型应用场景下，用来刷新界面数据显示的数据对象
	 * @see #loadData(String...)
	 */
	abstract protected void refreshToView(Object dateToView);

	/** 
	 * 是否在activity被新建时刷新一次界面数据，即调用方法 {@link #loadData(String...}1次.
	 * 
	 * @return true表示是 ，默认是true
	 */
	public boolean isLoadDataOnCreate()
	{
		return loadDataOnCreate;
	}
	/** 
	 * 设置是否在activity被新建时刷新一次界面数据，即调用方法 {@link #loadData(String...)}1次.
	 * 
	 * @param loadDataOnCreate true表示是 ，默认是true
	 */
	public void setLoadDataOnCreate(boolean loadDataOnCreate)
	{
		this.loadDataOnCreate = loadDataOnCreate;
	}

	/**
	 * 创建默认的菜单，有返回和退出功能.
	 * 
	 * @param menu
	 * @return
	 * @see #getOptionsMenuRes()
	 */
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(getOptionsMenuRes(), menu);
		return true;
	}
	/**
	 * 监听菜单按钮 实现返回和退出功能.
	 * 
	 * @see #fireOptionsItemSelected(int)
	 */
	public boolean onOptionsItemSelected(MenuItem item)
	{
		fireOptionsItemSelected(item.getItemId());
		return true;
	}
	/**
	 * 返回选项菜单对应的id.默认返回的是R.menu.common_data_loadable_activity_menu.
	 * 子类可重写本方法使用自定义的选项菜单实现.
	 * 
	 * @return res id
	 */
	protected int getOptionsMenuRes()
	{
		return R.menu.common_data_loadable_activity_menu;
//		return RHolder.getInstance().getEva$android$R().menu("common_data_loadable_activity_menu");
	}
	/**
	 * <p>
	 * 处理选项菜单item的选中事件.<br>
	 * 默认处理 返回和退出事件.
	 * 子类可重写本方法实现自已的菜单事件处理.
	 * </p>
	 * 
	 * @param itemId
	 */
	protected void fireOptionsItemSelected(int itemId)
	{
		if(itemId == R.id.newspaper_list_menu_back)
		{
			finish();
		}
//		else if(itemId == 
//			RHolder.getInstance().getEva$android$R().id("newspaper_list_menu_exit"))
//		{
//			LoginActivityRoot.doExit(DataLoadableActivity.this, );
//		}
//		switch (itemId)
//		{
//			case RHolder.getInstance().getEva$android$R().id("newspaper_list_menu_back"):
////			R.id.newspaper_list_menu_back:
//				finish();
//				break;
//			case RHolder.getInstance().getEva$android$R().id("newspaper_list_menu_exit"):
////			R.id.newspaper_list_menu_exit:
//				LoginActivity.doExit(DataLoadableActivity.this);
//				break;
//		}
	}

	//--------------------------------------------------------------------------------------------
	/** 
	 * 用于编辑或修改时的数据提交：设置维护操作所对应的业务类型
	 * 操作对应的默认业务类型在用户进入平台时已经被设定，如果业务类型
	 * 就是该默认值，则不再需要通过改方法再次设定。
	 * 
	 * @param processor_id 各个操作对应的业务类型
//	 * @see #submitToServer(int, Object, Object)
	 */
	public void setProcessorID(int processor_id)
	{
		this.processor_id = processor_id;
	}
	/** 
	 * 用于编辑或修改时的数据提交：设置维护操作所对应的操作类型
	 *
	 * @param job_dispatch_id 新的操作类型
//	 * @see #submitToServer(int, Object, Object)
	 */
	public void setJobDispatchID(int job_dispatch_id)
	{
		this.job_dispatch_id = job_dispatch_id;
	}
	/**
	 * 用于编辑或修改时的数据提交：获取对应的业务类型.
	 * 
	 * @return 获取的业务类型
//	 * @see #submitToServer(int, Object, Object)
	 */
	public int getProcessorID()
	{
		return processor_id;
	}
	/**
	 * 用于编辑或修改时的数据提交：获取对应的操作类型.
	 * 
	 * @return 操作类型
//	 * @see #submitToServer(int, Object, Object)
	 */
	public int getJobDispatchID()
	{
		return job_dispatch_id;
	}

//	/**
//	 * <p>
//	 * 用于编辑或修改时的数据提交：将操作（及数据）提交到服务端进行处理，并接收返回的处理结果.
//	 * 本方法是一个与服务端控制器进行效互的方便方法实现，绝大多数情况下，本方法不推荐也不需要被重写。
//	 * </p>
//	 * 
//	 * @param action_id 操作动作编码（也就是操作权限编码），它对应诸如：
//	 * 		新建、修改、删除等操作功能的编码，参见：{@link #defineActionsPermission}
//	 * @param Object newData 客端发送过来的本次修改新数据(可能为空，理论上与oldData不会同时空）
//	 * @param Object oldData 客端发送过来的本次修改前的老数据(可能为空，理论上与newData不会同时空）
//	 * @return 服务端处理完成后返回的数据对象（可据本对象获知处理是否成功执行，参见：DataFromServer）
//	 * @see AHttpServiceFactory.getDefaultService().sendObjToServer(DataFromClient)
//	 * @deprecated 2013-12-11日起，不推荐使用本方法实现了，使用 {@link HttpServiceFactory4A}代替！！！
//	 */
//	//** 保存数据这样的操作暂时不考虑放入多线程里（其实并不麻烦），主要考虑如果放入多线程里，就无法阻塞用户
//	//** 的操作（如在进度条提示时用户按了回退按钮呢），且返回值结果只能在独立的handle里处理，无法像现在一样
//	//** 调用方法后立马返回结果这样简单明了（否则就只能像现在loadData一样再开一个方法单独处理，那样太麻烦了！）
//	public DataFromServer submitToServer(int action_id,Object newData,Object oldData)
//	{
//		DataFromServer dfs = AHttpServiceFactory.getDefaultService().sendObjToServer(
//				DataFromClient.n()
//				.setProcessorId(this.processor_id)
//				.setJobDispatchId(this.job_dispatch_id)
//				.setActionId(action_id)
//				.setNewData(newData)
//				.setOldData(oldData)
//		);
//		return dfs;
//	}
}
