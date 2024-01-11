package graph.test;

import static org.junit.Assert.assertTrue;

import java.util.Random;

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

    @Test
    public void randomListTest() {
        // pass
        Random rand = new Random();
        for (int i = 10; i <= 20; i++) {
            int sum = rand.nextInt(100)+i;
            int[] arr = Functions.randomList(i, sum);
            int check = 0;

            for(int j:arr){
                check+=j;
            }
            System.out.println(check);
            System.out.print(sum);
            assertTrue(check==sum);

        }

    }

}
