import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

public class Board {

    private final int n;
    private final int[][] tiles;
    private final int[][] goal;
    
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles[0].length;
        this.tiles = deepCopy(tiles);

        // create goal
        goal = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row == n - 1 && col == n - 1) goal[row][col] = 0;
                else goal[row][col] = 1 + (row * n) + col;
            }
        }
    }

    // private deepCopy
    private int[][] deepCopy(int[][] oldTile) {
        int[][] newTile = new int[n][n];
        for (int row = 0; row < n; row++) {
            newTile[row] = oldTile[row].clone();
        }
        return newTile;
    }

    // string representation of this board
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
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
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] == 0)
                    continue;
                if (tiles[row][col] != goal[row][col])
                    count++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int count = 0;
        int correctRow;
        int correctCol;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] != 0) {
                    correctRow = Math.floorDiv(tiles[row][col] - 1, n);
                    correctCol = tiles[row][col] - (correctRow * n) - 1;
                    count += Math.abs(correctRow - row) + Math.abs(correctCol - col);
                }
            }
        }
        return count;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (manhattan() == 0) return true;
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

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] == 0) {
                    zeroLocation[0] = row;
                    zeroLocation[1] = col;
                }
            }
        }
        return zeroLocation;
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
        int[][] newTile = deepCopy(tiles);

        int temp = newTile[oldRow][oldCol];
        newTile[oldRow][oldCol] = newTile[newRow][newCol];
        newTile[newRow][newCol] = temp;
        return new Board(newTile);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board newTile;

        if (tiles[0][0] != 0) {
            if (tiles[1][1] != 0)
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
        System.out.println("Initial: \n" + initial);
        
        /*
        System.out.println("Goal: \n" + Arrays.deepToString(initial.goal));
        System.out.println("Hamming: " + initial.hamming());
        System.out.println("Manhattan: " + initial.manhattan()); 

        // check equals function - true condition
        Board initial2 = new Board(tiles);
        System.out.println("initial == initial2? " + initial.equals(initial2));

        // check equals function - false condition + check isGoal
        int[][] tiles2 = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (i == n - 1 && j == n - 1) tiles2[i][j] = 0;
                else tiles2[i][j] = 1 + (i * n) + j;

        Board goal = new Board(tiles2);
        System.out.println("is Goal? " + initial.equals(goal));
        System.out.println("is Goal? " + goal.isGoal());
        */

        // check neighbors
        for (Board i: initial.neighbors()) {
            System.out.println(i);
        }
        

        /*for (int i = 0; i < 10; i++) {
            System.out.println(initial.twin());
        }*/
    }
}
