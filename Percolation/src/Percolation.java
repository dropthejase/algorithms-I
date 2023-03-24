import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {

    // properties
    private int n;
    private WeightedQuickUnionUF gridIdx;
    private WeightedQuickUnionUF gridIdxBackwash;
    private boolean[] gridOpen;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int num) {
        if (num <= 0)
            throw new IllegalArgumentException("n must be positive integer");
        
        n = num;
        openSites = 0;
        gridIdx = new WeightedQuickUnionUF(n * n + 2);
        gridIdxBackwash = new WeightedQuickUnionUF(n * n + 1);
        gridOpen = new boolean[n * n];

        // all sites are closed initially
        for (int i = 0; i < n * n; i++) {
            gridOpen[i] = false;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int rowCord, int colCord) {

        int row = rowCord - 1;
        int col = colCord - 1;
        
        if ((row >= n || row < 0) || (col >= n || col < 0))
            throw new IllegalArgumentException("Integer(s) must not be negative or out of bounds");

        // if not open, open it
        if (!isOpen(rowCord, colCord)) {
            gridOpen[(row * n) + col] = true;
            openSites++;
        }

        // make connections
        int i = row * n + col;

        // if opened and at the top, connect to virtual node
        if (i < n && gridOpen[i]) {
            gridIdx.union(0, i + 1);
            gridIdxBackwash.union(0, i + 1);
        }

        // if opened and at the bottom, connect to virtual node
        if (i >= n * (n - 1) && gridOpen[i])
            gridIdx.union(n * n + 1, i + 1);
            
        // if above is valid, union if both i and above_i = open
        if (!(i - n < 0)) {
            if (gridOpen[i] && gridOpen[i - n]) {
                gridIdx.union(i + 1, i - n + 1);
                gridIdxBackwash.union(i + 1, i - n + 1);
            }
        }
        // if below is valid, union if both i and below_i = open
        if (!(i + n >= n * n)) {
            if (gridOpen[i] && gridOpen[i + n]) {
                gridIdx.union(i + 1, i + n + 1);
                gridIdxBackwash.union(i + 1, i + n + 1);
            }                
        }
        // if not on left side, union if both i and left_i = open
        if (i % n != 0) {
            if (gridOpen[i] && gridOpen[i - 1]) {
                gridIdx.union(i + 1, i);
                gridIdxBackwash.union(i + 1, i);
            }             
        }
        // if not on right side, union if both i and right_i = open
        if ((i + 1) % n != 0) {
            if (gridOpen[i] && gridOpen[i + 1]) {
                gridIdx.union(i + 1, i + 2);  
                gridIdxBackwash.union(i + 1, i + 2);  
            }            
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int rowCord, int colCord) { 

        int row = rowCord - 1;
        int col = colCord - 1;

        if ((row >= n || row < 0) || (col >= n || col < 0))
            throw new IllegalArgumentException("Integer(s) must not be negative or out of bounds");
        return gridOpen[(row * n) + col];
    }

    // does the system percolate?
    public boolean percolates() {
        if (gridIdx.find(0) == gridIdx.find(n * n + 1))
            return true;
        return false;
    }
    
    // returns number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // is the site (row, col) full?
    public boolean isFull(int rowCord, int colCord) {

        int row = rowCord - 1;
        int col = colCord - 1;

        // flatten
        int i = row * n + col;

        // check within range
        if (i < 0 || i >= n * n)
            throw new IllegalArgumentException("Integer(s) must not be negative or out of bounds");

        // is it connected to virtual top?
        if (isOpen(rowCord, colCord) && gridIdxBackwash.find(i + 1) == gridIdxBackwash.find(0))
            return true;
        return false;
    }

    // print the grid
    /* public void printGrid() {

        // print gridIdx
        StdOut.println(gridIdx.find(0));
        for (int row = 0; row < n; row++) {
            int[] temp = new int[n];
            for (int col = 0; col < n; col++) { 
                temp[col] = gridIdx.find(row * n + col + 1);
            }
            StdOut.print(java.util.Arrays.toString(temp));
            StdOut.print("\n");
        }
        StdOut.println(gridIdx.find(n * n + 1));

        // print gridOpen
        for (int row = 0; row < n; row++) {
            Boolean[] temp = new Boolean[n];
            for (int col = 0; col < n; col++) { 
                //temp[col] = gridOpen[row + col];
                temp[col] = isOpen(row + 1, col + 1);
            }
            StdOut.print(java.util.Arrays.toString(temp));
            StdOut.print("\n");
        }
    } */

    public static void main(String[] args) {

        Percolation perc1 = new Percolation(Integer.parseInt(args[0]));

        while (!perc1.percolates()) {

            // randomly open sites
            int row = StdRandom.uniformInt(1, perc1.n + 1);
            int col = StdRandom.uniformInt(1, perc1.n + 1);
            perc1.open(row, col);
        }
    }
}
