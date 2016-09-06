package com.kunka;

public interface Dispatch<T> {

	/**
	 *  Ĭ�ϵ������������������������Ĳ��֣��ᱻ�������ȴ�����
	 */
	final int defaultCapacity=1*1000;
	
	/**
	 *  Ĭ�ϵ������������ڣ��룩�������������ڣ��������Զ��ر�
	 */
	final int defaultAliveTime=20;
	
	/**
	 *  ��Ӵ�����T
	 */
	public void add(T t);
	
	/**
	 *  �Ƴ�������T
	 */
	public void remove(T t);
	/**
	 *  ����
	 */
	public void dispatch(T t);
	
	/**
	 *  �������رսӿڣ�����ʵ��Dispatcher�У��ἶ���ر����ִ����
	 */
	public void shutdown();
}
