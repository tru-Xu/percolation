import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double meanVal;
    private double halfInterval;
    private double stddeVal;
    public PercolationStats(int n, int trials){
        if(n <= 0||trials <= 0) throw new IllegalArgumentException();

        double [] sampleArray = new double[trials];
        int times = 0;
        while(times < trials){
            Percolation percolation = new Percolation(n);
            while(!percolation.percolates()){
                int openSite = StdRandom.uniform(n*n) + 1;
                int row,col;
                if(openSite%n == 0) {
                    row = openSite/n;
                    col = n;
                }else {
                    row = openSite/n + 1;
                    col = openSite%n;
                }
                if(!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }
            sampleArray[times] = (double)percolation.numberOfOpenSites()/((double)n*(double)n);
            times++;
        }
        this.meanVal = StdStats.mean(sampleArray);
        this.stddeVal = StdStats.stddev(sampleArray);
        this.halfInterval = 1.96 * stddeVal / java.lang.Math.sqrt(trials);

    }    // perform trials independent experiments on an n-by-n grid
    public double mean(){
        return this.meanVal;
    }                          // sample mean of percolation threshold
    public double stddev() {
        return this.stddeVal;
    }                       // sample standard deviation of percolation threshold
    public double confidenceLo(){
        return this.meanVal - this.halfInterval;

    }                  // low  endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.meanVal + this.halfInterval;
    }                 // high endpoint of 95% confidence interval

    public static void main(String[] args) {
        int n = StdIn.readInt();
        int T = StdIn.readInt();

        PercolationStats percolationStats = new PercolationStats(n,T);
        StdOut.print("meanVal = " + percolationStats.meanVal);
        StdOut.println();
        StdOut.print("stddevVal = " + percolationStats.stddeVal);
        StdOut.println();
        StdOut.print("95% confidence interval = [" + percolationStats.confidenceLo()
        +  ","+ percolationStats.confidenceHi() + "]" );

    }       // test client (described below)
}