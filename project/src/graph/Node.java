package graph;

public class Node<T> {
    public T element;
    public Node next;
    public Node previous;

    public Node(T e) {
        element = e;
        next = previous = null;
    }

}