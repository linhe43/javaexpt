package puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Deque;
import java.util.LinkedList;

public class Solver {

    private MinPQ<SearchNode> minPQ;
    private boolean solvable;
    private SearchNode solutionNode;

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moveNum;
        private final SearchNode predecessor;

        public SearchNode(Board board, int moveNum, SearchNode predecessor) {
            this.board = board;
            this.moveNum = moveNum;
            this.predecessor = predecessor;
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.getPriority() - that.getPriority();
        }

        public int getPriority() {
            return moveNum + board.manhattan();
        }

        public Board getBoard() {
            return board;
        }

        public int getMoveNum() {
            return moveNum;
        }

        public SearchNode getPredecessor() {
            return predecessor;
        }
    }

    /**
     * find a solution to the initial board (using the A* algorithm)
     * @param initial
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        if (initial.dimension() == 0) {
            return;
        }

        solutionNode = null;
        minPQ = new MinPQ<SearchNode>();
        minPQ.insert(new SearchNode(initial, 0, null));
        while (true) {
            SearchNode curNode = minPQ.delMin();
            Board curBoard = curNode.getBoard();

            if (curBoard.isGoal()) {
                solvable = true;
                solutionNode = curNode;
                break;
            }
            if (curBoard.hamming() == 2 && curBoard.twin().isGoal()) {
                solvable = false;
                break;
            }

            int moveNum = curNode.getMoveNum();
            Board predecessor = moveNum > 0 ? curNode.getPredecessor().getBoard() : null;

            Iterable<Board> neighbors = curBoard.neighbors();
            for (Board neighbor : neighbors) {
                if (predecessor != null && neighbor.equals(predecessor)) {
                    continue;
                }
                minPQ.insert(new SearchNode(neighbor, moveNum + 1, curNode));
            }
        }
    }

    /**
     * is the initial board solvable?
     * @return true if the board is solvable, otherwise, return false.
     */
    public boolean isSolvable() {
        return solvable;
    }

    /**
     * min number of moves to solve initial board
     * @return the min number of moves to solve initial board; -1 if unsolvable.
     */
    public int moves() {
        return solvable ? solutionNode.getMoveNum() : -1;
    }

    /**
     * sequence of boards in a shortest solution
     * @return the sequence of boards in a shortest solution; null if unsolvable.
     */
    public Iterable<Board> solution() {
        if (!solvable) {
            return null;
        }

        Deque<Board> solution = new LinkedList<Board>();
        SearchNode node = solutionNode;
        while (node != null) {
            solution.addFirst(node.getBoard());
            node = node.getPredecessor();
        }

        return solution;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In("8puzzle/puzzle46.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}