package com.kunka.dispatcher;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.kunka.Dispatch;
import com.kunka.Task;
import com.kunka.task.TaskManager;
import com.kunka.task.TaskStatus;

public abstract class Dispatcher<T extends Task> implements Dispatch<T> {
	private LinkedBlockingQueue<T> queue;
	private AtomicBoolean isShutdown = new AtomicBoolean(false);
	private Thread dispatcher;
	private final static String DispatcherName="Task dispatcher thread";
	private Timer timer;

	public Dispatcher(int keepAliveTime, int capacity) {
		init(keepAliveTime, capacity);
	}

	private void init(int keepAliveTime, int capacity) {
		this.queue = new LinkedBlockingQueue<T>(capacity);
		this.dispatcher = new Thread(DispatcherName) {
			public void run() {
				while (!isShutdown.get()) {
					T t = getTask();
					if (null == t) {
						continue;
					}
					TaskManager.getInstance().update(new TaskStatus(t.getTaskId(), 20));
					dispatch(t);
				}
			}
		};
		this.dispatcher.start();
		this.timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				shutdown();
				timer.cancel();
			}
		}, keepAliveTime * 1000);
	}

	private T getTask() {
		T task = null;
		try {
			task = queue.poll(2000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return task;
	}

	public abstract void dispatch(T t);

	@Override
	public void add(T t) {
		this.queue.add(t);
	}

	@Override
	public void remove(T t) {
		this.queue.remove(t);
	}
	
	//关闭执行器的方法，调度器自动关闭时系统调用
	public abstract void shutdownExecutor();
	
	@Override
	public void shutdown() {
		this.isShutdown.set(true);
		shutdownExecutor();
		System.out.println(DispatcherName + " shut down.");
	}
}
