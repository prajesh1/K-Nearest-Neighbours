import java.util.Iterator;
import java.util.Stack;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
/*
 * Brute-force implementation.
 * Using Balanced Search Tree= java.util.TreeSet and iterating using
 * balancedSearchTree.iterator() and checking if point is within rectangle for
 * each point in bst and same approach to find nearest point in bst
 */
public class PointSET {
  private TreeSet<Point2D> balancedSearchTree;
   public         PointSET()                               // construct an empty set of points
   {
     balancedSearchTree = new TreeSet<Point2D>();
   }
   public boolean isEmpty()                      // is the set empty? 
   {
     return this.balancedSearchTree.isEmpty();
   }
   public int size()                         // number of points in the set
   {
     return this.balancedSearchTree.size();
   }
   public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
     if(p==null)
       throw new java.lang.NullPointerException();
     this.balancedSearchTree.add(p);
   }
   public boolean contains(Point2D p)            // does the set contain point p?
   {
     if(p==null)
       throw new java.lang.NullPointerException();
     return this.balancedSearchTree.contains(p);
   }
   public void draw()                         // draw all points to standard draw
   {
     Iterator<Point2D> iter = balancedSearchTree.iterator();
     while(iter.hasNext())
       iter.next().draw();     
   }
    
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
   {
     if(rect==null)
       throw new java.lang.NullPointerException();
     Stack<Point2D> iterableStack = new Stack<Point2D>();
     Iterator<Point2D> iter = balancedSearchTree.iterator();    
       
      while(iter.hasNext())
     {
       Point2D point = iter.next();
       if(isPointWithinRange(point,rect))
         iterableStack.push(point);
     }
     return iterableStack;
   }
   private boolean isPointWithinRange(Point2D p, RectHV rect) 
   {
     if(p==null||rect==null)
       throw new java.lang.NullPointerException();
     
     if(p.x()>rect.xmax()||p.x()<rect.xmin()) return false;
     if(p.y()>rect.ymax()||p.y()<rect.ymin()) return false;
    return true;
   }
  public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
  {
    if(p==null)
      throw new java.lang.NullPointerException();
    
    Point2D nearestPoint=null;
    double distanceFromNearest= Double.POSITIVE_INFINITY;
    Iterator<Point2D> iter = balancedSearchTree.iterator();    
    
    while(iter.hasNext())
   {
      Point2D point = iter.next();
      double distanceFromPoint = point.distanceTo(p);
      if(distanceFromPoint<distanceFromNearest)
        {
          distanceFromPoint=distanceFromNearest;
          nearestPoint = point;
        }
    }
    return nearestPoint;
  }

  public static void main(String[] args)                  // unit testing of the methods (optional)
   {
     
   }
}