package graph.perfect;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import graph.Graph;
import graph.algorithms.GraphSearch;

/**
 * 
 * @author Liu Chenxi, Yixin Cao (February, 2023)
 *
 *         A bfs search to find an end vertex, and then LBFS delta from this
 *         vertex.
 * 
 *         Check whether the ordering is a valide umbrella ordering.
 * 
 */
public class UnitInterval extends Interval {

    /**
     * todo: add a constructor
     */
    public UnitInterval(Graph graph) {
    }
    
    public static boolean recognize(Graph graph) {
        /*
         * 
         * //LBFS +
         * int[] list = new int[graph.length];
         * for(int i=0;i<list.length;i++) {
         * list[i] = i;
         * }
         * int[] LBFS0 = new int [graph.length];
         * LBFS0 = GraphSearch.lbfs(graph,list,2);
         * LBFS = GraphSearch.lbfs(graph, LBFS0,2);
         */

        // LBFSdelta
        int endVertex = GraphSearch.bfsEndVertex(graph);
        int[] LBFS = GraphSearch.lbfsDelta(graph, endVertex);

        int[] vertexOrder = new int[LBFS.length];
        // convert the sigma order to the vertex order
        // sigma -> index: vertex num; element: order
        // vertexOrder -> index: order; element: vertex 
        for (int i = 0; i < LBFS.length; i++) {
        	// renum vertex
            vertexOrder[LBFS[i]] = i;
        }
        System.out.println(Arrays.toString(vertexOrder));
        boolean a = GraphSearch.intervalOrdering(graph, vertexOrder);
        int n = vertexOrder.length;
        int[] vertexOrderR = new int[n];
        for (int i = 0; i < n; i++) {
        	vertexOrderR[n - i - 1] = vertexOrder[i];
        }
        boolean b = GraphSearch.intervalOrdering(graph, vertexOrderR);
        System.out.println("a"+a);
        System.out.println("b"+b);
        return a && b;
    }

   /**
     * Draw an interval model for the graph.
     *
     * todo: output the model in tikz format.
     */
    public void visualize() {
    }

    /**
     * todo: catch FileNotFoundException.
     */
    public static void main(String[] args) throws FileNotFoundException {
        // when the input file is given in the command line, process it.
        Graph g = new Graph();
        if (0 < args.length) {
            boolean ans = recognize(g.readFile(args[0]));
            System.out.println("The graph is " + (ans?"":"not ") + "a unit interval graph");
             return;
        }
        if (0 == args.length)
            System.out.println("Please enter the path for the input grap, or q to quit:\n");

//         @SuppressWarnings("resource")
//         Scanner sc = new Scanner(System.in);
//         while (sc.hasNext()) {
//             String path = sc.nextLine().trim();
//             if (path.equals("q"))
//                 break;
//             // read the file and process it
//             boolean ans = recognize(g.readFile(path));
//             System.out.println("The graph is " + (ans?"":"not ") + "a unit interval graph");
        for(int i=1;i<=100;i++) {
        	String path = "./graphs/UnitIntervalGraph_"+i+".txt";
        	boolean ans = recognize(g.readFile(path));
        	System.out.println(i+" The graph is " + (ans?"":"not ") + "a unit interval graph");
        
        }

//         }
    }
}
