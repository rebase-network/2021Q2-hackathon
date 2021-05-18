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
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.tang.BuildConfig;

import java.io.File;
import java.util.Locale;

/**
 * Android中如何使用代码打开各种类型的文件的实用类。
 *
 * @author Jack Jiang
 * @since 4.3
 */
public class OpenFileUtil {

    /**声明各种类型文件的dataType**/
    private static final String DATA_TYPE_ALL = "*/*";//未指定明确的文件类型，不能使用精确类型的工具打开，需要用户选择
    private static final String DATA_TYPE_APK = "application/vnd.android.package-archive";
    private static final String DATA_TYPE_VIDEO = "video/*";
    private static final String DATA_TYPE_AUDIO = "audio/*";
    private static final String DATA_TYPE_HTML = "text/html";
    private static final String DATA_TYPE_IMAGE = "image/*";
    private static final String DATA_TYPE_PPT = "application/vnd.ms-powerpoint";
    private static final String DATA_TYPE_EXCEL = "application/vnd.ms-excel";
    private static final String DATA_TYPE_WORD = "application/msword";
    private static final String DATA_TYPE_CHM = "application/x-chm";
    private static final String DATA_TYPE_TXT = "text/plain";
    private static final String DATA_TYPE_PDF = "application/pdf";


    /**
     * 打开文件
     * @param filePath 文件的全路径，包括到文件名
     */
    public static boolean openFile(Context mContext, String filePath) {
        boolean sucess = false;
        try
        {
            File file = new File(filePath);

            if (!file.exists()){
                //如果文件不存在
//                Toast.makeText(mContext, "打开失败，原因：文件已经被移动或者删除!", Toast.LENGTH_SHORT).show();
//                WidgetUtils.showToast(mContext, "文件不存在，请稍后再试", WidgetUtils.ToastType.WARN);
                return false;
            }

            /* 取得扩展名 */
            String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase(Locale.getDefault());
            if(end == null)
                end = "";

            /* 依扩展名的类型决定MimeType */
            Intent intent = null;
            if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
                intent =  generateVideoAudioIntent(mContext, filePath,DATA_TYPE_AUDIO);
            } else if (end.equals("3gp") || end.equals("mp4")) {
                intent = generateVideoAudioIntent(mContext, filePath,DATA_TYPE_VIDEO);
            } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
                intent = generateCommonIntent(mContext, filePath,DATA_TYPE_IMAGE);
            } else if (end.equals("apk")) {
                intent = generateCommonIntent(mContext, filePath,DATA_TYPE_APK);
            }else if (end.equals("html") || end.equals("htm")){
                intent = generateHtmlFileIntent(filePath);
            } else if (end.equals("ppt")) {
                intent = generateCommonIntent(mContext, filePath,DATA_TYPE_PPT);
            } else if (end.equals("xls")) {
                intent = generateCommonIntent(mContext, filePath,DATA_TYPE_EXCEL);
            } else if (end.equals("doc")) {
                intent = generateCommonIntent(mContext, filePath,DATA_TYPE_WORD);
            } else if (end.equals("pdf")) {
                intent = generateCommonIntent(mContext, filePath,DATA_TYPE_PDF);
            } else if (end.equals("chm")) {
                intent = generateCommonIntent(mContext, filePath,DATA_TYPE_CHM);
            } else if (end.equals("txt")) {
                intent = generateCommonIntent(mContext, filePath, DATA_TYPE_TXT);
            } else {
                intent = generateCommonIntent(mContext, filePath,DATA_TYPE_ALL);
            }

            mContext.startActivity(intent);
            sucess = true;
        }
        catch (Exception e)
        {
            Log.w(OpenFileUtil.class.getSimpleName(), e);
        }

        return sucess;
    }

//
//    // Android获取一个用于打开APK文件的intent
//    public static Intent getAllIntent(String param) {
//
//        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setAction(Intent.ACTION_VIEW);
//        Uri uri = Uri.fromFile(new File(param));
//        intent.setDataAndType(uri, "*/*");
//        return intent;
//    }
//
//    // Android获取一个用于打开APK文件的intent
//    public static Intent getApkFileIntent(String param) {
//
//        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setAction(Intent.ACTION_VIEW);
//        Uri uri = Uri.fromFile(new File(param));
//        intent.setDataAndType(uri, "application/vnd.android.package-archive");
//        return intent;
//    }
//
//    // Android获取一个用于打开VIDEO文件的intent
//    public static Intent getVideoFileIntent(String param) {
//
//        Intent intent = new Intent("android.intent.action.VIEW");
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("oneshot", 0);
//        intent.putExtra("configchange", 0);
//        Uri uri = Uri.fromFile(new File(param));
//        intent.setDataAndType(uri, "video/*");
//        return intent;
//    }

    /**
     * 产生打开视频或音频的Intent
     * @param filePath 文件路径
     * @param dataType 文件类型
     * @return
     */
    private static Intent generateVideoAudioIntent(Context mContext, String filePath, String dataType){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        File file = new File(filePath);
        intent.setDataAndType(getUri(mContext, file), dataType);

        // 添加这一句表示对目标应用临时授权该Uri所代表的文件（Android 7.0及以上版本必须！）
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);// 此条非必须

        return intent;
    }

    /**
     * 产生打开网页文件的Intent
     * @param filePath 文件路径
     * @return
     */
    private static Intent generateHtmlFileIntent(String filePath) {
        Uri uri = Uri.parse(filePath)
                .buildUpon()
                .encodedAuthority("com.android.htmlfileprovider")
                .scheme("content")
                .encodedPath(filePath)
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, DATA_TYPE_HTML);

        // 添加这一句表示对目标应用临时授权该Uri所代表的文件（Android 7.0及以上版本必须！）
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);// 此条非必须

        return intent;
    }

