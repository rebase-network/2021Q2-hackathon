package com.eva.android;

import java.util.ArrayList;
import java.util.Observer;

/**
 * 一个数据变动可通知观察者对象的ArrayList包装实现类.
 *
 *
// * <p>
// * 自rainbowchat2.5.1版（2014-04-19）起，观察者对象不推荐使用{@link obs}，推荐由{@link obsNew}
// * 替代之（虽然原{@link obs}任保留完整功用及兼容性）。{@link obsNew}作为观察者相比{@link obs}将能获得
// * 更为具体的通知信息（信息由类 {@link UpdateDataToObserver<T>}封装）。
// * <p>
// * <b><font color="#ff0000">特别注意：</font></b>当你设置的是obs时，与之对应的通知方法
// * 是 {@link #notifyObserver(Object)}，如果设置的是obeNew时，与之对应的是
// *  {@link #notifyObserverNew(UpdateDataToObserver)}，二者不会交叉调用，切记！
 * 
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @since RainbowChat1.0
 * @param <T> 数据结构中存放的对象，可以是任意{@link Object}的子类
 */
public class ArrayListObservable<T>
{
	private ArrayList<T> dataList = new ArrayList<T>();
//	/**
//	 * 数据变动观察者对象.
//	 * <p>
//	 * 本对象之前在kchat2.5版及更低版本上使用，自kchat2.5.1（以及后）版本里
//	 * 推荐使用 {@link obsNew}对象来实现观察者。
//	 * @deprecated 本对象自kchat2.5.1及以后版本里将推荐由 {@link obsNew}来替代，以便
//	 * 使观察者获得更多通知信息.
//	 * @since kchat1.0
//	 *  */
//	private Observer obs = null;
//
//	/**
//	 * 数据变动观察者对象.
//	 *
//	 * @since kchat2.5.1
//	 *  */
//	private Observer obsNew = null;

    private ArrayList<Observer> observers = new ArrayList<Observer>();


	public ArrayListObservable()
	{
		super();
	}
	
	public void set(int index, T cme)
	{
		this.set(index, cme, true);
	}
	/**
	 * 
	 * @param index
	 * @param cme
	 * @param notifyObserver true表示要通知观察者，此观察者通常用于刷新UI之用，所以可以将此参数理解为更新完数据模型后是否要刷新ui
	 */
	public void set(int index, T cme, boolean notifyObserver)
	{
		dataList.set(index, cme);
		if(notifyObserver)
		{
			// 通知观察者
//			notifyObserver(cme);
//			notifyObserverNew(new UpdateDataToObserver<T>(UpdateTypeToObserver.set, cme));
            notifyObservers(new UpdateDataToObserver<T>(UpdateTypeToObserver.set, cme));
		}
	}

	/**
	 * 在集合末尾加入一个元素。
	 * 
	 * @param cme
	 */
	public void add(T cme)
	{
		this.add(cme, true);
	}
	/**
	 * 在集合末尾加入一个元素。
	 * 
	 * @param cme
	 * @param notifyObserver true表示要通知观察者，此观察者通常用于刷新UI之用，所以可以将此参数理解为更新完数据模型后是否要刷新ui
	 */
	public void add(T cme, boolean notifyObserver)
	{
		dataList.add(cme);
		if(notifyObserver)
		{
			// 通知观察者
//			notifyObserver(cme);
//			notifyObserverNew(new UpdateDataToObserver<T>(UpdateTypeToObserver.add, cme));
            notifyObservers(new UpdateDataToObserver<T>(UpdateTypeToObserver.add, cme));
		}
	}
	
	/**
	 * 在指定索引处加入一个元素。
	 * 
	 * @param index
	 * @param cme
	 */
	public void add(int index, T cme)
	{
		this.add(index, cme, true);
	}
	/**
	 * 在指定索引处加入一个元素。
	 * 
	 * @param index
	 * @param cme
	 * @param notifyObserver true表示要通知观察者，此观察者通常用于刷新UI之用，所以可以将此参数理解为更新完数据模型后是否要刷新ui
	 */
	public void add(int index, T cme, boolean notifyObserver)
	{
		dataList.add(index, cme);
		if(notifyObserver)
		{
			// 通知观察者
//			notifyObserver(cme);
//			notifyObserverNew(new UpdateDataToObserver<T>(UpdateTypeToObserver.add, cme));
            notifyObservers(new UpdateDataToObserver<T>(UpdateTypeToObserver.add, cme));
		}
	}
	
	/**
	 * 
	 * @param index
	 * @param notifyObserver true表示要通知观察者，此观察者通常用于刷新UI之用，所以可以将此参数理解为更新完数据模型后是否要刷新ui
	 */
	public T remove(int index, boolean notifyObserver)
	{
		// theRemovedElement是该被删除地对象
		T theRemovedElement = dataList.remove(index);
		if(notifyObserver)
		{
			// 通知观察者
//			notifyObserver(theRemovedElement);
//			notifyObserverNew(new UpdateDataToObserver<T>(UpdateTypeToObserver.remove, theRemovedElement));
            notifyObservers(new UpdateDataToObserver<T>(UpdateTypeToObserver.remove, theRemovedElement));
		}
		return theRemovedElement;
	}

