package graph.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import utils.Functions;

public class FunctionTest {
    @Test
    public void shuffleTest() {
        // pass
        for (int i = 10; i <= 20; i++) {
            // 1...i
            int[] shuffled = Functions.shuffle(i);
            assertTrue(shuffled.length == i);
            boolean[] included = new boolean[i];
            for (int j = 0; j < i; j++) {
                included[j] = false;
            }
            for (int j = 0; j < i; j++) {
                included[shuffled[j] - 1] = true;
            }
            for (int j = 0; j < i; j++) {
                assertTrue(included[j]);
            }

        }

    }
}
