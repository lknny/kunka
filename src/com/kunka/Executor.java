package com.kunka;

public interface Executor<T> {
	final int defaultCapacity=1*1000;
	final int defaultAliveTime=20;
	void execute(T t);
	void close();
	void add(T t);
	void remove(T t);
	
	/**
	 * ִ������β�ӿڣ�����ʵ��TaskExecutor�����ȵ���Task�����taskFinished()��Ȼ��Task״̬��Ϊ100
	 */
	void finished(T t);
}
