package graph.test;

import static org.junit.Assert.assertFalse;
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
            // System.out.println(i);
            path = ".\\project\\graphs\\unit_interval_graph_connected" + i + ".txt";
            Graph graph = new Graph();
            graph.readFile(path);
            this.graphs[i - 1] = graph;
        }

    }

    @Test
    public void disconnectedUIGinit() throws FileNotFoundException {
        String path = "";
        for (int i = 1; i <= 100; i++) {
            // System.out.println(i);
            path = ".\\project\\graphs\\unit_interval_graph_disconnected" + i + ".txt";
            Graph graph = new Graph();
            graph.readFile(path);
            this.graphs[i - 1] = graph;
        }

    }

    @Test
    public void connectedIGinit() throws FileNotFoundException {
        String path = "";
        for (int i = 1; i <= 100; i++) {
            // System.out.println(i);
            path = ".\\project\\graphs\\interval_graph_connected" + i + ".txt";
            Graph graph = new Graph();
            graph.readFile(path);
            this.graphs[i - 1] = graph;
        }

    }

    @Test
    public void disconnectedIGinit() throws FileNotFoundException {
        String path = "";
        for (int i = 1; i <= 100; i++) {
            // System.out.println(i);
            path = ".\\project\\graphs\\interval_graph_disconnected" + i + ".txt";
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
        assertTrue(passed == 100);

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
        assertTrue(passed == 100);
    }

    @Test
    public void threeSweepDisconnectedUIGTest() throws FileNotFoundException {
        int passed = 0;
        int rejected = 0;
        LinkedList<Integer> rejectedgraph = new LinkedList();
        disconnectedUIGinit();
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
        assertTrue(passed == 100);

    }

    @Test
    public void twoSweepDisconnectedUIGTest() throws FileNotFoundException {
        // pass
        int passed = 0;
        int rejected = 0;
        LinkedList<Integer> rejectedgraph = new LinkedList<>();

        disconnectedUIGinit();
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
        assertTrue(passed == 100);
    }

    @Test
    public void interval_recognition_connectedTest() throws FileNotFoundException {
        // pass
        int passed = 0;
        int rejected = 0;
        LinkedList<Integer> rejectedgraph = new LinkedList<>();

        connectedIGinit();
        for (int i = 0; i < 100; i++) {
            if (GraphSearch.interval_recognition(this.graphs[i])) {
                passed++;
            } else {
                rejected++;
                rejectedgraph.add(i);
            }
            System.out.println("interval recognition: passed " + passed + " rejected " + rejected);
        }

        for (int i : rejectedgraph) {
            System.out.println(i);
        }
        assertTrue(passed == 100);
    }

    @Test
    public void interval_recognition_disconnectedTest() throws FileNotFoundException {
        // pass
        int passed = 0;
        int rejected = 0;
        LinkedList<Integer> rejectedgraph = new LinkedList<>();

        disconnectedIGinit();
        for (int i = 0; i < 100; i++) {
            if (GraphSearch.interval_recognition(this.graphs[i])) {
                passed++;
            } else {
                rejected++;
                rejectedgraph.add(i);
            }
            System.out.println("interval recognition: passed " + passed + " rejected " + rejected);
        }

        for (int i : rejectedgraph) {
            System.out.println(i);
        }
        assertTrue(passed == 100);
    }

    @Test
    public void c4() throws FileNotFoundException {
        String path = ".\\project\\graphs\\c4.txt";
        Graph graph = new Graph();
        graph.readFile(path);
        assertFalse(GraphSearch.interval_recognition(graph));
        assertFalse(GraphSearch.threeSweepUIG(graph));
        assertFalse(GraphSearch.twoSweepUIG(graph));
    }

    @Test
    public void not1() throws FileNotFoundException {
        String path = ".\\project\\graphs\\not1.txt";
        Graph graph = new Graph();
        graph.readFile(path);
        assertFalse(GraphSearch.interval_recognition(graph));
        assertFalse(GraphSearch.threeSweepUIG(graph));
        assertFalse(GraphSearch.twoSweepUIG(graph));
    }

    @Test
    public void not2() throws FileNotFoundException {
        String path = ".\\project\\graphs\\not2.txt";
        Graph graph = new Graph();
        graph.readFile(path);
        assertFalse(GraphSearch.interval_recognition(graph));
        assertFalse(GraphSearch.threeSweepUIG(graph));
        assertFalse(GraphSearch.twoSweepUIG(graph));
    }

    @Test
    public void not3() throws FileNotFoundException {
        String path = ".\\project\\graphs\\not3.txt";
        Graph graph = new Graph();
        graph.readFile(path);
        assertFalse(GraphSearch.interval_recognition(graph));
        assertFalse(GraphSearch.threeSweepUIG(graph));
        assertFalse(GraphSearch.twoSweepUIG(graph));
    }
}
