package com.aselsan.taskmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aselsan.taskmanagement.controller.TaskManager;

@SpringBootApplication
public class TaskmanagementApplication {

	public static void main(String[] args) {

		SpringApplication.run(TaskmanagementApplication.class, args);

		TaskManager taskManager = new TaskManager();
	}

}
