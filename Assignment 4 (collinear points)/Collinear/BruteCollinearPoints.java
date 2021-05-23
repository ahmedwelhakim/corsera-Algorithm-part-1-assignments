import java.util.ArrayList;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> lineSegList;
    private int segmentNo;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("null argument");
        checkNullPoints(points);
        checkDuplicatedPoints(points);

        lineSegList = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        if (points[i].slopeTo(points[j]) ==
                                points[i].slopeTo(points[k]) &&
                                points[i].slopeTo(points[j]) ==
                                        points[i].slopeTo(points[m])
                        ) {
                            segmentNo++;
                            Point max = points[i];
                            Point min = points[i];
                            if (max.compareTo(points[j]) > 0) {
                                max = points[j];
                            }
                            if (max.compareTo(points[k]) > 0) {
                                max = points[k];
                            }
                            if (max.compareTo(points[m]) > 0) {
                                max = points[m];
                            }
                            if (min.compareTo(points[j]) < 0) {
                                min = points[j];
                            }
                            if (min.compareTo(points[k]) < 0) {
                                min = points[k];
                            }
                            if (min.compareTo(points[m]) < 0) {
                                min = points[m];
                            }

                            lineSegList.add(new LineSegment(max, min));
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

