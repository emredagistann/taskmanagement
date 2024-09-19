package com.aselsan.taskmanagement.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aselsan.taskmanagement.queue.ConcurrentPriorityQueue;
import com.aselsan.taskmanagement.task.ITask;
import com.aselsan.taskmanagement.task.Task;

@Service
public class TaskService {

	private ConcurrentPriorityQueue<ITask> tasks;

	public TaskService(@Value("${taskmanagement.task.priorityBorderValue}") int highestLowPriorityValue) {

		// above highestLowPriorityValue is considered as HIGH PRIORITY TASK otherwise
		// LOW PRIORITY TASK
		tasks = new ConcurrentPriorityQueue<>(new Task(highestLowPriorityValue));
	}

	public ConcurrentPriorityQueue<ITask> getTasks() {

		return tasks;
	}
}
