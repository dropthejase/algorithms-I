import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {

    private LineSegment[] segments;
    private int numSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        segments = new LineSegment[points.length];

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Point[] pArray = new Point[points.length - 1]; // capture sorted points
            double[] pArraySlopes = new double[points.length - 1]; // capture actual slopes
            int idx = 0;

            // main loop
            for (int j = 0; j < points.length; j++) {
                // if i == j, skip loop
                if (i == j) continue;

                pArray[idx] = points[j];
                pArraySlopes[idx] = p.slopeTo(points[j]);
                idx++;
            }

            // sort the array
            Arrays.sort(pArray, p.slopeOrder());
            Arrays.sort(pArraySlopes);
            // System.out.println("p = " + p);
            // System.out.println("pArray: " + Arrays.toString(pArray));
            // System.out.println("pArraySlopes: " + Arrays.toString(pArraySlopes));

            // isolate collinear points only
            Point[] collinear = new Point[points.length];
            int colIdx = 1;

            // comparator slope
            double slope = pArraySlopes[0];

            collinear[0] = p;
            collinear[1] = pArray[0];
            for (int j = 1; j < points.length - 1; j++) {

                // if slope is the same, continue...
                if (pArraySlopes[j] == slope) {
                    colIdx++;
                    collinear[colIdx] = pArray[j];
                    continue;
                }
                // else if we find 2 or more slopes == slope, break out of loop, otherwise reset colIdx
                else {
                    if (colIdx >= 3) {
                        break;
                    }
                    else {
                        slope = pArraySlopes[j];
                        colIdx = 1;
                        collinear[colIdx] = pArray[j];               
                    }
                }
            }
            // System.out.println("Collinear only: " + Arrays.toString(collinear));

            // add the line segments
            Point[] tempCollinear = Arrays.copyOf(collinear, colIdx + 1);
            Arrays.sort(tempCollinear);
            System.out.println("Temp: " + Arrays.toString(tempCollinear));

            // System.out.println("Temp: " + Arrays.toString(tempCollinear));
            // System.out.println("-------------------------------------------------------------------------------------------");
        }

        // System.out.println(Arrays.toString(segments));
    }

    // the number of line segments
    public int numberOfSegments() {
        return numSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
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
        StdDraw.line(0, 0, 32768, 0);
        StdDraw.line(0, 0, 0, 32768);
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLUE);

        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // Create Brute Collinear Points
        Arrays.sort(points);
        FastCollinearPoints fcp = new FastCollinearPoints(points);
    }
}
