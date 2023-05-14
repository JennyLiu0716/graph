package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;
import utils.DList;

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
//        LinkedList[] nodelist= new LinkedList[vertexNum];
        DList[] nodelist = new DList[vertexNum];
        for (int i=0;i<edgeNum;i++) {
            String edge = sc.nextLine();
            String[] edges = edge.split(" ");
            int a = Integer.parseInt(edges[1])-1;
            int b = Integer.parseInt(edges[2])-1;
            Node nodea = new Node(a);
            Node nodeb = new Node(b);
//            if (nodelist[a]==null) nodelist[a] = new LinkedList();
            if (nodelist[a]==null) nodelist[a] = new DList();
//            if (nodelist[b]==null) nodelist[b] = new LinkedList();
            if (nodelist[b]==null) nodelist[b] = new DList();
            nodelist[a].insertLastNode(nodeb);
            nodelist[a].head.degree++;
            nodelist[b].insertLastNode(nodea);
            nodelist[b].head.degree++;
        }
        
        for(int k=0;k<nodelist.length;k++) {
            adjacentGraph[k] = new int[nodelist[k].head.degree+1];
            Node nodee = nodelist[k].head.next;
            int index = 0;
            while(nodee!=nodelist[k].tail) {
                adjacentGraph[k][index] = (int)nodee.element;
//                System.out.print(nodee.element);
                index++;
                nodee = nodee.next;
            }
//            System.out.println();
        }
//        for(int i=0;i<adjacentGraph.length;i++) {
//            for(int j=0;j<adjacentGraph[i].length;j++) {
//                System.out.print(adjacentGraph[i][j]+1);
//            }System.out.println();
//        }
	Graph g = new Graph();
	g.adjacentGraph = adjacentGraph;
        return g;
    }
    
}
