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
import android.media.MediaRecorder;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * 录音包装实现类（目前默认只支持保存成.amr格式）.
 * 其内封装了一个计时器以便使用者读取录音时长（注：此时长并非绝对的音频录音时长，推
 * 荐仅用于ui显示）和刷新音量等使用.
 * <p>
 * 本类支持多次start和stop（正常运行而不会崩溃）哦，但请确保start后一定会stop 1次哦。
 * <p>
 * 本类中唯一设置录音文件保存路径的地方是方法{@link #start(String)}.
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 * @since 2.3
 */
public class VoiceRecorder
{
	private final static String TAG = VoiceRecorder.class.getSimpleName();

	/** 录音核心对象：本对象当且仅当 {@link #start()}方法被调用时被赋值 */
	private MediaRecorder recorder = null;
	private String voiceSavedPath;
	
	private Context context = null;
	private TimerDeamon timerDeamon = null;
	
	/** 是否录音中：true表示录音中，否则表示未处于录音状态. */
	private boolean recording = false;

	public VoiceRecorder(Context context)
	{
		this(context, null);
	}
	public VoiceRecorder(Context context, String path)
	{
		this.context = context;
		this.voiceSavedPath = path;
		init();
	}
	
	protected void init()
	{
		initTimerDeamon(context);
	}
	
	protected void initTimerDeamon(Context context)
	{
		this.timerDeamon = new TimerDeamon(context){
			/**
			 * 子类请重写此方法，实现计时时间变动的ui展现等.<br>
			 * 本方法默认什么也不做.
			 * 
			 * @param duration 当前总时长（毫秒数）
			 */
			@Override
			protected void timerChanged(long duration)
			{
				// 尝试捕获声音的音量
				volumnCaptured(getAmplitude());
				// 刷新录制时长（给调用者的ui哦）
				durationChanged(duration);
			}
		};
	}
	
	/**
	 * 设置（重置）录音核心模块.
	 * 
	 * @throws Exception
	 */
	private void setupMediaRecorder() throws Exception
	{
		recorder = new MediaRecorder();
		/** 
		 * mediaRecorder.setAudioSource设置声音来源。 
		 * MediaRecorder.AudioSource这个内部类详细的介绍了声音来源。 
		 * 该类中有许多音频来源，不过最主要使用的还是手机上的麦克风，MediaRecorder.AudioSource.MIC 
		 */ 
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		/** 
		 * mediaRecorder.setOutputFormat代表输出文件的格式。该语句必须在setAudioSource之后，在prepare之前。 
		 * OutputFormat内部类，定义了音频输出的格式，主要包含MPEG_4、THREE_GPP、RAW_AMR……等。 
		 */ 
		recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
		/** 
		 * mediaRecorder.setAddioEncoder()方法可以设置音频的编码 
		 * AudioEncoder内部类详细定义了两种编码：AudioEncoder.DEFAULT、AudioEncoder.AMR_NB 
		 */  
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		// 有的机器上设置此选项就会崩溃，估计是底层硬件不支持的原因，不设置系统使用默认，也就能提升兼容性了
//		recorder.setAudioChannels(AudioFormat.CHANNEL_CONFIGURATION_MONO);
		// 此项设不设置文件的大小貌似也不会有太大变化，就不设置了，省的出问题
//		recorder.setAudioSamplingRate(SAMPLE_RATE_IN_HZ);
		recorder.setOutputFile(voiceSavedPath);
	}
	
	/**
	 * 捕获了音量.
	 * <p>
	 * 子类可重写此方法实现音量变化时的ui刷新.
	 * 
	 * @param volumn 声音的振幅
	 * @see MediaRecorder#getMaxAmplitude()
	 */
	protected void volumnCaptured(double volumn)
	{
		// default do nothing
	}
	
	/**
	 * 录音时长通知.
	 * <p>
	 * 子类可重写此方法实现录音时间变化时的ui刷新.
	 * 
	 * @param duration
	 */
	protected void durationChanged(long duration)
	{
		// default do nothing
	}
	
	/**
	 * @see TimerDeamon#resetDuration()
	 */
	public void resetDuration()
	{
		timerDeamon.resetDuration();
	}

	/**
	 * 开始录音.
	 * <p>
	 * 本类支持多次start和stop（正常运行而不会崩溃）哦，但请确保start后一定会stop 1次哦。
	 * <p>
	 * 本方法是目前唯一指定录音文件保存路径的地方.
	 * 
	 * @param savedAbsolutePath 文件保存到的绝对路径，不为null则表示将要保存的声音文件保存地也本路径，否则不改变之前设定的路径
	 * @throws IOException
	 * @throws Exception
	 */
	public void start(String savedAbsolutePath) throws IOException, Exception
	{
		// 设定的保存路径不为空则将保存路径更新为最新，否则保持原来不变（如果原来已经设置了的话）
		if(savedAbsolutePath != null)
			this.voiceSavedPath = savedAbsolutePath;
		
		String state = android.os.Environment.getExternalStorageState();
		if (!state.equals(android.os.Environment.MEDIA_MOUNTED))
			throw new IOException("SD Card is not mounted,It is  " + state + "."); 
		
		// 如果未设定保存路径则抛出异常
		if(voiceSavedPath == null)
			throw new IOException("Path is null!"); 
		
		File directory = new File(voiceSavedPath).getParentFile();
		if (!directory.exists() && !directory.mkdirs()) 
			throw new IOException("Path to file could not be created"); 

//		/** 
//		 * mediaRecorder.setAudioSource设置声音来源。 
//		 * MediaRecorder.AudioSource这个内部类详细的介绍了声音来源。 
//		 * 该类中有许多音频来源，不过最主要使用的还是手机上的麦克风，MediaRecorder.AudioSource.MIC 
//		 */ 
//		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//		/** 
//		 * mediaRecorder.setOutputFormat代表输出文件的格式。该语句必须在setAudioSource之后，在prepare之前。 
//		 * OutputFormat内部类，定义了音频输出的格式，主要包含MPEG_4、THREE_GPP、RAW_AMR……等。 
//		 */ 
//		recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
//		/** 
//		 * mediaRecorder.setAddioEncoder()方法可以设置音频的编码 
//		 * AudioEncoder内部类详细定义了两种编码：AudioEncoder.DEFAULT、AudioEncoder.AMR_NB 
//		 */  
//		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//		// 有的机器上设置此选项就会崩溃，估计是底层硬件不支持的原因，不设置系统使用默认，也就能提升兼容性了
//		//		recorder.setAudioChannels(AudioFormat.CHANNEL_CONFIGURATION_MONO);
//		// 此项设不设置文件的大小貌似也不会有太大变化，就不设置了，省的出问题
//		//		recorder.setAudioSamplingRate(SAMPLE_RATE_IN_HZ);
//		recorder.setOutputFile(path);
//		//		recorder.prepare();
//		//		recorder.start();
		
		/** 
		 * 调用start开始录音之前，一定要调用prepare方法。 
		 */  
		try 
		{  
			// 设置或重置录音核心模块
			// 注意：经实地测试，stop方法调用后下次再调用start时，必须要保证重置（即调用本方法或类假方法）1次
			// 否则将出现Fatal signal 11 (SIGSEGV) 的底层库崩溃错误！所以在不完全new 本类的情况下，再start请调用本方法先！
			setupMediaRecorder();
			
			recorder.prepare();  
			recorder.start();  
			recording = true;
			// 启动计时器
			timerDeamon.start();
		} 
		catch (IllegalStateException e)
		{  
			throw e;
		} 
		catch (IOException e) 
		{  
			throw e;
		} 

		//-------------------------------------------------------------------------
		/*** 
		 * 此外，还有和MediaRecorder有关的几个参数与方法，我们一起来看一下： 
		 * sampleRateInHz :音频的采样频率，每秒钟能够采样的次数，采样率越高，音质越高。 
		 * 给出的实例是44100、22050、11025但不限于这几个参数。例如要采集低质量的音频就可以使用4000、8000等低采样率 
		 *  
		 * channelConfig ：声道设置：android支持双声道立体声和单声道。MONO单声道，STEREO立体声 
		 *  
		 * recorder.stop();停止录音 
		 * recorder.reset();  重置录音 ，会重置到setAudioSource这一步 
		 * recorder.release(); 解除对录音资源的占用 
		 */ 
	}

	/**
	 * 停止录音处理.
	 * 
	 * @return 停止时返回录音文件保存路径（本方法不保证返回路径所指定的声
	 * 音文件是否已正常保存，调用者需要判断此路径所描述文件是否存在、文件大小是否合适等）
	 * @throws IOException
	 */
	public String stop() throws IOException, IllegalStateException
	{
		recording = false;
		// 关闭计时器
		timerDeamon.stop();
		recorder.stop();
		// 经测试，此行代码存不存在stop后再start等等情况下没有影响，但网
		// 上老外说没有此行在stop时会报Fatal（libc）错误，还是先留着吧
		recorder.reset();    // set state to idle
		recorder.release();
		
		Log.d(TAG, "【SendVoice】录音停止了，保存路径是："+voiceSavedPath+"！");
		
		return voiceSavedPath;
	}

	/**
	 * 获得当前音量.
	 * 
	 * FIXME 经测试，在Motolora XT788（可能不只此手机）上getMaxAmplitude一直返回的是0，原因未知！
	 * 
	 * @return
	 */
	public double getAmplitude()
	{		
		if (recorder != null)
			return  (recorder.getMaxAmplitude());		
		else			
			return 0;	
	}
	
	/**
	 * 获得录音时长.
	 * 
	 * @return
	 */
	public long getDuration()
	{
		return timerDeamon.getDuration();
	}
	
	/**
	 * 是否处理正在录音中.
	 * 
	 * @return
	 */
	public boolean isRecording()
	{
		return recording;
	}
	
	//----------------------------------------------------------------------------------- inner class
	/**
	 * 计时器精灵类.
	 */
	private class TimerDeamon
	{
		// 计时更新时间间隔（单位：毫秒）
		public final static int KEEP_ALIVE_INTERVAL = 200;
		
		// 语音时长（单位：毫秒）：本时长非绝对的音频文件时长，仅推荐用于ui显示用
		protected long duration = 0;

		/** 当前线程是否正在执行中 */
		protected boolean running = false;
		protected Handler handler = null;
		protected Runnable runnable = null;
		protected Context context = null;

		public TimerDeamon(Context context)
		{
			this.context = context;
			
			//
			init();
		}
		
		/**
		 * 核心初始化方法.
		 * <p>
		 * 理论上本方法在实例的生命周期中只需要调用一次即可.
		 * 
		 * @see #runImpl()
		 */
		protected void init()
		{
			handler = new Handler();
			runnable = new Runnable(){
				@Override
				public void run()
				{
					runImpl();
				}
			};
		}
		/**
		 * 循环执行实现方法.
		 * <p>
		 * 子类可重写本方法实现自定义逻辑哦.
		 */
		protected void runImpl()
		{
			// 
			duration += KEEP_ALIVE_INTERVAL;
			
			//
			timerChanged(duration);
			
			// 开始下一个心跳循环
			handler.postDelayed(runnable, KEEP_ALIVE_INTERVAL);
		}
		
		/**
		 * 重置计时.
		 */
		public void resetDuration()
		{
			duration = 0;
		}
		
		/**
		 * 第3方法可随时调用的开始方法.
		 * <p>
		 * 本方法调用时将会确保之前的循环被重置，所以可放心调用.
		 * @see #stop()
		 * @see #resetDuration()
		 * @see Handler#postDelayed(Runnable, long)
		 */
		public void start()
		{
			//
			stop();
			
			//
			resetDuration();
			
			//
			handler.postDelayed(runnable, KEEP_ALIVE_INTERVAL);
			//
			running = true;
		}
		
		/**
		 * 第3方法可随时调用的停止方法.
		 * 
		 * @see Handler#removeCallbacks(Runnable)
		 */
		public void stop()
		{
			//
			handler.removeCallbacks(runnable);
			//
			running = false;
		}
		
		/**
		 * 循环是否正在运行中.
		 * 
		 * @return true表示正在，否则不在运行中
		 */
		public boolean isRunning()
		{
			return running;
		}
		
		/**
		 * 子类请重写此方法，实现计时时间变动的ui展现等.<br>
		 * 本方法默认什么也不做.
		 * 
		 * @param duration 当前总时长（毫秒数）
		 */
		protected void timerChanged(long duration)
		{
			// default do nothing
		}
		
		public long getDuration()
		{
			return duration;
		}
	}
}