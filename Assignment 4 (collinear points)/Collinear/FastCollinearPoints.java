import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        // Checking corner cases
        if (points == null) throw new IllegalArgumentException("null argument");
        checkNullDuplicatePoints(points);


        //
        Point[] pointsSlopeSorted = Arrays.copyOf(points, points.length);
        Point[] pointsSorted = Arrays.copyOf(points, points.length);
        ArrayList<LineSegment> segList = new ArrayList<LineSegment>();
        Arrays.sort(pointsSorted);

        for (int i = 0; i < pointsSorted.length; i++) {
            Point origin = pointsSorted[i];
            Arrays.sort(pointsSlopeSorted);
            Arrays.sort(pointsSlopeSorted, origin.slopeOrder());

            int count = 0;
            Point first = origin;
            for (int j = 1; j < pointsSlopeSorted.length - 2; j++) {
                double slopeOfFirst = origin.slopeTo(pointsSlopeSorted[j]);


                boolean areEqualSlope = origin.slopeTo(pointsSlopeSorted[j]) == origin
                        .slopeTo(pointsSlopeSorted[j + 1]);
                boolean areNextEqualSlope = origin.slopeTo(pointsSlopeSorted[j + 1]) == origin
                        .slopeTo(pointsSlopeSorted[j + 2]);

                if (areEqualSlope) {
                    count++;
                }
                else {
                    count = 0;
                }
                if (count == 1) {
                    first = pointsSlopeSorted[j];
                }

                //if j+1 last element
                if ((j + 2) == (pointsSlopeSorted.length - 1) && areNextEqualSlope) {
                    count++;
                    if (count >= 2) {
                        if (origin.compareTo(first) <= 0) {

                            segList.add(new LineSegment(origin, pointsSlopeSorted[j + 2]));

                        }
                        count = 0;
                    }
                }
                if (count >= 2 && !areNextEqualSlope) {

                    if (origin.compareTo(first) <= 0) {

                        segList.add(new LineSegment(origin, pointsSlopeSorted[j + 1]));

                    }
                    count = 0;

                }

            }
        }
        segments = segList.toArray(new LineSegment[segList.size()]);
    }


    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments());
    }

/*
    public static void main(String[] args) {

        // read the n points from a file

        int n = StdIn.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);
        }


        // pri  nt and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (
                LineSegment segment : collinear.segments()) {
            StdOut.println(segment);

        }

    }
*/

    // check duplicate or null points
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
