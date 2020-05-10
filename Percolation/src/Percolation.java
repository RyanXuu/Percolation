import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	// instance variables
	private boolean[][] booleanGrid;
	private WeightedQuickUnionUF unionGrid;
	private int sideLength;
	private int openSites;
	
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) throws IllegalArgumentException {
    	
    	if (n<= 0) {
    		throw new IllegalArgumentException();
    	}
    	
    	sideLength = n;
    	openSites = 0;
    	
    	booleanGrid = new boolean[n][n]; // creates the grid	
    	for (int i = 0; i < n; i++) {
    		for (int j = 0; j < n; j++) {
    			booleanGrid[i][j] = false;
    		}
    	}
    	
    	unionGrid = new WeightedQuickUnionUF(n*n + 2);
    	for (int i = 0; i <= n; i++) {
    		unionGrid.union(n*n, i);
    	}
    	for (int i = n*n-n; i < n*n; i++) {
    		unionGrid.union(n*n + 1, i);
    	}
    }
    
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) throws IllegalArgumentException {	
    	
    	if (row <= 0 || row > sideLength || col <=0 || col > sideLength) {
    		throw new IllegalArgumentException();
    	}
    	
    	int current = matrixToId(row,col);
    	int above = matrixToId(row - 1, col);
    	int below = matrixToId(row + 1, col);
    	int left = matrixToId(row, col - 1);
    	int right = matrixToId(row, col + 1);
    	
    	row--;
    	col--;
    	booleanGrid[row][col] = true;
    	
    	
    	if (col > 0) {
    		if (booleanGrid[row][col - 1]) {
    			unionGrid.union(current,left);	
    		}
    	}
    	if (col < sideLength - 1) {
    		if (booleanGrid[row][col + 1] && col < sideLength) {
    		unionGrid.union(current,right);
    		}
    	}
    	if (row > 0) {
    		if (booleanGrid[row - 1][col] && row > 0) {
    		unionGrid.union(current,above);
    		}
    	}
    	if (row < sideLength - 1)
    		if (booleanGrid[row + 1][col]) {
    			unionGrid.union(current,below);
    	}
    	
    	openSites++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
    	
    	if (row <= 0 || row > sideLength*sideLength || col <=0 || col > sideLength*sideLength) {
    		throw new IllegalArgumentException();
    	}
    	
    	return booleanGrid[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
    	
    	if (row <= 0 || row > sideLength*sideLength || col <=0 || col > sideLength*sideLength) {
    		throw new IllegalArgumentException();
    	}
    	
    	int current = matrixToId(row,col);
    	int topSite = sideLength*sideLength;
    	
    	if (unionGrid.connected(current,topSite)) {
    		return true;
    	}
    	return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
    	return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
    	
    	int topSite = sideLength * sideLength;
    	int bottomSite = sideLength * sideLength + 1;
    	
    	if (unionGrid.connected(topSite, bottomSite)) {
    		return true;
    	}
    	return false;
    }
    
    // changes from matrix to id
    private int matrixToId(int row,int col) {
    	int toId = (row - 1) * sideLength + col - 1;
    	return toId;
    }

}