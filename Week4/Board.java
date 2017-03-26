import java.lang.*;
import java.util.*;
import edu.princeton.cs.algs4.*;

public class Board {
    private final int[][] config;
    private int[][] goal;
    
    public Board(int[][] blocks) {         // construct a board from an n-by-n array of blocks
        config = new int[blocks.length][blocks.length];
        for (int i = 0; i < config.length; i++) {
            for (int j = 0; j < config.length; j++) {
                config[i][j] = blocks[i][j];
            }
        }
        goal = new int[blocks.length][blocks.length];
        for (int i = 0; i < config.length; i++) {
            for (int j = 0; j < config.length; j++) {
                goal[i][j] = i*config.length + j + 1;
            }
        }
        goal[config.length - 1][config.length - 1] = 0;
        
    }
                                           // (where blocks[i][j] = block in row i, column j)
    public int dimension() {                 // board dimension n
        return config.length;
    }
    public int hamming() {                  // number of blocks out of place
        int hamming = 0;
        for (int i = 0; i < config.length; i++) {
            for (int j = 0; j < config.length; j++) {
                if (config[i][j] == 0) continue;
                if (config[i][j] != goal[i][j]) hamming++;
            }
        }
        return hamming;
    }
    
    public int manhattan()  {               // sum of Manhattan distances between blocks and goal
        int manhattan = 0;
        for (int i = 0; i < config.length; i++) {
            for (int j = 0; j < config.length; j++) {
                if (config[i][j] == 0) continue;
                manhattan += Math.abs((config[i][j] - 1)/ config.length - i) + Math.abs( (config[i][j]-1) % config.length - j);
            }
        }
        return manhattan;
    }
        
    public boolean isGoal()  {              // is this board the goal board?
        return this.hamming() == 0;
    }
    public Board twin() {                   // a board that is obtained by exchanging any pair of blocks
        
        int[][] twinConfig = new int[config.length][config.length];
        for (int i = 0; i < config.length; i++) {
            for (int j = 0; j < config.length; j++) {
                twinConfig[i][j] = config[i][j];
            }
        }        
        
        int ii1 = -1;
        int jj1 = -1;
        int ii2 = -1;
        int jj2 = -1;
        int num1;
        int num2;
        
        Random rand = new Random();
        while (ii1 == -1 || ii2 == -1) {
            num1 = rand.nextInt(twinConfig.length);
            num2 = rand.nextInt(twinConfig.length);
            
            if (twinConfig[num1][num2] != 0 && ii1 == -1) {
                ii1 = num1;
                jj1 = num2;
            }     
            
            if (twinConfig[num1][num2] != 0  && ii1 != -1) {
                if (twinConfig[num1][num2] != twinConfig[ii1][jj1]) {
                   ii2 = num1;
                   jj2 = num2; 
                }
            }
        }
        
        int temp = twinConfig[ii1][jj1];
        twinConfig[ii1][jj1] = twinConfig[ii2][jj2];
        twinConfig[ii2][jj2] = temp;
       
        return new Board(twinConfig);
    }
    
    public boolean equals(Object y) {       // does this board equal y?
        if (y == null) return false;
        if (!y.getClass().equals(this.getClass())) return false;
        Board that = (Board) y;
        if (this. dimension() !=  that.dimension()) return false;
        for (int i = 0; i < config.length; i++) {
            for (int j = 0; j < config.length; j++) {
                if (this.config[i][j] != that.config[i][j]) return false;
            }
        }
        return true;       
    }
    
    public Iterable<Board> neighbors() {    // all neighboring boards
        return new neighborIteratable();
    }
    
    private class neighborIteratable implements Iterable<Board> {
        public Iterator<Board> iterator() {
            List<Board> neighbers = new ArrayList<Board>();
            
            for (int i = 0; i < config.length; i++) {
                for (int j = 0; j < config.length; j++) {
                    if (config[i][j] == 0) {                       
                        if (i > 0) {
                          int[][] neighberConfig = new int[config.length][config.length]; 
                          for (int k = 0; k < config.length; k++) {
                              for (int l = 0; l < config.length; l++) {
                                  neighberConfig[k][l] = config[k][l];
                              }
                          }                          
                          neighberConfig[i][j] = neighberConfig[i-1][j];
                          neighberConfig[i-1][j] = 0;
                          neighbers.add(new Board(neighberConfig));                          
                        }
                        if (i < config.length - 1) {
                          int[][] neighberConfig = new int[config.length][config.length]; 
                          for (int k = 0; k < config.length; k++) {
                              for (int l = 0; l < config.length; l++) {
                                  neighberConfig[k][l] = config[k][l];
                              }
                          }                          
                          neighberConfig[i][j] = neighberConfig[i+1][j];
                          neighberConfig[i+1][j] = 0;
                          neighbers.add(new Board(neighberConfig));                      
                       }
                       if (j > 0) {
                          int[][] neighberConfig = new int[config.length][config.length]; 
                          for (int k = 0; k < config.length; k++) {
                              for (int l = 0; l < config.length; l++) {
                                  neighberConfig[k][l] = config[k][l];
                              }
                          }                          
                          neighberConfig[i][j] = neighberConfig[i][j-1];
                          neighberConfig[i][j-1] = 0;
                          neighbers.add(new Board(neighberConfig));                          
                       }
                       if (j < config.length - 1) {
                          int[][] neighberConfig = new int[config.length][config.length]; 
                          for (int k = 0; k < config.length; k++) {
                              for (int l = 0; l < config.length; l++) {
                                  neighberConfig[k][l] = config[k][l];
                              }
                          }                          
                          neighberConfig[i][j] = neighberConfig[i][j+1];
                          neighberConfig[i][j+1] = 0;
                          neighbers.add(new Board(neighberConfig));                      
                       }                      
                                          
                    }
                }
            }
                       
           return neighbers.iterator();
        }      
    }
      
    public String toString() {              // string representation of this board (in the output format specified below)
       String out = Integer.toString(config.length); 
       for (int i = 0; i < config.length; i++) {
            out = out.concat("\n");
            for (int j = 0; j < config.length; j++) {
                out = out.concat(Integer.toString(config[i][j]));
                out = out.concat(" ");
            }
        }
       return out;
    }

    public static void main(String[] args) { // unit tests (not graded)
        In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);
    //for (Board a : initial.neighbors()) 
    //      System.out.println(a);
     
    //int[][] blocksclone = blocks.clone();        

    }
}
