import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.List;



class TreeNode {
    boolean dir = false;
    Point2D point;
    TreeNode left = null;
    TreeNode right = null;
    TreeNode parent = null;
    double[] limits = new double[4];
    
    public TreeNode(Point2D p) {
        this.point = p;
        if (this.parent != null)
            this.dir = !this.parent.dir;
    }   
}


public class KdTree {
    
   private int size;
   private TreeNode root;
       
   // construct an empty set of points 
   public         KdTree() {
       this.size = 0; 
       this.root = null;
   }
   
   // is the set empty? 
   public           boolean isEmpty() {                     
       return this.size == 0;
   }
   
    // number of points in the set 
   public               int size()  {
       return this.size;
   }
   // add the point to the set (if it is not already in the set)   
   public              void insert(Point2D p)  {
       this.size++;
       double[] limits = new double[4];
       limits[0] = 0;
       limits[1] = 1;
       limits[2] = 0;
       limits[3] = 1;
       this.root = put(null, this.root, p, true, false, limits);
   }
   private TreeNode put(TreeNode parent, TreeNode node, Point2D p, boolean LR, boolean dir, double[] lims) {
       double[] limits = lims;
       if (node==null) {
           node = new TreeNode(p);
           node.dir = dir;
           node.parent = parent;
           if (LR && parent != null)
               node.parent.right = node;
           else if (!LR && parent != null)
               node.parent.left = node;
           node.limits = lims;
           return node;
       }
       if (node.dir == false) {
           if (node.point.x() > p.x()) {
               limits[1] = node.point.x(); 
               this.put(node, node.left, p, false, !node.dir, limits);
           }
           else if (node.point.x() <= p.x()) {
               limits[0] = node.point.x(); 
               this.put(node, node.right, p, true, !node.dir, limits);
           }
       }
       else {
           if (node.point.y() > p.y()) {
               limits[3] = node.point.y(); 
               this.put(node, node.left, p, false, !node.dir, limits);
           }
           else if (node.point.y() <= p.y()) {
               limits[2] = node.point.y(); 
               this.put(node, node.right, p, true, !node.dir, limits);
           }
                   
       }
      return node;
   }
   
   // does the set contain point p? 
   public           boolean contains(Point2D p)  {
       return check(this.root, p, true, false); 
   }
   
   private boolean check(TreeNode node, Point2D p, boolean LR, boolean dir) {
       if (node==null) {
           return false;
       }
       if (node.point.x()==p.x() && node.point.y()==p.y()) {
           return true;
       }    
       if (node.dir == false) {
           if (node.point.x() > p.x())
               return this.check(node.left, p, false, !node.dir);                 
           else if (node.point.x() <= p.x())
               return this.check(node.right, p, true, !node.dir);
       }
       else {
           if (node.point.y() > p.y())
               return this.check(node.left, p, false, !node.dir);
           else if (node.point.y() <= p.y())
               return this.check(node.right, p, true, !node.dir);                 
       } 
       return false;
   }

   // draw all points to standard draw 
   public              void draw() {
       drawit(this.root);       
       StdDraw.show();
   }
   private void drawit(TreeNode node) {
       if (node==null) return;
       else if (node.dir == true) {
           StdDraw.setPenRadius(0.002);
           StdDraw.setPenColor(StdDraw.BLUE);
           if (node.parent == null)
               StdDraw.line(0, node.point.y(), 1, node.point.y());
           else {
               if (node == node.parent.right)
                    StdDraw.line(node.limits[1], node.point.y(), node.limits[0], node.point.y());
               else
                    StdDraw.line(node.limits[0], node.point.y(), node.limits[1], node.point.y());
           }
           StdDraw.setPenRadius(0.02);
           StdDraw.setPenColor(StdDraw.BLACK);
           StdDraw.point(node.point.x(), node.point.y());
           drawit(node.left);
           drawit(node.right);
       }
       else {
           StdDraw.setPenRadius(0.002);
           StdDraw.setPenColor(StdDraw.RED);
           if (node.parent == null)
               StdDraw.line(node.point.x(),0,node.point.x(),1);
           else {
               if (node == node.parent.right)
                   StdDraw.line(node.point.x(), node.limits[2], node.point.x(), node.limits[3]);
               else
                   StdDraw.line(node.point.x(), node.limits[2],node.point.x(), node.limits[3]);       
           }
           StdDraw.setPenRadius(0.02);
           StdDraw.setPenColor(StdDraw.BLACK);
           StdDraw.point(node.point.x(), node.point.y());
           drawit(node.left);
           drawit(node.right);
       }
       return;
   }
   
   // all points that are inside the rectangle     
   public Iterable<Point2D> range(RectHV rect) {
       List<Point2D> out = new ArrayList<Point2D>();
       addtolist(this.root, rect, out);
       return out;
   }
   
   private void addtolist(TreeNode node, RectHV rect, List<Point2D> out) {

       if (rect.contains(node.point)) {
          out.add(node.point);
       }
       
       if (node.right != null) {
           RectHV rightRect = new RectHV(node.right.limits[0], node.right.limits[2],
                                     node.right.limits[1], node.right.limits[3]);
           if (rect.intersects(rightRect)) 
           addtolist(node.right, rect, out);
       }
       if (node.left != null) {

        RectHV leftRect = new RectHV(node.left.limits[0], node.left.limits[2],
                                     node.left.limits[1], node.left.limits[3]);
        if (rect.intersects(leftRect)) 
           addtolist(node.left, rect, out);
       }
     
       return;
   }
   
    
  // a nearest neighbor in the set to point p; null if the set is empty 
  public           Point2D nearest(Point2D p)   {
      if (this.isEmpty()) return null;
      return findnearest(this.root, p, null); 
  }
  
  private Point2D findnearest(TreeNode searchNode, Point2D p, Point2D n) {
      Point2D near = n;
      if (near == null) 
          near = searchNode.point;
      else if (searchNode.point.distanceTo(p) < near.distanceTo(p))
          near = searchNode.point;
          
      if (searchNode.right != null) {
           RectHV rightRect = new RectHV(searchNode.right.limits[0], searchNode.right.limits[2],
                                     searchNode.right.limits[1], searchNode.right.limits[3]);
           if (rightRect.distanceTo(p) < near.distanceTo(p)) 
               near = findnearest(searchNode.right, p, near);
       }
       if (searchNode.left != null) {
           RectHV leftRect = new RectHV(searchNode.left.limits[0], searchNode.left.limits[2],
                                     searchNode.left.limits[1], searchNode.left.limits[3]);
           if (leftRect.distanceTo(p) < near.distanceTo(p)) 
               near = findnearest(searchNode.left, p, near);
       } 
       return near;
  }
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.enableDoubleBuffering();

        // initialize the two data structures with point from standard input
        KdTree tree = new KdTree();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
           Point2D p = new Point2D(x, y);
           tree.insert(p);   
        }
        System.out.println(tree.size());
        //tree.draw();
        
         //Point2D p = new Point2D(0.5, 0.4);
         //System.out.println(tree.contains(p));
        
    }
}