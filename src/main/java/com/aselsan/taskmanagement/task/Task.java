package com.aselsan.taskmanagement.task;

public class Task implements ITask {

	private int priority;

	private long duration;

	public Task(int priority, long duration) {

		this.priority = priority;
		this.duration = duration;
	}

	public Task(int priority) {

		this(priority, 5L);
	}

	@Override
	public int getPriority() {

		return priority;
	}

	@Override
	public void doTask() {

		try {

			Thread.sleep(duration);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		System.out.println(String.format("Task with priority %d has been completed.", priority));
	}

	@Override
	public int compareTo(ITask o) {

		return this.getPriority() > o.getPriority() ? -1 : (this.getPriority() < o.getPriority() ? 1 : 0);
	}
}
