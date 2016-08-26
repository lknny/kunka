package com.kunka.task;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskStatus implements Serializable,Comparable<TaskStatus> {
    private static final long serialVersionUID = -4969856142081165657L;
    /** 准备状态 */
    public transient final static int S_READY = 0;
    /** 执行中状态 */
    public transient final static int S_RUNNING = 1;
    /** 完成-成功状态 */
    public transient final static int S_SUCCEED = 100;
    /** 完成-失败状态 */
    public transient final static int S_FAILED = 101;
    /** 完成-部分失败状态 */
    public transient final static int S_PART_FAILED = 102;
    /** 完成-全部失败状态 */
    public transient final static int S_ALL_FAILED = 103;
    /** 完成-任务被终止 */
    public transient final static int S_INTERRUPTED = 104;
    /** 完成-任务超时 */
    public transient final static int S_TIMEOUT = 105;
    /** 完成-不知道成功还是失败 */
    public transient final static int S_FINISH = 106;
    /** 完成-任务不存在 */
    public transient final static int S_NOT_EXIST = 107;
    
    protected long taskID;
    protected int status=S_READY;
    protected int progress=S_READY;
    private Long startTime = System.currentTimeMillis();
    private Long lastTime = System.currentTimeMillis();
    
    public TaskStatus(long taskID,int status) {
        this.taskID = taskID;
        this.status = status;
    }
    public long getTaskId(){
    	return taskID;
    }
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        lastTime = System.currentTimeMillis();
    }
    
    public String getElapsedTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return (lastTime-startTime)/1000+"(Start time:"+sdf.format(new Date(startTime))+", Last update time:"+sdf.format(new Date(lastTime))+")";
    }

    public synchronized void update(int status, int progress){
        setStatus(status);
        setProgress(progress);
    }
    
    
    public synchronized void update(int status, int progress, String desc){
        setStatus(status);
        setProgress(progress);
    }

    @Override
    public int compareTo(TaskStatus other) {
        if(other==null){
            return 1;
        }
        if(isFinish()){
            if(!other.isFinish()){
                return -1;
            }
        }else{
            if(other.isFinish()){
                return 1;
            }
        }       
        return lastTime.compareTo(other.lastTime);
    }
    
    public boolean isFinish() {
        return status >=100;
    }
    
}