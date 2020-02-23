import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n; // length of grid
    private boolean[][] status;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF full;
    private int top = 0; // virtual top site
    private int bottom; // virtual bottom site
    private int numberOfOpenSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 0) {
            throw new java.lang.IllegalArgumentException("Number of grid must be positive");
        }
        this.n = n;
        this.bottom = n * n + 1;
        uf = new WeightedQuickUnionUF(n * n + 2);
        full = new WeightedQuickUnionUF(n * n + 1);
        status = new boolean[n][n];
    }

    private int to1D(int row, int col) {
        return n * row + (1 + col);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row >= n || col >= n) {
            throw new java.lang.IllegalArgumentException("Cannot open this site because it does not exist");
        }

        if (!isOpen(row, col)) {
            // update the 2-dimensional array
            status[row][col] = true;
            numberOfOpenSites++;

            // check if the site we want to open is on the top row or bottom row
            if (row == 0) {
                uf.union(top, to1D(row, col));
                full.union(top, to1D(row, col));
            } else if (row == (n - 1)) {
                uf.union(bottom, to1D(row, col));
            }

            // union the open site with other open site next to it
            if (col > 0 && isOpen(row, col - 1)) {  // check left
                uf.union(to1D(row, col), to1D(row, col - 1));
                full.union(to1D(row, col), to1D(row, col - 1));
            }
            if (row > 0 && isOpen(row - 1, col)) {  // check up
                uf.union(to1D(row, col), to1D(row - 1, col));
                full.union(to1D(row, col), to1D(row - 1, col));
            }
            if (col < (n - 1) && isOpen(row, col + 1)) {  // check right
                uf.union(to1D(row, col), to1D(row, col + 1));
                full.union(to1D(row, col), to1D(row, col + 1));
            }
            if (row < (n - 1) && isOpen(row + 1, col)) {  // check down
                uf.union(to1D(row, col), to1D(row + 1, col));
                full.union(to1D(row, col), to1D(row + 1, col));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row >= n || col >= n) {
            throw new java.lang.IllegalArgumentException("Cannot open this site because it does not exist");
        }
        return status[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return full.connected(top, to1D(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);

        // test the constructor
        Percolation minh = new Percolation(size);
        StdOut.println("The whole boolean 2-dimensional array is set as " + minh.status[0][0]);

        // test open(), isOpen() and isFull()
        minh.open(0, 0);
        minh.open(2, 0);
        minh.open(1, 0);
        if (!minh.isFull(2, 0)) StdOut.print("Failed. Expected result is True");
        else StdOut.println("Pass");
    }
}
