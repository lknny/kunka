package com.kunka;

import java.util.List;

/**
 *  多子任务，每个Task中又包含多个小任务
 *
 */
public abstract class MultiTask extends Task {
	
	/**
	 *  获得子任务列表
	 */
	public abstract List<Task> getSubTasks();
	/**
	 *  添加子任务
	 */
	public abstract void addSubTask(List<Task> t);
}
