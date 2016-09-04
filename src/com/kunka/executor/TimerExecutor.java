package com.kunka.executor;

import java.util.Timer;
import java.util.TimerTask;

import com.kunka.Task;

/**
 *  定时/延时执行器，到达指定时间后，所有任务并发执行。
 *
 */
public class TimerExecutor extends TaskExecutor<Task> {
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
    	this.timer=new Timer();
    }
    
    @Override
    public void execute( final Task task) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	task.runTask();
            	finished(task);
            }
        }, delaySeconds*1000);
    }
	@Override
	protected void closeExecutor() {
		// TODO Auto-generated method stub
		timer.cancel();
		System.out.println("TimerExecutor shut down.");
	}
}