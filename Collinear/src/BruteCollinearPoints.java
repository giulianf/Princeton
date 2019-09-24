import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> jSegments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        Point[] jCopy = points.clone();
        Arrays.sort(jCopy);

        if (hasDuplicate(jCopy)) {
            throw new IllegalArgumentException("U have duplicate points");
        }

        for (int i = 0; i < jCopy.length - 3; i++) {
            for (int j = i + 1; j < jCopy.length - 2; j++) {
                double slopeFS = jCopy[i].slopeTo(jCopy[j]);
                for (int third = j + 1; third < jCopy.length - 1; third++) {
                    double slopeFT = jCopy[i].slopeTo(jCopy[third]);
                    if (slopeFS == slopeFT) {
                        for (int forth = third + 1; forth < jCopy.length; forth++) {
                            double slopeFF = jCopy[i].slopeTo(jCopy[forth]);
                            if (slopeFS == slopeFF) {
                                jSegments.add(new LineSegment(jCopy[i], jCopy[forth]));
                            }
                        }
                    }
                }

            }
        }
    }

    private boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;

    }

    // the number of line segments
    public int numberOfSegments() {
        return jSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return jSegments.toArray(new LineSegment[jSegments.size()]);
    }

}