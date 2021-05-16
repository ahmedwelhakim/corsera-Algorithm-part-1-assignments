import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONF_95 = 1.96;
    private final int t;

    private final double[] pt; // percolation thresholds

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        t = trials;
        if (n <= 0 || trials < 0) {
            throw new IllegalArgumentException();
        }
        pt = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            do {

                p.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            } while (!p.percolates());
            pt[i] = ((double) p.numberOfOpenSites() / (n * n));
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(pt);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(pt);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONF_95 * stddev()) / Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONF_95 * stddev()) / Math.sqrt(t);

    }

    // test client (see below)
    public static void main(String[] args) {
        int n, t;
        n = Integer.parseInt(args[0]);
        t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        StdOut.println("mean \t\t\t\t\t= " + ps.mean());
        StdOut.println("stddev \t\t\t\t\t= " + ps.stddev());
        StdOut.println(
                "95% confidence interval = " + "[" + ps.confidenceLo() + ", " + ps.confidenceHi()
                        + "]");
    }
}
