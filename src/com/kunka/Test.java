package com.kunka;

import com.kunka.dispatcher.Dispatcher;
import com.kunka.executor.ParallelExecutor;
import com.kunka.executor.SerialExecutor;
import com.kunka.task.TaskListener;
import com.kunka.task.TaskManager;
import com.kunka.task.TaskStatus;

public class Test extends Task {

	public Test(String s) {
		super(s);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Executor<Task> executor=new ParallelExecutor(5,"pThread");
		final Executor<Task> executor = new SerialExecutor();
		final TaskListener listener=new TaskListener() {
			@Override
			public void onchange(TaskStatus taskStatus) {
				System.out.println("任务ID:"+taskStatus.getTaskId()+"   任务执行："+taskStatus.getStatus()+"%");
				
			}
		};
		Task task = null;
		for (int i = 1; i < 100; i++) {

			if (i == 99) {
				task = new Test(String.valueOf(i));
				TaskManager.getInstance().addTask(task, executor,new TaskListener[]{listener});
				TaskManager.getInstance().interruptTask(task);
			}
			TaskManager.getInstance().addTask(new Test(String.valueOf(i)), executor,new TaskListener[]{listener});
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
					TaskManager.getInstance().addTask(new Test(String.valueOf(i)), executor,new TaskListener[]{listener});
				}
			}
		}.start();

	}

	@Override
	public void runTask() {
		// TODO Auto-generated method stub
		System.out.println("task:---------" + this.getTaskId());
	}

	@Override
	public void timeOutAction() {
		// TODO Auto-generated method stub
	}

	@Override
	public void finishTask(long id) {
		TaskManager.getInstance().update(new TaskStatus(id, 100));

	}

	@Override
	public void afterTask() {

	}
}