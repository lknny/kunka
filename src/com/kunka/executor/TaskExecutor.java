package com.kunka.executor;

import com.kunka.Dispatch;
import com.kunka.Executor;
import com.kunka.Task;
import com.kunka.dispatcher.Dispatcher;
import com.kunka.task.TaskManager;
import com.kunka.task.TaskStatus;

/**
 * @author toto
 * ִ���������࣬�ڲ�����һ��������
 *
 */
//�������Ƿ��жϣ��ŵ���������ȥ�жϣ��ж��򲻽���ִ�С�
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

         //�Զ��ر�ִ����ʱϵͳ����
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
        
    //�����رյ�������ִ��������
    @Override
    public void close() {
    	//�������ر�ʱ���ἶ���ر����ִ����
        dispatcher.shutdown();
    }

	@Override
	public void finished(T t){
		t.taskFinished();
		TaskManager.getInstance().update(new TaskStatus(t.getTaskId(), TaskStatus.S_SUCCEED));
	}
}
