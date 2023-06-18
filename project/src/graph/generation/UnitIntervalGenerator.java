package graph.generation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.List;

/**
* To generate a unit interval graph, it suffices to specify the last neighbor of each vertex, such that the sequence is not non-decreasing.
*/

public class UnitIntervalGenerator {

    /**
     * Assign a random last neighbor for each vertex in order.
     * The number for the (i+1)st vertex cannot be smaller than i+1, or the number for the ith vertex.
     * Stop the process when the number is already n.
     
     * @param n: the number of vertices
     */
    static int[] generate(int n) {
        int lastNeighbors[] = new int[n];
        
        Random rand = new Random();
        int cur = 1;
        for (int i = 0; i < n && cur < n - 1; i++) {
        	// make sure this is a connected graph
        	if (cur<i+1) cur=i+1;
        	cur = cur + rand.nextInt(n - cur);
            lastNeighbors[i] = cur;
        }
        for (int i=n-1;i>=0;i--) {
        	if (lastNeighbors[i]==0) {
        		lastNeighbors[i]=n-1;
        	}else {
        		break;
        	}
        }
//      System.out.println(Arrays.toString(lastNeighbors));

        return lastNeighbors;
  }
    
    /**
     * generate a random order @numbers for vertices
     * this permutation is used to shuffle the numbers of the vertices 
     * @param n: number of vertices
     * @return permutation
     */
    static int[] shuffle(int n) {
		int nodeNum = n;
		int numbers[] = new int[nodeNum];
		
		// ordered list
		for(int i=0;i<nodeNum;i++) {
			numbers[i] = i+1;
		}
		// Iterate through the array elements in a for loop
		// Use the Random class to generate a random index number. 
		// Then swap the current index element with the randomly generated index element. 
		// At the end of the for loop, we will have a randomly shuffled array.
		// ref: https://www.digitalocean.com/community/tutorials/shuffle-array-java
		Random rand = new Random();
		
		for (int i = 0; i < nodeNum; i++) {
			int randomIndexToSwap = rand.nextInt(nodeNum);
			int temp = numbers[randomIndexToSwap];
			numbers[randomIndexToSwap] = numbers[i];
			numbers[i] = temp;
		}
		return numbers;
		
//		System.out.println(Arrays.toString(numbers));
    }

    /**
     * Write the generated graph into a file
     * Shuffle the numbers of the vertices.
     */
    static void write(int[] lastNeighbors, String filename,int[]numbers) {
    	int nodeNum = lastNeighbors.length;
		// calculate edgeNum
		int edgeNum = 0;
		for(int i=0;i<nodeNum;i++) {
			edgeNum+=lastNeighbors[i]-i;
		}
		
		// given the permutation @numbers and the position @lastNeighbors
		// write the information to text file
		
    	try {
	        FileWriter myWriter = new FileWriter(filename);
	        myWriter.write("c this is the interval graph \n");
	        myWriter.write("p edge "+nodeNum+" "+edgeNum+"\n");
	        for (int i=0;i<nodeNum;i++) {
	        	for(int j=i+1;j<=lastNeighbors[i];j++) {
	        		myWriter.write("e "+numbers[i]+" "+numbers[j]+"\n");
	        		edgeNum++;
	        	}
	        }	
	        myWriter.close();
	        System.out.println("successful generation");
//	        
	   }
	    catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }
    }

    /**
     * The interface for the user to call. 
     * They can ask for a certain number of unit interval graphs of a fixed order, or random orders (each independent).
     *
     * @param path: the path to store the generated files
     * @param prefix: the prefix for the generated files, e.g., uig01.txt, uig02.txt, ... if prefix = "uig"
     * @param n: the number of vertices
     * @param random: if 0, all the generated graphs have the same order; otherwise, the order is at most n
     * @param number: the number of graphs to be generated
     */
    public static void generate(String path, String prefix, int n, boolean random, int number) {
    	// filename format
    	int length = (int) (Math.log10(number) + 1);
    	String format = "%0"+length+"d";
    	// generate "number" graphs
        for (int i = 1; i <= number; i++) {
            int n0 = n;
            // if random, there are at most n vertices
            if (random) {
                Random rand = new Random();
                n0 = rand.nextInt(n-1)+2;
            }
            //generate last neighbors and write into files
            String filename = path+prefix+String.format(format, i) +".txt";
            int[] lastNeighbors = generate(n0);
        	int nodeNum = lastNeighbors.length;
        	// get the new order for the nodes
        	// seperate 2 functions for JUnit test
        	int numbers[] = shuffle(nodeNum);
            write(lastNeighbors,filename,numbers);
        }
    }
    
//     public static void main(String[] args) {
// //    	generate(10);
// //    	int[] temp = new int[10];
// //    	write(temp,"asd");
//     	String path = "./graphs/";
//     	generate(path, "test",100,true,100);
//     }

        
}
