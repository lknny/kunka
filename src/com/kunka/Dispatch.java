package com.kunka;

public interface Dispatch<T> {

	/**
	 *  默认调度器调度能力，超过能力的部分，会被阻塞，等待调度
	 */
	final int defaultCapacity=1*1000;
	
	/**
	 *  默认调度器生命周期（秒），超过生命周期，调度器自动关闭
	 */
	final int defaultAliveTime=20;
	
	/**
	 *  添加待调度T
	 */
	public void add(T t);
	
	/**
	 *  移除待调度T
	 */
	public void remove(T t);
	/**
	 *  调度
	 */
	public void dispatch(T t);
	
	/**
	 *  调度器关闭接口，抽象实现Dispatcher中，会级联关闭相关执行器
	 */
	public void shutdown();
}
