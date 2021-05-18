package com.eva.android;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 */
public class AppManager {
	
	private final static String TAG = AppManager.class.getSimpleName();
	
	private static Stack<Activity> activityStack;
	private static AppManager instance;
	
	private AppManager(){}
	
	/**
	 * 单一实例
	 */
	public static AppManager getAppManager(){
		if(instance==null){
			instance=new AppManager();
		}
		return instance;
	}
	
	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity){
		if(activityStack==null){
			activityStack=new Stack<Activity>();
		}
		
		Log.d(TAG, "【AM+】activity"+activity.getClass().getCanonicalName()
				+"(hash="+activity.hashCode()+")正在被加入全局管理列表.");
		activityStack.add(activity);
	}
	
	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity(){
		Activity activity=activityStack.lastElement();
		return activity;
	}
	
	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void removeActivity(){
		Activity activity=activityStack.lastElement();
		removeActivity(activity);
	}
	
	/**
	 * 结束指定的Activity
	 */
	public void removeActivity(Activity activity){
		if(activity!=null){
			Log.d(TAG, "【AM-】finishActivity：activity" + activity.getClass().getCanonicalName()
					+"(hash="+activity.hashCode()+")正在从全局管理列表中移除.A");
			activityStack.remove(activity);
			
			// FIX: 注意此处不应该调用finish，因为当调用本remove方法时通常是Activity中
			//      自行finish时，如果此处再调用finish则会导致多次调用finish，那么将导致
			//      Ativity中有关finish的逻辑混乱。这也说明了国内开源代码的开发者水平并不高，这么明显的bug却被人传来传去！！！
//			if(activity != null)
//				activity.finish();
			activity=null;
		}
	}
	
	/**
	 * 结束指定类名的Activity
	 */
	public void removeActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				removeActivity(activity);
			}
		}
	}
	
	/**
	 * 结束所有Activity.
	 * 
	 * <p>
	 * 说明：通常情况下程序中正常的Activity都是会自已调用finish方法来结束自已的，
	 * 这种情况下就只需要该Activity从本manager中remove掉自已的句柄就可以了。
	 * 需要finishAll的场景一般是：程序正常退出了（那么就由本manager来finish所有余下的的未finish掉的activity）、
	 * 程序崩溃了（同样由本manager来finish所有余下的的未finish掉的activity）。
	 */
	public void finishAllActivity(){
		if(activityStack == null)
			return;

		for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
            	
            	Log.d(TAG, "【AM-】finishAllActivity：activity"
            			+ activityStack.get(i).getClass().getCanonicalName()
            			+"(hash="+activityStack.get(i).hashCode()+")正在从全局管理列表中移除.B");
            	
            	try{
            		activityStack.get(i).finish();
				}
				catch (Exception e){
					Log.w(TAG, "finishAllActivity时出错了，"+e.getMessage(), e);
				}
            }
	    }
		activityStack.clear();
	}
	
	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
//		try {
//			finishAllActivity();
//			ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//			activityMgr.restartPackage(context.getPackageName());
//			System.exit(0);
//		} catch (Exception e) {	}
		try
		{
			finishAllActivity(); // add by Jack Jiang 20180117
			
			int currentVersion = android.os.Build.VERSION.SDK_INT;  
			Log.d(TAG, "【APP正在退出】"+currentVersion);
	        if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {  
	            Intent startMain = new Intent(Intent.ACTION_MAIN);  
	            startMain.addCategory(Intent.CATEGORY_HOME);  
	            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
	            context.startActivity(startMain);  
	            System.exit(0);  
	        }
	        else
	        {// android2.1 以及以前版本退出app的主法
	            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);  
	            am.restartPackage(context.getPackageName());  
	        }  
		}
		catch (Exception e)
		{
			Log.w(TAG, e.getMessage(), e);
		}
	}
}