package leetcode;

import util.ArrayUtil;

public class HouseRobberII {

    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] amount = new int[n + 1];

        int maxAmount1 = 0;
        for (int i = 1; i <= n; i++) {
            if (i - 2 > 0 && i - 3 > 1) {
                amount[i] = Math.max(amount[i - 2], amount[i - 3]) + nums[i - 1];
            } else if (i - 2 > 1) {
                amount[i] = amount[i - 2] + nums[i - 1];
            } else {
                amount[i] = nums[i - 1];
            }
            if (amount[i] > maxAmount1) {
                maxAmount1 = amount[i];
            }
        }
        ArrayUtil.print(amount);

        int maxIdx = 0, maxAmount2 = 0;
        for (int i = 1; i <= n; i++) {
            if (i - 2 > 0 && i - 3 > 0) {
                amount[i] = Math.max(amount[i - 2], amount[i - 3]) + nums[i - 1];
            } else if (i - 2 > 0) {
                amount[i] = amount[i - 2] + nums[i - 1];
            } else {
                amount[i] = nums[i - 1];
            }
            if (amount[i] > maxAmount2) {
                maxIdx = i;
                maxAmount2 = amount[i];
            }
        }
        ArrayUtil.print(amount);

        if (maxIdx == n && maxAmount2 == maxAmount1) {
            return maxAmount2;
        }

        maxAmount2 = 0;
        for (int i = 1; i < n; i++) {
            maxAmount2 = Math.max(amount[i], maxAmount2);
        }

        return Math.max(maxAmount1, maxAmount2);
    }

    public static void main(String[] args) {
        int[] nums = {2,2,4,3,2,5};
        nums = new int[] {1, 1, 3, 6, 7, 10, 7, 1, 8, 5, 9, 1, 4, 4, 3};
        nums = new int[] {2, 3, 2};
        nums = new int[] {1, 2, 3, 1};
        nums = new int[] {2,7,9,3,1};
        System.out.println(new HouseRobberII().rob(nums));
    }
}
