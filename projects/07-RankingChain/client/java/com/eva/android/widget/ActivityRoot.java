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

//import net.dmkx.mobi1.R;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

//import com.eva.android.RHolder;

/**
 * <p>
 * 本类推荐用于应用系统的首页中，作为这些首页activity的根类使用.<br><br>
 * 
 * 1）关于back键：<br>
 * 本类实现了按back键相当于按Home键的效果，目的是使得在首页这样的地方按back
 * 键程序回到回台运行，而不是默认的finish掉进而关闭整个进程等.<br><br>
 * 
 * 2）关于自定义标题栏：<br>
 * 要开启自定义的通用标题栏（当前是一个蓝色自定义主题样式），你需要做几件事：
 * <ul>
 * <li>在你的activity所用的布局文件中加入自定义标题组件，
 * 		<pre>
 * 		{@code
 * 				<com.eva.android.widget.CustomeTitleBar
 *					android:layout_width="fill_parent" android:layout_height="wrap_content"
 *					android:id="@+id/main_login_titleBar">
 *				</com.eva.android.widget.CustomeTitleBar> 
 * 		}；
 * 		</pre>
 * </li>
 * <li>
 * 		<b>在setContentView(..)方法被调用前<a>设置 customeTitleBarResId</b>（它就是你已经加
 * 		入到你的layout中的标题栏的id），程序逻辑是只要customeTitleBarResId!=-1就意味着你开启了自定
 * 		义标题栏的使用，自定义标题栏的初始化工作将自动在重写过的setContentView(..)方法里处理；
 * </li>
 * <li>
 * 		上述完成后你就可以在setContentView(..)方法后的任何地方对该标题栏实例进行引用设置等（
 * 		比如把默认可见的back按钮设置成不可见this.getCustomeTitleBar().getLeftBackButton().setVisibility(View.GONE)）
 * 		，获得实例引用的方法是{@link #getCustomeTitleBar()}。
 * </li><br><br>
 * 
 * <b>自定义标题需要注意地方：</b><br>
 * a、自定义标题的开启当且仅当customeTitleBarResId!=-1时；<br>
 * b、customeTitleBarResId必须要在setContentView(..)方法前设置完成；<br>
 * c、开启自定义标题后，你无需额外在AndroidManifest.xml对该Activity设
 * 		置android:theme="@android:style/Theme.NoTitleBar"，程序将在代码中自动完成同样的事项
 * 		即把activity默认主题设成无默认标题栏（否则跟你的自定义标题栏将同时出现哦）；<br>
 * d、要对自定义标题栏组件进行的任何引用（或处理）必须要在setContentView(..)方法后完成，
 * 		否则它还没有被初始化哦；
 * e、开启自定义标题栏后，该Activity的标题文本必须要程序里调用setTitle(..)进行设定，
 * 		在AndroidManifest.xml设置android:label=""将不会起效（系统具体是怎么把label设置到activity的标题
 *		的有待进一步研究，但目前还不能支持像系统默认一样在代码里没有setTitle将会使用label作为title）；
 * f、开启自定义标题栏后，原activity中对标题的设置目前只有 {@link #setTitle(CharSequence)}和
 * 		{@link #setTitle(int)}是有效的，其它未提及的方法暂不支持（比如方法 {@link #setTitleColor(int)}
 * 		就暂时未被支持 ）.
 * </p>
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 * @see CustomeTitleBar
 */
public abstract class ActivityRoot extends BaseActivity
{
	public final static String EX1 = "__intent_extra_name1__";
	public final static String EX2 = "__intent_extra_name2__";
	public final static String EX3 = "__intent_extra_name3__";
	public final static String EX4 = "__intent_extra_name4__";
	public final static String EX5 = "__intent_extra_name5__";
	public final static String EX6 = "__intent_extra_name6__";
	public final static String EX7 = "__intent_extra_name7__";
	public final static String EX8 = "__intent_extra_name8__";
	public final static String EX9 = "__intent_extra_name9__";
	public final static String EX10 = "__intent_extra_name10__";
	public final static String EX11 = "__intent_extra_name11__";
	public final static String EX12 = "__intent_extra_name12__";
	public final static String EX13 = "__intent_extra_name13__";
	public final static String EX14 = "__intent_extra_name14__";
	public final static String EX15 = "__intent_extra_name15__";
	public final static String EX16 = "__intent_extra_name16__";
	public final static String EX17 = "__intent_extra_name17__";
	public final static String EX18 = "__intent_extra_name18__";
	public final static String EX19 = "__intent_extra_name19__";
	public final static String EX20 = "__intent_extra_name20__";
	
