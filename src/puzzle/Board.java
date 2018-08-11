package puzzle;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int dimension;
    private int[][] board;
    private int hammingDist;
    private int manhattanDist;

    private int blankRowIdx, blankColIdx;

    /**
     * construct a board from an n-by-b array of blocks
     * @param blocks
     */
    public Board(int[][] blocks) {
        dimension = blocks.length;
        board = new int[dimension][];
        for (int i = 0; i < dimension; i++) {
            board[i] = new int[dimension];
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] == 0) {
                    blankRowIdx = i;
                    blankColIdx = j;
                }
                board[i][j] = blocks[i][j];
            }
        }

        // calculate hamming dist;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j] == 0) {
                    continue;
                }
                int curIdx = i * dimension + j + 1;
                if (board[i][j] != curIdx || (curIdx == dimension * dimension && board[i][j] != 0)) {
                    hammingDist++;
                }
            }
        }

        // calculate manhattan dist;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j] == 0) {
                    continue;
                }
                int destRow = (board[i][j] - 1) / dimension;
                int destCol = (board[i][j] - 1) % dimension;
                int dist = i > destRow ? i - destRow : destRow - i;
                dist += j > destCol ? j - destCol : destCol - j;
                manhattanDist += dist;
            }
        }
    }

    /**
     * board dimension n
     * @return the dimension n of the board.
     */
    public int dimension() {
        return dimension;
    }

    /**
     * number of blocks out of place
     * @return the number of blocks out of place
     */
    public int hamming() {
        return hammingDist;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     * @return the sum of Manhattan distance between blocks and goal.
     */
    public int manhattan() {
        return manhattanDist;
    }

    /**
     * is this board the goal board?
     * @return is this board the goal board?
     */
    public boolean isGoal() {
        return hammingDist == 0;
    }

    /**
     * a board that is obtained by exchanging any pair of blocks
     * @return a board that is obtained by exchanging any pair of blocks.
     */
    public Board twin() {
        int[][] clone = copyBoardBlock();

        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension - 1; col++) {
                if (clone[row][col] != 0 && clone[row][col + 1] != 0) {
                    swap(clone, row, col, row, col + 1);
                    return new Board(clone);
                }
            }
        }

        return null;
    }

    /**
     * does this board equal y?
     * @param y
     * @return
     */
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }

        return ((Board) y).equals(board);
    }

    /**
     * helper function for comparing boards
     * @param board
     * @return
     */
    private boolean equals(int[][] board) {
        if (board == null || dimension != board.length) {
            return false;
        }

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (this.board[i][j] != board[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * all neighboring boards
     * @return
     */
    public Iterable<Board> neighbors() {
        List<Board> neighborList = new ArrayList<Board>();

        if (blankRowIdx - 1 >= 0) {
            int[][] clone = copyBoardBlock();
            swap(clone, blankRowIdx, blankColIdx, blankRowIdx - 1, blankColIdx);
            neighborList.add(new Board(clone));
        }
        if (blankRowIdx + 1 < dimension) {
            int[][] clone = copyBoardBlock();
            swap(clone, blankRowIdx, blankColIdx, blankRowIdx + 1, blankColIdx);
            neighborList.add(new Board(clone));
        }
        if (blankColIdx - 1 >= 0) {
            int[][] clone = copyBoardBlock();
            swap(clone, blankRowIdx, blankColIdx, blankRowIdx, blankColIdx - 1);
            neighborList.add(new Board(clone));
        }
        if (blankColIdx + 1 < dimension) {
            int[][] clone = copyBoardBlock();
            swap(clone, blankRowIdx, blankColIdx, blankRowIdx, blankColIdx + 1);
            neighborList.add(new Board(clone));
        }

        return neighborList;
    }

    /**
     * get a copy of the block of a board
     * @return
     */
    private int[][] copyBoardBlock() {
        int[][] clone = new int[dimension][];
        for (int i = 0; i < dimension; i++) {
            clone[i] = new int[dimension];
            for (int j = 0; j < dimension; j++) {
                clone[i][j] = board[i][j];
            }
        }

        return clone;
    }

    /**
     * swap the adjacent two elements in a matrix
     * @param mat
     * @param row1
     * @param col1
     * @param row2
     * @param col2
     */
    private void swap(int[][] mat, int row1, int col1, int row2, int col2) {
        int tmp = mat[row1][col1];
        mat[row1][col1] = mat[row2][col2];
        mat[row2][col2] = tmp;
    }

    /**
     * generate the string representation of this board
     * @return string representation of this board (in the output format specified below)
     */
    public String toString() {
        if (dimension == 0) {
            return "0\n";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(dimension).append("\n ");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                sb.append(board[i][j]).append(" ");
            }
            sb.append("\n ");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        In in = new In("puzzle/puzzle04.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board b = new Board(blocks);
        System.out.println(b.toString());
        System.out.format("hamming distance = %d\nmanhattam distance = %d\n", b.hamming(), b.manhattan());

        System.out.println(b.isGoal());
    }
}