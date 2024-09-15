package com.aselsan.taskmanagement.datastructure;

public class ConcurrentPriorityQueue<T extends Comparable<T>> {

	private Node<T> highPriorityRoot;

	private Node<T> lowPriorityRoot;

	// biggest priority value to be low priority (inclusive)
	private T priorityBorderValue;

	private int size = 0;

	private static final Object LOCK = new QueueLock();

	private static class QueueLock {}

	public ConcurrentPriorityQueue(T priorityBorderValue) {

		this.priorityBorderValue = priorityBorderValue;
	}

	public void add(T value) {

		synchronized (getQueueLock()) {

			// doğru sıraya ekle
			if (value.compareTo(priorityBorderValue) >= 0) {

				if (lowPriorityRoot == null) {

					lowPriorityRoot = new Node<>(value);

				} else {

					putInOrder(lowPriorityRoot, value, true);
				}

			} else {

				if (highPriorityRoot == null) {

					highPriorityRoot = new Node<>(value);

				} else {

					putInOrder(highPriorityRoot, value, false);
				}
			}

			size++;
		}
	}

	public void printQueue() {

		synchronized (getQueueLock()) {

			Node<T> current = highPriorityRoot;

			while (current != null) {

				System.out.print(current.getValue() + ", ");

				current = current.getNext();
			}

			System.out.println();
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

	public void peekHighPriority() {

		synchronized (getQueueLock()) {

		}
	}

	public void peekLowPriority() {

		synchronized (getQueueLock()) {

		}
	}

	public T pollHighPriority() {

		synchronized (getQueueLock()) {

			T value = null;

			if (highPriorityRoot != null) {

				value = highPriorityRoot.getValue();

				highPriorityRoot = highPriorityRoot.getNext();
				size--;
			}

			return value;
		}
	}

	public T pollLowPriority() {

		synchronized (getQueueLock()) {

			T value = null;

			if (highPriorityRoot != null) {

				value = highPriorityRoot.getValue();

				highPriorityRoot = highPriorityRoot.getNext();
			}

			size--;
			return value;
		}
	}

	public int getSize() {

		synchronized (getQueueLock()) {

			return size;
		}
	}

	public final Object getQueueLock() {

		return LOCK;
	}
}
