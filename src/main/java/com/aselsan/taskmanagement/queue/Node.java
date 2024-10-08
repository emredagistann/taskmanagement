package com.aselsan.taskmanagement.queue;

public class Node<T extends Comparable<T>> {

	private T value;

	private Node<T> next;

	public Node() {

	}

	public Node(T value) {

		this.value = value;
	}

	public T getValue() {

		return value;
	}

	public Node<T> getNext() {

		return next;
	}

	public void setNext(Node<T> next) {

		this.next = next;
	}
}
