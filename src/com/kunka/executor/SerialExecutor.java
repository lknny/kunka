package com.kunka.executor;

import com.kunka.Executor;
import com.kunka.Task;

public class SerialExecutor  implements Executor<Task>{
	private Thread executor=null;
	
	@Override
	public void execute(final Task task) {
		this.executor=new Thread(){
			public void run(){
				task.runTask();
			}
		};
		this.executor.start();
	}
}
