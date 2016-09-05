package com.kunka.executor;

import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.kunka.MultiTask;
import com.kunka.Task;
import com.kunka.task.TaskManager;
import com.kunka.task.TaskStatus;

/**
 * @author toto 多子任务执行器，每个父任务包含多个子任务，父类任务串行执行，各子任务间并发执行
 */
public class MultiTaskExecutor extends TaskExecutor<MultiTask> {
	private final static String EXECUTORNAME = "Multi Task Executor";
	private final static int TASKFINISHED = 1;
	private ExecutorService executor;
	private CompletionService<Integer> completionExecutor;

	public MultiTaskExecutor(int subTaskRunTreadNum, int keepAliveTime, int capacity) {
		super(keepAliveTime, capacity);
		Init(subTaskRunTreadNum);
	}

	public MultiTaskExecutor(int subTaskRunTreadNum) {
		super(defaultAliveTime, defaultCapacity);
		Init(subTaskRunTreadNum);
	}

	private void Init(int subTaskRunTreadNum) {
		this.executor = Executors.newFixedThreadPool(subTaskRunTreadNum, new ThreadFactory(EXECUTORNAME));
		((ThreadPoolExecutor) this.executor).setKeepAliveTime(10, TimeUnit.SECONDS);
		((ThreadPoolExecutor) this.executor).allowCoreThreadTimeOut(true); // 设置允许超时回收
		this.completionExecutor = new ExecutorCompletionService<>(executor);

	}

	@Override
	public void execute(final MultiTask multiTask) {
		if (multiTask.isInterrupted()) {
			System.out.println("Task is interrupted,ID: "+multiTask.getTaskId());
			return;
		}
		multiTask.runTask();
		TaskManager.getInstance().update(new TaskStatus(multiTask.getTaskId(), 50));

		List<Task> subTasks = multiTask.getSubTasks();

		for (Task task : subTasks) {
			final Task subTask = task;
			completionExecutor.submit(new Runnable() {
				public void run() {
					subTask.runTask();
				}
			}, Integer.valueOf(TASKFINISHED));
		}

		// 取出所有子任务完成结果
		for (int i = 0; i < subTasks.size(); i++) {
			try {
				// 阻塞，直至有一个任务完成，并取回结果
				Future<Integer> future = completionExecutor.take();
				if (future.get().intValue() == TASKFINISHED) {
					System.out.println("SubTask :" + subTasks.get(i).getTaskId() + " is finished.");
				}
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		multiTask.taskFinished();
	}

	@Override
	protected void closeExecutor() {
		// TODO Auto-generated method stub
		this.executor.shutdown();
		System.out.println(EXECUTORNAME + " shut down.");
	}
}
