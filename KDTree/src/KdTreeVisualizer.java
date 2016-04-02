/******************************************************************************
 *  Compilation:  javac KdTreeVisualizer.java
 *  Execution:    java KdTreeVisualizer
 *  Dependencies: KdTree.java
 *
 *  Add the points that the user clicks in the standard draw window
 *  to a kd-tree and draw the resulting kd-tree.
 *
 ******************************************************************************/

import java.util.Random;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTreeVisualizer {

    public static void main(String[] args) {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.show(0);
        KdTree kdtree = new KdTree();
     //  PointSET kdtree = new PointSET();
        int j= 100;
        while (true) {
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          if (StdDraw.mousePressed()) {
               double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
              Random r = new Random();
              //  double x = r.nextDouble();
                //double y = r.nextDouble();
               //StdOut.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)&&!kdtree.contains(p)) {
                    //StdOut.printf("%8.6f %8.6f\n", x, y);
                    kdtree.insert(p);
                    System.out.println("called insert"+kdtree.size());
                    StdDraw.clear();
                    kdtree.draw();
                }
            }
          else{
            j=10;//System.out.println("failed");
          }
            StdDraw.show(50);
        }

    }
}
