package leetcode;

import util.ArrayUtil;

import java.util.Arrays;

public class RedundantDirectConnection {

    public int[] findRedundantDirectedConnection(int[][] edges) {
        int[] ret = new int[0];
        if (edges == null || edges.length == 0) return ret;

        int len = edges.length;
        int[] roots = new int[len + 1];
        for (int i = 1; i <= len; i++) roots[i] = i;

        int[] cand1 = null, cand2 = null;
        for (int[] e : edges) {
            int rootx = find(roots, e[0]), rooty = find(roots, e[1]);
            if (rootx != rooty) {
                if (rooty != e[1]) cand1 = e; // record the last edge which results in "multiple parents" issue
                else roots[rooty] = rootx;
            } else cand2 = e; // record last edge which results in "cycle" issue, if any.
        }

        // if there is only one issue, return this one.
        if (cand1 == null) return cand2;
        if (cand2 == null) return cand1;

        // If both issues present, then the answer should be the first edge which results in "multiple parents" issue
        // Could use map to skip this pass, but will use more memory.
        for (int[] e : edges) {
            if (e[1] == cand1[1]) {
                ret = e;
                break;
            }
        }

        return ret;
    }

    private int find(int[] roots, int i) {
        while (i != roots[i]) {
            i = roots[i];
        }
        return i;
    }

    public static void main(String[] args) {
        int[][] edges = new int[][]{{2, 1}, {3, 1}, {4, 2}, {1, 4}};
        int[] ret = new RedundantDirectConnection().findRedundantDirectedConnection(edges);

        Integer[] objArr = Arrays.stream(ret).boxed().toArray(Integer[]::new);
        ArrayUtil.print(objArr);
    }
}
