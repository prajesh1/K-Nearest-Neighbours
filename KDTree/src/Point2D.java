public class Point2D implements Comparable<Point2D> {
  
   private final double x;    // x coordinate
   private final double y;    // y coordinate
   
   public Point2D(double x, double y)              // construct the point (x, y)
   {
       if (Double.isInfinite(x) || Double.isInfinite(y))
           throw new IllegalArgumentException("Coordinates must be finite");
       if (Double.isNaN(x) || Double.isNaN(y))
           throw new IllegalArgumentException("Coordinates cannot be NaN");
       if (x == 0.0) this.x = 0.0;  // convert -0.0 to +0.0
       else          this.x = x;
    
       if (y == 0.0) this.y = 0.0;  // convert -0.0 to +0.0
       else          this.y = y;
   }
   public  double x()                              // x-coordinate 
   {
     return this.x;
   }
   public  double y()                              // y-coordinate 
   {
     return this.y;
   }
   public  double distanceTo(Point2D that)         // Euclidean distance between two points
   {
     double dx = this.x - that.x;
     double dy = this.y - that.y;
     return dx*dx + dy*dy;
   }
   public  double distanceSquaredTo(Point2D that)  // square of Euclidean distance between two points
   {
     return distanceTo(that)*distanceTo(that);
   }
   public     int compareTo(Point2D that)          // for use in an ordered symbol table
   {
     if (this.y < that.y) return -1;
     if (this.y > that.y) return +1;
     if (this.x < that.x) return -1;
     if (this.x > that.x) return +1;
     return 0;
   }
   public boolean equals(Object that)              // does this point equal that object?
   {
     if (that == this) return true;
     if (that == null) return false;
     if (that.getClass() != this.getClass()) return false;
     Point2D P2dthat = (Point2D) that;
     return this.x == P2dthat.x && this.y == P2dthat.y;
   }
   public  void draw()                           // draw to standard draw 
   {
     System.out.println("x = "+x+" and y = "+y);
   }
   public  String toString()                       // string representation 
   {
     return "(" + x + ", " + y + ")";
   }
}