	public T get(int index)
	{
		return dataList.get(index);
	}
	
	public int indexOf(T o) 
	{
		return dataList.indexOf(o);
	}

	public ArrayList<T> getDataList()
	{
		return dataList;
	}
	
	/**
	 * 用新的集合来覆盖原dataList.
	 * 
	 * <p>注：本方法不是用新的ArrayList<T>对象来替换原dataList
	 * 对象，而是将新集后的所有元素放到被clear后的原dataList集合里，
	 * 也即是说：调用完本方法后，dataList还是原来的对象，只是集合元素改变了而
	 * 已，此举对将dataList引用作为ListView列表数据集的场景中有好处：浅拷贝使得数据随时
	 * 是被同步的（映射到ListView列表中）。
	 * 
	 * <p>注意：此方法将会多次通知观察者.
	 * 
	 * @param newDatas
	 * @see #add(Object)
	 */
	public void putDataList(ArrayList<T> newDatas, boolean notifyObserver)
	{
		dataList.clear();
		for(T t : newDatas)
		{
			add(t, notifyObserver);
		}
	}

//	/**
//	 * 本对象自kchat2.5.1及以后版本里将推荐由 {@link setObserverNew(Observer)}来替代，以便
//	 * 使观察者获得更多通知信息.
//	 * @param obs 数据变动将通知的观察者，通知将携带该次变动的对象T作为Observer.update(null, T)
//	 * 参数告之观察者对象
//	 */
//	public void setObserver(Observer obs)
//	{
//		this.obs = obs;
//	}
//	/**
//	 * 本对象自kchat2.5.1及以后版本里将推荐由 {@link notifyObserverNew(T)}来替代，以便
//	 * 使观察者获得更多通知信息.
//	 * @param cme 通知将携带该次变动的对象T作为Observer.update(null, T)
//	 * 参数告之观察者对象
//	 */
//	public void notifyObserver(T cme)
//	{
//		if(obs != null)
//			obs.update(null, cme);
//	}
//
//	/**
//	 * @param obsNew 数据变动将通知的观察者，通知将携带该次变动详细信息的对象
//	 * UpdateDataToObserver作为Observer.update(null, UpdateDataToObserver)
//	 * 参数告之观察者对象
//	 * @since kchat2.5.1
//	 */
//	public void setObserverNew(Observer obsNew)
//	{
//		this.obsNew = obsNew;
//	}
//	/**
//	 * @param udto 通知将携带该次变动详细信息的对象
//	 * UpdateDataToObserver作为Observer.update(null, UpdateDataToObserver)
//	 * 参数告之观察者对象
//	 * @since kchat2.5.1
//	 */
//	public void notifyObserverNew(UpdateDataToObserver<T> udto)
//	{
//		if(obsNew != null)
//			obsNew.update(null, udto);
//	}

    public void addObserver(Observer obs)
    {
        this.observers.add(obs);
    }

    public void removeObserver(Observer obs)
    {
//    [self.observers removeObject:obs];
        this.observers.remove(obs);
    }

    public void removeAllObservers()
    {
//    [self.observers removeAllObjects];
        this.observers.clear();;
    }

    public void notifyObservers(UpdateDataToObserver<T> udto)
    {
        for (Observer obs : this.observers)
        {
            if(obs != null)
                obs.update(null, udto);
        }
    }
	
	public void clear()
	{
		this.dataList.clear();
	}
	
	//----------------------------------------------------------------------------- inner class
	/**
	 * 数据变动时将作为变动详细信息提供对象传对观察者.
	 *
	 * @param <T>
	 * @since kchat2.5.1
	 */
	public static class UpdateDataToObserver<T>
	{
		private UpdateTypeToObserver updateType = null;
		private T extraData = null;
		
		public UpdateDataToObserver(UpdateTypeToObserver updateType
				, T extraData)
		{
			this.updateType = updateType;
			this.extraData = extraData;
		}
		
		public UpdateTypeToObserver getUpdateType()
		{
			return updateType;
		}
		public void setUpdateType(UpdateTypeToObserver updateType)
		{
			this.updateType = updateType;
		}
		public T getExtraData()
		{
			return extraData;
		}
		public void setExtraData(T extraData)
		{
			this.extraData = extraData;
		}
	}
	
	/**
	 * 数据变动类型。
	 */
	public enum UpdateTypeToObserver
	{
		/** 新加入了行 */
		add,
		/** 移除了行 */
		remove,
		/** 替换了行 */
		set,
        /** 未定义 */
        unknow
	}
}