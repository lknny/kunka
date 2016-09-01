package com.kunka.dispatcher;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.kunka.Dispatch;
import com.kunka.Executor;
import com.kunka.Task;
import com.kunka.task.TaskManager;
import com.kunka.task.TaskStatus;

public class Dispatcher implements Dispatch<Task> {
	private LinkedBlockingQueue<Task> queue ;
	private AtomicBoolean isShutdown = new AtomicBoolean(false);
	private AtomicBoolean isFinished = new AtomicBoolean(false);
	private Thread dispatcher;
	private String DispatcherName;
	private Executor<Task> executor;
	
	public String getDispatcherName() {
		return DispatcherName;
	}
	public Dispatcher(final Executor<Task> executor){
		init("Task dispatcher thread",executor, defaultAliveTime);
	}

	public Dispatcher(String dispatcherName, final Executor<Task> executor) {
		init(dispatcherName, executor,defaultAliveTime);
	}
	public Dispatcher(String dispatcherName, final Executor<Task> executor, int keepAliveTime){
		init(dispatcherName, executor, keepAliveTime);
	}
	
	private void init(String dispatcherName, final Executor<Task> executor, int keepAliveTime) {
		this.executor=executor;
		this.DispatcherName = dispatcherName;
		this.queue= new LinkedBlockingQueue<Task>(defaultCapacity);
		this.dispatcher = new Thread(dispatcherName) {
			public void run() {
				while(!isShutdown.get()){
					Task task = getTask();
					if (null==task) {
						continue;
					}
					TaskManager.getInstance().update(new TaskStatus(task.getTaskId(), 20));
					dispatch(executor, task);
				}
			}

		};
		this.dispatcher.start();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				shutdown();
			}
		}, keepAliveTime);
	}

	private Task getTask() {
		Task task=null;
			try {
				task = queue.poll(defaultDispathIntervalTime,TimeUnit.MILLISECONDS);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		return task;
	}

	@Override
	public void dispatch(Executor<Task> executor, Task task) {
		executor.execute(task);
	}

	@Override
	public  void add(Task t) {
		this.queue.add(t);
	}

	@Override
	public void remove(Task t) {
		this.queue.remove(t);

	}

	@Override
	public void shutdown() {
		this.isShutdown.set(true);
		System.out.println(DispatcherName+" shut down.");
		synchronized (this) {
			executor.shutdown();
		}
	}
	@Override
	public void finish() {
		isFinished.set(true);
		
	}
}
