package com.aselsan.taskmanagement.employee;

import com.aselsan.taskmanagement.controller.TaskManager;

public class Company {

	private TaskManager taskManager;

	private Employee priorityEmployee;

	private Employee nonPriorityEmployee;

	public Company() {

		taskManager = new TaskManager();

		priorityEmployee = new PriorityEmployee(taskManager.getTasks());

		nonPriorityEmployee = new NonPriorityEmployee(taskManager.getTasks());
	}

	public void startWorking() {

		priorityEmployee.startWorking();
		nonPriorityEmployee.startWorking();

		new Thread(() -> {

			while (true) {

				try {

					Thread.sleep(5000L);
				} catch (InterruptedException e) {

					e.printStackTrace();
				}

				int size = taskManager.getTasks().getSize() / 10 + 1;

				System.out.println("RESIZING...");
				priorityEmployee.resizeThreadPool(size);
				nonPriorityEmployee.resizeThreadPool(size);
				System.out.println("RESIZED TO " + size);

				priorityEmployee.startWorking();
				nonPriorityEmployee.startWorking();
			}

		}).start();
	}
}
