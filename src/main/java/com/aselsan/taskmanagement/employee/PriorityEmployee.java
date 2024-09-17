package com.aselsan.taskmanagement.employee;

import com.aselsan.taskmanagement.datastructure.ConcurrentPriorityQueue;
import com.aselsan.taskmanagement.task.ITask;

public class PriorityEmployee extends Employee {

	public PriorityEmployee(ConcurrentPriorityQueue<ITask> taskQueue) {

		super(taskQueue);
	}

	@Override
	public ITask getTask() {

		return taskQueue.pollHighPriority();
	}
}
