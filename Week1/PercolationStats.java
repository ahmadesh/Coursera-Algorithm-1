import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats { // perform trials independent experiments on an n-by-n grid
    
    private double[] stat; 
    
    public PercolationStats(int n, int trials) {
        
        stat = new double[trials];
        Percolation perc;
      
        int count;
        int i;
        int j;
        for (int k = 0; k < trials; k++) {              
            count = 0;
            perc = new Percolation(n);
            while (!perc.percolates()) {
                i = StdRandom.uniform(1, n+1);
                j =  StdRandom.uniform(1, n+1);
                if (!perc.isOpen(i, j)) {
                    perc.open(i, j);
                    count++;
                }
            }
        stat[k] = (double) count/(n*n);
        }
      
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(stat);
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
    return StdStats.stddev(stat);
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
    return this.mean() - 1.96*(this.stddev())/Math.sqrt(stat.length);
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    return this.mean() + 1.96*(this.stddev())/Math.sqrt(stat.length);
    }

    // test client (described below)
    public static void main(String[] args) {
        int nn = Integer.parseInt(args[0]);
        int intrial = Integer.parseInt(args[1]);
        
        PercolationStats percstat = new PercolationStats(nn, intrial);
        
        System.out.println("mean = "+ percstat.mean());
        System.out.println("stddev = "+ percstat.stddev());
        System.out.println("95% confidence interval = "+ percstat.confidenceLo() + ", "+ percstat.confidenceHi());
    }      
}