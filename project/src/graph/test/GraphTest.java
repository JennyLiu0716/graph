package graph.test;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Test;

import graph.Graph;

public class GraphTest {
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
    public void split_connectedcomponentsConnectedTest() throws FileNotFoundException {
        connectedUIGinit();
        for (int i = 0; i < 100; i++) {
            assertTrue(this.graphs[i].connectedComponents.size() == 1);
            assertTrue(this.graphs[i].connectedComponents.get(0).vertexNum == this.graphs[i].vertexNum);
            assertTrue(this.graphs[i].connectedComponents.get(0).edgeNum == this.graphs[i].edgeNum);

        }
    }

    @Test
    public void split_disconnectedcomponentsConnectedTest() throws FileNotFoundException {
        disconnectedUIGinit();
        for (int i = 0; i < 100; i++) {

            assertTrue(this.graphs[i].connectedComponents.size() > 1);
            // System.out.println(i);
            for (Graph g : this.graphs[i].connectedComponents) {
                assertTrue(g.vertexNum < this.graphs[i].vertexNum);
            }
        }
    }
}
