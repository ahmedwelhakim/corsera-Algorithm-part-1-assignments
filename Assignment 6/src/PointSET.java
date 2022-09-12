import edu.princeton.cs.algs4.*;

import java.util.ArrayList;

public class PointSET {
    SET<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!points.contains(p))
            points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : points) {
            StdDraw.point(p.x(), p.y());
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> pointsInside = new ArrayList<>();
        for(Point2D p : points){
            if(rect.contains(p))
                pointsInside.add(p);
        }
        return pointsInside;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        PointDistance nearest = new PointDistance(points.min(), distance(p, points.min()));
        for(Point2D p1 : points){
            final double dist = distance(p1,p);
            if(dist<nearest.distance)
                nearest = new PointDistance(p1, dist);
        }
        return nearest.point;
    }
    private double distance(Point2D p1, Point2D p2){
        return Math.sqrt((p1.x()-p2.x())*(p1.x()-p2.x()) + (p1.y()-p2.y())*(p1.y()-p2.y()));
    }
    private static class PointDistance{
        public Point2D point;
        public double distance;
        PointDistance(Point2D p, double distance){
            this.point = p;
            this.distance = distance;
        }
    }

}