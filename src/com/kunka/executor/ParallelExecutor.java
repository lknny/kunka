package com.kunka.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.kunka.Executor;
import com.kunka.Task;
import com.kunka.task.TaskManager;
import com.kunka.task.TaskStatus;

public class ParallelExecutor implements Executor<Task> {

    private ExecutorService threadPool;
    
    public ParallelExecutor(int maxParallelNumber, String executorName) {
        this.threadPool = Executors.newFixedThreadPool(maxParallelNumber, new ThreadFactory(executorName));
    }

    @Override
    public void execute(final Task task) {
    	if (task.isInterrupted()) {
			return;
		}
    	TaskManager.getInstance().update(new TaskStatus(task.getTaskId(), 50));
       Future future=  threadPool.submit(new Runnable() {
            public void run() {
                task.runTask();
                task.finishTask(task.getTaskId());
            }
        });
       if (future.isDone()) {
    	   
	}
    }

    @Override
    public synchronized void shutdown() {
    	  threadPool. shutdown();
    	  System.out.println("Executor shut down.");
    }

    
}
