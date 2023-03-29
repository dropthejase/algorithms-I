import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {

    private LineSegment[] segments;
    private Point[] segmentPairs;
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
        segments = new LineSegment[points.length * points.length]; // for N x N grid cases
        segmentPairs = new Point[points.length * points.length];
        numSegments = 0;

        Point[] pointsSorted = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsSorted);

        // check duplicates
        for (int i = 1; i < pointsSorted.length; i++) {
            if (pointsSorted[i].compareTo(pointsSorted[i-1]) == 0)
                throw new IllegalArgumentException("No duplicates");
        }

        // MAIN LOOP
        for (int i = 0; i < pointsSorted.length - 1; i++) {
            Point p = pointsSorted[i];
            Point[] pArray = new Point[pointsSorted.length - 1 - i]; // capture points
            double[] pArraySlopes = new double[pointsSorted.length - 1 - i]; // capture actual slopes
            int idx = 0;

            // calculate slopes against point p
            for (int j = i + 1; j < pointsSorted.length; j++) {
                // if (i == j) continue;
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
                if (pArraySlopes[j] == comparatorSlope && j != pArraySlopes.length - 1) {
                    colIdx++;
                    collinear[colIdx] = pArray[j];
                    continue;
                }

                else {
                    // add the last collinear point if last point is end of pArraySlopes
                    if (pArraySlopes[j] == comparatorSlope) {
                        colIdx++;
                        collinear[colIdx] = pArray[j];
                    }
                    // else if we find 2 or more additional collinear points
                    // add line segment
                    if (colIdx >= 3) {
                        // System.out.println(Arrays.toString(collinear));
                        
                        // check duplicate segments
                        if (noDuplicateSegments(comparatorSlope, collinear[colIdx])) {
                            segments[numSegments] = new LineSegment(collinear[0], collinear[colIdx]);
                            segmentPairs[numSegments * 2] = collinear[0];
                            segmentPairs[numSegments * 2 + 1] = collinear[colIdx];
                            numSegments++;
                        }
                    }         
                }         
                }

                // change the comparator
                comparatorSlope = pArraySlopes[j];
                colIdx = 1;
                collinear[colIdx] = pArray[j];      
            }
        }
    }

    // check duplicate segments
    private boolean noDuplicateSegments(double comparatorSlope, Point q) {
        if (numSegments == 0) return true;
        for (int pair = 0; pair < numSegments * 2; pair += 2) {
            if ((segmentPairs[pair]).slopeTo(segmentPairs[pair + 1]) == comparatorSlope && segmentPairs[pair + 1].compareTo(q) == 0)
                return false;
        }
        return true;
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
        /*StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.line(0, 0, 32768, 0);
        StdDraw.line(0, 0, 0, 32768);
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLUE);

        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();*/

        // Create Fast Collinear Points
        Arrays.sort(points);
        FastCollinearPoints fcp = new FastCollinearPoints(points);
        for (LineSegment seg: fcp.segments()) {
            if (seg != null)
                System.out.println(seg);
        }
    }
}