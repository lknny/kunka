package com.kunka;

public interface Executor<T> {

	/**
	 *  Ĭ�ϵ������������������������Ĳ��֣��ᱻ�������ȴ�����
	 */
	final int defaultCapacity=1*1000;
	
	/**
	 *  Ĭ�ϵ������������ڣ��룩�������������ڣ��������Զ��ر�
	 */
	final int defaultAliveTime=20;
	/**
	 * ִ�з�������T
	 */
	void execute(T t);
	/**
	 * ִ�����رսӿڣ��ֹ����ã�����ʵ��TaskExecutor�ἶ���رյ�����
	 */
	void close();
	
	/**
	 *  ���T��ִ����
	 */
	void add(T t);
	
	/**
	 *  �Ƴ�T
	 */
	void remove(T t);
	
	/**
	 * ִ������β�ӿڣ�����ʵ��TaskExecutor�����ȵ���Task�����taskFinished()��Ȼ��Task״̬��Ϊ100
	 */
	void finished(T t);
}
