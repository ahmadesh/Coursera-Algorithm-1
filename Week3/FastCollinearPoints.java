import java.lang.*;
import java.util.*;
import edu.princeton.cs.algs4.*;


public class FastCollinearPoints {
    
    private List<LineSegment> lines = new ArrayList<LineSegment>();
    
    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
        if (points == null)
            throw new NullPointerException();
        
        Point[] pcopy = points.clone();
        
        for (int i = 0; i < points.length-3; i++) {
            Arrays.sort(pcopy);
            Arrays.sort(pcopy, pcopy[i].slopeOrder());
            
            for (int p = 0, first = 1, last = 2; last < pcopy.length; last++) {
                while (last < pcopy.length &&  pcopy[p].slopeTo(pcopy[first]) == pcopy[p].slopeTo(pcopy[last]) ) {
                    last++;
                }
                
                if ( (last - first) >= 3 && pcopy[p].compareTo(pcopy[first]) < 0 ) {
                    lines.add(new LineSegment(pcopy[p], pcopy[last-1]));                   
                }
                
             first = last;
                    
            }
            
        }
        
    }
    
    public int numberOfSegments() {      // the number of line segments
        return lines.size();
    }
        
    public LineSegment[] segments() {               // the line segments
        return lines.toArray(new LineSegment[lines.size()]);
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
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
    
}
        