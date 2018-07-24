package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private final int trials;

    private final double mu;
    private final double sigma;

    /**
     * perform trials independent experiments on an b-by-n grid
     * @param n
     * @param trials
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.trials = trials;

        double[] rArr = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (true) {
                int row = StdRandom.uniform(n) + 1, col = StdRandom.uniform(n) + 1;
                p.open(row, col);
                if (p.percolates()) {
                    rArr[i] = (double) p.numberOfOpenSites() / (n * n);
                    break;
                }
            }
        }

        mu = StdStats.mean(rArr);
        sigma = StdStats.stddev(rArr);
    }

    /**
     * sample mean of percolation threshold
     * @return
     */
    public double mean() {
        return mu;
    }

    /**
     * sample stadard deviation of percolation threshold
     * @return
     */
    public double stddev() {
        return sigma;
    }

    /**
     * low endpoint of 95% confidence interval
     * @return
     */
    public double confidenceLo() {
        return mu - CONFIDENCE_95 * sigma / Math.sqrt(trials);
    }

    /**
     * high endpoint of 95% confidence interval
     * @return
     */
    public double confidenceHi() {
        return mu + CONFIDENCE_95 * sigma / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            System.out.println("Usage: percolation.PercolationStats n trials");
            return;
        }

        int n, trials;
        try {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Argument error");
            return;
        }

        PercolationStats ps = new PercolationStats(n, trials);
        System.out.format("mean\t\t\t\t\t= %f\nstddev\t\t\t\t\t= %f\n95 confidence interval\t= [%f, %f]",
                ps.mean(), ps.stddev(), ps.confidenceLo(), ps.confidenceHi());
    }

}
