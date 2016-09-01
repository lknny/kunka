package com.kunka.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.kunka.Dispatch;
import com.kunka.Executor;
import com.kunka.Task;
import com.kunka.dispatcher.Dispatcher;

public class TaskManager {
    private static long globalTaskNo = Math.round(Math.random());
    private final static Map<Long, Task> runningTasks = new ConcurrentHashMap<Long, Task>();
    private final static Map<Long, List<TaskListener>> listeners = new ConcurrentHashMap<Long, List<TaskListener>>();
    private final static Map<Long,TaskStatus> taskStatus = new ConcurrentHashMap<Long, TaskStatus>();
    
    public TaskStatus  getTaskStatus(long taskid) {
        return taskStatus.get(taskid);
    }


    private static final int MAX_TASK_COUNT = 10000;
    private static final int REMOVE_TASK_COUNT = 100;
    
    private static class TaskManagerFactory{
    	private static TaskManager instance=new TaskManager();
    }
    private TaskManager() {
    }
    public static  TaskManager getInstance() {
        return TaskManagerFactory.instance;
    }

    protected void addTask(Task task) {
        runningTasks.put(task.getTaskId(), task);
        addTaskStatus(new TaskStatus(task.getTaskId(), TaskStatus.S_READY));
    }
    
    public void addTask(Task task,Executor<Task> executor){
        Dispatch<Task> dispatch=new Dispatcher(executor);
        addTask(task);
        dispatch.add(task);
    }
    
    public void addTask(Task task,Executor<Task> executor,TaskListener[] listener){
        Dispatch<Task> dispatch=new Dispatcher(executor);
        addTask(task);
        for (TaskListener taskListener : listener) {
			registerListener(task.getTaskId(), taskListener);
		}
        dispatch.add(task);
    }
    
    public void removeTask(Long taskid) {
        runningTasks.remove(taskid);
    }

    public synchronized static long applyTaskId() {
    	++globalTaskNo;
        if (globalTaskNo >= 0xffffff){
            globalTaskNo = 1;
        }
        return globalTaskNo;
    }
    
    
    public void registerListener(long taskID, TaskListener listener) {
        synchronized (listeners) {
            List<TaskListener> list = listeners.get(taskID);
            if (list == null) {
                list = new ArrayList<TaskListener>();
                listeners.put(taskID, list);
            }
            list.add(listener);
        }
    }

    public void removeListener(long taskID, TaskListener listener) {
        synchronized (listeners) {
            List<TaskListener> list = listeners.get(taskID);
            if (list != null) {
                list.remove(listener);
            }
        }
    }
    
    public void addTaskStatus(TaskStatus status){
        if(status==null){
            return;
        }
        taskStatus.put(status.getTaskId(), status);
        maintain(); 
    }
    
    /**
     * 更新任务状态
     * @param status
     */
    public void update(final TaskStatus status){
        if(status==null){
            return;
        }           
        taskStatus.put(status.getTaskId(), status);
        List<TaskListener> list = getListeners(status.getTaskId());
        for(TaskListener l:list){
            try {
                l.onchange(status);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        if(status.isFinish()){
            runningTasks.remove(status.getTaskId());
        }
    }
    
    private List<TaskListener> getListeners(long taskID){
        List<TaskListener> list = new ArrayList<TaskListener>();
        List<TaskListener> sp = listeners.get(taskID);
        if(sp!=null){
            list.addAll(sp);
        }       
        return list;
    }
    
public synchronized void interruptTask(Task task){
	if (null!=runningTasks.get(String.valueOf(task.getTaskId()))) {
		runningTasks.get(String.valueOf(task.getTaskId())).interrupt();
	}
	return;
}
    
    
    
    /**
     * 维护任务状态记录信息
     * 超过MAX_TASK_COUNT个任务时，删除已经完成的REMOVE_TASK_COUNT个任务
     */
    private  void maintain(){
        synchronized (taskStatus) {
            if(taskStatus.size()>MAX_TASK_COUNT){
                TaskStatus[] tks = taskStatus.values().toArray(new TaskStatus[0]);
                Arrays.sort(tks);
                int count = 0;
                for(TaskStatus t:tks){
                    long id = t.getTaskId();
                    if(taskStatus.get(id).isFinish()){
                        taskStatus.remove(id);
                        count++;
                        if(count>=REMOVE_TASK_COUNT){
                            break;
                        }
                    }
                }
            }
        }
        
    }
    
}