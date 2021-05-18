package com.eva.android;// Copyright (C) 2020 即时通讯网(52im.net) & Jack Jiang.
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
//package com.eva.android;
//
//import java.io.File;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.net.Uri;
//
///**
// * android平台文件系统辅助类.
// *
// * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
// * @version 1.0
// */
//public class FileSystemHelper
//{
//	/**
//	 * <p>
//	 * 查看某文件.<br>
//	 *
//	 * 相当于windows平台下双击文件（然后自动用关联程序打开之）.
//	 * 如：当前文件是一个apk文件，则调用本方法的效果就等于安装这个apk程序.
//	 * </p>
//	 *
//	 * @param f
//	 * @see #getMIMEType(File)
//	 */
//	public static void viewFile(File f, Activity activity)
//	{
//		Intent intent = new Intent();
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		intent.setAction(android.content.Intent.ACTION_VIEW);
//		String type = getMIMEType(f);
//		intent.setDataAndType(OpenFileUtil.getUri(activity, f), type);
//		activity.startActivity(intent);
//	}
//
//	/**
//	 * 获取文件的MIME类型.
//	 *
//	 * @param f
//	 * @return
//	 */
//	public static String getMIMEType(File f)
//	{
//		String type = "";
//		String fName = f.getName();
//		String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
//		if (end.equals("m4a")
//				|| end.equals("mp3")
//				|| end.equals("mid")
//				|| end.equals("xmf")
//				|| end.equals("ogg")
//				|| end.equals("wav"))
//			type = "audio";
//		else if (end.equals("3gp")
//				|| end.equals("mp4"))
//			type = "video";
//		else if (end.equals("jpg")
//				|| end.equals("gif")
//				|| end.equals("png")
//				|| end.equals("jpeg")
//				|| end.equals("bmp"))
//			type = "image";
//		else if (end.equals("apk"))
//			type = "application/vnd.android.package-archive";
//		else
//			type = "*";
//
//		if (end.equals("apk"))
//			;
//		else
//			type += "/*";
//		return type;
//	}
//
//}
