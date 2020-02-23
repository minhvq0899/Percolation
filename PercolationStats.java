import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double[] threshole;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("n and trials must be bigger than 0");
        }

        Percolation test;
        threshole = new double[trials];
        int i;
        int j;
        for (int k = 0; k < trials; k++) {
            test = new Percolation(n);
            int openedSites = 0;
            while (!test.percolates()) {
                i = StdRandom.uniform(n);
                j = StdRandom.uniform(n);
                if (!test.isOpen(i, j)) {
                    test.open(i, j);
                    openedSites++;
                }
            }

            threshole[k] = (double) openedSites / (n * n);
        }

    }


    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshole);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshole);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (1.96 * stddev()) / (threshole.length ^ (1 / 2));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96 * stddev()) / (threshole.length ^ (1 / 2));

    }

    // test client (see below)
    public static void main(String[] args) {

        int N = Integer.parseInt(args[0]); // take input from user
        int T = Integer.parseInt(args[1]);
        Stopwatch time = new Stopwatch();   // initialize the stop watch
        PercolationStats minh = new PercolationStats(N, T); // run experiment T times
        double elapsedTime = time.elapsedTime(); // stops the watch
        StdOut.println("mean()           = " + minh.mean());
        StdOut.println("stddev()         = " + minh.stddev());
        StdOut.println("confidenceLow()  = " + minh.confidenceLow());
        StdOut.println("confidenceHigh() = " + minh.confidenceHigh());
        StdOut.println("elapsed time     = " + elapsedTime);
    }
}
