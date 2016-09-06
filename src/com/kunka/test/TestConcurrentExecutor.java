package com.kunka.test;

import com.kunka.Executor;
import com.kunka.Task;
import com.kunka.executor.ConcurrentExecutor;
import com.kunka.executor.TimerExecutor;
import com.kunka.task.TaskListener;
import com.kunka.task.TaskManager;
import com.kunka.task.TaskStatus;

public class TestConcurrentExecutor extends Task {

	public TestConcurrentExecutor(String s) {
		super(s);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final Executor<Task> executor = new ConcurrentExecutor(10);
		// final Executor<Task> executor=new TimerExecutor(3);
		// final Executor<Task> executor = new SerialExecutor();
		final TaskListener listener = new TaskListener() {
			@Override
			public void onchange(TaskStatus taskStatus) {
				System.out.println("任务ID:" + taskStatus.getTaskId() + "   任务执行：" + taskStatus.getStatus() + "%");

			}
		};
		Task task = null;
		for (int i = 1; i <100; i++) {

			if (i ==4) {
				task = new TestConcurrentExecutor(String.valueOf(i));
				
				TaskManager.getInstance().addTask(task, executor, new TaskListener[] { listener });
				TaskManager.getInstance().interruptTask(task);
				continue;
			}
			TaskManager.getInstance().addTask(new TestConcurrentExecutor(String.valueOf(i)), executor, new TaskListener[] { listener });
		}
		
		

		new Thread() {
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int i = 100; i < 200; i++) {
					TaskManager.getInstance().addTask(new TestConcurrentExecutor(String.valueOf(i)), executor,
							new TaskListener[] { listener });
				}
			}
		}.start();

	}

	@Override
	public void runTask() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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