import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.Math; 

public class PercolationStats {
	private int[] trialCountResults;
	private int numOfTrials;
	
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
    	
    	trialCountResults = new int[trials];
    	numOfTrials = trials;
    	
    	for (int i = 0; i < trials; i++) { 
    		Percolation trialGrid = new Percolation(n);
    		int count = 0;
    		
    		while (!trialGrid.percolates()) {
    			int row = StdRandom.uniform(1, n + 1);
    			int col = StdRandom.uniform(1, n + 1);
    			
    			if (!trialGrid.isOpen(row, col)) {
    				trialGrid.open(row, col);
    				count++;
    			}
    		}
    		trialCountResults[i] = count;
    	}
    	
    }

    // sample mean of percolation threshold
    public double mean() {
    	double mean = StdStats.mean(trialCountResults);
    	return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
    	double standardDeviation = StdStats.stddev(trialCountResults);
    	return standardDeviation;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
    	double lowConfidence = mean() - (1.96*stddev()/Math.sqrt(numOfTrials));
    	return lowConfidence;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
    	double highConfidence = mean() + (1.96*stddev()/Math.sqrt(numOfTrials));
    	return highConfidence;
    }

   // test client (see below)
   public static void main(String[] args) {
	   PercolationStats perkies = new PercolationStats(5, 100);
	   System.out.println("Mean:                    " + perkies.mean());
	   System.out.println("Stddev:                  " + perkies.stddev());
	   System.out.println("95% Confidence Interval: [" + perkies.confidenceLo() + ", " + perkies.confidenceHi() + "]");

   }

}