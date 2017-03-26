import java.lang.*;
import java.util.*;
import edu.princeton.cs.algs4.*;

public class Solver {
     
    private MinPQ<Node> pq = new MinPQ<Node>();
    private MinPQ<Node> pq_twin = new MinPQ<Node>();
    private int twin = 0; 
    private Node frontNode;
    private Node frontNode_twin;
    
    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm
        
        Node initialNode = new Node(initial, null);       
        pq.insert(initialNode);
        
        Node initialNode_twin = new Node(initial.twin(), null);       
        pq_twin.insert(initialNode_twin);
        
        if (initialNode.nodeBoard.isGoal()) {
            frontNode = initialNode;
            return;
        }

        
        while (true) {
            frontNode = pq.delMin();
            //if (frontNode.nodeBoard.isGoal()) break;
            for (Board item : frontNode.nodeBoard.neighbors()) {
                Node childNode = new Node(item, frontNode);
                if (item.isGoal()) {
                    frontNode = childNode;
                    return;
                }
                if (!childNode.equals(frontNode.parentNode)) {
                    pq.insert(childNode);
                }                                
            }
            
            frontNode_twin = pq_twin.delMin();
            for (Board item : frontNode_twin.nodeBoard.neighbors()) {
                Node childNode_twin = new Node(item, frontNode_twin);
                if (item.isGoal()) {
                    twin = 1;
                    return;
                }
                if (!childNode_twin.equals(frontNode_twin.parentNode)) {
                    pq_twin.insert(childNode_twin);
                }                                
            }
            
        }
        
    }    
        
    public boolean isSolvable()  { return twin == 0; }// is the initial board solvable?
    
    public int moves()   {                  // min number of moves to solve initial board; -1 if unsolvable
        if (!this.isSolvable()) return -1;
        int moves = 0;
        Node node = frontNode; 
        while (node != null) {
            node = node.parentNode;
            moves += 1;
        }
        return moves - 1;
    }
    
    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
         if (twin == 1) return null;
         return new solIteratable();
    }
    
    private class solIteratable implements Iterable<Board> {
        public Iterator<Board> iterator() {
            Node node = frontNode;
            List<Board> solutionList = new ArrayList<Board>(); 
            while (node != null) {
                solutionList.add(0,node.nodeBoard);
                node = node.parentNode;
            }            
        return solutionList.iterator(); 
        }
    }
      

    private class Node implements Comparable<Node>
    {   
        public final Board nodeBoard;
        private final Node parentNode;
        
        public Node(Board thisBoard, Node parentnode) {
            nodeBoard = thisBoard;
            parentNode = parentnode;
        }
        
        public int compareTo(Node that) {
            return this.nodeBoard.manhattan() - that.nodeBoard.manhattan();
        }
        
        public boolean equals(Node that) {
            if (that == null) return false;
            return this.nodeBoard.equals(that.nodeBoard);
        }
         
    }
    
    public static void main(String[] args) { // unit tests (not graded)
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            //for (Board board : solver.solution())
            //    StdOut.println(board);
    }
    }
}