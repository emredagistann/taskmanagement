package com.aselsan.taskmanagement.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aselsan.taskmanagement.queue.Priority;
import com.aselsan.taskmanagement.service.TaskService;
import com.aselsan.taskmanagement.task.ITask;
import com.aselsan.taskmanagement.task.Task;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@PostMapping
	public ResponseEntity<String> addTask(@RequestParam int priority, @RequestParam Optional<Integer> duration) {

		Task task = new Task(priority);

		if (duration.isPresent()) {

			task.setDuration(duration.get());
		}

		taskService.getTasks().add(task);

		return ResponseEntity.ok("Task added with priority: " + task.getPriority());
	}
	
	@GetMapping("/size")
	public ResponseEntity<Integer> getTaskSize() {
		
		return ResponseEntity.ok(taskService.getTasks().getSize());
	}

	@GetMapping
	public ResponseEntity<String> getTasks() {

		return ResponseEntity.ok(taskService.getTasks().stringifyQueue());
	}

	@GetMapping("/high")
	public ResponseEntity<ITask> getHighPriorityTask() {

		return ResponseEntity.ok(taskService.getTasks().poll(Priority.HIGH));
	}

	@GetMapping("/low")
	public ResponseEntity<ITask> getLowPriorityTask() {

		return ResponseEntity.ok(taskService.getTasks().poll(Priority.LOW));
	}
}
