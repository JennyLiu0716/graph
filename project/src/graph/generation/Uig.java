package graph.generation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.LinkedList;

/**
* Explain the algorithm you use.

*/
public class Uig {
	public static void OneUig(int nodeNum,int nameNum) {
		
		int length = nodeNum;
		int numArray[] = new int[length];
		
		for(int i=0;i<length;i++) {
			numArray[i] = i+1;
		}
	
		Random rand = new Random();
		
		for (int i = 0; i < numArray.length; i++) {
			int randomIndexToSwap = rand.nextInt(numArray.length);
			int temp = numArray[randomIndexToSwap];
			numArray[randomIndexToSwap] = numArray[i];
			numArray[i] = temp;
		}
		
//		System.out.println(Arrays.toString(numArray));
		
		LinkedList[] llarray = new LinkedList[length];
		for (int i=0;i<length;i++) {
			llarray[i] = new LinkedList();
		}
		int previous = -1;
		int p;
		int begin ;
		
		for(int i=0;i<length;i++) {
//			previous
			begin = i+1;
			Random randomNum = new Random();
			if (previous > i+1) {
				begin = previous;
			}
			if (length-begin>0) {
				p = randomNum.nextInt(length-begin);
				p = p+begin ;
				previous = p;
				llarray[p].add(numArray[i]);
			}else {
				previous = length-1;
				llarray[length-1].add(numArray[i]);
			}

		}
		
		int[] number = new int[length*2];
		int pointer = 0;
		for (int i=0;i<length;i++) {
			number[pointer] = numArray[i];
			pointer ++;
			while (!llarray[i].isEmpty()) {
				number[pointer] = (int) llarray[i].remove();
				pointer++;
			}
		}
		
		System.out.println(Arrays.toString(number));
		
		String filename = "D:/polyu/graph/graphs/UnitIntervalGraph_"+nameNum+".txt";
		int edgeNum = 0;
	    try {
	        FileWriter myWriter = new FileWriter(filename);
	        myWriter.write("c this is the interval graph "+Arrays.toString(number)+"\n");
	        myWriter.write("p edge \n");
			int[][] intersection = new int[nodeNum+1][nodeNum+1];
			Arrays.stream(intersection).forEach(row -> Arrays.fill(row, 0));
			boolean[] status = new boolean[nodeNum+1];
			Arrays.fill(status, false);
			
			for (int i=0;i<length*2;i++) {
				
				for(int j=1;j<=nodeNum;j++) {
					if (status[j]==true) {
						intersection[j][number[i]]=1;
					}
				}
				if (status[number[i]]==true) {
					status[number[i]] = false;
				}else {
					status[number[i]]=true;
				}	
			}
			for(int i=1;i<=nodeNum;i++) {
				for(int j=1;j<=nodeNum;j++) {
					if (intersection[i][j]==1&&i!=j) {
						intersection[i][j] = 0;
						intersection[j][i] = 0;
						myWriter.write("e "+i+" "+j+"\n");
						edgeNum++;
					}
				}
			}
			
	        myWriter.close();
	        Scanner sc = new Scanner(new File(filename));
	        StringBuffer buffer = new StringBuffer();
	        while (sc.hasNextLine()) {
	            buffer.append(sc.nextLine()+System.lineSeparator());
	         }
	        String fileContents = buffer.toString();
	        sc.close();
	        String oldLine = "p edge ";
	        String newLine = "p edge "+nodeNum+" "+edgeNum;
	        //Replacing the old line with new line
	        fileContents = fileContents.replaceAll(oldLine, newLine);
	        //instantiating the FileWriter class
	        FileWriter writer = new FileWriter(filename);
	        writer.append(fileContents);
	        writer.flush();
	        System.out.println("successful generation");
	        
	   }
	    catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }
		
	}
	
	public static void main(String[] args) {
		System.out.println("Please input the number of interval graphs you would like to create:");
		Scanner sc= new Scanner(System.in); 
		int num= sc.nextInt();
		for(int i=1;i<=num;i++) {
			Random rand = new Random();

			// Obtain a number between [0 - 49].
			int n = rand.nextInt(50);

			// Add 1 to the result to get a number from the required range
			// (i.e., [1 - 50]).
			n += 1;
			
			OneUig(n,i);
		}
		
	}
}
