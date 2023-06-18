package utils;

import graph.Node;

public class DList<T> {
    public Node head;
	public Node tail;

    public DList() {
//            head = tail = null;
        head = new Node(-1);
        tail = new Node(-1);
        head.next = tail;
        head.previous = null;
        tail.previous = head;
        tail.next = null;
        //head&tail's degree: when the linked list was created
        // - used in lbfscore
        head.degree = -1;
        tail.degree = -1;
        //head&tail's predegree: when the list was created from...
//        head.predegree = -2;
//        tail.predegree = -2;
    }

    public boolean isEmpty() {
//            return tail == null; //or head == null 
    	return head.next==tail;
    }

    public void err() {
        System.out.println("Oops...");
    }

    public Node insertFirst(T e) {
    	Node<T> newNode = new Node(e);
    	newNode.next = head.next;
    	newNode.previous = head;
    	head.next.previous = newNode;
    	head.next = newNode;
//        	System.out.println(newNode.next);
    	return newNode;
    	
    }
    public Node insertFirstNode(Node newNode) {
    	newNode.next = head.next;
    	newNode.previous = head;
    	head.next.previous = newNode;
    	head.next = newNode;
//        	System.out.println(newNode.next);
    	return newNode;
    	
    }
    public Node insertLastNode(Node newNode) {
    	newNode.next = tail;
    	newNode.previous = tail.previous;
    	tail.previous = newNode;
    	newNode.previous.next = newNode;
//        	System.out.println(newNode.next);
    	return newNode;
    	
    }
 



    public static Node insertBefore(Node node, DList<Node> newlist) {
    	Node newNode = new Node(newlist);
    	Node temp = node.previous;
    	node.previous = newNode;
    	newNode.next = node;
    	newNode.previous = temp;
    	temp.next = newNode;
    	return newNode;


    }
    
    public void printToString() {
    	Node node = this.head.next;
//    	System.out.println(node.element);
    	while(node!=this.tail) {
    		System.out.print((int)node.element+" ");
    		node = node.next;
    	}
    	System.out.println();
    }

     
    

}

