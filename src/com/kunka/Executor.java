package com.kunka;

public interface Executor<T> {
	final int defaultCapacity=1*1000;
	final int defaultAliveTime=20;
	void execute(T t);
	void close();
	void add(T t);
	void remove(T t);
	
	/**
	 * 执行器收尾接口，抽象实现TaskExecutor中首先调用Task本身的taskFinished()，然后将Task状态设为100
	 */
	void finished(T t);
}
