package com.kunka.executor;

import com.kunka.Executor;
import com.kunka.Task;
import com.kunka.task.TaskManager;
import com.kunka.task.TaskStatus;

public class SerialExecutor  implements Executor<Task>{
	@Override
	public void execute(Task task) {
	if (task.isInterrupted()) {
		System.out.println("Task is interrupted,ID: "+task.getTaskId());
		return;
	}
	TaskManager.getInstance().update(new TaskStatus(task.getTaskId(), 50));
		task.runTask();
		TaskManager.getInstance().update(new TaskStatus(task.getTaskId(), 50));
	}

	@Override
	public synchronized void shutdown() {
		System.out.println("Executor shut down.");
	}
}