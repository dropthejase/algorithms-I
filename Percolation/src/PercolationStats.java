import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private int iter;
    private int n;
    private double[] pThresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int num, int trials) {

        if (num <= 0 || trials <= 0)
            throw new IllegalArgumentException("Please enter two positive integers for: mum and trials");
        
        n = num;
        iter = trials;
        pThresholds = new double[iter];

        // training loop
        for (int it = 0; it < iter; it++) {
            // initialise new perc
            Percolation perc = new Percolation(n);

            // check perculations
            while (!perc.percolates()) {
                // randomly open sites
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                perc.open(row, col);
            }
            // add perc threshold to pThresholds
            pThresholds[it] = (double) perc.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(pThresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(pThresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(iter));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(iter));
    }

    // test client (see below)
    public static void main(String[] args) {

        int nGrid = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats percStats = new PercolationStats(nGrid, t);

        // print stats
        StdOut.println("mean = " + percStats.mean());
        StdOut.println("stddev = " + percStats.stddev());
        StdOut.println("95% confidence interval = [" + percStats.confidenceLo() + ", " + percStats.confidenceHi() + "]");
    }

}
