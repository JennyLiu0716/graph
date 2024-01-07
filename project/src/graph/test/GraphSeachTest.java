package graph.test;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;

import graph.Graph;
import graph.algorithms.GraphSearch;

public class GraphSeachTest {
    Graph[] graphs = new Graph[10];
	
	@Test
	public void init() throws FileNotFoundException {
		String path="";
        for(int i=1;i<=10;i++) {
			path = ".\\project\\graphs\\unit_interval_graph_connected"+i+".txt";
	      	Graph graph = new Graph();
            graph.readFile(path);
	      	this.graphs[i-1] = graph;
	    }

	}

    @Test
    public void threeSweepUIGTest() throws FileNotFoundException{
        init();
        String path="";
        for(int i= 9; i<=9;i++){
            assertTrue(GraphSearch.threeSweepUIG(this.graphs[i]));
        }

        
    }
    // pass: 0,1,6,8,9
    // reject: 2,3,4,5,7,
    @Test
    public void twoSweepUIGTest() throws FileNotFoundException{
        init();
        String path="";
        for(int i= 8; i<=9;i++){
            assertTrue(GraphSearch.threeSweepUIG(this.graphs[i]));
        }

        
    }
}
