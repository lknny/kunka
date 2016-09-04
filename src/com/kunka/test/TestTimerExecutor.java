package com.kunka.test;

import com.kunka.Executor;
import com.kunka.Task;
import com.kunka.executor.ConcurrentExecutor;
import com.kunka.executor.TimerExecutor;
import com.kunka.task.TaskListener;
import com.kunka.task.TaskManager;
import com.kunka.task.TaskStatus;

public class TestTimerExecutor extends Task {

	public TestTimerExecutor(String s) {
		super(s);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final Executor<Task> executor = new TimerExecutor(3);
		final TaskListener listener = new TaskListener() {
			@Override
			public void onchange(TaskStatus taskStatus) {
				System.out.println("任务ID:" + taskStatus.getTaskId() + "   任务执行：" + taskStatus.getStatus() + "%");

			}
		};
		Task task = null;
		for (int i = 1; i < 100; i++) {

			if (i == 99) {
				task = new TestTimerExecutor(String.valueOf(i));
				TaskManager.getInstance().addTask(task, executor, new TaskListener[] { listener });
				TaskManager.getInstance().interruptTask(task);
			}
			TaskManager.getInstance().addTask(new TestTimerExecutor(String.valueOf(i)), executor,
					new TaskListener[] { listener });
		}
	}

	@Override
	public void runTask() {
		// TODO Auto-generated method stub
		System.out.println("Task: " + this.getTaskId() + " is running.");
	}

	@Override
	public void timeOutAction() {
		// TODO Auto-generated method stub
	}

	@Override
	public void taskFinished() {
		System.out.println("Task : " + getTaskId() + " is finished.");
	}

}