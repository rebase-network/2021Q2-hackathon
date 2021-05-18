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

import android.content.Context;
import android.util.Log;

import com.eva.android.widget.WidgetUtils;
import com.eva.epc.common.util.CommonUtils;
import com.eva.epc.common.util.EException;
import com.eva.framework.dto.DataFromClient;
import com.eva.framework.dto.DataFromServer;

import java.util.HashMap;

//import com.eva.android.platf.corex.d.ServiceFactoryRoot4A;

/**
 * 客户端命名用的HTTP服务工厂类.<br>
 * <p>
 * 在不特殊指明的情况下，使用默认服务即可，在使服对应服务前必须保证已经添加了对应的服务实例到
 * 列表中（调用  {@link #addServices(String, String, String, boolean)}等）  ，之后直接调
 * 用 {@link #getService(String)}即可取得指定的服务实例、调用 {@link #getServices()}
 * 取得默认服务实例引用.<br><br>
 *
 * <b>特别注意：</b>现时HttpService的设计思路（cookie0作全局静态变量），允许多个servlet服务（MVC控制器）存在，但必须确保它们
 * 处于同一个WEB模块下（不允许连接到不同的WEB应用模块上）。
 * </p>
 *
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 * @see HttpService4A
 */
public abstract class HttpServiceFactory4A //extends ServiceFactoryRoot4A
{
	public final static String TAG = HttpServiceFactory4A.class.getSimpleName();
	
	/** 当网络请求处理失败时（或服务端处理不成功）的默认提示文本 */
	public static String defaultTipMsgIfFail = "Network is not stable, pls try again!";

	/** 默认服务存放于服务实例列表中的键名 */
	public static final String DEFAULT_SERVICE_NAME = "default_service";
	
	/** 所有服务实例引用列表 */
	protected HashMap<String,HttpService4A> serviceInstances = null;

	protected HashMap<String,HttpService4A> createServiceInstances()
	{
		return new HashMap<String,HttpService4A>();
	}
	protected HashMap<String,HttpService4A> getServiceInstances()
	{
		if(serviceInstances == null)
			serviceInstances = createServiceInstances();
		return serviceInstances;
	}

	/**
	 * 添加默认服务实例到列表中（如果默认实例已经存在则不允许覆盖列表中的同名服务实例）.
	 *
	 * @param servletRootURL 服务root URL，形如: http://127.0.0.1:8080/ （需要slash结尾） 
	 * @param servletName 服务名（servlet名称），形如: MyController （不需要slash开头）
	 * @param overWriteIfExists 如果要添加的服务已经添加到列表中了（据服务名称）
	 * 	，是重写还是抛出异常（不允许重写），true表示无条件用新服务实例覆盖已经存在服务实例，否则抛出异常（不允许覆盖）
	 * @see #addServices(String, String, boolean)
	 */
	public void addServices(String servletRootURL,String servletName)
	{
		addServices(servletRootURL,servletName,false);
	}
	/**
	 * 添加默认服务实例到列表中（如果默认实例已经存在，可据参数overWriteIfExists决定是否能覆盖之）.
	 *
	 * @param servletRootURL 服务root URL，形如: http://127.0.0.1:8080/ （需要slash结尾） 
	 * @param servletName 服务名（servlet名称），形如: MyController （不需要slash开头）
	 * @param overWriteIfExists 如果要添加的服务已经添加到列表中了（据服务名称）
	 * 	，是重写还是抛出异常（不允许重写），true表示无条件用新服务实例覆盖已经存在服务实例，否则抛出异常（不允许覆盖）
	 * @see #addServices(String, String, String, boolean)
	 * @see #DEFAULT_SERVICE_NAME
	 */
	public void addServices(String servletRootURL,String servletName,boolean overWriteIfExists)
	{
		addServices(DEFAULT_SERVICE_NAME,servletRootURL,servletName,overWriteIfExists);
	}
	/**
	 * 添加一个服务实例到列表中（默认不允许覆盖列表中的同名服务实例）.
	 *
	 * @param serviceName 服务名
	 * @param servletRootURL 服务root URL，形如: http://127.0.0.1:8080/ （需要slash结尾） 
	 * @param servletName 服务名（servlet名称），形如: MyController （不需要slash开头）
	 * @see #addServices(String, String, String, boolean)
	 */
	public void addServices(String serviceName,String servletRootURL,String servletName)
	{
		addServices(serviceName,servletRootURL,servletName,false);
	}
	/**
	 * 添加一个服务实例到列表中.
	 *
	 * @param serviceName 服务名
	 * @param servletRootURL 服务root URL，形如: http://127.0.0.1:8080/ （需要slash结尾） 
	 * @param servletName 服务名（servlet名称），形如: MyController （不需要slash开头）
	 * @param overWriteIfExists 如果要添加的服务已经添加到列表中了（据服务名称）
	 * 	，是重写还是抛出异常（不允许重写），true表示无条件用新服务实例覆盖已经存在服务实例，否则抛出异常（不允许覆盖）
	 */
	public void addServices(String serviceName,String servletRootURL,String servletName
			,boolean overWriteIfExists)
	{
		if(CommonUtils.isStringEmpty(serviceName,true))
		{
			Log.e(TAG, "addServices时，服务名称不可为空，增加新服务到服务列表失败！");
			return;
		}

		if(getServiceInstances().containsKey(serviceName)&&!overWriteIfExists)
		{
			Log.e(TAG, "addServices时，服务"+serviceName+"已经存在，再新添加到它到服务列表失败！");
			return;
		}

		addServices(serviceName, new HttpService4A(serviceName,servletRootURL,servletName));
	}
	public void addServices(String serviceName, HttpService4A service)
	{
		getServiceInstances().put(serviceName, service);
	}

