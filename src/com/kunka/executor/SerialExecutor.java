package com.kunka.executor;

import com.kunka.Executor;
import com.kunka.Task;

public class SerialExecutor  implements Executor<Task>{
	
	@Override
	public void execute(Task task) {
		task.runTask();
	}

	@Override
	public void shutdown() {
		System.out.println("Executor shut down.");
		
	}
}
