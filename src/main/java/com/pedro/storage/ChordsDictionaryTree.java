package com.pedro.storage;

public class ChordsDictionaryTree {

	private Node root;

	public void add(String chord) {
		
	}

	public void remove() {

	}
}

class Node {
	private String name;
	private Node next;

	public Node() {

	}

	public Node(String name, Node next) {
		this.name = name;
		this.next = next;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Node getNext() {
		return this.next;
	}
}