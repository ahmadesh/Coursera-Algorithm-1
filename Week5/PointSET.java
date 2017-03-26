import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.In;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.List;



public class PointSET {
   private TreeSet<Point2D> setPoints; 
       
   // construct an empty set of points 
   public         PointSET() {
       setPoints = new TreeSet<Point2D>();    
   }
   
   // is the set empty? 
   public           boolean isEmpty() {                     
       return setPoints.size() == 0;
   }
   
    // number of points in the set 
   public               int size()  {
       return setPoints.size();
   }
   // add the point to the set (if it is not already in the set)   
   public              void insert(Point2D p)  {
       if (!setPoints.contains(p)) {
           setPoints.add(p);
       }
   }
   
   // does the set contain point p? 
   public           boolean contains(Point2D p)  {
       return setPoints.contains(p);
   }
   
   // draw all points to standard draw 
   public              void draw() {
       StdDraw.setPenRadius(0.01);
       StdDraw.setPenColor(StdDraw.BLACK);
        for (Point2D drawPoint : setPoints) {
           StdDraw.point(drawPoint.x(), drawPoint.y());
        }
       StdDraw.show();
   }
   
   // all points that are inside the rectangle     
   public Iterable<Point2D> range(RectHV rect) {
       List<Point2D> inside = new ArrayList<Point2D>();
       for (Point2D inpoint : setPoints) {
           if (rect.contains(inpoint))
               inside.add(inpoint);
        } 
       return inside;
   }
   
   // a nearest neighbor in the set to point p; null if the set is empty 
   public           Point2D nearest(Point2D p)   {
       if (this.isEmpty()) return null;
       Point2D nearest = setPoints.iterator().next();
       double minD  = Math.sqrt(Math.pow(p.x()-nearest.x(),2)+Math.pow(p.y()-nearest.y(),2));
       double curD;
       for (Point2D curPoint : setPoints) {
           curD = Math.sqrt(Math.pow(p.x()-curPoint.x(),2)+Math.pow(p.y()-curPoint.y(),2));
           if (curD<minD) {
               minD = curD;
               nearest= curPoint;
           }
        } 
       return nearest;   
   }
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.enableDoubleBuffering();

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        //KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            //kdtree.insert(p);
            brute.insert(p);
        }

        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            brute.nearest(query).draw();
            StdDraw.setPenRadius(0.02);

            // draw in blue the nearest neighbor (using kd-tree algorithm)
            StdDraw.setPenColor(StdDraw.BLUE);
            //kdtree.nearest(query).draw();
            StdDraw.show();
            StdDraw.pause(40);
        }
    }
}