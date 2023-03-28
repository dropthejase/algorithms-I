import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    LineSegment[] segments;
    int numSegments;


    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        segments = new LineSegment[points.length];

        for (int p = 0; p < points.length; p++) {
            int tempCount = 0;

            for (int q = 0; q < points.length; q++) {
                if (points[p].compareTo(points[q]) == -1) tempCount++; else tempCount = 0;
                if ()

                for (int r = 0; r < points.length; r++) {
                    if (points[q].compareTo(points[r]) == -1) tempCount++; else tempCount = 0;

                    for (int s = 0; s < points.length; s++) {
                        if (points[r].compareTo(points[s]) == -1) tempCount++; else tempCount = 0;

                        double pq = points[p].slopeTo(points[q]);
                        double pr = points[p].slopeTo(points[r]);
                        double ps = points[p].slopeTo(points[s]);

                        if (pq == pr && pr == ps && pq == ps) {
                            segments[p] = new LineSegment(points[p], points[s]);
                            System.out.println(segments[p]);
                            numSegments++;
                        }
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
        System.out.println(bcp.numberOfSegments());
    }
}