	/**
	 * 获取指定服务名的服务实例引用.
	 * @param serviceName 服务名
	 * @return  如果该服务实例已经被实例化并放入了列表中则返回它的引用，否则返回null
	 */
	public abstract HttpService4A getService(String serviceName);

	/**
	 * 获得默认的服务实例.
	 * @return 如果该默认服务实例已经被实例化并放入了列表中则返回它的引用，否则返回null
	 * @see #DEFAULT_SERVICE_NAME
	 */
	public HttpService4A getDefaultService()
	{
		return getService(DEFAULT_SERVICE_NAME);
	}
	
	
	/**
     * 操作是否成功的实用判断方法（默认needTipIfSucess被调置成false）.<br>
     * <p>
     * 它实际是判断返回对象DataFromServer.isSuccess()，如果返回true表示操作成功，否则表示
     * 失败，失败时可取出错误消息DataFromServer.getReturnValue()并给于提示.
     * </p>
     * 
     * @param dfs 服务器返回的数据对象
     * @param parentOfTipDialog 提示对话框的父组件（一般设置成发生操作的面板即可），用于出现提示消息时设置对象框的父组件
     * @param needTipIfSucess 处理成功时是否显示提示文本（以便告知用户处理成功了）,true表示提示
     * @see #isSuccess(DataFromServer, Component, boolean)
     */
    public static boolean isSuccess(DataFromServer dfs, Context context)
    {
    	return isSuccess(dfs, context, false);
    }
    /**
     * 操作是否成功的实用判断方法（如果needTipIfSucess被调置成true时，操作成功的提示文本使用默认值
     * ，如“本次操作成功完成”）.<br>
     * <p>
     * 它实际是判断返回对象DataFromServer.isSuccess()，如果返回true表示操作成功，否则表示
     * 失败，失败时可取出错误消息DataFromServer.getReturnValue()并给于提示.
     * </p>
     * 
     * @param dfs 服务器返回的数据对象
     * @param parentOfTipDialog 提示对话框的父组件（一般设置成发生操作的面板即可），用于出现提示消息时设置对象框的父组件
     * @param needTipIfSucess 处理成功时是否显示提示文本（以便告知用户处理成功了）,true表示提示
     * @see #isSuccess(DataFromServer, Component, boolean, String)
     */
    public static boolean isSuccess(DataFromServer dfs, Context context, boolean needTipIfSucess)
    {
    	return isSuccess(dfs, context, needTipIfSucess, "本次操作成功完成.");
    }
    /**
     * 操作是否成功的实用判断方法.<br>
     * <p>
     * 它实际是判断返回对象DataFromServer.isSuccess()，如果返回true表示操作成功，否则表示
     * 失败，失败时可取出错误消息DataFromServer.getReturnValue()并给于提示.
     * </p>
     * 
     * @param dfs 服务器返回的数据对象
     * @param parentOfTipDialog 提示对话框的父组件（一般设置成发生操作的面板即可），用于出现提示消息时设置对象框的父组件
     * @param needTipIfSucess 处理成功时是否显示提示文本（以便告知用户处理成功了）,true表示提示
     * @param tipMsgIfSucess 处理成功时的提示消息文本（本值只在needTipIfSucess被设置成true时有意义）
     * @see #isSuccess(DataFromServer, Context, boolean, String, boolean, String)
     * @see DataFromServer#isSuccess()
     * @see DataFromServer#getReturnValue()
     * @see com.eva.epc.core.ends.HttpPotal#process(HttpServletRequest ,HttpServletResponse)
     */
    public static boolean isSuccess(DataFromServer dfs, Context context
    		, boolean needTipIfSucess, String tipMsgIfSucess)
    {
    	return isSuccess(dfs, context, needTipIfSucess, "本次操作成功完成.", true, null);
    }
    /**
     * 操作是否成功的实用判断方法.<br>
     * <p>
     * 它实际是判断返回对象DataFromServer.isSuccess()，如果返回true表示操作成功，否则表示
     * 失败，失败时可取出错误消息DataFromServer.getReturnValue()并给于提示.
     * </p>
     * 
     * @param dfs 服务器返回的数据对象
     * @param parentOfTipDialog 提示对话框的父组件（一般设置成发生操作的面板即可），用于出现提示消息时设置对象框的父组件
     * @param needTipIfSucess 处理成功时是否显示提示文本（以便告知用户处理成功了）,true表示提示
     * @param tipMsgIfSucess 处理成功时的提示消息文本（本值只在needTipIfSucess被设置成true时有意义）
     * @param needTipIfFail 处理失败时是否显示提示文本（以便告知用户处理成功了）,true表示提示
     * @param tipMsgIfFail 处理失败时是否显示提示文本（以便告知用户处理成功了）,null表示使用默认提示
     * @see DataFromServer#isSuccess()
     * @see DataFromServer#getReturnValue()
     * @see com.eva.epc.core.ends.HttpPotal#process(HttpServletRequest ,HttpServletResponse)
     */
    public static boolean isSuccess(DataFromServer dfs, Context context
    		, boolean needTipIfSucess, String tipMsgIfSucess
    		, boolean needTipIfFail, String tipMsgIfFail)
    {
    	boolean ok = dfs.isSuccess();
    	//执行成功了并且要显示提示信息
    	if (ok && needTipIfSucess)
    		WidgetUtils.showToastLong(context, tipMsgIfSucess, WidgetUtils.ToastType.OK);
    	//显示错误信息
    	else if (!ok) 
    	{
    		Object ex = dfs.getReturnValue() != null ? dfs.getReturnValue() : "";
    		if(ex instanceof EException)
    		{
    			EException eee = (EException)ex;
//    			EOptionPane.showInfoDialog_ERROR(context, eee.getShortMessage(),ex);
    			if(needTipIfFail)
    			{
    				WidgetUtils.showToastLong(context
    						, (CommonUtils.isStringEmpty(tipMsgIfFail)?eee.getShortMessage():tipMsgIfFail)
    						, WidgetUtils.ToastType.WARN);
    			}
    			Log.e(TAG, eee.getShortMessage(),eee);
    		}
    		else
    		{
//    			EOptionPane.showInfoDialog_ERROR(context, ex.toString(),ex);
    			if(needTipIfFail)
    			{
    				WidgetUtils.showToastLong(context
    						, (CommonUtils.isStringEmpty(tipMsgIfFail)?
    								(defaultTipMsgIfFail == null ? ex.toString():defaultTipMsgIfFail)
    								:tipMsgIfFail)
    						, WidgetUtils.ToastType.WARN);
    			}
    			Log.e(TAG, ex.toString(),ex instanceof Exception?(Exception)ex:null);
    		}
    	}
    	return ok;
    }
    

	/**
	 * 连接服务器，否则无法连接到数据库
	 * 
	 * @param action_id 处理Ide
	 * @param nw 新数据
	 * @param old 旧数据
	 * @return
	 */
    public static DataFromServer sendObjToServer(HttpService4A httpService, int processorId, int jobDispatchId, int action_id, Object nw,
                                                 Object old)
	{
    	if(httpService == null)
    		return new DataFromServer().setSuccess(false);
		DataFromClient dataFromClient = DataFromClient.n();
		dataFromClient.setProcessorId(processorId);		
		dataFromClient.setJobDispatchId(jobDispatchId);
		dataFromClient.setActionId(action_id);
		dataFromClient.setNewData(nw);
		dataFromClient.setOldData(old);
		
//		return HttpServiceFactory4A.getDefaultService().sendObjToServer(dataFromClient);
		return httpService.sendObjToServer(dataFromClient);
	}
}
