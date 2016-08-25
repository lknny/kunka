package com.kunka.dispatcher;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.kunka.Dispatch;
import com.kunka.Executor;
import com.kunka.Task;

public class Dispatcher implements Dispatch<Task> {
	private LinkedBlockingQueue<Task> queue = new LinkedBlockingQueue<Task>();
	private Thread dispatcher;
	private String DispatcherName;
	private AtomicBoolean isShutdown = new AtomicBoolean(false);

	public Dispatcher(String dispatcherName, final Executor<Task> executor) {
		this.DispatcherName = dispatcherName;
		this.dispatcher = new Thread(dispatcherName) {
			public void run() {
				Task task = getTask();
				dispatch(executor, task);
			}

		};
		this.dispatcher.start();
	}

	private synchronized Task getTask() {
		while (!isShutdown.get()) {
			Task task = queue.poll();
			if (task != null) {
				return task;
			}
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				shutdown();
			}
		}
		return null;
	}

	@Override
	public void dispatch(Executor<Task> executor, Task task) {
		executor.execute(task);

	}

	@Override
	public synchronized void add(Task t) {
			this.notifyAll();
		this.queue.add(t);
		System.out.println("notify all...'");
	}

	@Override
	public void remove(Task t) {
		this.queue.remove(t);

	}

	@Override
	public void shutdown() {
		this.isShutdown.set(true);

	}

}
