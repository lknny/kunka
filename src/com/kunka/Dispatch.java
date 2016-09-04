package com.kunka;

public interface Dispatch<T> {

	final int defaultDispathIntervalTime=3*1000;

	public void add(T t);
	public void remove(T t);
	public void dispatch(T t);
	
	/**
	 *  调度器关闭接口，抽象实现Dispatcher中，会级联关闭相关执行器
	 */
	public void shutdown();
}
