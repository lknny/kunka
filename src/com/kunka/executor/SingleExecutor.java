package com.kunka.executor;

import com.kunka.Task;
import com.kunka.task.TaskManager;
import com.kunka.task.TaskStatus;

/**
 * @author toto
 *	串行执行器，所有任务串行执行
 */
public class SingleExecutor extends TaskExecutor<Task> {

	public SingleExecutor() {
		super(defaultAliveTime,defaultCapacity);
	}
	public SingleExecutor(int keepAliveTime,int capacity) {
		super(keepAliveTime,capacity);
	}

	@Override
	public synchronized void execute(Task task) {
		TaskManager.getInstance().update(new TaskStatus(task.getTaskId(), 50));
		task.runTask();
		finished(task);
	}
	@Override
	protected void closeExecutor() {
		// TODO Auto-generated method stub
		
	}
}