package com.kunka;

public interface Executor<T> {
	final int defaultCapacity=1*1000;
	final int defaultAliveTime=20;
	/**
	 * 执行泛型任务T
	 */
	void execute(T t);
	/**
	 * 执行器关闭接口，手工调用，抽象实现TaskExecutor会级联关闭调度器
	 */
	void close();
	
	void add(T t);
	
	void remove(T t);
	
	/**
	 * 执行器收尾接口，抽象实现TaskExecutor中首先调用Task本身的taskFinished()，然后将Task状态设为100
	 */
	void finished(T t);
}
