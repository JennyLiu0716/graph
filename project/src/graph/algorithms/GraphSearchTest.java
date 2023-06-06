package graph.algorithms;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import graph.Graph;
import graph.perfect.*;


class GraphSearchTest {
	
	

	@Test
	void testBfsEndVertex() throws FileNotFoundException {
		Graph g = new Graph();
        for(int i=0;i<=100;i++) {
        	String path = "./graphs/UnitIntervalGraph_"+i+".txt";
        	Graph graph = g.readFile(path);
        	int endVertex = GraphSearch.bfsEndVertex(graph,0);
        	for(int j=0;j<graph.vertexNum;j++) {
        		int end = GraphSearch.bfsEndVertex(graph,j);
        		assertEquals(end,endVertex);

        	}
        	
        }
	}

//	@Test
//	void testLbfs() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testLbfsDelta() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSortAdjacencyLists() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testLbfsCoreGraphIntArrayInt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testLbfsCoreIntArrayArrayIntArrayInt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testCountSort() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testIntervalOrdering() {
////		assertTrue(IntervalOrderting());
//	}
//
//	@Test
//	void testIsPermutation() {
//		fail("Not yet implemented");
//	}

//	@Test
//	void testMcs() {
//		fail("Not yet implemented");
//	}

}
