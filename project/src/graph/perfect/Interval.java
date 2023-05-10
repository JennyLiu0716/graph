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
public class Interval extends Chordal {

    /**
     * decide whether the graph is an interval graph.
     */
    public static boolean recognize(Graph graph) {
        return false;
    }

    /**
     * Draw an interval model for the graph.
     *
     * todo: output the model in tikz format.
     */
    public void visualize() {
    }

}
