import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        // equal
        if (this.x == that.x && this.y == that.y)
            return Double.NEGATIVE_INFINITY;
        // horizontal
        if (this.y == that.y)
            return +0.0;
        // vertical
        if (this.x == that.x)
            return Double.POSITIVE_INFINITY;

        return (double) (that.y - this.y) / (that.x - this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        // that point is greater
        if (that.y > this.y || (that.y == this.y && that.x > this.x))
            return -1;
        // that point is equal
        else if (that.y == this.y && that.x == this.x)
            return 0;
        // that point is lesser
        else
            return 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new slopeOrderComparator();
    }

    private class slopeOrderComparator implements Comparator<Point> {
        public int compare(Point first, Point second) {
            if (slopeTo(first) < slopeTo(second)) return -1;
            if (slopeTo(first) > slopeTo(second)) return +1;
            return 0;
        }
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */

    public static void main(String[] args) {
        Point point0 = new Point(0, 0);
        Point point1 = new Point(100, 200);
        Point point2 = new Point(100, 0);

        System.out.println("compareTo (point0 -> point 1): " + point0.compareTo(point1));
        System.out.println("compareTo (point0 -> point 2): " + point0.compareTo(point2));

        System.out.println("Slope (point0 -> point 1): " + point0.slopeTo(point1));
        System.out.println("Slope (point0 -> point 2): " + point0.slopeTo(point2));

        System.out.println("Compare point 1 and point 2: " + point0.slopeOrder().compare(point1, point2));

        // draw the points
        StdDraw.setXscale(-500, 500);
        StdDraw.setYscale(-500, 500);
        StdDraw.line(-500, 0, 500, 0);
        StdDraw.line(0, -500, 0, 500);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLUE);
        point0.draw();
        point1.draw();
        point2.draw();

        point0.drawTo(point1);
        point0.drawTo(point2);

    }
}
