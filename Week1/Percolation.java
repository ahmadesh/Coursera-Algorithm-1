import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private WeightedQuickUnionUF wQUF;
    private boolean[][] grid;
    private int N; 
    
    // create n-by-n grid, with all sites //blocked
    public Percolation(int n) { 
        if (n <= 0) throw new java.lang.IllegalArgumentException("size is not acceptable");
        grid = new boolean[n][n];
        wQUF = new WeightedQuickUnionUF(n*n+2);
        N = n;
    } 
    
   // open site (row i, column j) if it //is not open already
    public void open(int i, int j) {
        if (i <= 0 || i > N || j <= 0 || j > N) 
            throw new IndexOutOfBoundsException("row index i out of bounds");
        
        grid[i-1][j-1] = true;      
        
        if (j != N && isOpen(i, j+1)) wQUF.union(oneDCoord(i, j),oneDCoord(i, j+1));
        if (j != 1 && isOpen(i, j-1)) wQUF.union(oneDCoord(i, j),oneDCoord(i, j-1));
        if (i != N && isOpen(i+1, j)) wQUF.union(oneDCoord(i, j),oneDCoord(i+1, j));
        if (i != 1 && isOpen(i-1, j)) wQUF.union(oneDCoord(i, j),oneDCoord(i-1, j));
        
        if (i == 1) wQUF.union(oneDCoord(i, j), N*N);
        if (i == N) wQUF.union(oneDCoord(i, j), N*N+1);        
    }  
    
     // is site (row i, column j) open?
    public boolean isOpen(int i, int j) { 
        if (i <= 0 || i > N || j <= 0 || j > N) 
            throw new IndexOutOfBoundsException("row index i out of bounds");      
        return grid[i-1][j-1];
    }    
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {  
        if (i <= 0 || i > N || j <= 0 || j > N) 
            throw new IndexOutOfBoundsException("row index i out of bounds");
        return wQUF.connected(oneDCoord(i, j), N*N);
    }   
    
    // does the system percolate? 
    public boolean percolates() {  
        return wQUF.connected(N*N, N*N+1);
    }             
    
    private int oneDCoord(int i, int j) {  
        return (i-1)*N+(j-1);
    }

    // test client (optional)
    public static void main(String[] args) {  
        
    
    }  
}