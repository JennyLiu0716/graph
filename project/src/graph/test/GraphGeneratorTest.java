package graph.test;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Test;
import graph.algorithms.GraphSearch;
import graph.generation.GraphGenerator;
import graph.Graph;

public class GraphGeneratorTest {
    @Test
    public void generateTest() throws FileNotFoundException{
        String path = ".\\project\\graphs\\";
        GraphGenerator.generator(20, true, path, true, 100, true);
    }
    
    @Test
	public void unitIntervalGraphTest() {
		// pass
		for (int i = 1; i <= 1000; i++) {

			int[] last = GraphGenerator.unitIntervalGraph(10, true);
			assertTrue(last.length == 11);

			for (int j = 1; j <= last.length - 1; j++) {
				assertTrue(last[j]<=10);
				if (j > 1) {
					assertTrue(last[j] >= last[j - 1]);
				}
				assertTrue(last[j] >= j);
			}

		}
	}


}
