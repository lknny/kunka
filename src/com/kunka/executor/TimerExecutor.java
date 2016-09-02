package com.kunka.executor;

import java.util.Timer;
import java.util.TimerTask;

import com.kunka.Task;

/**
 *  定时/延时执行器，到达指定时间后，所有任务并发执行。
 *
 */
public class TimerExecutor extends TaskExecutor {
    private long  delaySeconds;
    private Timer timer;
    public TimerExecutor(long delaySeconds) {
    	super(defaultAliveTime, defaultCapacity);
    	Init(delaySeconds);
    }
    public TimerExecutor(long delaySeconds,int keepAliveTime,int capacity){
    	super(keepAliveTime, capacity);
    	Init(delaySeconds);
    }
    
    private void Init(long delaySeconds){
    	this.delaySeconds=delaySeconds;
    	this.timer=new Timer(false);
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
            	finished(task);
            }
        }, delaySeconds*1000);
    }
    
	@Override
	public synchronized void close() {
		super.close();
		timer.cancel();
		System.out.println("TimerExecutor shut down.");
	}
}