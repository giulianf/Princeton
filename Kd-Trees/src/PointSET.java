import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

public class PointSET {
    private SET<Point2D> setPoints;

    // construct an empty set of points
    public PointSET() {
        setPoints = new SET<>();
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }

    // is the set empty?
    public boolean isEmpty() {
        return setPoints.isEmpty();
    }

    // number of points in the set
    public int size() {
        return setPoints.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D point2D) {
        if (point2D == null) {
            throw new IllegalArgumentException();
        }
        setPoints.add(point2D);
    }

    // does the set contain point point2D?
    public boolean contains(Point2D point2D) {
        if (point2D == null) {
            throw new IllegalArgumentException();
        }
        return setPoints.contains(point2D);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : setPoints) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rectHV) {
        if (rectHV == null) {
            throw new IllegalArgumentException();
        }
        // Touch each point to see whether the given rectHV contains it.
        Stack<Point2D> ans = new Stack<>();
        for (Point2D p : setPoints) {
            if (rectHV.contains(p)) {
                ans.push(p);
            }
        }

        return ans;
    }

    // a nearest neighbor in the set to point point2D; null if the set is empty
    public Point2D nearest(Point2D point2D) {
        if (point2D == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            return null;
        }

        // Initialize loop variables
        Point2D closestPoint = null; // null initialization required by Java?
        double closestDistance = Double.MAX_VALUE;

        // Touch every point to see which is the closest to the given point point2D
        for (Point2D point : setPoints) {
            double currentDistance = point2D.distanceTo(point);
            if (currentDistance < closestDistance) {
                closestDistance = currentDistance;
                closestPoint = point;
            }
        }

        return closestPoint;

    }
}
