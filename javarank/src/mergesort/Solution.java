package mergesort;

import java.util.Arrays;


public class Solution {
    public static void main(String[] args) {
        int[] arr = {2, 1, 4, 3, 6, 5};
        int[] aux = new int[arr.length];
        for (int i = 0; i < aux.length; i++) {
            aux[i] = i;
        }
        mergeSort(arr, aux);

        int swaps = 0;
        for (int i = 0; i < aux.length; i++) {
            if (aux[i] != i) {
                int k = i;
                do {
                    int buf = k;
                    k = aux[k];
                    aux[buf] = buf;
                    swaps++;
                } while (k != i);
                swaps--;
            }
        }
        System.out.println(swaps);
    }

    private static void mergeSort(int[] arr, int[] aux) {
        while (!mergeInPlace(arr, aux)) {};
    }

    private static boolean mergeInPlace(int arr[], int aux[]) {
        boolean foundSecond = false;
        int first = 0;
        int second = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i-1]) {
                if (foundSecond) {
                    mergeParts(arr, first, second, i, aux);
                    foundSecond = false;
                    first = i;
                } else {
                    foundSecond = true;
                    second = i;
                }
            }
        }
        if (foundSecond) {
            mergeParts(arr, first, second, arr.length, aux);
        }
        return first == 0;
    }


    private static void mergeParts(int[] arr, int start, int mid, int end, int[] aux) {
        int[] buf = new int[mid-start];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = arr[start+i];
        }

        int[] auxBuf = new int[buf.length];
        for (int i = 0; i < buf.length; i++) {
            auxBuf[i] = aux[start+i];
        }

        int b = 0;
        int r = mid;
        int i = start;
        while (i < end) {
            if (b == buf.length) {
                arr[i] = arr[r];
                aux[i] = aux[r];
                r++;
            } else if (r == end) {
                arr[i] = buf[b];
                aux[i] = auxBuf[b];
                b++;
            } else if (arr[r] < buf[b]) {
                aux[i] = aux[r];
                arr[i] = arr[r];
                r++;
            } else {
                arr[i] = buf[b];
                aux[i] = auxBuf[b];
                b++;
            }
            i++;
        }
    }
}
