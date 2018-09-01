package hackerrank;

import java.io.*;
import java.util.*;

public class ArrayManipulation {
    static long arrayManipulation(int n, int[][] queries) {
        if (queries == null || queries.length == 0) return 0;

        long[] aux = new long[n + 1];
        for (int[] query : queries) {
            int lo = query[0];
            int hi = query[1];
            int val = query[2];
            aux[lo] += val;
            if (hi < n) aux[hi + 1] -= val;
        }

        long max = 0, sum = 0;
        for (long i : aux) {
            sum += i;
            max = Math.max(max, sum);
        }

        return max;
    }

    // Complete the arrayManipulation function below.
    static long arrayManipulationIntervalMethod(int n, int[][] queries) {
        if (queries == null || queries.length == 0) return 0;

        TreeSet<Interval> set = new TreeSet<>();
        set.add(new Interval(0, n, 0));
        for (int[] query : queries) {
            int lo = query[0];
            int hi = query[1];
            int val = query[2];

            Iterator<Interval> iter = set.tailSet(new Interval(0, lo, 0)).iterator();
            List<Interval> list = new ArrayList<>();
            while (iter.hasNext()) {
                Interval cur = iter.next();
                if (hi < cur.start) break;
                if (lo < cur.start) {
                    iter.remove();
                    list.add(new Interval(lo, cur.start - 1, val));
                    if (hi <= cur.end) {
                        list.add(new Interval(cur.start, hi, val + cur.val));
                        if (hi < cur.end) {
                            list.add(new Interval(hi + 1, cur.end, cur.val));
                        }
                    } else {
                        list.add(new Interval(cur.start, cur.end, val + cur.val));
                        lo = cur.end + 1;
                    }
                } else {
                    iter.remove();
                    if (lo > cur.start) {
                        list.add(new Interval(cur.start, lo - 1, cur.val));
                    }
                    if (hi <= cur.end) {
                        list.add(new Interval(lo, hi, val + cur.val));
                        if (hi < cur.end) {
                            list.add(new Interval(hi + 1, cur.end, cur.val));
                        }
                    } else {
                        list.add(new Interval(lo, cur.end, val + cur.val));
                        lo = cur.end + 1;
                    }
                }
            }
            set.addAll(list);
        }

        long max = 0;
        for (Interval intv : set) {
            max = Math.max(max, intv.val);
        }

        return max;
    }

    public static void main(String[] args) throws IOException {
        int n = 5;
        int[][] queries = {
                {1, 2, 100},
                {2, 5, 100},
                {3, 4, 100}
        };
        n = 10;
        queries = new int[][]{
                {2, 6, 8},
                {3, 5, 7},
                {1, 8, 1},
                {5, 9, 15}
        };


        System.out.println(arrayManipulation(n, queries));
    }
}

class Interval implements Comparable<Interval> {
    int start;
    int end;
    long val;

    public Interval(int start, int end, long val) {
        this.start = start;
        this.end = end;
        this.val = val;
    }

    @Override
    public int compareTo(Interval that) {
        return this.end - that.end;
    }

    @Override
    public String toString() {
        return String.format("%d->%d, %d", start, end, val);
    }
}
