package com.kunka.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.kunka.Executor;
import com.kunka.Task;

public class ParallelExecutor implements Executor<Task> {

    private ExecutorService threadPool = null;

    public ParallelExecutor(int maxParallelNumber, String executorName) {
        this.threadPool = Executors.newFixedThreadPool(maxParallelNumber, new ThreadFactory(executorName));
    }

    @Override
    public void execute(final Task task) {
        threadPool.submit(new Runnable() {
            public void run() {
                task.runTask();
            }
        });
    }

    @Override
    public synchronized void shutdown() {
    	  threadPool. shutdown();
    	  System.out.println("Executor shut down.");
    }
}
