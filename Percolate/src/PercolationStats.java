import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

// https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
public class PercolationStats {
    private static final double ONE_NINE_SIX = 1.96;
    private final double[] results;
    private final int trials;

    // perform independent trials on an n-by-n grid		
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        results = new double[trials];
        this.trials = trials;

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            double step = 0;

            while (!percolation.percolates()) {

                int randomRow = 1 + StdRandom.uniform(n);
                int randomCol = 1 + StdRandom.uniform(n);

                if (!percolation.isOpen(randomRow, randomCol)) {
                    step++;
                    percolation.open(randomRow, randomCol);
                }
            }
            //          System.out.println("********");
            //          System.out.println("PERCOLATED i "+i);
            //          System.out.println("********");

            results[i] = step / Math.pow(n, 2);
        }


    }

    // sample mean of percolation threshold		
    public double mean() {
        return StdStats.mean(results);

    }

    // sample standard deviation of percolation threshold		
    public double stddev() {
        return StdStats.stddev(results);

    }

    // low endpoint of 95% confidence interval		
    public double confidenceLo() {
        return mean() - ((ONE_NINE_SIX * stddev()) / Math.sqrt(trials));

    }

    // high endpoint of 95% confidence interval		
    public double confidenceHi() {
        return mean() + ((ONE_NINE_SIX * stddev()) / Math.sqrt(trials));
    }

    // test client (see below)		
    public static void main(String[] args) {
        // test
        int n = 2;
        StdOut.println("N : " + n);
        int trials = 100000;
        StdOut.println("trials : " + trials);
        PercolationStats percolationStats = new PercolationStats(n, trials);

        StdOut.println("mean = " + percolationStats.mean());
        StdOut.println("stddev = " + percolationStats.stddev());
        StdOut.println("95% interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}