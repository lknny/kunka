package com.kunka;

public interface Executor<T> {

	/**
	 *  默认调度器调度能力，超过能力的部分，会被阻塞，等待调度
	 */
	final int defaultCapacity=1*1000;
	
	/**
	 *  默认调度器生命周期（秒），超过生命周期，调度器自动关闭
	 */
	final int defaultAliveTime=20;
	/**
	 * 执行泛型任务T
	 */
	void execute(T t);
	/**
	 * 执行器关闭接口，手工调用，抽象实现TaskExecutor会级联关闭调度器
	 */
	void close();
	
	/**
	 *  添加T到执行器
	 */
	void add(T t);
	
	/**
	 *  移除T
	 */
	void remove(T t);
	
	/**
	 * 执行器收尾接口，抽象实现TaskExecutor中首先调用Task本身的taskFinished()，然后将Task状态设为100
	 */
	void finished(T t);
}
