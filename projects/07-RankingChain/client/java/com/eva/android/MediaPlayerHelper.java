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
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.util.Log;

import java.io.File;

public class MediaPlayerHelper
{
	private final static String TAG = MediaPlayerHelper.class.getSimpleName();
	private static MediaPlayerHelper instance = null;
	
	private Context context = null;
	
	public static MediaPlayerHelper getInstance(Context context)
	{
		if(instance == null)
			instance = new MediaPlayerHelper(context);
		return instance;
	}
	
	private MediaPlayerHelper(Context context)
	{
		this.context = context;
	}
	
	public void play(int audio_res_id)
	{
		final MediaPlayer md = createMediaPlayer(audio_res_id);
		if(md != null)
			md.start();
	}
	public MediaPlayer createMediaPlayer(int audio_res_id)
	{
		try
		{
			final MediaPlayer md = MediaPlayer.create(context, audio_res_id);
			md.setOnCompletionListener(new OnCompletionListener(){
				@Override
				public void onCompletion(MediaPlayer mp)
				{
					md.release();
				}
			});
			return md;
		}
		catch (Exception e)
		{
			Log.e(TAG, e.getMessage(), e);
			return null;
		}
	}
	
	public void play(File f)
	{
		final MediaPlayer md = createMediaPlayer(f);
		if(md != null)
			md.start();
	}
	public MediaPlayer createMediaPlayer(File f)
	{
		try
		{
			final MediaPlayer md = MediaPlayer.create(context, Uri.fromFile(f));
			final long ss = System.currentTimeMillis();
			md.setOnCompletionListener(new OnCompletionListener(){
				@Override
				public void onCompletion(MediaPlayer mp)
				{
					System.out.println("!!OOOOOOOOOOOOOOOOOOOOOO?"+(System.currentTimeMillis()-ss)+"，音频："+md.getDuration());
					md.release();
				}
			});
			return md;
		}
		catch (Exception e)
		{
			Log.e(TAG, e.getMessage(), e);
			return null;
		}
	}
			
	public void play(Uri uri)
	{
		final MediaPlayer md = createMediaPlayer(uri);
		if(md != null)
			md.start();
	}
	public MediaPlayer createMediaPlayer(Uri uri)
	{
		try
		{
			final MediaPlayer md = MediaPlayer.create(context, uri);
			md.setOnCompletionListener(new OnCompletionListener(){
				@Override
				public void onCompletion(MediaPlayer mp)
				{
					md.release();
				}
			});
			return md;

		}
		catch (Exception e)
		{
			Log.e(TAG, e.getMessage(), e);
			return null;
		}
	}
}
