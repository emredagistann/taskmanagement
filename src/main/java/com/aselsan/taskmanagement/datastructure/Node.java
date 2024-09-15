package com.aselsan.taskmanagement.datastructure;

public class Node<T extends Comparable<T>> {

	private T value;

	private Node<T> next;

	private Node<T> prev;

	public Node() {

	}

	public Node(T value) {

		this.value = value;
	}

	public T getValue() {

		return value;
	}

	public Node<T> getPrev() {

		return prev;
	}

	public void setPrev(Node<T> prev) {

		this.prev = prev;
	}

	public Node<T> getNext() {

		return next;
	}

	public void setNext(Node<T> next) {

		this.next = next;
	}
}
