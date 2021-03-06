package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final int head, tail;
    private boolean[][] statusMat;
    private int openSiteNum = 0;

    private final WeightedQuickUnionUF uf;

    /**
     * create n-by-n grid, with all sites blocked
     * @param n
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        head = 0;
        tail = n * n + 1;
        statusMat = new boolean[n][n];
        uf = new WeightedQuickUnionUF(tail + 1);
    }

    /**
     * open site (row, col) if it is not open already
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }

        statusMat[row - 1][col - 1] = true;
        openSiteNum++;

        int curIdx = posToIdx(row, col);
        if (row == 1) {
            uf.union(head, curIdx);
        }
        if (row == n) {
            uf.union(curIdx, tail);
        }

        // check its neighbours for union;
        if (row - 1 > 0) {
            if (isOpen(row - 1, col)) {
                uf.union(curIdx, posToIdx(row - 1, col));
            }
        }
        if (row + 1 <= n) {
            if (isOpen(row + 1, col)) {
                uf.union(curIdx, posToIdx(row + 1, col));
            }
        }
        if (col - 1 > 0) {
            if (isOpen(row, col - 1)) {
                uf.union(curIdx, posToIdx(row, col - 1));
            }
        }
        if (col + 1 <= n) {
            if (isOpen(row, col + 1)) {
                uf.union(curIdx, posToIdx(row, col + 1));
            }
        }
    }

    /**
     * is site (row, col) open?
     * @param row
     * @param col
     * @return
     */
    public boolean isOpen(int row, int col) {
        return isValid(row, col) && statusMat[row - 1][col - 1];
    }

    /**
     * is site (row, col) full
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(int row, int col) {
        return isValid(row, col) && uf.connected(head, posToIdx(row, col));
    }

    /**
     * number of open sites
     * @return
     */
    public int numberOfOpenSites() {
        return openSiteNum;
    }

    /**
     * does the system percolate?
     * @return
     */
    public boolean percolates() {
        return uf.connected(head, tail);
    }

    /**
     * check validation of row and col
     * @return
     */

    private boolean isValid(int row, int col) {
        return row > 0 && row <= n && col > 0 && col <= n;
    }

    /**
     * calculate the array index from (row, col)
     * @param row
     * @param col
     * @return
     */
    private int posToIdx(int row, int col) {
        return (row - 1) * n + col;
    }

}
