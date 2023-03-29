import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {

    private LineSegment[] segments;
    private int numSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        // check null argument
        if (points == null) {
            throw new IllegalArgumentException("Points array cannot be null");
        }

        // check null points
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("Points cannot be null");
        }

        // create sorted array
        segments = new LineSegment[points.length];
        numSegments = 0;

        Point[] pointsSorted = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsSorted);

        // check duplicates
        for (int i = 1; i < pointsSorted.length; i++) {
            if (pointsSorted[i].compareTo(pointsSorted[i-1]) == 0)
                throw new IllegalArgumentException("No duplicates");
        }

        /////// FOR EACH POINT P, IF SLOPE ORDER

        // MAIN LOOP
        for (int i = 0; i < pointsSorted.length - 1; i++) {
            Point p = pointsSorted[i];
            Point[] pArray = new Point[pointsSorted.length - 1 - i]; // capture points
            double[] pArraySlopes = new double[pointsSorted.length - 1 - i]; // capture actual slopes
            int idx = 0;

            // calculate slopes against point p
            for (int j = i + 1; j < pointsSorted.length; j++) {
                if (i == j) continue;
                pArray[idx] = pointsSorted[j];
                pArraySlopes[idx] = p.slopeTo(pointsSorted[j]);
                idx++;
            }

            // sort the arrays
            Arrays.sort(pArray, p.slopeOrder());
            Arrays.sort(pArraySlopes);

            // isolate collinear points only
            Point[] collinear = new Point[pointsSorted.length];

            double comparatorSlope = pArraySlopes[0]; // comparator

            collinear[0] = p;
            collinear[1] = pArray[0];
            int colIdx = 1;

            for (int j = 1; j < pArraySlopes.length; j++) {

                // if slope is the same, continue...
                if (pArraySlopes[j] == comparatorSlope) {
                    colIdx++;
                    collinear[colIdx] = pArray[j];
                    continue;
                }
                else {
                    // else if we find 2 or more additional collinear points
                    // add line segment and break out of loop
                    if (colIdx >= 3) {
                        Point[] tempCollinear = Arrays.copyOf(collinear, colIdx + 1);
                        // System.out.println("Temp: " + Arrays.toString(tempCollinear));
                        segments[numSegments++] = new LineSegment(tempCollinear[0], tempCollinear[tempCollinear.length - 1]);
                        break;
                    }
                    // else change the comparator
                    else {
                        comparatorSlope = pArraySlopes[j];
                        colIdx = 1;
                        collinear[colIdx] = pArray[j];               
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] tempSegments = Arrays.copyOf(segments, numSegments);
        return tempSegments;
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

        // Create Fast Collinear Points
        Arrays.sort(points);
        FastCollinearPoints fcp = new FastCollinearPoints(points);
        for (LineSegment seg: fcp.segments()) {
            if (seg != null)
                System.out.println(seg);
        }
    }
}