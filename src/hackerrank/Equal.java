package hackerrank;

import java.io.*;
import java.util.*;

public class Equal {

    private static int[] steps = new int[] {1, 3, 5};

    class Node {
        int[] arr;
        int num;

        Node(int[] arr, int num) {
            this.arr = arr;
            this.num = num;
        }
    }

    // Complete the equal function below.
    static int equal(int[] arr) {
        return new Equal().solve(arr);
    }

    int solve(int[] arr) {
        int n = arr.length;

        int max = 0, sum = 0;
        for (int i : arr) {
            sum += i;
            if (max < i) max = i;
        }
        List<List<Node>> list = new ArrayList<>();
        for (int i = 0; i < sum; i++) list.add(new ArrayList<>());
        list.add(new ArrayList<>());
        list.get(sum).add(new Node(arr, 0));
        int idx = sum + 1;
        int minNum = Integer.MAX_VALUE;
        while (true) {
            List<Node> curList = new ArrayList<>();
            list.add(curList);
            for (int step : steps) {
                int stepSum = step * (n - 1);
                if (idx <= stepSum) {
                    continue;
                }
                for (Node node : list.get(idx - stepSum)) {
                    for (int i = 0; i < n; i++) {
                        int[] newArr = node.arr.clone();
                        for (int j = 0; j < n; j++) {
                            if (j == i) continue;
                            newArr[j] += step;
                        }
                        if (checkValid(newArr)) {
                            minNum = Math.min(minNum, node.num + 1);
                        }
                        curList.add(new Node(newArr, node.num + 1));
                    }
                }
            }

            if (minNum < Integer.MAX_VALUE) break;
            idx++;
        }

        return minNum;
    }

    private boolean checkValid(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] != arr[i]) return false;
        }
        return true;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int[] arr = new int[]{
                1, 5, 5
        };
//        int[] arr = new int[] {2, 2, 3, 7};

        System.out.println(equal(arr));
    }
}
