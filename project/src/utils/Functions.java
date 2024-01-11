package utils;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Functions {

    /**
     * UPDATED
     * A sorting algorithm in O(n) to sort the vertex by permutation
     * Record the number for each degree and the start position in the sorted list
     * https://www.geeksforgeeks.org/counting-sort/
     * 
     * @param permutation
     * @return
     */
    public static int[] countSortIndex(int[] permutation) {
        int N = permutation.length;
        int M = 0;

        for (int i = 0; i < N; i++) {
            M = Math.max(M, permutation[i]);
        }

        int[] countArray = new int[M + 1];

        for (int i = 0; i < N; i++) {
            countArray[permutation[i]]++;
        }

        for (int i = 1; i <= M; i++) {
            countArray[i] += countArray[i - 1];
        }

        int[] outputArray = new int[N];

        for (int i = N - 1; i >= 0; i--) {
            // return the sorting of vertices, not sorted degree
            outputArray[countArray[permutation[i]] - 1] = i;
            countArray[permutation[i]]--;
        }

        return outputArray;
    }

    /**
     * UPDATED
     * generate a random order {@code permutation} to shuffle the vertices
     * 
     * https://www.digitalocean.com/community/tutorials/shuffle-array-java
     * 
     * We can iterate through the array elements in a for loop. Then, we use the
     * Random class to generate a random index number. Then swap the current index
     * element with the randomly generated index element. At the end of the for
     * loop, we will have a randomly shuffled array.
     * 
     * @param n: number of vertices
     * @return permutation - index 0...n-1, element 1...n
     */
    public static int[] shuffle(int n) {

        int[] array = IntStream.rangeClosed(1, n).toArray();

        Random rand = new Random();

        for (int i = 0; i < array.length; i++) {
            int randomIndexToSwap = rand.nextInt(array.length);
            int temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[i];
            array[i] = temp;
        }

        return array;
    }

    /**
     * a function to transfer the element with the index
     * helper function in LBFS sigma representation, to convert it from a precedence
     * array to vertex array
     * 
     * @param array
     * @return
     */
    public static int[] transferIE(int[] array) {
        int n = array.length;
        int[] newlist = new int[n];

        for (int i = 0; i < n; i++) {
            newlist[array[i]] = i;
        }

        return newlist;
    }

    /**
     * Random list of M non-negative integers whose sum is N
     * 
     * Given two integers M and N, the task is to create a list of M positive
     * integers whose sum is N.
     * Approach: To get a complete random list of integers, create an array of size
     * M where every element is initialised with 1. Now run a loop from 0 to N – M
     * -1 and increment any randomly chosen element from the array by 1 using the
     * rand() function. This way, sum of the resultant list will be N.
     * 
     * https://www.geeksforgeeks.org/random-list-of-m-non-negative-integers-whose-sum-is-n/
     * 
     * @param m - a list of M numbers
     * @param n - sum of n
     * @return
     */
    public static int[] randomList(int m, int n) {

        // Create an array of size m where
        // every element is initialized to 1
        int arr[] = new int[m];
        Arrays.fill(arr, 1);

        // To make the sum of the final list as n
        for (int i = 0; i < n - m; i++) {

            // Increment any random element
            // from the array by 1
            arr[(int) (Math.random() * m)]++;
        }

        return arr;

    }
}
