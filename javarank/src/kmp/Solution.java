package kmp;

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
        System.out.println(Arrays.toString(createTable("aabaaab".toCharArray())));
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
        String[] genesOfInterest = new String[last-first+1];
        int[] healthsOfInterest = new int[last-first+1];
        for (int i = 0; i < genesOfInterest.length; i++) {
            genesOfInterest[i] = genes[first+i];
            healthsOfInterest[i] = healths[first+i];
        }
        sortGenesAndHealths(genesOfInterest, healthsOfInterest);
        List<String> uniqGenes = new ArrayList<>();
        List<Integer> uniqHealth = new ArrayList<>();
        uniqueHealths(healthsOfInterest, genesOfInterest, uniqGenes, uniqHealth);
        genes = uniqGenes.toArray(new String[0]);
        long health = 0;
        for (int i = 0; i < genes.length; i++) {
            int geneHealth = kmp(d.toCharArray(), genes[i].toCharArray()) * uniqHealth.get(i);
            health += geneHealth;
        }
        return health;
    }

    static void sortGenesAndHealths(String[] genes, int[] healths) {
        int n = 1;
        while (n > 0) {
            n = 0;
            int first = 0;
            int mid = 0;
            boolean secondSet = false;
            for (int i = 1; i < genes.length; i++) {
                if (compareStrings(genes[i-1], genes[i]) == 1 || i == genes.length - 1) {
                    if (secondSet) {
                        merge(genes, first, mid, i, healths);
                        first = i;
                        n++;
                        secondSet = false;
                    } else {
                        mid = i;
                        secondSet = true;
                    }
                }
            }
        }

    }

    static void uniqueHealths(int[] healths, String[] genes, List<String> uniqGenes, List<Integer> uniqHealths) {
        uniqGenes.add(genes[0]);
        uniqHealths.add(healths[0]);
        for (int i = 0; i < genes.length - 1; i++) {
            if (compareStrings(genes[i + 1], genes[i]) != 0) {
                uniqGenes.add(genes[i + 1]);
                uniqHealths.add(healths[i + 1]);
            } else {
                uniqHealths.set(uniqHealths.size(), uniqHealths.get(uniqHealths.size()) + healths[i]);
            }
        }
    }

    static void merge(String[] arr, int start, int mid, int end, int[] healths) {
        String[] buf = new String[mid - start];
        int[] healthBuf = new int[mid - start];

        for (int i = 0; i < buf.length; i++) {
            buf[i] = arr[start + i];
            healthBuf[i] = healths[start + 1];
        }

        int left = 0;
        int right = mid;
        int all = start;
        while (all < end) {
            if (left == buf.length) {
                arr[all] = arr[right];
                healths[all] = healths[right];
                right++;
                all++;
            } else if (right == end) {
                arr[all] = buf[left];
                healths[all] = healthBuf[left];
                left++;
                all++;
            } else if (compareStrings(arr[right], buf[left]) == 1) {
                arr[all] = buf[left];
                healths[all] = healthBuf[left];
                left++;
                all++;
            } else {
                arr[all] = arr[right];
                healths[all] = healths[right];
                right++;
                all++;
            }
        }
    }

    static int compareStrings(String left, String right) {
        int i = 0;
        while (i < left.length() && i < right.length()) {
            if (left.charAt(i) > right.charAt(i)) {
                return 1;
            } else {
                return -1;
            }
        }
        if (left.length() > right.length()) {
            return 1;
        } else if (left.length() < right.length()) {
            return -1;
        }
        return 0;
    }

    static int kmp(char[] bigStr, char[] smallStr) {
        int[] prefixTable = createTable(smallStr);

        int n = 0;
        int i = 0; //bigstr
        int p = 0; //smallstr
        while (i + p < bigStr.length) {
            if (bigStr[i + p] == smallStr[p]) {
                if (p + 1 == smallStr.length) {
                    n++;
                    i = i + p - (p > 0 ? prefixTable[p-1] : -1);
                    p = (p > 0 ? prefixTable[p-1] : 0);
                } else {
                    p++;
                }
            } else {
                i = i + p - (p > 0 ? prefixTable[p-1] : -1);
                p = (p > 0 ? prefixTable[p-1] : 0);
            }
        }
        return n;
    }

    static int[] createTable(char[] pattern) {
        int[] fftable = new int[pattern.length];

        fftable[0] = 0;
        int i = 1;
        int j = 0;
        while (i < fftable.length) {
            while (j > 0 && pattern[i] != pattern[j]) {
                j = fftable[j-1];
            }
            if (pattern[i] == pattern[j]) {
                j++;
                fftable[i] = j;
            } else {
                fftable[i] = j;
            }
            i++;
        }
        return fftable;
    }
}
