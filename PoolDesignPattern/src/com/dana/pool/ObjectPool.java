package com.dana.pool;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * 
 * @author Praveen Rawat
 *
 * @param <T>
 */
public abstract class ObjectPool<T> {

	long deadTime;
	
	Hashtable<T,Long> lock, unLock;

	ObjectPool()
	{
		deadTime = 50000;// 50 seconds
		lock = new Hashtable<T, Long>();
		unLock = new Hashtable<T,Long>();
	}
	
	abstract T create();
	
	abstract boolean validate(T obj);
	
	abstract void dead(T obj);
	
	synchronized T takeOut()
	{
		long now = System.currentTimeMillis();
		T t;
		
		if(unLock.size()>0)
		{
			Enumeration<T> e = unLock.keys();
			
			while(e.hasMoreElements())
			{
				t= e.nextElement();
				
				if((now-unLock.get(t))>deadTime)
				{
					unLock.remove(t);
					dead(t);
					t=null;
				}
				else
				{
					if(validate(t))
					{
					unLock.remove(t);
					lock.put(t, now);
					return (t);
					}
					else
					{
						unLock.remove(t);
						dead(t);
						t=null;
					}
				}
			}
		}
		
		t= create();
		lock.put(t, now);
		
		return (t);
	}
	
	
	/**
	 * 
	 */
	synchronized void takeIn(T t)
	{
		lock.remove(t);
		unLock.put(t, System.currentTimeMillis());
		
	}
	
	
}
