package com.eva.android;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

//import com.eva.epc.core.dto.User;

/**
 * <p>
 * 本系统中的所有全局变量存放于本类中.<br>
 * 
 * 至于为什么不采用Java平台中惯用的且简单的全局static静态变量的方式的原因是：
 * 这些类可能会像Activity一样在某些时候被android系统强行销毁，所以在
 * 应用程序运行期间，DataStoreClass的值可能会丢失，或得到一些您不想要的值（比如理论上
 * 是已经被赋值过的变量而此时返回的却是null，这一般发生时程序时出现了exception异常的情况）。
 * </p>
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 */
// 为何要抽取此次到通用包里？因为LoginActivityRoot里需要用到Application里的东西，
// 为了尽可能多地重用或者隐藏LoginActivityRoot的方法，干脆就提取一个LoginActivityRoot里
// 需要的Apllication的东西到ApplicationRoot里，目的就是这样。
public class ApplicationRoot extends Application
{
    public void onCreate()
	{  
        super.onCreate();  
        
      //@Deprecated 此类自2013-12-10日被Jack Jiang建议停止使用，因为在Android 4.X系统中时因未知原因，
     // 程序崩溃时本类会导致程序界面卡死，直接卡在那崩也崩不了，估计这样的机制需要重新设计和验证，目前
     // KChat中使用的是google的分析实现的crash统计，以后不要轻易加入本类的类似功能，否则崩溃就卡死在那
     // ， 体验差极了！
//        //设定自定义的捕获异常处理器
//        CrashHandler crashHandler = CrashHandler.getInstance();  
//        crashHandler.init(getApplicationContext());  
    } 

	/**
	 * 获取App安装包信息
	 * 
	 * @return
	 */
	public PackageInfo getPackageInfo()
	{
		PackageInfo info = null;
		try
		{
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		}
		catch (NameNotFoundException e)
		{
//			e.printStackTrace(System.err);
			Log.e(ApplicationRoot.class.getSimpleName(), e.getMessage(), e);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}
}
