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

public abstract class Dispatcher implements Dispatch<Task> {
	private LinkedBlockingQueue<Task> queue ;
	private AtomicBoolean isShutdown = new AtomicBoolean(false);
	private Thread dispatcher;
	private final static String DispatcherName="Task dispatcher thread";
	
	public Dispatcher(int keepAliveTime,int capacity){
		init( keepAliveTime,capacity);
	}
	
	private void init(int keepAliveTime,int capacity) {
		this.queue= new LinkedBlockingQueue<Task>(capacity);
		this.dispatcher = new Thread(DispatcherName) {
			public void run() {
				while(!isShutdown.get()){
					Task task = getTask();
					if (null==task) {
						continue;
					}
					TaskManager.getInstance().update(new TaskStatus(task.getTaskId(), 20));
					dispatch(task);
				}
			}
		};
		this.dispatcher.start();
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				shutdown();
			}
		}, keepAliveTime*1000);
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

	public abstract void dispatch(Task task);

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
	}
}
