import edu.princeton.cs.algs4.Queue;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;

public class Board {

    private final int n;
    private final int[] tiles;
    private final int manhattan;
    
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles[0].length;
        this.tiles = new int[n * n];

        // create tiles
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                this.tiles[row * n + col] = tiles[row][col];
            }
        }

        this.manhattan = manhattan();
    }

    // string representation of this board
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i * n + j]));
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
        int count = 0;
        for (int i = 0; i < n * n; i++) {
            if (tiles[i] == 0)
                continue;
            if (tiles[i] != i + 1)
                count++;
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int count = 0;
        int correctRow;
        int correctCol;

        for (int i = 0; i < n * n; i++) {
            if (tiles[i] != 0) {
                correctRow = toRowCol(tiles[i] - 1)[0];
                correctCol = toRowCol(tiles[i] - 1)[1];
                count += Math.abs(correctRow - toRowCol(i)[0]) + Math.abs(correctCol - toRowCol(i)[1]);
            }
        }
        return count;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (manhattan == 0) return true;
        else return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        return this.toString().equals(y.toString());
    }

    // helper function to find location of zero / bank
    // returns [row, col]
    private int[] findZero() {
        int[] zeroLocation = new int[2];

        for (int i = 0; i < n * n; i++) {
            if (tiles[i] == 0)
                zeroLocation = toRowCol(i);
        }
        return zeroLocation;
    }

    // converts a given idx to a row and column index
    // returns [row, col]
    private int[] toRowCol(int idx) {
        int[] twoD  = new int[2];
        twoD[0] = Math.floorDiv(idx, n);
        twoD[1] = idx % n;
        return twoD;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

        Queue<Board> neighbours = new Queue<>();
        int zeroRow = findZero()[0];
        int zeroCol = findZero()[1];

        // if not top row, swap above
        if (zeroRow > 0)
            neighbours.enqueue(exch(zeroRow, zeroCol, zeroRow - 1, zeroCol));
        // if left valid, swap left
        if (zeroCol > 0)
            neighbours.enqueue(exch(zeroRow, zeroCol, zeroRow, zeroCol - 1));
        // if not bottom row, swap below
        if (zeroRow < n - 1)
            neighbours.enqueue(exch(zeroRow, zeroCol, zeroRow + 1, zeroCol));
        // if right valid, swap right
        if (zeroCol < n - 1)
            neighbours.enqueue(exch(zeroRow, zeroCol, zeroRow, zeroCol + 1));
        
        return neighbours;
    }

    // helper function to swap tile
    // row and col are the indices for tile to be swapped
    // returns new Board with swapped tile
    private Board exch(int oldRow, int oldCol, int newRow, int newCol) {
        int[][] newTile = new int[n][n];
        
        // clone tiles
        for (int row = 0; row < n; row++)
            newTile[row] = Arrays.copyOfRange(tiles, row * n, row * n + n);

        int temp = newTile[oldRow][oldCol];
        newTile[oldRow][oldCol] = newTile[newRow][newCol];
        newTile[newRow][newCol] = temp;
        return new Board(newTile);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board newTile;
        if (tiles[0] != 0) {
            if (tiles[n + 1] != 0)
                newTile = exch(0, 0, 1, 1);
            else 
                newTile = exch(0, 0, 0, 1);
        }
        else {
            newTile = exch(0, 1, 1, 0);
        }
        return newTile;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();

        
        Board initial = new Board(tiles);
        System.out.println(initial);
        System.out.println("Hamming: " + initial.hamming());
        System.out.println("Manhattan: " + initial.manhattan());
        //System.out.println("Tile length: " + initial.tiles.length);
    }
}
