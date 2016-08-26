package com.kunka;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import com.kunka.task.TaskManager;
import com.kunka.task.TaskStatus;

public abstract class Task {
    private String taskName = "";
    private AtomicBoolean isInterrupted = new AtomicBoolean(false);
    private long taskId;
    private TimerTask timerTask=null;



    protected Task(String taskName) {
        this.taskName = taskName;
        init();
    }
    protected Task () {
        init();
    }

    private void init() {
        this.taskId = TaskManager.applyTaskId();
    }

    abstract public void runTask();

    abstract public void timeOutAction();
    
    abstract public void finishTask();
    
    abstract public void afterTask();


    public void createTimerTask(){
           this.timerTask = new TimerTask(){
                public void run(){
                    try{
                        update(TaskStatus.S_TIMEOUT, 100,"timeout");
                        finishTask();
                        timeOutAction();
                        TaskManager.getInstance().removeTask(taskId);
                    }catch(Throwable e){
                        e.printStackTrace();
                    }
                }
            };
        }
    
    
    public void update(int status, int progress){
        getTaskStatus().update(status, progress); 
    }
        
    public void update(int status, int progress, String desc) {     
        getTaskStatus().update(status, progress, desc);     
    }
    
    public TaskStatus getTaskStatus(){
        TaskStatus ts =TaskManager.getInstance().getTaskStatus(taskId);
        if(ts==null){
            ts = new TaskStatus(taskId,TaskStatus.S_READY);  
            TaskManager.getInstance().update(ts);
        }
        return ts;      
    }
    
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isInterrupted() {
        return isInterrupted.get();
    }

    public void interrupt() {
        isInterrupted.set(true);
    }

    public long getTaskId() {
        return taskId;
    }

}
