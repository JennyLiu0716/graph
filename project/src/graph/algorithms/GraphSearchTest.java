package graph.algorithms;


import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import graph.Graph;
import graph.perfect.*;

// delete the file
class GraphSearchTest {
	
// 	Graph[] graphs = new Graph[101];
	
// 	@BeforeClass
// 	void init() throws FileNotFoundException {
// 		Graph g = new Graph();
// 		for(int i=1;i<=100;i++) {
// 			String path = "./graphs/UnitIntervalGraph_"+i+".txt";
// 	      	Graph graph = g.readFile(path);
// 	      	graphs[i] = graph;
// 	    }
// 	}
	
// 	@Test
// 	void getDegreeOrderTest() throws FileNotFoundException {
// 		Graph graph = new Graph();
// 		for(int i=1;i<=100;i++) {
// 			String path = "./graphs/UnitIntervalGraph_"+i+".txt";
// 	      	Graph g = graph.readFile(path);
// 	      	int[][] adjgraph = g.adjacentGraph;
// 	      	int[] degree = GraphSearch.getDegreeOrder(g);
// 	      	for(int j=1;j<degree.length;j++) {
// 	      		assertTrue(adjgraph[degree[j]].length<=adjgraph[degree[j-1]].length);
// 	      	}
// 		}
// 	}
// 	@Test
// 	void sortAdjacencyListsTest() throws FileNotFoundException {
// 		Graph graph = new Graph();
// 		for(int i=1;i<=100;i++) {
// 			String path = "./graphs/UnitIntervalGraph_"+i+".txt";
// 	      	Graph g = graph.readFile(path);
// 			int[][] adjgraph = g.adjacentGraph;
// 			int[] degree = GraphSearch.getDegreeOrder(g);
// 	      	int[][] adj = GraphSearch.sortAdjacencyLists(adjgraph, degree);
// 	      	for(int j=0;j<adj.length;j++) {
// 	      		for(int k=1;k<adj[j].length;k++) {
// 	      			assertTrue(adjgraph[adj[j][k]].length<=adjgraph[adj[j][k-1]].length);
// //	      			System.out.println(adjgraph[adj[j][k]].length+" "+adjgraph[adj[j][k-1]].length);
// 	      		}
// 	      	}
// 		}
// 	}
// 	@Test
// 	void bfsConnectedTest() throws FileNotFoundException {
// 		Graph graph = new Graph();
// 		for(int i=1;i<=100;i++) {
// 			String path = "./graphs/UnitIntervalGraph_"+i+".txt";
// 	      	Graph g = graph.readFile(path);
// 	      	int[] level = GraphSearch.bfsConnected(g,0);
// 	      	int l = 1;
// 	      	int[][] adjgraph = g.adjacentGraph;
// 	      	int[] degree = GraphSearch.getDegreeOrder(g);
// 	      	int[][] adj = GraphSearch.sortAdjacencyLists(adjgraph, degree);
// 	      	boolean found ;
// 	      	// for each vertex on level (i), it must be a neighbor of one of the vertex on level (i-1)
// 	      	for(int j=0;j<level.length&&level[j]!=0;j++) {
// 	      		found = false;
// 	      		l = level[j];
// 	      		for(int k = 0;k<level.length&&k!=j;k++) {
// 	      			if (level[k]==l-1) {
// 	      				for(int m=0;m<adj[k].length;m++) {
// 	      					if (adj[k][m]==j) {
// 	      						found = true;
// 	      						k = level.length;
// 	      						break;
// 	      					}
// 	      				}
// 	      			}
// 	      		}
// //	      		System.out.println(j);
// 	      		assertTrue(found);
// 	      		l++;
// 	      	}
// 		}
// 	}
// 	@Test
// 	void endVertexBFSTest() throws FileNotFoundException {
// 		Graph graph = new Graph();
// 		for(int i=1;i<=100;i++) {
// 			String path = "./graphs/UnitIntervalGraph_"+i+".txt";
// 	      	Graph g = graph.readFile(path);
// 			// test by definition: min degree on max level
// 			int[][] adjgraph = g.adjacentGraph;
// 	      	int[] degree = GraphSearch.getDegreeOrder(g);

// 			int[][] adj = GraphSearch.sortAdjacencyLists(adjgraph, degree);
// 	      	int endVertex = GraphSearch.endVertexBFS(g);
// //	      	Arrays.sort(level);
// //	        int largestLevel = level[level.length - 1];
// 	      	int largestlevel = 0;
// 	      	int[] level = GraphSearch.bfsConnected(g,0);
// 	      	for(int j=0;j<level.length;j++) {
// 	      		if (level[j]>largestlevel) {
// 	      			largestlevel = level[j];
// 	      		}
// 	      	}
// 	        assertTrue(level[endVertex]==largestlevel);
// 	        for(int j=0;j<level.length&&j!=endVertex;j++) {
// 	        	if (level[j]==largestlevel) {
// 	        		assertTrue(adj[endVertex].length<=adj[j].length);
// 	        	}
// 	        }
// 		}
// 	}
// 	@Test
// 	void lbfsCoreTest() throws FileNotFoundException {
// 		Graph graph = new Graph();
// 		for(int i=1;i<=100;i++) {
// 			String path = "./graphs/UnitIntervalGraph_"+i+".txt";
// 	      	Graph g = graph.readFile(path);
// 			int[][] adjgraph = g.adjacentGraph;
	      	
// 			int[] degree = GraphSearch.getDegreeOrder(g);
// 			int[][] adj = GraphSearch.sortAdjacencyLists(adjgraph, degree);
// 			int endVertex = GraphSearch.endVertexBFS(g);
// 			int[] LBFS = GraphSearch.lbfsCore(adj, degree, endVertex);
// 	      	System.out.println(i);
// 	      	System.out.println(Arrays.toString(LBFS));
// 	      	// turn lbfs order into node order
// 	      	int[] nodeOrder = new int[LBFS.length];
// 	      	for(int j=0;j<LBFS.length;j++) {
// 	      		nodeOrder[LBFS[j]] = j;
// 	      	}
// 	      	System.out.println(Arrays.toString(nodeOrder));
	      	
// 	      	int[][] label = new int[LBFS.length][LBFS.length];
// 	      	for(int j=0;j<LBFS.length;j++) {
// 	      		for(int k=0;k<LBFS.length;k++) {
// 	      			label[j][k]=0;
// 	      		}
// 	      	}
// 	      	// test by the definition of lbfs, lexicographically larger
// 	      	// L1 is lexicographically larger than L2 if the minimum element in L1\L2 & L2\L1 belongs to L1
// 	      	int[] takeOut = new int[nodeOrder.length];
// 	      	Arrays.fill(takeOut, 0);
// 	      	for(int j=0;j<LBFS.length-1;j++) {
// 	      		takeOut[nodeOrder[j]]=1;
// 	      		for(int k=0;k<adj[nodeOrder[j]].length;k++) {
// 	      			if (takeOut[adj[nodeOrder[j]][k]]==0) {
// //	      				System.out.println(adj[nodeOrder[j]][k]);
// 	      				label[adj[nodeOrder[j]][k]][nodeOrder[j]]=1;
// 	      			}
// 	      		}
// 	      		// after taking out nodeOrder[j], 
// 	      		// the label of nodeOrder[j+1] is lexicographically larger than any node from j+2 to n;
// 	      		for(int k=j+2;k<LBFS.length;k++) {
// 	      			for(int m = 0;m<LBFS.length;m++) {
// 	      				// find the minimum element in L1\L2 & L2\L1
// 		      			if (label[nodeOrder[j+1]][m]+label[nodeOrder[k]][m]==1) {
// 		      				// belong to L1
// 		      				assertTrue(label[nodeOrder[j+1]][m]==1);
// 		      				break;
// 		      			}
// 	      			}
// 	      		}
// 	      	}
	      	
// 		}
// 	}
	
}
