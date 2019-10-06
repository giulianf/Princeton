import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Board {
    private final int n;

    private final char[] tiles;
    private int blankpos;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new char[n * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[k] = (char) tiles[i][j];
                if (tiles[i][j] == 0) blankpos = k;
                k++;
            }
        }
    }

    // first row = 1
    private int row(int p) {
        return (int) Math.ceil((double) p / (double) n);
    }

    // first column = 1
    private int col(int p) {
        if (p % n == 0) return n;
        return p % n;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n);
        s.append("\n");
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", (int) tiles[k]));
                k++;
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int k = 0, ans = 1; k < n * n; k++, ans++) {
            if (tiles[k] == 0) continue;
            if (tiles[k] != ans) hamming++;
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int k = 0; k < n * n; k++) {
            if (tiles[k] == 0) continue;
            int rowdiff = Math.abs(row(tiles[k]) - row(k + 1));
            int coldiff = Math.abs(col(tiles[k]) - col(k + 1));
            manhattan = manhattan + rowdiff + coldiff;
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int k = 0; k < n * n - 2; k++) {
            if (tiles[k] > tiles[k + 1]) return false;
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (!Arrays.equals(this.tiles, that.tiles)) return false;
        return true;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> stackNeighbors = new Stack<Board>();
        char[] neighbor;
        if (row(blankpos + 1) != 1) {
            neighbor = tiles.clone();
            swapAbove(neighbor, blankpos);
            Board neighborBoard = new Board(toTwoDarray(neighbor));
            stackNeighbors.push(neighborBoard);
        }
        if (row(blankpos + 1) != n) {
            neighbor = tiles.clone();
            swapBelow(neighbor, blankpos);
            Board neighborBoard = new Board(toTwoDarray(neighbor));
            stackNeighbors.push(neighborBoard);
        }
        if (col(blankpos + 1) != 1) {
            neighbor = tiles.clone();
            swapLeft(neighbor, blankpos);
            Board neighborBoard = new Board(toTwoDarray(neighbor));
            stackNeighbors.push(neighborBoard);
        }
        if (col(blankpos + 1) != n) {
            neighbor = tiles.clone();
            swapRight(neighbor, blankpos);
            Board neighborBoard = new Board(toTwoDarray(neighbor));
            stackNeighbors.push(neighborBoard);
        }
        return stackNeighbors;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        boolean swapSuccess = false;
        char[] twin = tiles.clone();
        // choose a non-blank block
        int k = 0;
        do {
            k = StdRandom.uniform(n * n);
        } while (tiles[k] == 0);
        // choose an exchange direction
        while (swapSuccess == false) {
            int choice = StdRandom.uniform(4);
            switch (choice) {
                case 0: //swapAbove
                    if (row(k + 1) == 1) swapSuccess = false;
                    else if (twin[k - n] == 0) swapSuccess = false;
                    else {
                        swapAbove(twin, k);
                        swapSuccess = true;
                    }
                    break;
                case 1: //swapBelow
                    if (row(k + 1) == n) swapSuccess = false;
                    else if (twin[k + n] == 0) swapSuccess = false;
                    else {
                        swapBelow(twin, k);
                        swapSuccess = true;
                    }
                    break;
                case 2: //swapLeft
                    if (col(k + 1) == 1) swapSuccess = false;
                    else if (twin[k - 1] == 0) swapSuccess = false;
                    else {
                        swapLeft(twin, k);
                        swapSuccess = true;
                    }
                    break;
                case 3: //swapRight
                    if (col(k + 1) == n) swapSuccess = false;
                    else if (twin[k + 1] == 0) swapSuccess = false;
                    else {
                        swapRight(twin, k);
                        swapSuccess = true;
                    }
                    break;
            }
        }
        return new Board(toTwoDarray(twin));
    }

    //swap functions
    private void swapAbove(char[] oneDarray, int k) {
        char temp = oneDarray[k];
        oneDarray[k] = oneDarray[k - n];
        oneDarray[k - n] = temp;
    }

    private void swapBelow(char[] oneDarray, int k) {
        char temp = oneDarray[k];
        oneDarray[k] = oneDarray[k + n];
        oneDarray[k + n] = temp;
    }

    private void swapLeft(char[] oneDarray, int k) {
        char temp = oneDarray[k];
        oneDarray[k] = oneDarray[k - 1];
        oneDarray[k - 1] = temp;
    }

    private void swapRight(char[] oneDarray, int k) {
        char temp = oneDarray[k];
        oneDarray[k] = oneDarray[k + 1];
        oneDarray[k + 1] = temp;
    }

    private int[][] toTwoDarray(char[] oneDarray) {
        int k = 0;
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                tiles[i][j] = oneDarray[k];
                k++;
            }
        return tiles;
    }


}