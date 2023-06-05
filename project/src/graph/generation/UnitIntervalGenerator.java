package graph.generation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.LinkedList;

/**
* To generate a unit interval graph, it suffices to specify the last neighbor of each vertex, such that the sequence is not non-decreasing.
*/

public class UnitIntervalGenerator {

    /**
     * Assign a random last neighbor for each vertex in order.
     * The number for the (i+1)st vertex cannot be smaller than i+1, or the number for the ith vertex.
     * Stop the process when the number is already n.
     
     * @param n: the number of vertices
     */
    static int[] generate(int n) {
        int lastNeighbors[] = new int[n];
        
        Random rand = new Random();
        int cur = 0;
        for (int i = 0; i < n && cur < n - 1; i++) {
            cur = cur + rand.nextInt(n - cur);
            lastNeighbors[i] = cur;
        }
        return lastNeighbors;
  }

    /**
     * Assign a random last neighbor for each vertex in order.
     * 
     * Shuffle the numbers when generating the ajdacency lists.
     */
    static void write(int[] lastNeighbors, String filename) {
    }

    /**
     * The interface for the user to call. 
     * They can ask for a certain number of unit interval graphs of a fixed order, or random orders (each independent).
     *
     * @param path: the path to store the generated files
     * @param prefix: the prefix for the generated files, e.g., uig01.txt, uig02.txt, ... if prefix = "uig"
     * @param n: the number of vertices
     * @param random: if 0, all the generated graphs have the same order; otherwise, the order is at most n
     * @param number: the number of graphs to be generated
     */
    public static void write(String path, String prefix, int n, boolean random, int number) {
        for (int i = 1; i <= number; i++) {
            int n0 = n;
            if (random) {
                Random rand = new Random();
                n0 = rand.nextInt(n);
            }
        }
    }

        
}
