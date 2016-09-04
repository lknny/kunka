package com.kunka.test;

import java.util.ArrayList;
import java.util.List;

import com.kunka.Executor;
import com.kunka.MultiTask;
import com.kunka.Task;
import com.kunka.executor.MultiTaskExecutor;
import com.kunka.task.TaskListener;
import com.kunka.task.TaskManager;
import com.kunka.task.TaskStatus;

public class TestMultiTask extends MultiTask {

public static void main(String[] args) {
    final Executor<MultiTask> executor = new MultiTaskExecutor(5);
    final TaskListener listener = new TaskListener() {
        @Override
        public void onchange(TaskStatus taskStatus) {
            System.out.println("任务ID:" + taskStatus.getTaskId() + "   任务执行：" + taskStatus.getStatus() + "%");
        }
    };
    Task task = null;
    for (int i = 1; i <= 10; i++) {

        if (i == 10) {
            task = new TestMultiTask();
            TaskManager.getInstance().addTask(task, executor, new TaskListener[] { listener });
            TaskManager.getInstance().interruptTask(task);
        }
        TaskManager.getInstance().addTask(new TestMultiTask(), executor, new TaskListener[] { listener });
    }

}

@Override
public List<Task> getSubTasks() {
    // TODO Auto-generated method stub
    List<Task> tasks = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
        tasks.add(new Task() {
            
            @Override
            public void timeOutAction() {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void taskFinished() {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void runTask() {
                System.out.println("SubTask "+getTaskId()+" is running..");
                
            }
        });
    }
    return tasks;
}

@Override
public void addSubTask(List<Task> t) {
    // TODO Auto-generated method stub

}

@Override
public void runTask() {
    // TODO Auto-generated method stub
    System.out.println("Task: " + this.getTaskId() + " is running.");
}

@Override
public void timeOutAction() {
    // TODO Auto-generated method stub

}

@Override
public void taskFinished() {
    System.out.println("Task : " + getTaskId() + " is finished.");

}
}
