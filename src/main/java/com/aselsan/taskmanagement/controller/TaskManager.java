package com.aselsan.taskmanagement.controller;

import java.util.Random;

import com.aselsan.taskmanagement.datastructure.ConcurrentPriorityQueue;
import com.aselsan.taskmanagement.task.ITask;
import com.aselsan.taskmanagement.task.Task;

public class TaskManager {

	private ConcurrentPriorityQueue<ITask> tasks;

	public TaskManager() {

		tasks = new ConcurrentPriorityQueue<ITask>(new Task(-1));

		startGeneratingTasks();
		startConsumingTasks();
	}

	private void startConsumingTasks() {

		new Thread(() -> {

			while (true) {

				System.out.println("Task size: " + tasks.getSize());

				ITask task = tasks.pollHighPriority();

				if (task != null) {

					task.doTask();
				}
			}

		}, "Task consumer.").start();
	}

	private void startGeneratingTasks() {

		new Thread(() -> {

			Random r = new Random();

			while (true) {

				tasks.add(new Task(r.nextInt(6), r.nextLong(101L)));

				try {

					Thread.sleep(50L);
				} catch (InterruptedException e) {

					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				tasks.printQueue();
			}

		}, "Task generator.").start();
	}
}
