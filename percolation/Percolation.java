package percolation;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    public static void main(String[] args) {
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(5);
        uf.union(0, 1);
        uf.union(1, 2);
        uf.union(3, 4);
        System.out.println(uf.connected(0, 2));
        System.out.println(uf.connected(2, 4));
        System.out.println(uf.count() + " components");
    }
}
