package fraud;


public class Solution {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 4};
        System.out.println(activityNotifications(arr, 4));
    }
    static int activityNotifications(int[] arr, int d) {
        int count = 0;
        boolean notFirst = false;
        int[] counts = new int[201];
        for (int end = d; end < arr.length; end++) {
            int start = end - d;
            double median = getMedian(arr, start, end, notFirst, counts);

            if (2*median <= arr[end]) {
                count++;
            }
            notFirst = true;
        }
        return count;
    }

    static double getMedianFromCounts(int[] cnts, int size) {
        if (size % 2 == 0) {
            int firstPos = (int)(size / 2);
            int digs = 0;
            double firstVal = 0;
            for (int i = 0; i < cnts.length; i++) {
                digs += cnts[i];
                if (digs > firstPos && firstVal != 0) return (firstVal + i) / 2;
                else if (digs > firstPos) return i;
                else if (digs == firstPos) firstVal = i;
            }
        } else {
            int pos = size / 2 + 1;
            int digs = 0;
            for (int i = 0; i < cnts.length; i++) {
                digs += cnts[i];
                if (digs >= pos) {
                    return i;
                }
            }
        }
        throw new RuntimeException("OLOLOLOL");
    }

    static double getMedian(int[] arr, int start, int end, boolean once, int[] counts) {
        if (once) {
            counts[arr[start-1]] -= 1;
            counts[arr[end-1]] += 1;
        } else {
            for (int i = start; i < end; i++) {
                counts[arr[i]] += 1;
            }
        }
        return getMedianFromCounts(counts, end - start);
    }
}
