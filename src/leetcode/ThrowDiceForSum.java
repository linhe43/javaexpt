package leetcode;

import util.ArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThrowDiceForSum {

    public List<int[]> getThrows(int sum, int k, int n) {
        List<int[]> ret = new ArrayList<>();

        List<Integer>[][] dp = new List[n + 1][sum + 1];
        // initialize the first row;
        for (int j = 1; j <= k && j <= sum; j++) {
            dp[1][j] = new ArrayList<>();
            dp[1][j].add(j);
        }

        // fill the remaining part of the dp matrix;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                for (int m = 1; m <= k; m++) {
                    if (j - m > 0 && dp[i - 1][j - m] != null) {
                        if (dp[i][j] == null) dp[i][j] = new ArrayList<>();
                        dp[i][j].add(m);
                    }
                }
            }
        }

        // backtrack from dp[n][sum];
        List<List<Integer>> list = new ArrayList<>();
        backtrack(dp, n, sum, list);

        for (List<Integer> l : list) {
            ret.add(l.stream().mapToInt(Integer::intValue).toArray());
        }
        return ret;
    }

    private void backtrack(List<Integer>[][] dp, int n, int sum, List<List<Integer>> ret) {
        if (n == 1) {
            if (dp[n][sum] != null) {
                ret.add(new ArrayList<>(dp[n][sum]));
                return;
            }
        }
        List<Integer> candList = dp[n][sum];
        for (int num : candList) {
            List<List<Integer>> local = new ArrayList<>();
            backtrack(dp, n - 1, sum - num, local);
            for (List<Integer> list : local) {
                list.add(num);
                ret.add(list);
            }
        }
    }

    public static void main(String[] args) {
        int k = 6; // face number of the dice;
        int n = 3; // number of throws;
        int sum = 15; // the targeted sum;

        List<int[]> ways = new ThrowDiceForSum().getThrows(sum, k, n);
        for (int[] arr : ways) {
            ArrayUtil.print(Arrays.stream(arr).boxed().toArray(Integer[]::new));
        }
    }

}
