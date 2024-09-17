package com.aselsan.taskmanagement.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.aselsan.taskmanagement.datastructure.ConcurrentPriorityQueue;
import com.aselsan.taskmanagement.task.ITask;

public abstract class Employee {

	private AtomicBoolean acceptNewTasks = new AtomicBoolean(false);

	private List<Thread> threadPool = new ArrayList<>(2);

	protected ConcurrentPriorityQueue<ITask> taskQueue;

	public Employee(ConcurrentPriorityQueue<ITask> taskQueue) {

		this.taskQueue = taskQueue;

		prepareThreadPool(2);
	}

	public abstract ITask getTask();

	public void stopWorking() {

		acceptNewTasks.set(false);

		threadPool.forEach(t -> {

			try {

				t.join();

			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		});

		threadPool = new ArrayList<>();
	}

	/**
	 * 
	 * Stops and resizes the thread pool. It wont resume if threads already running.
	 * 
	 * @param n new Thread Pool size
	 */
	public void resizeThreadPool(int n) {

		acceptNewTasks.set(false);
		stopWorking();
		prepareThreadPool(n);
	}

	public void startWorking() {

		acceptNewTasks.set(true);
		threadPool.forEach(Thread::start);
	}

	private void prepareThreadPool(int n) {

		for (int i = 0; i < n; i++) {

			threadPool.add(new Thread(this::doWork));
		}
	}

	private void doWork() {

		ITask task = getTask();

		if (task != null) {

			// do task
			task.simulateTask();

		} else {

			// no task available wait for one to arrive
			try {

				Thread.sleep(500L);

			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

		if (acceptNewTasks.get()) {

			doWork();
		}
	}
}
