package com.aselsan.taskmanagement.employee;

import org.springframework.stereotype.Component;

import com.aselsan.taskmanagement.queue.Priority;
import com.aselsan.taskmanagement.service.TaskService;
import com.aselsan.taskmanagement.task.ITask;

@Component
public class NonPriorityEmployee extends Employee {

	public NonPriorityEmployee(TaskService taskService) {

		super(taskService);
	}

	@Override
	public ITask getTask() {

		ITask task = taskService.getTasks().poll(Priority.LOW);

		if (task == null) {

			task = taskService.getTasks().poll(Priority.HIGH);

		}

		return task;
	}
}
