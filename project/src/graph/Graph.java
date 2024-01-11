package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.stream.IntStream;

import org.w3c.dom.css.ViewCSS;

/**
 * graph contruction - adjacency list representation
 */
public class Graph {

    public int vertexNum;
    public int edgeNum;
    public LinkedList<Integer>[] adjlist; // corresponding to simplifidied node list

    // Todo:
    public LinkedList<Graph> connectedComponents;
    public int[] nodelist; // 0...n-1 maybe not consecutive, not ordered
    public int[] simplified_nodelist; // 0...vertexNum-1 consecutive

    /**
     * Load the graph from a text file in the DIMACS format.
     * 
     * The graph file format:
     * https://users.aalto.fi/~tjunttil/bliss/fileformat.html
     * 
     * Comments:
     * In the beginning of the file, there can be comment lines that start with the
     * character c.
     * For instance {c The constraint graph of a CNF formula}.
     * 
     * Problem definition line:
     * After the comment lines, there must be the “problem definition line” of the
     * form {p edge N E}.
     * Here N is the number of vertices and E is the number of edges in the graph.
     * In the file format, the vertices are numbered from 1 to N.
     * 
     * Vertex colors:
     * After the problem definition line, the next lines of the form {n v c} define
     * the colors of the vertices
     * v is the number of a vertex and c is the color of that vertex.
     * The color c should be a non-negative integer fitting in the domain of the
     * unsigned int type in the C++ programming language.
     * It is not necessary to include a color definition line for each vertex, the
     * default color for a vertex is 0.
     * If the color of a vertex is defined more than once, the last definition
     * applies.
     * 
     * Edges. Following the color definition lines, the next E lines of the form {e
     * v1 v2}
     * describe the edges in the graph, where 1 ≤ v1,v2 ≤ N are the numbers of the
     * vertices connected by the edge.
     * Multiple definitions of the same edge are ignored.
     * 
     * @param the path of the graph file
     * @return a Graph
     * @throws FileNotFoundException when the file cannot be found.
     */
    public void readFile(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner sc = new Scanner(file);
        String curline = sc.nextLine();
        String[] lineStrings = curline.split(" ");

        // ignore the comment lines
        while (lineStrings[0].equals("c")) {
            curline = sc.nextLine();
            lineStrings = curline.split(" ");
        }

        // process the problem definition line
        this.vertexNum = Integer.parseInt(lineStrings[2]);
        this.edgeNum = Integer.parseInt(lineStrings[3]);
        this.adjlist = new LinkedList[vertexNum];

        for (int i = 0; i < vertexNum; i++) {
            this.adjlist[i] = new LinkedList<>();
        }

        this.nodelist = IntStream.range(0, vertexNum).toArray();
        this.simplified_nodelist = this.nodelist;

        // no edges
        if (this.edgeNum == 0) {
            this.connectedComponents = new LinkedList<>();
            split_connectedcomponents();
            return;
        }

        // ignore the vertex colors
        curline = sc.nextLine();
        lineStrings = curline.split(" ");
        while (lineStrings[0].equals("n")) {
            curline = sc.nextLine();
            lineStrings = curline.split(" ");
        }

        // process the edges, nodes from [1...n] to [0...n-1]
        while (lineStrings[0].equals("e")) {

            int vertex1 = Integer.parseInt(lineStrings[1]) - 1;
            int vertex2 = Integer.parseInt(lineStrings[2]) - 1;

            this.adjlist[vertex1].add(vertex2);
            this.adjlist[vertex2].add(vertex1);

            if (sc.hasNextLine()) {
                curline = sc.nextLine();
                lineStrings = curline.split(" ");
            } else
                break;
        }

        // process connected components
        this.connectedComponents = new LinkedList<>();
        // this.connectedComponents.add(this);
        // System.out.println(this.connectedComponents.size());
        split_connectedcomponents();
    }

    /**
     * https://www.geeksforgeeks.org/connected-components-in-an-undirected-graph/
     * 
     */
    private void split_connectedcomponents() {

        int V = this.vertexNum;

        // Mark all the vertices as not visited
        boolean[] visited = new boolean[V];
        for (int v = 0; v < V; ++v) {
            if (!visited[v]) {
                // save all reachable vertices from v
                LinkedList<Integer> nodes = new LinkedList();
                DFSUtil(v, visited, nodes);
                // System.out.println(nodes.size()+" "+this.vertexNum);
                this.connectedComponents.add(cc_subgraph(nodes));
            }
        }
    }

    private void DFSUtil(int v, boolean[] visited, LinkedList<Integer> nodes) {

        // Mark the current node as visited store
        visited[v] = true;
        nodes.add(v);

        // Recur for all the vertices
        // adjacent to this vertex
        for (int x : this.adjlist[v]) {
            if (!visited[x])
                DFSUtil(x, visited, nodes);
        }
    }

    private Graph cc_subgraph(LinkedList<Integer> nodes) {
        Graph g = new Graph();
        int v = nodes.size();
        g.vertexNum = v;
        g.simplified_nodelist = IntStream.range(0, v).toArray();

        g.nodelist = new int[v];
        int p = 0;
        for (int i : nodes) {
            g.nodelist[p++] = i;
        }

        int transfer[] = new int[this.vertexNum];
        for (int i = 0; i < v; i++) {
            transfer[g.nodelist[i]] = i;
        }

        g.adjlist = new LinkedList[v];

        for (int i = 0; i < v; i++) {
            int original_node = g.nodelist[i];
            g.adjlist[i] = new LinkedList<>();
            LinkedList<Integer> neighbors = this.adjlist[original_node];
            for (int j : neighbors) {
                g.adjlist[i].add(transfer[j]);
            }
            g.edgeNum += neighbors.size();
        }
        g.edgeNum = g.edgeNum / 2;

        return g;
    }
}