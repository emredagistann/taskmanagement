package com.aselsan.taskmanagement.task;

public interface ITask extends Comparable<ITask> {

	void simulateTask();

	int getPriority();
}
