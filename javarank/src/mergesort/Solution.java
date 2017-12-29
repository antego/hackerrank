package mergesort;

import java.util.Arrays;


public class Solution {
    public static void main(String[] args) {
        int[] arr = {1,3,5,7,2,4,6};
        mergeSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void mergeSort(int[] arr) {
        while (!mergeInPlace(arr)) {};
    }

    private static boolean mergeInPlace(int arr[]) {
        boolean foundSecond = false;
        int first = 0;
        int second = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i-1]) {
                if (foundSecond) {
                    mergeParts(arr, first, second, i);
                    foundSecond = false;
                    first = i;
                } else {
                    foundSecond = true;
                    second = i;
                }
            }
        }
        if (foundSecond) {
            mergeParts(arr, first, second, arr.length);
        }
        return first == 0;
    }


    private static void mergeParts(int[] arr, int start,  int mid, int end) {
        int[] buf = new int[mid-start];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = arr[start+i];
        }

        int b = 0;
        int r = mid;
        int i = start;
        while (i < end) {
            if (b == buf.length) {
                arr[i] = arr[r];
                r++;
            } else if (r == end) {
                arr[i] = buf[b];
                b++;
            } else if (arr[r] < buf[b]) {
                arr[i] = arr[r];
                r++;
            } else {
                arr[i] = buf[b];
                b++;
            }
            i++;
        }
    }
}
