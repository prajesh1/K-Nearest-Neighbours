import java.util.Iterator;
import java.util.Stack;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    //private TreeSet<Node> balancedSearchTree;
    private int size = 0;
    private Stack<Point2D> iterableStack;
    private Node root;
    private Point2D closestPointDiscoveredTillNow;
    private double distanceToClosestPoint = Double.POSITIVE_INFINITY;
    private static class Node
    {
      private Point2D p;      // the point
      private RectHV rect;    // the axis-aligned rectangle corresponding to this node
      private Node lb;        // the left/bottom subtree
      private Node rt;        // the right/top subtree
      public Node(Point2D p, RectHV rect, Node lb, Node rt) {        
        this.p = p;
        this.rect = rect;
        this.lb = lb;
        this.rt = rt;
      }
        
    }
   public KdTree()                               // construct an empty set of points 
   {
     //balancedSearchTree = new TreeSet<Node>();
   }
   public boolean isEmpty()                      // is the set empty? 
   {
     return this.root==null;
   }
   public int size()                         // number of points in the set 
   {
     return this.size;
   }
   public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
      root = insertNode(p,root,null,null,true,false); 
      
   }
   private Node insertNode(Point2D p,Node node,Node fatherNode, RectHV fatherRect,boolean vertical, boolean leftChild) {
     double xMax = -1, xMin = -1, yMin = -1, yMax = -1, tcase =0;
     try{
    if(node==null)
    {
                 
                  if(fatherNode==null)
                  {
                    xMax = 1; xMin = 0; yMin = 0; yMax = 1; tcase=1;
                  }
                  else if(vertical==false&&leftChild==true)
                  {
                  //  xMax = fatherNode.p.x(); xMin = fatherRect.xmin(); yMin = fatherRect.ymin(); yMax = fatherRect.ymax();
                    xMax = fatherNode.p.x(); xMin = fatherRect.xmin(); yMin = fatherRect.ymin(); yMax = fatherRect.ymax();
                    tcase=2;
                  }
                  else if(vertical==false&&leftChild==false)
                  {
                    xMax = fatherRect.xmax(); xMin = fatherNode.p.x(); yMin = fatherRect.ymin(); yMax = fatherRect.ymax();
                    tcase=3;
                  }
                  else if(vertical==true&&leftChild==true)
                  {
                    tcase =4;
                    xMax = fatherRect.xmax(); xMin = fatherRect.xmin(); yMax = fatherNode.p.y(); yMin = fatherRect.ymin();
                  }
                  else if(vertical==true&&leftChild==false)
                  {
                    tcase =5;
                    xMax = fatherRect.xmax(); xMin = fatherRect.xmin(); yMax = fatherRect.ymax(); yMin = fatherNode.p.y();
                  }
                  else
                  {
                    System.out.println("case missed");
                  }
                  RectHV rect = new RectHV(xMin, yMin, xMax, yMax);
                  if(yMax==-1)
                    System.out.println("gone");
                  Node newNode = new Node(p,rect,null,null);
                  this.size++;
                  return newNode;
    }
   
    else if(vertical==true&&node.p.x()>p.x())
    {
     // System.out.println("vlest");
      node.lb = insertNode( p, node.lb,node, node.rect, !vertical, true); 
      
    }
    else if(vertical==true&&node.p.x()<p.x())
    {
    //  System.out.println("vr");
      node.rt = insertNode( p, node.rt,node, node.rect, !vertical, false);  
    }
    
    else if(vertical==false&&node.p.y()>p.y())
    {
    //  System.out.println("hlest");
      node.lb = insertNode( p, node.lb,node, node.rect, !vertical, true); 
      
    }
    else if(vertical==false&&node.p.y()<p.y())
    {
  //    System.out.println("hr");
      node.rt = insertNode( p, node.rt,node, node.rect, !vertical, false); 
     
    }
    else node.rt = insertNode( p, node.rt,node, node.rect, !vertical, false); 
    
    //if same point sent again dont do anything
     } catch (Exception e)
     {
       System.out.println("xmax="+xMax+" "+xMin+" "+yMax+" "+yMin+" "+tcase);
     }
    return node;
  }
   public boolean contains(Point2D p)            // does the set contain point p? 
   {
     Node node = this.root;
     boolean vertical = true;
     while(node!=null)
     {
       if(vertical==true&&node.p.x()>p.x())
         node = node.lb;
       else if(vertical==true&&node.p.x()<p.x())
         node = node.rt;
       else if(vertical==false&&node.p.y()>p.y())
         node = node.lb;
       else if(vertical==false&&node.p.y()<p.y())
         node = node.rt;
       else if( p.equals(node)) return true;
       else node = node.rt;
       vertical = !vertical;
     }
     return false;
   }
   public void draw()                         // draw all points to standard draw 
   {
          
     recursiveDraw(this.root,true);
   }
   private void recursiveDraw(Node newNode,boolean color) {
     if(color)
       StdDraw.setPenColor(StdDraw.BLUE);
       else
         StdDraw.setPenColor(StdDraw.RED);
    if(newNode!=null)
    {
      StdDraw.setPenRadius(.02);
      
      newNode.rect.draw();
      StdDraw.setPenColor(StdDraw.BLACK);
      newNode.p.draw();
      //System.out.println(" "+newNode.rect.xmax()+ " "+newNode.rect.xmin()+" "+newNode.rect.ymax()+" "+newNode.rect.ymin()+" sz"+this.size);
      
      
      this.recursiveDraw(newNode.lb,!color);
      this.recursiveDraw(newNode.rt,!color);
    }
  }
  public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
   {
     if(rect==null)
       throw new java.lang.NullPointerException();
     iterableStack = new Stack<Point2D>();
     
     rangeRecursiveSearch(rect,root);     
     return this.iterableStack;
   }
   private void rangeRecursiveSearch(RectHV rect, Node node) 
   {
     if(node!=null&&rect.intersects(node.rect)) // Does given rectangle intersect with rectangle of node
     {
       if(rect.contains(node.p))this.iterableStack.add(node.p);
       this.rangeRecursiveSearch(rect, node.lb);
       this.rangeRecursiveSearch(rect, node.rt);  
     }
   }
  public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
  {
    distanceToClosestPoint = Double.POSITIVE_INFINITY;
    if(this.isEmpty())
      return null;
    this.closestPointDiscoveredTillNow = root.p;
    this.distanceToClosestPoint = p.distanceSquaredTo(this.closestPointDiscoveredTillNow);
    recursiveNearestNeighbour(p,root);
    return this.closestPointDiscoveredTillNow;
  }

   private void recursiveNearestNeighbour(Point2D p, Node node)
   {
     if(node!=null&&node.p.distanceSquaredTo(p)<this.distanceToClosestPoint)
     {
       this.closestPointDiscoveredTillNow = node.p;
       this.distanceToClosestPoint = node.p.distanceSquaredTo(p);
     }
     if(node!=null&&node.rect.distanceTo(p)<this.distanceToClosestPoint) // Is there any point in current rectangle 
     {                                                     //nearer than current closestPoint
       this.recursiveNearestNeighbour(p, node.lb);
       this.recursiveNearestNeighbour(p, node.rt);
     }
   } 
  public static void main(String[] args)                  // unit testing of the methods (optional) 
  {
    
  }
}