package com.kunka.executor;

import com.kunka.Task;

/**
 * @author toto
 *	��������ִ������ÿ�������������������񣬸���������ִ�У���������䲢��ִ��
 */
public class MultiTaskExecutor extends TaskExecutor {

	public MultiTaskExecutor(int keepAliveTime, int capacity) {
		super(keepAliveTime, capacity);
	}
	public  MultiTaskExecutor() {
		super(defaultAliveTime, defaultCapacity);
	}

	@Override
	public void execute(Task t) {
		// TODO Auto-generated method stub
		
	}
}
