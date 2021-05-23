import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        // Checking corner cases
        if (points == null) throw new IllegalArgumentException("null argument");
        checkNullPoints(points);
        checkDuplicatedPoints(points);

        //
        Point[] pointsSlopeSorted = Arrays.copyOf(points, points.length);
        Point[] pointsSorted = Arrays.copyOf(points, points.length);
        ArrayList<LineSegment> segList = new ArrayList<LineSegment>();
        Arrays.sort(pointsSorted);
        Point lineBeginning = null;
        for (int i = 0; i < pointsSorted.length; i++) {
            Point origin = pointsSorted[i];
            Point end;
            Arrays.sort(pointsSlopeSorted);
            Arrays.sort(pointsSlopeSorted, origin.slopeOrder());
            int count = 1;
            for (int j = 0; j < pointsSlopeSorted.length - 1; j++) {
                if (origin.slopeTo(pointsSlopeSorted[j]) == origin
                        .slopeTo(pointsSlopeSorted[j + 1])) {
                    count++;

                    if (count == 2) {
                        lineBeginning = pointsSlopeSorted[j];
                        count++;
                    }
                    else if (count >= 4 && j + 1 == pointsSlopeSorted.length - 1) {
                        if (lineBeginning.compareTo(origin) > 0) {
                            segList.add(new LineSegment(origin, pointsSlopeSorted[j + 1]));
                        }
                        count = 1;
                    }
                }
                else if (count >= 4) {
                    segList.add(new LineSegment(origin, pointsSlopeSorted[j - 1]));
                    count = 1;
                }
                else {
                    count = 1;
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


    public static void main(String[] args) {

        // read the n points from a file

        int n = Integer.parseInt(args[0]);
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);
        }


        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);

        }

    }

    // check null points
    private void checkNullPoints(Point[] p) {
        for (int i = 0; i < p.length - 1; i++) {
            if (p[i] == null) {
                throw new java.lang.NullPointerException("point is null");
            }
        }

    }

    // check duplicate points
    private void checkDuplicatedPoints(Point[] p) {
        for (int i = 0; i < p.length - 1; i++) {
            if (p[i].compareTo(p[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicated points");
            }
        }
    }
}
