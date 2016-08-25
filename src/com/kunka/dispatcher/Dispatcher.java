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
	public String getDispatcherName() {
		return DispatcherName;
	}

	private AtomicBoolean isShutdown = new AtomicBoolean(false);

	public Dispatcher(String dispatcherName, final Executor<Task> executor) {
		this.DispatcherName = dispatcherName;
		this.dispatcher = new Thread(dispatcherName) {
			public void run() {
				while(!isShutdown.get()){
					Task task = getTask();
					dispatch(executor, task);
				}
			}

		};
		this.dispatcher.start();
	}

	private Task getTask() {
		Task task=null;
		while (!isShutdown.get()) {
			try {
				task = queue.poll(3,TimeUnit.SECONDS);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (task != null) {
				return task;
			}
			continue;
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

	}

}
