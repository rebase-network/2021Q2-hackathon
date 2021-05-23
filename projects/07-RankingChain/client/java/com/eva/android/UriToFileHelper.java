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

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.eva.epc.common.util.ReflectHelper;

import java.io.File;

/**
 * 一个从Uri中解析真实SD卡地址的实用类.
 * <p>
 * 本类中Uri to file的方法兼容2014最新的Android4.4版.
 * <p>
 * Android 4.4中Uri取真实的SD卡地址是不一样的，否则将取不到.
 * <p>
 * 4.4之前Uri是：content://media/external/images/media/62，而到了
 * Android4.4时却是：content://com.android.providers.media.documents/document/image:62
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 * @since RainbowChat 2.0+
 * @see <a href="http://stackoverflow.com/questions/20067508/get-real-path-from-uri-android-kitkat-new-storage-access-framework">Get real path from URI, Android KitKat new storage access framework</a>
 * @see <a href="https://github.com/iPaulPro/aFileChooser">OpenSource project: aFileChooser</a>
 */
public class UriToFileHelper
{
	private final static String TAG = UriToFileHelper.class.getSimpleName();
	
	/**
	 * 返回指定Uri所指向文件的真正File句柄.
	 * 
	 * @param uri
	 * @return 成功则返回File对象，否则返回null
	 */
	public static File uri2File(Context context, Uri uri) 
	{
		File file = null;
		try
		{
			String img_path = UriToFileHelper.getPath(context, uri);
			System.out.println("++++++++++++++++++++++++++++++++++++++++ffffffffff="+img_path);
			
			// ## 以下取文件路径的方法在Android4.4上时取到的会是null
//			String[] proj = { MediaStore.Images.Media.DATA };
//			Cursor actualimagecursor = parentActivity.managedQuery(uri, proj, null, null, null);
//			int actual_image_column_index = actualimagecursor
//					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//			actualimagecursor.moveToFirst();
//			String img_path = actualimagecursor
//					.getString(actual_image_column_index);
			
			file = new File(img_path);
		}
		catch (Exception e)
		{
			Log.e(TAG, e.getMessage(), e);
		}
		return file;
	}
	
	/**
	 * Get a file path from a Uri. This will get the the path for Storage Access
	 * Framework Documents, as well as the _data field for the MediaStore and
	 * other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri The Uri to query.
	 * @author paulburke
	 */
	public static String getPath(final Context context, final Uri uri) 
	{
	    final boolean isKitKat = Build.VERSION.SDK_INT >= 19;//Build.VERSION_CODES.KITKAT;
	    
	    // 尝试反射SDK 19（Android4.4 Kitcat）上的类DocumentsContract
		Class documentsContractClass = ReflectHelper.getClass("android.provider.DocumentsContract");
	    // DocumentProvider
	    if (isKitKat && documentsContractClass != null && (
	    		// 反射 SDK 19（Android4.4 Kitcat）的方法：DocumentsContract.isDocumentUri(context, uri)
	    		((Boolean)(ReflectHelper.invokeMethod(
	    				documentsContractClass
	    				, documentsContractClass, "isDocumentUri"
	    				, new Class[]{Context.class, Uri.class}
	    				, new Object[]{context, uri}))).booleanValue()
	    		)
	    )
	    {
	        // ExternalStorageProvider
	        if (isExternalStorageDocument(uri))
	        {
	            final String docId = 
	            	// 反射 SDK 19（Android4.4 Kitcat）的方法：DocumentsContract.getDocumentId(uri);
	            	getDocumentId_DocumentsContract_SDK19(documentsContractClass, uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            if ("primary".equalsIgnoreCase(type)) 
	            {
	                return Environment.getExternalStorageDirectory() + "/" + split[1];
	            }
	        }
	        // DownloadsProvider
	        else if (isDownloadsDocument(uri)) 
	        {
	            final String id = 
	            	// 反射 SDK 19（Android4.4 Kitcat）的方法：DocumentsContract.getDocumentId(uri);
	            	getDocumentId_DocumentsContract_SDK19(documentsContractClass, uri);
	            final Uri contentUri = ContentUris.withAppendedId(
	                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

	            return getDataColumn(context, contentUri, null, null);
	        }
	        // MediaProvider
	        else if (isMediaDocument(uri)) 
	        {
	            final String docId = 
	            	// 反射 SDK 19（Android4.4 Kitcat）的方法：DocumentsContract.getDocumentId(uri);
	            	getDocumentId_DocumentsContract_SDK19(documentsContractClass, uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            Uri contentUri = null;
	            if ("image".equals(type)) 
	            {
	                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	            } 
	            else if ("video".equals(type)) 
	            {
	                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	            } 
	            else if ("audio".equals(type)) 
	            {
	                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	            }

	            final String selection = "_id=?";
	            final String[] selectionArgs = new String[] { split[1] };

	            return getDataColumn(context, contentUri, selection, selectionArgs);
	        }
	    }
	    // MediaStore (and general)
	    else if ("content".equalsIgnoreCase(uri.getScheme())) 
	    {
	        return getDataColumn(context, uri, null, null);
	    }
	    // File
	    else if ("file".equalsIgnoreCase(uri.getScheme())) 
	    {
	        return uri.getPath();
	    }

	    return null;
	}
	private static String getDocumentId_DocumentsContract_SDK19(Class documentsContractClass, Uri uri)
	{
		// 反射 SDK 19（Android4.4 Kitcat）的方法：DocumentsContract.getDocumentId(uri);
		return (String) ReflectHelper.invokeMethod(
        		documentsContractClass
				, documentsContractClass, "getDocumentId"
				, new Class[]{Uri.class}
				, new Object[]{uri});
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri The Uri to query.
	 * @param selection (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection,
	        String[] selectionArgs) 
	{
	    Cursor cursor = null;
	    final String column = "_data";
	    final String[] projection = { column };

	    try 
	    {
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
	        if (cursor != null && cursor.moveToFirst()) 
	        {
	            final int column_index = cursor.getColumnIndexOrThrow(column);
	            return cursor.getString(column_index);
	        }
	    } 
	    finally 
	    {
	        if (cursor != null)
	            cursor.close();
	    }
	    return null;
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri)
	{
	    return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) 
	{
	    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) 
	{
	    return "com.android.providers.media.documents".equals(uri.getAuthority());
	}
}
