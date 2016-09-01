package com.kunka.executor;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import com.kunka.Executor;
import com.kunka.Task;

public class TimerExecutor implements Executor<Task> {
    private Date date;
    private Timer timer;
    public TimerExecutor(Date date) {
        this.date=date;
       this. timer=new Timer();
    }
    
    @Override
    public void execute( final Task task) {
    	if (task.isInterrupted()) {
    		System.out.println("Task is interrupted,ID: "+task.getTaskId());
			return;
		}
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	task.runTask();
            }
        }, date);
    }

    @Override
    public synchronized void shutdown() {
    	timer.cancel();
    }
}