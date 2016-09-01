package com.kunka;

public interface Dispatch<T> {
	final int defaultAliveTime=30*1000;
	final int defaultDispathIntervalTime=3*1000;
	final int defaultCapacity=1*1000;
	public void add(T t);
	public void remove(T t);
	public void dispatch(Executor<T> executor,T t);
	public void finish();
	public void shutdown();
}
