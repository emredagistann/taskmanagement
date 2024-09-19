package com.aselsan.taskmanagement.task;

public class Task implements ITask {

	private int priority;

	private long duration;

	public Task(int priority, long duration) {

		this.priority = priority;
		this.duration = duration;
	}

	public Task(int priority) {

		this(priority, 0L);
	}

	public long getDuration() {

		return duration;
	}

	public void setDuration(long duration) {

		this.duration = duration;
	}

	@Override
	public int getPriority() {

		return priority;
	}

	@Override
	public void simulateTask() {

		try {

			Thread.sleep(duration);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}

//		System.out.println(String.format("Task with priority %d has been completed after %d ms.", priority, duration));
	}

	@Override
	public int compareTo(ITask o) {

		return this.getPriority() > o.getPriority() ? -1 : (this.getPriority() < o.getPriority() ? 1 : 0);
	}

	@Override
	public String toString() {

		return "Task [priority=" + priority + ", duration=" + duration + "]";
	}

}
