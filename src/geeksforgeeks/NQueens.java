package geeksforgeeks;

import util.ArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NQueens {

    List<String[]> res = new ArrayList<>();
    int count = 0;

    public List<String[]> solveNQueens(int n) {
        int[] arr = new int[n];
        Arrays.fill(arr, -1);
        solve(arr, 0, n);

        return res;
    }

    private void printBoard(int[] arr, int n) {
        String[] str = new String[n];
        for (int i = 0; i < n; i++) {
            String temp = "";
            for (int j = 0; j < n; j++) {
                if (arr[i] == j) {
                    temp += "Q";
                } else {
                    temp += ".";
                }
            }
            str[i] = temp;
        }

        res.add(str);
    }

    private boolean isValid(int[] arr, int col) {
        for (int i = 0; i < col; i++) {
            if (arr[i] == arr[col] || Math.abs(arr[i] - arr[col]) == (col - i)) {
                return false;
            }
        }
        return true;
    }

    private void solve(int[] arr, int col, int n) {
        if (col == n) {
            printBoard(arr, n);
            count++;
        } else {
            for (int i = 0; i < n; i++) {
                arr[col] = i;
                if (isValid(arr, col)) {
                    solve(arr, col + 1, n);
                }
            }
        }
    }

    public static void main(String[] args) {
        List<String[]> ret = new NQueens().solveNQueens(8);
        for (String[] arr : ret) {
            ArrayUtil.println(arr);
        }
    }
}
