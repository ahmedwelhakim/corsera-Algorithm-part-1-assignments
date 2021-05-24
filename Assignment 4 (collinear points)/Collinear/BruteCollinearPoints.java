import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lineSegList;
    private int segmentNo;
    private final Point[] pts;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("null argument");
        else {
            checkNullDuplicatePoints(points);
            pts = Arrays.copyOf(points, points.length);
            Arrays.sort(pts);
            lineSegList = new ArrayList<LineSegment>();
            for (int i = 0; i < pts.length; i++) {
                for (int j = i + 1; j < pts.length; j++) {
                    for (int k = j + 1; k < pts.length; k++) {
                        for (int m = k + 1; m < pts.length; m++) {
                            if (pts[i].slopeTo(pts[j]) ==
                                    pts[i].slopeTo(pts[k]) &&
                                    pts[i].slopeTo(pts[j]) ==
                                            pts[i].slopeTo(pts[m])
                            ) {
                                segmentNo++;


                                lineSegList.add(new LineSegment(pts[i], pts[m]));
                            }
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentNo;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[segmentNo];
        for (int i = 0; i < segmentNo; i++) {
            segments[i] = lineSegList.get(i);
        }
        return segments;
    }


    public static void main(String[] args) {
/*
        // read the n points from a file


        Point p1 = new Point(1000, 2000);
        Point p2 = null;


        StdOut.println(new LineSegment(p1, p2));
*/

    }

    // check null points
    private void checkNullDuplicatePoints(Point[] points) {
        for (Point point : points)
            if (point == null) throw new IllegalArgumentException("The input contains null points");
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicate point ");
                }
            }
        }
    }

}







