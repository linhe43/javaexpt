package hackerrank;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class PrimMST {
    // Complete the prims function below.
    static int prims(int n, int[][] edges, int start) {
        if (n == 0 || edges == null || edges.length == 0) return 0;

        // construct the graph using edges;
        Map<Integer, List<Edge>> adjMap = new HashMap<>();
        for (int[] edge : edges) {
            adjMap.putIfAbsent(edge[0], new ArrayList<>());
            adjMap.putIfAbsent(edge[1], new ArrayList<>());
            Edge e = new Edge(edge[0], edge[1], edge[2]);
            adjMap.get(edge[0]).add(e);
            adjMap.get(edge[1]).add(e);
        }

        // calculate the min sum;
        int sum = 0;
        boolean[] visited = new boolean[n];
        visited[start - 1] = true;
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        queue.addAll(adjMap.get(start));
        while (!queue.isEmpty()) {
            Edge cur = queue.poll();
            if (visited[cur.e1 - 1] && visited[cur.e2 - 1]) continue;
            sum += cur.w;
            if (visited[cur.e1 - 1]) {
                queue.addAll(adjMap.get(cur.e2));
                visited[cur.e2 - 1] = true;
            } else {
                queue.addAll(adjMap.get(cur.e1));
                visited[cur.e1 - 1] = true;
            }
        }

        return sum;
    }


    public static void main(String[] args) throws IOException {
        final Scanner scanner = new Scanner(new FileInputStream("src/hackerrank/inputs/PrimMST.txt"));
        String[] nm = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nm[0]);

        int m = Integer.parseInt(nm[1]);

        int[][] edges = new int[m][3];

        for (int i = 0; i < m; i++) {
            String[] edgesRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 3; j++) {
                int edgesItem = Integer.parseInt(edgesRowItems[j]);
                edges[i][j] = edgesItem;
            }
        }

        int start = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        System.out.println(prims(n, edges, start));

        scanner.close();
    }
}

class Edge implements Comparable<Edge> {
    int e1, e2, w;

    public Edge(int e1, int e2, int w) {
        this.e1 = e1;
        this.e2 = e2;
        this.w = w;
    }

    @Override
    public int compareTo(Edge that) {
        return this.w - that.w;
    }
}
