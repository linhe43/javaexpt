package uber;

/**
 * Given a sorted array of integers, find kth smallest square of the integer.
 k starts from 0

 Example:
 {-9, -8, -4, -3, -1, 2, 5, 6, 10}
 k = 2
 answer: (-3) * (-3) = 9
 */

public class KthSmallestSquare {

    public static void main(String args[] ) {
        int[] arr = new int[] {-9, -8, -4, -3, -1, 2, 5, 6, 10};
        int k = 2;

        System.out.println(getKthSmallestSquare(arr, k));
    }

    private static int getKthSmallestSquare(int[] arr, int k) {
        if (arr == null || arr.length == 0) return 0;

        int len = arr.length;
        if (arr[0] >= 0) return arr[k] * arr[k];
        if (arr[len - 1] <= 0) return arr[len - k - 1] * arr[len - k - 1];


        // find the postion of the first non-negtive number;
        int p = getPosOfNonNegNum(arr);
        int np = p - 1;

        while (k > 0) {
            if (-arr[np] < arr[p]) {
                np--;
            } else {
                p++;
            }
            k--;
        }

        return -arr[np] <= arr[p] ? arr[np] * arr[np] : arr[p] * arr[p];
    }

    private static int getPosOfNonNegNum(int[] arr) {
        int lo = 0, hi = arr.length - 1;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (arr[mid] < 0) lo = mid + 1;
            else if (arr[mid] == 0) return mid;
            else hi = mid;
        }

        return hi;
    }
}