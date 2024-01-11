package graph.test;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;

import org.junit.BeforeClass;
import org.junit.Test;

import graph.Graph;
import graph.algorithms.GraphSearch;

public class GraphSeachTest {
    Graph[] graphs = new Graph[100];

    @Test
    public void connectedUIGinit() throws FileNotFoundException {
        String path = "";
        for (int i = 1; i <= 100; i++) {
            path = ".\\project\\graphs\\unit_interval_graph_connected" + i + ".txt";
            Graph graph = new Graph();
            graph.readFile(path);
            this.graphs[i - 1] = graph;
        }

    }

    @Test
    public void threeSweepConnectedUIGTest() throws FileNotFoundException {
        int passed = 0;
        int rejected = 0;
        LinkedList<Integer> rejectedgraph = new LinkedList();
        connectedUIGinit();
        for (int i = 0; i < 100; i++) {
            if (GraphSearch.threeSweepUIG(this.graphs[i])) {
                passed++;
            } else {
                rejected++;
                rejectedgraph.add(i);
            }
            System.out.println("three sweep: passed " + passed + " rejected " + rejected);
        }
        for (int i : rejectedgraph) {
            System.out.println(i);
        }

    }

    @Test
    public void twoSweepConnectedUIGTest() throws FileNotFoundException {
        // pass
        int passed = 0;
        int rejected = 0;
        LinkedList<Integer> rejectedgraph = new LinkedList<>();

        connectedUIGinit();
        for (int i = 0; i < 100; i++) {
            if (GraphSearch.twoSweepUIG(this.graphs[i])) {
                passed++;
            } else {
                rejected++;
                rejectedgraph.add(i);
            }
            System.out.println("three sweep: passed " + passed + " rejected " + rejected);
        }

        for (int i : rejectedgraph) {
            System.out.println(i);
        }
    }
}
