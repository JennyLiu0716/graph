package graph.generation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.stream.IntStream;
import utils.Functions;

/**
 * a unit interval graph & interval graph generator
 * First, we assume for vertex 1-n, the left end for the interval representation
 * is non-decreasing
 * To generate an interval graph, it suffices to specify the last neighbor of
 * each vertex.
 * To generate a unit interval graph, we also need to ensure the sequence of
 * last neighbor is non-decreasing.
 * Then, we rename the vertices to get a general graph
 */

public class GraphGenerator {

    /**
     * UPDATED
     * interface for graph generator
     * 
     * @param vertexNumber Number of vertices in the generated graph. If random is
     *                     true, this number of vertices is at most
     *                     {@code vertexNumber}, it should be a positive number
     * @param connected    Whether the generated graph is connected
     * @param path         path to store the file
     * @param random       whether the number of vertices is random
     * @param graphNum     number of graphs to be generated
     * @param unit         whether the generated graphs are unit interval graphs
     */
    public static void generator(int vertexNumber, boolean connected, String path, boolean random, int graphNum,
            boolean unit) {

        String graphName;
        if (unit) {
            graphName = "unit_interval_graph";
        } else {
            graphName = "interval_graph";
        }
        if (connected) {
            graphName += "_connected";
            int vertexNum;
            int[] lastNeighbors;

            for (int j = 1; j <= graphNum; j++) {

                vertexNum = vertexNumber;

                if (random) {
                    Random rand = new Random();
                    vertexNum = 1 + rand.nextInt(vertexNumber);
                }

                if (unit) {
                    lastNeighbors = unitIntervalGraph(vertexNum, connected);
                } else {
                    lastNeighbors = intervalGraph(vertexNum, connected);
                }

                generate(graphName, path, Integer.toString(j), lastNeighbors);
            }
        } else {
            // generate disconnected graph
            graphName += "_disconnected";
            int vertexNum;
            int[] lastNeighbors;

            for (int j = 1; j <= graphNum; j++) {

                vertexNum = vertexNumber;

                // for each graph, how many vertices are there? at least 2
                if (random) {
                    Random rand = new Random();
                    vertexNum = 2 + rand.nextInt(vertexNumber - 1);
                }

                // how many connected components? 2...vertexNum
                Random rand = new Random();
                int cc = 2 + rand.nextInt(vertexNum - 1);

                // how many nodes for each connected component? At least 1, sum = vertexNumber
                // start from index 0
                int[] cc_nodes = Functions.randomList(cc, vertexNum);

                int[] full_lastNeighbors = new int[vertexNum + 1];
                int pointer = 1;
                int previous_node = 0;

                // for each connected component, generate the last neighbors
                // merge to the full last neighbors
                for (int component = 1; component <= cc; component++) {
                    int nodenumber = cc_nodes[component - 1];

                    if (unit) {
                        lastNeighbors = unitIntervalGraph(nodenumber, connected);
                    } else {
                        lastNeighbors = intervalGraph(nodenumber, connected);
                    }

                    for (int k = 1; k < lastNeighbors.length; k++) {
                        full_lastNeighbors[pointer++] = lastNeighbors[k] + previous_node;
                    }
                    previous_node += nodenumber;

                }
                generate(graphName, path, Integer.toString(j), full_lastNeighbors);
            }

        }

    }

    /**
     * UPDATED
     * 
     * Generate one interval graph.
     * 
     * Assume that the intervals for vertex 1 to n has non-decreasing left end.
     * Randomly assign the last neighbor for each vertex.
     * The number for the (i+1)st vertex cannot be smaller than i+1.
     * 
     * @param vertexNum
     * @param connected
     * @return
     */
    public static int[] intervalGraph(int vertexNum, boolean connected) {
        // store the last neighbor for each vertex
        // the graph generated has the vertices from 1 to n
        int lastNeighbors[] = new int[vertexNum + 1];

        // For connected graph generation, since we assume the left end for vertex i is
        // i, to ensure the connectness, when we generate the last neighbor for vertex
        // i:
        // If vertex i is less than the max node included, the last neighbor does not
        // matter
        // If vertex i is the max node included, the last neighbor of node i should be
        // not less than i+1
        // So, we need to record the maximum vertex which has been covered.
        // Since if vertex i is covered, then vertex i-1, i-2,...,1 must have been
        // covered.
        int maxcovered = 1;

        Random rand = new Random();
        for (int i = 1; i <= vertexNum; i++) {

            if (connected) {
                if (maxcovered > i) {
                    // any number from i to n, since i is assumed to be the left end
                    if (vertexNum - i + 1 <= 0)
                        lastNeighbors[i] = i;
                    else
                        lastNeighbors[i] = i + rand.nextInt(vertexNum - i + 1);
                    if (lastNeighbors[i] > vertexNum)
                        lastNeighbors[i] = vertexNum;

                    if (lastNeighbors[i] > maxcovered)
                        maxcovered = lastNeighbors[i];
                } else {
                    // any number from i+1 to n
                    if (vertexNum - i <= 0)
                        lastNeighbors[i] = i;
                    else
                        lastNeighbors[i] = i + 1 + rand.nextInt(vertexNum - i);
                    if (lastNeighbors[i] > vertexNum)
                        lastNeighbors[i] = vertexNum;

                    if (lastNeighbors[i] > maxcovered)
                        maxcovered = lastNeighbors[i];
                }
            } else {
                // any number from i to n, since i is assumed to be the left end
                if (vertexNum - i + 1 <= 0)
                    lastNeighbors[i] = i;
                else
                    lastNeighbors[i] = i + rand.nextInt(vertexNum - i + 1);
                if (lastNeighbors[i] > vertexNum)
                    lastNeighbors[i] = vertexNum;
            }

        }
        return lastNeighbors;
    }

