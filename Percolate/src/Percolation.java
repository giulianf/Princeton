import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private static final int TOP = 0;
    // convention 1,1 left-top
    private boolean[][] sites;
    // START from 0
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final int n;
    private final int virtualBottom;

    // creates n-by-n grid, with all sites initially blocked		
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        sites = new boolean[n][n];
        this.n = n;
        virtualBottom = n * n + 1;

        weightedQuickUnionUF = new WeightedQuickUnionUF(virtualBottom + 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sites[i][j] = false;
            }
        }
    }

    // opens the site (row, col) if it is not open already		
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new IllegalArgumentException();
        }

        sites[row - 1][col - 1] = true;

        int numberGrid = getNumberGrid(row, col);
        // check around if it's open and union it
        if (row == 1) {
            weightedQuickUnionUF.union(numberGrid, TOP);
        }
        if (row == n) {
            weightedQuickUnionUF.union(numberGrid, virtualBottom);
        }

        // top
        if (row - 1 != 0 && isOpen(row - 1, col)) {
            weightedQuickUnionUF.union(numberGrid, getNumberGrid(row - 1, col));
        }
        // bottom
        if (row + 1 <= n && isOpen(row + 1, col)) {
            weightedQuickUnionUF.union(numberGrid, getNumberGrid(row + 1, col));
        }
        // left
        if (col - 1 != 0 && isOpen(row, col - 1)) {
            weightedQuickUnionUF.union(numberGrid, getNumberGrid(row, col - 1));
        }
        // right
        if (col + 1 <= n && isOpen(row, col + 1)) {
            weightedQuickUnionUF.union(numberGrid, getNumberGrid(row, col + 1));
        }
    }

    // is the site (row, col) open?		
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new IllegalArgumentException();
        }

        return sites[row - 1][col - 1];
    }

    // is the site (row, col) full?		
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new IllegalArgumentException("row: " + row + " col: " + col + " count: " + n);
        }
        return weightedQuickUnionUF.connected(TOP, getNumberGrid(row, col));
    }

    // returns the number of open sites		
    public int numberOfOpenSites() {
        int opened = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (sites[i][j]) {
                    opened++;
                }
            }
        }
        return opened;
    }

    // does the system percolate?		
    public boolean percolates() {
        return weightedQuickUnionUF.connected(TOP, virtualBottom);
    }

    /**
     * grid starting from 0 to n -1
     *
     * @param row
     * @param col
     * @return
     */
    private int getNumberGrid(int row, int col) {
        return (row - 1) * n + (col);
    }

    public static void main(String[] args) {
        System.out.println("Hi");

    }
}