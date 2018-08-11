package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> points;

    /**
     * construct an empty set of points
     */
    public PointSET() {
        points = new TreeSet<>();
    }

    /**
     * is the set empty?
     * @return
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /**
     * number of points in the set
     * @return
     */
    public int size() {
        return points.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     * @param p
     */
    public void insert(Point2D p) {
        checkNull(p);
        points.add(p);
    }

    /**
     * does the set contain point p?
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        checkNull(p);
        return points.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    /**
     * all points that are inside the rectangle (or on the boundary)
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        checkNull(rect);

        List<Point2D> ret = new ArrayList<>();
        Point2D pMin = new Point2D(rect.xmin(), rect.ymin());
        Point2D pMax = new Point2D(rect.xmax(), rect.ymax());

        for (Point2D p : points.subSet(pMin, pMax)) {
            if (p.x() >= rect.xmin() && p.x() <= rect.xmax()) {
                ret.add(p);
            }
        }

        return ret;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        checkNull(p);
        if (isEmpty()) return null;

        double minDist = Double.MAX_VALUE;
        Point2D ret = null;
        for (Point2D point : points) {
            double dist = p.distanceTo(point);
            if (dist < minDist) {
                minDist = dist;
                ret = point;
            }
        }

        return ret;
    }

    private void checkNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {}
}