    /**
     * UPDATED
     * 
     * Generate one unit interval graph.
     * 
     * Randomly assign the last neighbor for each vertex in order.
     * The last neighbor for the (i+1)st vertex should not be smaller than i+1, and
     * the last neighbor of the ith vertex (unit).
     * 
     * @param vertexNum
     * @param connected
     * @return
     */
    public static int[] unitIntervalGraph(int vertexNum, boolean connected) {

        // same as interval graph
        int lastNeighbors[] = new int[vertexNum + 1];
        int maxcovered = 1;

        // record the last neighbor for the previous node
        // additional condition for unit interval graph, the right end is non-decreasing
        int previous = 1;

        Random rand = new Random();
        for (int i = 1; i <= vertexNum; i++) {

            if (connected) {
                if (maxcovered > i) {
                    // satisfy the non-decreasing right end condition
                    // since it is connected, previous should be not less than i
                    if (vertexNum - previous + 1 <= 0)
                        lastNeighbors[i] = previous;
                    else
                        lastNeighbors[i] = previous + rand.nextInt(vertexNum - previous + 1);

                    if (lastNeighbors[i] > vertexNum)
                        lastNeighbors[i] = vertexNum;

                    if (lastNeighbors[i] > maxcovered)
                        maxcovered = lastNeighbors[i];
                    previous = lastNeighbors[i];

                } else {
                    // max covered = i
                    // any number from i+1 to n
                    if (vertexNum - i <= 0)
                        lastNeighbors[i] = i + 1;
                    else
                        lastNeighbors[i] = i + 1 + rand.nextInt(vertexNum - i);

                    if (lastNeighbors[i] > vertexNum)
                        lastNeighbors[i] = vertexNum;

                    if (lastNeighbors[i] > maxcovered)
                        maxcovered = lastNeighbors[i];
                    previous = lastNeighbors[i];

                }
            } else {
                // to ensure it is a unit interval graph, the last neighbor has to satisfy:
                // if the last neighbor for the previous node is less than i: since the left end
                // for node i is i, the last neighbor for node i should not be less than i
                // if the last neighbor for the previous node is large than or equal to i: the
                // last neighbor for node i should not be less than that of the previous node
                if (previous < i) {
                    if (vertexNum - i + 1 <= 0)
                        lastNeighbors[i] = i;
                    else
                        lastNeighbors[i] = i + rand.nextInt(vertexNum - i + 1);

                    if (lastNeighbors[i] > vertexNum)
                        lastNeighbors[i] = vertexNum;

                } else {
                    if (vertexNum - previous + 1 <= 0)
                        lastNeighbors[i] = previous;
                    else
                        lastNeighbors[i] = previous + rand.nextInt(vertexNum - previous + 1);
                    if (lastNeighbors[i] > vertexNum)
                        lastNeighbors[i] = vertexNum;
                }
                previous = lastNeighbors[i];
            }
        }
        return lastNeighbors;
    }

    /**
     * UPDATED
     * renaming the vertices so that the non-decreasing left end is not exactly of
     * vertex 1 to n
     * Write the generated graph into a file
     * 
     * @param graphName
     * @param path
     * @param prefix
     * @param lastNeighbors index 1...n, element 1...n
     */
    public static void generate(String graphName, String path, String prefix, int[] lastNeighbors) {
        String filename = path + graphName + prefix + ".txt";
        int[] permutation = Functions.shuffle(lastNeighbors.length - 1);

        int nodeNum = permutation.length;

        // calculate edgeNum, for file writing
        int edgeNum = 0;

        for (int i = 1; i <= nodeNum; i++) {
            edgeNum += lastNeighbors[i] - i;
        }

        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println("p edge " + nodeNum + " " + edgeNum);

            // find all edges by the non-decreasing left end
            // Shuffle the numbers of the vertices.
            // renaming the vertices by permutation
            for (int i = 1; i <= nodeNum; i++) {
                for (int j = i + 1; j <= lastNeighbors[i]; j++) {
                    writer.println("e " + permutation[i - 1] + " " + permutation[j - 1]);
                }
            }

            writer.close();
            System.out.println("successful generation");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

}