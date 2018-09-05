package hackerrank;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class TripartiteMatching {
    /*
     * Complete the tripartiteMatching function below.
     */
    static int tripartiteMatching(int n, int[][] g1, int[][] g2, int[][] g3) {
        if (n == 0 || g1 == null || g2 == null || g3 == null
                || g1.length == 0 || g2.length == 0 || g3.length == 0)
            return 0;

        long start = System.currentTimeMillis();
        // hash g2 and g3;
        Map<Integer, Set<Integer>> adjMap2 = convertToMap(g2);
        Map<Integer, Set<Integer>> adjMap3 = convertToMap(g3);

        // traverse each edge in g1;
        long middle = System.currentTimeMillis();
        int ret = 0;
        for (int[] cur : g1) {
            ret += getMatchingNum(cur[0], cur[1], adjMap2, adjMap3);
            ret += getMatchingNum(cur[1], cur[0], adjMap2, adjMap3);
        }
        long end = System.currentTimeMillis();

        System.out.format("%d, %d%n", middle - start, end - middle);

        return ret;
    }

    private static int getMatchingNum(int start, int end, Map<Integer, Set<Integer>> adjMap2,
                                      Map<Integer, Set<Integer>> adjMap3) {
        int ret = 0;
        Set<Integer> set = adjMap2.get(end);
        for (Integer node : set) {
            if (adjMap3.get(node) != null && adjMap3.get(node).contains(start)) {
                ret++;
            }
        }

        return ret;
    }

    private static Map<Integer, Set<Integer>> convertToMap(int[][] edges) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int[] edge : edges) {
            map.putIfAbsent(edge[0], new HashSet<>());
            map.putIfAbsent(edge[1], new HashSet<>());
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
        }

        return map;
    }

    public static void main(String[] args) throws IOException {
        final Scanner scanner = new Scanner(new FileInputStream("src/hackerrank/inputs/TripartiteMatching.txt"));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

        int m1 = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

        int[][] g1 = new int[m1][2];

        for (int g1RowItr = 0; g1RowItr < m1; g1RowItr++) {
            String[] g1RowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

            for (int g1ColumnItr = 0; g1ColumnItr < 2; g1ColumnItr++) {
                int g1Item = Integer.parseInt(g1RowItems[g1ColumnItr]);
                g1[g1RowItr][g1ColumnItr] = g1Item;
            }
        }

        int m2 = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

        int[][] g2 = new int[m2][2];

        for (int g2RowItr = 0; g2RowItr < m2; g2RowItr++) {
            String[] g2RowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

            for (int g2ColumnItr = 0; g2ColumnItr < 2; g2ColumnItr++) {
                int g2Item = Integer.parseInt(g2RowItems[g2ColumnItr]);
                g2[g2RowItr][g2ColumnItr] = g2Item;
            }
        }

        int m3 = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

        int[][] g3 = new int[m3][2];

        for (int g3RowItr = 0; g3RowItr < m3; g3RowItr++) {
            String[] g3RowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])*");

            for (int g3ColumnItr = 0; g3ColumnItr < 2; g3ColumnItr++) {
                int g3Item = Integer.parseInt(g3RowItems[g3ColumnItr]);
                g3[g3RowItr][g3ColumnItr] = g3Item;
            }
        }

        System.out.println(tripartiteMatching(n, g1, g2, g3));

        scanner.close();
    }
}
