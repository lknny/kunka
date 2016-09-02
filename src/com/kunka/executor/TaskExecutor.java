package com.kunka.executor;

import com.kunka.Dispatch;
import com.kunka.Executor;
import com.kunka.Task;
import com.kunka.dispatcher.Dispatcher;
import com.kunka.task.TaskManager;
import com.kunka.task.TaskStatus;

/**
 * @author toto
 * 执行器抽象父类，内部持有一个调度器
 *
 */
public abstract  class TaskExecutor implements Executor<Task>{
	Dispatch<Task> dispatcher;
	public TaskExecutor(int keepAliveTime,int capacity ) {
		dispatcher = new Dispatcher(keepAliveTime,capacity) {
			@Override
			public void dispatch(Task task) {
				execute(task);
			}
		};
	}
	@Override
	public void add(Task t) {
		dispatcher.add(t);
		
	}
	@Override
	public void remove(Task t) {
		dispatcher.remove(t);
		
	}
	@Override
	public void close() {
		dispatcher.shutdown();
	}

	@Override
	public void finished(Task t){
		TaskManager.getInstance().update(new TaskStatus(t.getTaskId(), TaskStatus.S_SUCCEED));
		t.taskFinished();
	}
}
