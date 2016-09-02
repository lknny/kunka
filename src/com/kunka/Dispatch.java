package com.kunka;

public interface Dispatch<T> {

	final int defaultDispathIntervalTime=3*1000;

	public void add(T t);
	public void remove(T t);
	public void dispatch(T t);
	public void shutdown();
}
