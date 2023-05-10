package graph.perfect;

import graph.Graph;

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
public class Chordal extends Graph {

    /**
     * decide whether the graph is chordal.
     */
    public static boolean recognize(Graph graph) {
        return false;
    }

    /**
     * find a maximum clique
     */
   public int[] maxClique() {
        return null;
    }

    /**
     * Calculate the chromatic number of the graph.
     */
   public int chromatic() {
       return maxClique().length;
   }
    
    /**
     * Find a minimum coloring of the graph.
     * ans[i] is the set of vertices receiving color i.
     */
   public int[][] coloring() {
       
       return null;
    }

}
