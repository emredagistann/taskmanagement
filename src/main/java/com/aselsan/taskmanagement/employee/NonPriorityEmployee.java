package com.aselsan.taskmanagement.employee;

import com.aselsan.taskmanagement.datastructure.ConcurrentPriorityQueue;
import com.aselsan.taskmanagement.task.ITask;

public class NonPriorityEmployee extends Employee {

	public NonPriorityEmployee(ConcurrentPriorityQueue<ITask> taskQueue) {

		super(taskQueue);
	}

	@Override
	public ITask getTask() {

		ITask task = taskQueue.pollLowPriority();

		if (task == null) {

			task = taskQueue.pollHighPriority();

		}

		return task;
	}
}
