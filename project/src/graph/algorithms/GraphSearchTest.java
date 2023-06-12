package graph.algorithms;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import graph.Graph;

class GraphSearchTest2 {

	@Test
	void test() throws FileNotFoundException {
		Graph g = new Graph();
		for(int i=0;i<=100;i++) {
//			System.out.print(i+" ");
			String path = "./graphs/UnitIntervalGraph_"+i+".txt";
	      	Graph graph = g.readFile(path);
	      	int[][] adjgraph = g.adjacentGraph;
	      	int[] degree = GraphSearch.getDegreeOrder(g);
	      	
	      	// test function: getDegreeOrder - pass
	      	for(int j=1;j<degree.length;j++) {
	      		assertTrue(adjgraph[degree[j]].length<=adjgraph[degree[j-1]].length);
//	      		System.out.print(adjgraph[degree[j]].length);
	      	}
//	      	System.out.println();
	      	
	      	// test function: sortAdjacencyLists
	      	int[][] adj = GraphSearch.sortAdjacencyLists(adjgraph, degree);
	      	for(int j=0;j<adj.length;j++) {
	      		for(int k=1;k<adj[j].length;k++) {
	      			assertTrue(adjgraph[adj[j][k]].length<=adjgraph[adj[j][k-1]].length);
//	      			System.out.println(adjgraph[adj[j][k]].length+" "+adjgraph[adj[j][k-1]].length);
	      		}
	      	}
	      	
	      	// test function: lbfsCore/lbfsDelta - error in test case 79 
	      	int endVertex = GraphSearch.bfsEndVertex(graph,0);
	      	int[] LBFS = GraphSearch.lbfsCore(adj, degree, endVertex);
//	      	System.out.println(endVertex+" "+LBFS[0]);
	      	assertTrue(LBFS[endVertex]==0);
	      	int[] nodeOrder = new int[LBFS.length];
	      	for(int j=0;j<LBFS.length;j++) {
	      		nodeOrder[LBFS[j]] = j;
	      	}
	      	System.out.println(Arrays.toString(adj[0]));
	      	for(int j=1;j<LBFS.length;j++) {
	      		boolean found = false;
	      		int t = nodeOrder[j];
	      		for(int k=0;k<adj[nodeOrder[j-1]].length;k++) {
	      			System.out.println(t+" "+adj[nodeOrder[j-1]][k]);
	      			if (adj[nodeOrder[j-1]][k]==t) {
	      				found = true;
	      				continue;
	      			}
	      		}
	      		if (!found) {
	      			System.out.println(Arrays.toString(nodeOrder));
	      			System.out.println(j);
	      			System.out.println(endVertex);
	      		}
	      		System.out.println(i);
	      		assertTrue(found);
	      	}
	      	
		}
	}

}
