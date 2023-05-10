package graph;

import java.util.LinkedList;

public class Node<T> {
    public T element;
    public Node next;
    public Node previous;
//        Node head;
    public int degree;//for node degree for list when it is added
//    public Node curlistNode;
    public LinkedList curlist;

    

    public Node(T e) {
        element = e;
        next = previous = null;
    }
   
}
