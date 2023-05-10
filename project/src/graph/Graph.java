package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;
//import utils.DList;

  /**
   * todo: why use a doubly linked list for construction?
   * doubly linked list supports traversing in both ways due to previous and next pointer
   * Node deletion can be done in O(1) 
   * A new node may be readily added before an existing node
   */
public class Graph {
    public int[][]adjacentGraph;
    public int vertexNum;
    public int edgeNum;

  /**
   * Load the graph from a text file in the DIMACS format.
   *
   * @param the path of the graph file
   * @return a Graph
   * @throws FileNotFoundException when the file cannot be found.
   */
    public Graph readFile(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner sc = new Scanner(file);
        String comment = sc.nextLine();
        String problem = sc.nextLine();
        String[] problems = problem.split(" ");
        vertexNum = Integer.parseInt(problems[2]);
        edgeNum = Integer.parseInt(problems[3]);
        adjacentGraph = new int[vertexNum][];
        LinkedList[] nodelist= new LinkedList[vertexNum];
//        DList[] nodelist = new DList[vertexNum];
        for (int i=0;i<edgeNum;i++) {
            String edge = sc.nextLine();
            String[] edges = edge.split(" ");
            int a = Integer.parseInt(edges[1])-1;
            int b = Integer.parseInt(edges[2])-1;
            Node nodea = new Node(a);
            Node nodeb = new Node(b);
            
            if (nodelist[a]==null) {
            	nodelist[a] = new LinkedList<Node>();
            	Node head = new Node(-1);
            	Node tail = new Node(-1);
            	nodelist[a].add(head);
            	nodelist[a].add(tail);
            }
//            if (nodelist[a]==null) nodelist[a] = new DList();
            
            if (nodelist[b]==null) {
            	nodelist[b] = new LinkedList<Node>();
            	Node head = new Node(-1);
            	Node tail = new Node(-1);
            	nodelist[b].add(head);
            	nodelist[b].add(tail);
            }
//            if (nodelist[b]==null) nodelist[b] = new DList();
            
            int sizea = nodelist[a].size();
            nodelist[a].add(sizea-1,nodeb);
//            nodelist[a].insertLastNode(nodeb);
            
            Node firsta = (Node) nodelist[a].removeFirst();
            firsta.degree++;
            nodelist[a].addFirst(firsta);
//            nodelist[a].head.degree++;
            
            int sizeb = nodelist[b].size();
            nodelist[b].add(sizeb-1,nodea);
//            nodelist[b].insertLastNode(nodea);
            
            Node firstb = (Node) nodelist[b].removeFirst();
            firstb.degree++;
            nodelist[b].addFirst(firstb);         
//            nodelist[b].head.degree++;
        }
        
        for(int k=0;k<nodelist.length;k++) {

        	Node head = (Node) nodelist[k].getFirst();
        	Node tail = (Node) nodelist[k].getLast();
        	int size = nodelist[k].size();
        	//degree starts from 0
            adjacentGraph[k] = new int[head.degree];
            Node nodee = head.next;

//            System.out.println("size"+nodelist[k].size());

            for (int i=1;i<size-1;i++) {
            	Node node = (Node) nodelist[k].get(i);
            	adjacentGraph[k][i-1] = (int) node.element;
            }

        }
        
        for(int i=0;i<adjacentGraph.length;i++) {
        	for (int j=0;j<adjacentGraph[i].length;j++) {
        		System.out.print(adjacentGraph[i][j]+",");
        	}System.out.println();
        }
        Graph g = new Graph();
		g.adjacentGraph = adjacentGraph;
        return g;
    }
    
}
