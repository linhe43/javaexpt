package kthsmallest;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class KthSmallestDistancePair {

    private class Node {
        public int root;
        public int nei;

        public Node(int r, int n) {
            root = r;
            nei = n;
        }
    }

    public int bruteForceSolution(int[] nums, int k) {
        Arrays.sort(nums);
        PriorityQueue<Node> heap = new PriorityQueue<>(nums.length,
                Comparator.comparingInt(node -> nums[node.nei] - nums[node.root]));
        for (int i = 0; i < nums.length - 1; i++) {
            heap.offer(new Node(i, i + 1));
        }

        Node node = null;
        while (k > 0) {
            node = heap.poll();
            if (node.nei + 1 < nums.length) {
                heap.offer(new Node(node.root, node.nei + 1));
            }
            k--;
        }

        return nums[node.nei] - nums[node.root];
    }

    public int binarySearchWithDP(int[] nums, int k) {
        Arrays.sort(nums);

        int len = nums.length;
        int hi = nums[len - 1] - nums[0];
        int lo = 0;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cnt = 0;
            int j = 0;
            for (int i = 0; i < len; i++) {
                while (j < len && nums[j] - nums[i] <= mid) j++;
                cnt += j - i - 1;
            }

            if (cnt >= k) {
                hi = mid - 1;
            } else if (cnt < k) {
                lo = mid + 1;
            }
        }

        return lo;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{
                5, 2, 4, 9, 15, 26, 47, 12, 8
        };
        int k = 15;

        KthSmallestDistancePair ksdp = new KthSmallestDistancePair();
        int ret = ksdp.bruteForceSolution(nums, k);
        System.out.println(ret);

        ret = ksdp.binarySearchWithDP(nums, k);
        System.out.println(ret);
    }
}
