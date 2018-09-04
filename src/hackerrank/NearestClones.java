package hackerrank;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class NearestClones {
    static int findShortest(int graphNodes, int[] graphFrom, int[] graphTo, long[] ids, int val) {
        // handle the extreme cases.
        if (graphFrom == null || graphTo == null || graphFrom.length != graphTo.length)
            return -1;
        if (graphNodes <= 1) return -1;

        // construct the graph into a HashMap.
        Map<Integer, Set<Integer>> adjMap = new HashMap<>();
        for (int i = 0; i < graphFrom.length; i++) {
            adjMap.putIfAbsent(graphFrom[i] - 1, new HashSet<>());
            adjMap.get(graphFrom[i] - 1).add(graphTo[i] - 1);
            adjMap.putIfAbsent(graphTo[i] - 1, new HashSet<>());
            adjMap.get(graphTo[i] - 1).add(graphFrom[i] - 1);
        }

        // calculate the minimum distance from a vertex with color-to-analyze to other same colored vertices.
        int ret = Integer.MAX_VALUE;
        for (int i = 0; i < graphNodes; i++) {
            if (ids[i] == val) {
                boolean[] visited = new boolean[graphNodes];
                ret = Math.min(ret, bfs(i, adjMap, visited, ids, val));
            }
        }

        return ret == Integer.MAX_VALUE ? -1 : ret;
    }

    private static int bfs(int startIdx, Map<Integer, Set<Integer>> adjMap, boolean[] visited,
                           long[] ids, int val) {
        visited[startIdx] = true;
        int cnt = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startIdx);
        queue.offer(null);

        while (!queue.isEmpty()) {
            Integer cur = queue.poll();
            if (cur == null && !queue.isEmpty()) {
                queue.offer(null);
                cnt++;
                continue;
            }
            Set<Integer> adjSet = adjMap.getOrDefault(cur, new HashSet<>());
            for (Integer adj : adjSet) {
                if (!visited[adj]) {
                    if (ids[adj] == val) return cnt + 1;
                    visited[adj] = true;
                    queue.offer(adj);
                }
            }
        }

        return Integer.MAX_VALUE;
    }


    public static void main(String[] args) throws IOException {
        final Scanner scanner = new Scanner(new FileInputStream("src/hackerrank/inputs/NearestClones.txt"));
        String[] graphNodesEdges = scanner.nextLine().split(" ");
        int graphNodes = Integer.parseInt(graphNodesEdges[0].trim());
        int graphEdges = Integer.parseInt(graphNodesEdges[1].trim());

        int[] graphFrom = new int[graphEdges];
        int[] graphTo = new int[graphEdges];

        for (int i = 0; i < graphEdges; i++) {
            String[] graphFromTo = scanner.nextLine().split(" ");
            graphFrom[i] = Integer.parseInt(graphFromTo[0].trim());
            graphTo[i] = Integer.parseInt(graphFromTo[1].trim());
        }

        long[] ids = new long[graphNodes];

        String[] idsItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < graphNodes; i++) {
            long idsItem = Long.parseLong(idsItems[i]);
            ids[i] = idsItem;
        }

        int val = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        System.out.println(findShortest(graphNodes, graphFrom, graphTo, ids, val));


        scanner.close();
    }

}
