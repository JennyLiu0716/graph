package graph.generation;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class UnitIntervalGeneratorTest {

	@Test
	void generatetest() {
		for(int i=1;i<=1000;i++) {

			int[] last = UnitIntervalGenerator.generate(10);
//			System.out.println(Arrays.toString(last));
			for(int j=0;j<last.length;j++) {
				if (j>1) {
					assertTrue(last[j]>=last[j-1]);
				}
				assertTrue(last[j]>=j);
				if (last[j]==j&&last.length>1) {
//					System.out.println(j-1);
					assertTrue(last[j-1]==last[j]);
				}
			}

			
		}
	}
	
	@Test
	void writetest() throws IOException {
		for(int i=1;i<=1000;i++) {

			int[] last = UnitIntervalGenerator.generate(i);
			String filename = "./graphs/"+"test"+String.format("%4d", i) +".txt";
			// shuffle
			int[] numbers = UnitIntervalGenerator.randomOrder(last.length);
			// reverse numbers with its index
			int[] index = new int[numbers.length+1];
			for(int j=0;j<numbers.length;j++) {
				index[numbers[j]] = j;
			}
			UnitIntervalGenerator.write(last, filename,numbers);
	        // File path is passed as parameter
	        File file = new File(filename);
	        // Creating an object of BufferedReader class
	        BufferedReader br = new BufferedReader(new FileReader(file));
	        String s;
	        int pointer = 0;
	        while ((s = br.readLine()) != null) {
	            // Print the string
	        	pointer++;
	        	if (pointer>=3) {
				// test whether the two nodes in the file, exactly have edge in there original order
				// convert the nodes to lastNeighbors
	        		String[] splitS = s.split("\\s+");
	        		int first = index[Integer.parseInt(splitS[1])];
	        		int second = index[Integer.parseInt(splitS[2])];
//	        		System.out.println(first+" "+second);
	        		if (first<second) {
	        			assertTrue(last[first]>=second);
	        		}else {
	        			assertTrue(last[second]>first);
	        		}
	        	}
//	            System.out.println(s);
	        	
	        }
		}
	}

}
