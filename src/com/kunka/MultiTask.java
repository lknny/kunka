package com.kunka;

import java.util.List;

/**
 *  ��������ÿ��Task���ְ������С����
 *
 */
public abstract class MultiTask extends Task {
	
	/**
	 *  ����������б�
	 */
	public abstract List<Task> getSubTasks();
	/**
	 *  ���������
	 */
	public abstract void addSubTask(List<Task> t);
}
