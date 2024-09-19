package com.aselsan.taskmanagement.queue;

public class ConcurrentPriorityQueue<T extends Comparable<T>> {

	private Node<T> highPriorityRoot;

	private Node<T> lowPriorityRoot;

	// biggest priority value to be low priority (inclusive)
	private T priorityBorderValue;

	private int highPrioritySize = 0;

	private int lowPrioritySize = 0;

	private static final Object LOCK = new QueueLock();

	private static class QueueLock {}

	public ConcurrentPriorityQueue(T priorityBorderValue) {

		this.priorityBorderValue = priorityBorderValue;
	}

	public void add(T value) {

		synchronized (getQueueLock()) {

			// decide low or high priority
			if (value.compareTo(priorityBorderValue) >= 0) {

				if (lowPriorityRoot == null) {

					lowPriorityRoot = new Node<>(value);

				} else {

					putInOrder(lowPriorityRoot, value, true);
				}

				lowPrioritySize++;

			} else {

				if (highPriorityRoot == null) {

					highPriorityRoot = new Node<>(value);

				} else {

					putInOrder(highPriorityRoot, value, false);
				}

				highPrioritySize++;
			}
		}
	}

	public int getSize() {

		synchronized (getQueueLock()) {

			return highPrioritySize + lowPrioritySize;
		}
	}

	public int getSize(Priority priority) {

		if (priority == Priority.HIGH) {

			return getHighPrioritySize();

		} else {

			return getLowPrioritySize();
		}
	}

	public T poll(Priority priority) {

		if (priority == Priority.HIGH) {

			return pollHighPriority();

		} else {

			return pollLowPriority();
		}
	}

	public String stringifyQueue() {

		synchronized (getQueueLock()) {

			StringBuilder sb = new StringBuilder();

			Node<T> current = highPriorityRoot;

			while (current != null) {

				sb.append(current.getValue()).append(", ");

				current = current.getNext();
			}

			current = lowPriorityRoot;

			while (current != null) {

				sb.append(current.getValue()).append(", ");

				current = current.getNext();
			}

			if (sb.length() > 2) {

				sb.setLength(sb.length() - 2);
			}

			return sb.toString();
		}
	}

	private void putInOrder(Node<T> root, T value, boolean isLow) {

		Node<T> current = root;

		Node<T> nodeToBeAdded = new Node<>(value);

		if (value.compareTo(current.getValue()) < 0) {

			if (isLow) {

				nodeToBeAdded.setNext(lowPriorityRoot);
				lowPriorityRoot = nodeToBeAdded;

			} else {

				nodeToBeAdded.setNext(highPriorityRoot);
				highPriorityRoot = nodeToBeAdded;
			}

		} else {

			while (current != null) {

				if (current.getNext() == null) {

					current.setNext(new Node<>(value));

					break;

				} else {

					if (value.compareTo(current.getNext().getValue()) >= 0) {

						current = current.getNext();

					} else {

						nodeToBeAdded.setNext(current.getNext());
						current.setNext(nodeToBeAdded);
						break;
					}
				}
			}
		}
	}

	private T pollHighPriority() {

		synchronized (getQueueLock()) {

			T value = null;

			if (highPriorityRoot != null) {

				value = highPriorityRoot.getValue();

				highPriorityRoot = highPriorityRoot.getNext();

				highPrioritySize--;
			}

			return value;
		}
	}

	private T pollLowPriority() {

		synchronized (getQueueLock()) {

			T value = null;

			if (lowPriorityRoot != null) {

				value = lowPriorityRoot.getValue();

				lowPriorityRoot = lowPriorityRoot.getNext();

				lowPrioritySize--;
			}

			return value;
		}
	}

	private int getHighPrioritySize() {

		synchronized (getQueueLock()) {

			return highPrioritySize;
		}
	}

	private int getLowPrioritySize() {

		synchronized (getQueueLock()) {

			return lowPrioritySize;
		}
	}

	public final Object getQueueLock() {

		return LOCK;
	}
}
