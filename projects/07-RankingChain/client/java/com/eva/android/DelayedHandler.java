package com.eva.android;

import android.os.Handler;

/**
 * 一个利用Handler实现的延迟动作实现类.
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @since 2.0
 */
public abstract class DelayedHandler extends Handler
{
	private int delayTimeWithMillisecond = 0;
	
	private Runnable runnable = new Runnable(){
		@Override
		public void run()
		{
			fireRun();
		}
	};

	/**
	 * 构造方法.
	 * 
	 * @param delayTimeWithMillisecond 延迟执行时间（单位：毫秒）
	 */
	public DelayedHandler(int delayTimeWithMillisecond)
	{
		this.delayTimeWithMillisecond = delayTimeWithMillisecond;
		start();
	}

	protected abstract void fireRun();
	
	public void start()
	{
		stop();
		this.postDelayed(runnable, this.delayTimeWithMillisecond);
	}

	public void stop()
	{
		this.removeCallbacks(runnable);
	}

	public int getDelayTimeWithMillisecond()
	{
		return delayTimeWithMillisecond;
	}
	public void setDelayTimeWithMillisecond(int delayTimeWithMillisecond)
	{
		this.delayTimeWithMillisecond = delayTimeWithMillisecond;
	}
}