package com.kunka.executor;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.kunka.Executor;
import com.kunka.Task;

public class TimerExecutor implements Executor<Task> {
    private Date date;
    
    public TimerExecutor(Date date) {
        this.date=date;
    }
    
    @Override
    public void execute( final Task t) {
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                t.runTask();
            }
        }, date);
    }

    @Override
    public void shutdown() {
    }

}