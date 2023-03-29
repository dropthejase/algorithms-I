import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;


public class BruteCollinearPoints {

    private LineSegment[] segments;
    private int numSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

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

        for (int p = 0; p < pointsSorted.length; p++) {

            for (int q = p + 1; q < pointsSorted.length; q++) {

                for (int r = q + 1; r < pointsSorted.length; r++) {
                    
                    for (int s = r + 1; s < pointsSorted.length; s++) {

                        double pq = pointsSorted[p].slopeTo(pointsSorted[q]);
                        double pr = pointsSorted[p].slopeTo(pointsSorted[r]);
                        double ps = pointsSorted[p].slopeTo(pointsSorted[s]);

                        if (pq == pr && pr == ps && pq == ps) {                       
                            segments[numSegments++] = new LineSegment(pointsSorted[p], pointsSorted[s]);              
                        }
                    }
                }
            }
        }
        segments = Arrays.copyOf(segments, numSegments);
        // System.out.println(Arrays.toString(segments));
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

        // Create Brute Collinear Points
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        for (LineSegment seg: bcp.segments()) {
            if (seg != null)
                System.out.println(seg);
        }
    }
}
