import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private final SearchNode finalNode;
    private final int minMoves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        SearchNode initialNode = new SearchNode(initial, null, 0);
        MinPQ<SearchNode> pq = new MinPQ<>(new searchNodeOrderComparator());

        // add twin of initial node to test unsolvability
        Board initialTwin = initial.twin();
        SearchNode initialNodeTwin = new SearchNode(initialTwin, null, 0);
        MinPQ<SearchNode> pqTwin = new MinPQ<>(new searchNodeOrderComparator());

        // initial insertion and deletion
        pq.insert(initialNode);
        pqTwin.insert(initialNodeTwin);

        SearchNode poppedNode = pq.delMin();
        SearchNode poppedNodeTwin = pqTwin.delMin();

        // main loop
        while (!poppedNode.board.isGoal() && !poppedNodeTwin.board.isGoal()) {

            // main
            for (Board neighbor: poppedNode.board.neighbors()) {
                if (poppedNode.prev == null || !neighbor.equals(poppedNode.prev.board)) {
                    SearchNode newNode = new SearchNode(neighbor, poppedNode, poppedNode.moves + 1);
                    pq.insert(newNode);
                }
                else {
                    continue;
                }
            }
            // twin
            for (Board neighbor: poppedNodeTwin.board.neighbors()) {
                if (poppedNodeTwin.prev == null || !neighbor.equals(poppedNodeTwin.prev.board)) {
                    SearchNode newNodeTwin = new SearchNode(neighbor, poppedNodeTwin, poppedNodeTwin.moves + 1);
                    pqTwin.insert(newNodeTwin);
                }
                else {
                    continue;
                }
            }
            poppedNode = pq.delMin();
            poppedNodeTwin = pqTwin.delMin();
            //System.out.println("Priority: " + poppedNode.priority);
            //System.out.println(poppedNode.board);
        }
        if (poppedNodeTwin.board.isGoal()) {
            this.finalNode = poppedNodeTwin;
            this.minMoves = -1;
        }
        else {
            this.finalNode = poppedNode;
            this.minMoves = poppedNode.moves;
        }
    }

    private class SearchNode {
        Board board;
        SearchNode prev;
        int moves;
        int priority;

        private SearchNode(Board board, SearchNode prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
            this.priority = this.board.manhattan() + this.moves;
        }
    }

    private class searchNodeOrderComparator implements Comparator<SearchNode> {
        public int compare(SearchNode first, SearchNode second) {
            if (first.priority < second.priority) return -1;
            else if (first.priority > second.priority) return +1;
            else return 0;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return moves() != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() { 
        return minMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;

        Stack<Board> solutions = new Stack<>();
        SearchNode pointer = finalNode;
        while (pointer != null) {
            solutions.push(pointer.board);
            pointer = pointer.prev;
        }
        return solutions;
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
    
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