//    // Android获取一个用于打开图片文件的intent
//    public static Intent getImageFileIntent(String param) {
//
//        Intent intent = new Intent("android.intent.action.VIEW");
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Uri uri = Uri.fromFile(new File(param));
//        intent.setDataAndType(uri, "image/*");
//        return intent;
//    }
//
//    // Android获取一个用于打开PPT文件的intent
//    public static Intent getPptFileIntent(String param) {
//
//        Intent intent = new Intent("android.intent.action.VIEW");
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Uri uri = Uri.fromFile(new File(param));
//        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
//        return intent;
//    }
//
//    // Android获取一个用于打开Excel文件的intent
//    public static Intent getExcelFileIntent(String param) {
//
//        Intent intent = new Intent("android.intent.action.VIEW");
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Uri uri = Uri.fromFile(new File(param));
//        intent.setDataAndType(uri, "application/vnd.ms-excel");
//        return intent;
//    }
//
//    // Android获取一个用于打开Word文件的intent
//    public static Intent getWordFileIntent(String param) {
//
//        Intent intent = new Intent("android.intent.action.VIEW");
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Uri uri = Uri.fromFile(new File(param));
//        intent.setDataAndType(uri, "application/msword");
//        return intent;
//    }
//
//    // Android获取一个用于打开CHM文件的intent
//    public static Intent getChmFileIntent(String param) {
//
//        Intent intent = new Intent("android.intent.action.VIEW");
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Uri uri = Uri.fromFile(new File(param));
//        intent.setDataAndType(uri, "application/x-chm");
//        return intent;
//    }
//
//    // Android获取一个用于打开文本文件的intent
//    public static Intent getTextFileIntent(String param, boolean paramBoolean) {
//
//        Intent intent = new Intent("android.intent.action.VIEW");
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        if (paramBoolean) {
//            Uri uri1 = Uri.parse(param);
//            intent.setDataAndType(uri1, "text/plain");
//        } else {
//            Uri uri2 = Uri.fromFile(new File(param));
//            intent.setDataAndType(uri2, "text/plain");
//        }
//        return intent;
//    }
//
//    // Android获取一个用于打开PDF文件的intent
//    public static Intent getPdfFileIntent(String param) {
//
//        Intent intent = new Intent("android.intent.action.VIEW");
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Uri uri = Uri.fromFile(new File(param));
//        intent.setDataAndType(uri, "application/pdf");
//        return intent;
//    }

    /**
     * 产生除了视频、音频、网页文件外，打开其他类型文件的Intent
     * @param filePath 文件路径
     * @param dataType 文件类型
     * @return
     */
    private static Intent generateCommonIntent(Context mContext, String filePath, String dataType) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        File file = new File(filePath);
        Uri uri = getUri(mContext, file);
        intent.setDataAndType(uri, dataType);

        // 添加这一句表示对目标应用临时授权该Uri所代表的文件（Android 7.0及以上版本必须！）
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);// 此条非必须

        return intent;
    }

    /**
     * 获取对应文件的Uri.
     *
     * @param file 文件对象
     * @return
     */
    public static Uri getUri(Context mContext, File file) {
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //判断版本是否在7.0以上
            uri = FileProvider.getUriForFile(mContext.getApplicationContext()

                    // 【1】此行代码在android=7.x时，能工作的很好，但不能工作在android 8.x及以上版本
//                    , mContext.getPackageName() + ".fileprovider"

                    // 【2】此行代码在android 8.x及以上版本可正常工作，但需 AndroidManifest.xml中
                    //     的 FileProvider配置，以及 @xml/provider_paths.xml的配置内容一起才行。
                    , BuildConfig.APPLICATION_ID + ".provider"

                    , file);

//            //添加这一句表示对目标应用临时授权该Uri所代表的文件
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
}