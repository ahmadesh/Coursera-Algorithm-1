import java.lang.*;
import java.util.*;
import edu.princeton.cs.algs4.*;

public class BruteCollinearPoints {
    
    private List<LineSegment> lines = new ArrayList<LineSegment>();
    private List<Point> lasts = new ArrayList<Point>();
    private List<Point> firsts = new ArrayList<Point>();
   
    
    public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
        if (points.length == 0) throw new java.lang.NullPointerException();
        
        Point[] linPoints = new Point[4];
        for (int i = 0; i < points.length; i++)   
            for (int j = i+1; j < points.length; j++) 
                for (int k = j+1; k < points.length; k++) 
                    for (int l = k+1; l < points.length; l++) {
                         if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) && points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])) {
                            linPoints[0] = points[i];
                            linPoints[1] = points[j];
                            linPoints[2] = points[k];
                            linPoints[3] = points[l];
                        
                            Arrays.sort(linPoints);
                            double thisSlope = points[i].slopeTo(points[j]);
                            if (!(lasts.contains(linPoints[3]) && firsts.contains(linPoints[0]))) {
                                lines.add(new LineSegment(linPoints[0],linPoints[3]));
                                lasts.add(linPoints[3]);
                                firsts.add(linPoints[0]); 
                                
                            }
                         }
                   }
    }      
                  
       
    public int numberOfSegments() {        // the number of line segments
        return lines.size();}
    
    public LineSegment[] segments(){               // the line segments
       LineSegment[] out = new LineSegment[lines.size()];
       int i = 0;
       for(LineSegment ls : lines){
           out[i] = lines.get(i);
           i++;
       }
       return out;
    }
    
 public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
 
}