package com.aselsan.taskmanagement.datastructure;

public class ConcurrentPriorityQueue<T extends Comparable<T>> {

	// root of all
	private Node<T> highPriorityRoot = new Node<>();

	private Node<T> lowPriorityRoot = new Node<>();

	// biggest priority value to be low priority (inclusive)
	private T priorityBorderValue;

	private static final Object LOCK = new QueueLock();

	private static class QueueLock {}

	public ConcurrentPriorityQueue(T priorityBorderValue) {

		this.priorityBorderValue = priorityBorderValue;

		highPriorityRoot.setNext(lowPriorityRoot);
		lowPriorityRoot.setPrev(highPriorityRoot);
	}

	public void add(T value) {

		synchronized (getQueueLock()) {

			// sınır altında ise high olanları skipledik
			if (value.compareTo(priorityBorderValue) >= 0) {

				Node<T> current = lowPriorityRoot;

				while (current != null && value.compareTo(current.getValue()) > 0) {

				}

			} else {

				Node<T> current = highPriorityRoot;

				while (value.compareTo(current.getValue()) > 0) {

				}
			}
		}
	}

	public void peekHighPriority() {

		synchronized (getQueueLock()) {

		}
	}

	public void peekLowPriority() {

		synchronized (getQueueLock()) {

		}
	}

	public void pollHighPriority() {

		synchronized (getQueueLock()) {

		}
	}

	public void pollLowPriority() {

		synchronized (getQueueLock()) {

		}
	}

	public final Object getQueueLock() {

		return LOCK;
	}
}
