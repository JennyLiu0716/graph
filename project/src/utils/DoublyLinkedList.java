package utils;

import graph.Node;

// https://www.geeksforgeeks.org/introduction-to-doubly-linked-lists-in-java/

public class DoublyLinkedList {
	public Node head;
	public Node tail;
	// when was the linked list created
	public int time;
	private int size;

	public DoublyLinkedList() {
		this.head = null;
		this.tail = null;
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void err() {
		System.out.println("Oops...");
	}

	public void insertAtBeginning(Node temp) {
		if (head == null) {
			head = temp;
			tail = temp;
		} else {
			temp.next = head;
			head.previous = temp;
			head = temp;
		}
		size++;
	}

	public void insertBefore(Node old, Node temp) {
		if (this.head == old) {
			insertAtBeginning(temp);
		} else {

			temp.next = old;
			temp.previous = old.previous;
			old.previous.next = temp;
			old.previous = temp;

			size++;
		}
	}

	public void insertAtEnd(Node temp) {
		if (tail == null) {
			head = temp;
			tail = temp;
		} else {
			tail.next = temp;
			temp.previous = tail;
			tail = temp;
		}
		size++;
	}

	public void deleteAtBeginning() {
		if (head == null) {
			return;
		}

		if (head == tail) {
			head = null;
			tail = null;
			size--;
			return;
		}

		Node temp = head;
		head = head.next;
		head.previous = null;
		temp.next = null;
		size--;
	}

	public void delete(Node node) {
		if (head == null) {
			return;
		}

		if (this.head == node) {
			deleteAtBeginning();
			return;
		}

		if (this.tail == node) {
			deleteAtEnd();
			return;
		}

		node.previous.next = node.next;
		node.next.previous = node.previous;
		node.previous = null;
		node.next = null;
		size--;
	}

	public void deleteAtEnd() {
		if (tail == null) {
			return;
		}

		if (head == tail) {
			head = null;
			tail = null;
			size--;
			return;
		}

		Node temp = tail;
		tail = tail.previous;
		tail.next = null;
		temp.previous = null;
		size--;
	}
	public void display() {
		Node temp = this.head;
		while (temp != null) {
			System.out.print(temp.element.toString() + " --> ");
			temp = temp.next;
		}
		System.out.println("NULL");
	}

}
