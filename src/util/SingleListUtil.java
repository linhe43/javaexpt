package util;

import common.SingleListNode;

public class SingleListUtil {

    public static <T> SingleListNode<T> reverse(SingleListNode<T> root) {
        if (root == null || root.next == null) return root;

        SingleListNode cur = root, prev = null;
        SingleListNode tmp;
        while (cur != null) {
            tmp = cur.next;
            cur.next = prev;
            prev = cur;
            cur = tmp;
        }

        return prev;
    }

    public static <T> void print(SingleListNode<T> root) {
        SingleListNode cur = root;
        while (cur != null) {
            System.out.print(cur.val + ", ");
            cur = cur.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        SingleListNode root = new SingleListNode(0);
        SingleListNode cur = root;
        for (int i = 1; i <= 10; i++) {
            cur.next = new SingleListNode(i);
            cur = cur.next;
        }

        print(root);
        print(reverse(root));
        print(root);
    }

}
