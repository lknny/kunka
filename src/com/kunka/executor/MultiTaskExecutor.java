package com.kunka.executor;

import com.kunka.Task;

/**
 * @author toto
 *	多子任务执行器，每个父任务包含多个子任务，父类任务串行执行，各子任务间并发执行
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
