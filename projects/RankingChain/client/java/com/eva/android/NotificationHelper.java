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

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

/**
 * Notification创建实用类。
 *
 * @author Jack Jiang
 * @since 1.0
 * @revision 20190508 by JackJiang：已解决Android高版本中的Notification兼容性问题
 */
public class NotificationHelper
{
    private final static String TAG = NotificationHelper.class.getSimpleName();

	public static final  String CHANNEL_ID          = "default_1";
	public static final String CHANNEL_NAME        = "Default Channel";
//	public static final String CHANNEL_DESCRIPTION = "this is default channel!";

    /**
     * 显示一个Notification。
     *
     * @param notification_uniqe_ident_id
     * @param context
     * @param destActivityIntent
     * @param icon_res_id
     * @param tickerText
     * @param infoContentTitle
     * @param infoContentText
     * @param cancelIfExist true表示如果该notification_uniqe_ident_id标识的Notivication存在的话
     * 		直接更新，否则先退出(清除)原先的Notivication再发一个新的。如果不退出原有的Notivication
     * 		则收到同一个Notive之后就不会有响铃等提醒（只是更新通知时间而已）
     * @param useDefaultNotificationSound
     * @param silent
     * @return
     */
	public static NotificationManager addNotificaction(int notification_uniqe_ident_id
			, Context context
			, Intent destActivityIntent
			, int icon_res_id
			, String tickerText, String infoContentTitle, String infoContentText
			, boolean cancelIfExist
			, boolean useDefaultNotificationSound, boolean silent) 
	{
		NotificationManager manager = NotificationCreator.getNotificationManager(context);
		
		// 只有在声音模式打开时才会真正的给个系统提示（否则会有系统震动、声音等），否则无法实现真正的静音哦！
		if(!silent)
		{
			if(cancelIfExist)
				manager.cancel(notification_uniqe_ident_id);

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context, 0, destActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);//PendingIntent.FLAG_ONE_SHOT);

            // 创建一个Notification
			Notification notification = NotificationCreator.createNotification(context
					, pendingIntent, infoContentTitle, infoContentText, icon_res_id);

			// 当当前的notification被放到状态栏上的时候，提示内容
			notification.tickerText = tickerText;
			// 点击后自动退出
			notification.flags = Notification.FLAG_AUTO_CANCEL;

			if(useDefaultNotificationSound)
				// 添加声音提示
				notification.defaults = Notification.DEFAULT_ALL;
			
			manager.notify(notification_uniqe_ident_id, notification);
		}
		
		return manager;
	}

	/**
	 * 兼容的方式获取Notification对象的实用类。
	 * <p>
	 * 因setLatestEventInfo在 API11(Android 3.1)以及版本中已被deprecated了，
	 * 且在 API23(Android6.0）中彻底废弃了该方法，所以为了工程能更好地兼容高、低版本，
	 * 我们需要提供一个封装的方法，本实用类就是解决问题的。
	 * </p>
	 *
	 * <p>
	 * RainbowChat v4.5后，已使用 NotificationCompat 来解决高低Android版本中Notification的兼容性问题了。
	 * </p>
	 *
	 * @author Jack Jiang
	 * @since 4.3
	 */
	public static class NotificationCreator
	{
		public static NotificationManager getNotificationManager(Context context)
		{
			NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

			// 以下代码，解决在Android 8及以上代码中，无法正常显示Notification或报"Bad notification for startForeground"等问题
			NotificationChannel notificationChannel = null;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			{
				notificationChannel = new NotificationChannel(NotificationHelper.CHANNEL_ID
						, NotificationHelper.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
				notificationChannel.enableLights(true);

				notificationChannel.setLightColor(Color.RED);
				notificationChannel.setShowBadge(true);
				notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

				manager.createNotificationChannel(notificationChannel);
			}

			return manager;
		}

		/**
		 * 兼容地方法创建Notification方法。
		 *
		 * @param context
		 * @param pendingIntent
		 * @param title
		 * @param text
		 * @param iconId
		 * @return
		 */
		public static Notification createNotification(Context context, PendingIntent pendingIntent
				, String title, String text, int iconId)
		{
			// 创建一个Notification Builder，使用NotificationCompat可以更好的兼容Android各系统版本，
			// 有关Android Notitication的兼容性、详细设置等，参见：https://www.cnblogs.com/travellife/p/Android-Notification-xiang-jie.html
			NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
					.setContentTitle(title)
					.setContentText(text)
					.setContentIntent(pendingIntent)
					// 设置显示在手机最上边的状态栏的图标
					.setSmallIcon(iconId);

			// 创建一个Notification
			Notification notification = builder.build();

			return notification;
		}
	}
}
