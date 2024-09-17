package com.aselsan.taskmanagement.controller;

import java.util.Random;

import com.aselsan.taskmanagement.datastructure.ConcurrentPriorityQueue;
import com.aselsan.taskmanagement.task.ITask;
import com.aselsan.taskmanagement.task.Task;

public class TaskManager {

	private ConcurrentPriorityQueue<ITask> tasks;

	public TaskManager() {

		tasks = new ConcurrentPriorityQueue<>(new Task(2));

		startGeneratingTasks();
	}

	private void startGeneratingTasks() {

		new Thread(() -> {

			Random r = new Random();

			while (true) {

				tasks.add(new Task(r.nextInt(6), 450L));

				try {

					Thread.sleep(10L);

				} catch (InterruptedException e) {

					e.printStackTrace();
				}

				tasks.printQueue();
			}

		}, "Task generator.").start();

	}

	public ConcurrentPriorityQueue<ITask> getTasks() {

		return tasks;
	}
}
