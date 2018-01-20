package ahocorasick;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by anton on 05.01.18.
 */
public class Solution {
    public static void main(String[] args) {
        String[] genes = {"a", "b", "c", "aa", "d", "b"};
        int[] healths = {1, 2, 3, 4, 5, 6};
        System.out.println(computeHealth(genes, healths, 1, 5, "caaab"));
        System.out.println(computeHealth(genes, healths, 0, 4 , "xyz"));
        System.out.println(computeHealth(genes, healths, 2, 4, "bcdybc"));

    }

    @Test
    public void processFile() throws FileNotFoundException {
        Scanner in = new Scanner(new InputStreamReader(new FileInputStream("/home/anton/projects/hackerrank/javarank/src/kmp/input2")));
        int n = in.nextInt();
        String[] genes = new String[n];
        for(int genes_i=0; genes_i < n; genes_i++){
            genes[genes_i] = in.next();
        }
        int[] health = new int[n];
        for(int health_i=0; health_i < n; health_i++){
            health[health_i] = in.nextInt();
        }
        int s = in.nextInt();
        long max = 0;
        long min = Long.MAX_VALUE;
        for(int a0 = 0; a0 < s; a0++){
            int first = in.nextInt();
            int last = in.nextInt();
            String d = in.next();
            long health1 = computeHealth(genes, health, first, last, d);
            if (health1 > max) {
                max = health1;
            } else if (health1 < min) {
                min = health1;
            }
        }
        System.out.println(min + " " + max);
    }

    static long computeHealth(String[] genes, int[] healths, int first, int last, String d) {
        long health = 0;
        return health;
    }



}
