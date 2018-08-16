package hackerrank;

import java.util.*;

public class NewYearChaotics {

    // Complete the minimumBribes function below.
    static void minimumBribes(int[] q) {
        int cnt = 0;
        int hi = q.length - 1;
        while (hi > 0) {
            int localCnt = 0;
            while (hi > 0 && q[hi - 1] < q[hi]) hi--;
            if (hi == 0) break;
            int cur = hi - 1;
            while (cur < q.length - 1 && q[cur] > q[cur + 1]) {
                cnt++;
                localCnt++;
                if (localCnt > 2) {
                    System.out.println("Too chaotic");
                    return;
                }
                swap(q, cur, cur + 1);
                cur++;
            }
            hi--;
        }
        System.out.println(cnt);
    }

    static void swap(int[] q, int i, int j) {
        int tmp = q[i];
        q[i] = q[j];
        q[j] = tmp;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] q = new int[n];

            String[] qItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int qItem = Integer.parseInt(qItems[i]);
                q[i] = qItem;
            }

            minimumBribes(q);
        }

        scanner.close();
    }
}