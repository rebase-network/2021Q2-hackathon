//package com.tang.util;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.util.Log;
//
//import com.tang.data_entity.FilmEvaluateDataToSave;
//import com.tang.shangxingke.MusicApplication;
//import com.tang.shangxingke.logic.launch.loginimpl.LoginInfoToSave;
//import com.x52im.rainbowchat.http.logic.dto.RosterElementEntity;
//
///**
// * Preferences辅助类.
// *
// * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
// * @since 2.5
// */
//public class PreferencesToolkits
//{
//	private final static String TAG = PreferencesToolkits.class.getSimpleName();
//
//	/** 存储用户最近登陆用户名的key标识常量（本Shared Preferences目前只在LoginActivity内有效果哦，
//	 * 使用MODE_PRIVATE是为了只能被本APP访问（不然跟其它APP因key问题冲突了哦）） */
//	public final static String SHARED_PREFERENCES_KEY_LOGIN$NAME = "__app_l_n__";
//
//	/**
//	 * 存储常量的Shared Preferences标识常量（根据Android的Shared Preferences原理，如果不指名名字，则
//	 * 用的是对应Activity的包名加类名作为名称，那么其它activity就很难取到罗）  */
//	public final static String SHARED_PREFERENCES_SHAREDPREFERENCES = "__sharedpreferences__";
//
//
//	public static SharedPreferences getAppDefaultSharedPreferences(Context context, boolean canWrite)
//	{
//		return context.getSharedPreferences(
//				SHARED_PREFERENCES_SHAREDPREFERENCES
////              , canWrite?Context.MODE_WORLD_WRITEABLE:Context.MODE_WORLD_READABLE
//                , Context.MODE_PRIVATE // 20180612：因android 7.0上已不建议或有些厂商已不支持MODE_WORLD_READABLE这种，所以用MODE_PRIVATE替代
//        );
//	}
//
//
//
//
//
//
//
////	/**
////	 * 通用开关量获取方法
////	 *
////	 * @param key 要获取的key
////	 * @return true or fa;se
////	 */
////	public static boolean is(Context context, String key, boolean defaultValue)
////	{
////		SharedPreferences nameSetting = getAppDefaultSharedPreferences(context, false);
////		return nameSetting.getBoolean(getLocalUserUid()+key, defaultValue);
////	}
////
////	/**
////	 * 通用String值获取方法。
////	 *
////	 * @param @param key 要获取的key
////	 * @return true表示打开群组消息提示（包括声音），否则关闭（不提示，但不影响数据处理）
////	 */
////	public static String get(Context context, String key, String defaultValue)
////	{
////		SharedPreferences nameSetting = getAppDefaultSharedPreferences(context, false);
////		return nameSetting.getString(getLocalUserUid()+key, defaultValue);
////	}
//
//	/**
//	 * 通用开关量设置方法.
//	 *
//	 * @param key 要设置的key
//	 * @param is true or false
//	 */
////	public static void set(Context activity, String key, boolean is)
////	{
////		SharedPreferences nameSetting = getAppDefaultSharedPreferences(activity, true);
////		SharedPreferences.Editor namePref = nameSetting.edit();
////		namePref.putBoolean(getLocalUserUid()+key, is);
////		namePref.commit();
////	}
//
//	/**
//	 * 通用String值设置方法。
//	 *
//	 * @param key 要设置的key
//	 * @param v value
//	 */
////	public static void set(Context activity, String key, String v)
////	{
////		SharedPreferences nameSetting = getAppDefaultSharedPreferences(activity, true);
////		SharedPreferences.Editor namePref = nameSetting.edit();
////		namePref.putString(getLocalUserUid()+key, v);
//////		namePref.putString(getLocalUserUid()+key, v);
////		namePref.commit();
////	}
////
////	public static void remove(Context context, String key)
////	{
////		SharedPreferences nameSetting = getAppDefaultSharedPreferences(context, true);
////		SharedPreferences.Editor namePref = nameSetting.edit();
////		namePref.remove(getLocalUserUid()+key);
////		namePref.commit();
////	}
//
////	private static String getLocalUserUid()
////	{
////		RosterElementEntity localUserInfo = MusicApplication.getInstance2().getIMClientManager().getLocalUserInfo();
////		if(localUserInfo != null)
////			return localUserInfo.getUser_uid();
////		return "";
////	}
//
//	//----------------------------------------------------------------------- for auto login START
//	/**
//	 * <p>
//	 * 返回最近陆的用户名.
//	 * 它是使用SharedPreferences机制进行存放的.
//	 * </p>
//	 *
//	 * @return
//	 * @see SharedPreferences#getString(String, String)
//	 */
////
//
//
//
//	//---------------------------------------------------保存第一次参评时提交的评定数据，为第二次自动化提交做准备。
//
//	/**
//	 * <p>
//	 * 返回最近陆的用户名.
//	 * 它是使用SharedPreferences机制进行存放的.
//	 * </p>
//	 *
//	 * @return
//	 * @see SharedPreferences#getString(String, String)
//	 */
////	public static FilmEvaluateDataToSave getDefaultLoginName(Context context)
////	{
////		//取出最近登陆过的用户名
////		SharedPreferences filmEvaluateSetting = getAppDefaultSharedPreferences(context, true);
////		String json = filmEvaluateSetting.getString(PreferencesToolkits.SHARED_PREFERENCES_KEY_LOGIN$NAME,null);
////		return json == null?null: LoginInfoToSave.fromJSON(json);
////	}
//
//	/**
//	 * 设置"自动登陆"开关量。
//	 *
//	 * @param context
//	 * @param autoLogin true表示允许自动登陆，否由不允许
//	 */
//	public static void setIsFinished(Context context, boolean isFinished)
//	{
//		try{
//			FilmEvaluateDataToSave li = getDefaultLoginName(context);
//			if(li != null)
//				li.setAutoLogin(autoLogin);
//
//			// 重新保存
//			saveDefaultLoginName(context, li);
//		}
//		catch(Exception e){
//			Log.w(TAG, e);
//		}
//	}
//
//	/**
//	 * 调用本方法实现对用户名的保存(以备下次登陆时无需再次输入).
//	 *
//	 * @param li
//	 * @see SharedPreferences.Editor#putString(String, String)
//	 */
//	public static void saveDefaultLoginName(Context context, LoginInfoToSave li)
//	{
//		SharedPreferences nameSetting = getAppDefaultSharedPreferences(context, true);
//		SharedPreferences.Editor namePref=nameSetting.edit();
//		namePref.putString(PreferencesToolkits.SHARED_PREFERENCES_KEY_LOGIN$NAME
//				, li==null?null:LoginInfoToSave.toJSON(li));
//		namePref.commit();
//	}
//
//	/**
//	 * 调用本方法实现删除之前保存过的最近登陆用户名.
//	 *
//	 * @see SharedPreferences.Editor#remove(String)
//	 */
//	public static void removeDefaultLoginName(Context context)
//	{
//		SharedPreferences nameSetting = getAppDefaultSharedPreferences(context, true);
//		SharedPreferences.Editor namePref = nameSetting.edit();
//		namePref.remove(PreferencesToolkits.SHARED_PREFERENCES_KEY_LOGIN$NAME);
//		namePref.commit();
//	}
//
//
//
//}
