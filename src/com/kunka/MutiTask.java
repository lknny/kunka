package com.kunka;

import java.util.List;

public abstract class MutiTask extends Task {
	
	public abstract List<Task> getSubTasks();
	public abstract void addSubTask(List<Task> t);
}
