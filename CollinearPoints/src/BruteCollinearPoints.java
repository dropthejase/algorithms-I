import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;


public class BruteCollinearPoints {

    private LineSegment[] segments;
    private int numSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        segments = new LineSegment[points.length];
        Point[] pointsSorted = points;
        Arrays.sort(pointsSorted);

        for (int p = 0; p < pointsSorted.length; p++) {

            for (int q = 0; q < pointsSorted.length; q++) {

                for (int r = 0; r < pointsSorted.length; r++) {
                    
                    for (int s = 0; s < pointsSorted.length; s++) {

                        double pq = pointsSorted[p].slopeTo(pointsSorted[q]);
                        double pr = pointsSorted[p].slopeTo(pointsSorted[r]);
                        double ps = pointsSorted[p].slopeTo(pointsSorted[s]);

                        if (pq == pr && pr == ps && pq == ps) {
                            if (pointsSorted[p].compareTo(pointsSorted[q]) == -1 && pointsSorted[q].compareTo(pointsSorted[r]) == -1 && pointsSorted[r].compareTo(pointsSorted[s]) == -1) {
                                segments[numSegments] = new LineSegment(pointsSorted[p], pointsSorted[s]);
                                System.out.println(segments[numSegments]);
                                numSegments++;
                            }
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
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
    }
}
