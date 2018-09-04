package hackerrank;

import java.io.*;
import java.util.*;

public class RoadNLibs {

    // Complete the roadsAndLibraries function below.
    static long roadsAndLibraries(int n, int c_lib, int c_road, int[][] cities) {
        if (n == 0) return 0;
        if (n == 1) return c_lib;

        // determine whether to build libraries only;
        if (c_lib < c_road) {
            return (long) c_lib * n;
        }

        int[] aux = new int[n];
        for (int i = 0; i < n; i++) {
            aux[i] = i;
        }

        // find and union;
        for (int[] road : cities) {
            int r0 = find(road[0] - 1, aux);
            int r1 = find(road[1] - 1, aux);
            if (r0 != r1) aux[r1] = r0;
        }

        // find groups;
        Map<Integer, Integer> grpToCntMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int a = find(i, aux);
            int cnt = grpToCntMap.getOrDefault(a, 0);
            grpToCntMap.put(a, cnt + 1);
        }

        long ret = 0;
        for (Integer grp : grpToCntMap.keySet()) {
            int cnt = grpToCntMap.get(grp);
            ret += (cnt - 1) * c_road + c_lib;
        }

        return ret;
    }

    private static int find(int a, int[] aux) {
        while (a != aux[a]) a = aux[a];
        return a;
    }

    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream("src/hackerrank/inputs/RoadNLibs.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            String[] nmC_libC_road = scanner.nextLine().split(" ");

            int n = Integer.parseInt(nmC_libC_road[0]);

            int m = Integer.parseInt(nmC_libC_road[1]);

            int c_lib = Integer.parseInt(nmC_libC_road[2]);

            int c_road = Integer.parseInt(nmC_libC_road[3]);

            int[][] cities = new int[m][2];

            for (int i = 0; i < m; i++) {
                String[] citiesRowItems = scanner.nextLine().split(" ");
                scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

                for (int j = 0; j < 2; j++) {
                    int citiesItem = Integer.parseInt(citiesRowItems[j]);
                    cities[i][j] = citiesItem;
                }
            }

            System.out.println(roadsAndLibraries(n, c_lib, c_road, cities));

        }

        scanner.close();
    }
}
