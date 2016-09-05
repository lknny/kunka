package com.kunka.executor;

import com.kunka.Dispatch;
import com.kunka.Executor;
import com.kunka.Task;
import com.kunka.dispatcher.Dispatcher;
import com.kunka.task.TaskManager;
import com.kunka.task.TaskStatus;

/**
 * @author toto
 * 执行器抽象父类，内部持有一个调度器
 *
 */
//将任务是否中断，放到调度器中去判断，中断则不进行执行。
public abstract  class TaskExecutor<T extends Task> implements Executor<T>{
 Dispatch<T> dispatcher;
 public TaskExecutor(int keepAliveTime,int capacity ) {
     dispatcher = new Dispatcher<T>(keepAliveTime,capacity){
         @Override
         public void dispatch(T task) {
             if (task.isInterrupted()) {
                 System.out.println("Task is interrupted by dispatcher,ID: "+task.getTaskId());
                 return;
             }
             execute(task);
         }

         //自动关闭执行器时系统调用
		@Override
		public void shutdownExecutor() {
			closeExecutor();
		}
     };
 }
	@Override
	public void add(T t) {
		dispatcher.add(t);
		
	}
	@Override
	public void remove(T t) {
		dispatcher.remove(t);
	}
	
protected abstract void  closeExecutor() ;
        
    //主动关闭调度器和执行器方法
    @Override
    public void close() {
    	//调度器关闭时，会级联关闭相关执行器
        dispatcher.shutdown();
    }

	@Override
	public void finished(T t){
		t.taskFinished();
		TaskManager.getInstance().update(new TaskStatus(t.getTaskId(), TaskStatus.S_SUCCEED));
	}
}
