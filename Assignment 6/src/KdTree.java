import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.*;
import java.util.ArrayList;

public class KdTree {
    Tree tree;

    // construct an empty set of points
    public KdTree() {
        tree = new Tree();
    }

    // is the set empty?
    public boolean isEmpty() {
        return tree.size() == 0;
    }

    // number of points in the set
    public int size() {
        return  tree.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        tree.insert(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return tree.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        tree.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        return tree.range(rect);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if(tree.size()==0) {

            return null;
        }
        Point2D ans = tree.nearest(p);
        StdOut.println(ans);
        return  ans;
    }


    private static class Node {
        private Point2D point = null;      // the point
        private double depth;
        private Node left = null;        // the left/bottom subtree
        private Node right = null;         // the right/top subtree

        public Node(Point2D p, double depth) {
            this.point = p;
            this.depth = depth;
        }

        public Node(double depth) {
            this.depth = depth;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public void setDepth(double depth) {
            this.depth = depth;
        }

        public void setPoint(Point2D p) {
            this.point = p;
        }

        public Point2D getPoint() {
            return this.point;
        }

        public double getDepth() {
            return this.depth;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }
    }

    private static class Tree {
        final private Node root = new Node(1);
        private int size = 0;

        public void insert(Point2D p) {
            if(this.contains(p)) return;
            this.size++;
            if (root.getPoint() == null) {
                root.setPoint(p);
                return;
            }
            Node iter = root;
            Node prev = root;
            while (iter != null) {
                prev = iter;
                if (lte(p, iter.getPoint(), iter.getDepth())) iter = iter.getLeft();
                else iter = iter.getRight();
            }
            Node newNode = new Node(p, prev.getDepth() + 1);
            if (lte(p, prev.getPoint(), prev.getDepth()))
                prev.setLeft(newNode);
            else
                prev.setRight(newNode);
        }

        public boolean contains(Point2D p) {
            Node iter = root;
            while (iter != null && iter.getPoint()!=null) {
                if (iter.getPoint().equals(p)) return true;
                if (lte(p, iter.getPoint(), iter.getDepth())) iter = iter.getLeft();
                else iter = iter.getRight();
            }
            return false;
        }

        public ArrayList<Point2D> range(RectHV rect) {
            ArrayList<Point2D> res = rangeHelper(root, rect);
            if (res != null )return res;
            return new ArrayList<>();
        }

        private static ArrayList<Point2D> rangeHelper(Node root, RectHV rec) {
            if (root == null) return null;
            ArrayList<Point2D> left = null, right = null, res = null;
            if (both(root, rec)) {
                left = rangeHelper(root.getLeft(), rec);
                right = rangeHelper(root.getRight(), rec);
            } else if (left(root, rec)) {
                left = rangeHelper(root.getLeft(), rec);
            } else {
                right = rangeHelper(root.getRight(), rec);
            }
            if (rec.contains(root.getPoint())) {
                res = new ArrayList<>();
                res.add(root.getPoint());
            }
            if (res != null) {
                if (left != null) res.addAll(left);
                if (right != null) res.addAll(right);
                return right;
            }
            if (left != null) {
                if (right != null) left.addAll(right);
                return left;
            }
            return right;
        }

        public Point2D nearest(Point2D target) {
            Node ans = nearestNeighbor(root, target);
            if (ans == null) return null;
            return ans.getPoint();
        }

        public int size() {
            return this.size;
        }

        private static Node nearestNeighbor(Node root, Point2D target) {
            if (root == null || root.getPoint() == null) return null;
            Node nextNode, otherNode, bestNode;
            if (lte(target, root.getPoint(), root.getDepth())) {
                nextNode = root.getLeft();
                otherNode = root.getRight();
            } else {
                otherNode = root.getLeft();
                nextNode = root.getRight();
            }
            Node tmp = nearestNeighbor(nextNode, target);
            bestNode = closest(tmp, root, target);
            double radiusSquared = distanceSquared(bestNode.getPoint(), target);
            double distSquared ;
            if (root.getDepth() % 2 == 0)
                distSquared = Math.pow((target.x() - root.getPoint().x()), 2);
            else
                distSquared = Math.pow((target.y() - root.getPoint().y()), 2);
            if (radiusSquared >= distSquared) {
                tmp = nearestNeighbor(otherNode, target);
                bestNode = closest(tmp, bestNode, target);
            }
            return bestNode;
        }

        private static Node closest(Node n1, Node n2, Point2D target) {
            if (n1 == null) return n2;
            if (n2 == null) return n1;
            double distance1 = distance(n1.getPoint(), target);
            double distance2 = distance(n2.getPoint(), target);
            return distance1 < distance2 ? n1 : n2;
        }

        private static double distance(Point2D p1, Point2D p2) {
            return Math.sqrt(Math.pow(p1.x() - p2.x(), 2) + Math.pow(p1.y() - p2.y(), 2));
        }

        private static double distanceSquared(Point2D p1, Point2D p2) {
            return Math.pow(p1.x() - p2.x(), 2) + Math.pow(p1.y() - p2.y(), 2);
        }

        private static boolean both(Node n, RectHV rect) {
            Point2D np = n.getPoint();
            if (n.getDepth() % 2 == 1)
                return rect.xmin() <= np.x() && rect.xmax() >= np.x();
            else
                return rect.ymin() <= np.y() && rect.ymax() >= np.y();
        }

        private static boolean left(Node n, RectHV rect) {
            Point2D np = n.getPoint();
            if (n.getDepth() % 2 == 1)
                return rect.xmin() <= np.x();
            else
                return rect.ymin() <= np.y();
        }


        private static boolean lte(Point2D p1, Point2D p2, double d) {
            if(d % 2 == 1) return (p1.x() <= p2.x());
            return (p1.y() <= p2.y());
        }


        public void draw(){
            drawHelper(root,0,1,1,0);
        }
        private static  void  drawHelper(Node root,double left , double right,double top, double bottom ){
            if(root == null || root.getPoint() == null) return;

            if(root.getDepth()%2==0){
                // Horizontal
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.line(left,root.getPoint().y(),right,root.getPoint().y());
                // Vertical
                drawHelper(root.getRight(),left,right,top,root.getPoint().y());
                drawHelper(root.getLeft(),left,right,root.getPoint().y(),bottom);
            }
            else {
                // vertical
                StdDraw.setPenColor(Color.RED);
                StdDraw.line(root.getPoint().x(),top,root.getPoint().x(),bottom);
                // Horizontal
                drawHelper(root.getLeft(),left,root.getPoint().x(),top,bottom);
                drawHelper( root.getRight(),root.getPoint().x(),right,top,bottom);
            }
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.point(root.getPoint().x(),root.getPoint().y());
        }
    }
}