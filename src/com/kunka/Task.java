package com.kunka;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Task {
	
	private String taskName = "";
    private AtomicBoolean isInterrupted = new AtomicBoolean(false);
    private static long globalTaskNo = Math.round(Math.random());
    private long taskNo = 0;
    public long getTaskNo() {
		return taskNo;
	}

	private long timeout = 30000;
    
    protected Task(long taskNo){
    	this.taskNo=taskNo;
    }
    
    
	abstract public  void runTask();
	abstract public void timeOutAction();
	
	public void setTimeout(long to){
		this.timeout=to;
	}
	
	public synchronized static long applyTaskId() {
        Task.globalTaskNo++;
        if (Task.globalTaskNo >= 0xffffff){
            Task.globalTaskNo = 1;
        }
        return Task.globalTaskNo;
    }
	
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
	
    public boolean isInterrupted(){
        return isInterrupted.get();
    }
    
    public void interrupt() {
            isInterrupted .set(true);
    }
    
}
