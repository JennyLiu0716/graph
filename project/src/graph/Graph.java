package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * graph contruction - adjacency list representation
 */
public class Graph {

    public int vertexNum;
    public int edgeNum;
    public LinkedList<Integer>[] adjlist; // corresponding to simplifidied node list

    // not used
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
    @SuppressWarnings("unchecked")
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
            sc.close();
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
        sc.close();

    }


}