import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int arNum = Integer.valueOf(reader.readLine());
            for (int i = 0; i < arNum; i++) {
                reader.readLine();
                String[] splitted = reader.readLine().split(" ");
                int[] intArr = new int[splitted.length];
                for (int j = 0; j < splitted.length; j++) {
                    intArr[j] = Integer.valueOf(splitted[j]);
                }
                System.out.println(getSwaps(intArr));
            }
        }
    }

    public static long getSwaps(int[] arr) {
        int[] bit = new int[10_000_002];
        long numberOfSwaps = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            numberOfSwaps += getNumberOfSmaller(bit, arr[i] - 1);
            increment(bit, arr[i]);
        }
        return numberOfSwaps;
    }

    public static void increment(int[] bit, int idx) {
        for (; idx < bit.length; idx += -idx & idx) {
            bit[idx] = bit[idx] + 1;
        }
    }

    public static int getNumberOfSmaller(int[] bit, int idx) {
        int sum = 0;
        for(; idx > 0; idx -= -idx & idx) {
            sum += bit[idx];
        }
        return sum;
    }
}