	/**
	 * 本参数为true表示按back键相当于按Home键的效果，
	 * 否则执行系统默认的back键效果——即finish掉当前activity.<br>
	 * 本参数默认为false.
	 */
	protected boolean goHomeOnBackPressed = false;
	
	//---------------------------------------------------------------------------- 自定义标题栏 START
	/** 自定义标题栏实例 */
	private CustomeTitleBar customeTitleBar = null;
	/**
	 * 本参数为-1时表示不启用自定义标题栏，否则表示启用自定义标题栏.
	 * 本参数是自定义的标题栏组件在该Activity布局中的id值.
	 *
	 * <font color="#ff0000"><b>重要说明：</b></font>本条件当且仅当在在 setContentView(..)之前被设置才有效，否则将不起效！
	 */
	protected int customeTitleBarResId = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 20190513前的说明：android要求设置默认标题的可见性必须在setContetView前完成，否则将报错！！
		// 20190513日的说明：使用最新AppCompat主题后，在一些手机比如摩托罗拉android 5.1的手机上，必须要放到
		//                  super.onCreate前调用，否则会报："requestFeature() must be called before adding content"
		unVisibleSystemTitleBar();

		super.onCreate(savedInstanceState);
	}
	
	/**
	 * 重写父类方法，实现自定义标题栏的相关设定.
	 *
	 * <p>
	 * <font color="#ff0000"><b>重要说明：</b></font>当使用自定义标题时，{@link #customeTitleBarResId}
	 * 必须在本方法调用前设定，否则报错！
	 * </p>
	 *
	 * {@inheritDoc}
	 */
	@Override
    public void setContentView(int layoutResID)
	{
//		//android要求设置默认标题的可见性必须在setContetView前完成，否则将报错！！
//		unVisibleSystemTitleBar();
    	super.setContentView(layoutResID);
    	//初始化自定义标题栏
    	initCustomeTitleBar();
    }
	
	/**
	 * 重写父类方法，实现自定义标题栏的相关设定.<br>
	 *
	 * <p>
	 * <font color="#ff0000"><b>重要说明：</b></font>当使用自定义标题时，{@link #customeTitleBarResId}
	 * 必须在本方法调用前设定，否则报错！
	 * </p>
	 *
	 * {@inheritDoc}
	 */
    @Override
    public void setContentView(View view) 
    {
//    	//android要求设置默认标题的可见性必须在setContetView前完成，否则将报错！！
//    	unVisibleSystemTitleBar();
    	super.setContentView(view);
    	//初始化自定义标题栏
    	initCustomeTitleBar();
    }
    
    /**
	 * 重写父类方法，实现自定义标题栏的相关设定.<br>
	 *
	 * <p>
	 * <font color="#ff0000"><b>重要说明：</b></font>当使用自定义标题时，{@link #customeTitleBarResId}
	 * 必须在本方法调用前设定，否则报错！
	 * </p>
	 *
	 * {@inheritDoc}
	 */
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) 
    {
//    	// android要求设置默认标题的可见性必须在setContetView前完成，否则将报错！！
//    	unVisibleSystemTitleBar();
    	super.setContentView(view, params);
    	//初始化自定义标题栏
    	initCustomeTitleBar();
    }
    
    /**
     * 为自定义标题栏设置系统默认标题栏的可见性.
     * 即当开启自定义标题栏时将隐藏系统标题栏.
     */
    protected void unVisibleSystemTitleBar()
    {
//    	if(customeTitleBarResId != -1)
    		//隐藏系统默认的标题栏（自定义标题栏就可以代替它很好地显示了，否则自定义2者将同时出现）  
    		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
    
    /**
     * 尝试初始化自定义标题栏.
     * 当且仅当customeTitleBarResId!=-1时才会初始化自定标题栏.
     */
    protected void initCustomeTitleBar()
    {
    	if(customeTitleBarResId != -1)
    		customeTitleBar = (CustomeTitleBar)this.findViewById(customeTitleBarResId);
    }
    /**
     * 获得自定义标题栏组件的实例引用.
     * 当且仅当 {@link #isUsedCustomeTitleBat()}返回true时
     * （即表示开启了自定义标题栏）才会获得有效引用，否则将返回null.
     * 
     * @return
     */
	public CustomeTitleBar getCustomeTitleBar()
	{
		return customeTitleBar;
	}
	
	/**
	 * 是否开启自定义标题栏的使用.
	 * 
	 * @return true表示已开启
	 */
	public boolean isUsedCustomeTitleBat()
	{
		return customeTitleBarResId != -1;
	}
	
	/**
	 * 重写父类方法以便支持自定义标题栏的设定.<br>
	 * {@inheritDoc}
	 */
	@Override
	public void setTitle(CharSequence title)
	{
		if(customeTitleBar != null)
			customeTitleBar.getTitleView().setText(title);
			
		super.setTitle(title);
	}
	/**
	 * 重写父类方法以便支持自定义标题栏的设定.<br>
	 * {@inheritDoc}
	 */
	@Override
	public void setTitle (int titleId)
	{
		if(customeTitleBar != null)
			customeTitleBar.getTitleView().setText(getText(titleId));
		super.setTitle(titleId);
	}
	//---------------------------------------------------------------------------- 自定义标题栏 END
	
	/**
	 * 重写父类方法，实现当 {@link #goHomeOnBackPressed}==true时按back键
	 * 相当于按Home键的效果.<br><br>
	 * 
	 * <b>注意：</b>当且仅当 {@link #goHomeOnBackPressed}==true才会启用该效果，
	 * 否则按back键执行系统默认的back键效果——即finish掉当前activity.<br><br>
	 * 
	 * 需要按back键相当于按Home键的效果的目的是使得在首页这样的地方按back
	 * 键程序回到回台运行，而不是默认的finish掉进而关闭整个进程.<br><br>
	 * 
	 * <b>说明：</b>android2.0以前，需要重写onKeyDown方法才能捕获back键，
	 * 2.0及以后版本只需要重写本方法即可了，简单直接.
	 * 
	 * @see com.eva.android.IntentFactory#createPrssedHomeKeyIntent()
	 * @see #startActivity(android.content.Intent)
	 */
	public void onBackPressed()
	{
		if(goHomeOnBackPressed)
		{
			//实现Home键效果
			//super.onBackPressed();这句话一定要注掉,不然又去调用默认的back处理方式了
			startActivity(com.eva.android.IntentFactory.createPrssedHomeKeyIntent());
		}
		else
			super.onBackPressed();
	}
	//	/** 
	//	 * 捕获back键.
	//	 */  
	//	@Override  
	//	public boolean onKeyDown(int keyCode, KeyEvent event) 
	//	{  
	//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) 
	//		{  
	//			LoginActivity.doExit(FunctionListActivity.this);
	//			return true;  
	//		}  
	//		return super.onKeyDown(keyCode, event);  
	//	} 
	
//	/**
//	 * 重写父类方法，自动在设置标题时加上系统名称.<br>
//	 * 形如：”XX移动通 - 销售单“.<br>
//	 * {@inheritDoc}
//	 */
//	public void setTitle(CharSequence title)
//	{
//		String titlePrefix = this.getResources().getString(R.string.app_name);
//		super.setTitle(titlePrefix+(title == null?"":" - "+title));
//	}
	
	/**
	 * 返回activity的统一标题题前缀.
	 * 
	 * @return 前缀前符串，本方法返回的应用程序名
	 * @see {@link #getResources()}.{@link #getString(int)}
	 */
	public String getTitlePrefix()
	{
//		return this.getResources().getString(
//				RHolder.getInstance().getEva$android$R().string("app_name"));
////				R.string.app_name);
		return "No title";
	}
}
