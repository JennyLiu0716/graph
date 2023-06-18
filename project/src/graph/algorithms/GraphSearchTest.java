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
		for(int i=7;i<=100;i++) {
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
	      	
//	      	// test function: lbfsCore/lbfsDelta - error in test case 79 
//	      	int endVertex = GraphSearch.bfsEndVertex(graph,0);
//	      	int[] LBFS = GraphSearch.lbfsCore(adj, degree, endVertex);
////	      	System.out.println(endVertex+" "+LBFS[0]);
//	      	assertTrue(LBFS[endVertex]==0);
//	      	int[] nodeOrder = new int[LBFS.length];
//	      	for(int j=0;j<LBFS.length;j++) {
//	      		nodeOrder[LBFS[j]] = j;
//	      	}
//	      	System.out.println(Arrays.toString(adj[0]));
//	      	for(int j=1;j<LBFS.length;j++) {
//	      		boolean found = false;
//	      		int t = nodeOrder[j];
//	      		for(int k=0;k<adj[nodeOrder[j-1]].length;k++) {
//	      			System.out.println(t+" "+adj[nodeOrder[j-1]][k]);
//	      			if (adj[nodeOrder[j-1]][k]==t) {
//	      				found = true;
//	      				continue;
//	      			}
//	      		}
//	      		if (!found) {
//	      			System.out.println(Arrays.toString(nodeOrder));
//	      			System.out.println(j);
//	      			System.out.println(endVertex);
//	      		}
//	      		System.out.println(i);
//	      		assertTrue(found);
//	      	}
	      	
	     // test function: lbfsCore
	      	int endVertex = GraphSearch.bfsEndVertex(graph,0);
	      	int[] LBFS = GraphSearch.lbfsCore(adj, degree, endVertex);
	      	System.out.println(i);
	      	System.out.println(Arrays.toString(LBFS));
	      	int[] nodeOrder = new int[LBFS.length];
	      	for(int j=0;j<LBFS.length;j++) {
	      		nodeOrder[LBFS[j]] = j;
	      	}
	      	System.out.println(Arrays.toString(nodeOrder));
	      	int[][] label = new int[LBFS.length][LBFS.length];
	      	for(int j=0;j<LBFS.length;j++) {
	      		for(int k=0;k<LBFS.length;k++) {
	      			label[j][k]=0;
	      		}
	      	}
	      	int[] takeOut = new int[nodeOrder.length];
	      	Arrays.fill(takeOut, 0);
	      	for(int j=0;j<LBFS.length-1;j++) {
	      		takeOut[nodeOrder[j]]=1;
	      		for(int k=0;k<adj[nodeOrder[j]].length;k++) {
	      			if (takeOut[adj[nodeOrder[j]][k]]==0) {
//	      				System.out.println(adj[nodeOrder[j]][k]);
	      				label[adj[nodeOrder[j]][k]][nodeOrder[j]]=1;
	      			}
	      		}
	      		for(int k=j+2;k<LBFS.length;k++) {
	      			for(int m = 0;m<LBFS.length;m++) {
		      			if (label[nodeOrder[j+1]][m]+label[nodeOrder[k]][m]==1) {
//		      				System.out.println(i);
//		      				System.out.println(j+1+" "+k);
//		      				System.out.println("j"+Arrays.toString(label[nodeOrder[j+1]]));
//		      				System.out.println("k"+Arrays.toString(label[nodeOrder[k]]));
		      				assertTrue(label[nodeOrder[j+1]][m]==1);
		      				break;
		      			}
	      			}
	      		}
	      	}
	      	
		}
	}

}
