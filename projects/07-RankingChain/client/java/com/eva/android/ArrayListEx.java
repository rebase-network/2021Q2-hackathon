package com.eva.android;

import java.util.ArrayList;
import java.util.Collection;

/**
 * ArrayList的继承类。
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @since RainbowChat4
 * @param <E>
 */
public class ArrayListEx<E> extends ArrayList<E>
{
	/**
	 * {@inheritDoc}
	 */
	public ArrayListEx(int capacity) 
	{
		super(capacity);
	}
	/**
	 * {@inheritDoc}
	 */
	public ArrayListEx() 
	{
		super();
	}
	/**
	 * {@inheritDoc}
	 */
	public ArrayListEx(Collection<? extends E> collection) 
	{
		super(collection);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override 
	public void removeRange(int fromIndex, int toIndex) 
	{
		super.removeRange(fromIndex, toIndex);
	}

}
