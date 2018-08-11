package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class KdTree {

    private enum Seperator {
        VERTICAL,
        HORIZONTAL
    }

    private int size;
    private Node root;

    private class Node {

        private final Point2D point;
        private final Seperator sepr;
        private final RectHV rect;
        private Node left;
        private Node right;

        public Node(Point2D point, RectHV rect, Seperator sepr) {
            this.point = point;
            this.rect = rect;
            this.sepr = sepr;
        }

        public Seperator nextSepr() {
            return sepr == Seperator.VERTICAL ? Seperator.HORIZONTAL : Seperator.VERTICAL;
        }

        public Point2D getPoint() {
            return point;
        }

        public void setLeft(Node n) {
            this.left = n;
        }

        public Node getLeft() {
            return left;
        }

        public void setRight(Node n) {
            this.right = n;
        }

        public Node getRight() {
            return right;
        }

        public Seperator getSepr() {
            return sepr;
        }

        public RectHV getRect() {
            return rect;
        }

        @Override
        public String toString() {
            return (point.toString() + ", " + (sepr == Seperator.VERTICAL ? "vertical" : "horizontal"));
        }

    }


    /**
     * construct an empty set of points
     */
    public KdTree() {
        root = null;
        size = 0;
    }

    /**
     * is the set empty?
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * number of points in the set
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * add the point to the set (if it is not already in the set)
     * @param p
     */
    public void insert(Point2D p) {
        checkNull(p);

        if (root == null) {
            root = new Node(p, new RectHV(0, 0, 1, 1), Seperator.VERTICAL);
            size++;
            return;
        }

        Node cur = root;
        while (true) {
            if (cur.getPoint().compareTo(p) == 0) return;

            if ((cur.getSepr() == Seperator.VERTICAL && p.x() < cur.getPoint().x())
                    || (cur.getSepr() == Seperator.HORIZONTAL && p.y() < cur.getPoint().y())) {
                if (cur.getLeft() == null) {
                    RectHV rect = null;
                    if (cur.getSepr() == Seperator.VERTICAL) {
                        rect = new RectHV(cur.getRect().xmin(), cur.getRect().ymin(), cur.getPoint().x(), cur.getRect().ymax());
                    } else {
                        rect = new RectHV(cur.getRect().xmin(), cur.getRect().ymin(), cur.getRect().xmax(), cur.getPoint().y());
                    }
                    cur.setLeft(new Node(p, rect, cur.nextSepr()));
                    size++;
                    return;
                } else {
                    cur = cur.getLeft();
                }
            } else {
                if (cur.getRight() == null) {
                    RectHV rect = null;
                    if (cur.getSepr() == Seperator.VERTICAL) {
                        rect = new RectHV(cur.getPoint().x(), cur.getRect().ymin(), cur.getRect().xmax(), cur.getRect().ymax());
                    } else {
                        rect = new RectHV(cur.getRect().xmin(), cur.getPoint().y(), cur.getRect().xmax(), cur.getRect().ymax());
                    }
                    cur.setRight(new Node(p, rect, cur.nextSepr()));
                    size++;
                    return;
                } else {
                    cur = cur.getRight();
                }
            }
        }
    }

    /**
     * does the set contain point p?
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        checkNull(p);
        if (root == null) return false;

        Node cur = root;
        while (cur != null) {
            if (p.compareTo(cur.getPoint()) == 0) return true;
            if ((cur.getSepr() == Seperator.VERTICAL && p.x() < cur.getPoint().x())
                    || (cur.getSepr() == Seperator.HORIZONTAL && p.y() < cur.getPoint().y())) {
                cur = cur.getLeft();
            } else {
                cur = cur.getRight();
            }
        }

        return false;
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {}

    /**
     * all points that are inside the rectangle (or on the boundary)
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        checkNull(rect);
        if (root == null) return null;

        List<Point2D> ret = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            if (cur == null) continue;
            Point2D p = cur.getPoint();
            if (rect.contains(p)) {
                queue.offer(cur.getLeft());
                queue.offer(cur.getRight());
                ret.add(p);
            } else {
                if (cur.getLeft() != null && cur.getLeft().getRect().intersects(rect)) {
                    queue.offer(cur.getLeft());
                }
                if (cur.getRight() != null && cur.getRight().getRect().intersects(rect)) {
                    queue.offer(cur.getRight());
                }
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

        return nearest(p, root, root.getPoint());
    }

    private Point2D nearest(Point2D p, Node node, Point2D minPoint) {
        Point2D ret = minPoint;
        if (node == null) return ret;

        if (p.distanceTo(node.getPoint()) < p.distanceTo(minPoint)) {
            ret = node.getPoint();
        }

        if ((node.getSepr() == Seperator.VERTICAL && p.x() < node.getPoint().x())
                || (node.getSepr() == Seperator.HORIZONTAL && p.y() < node.getPoint().y())) {
            ret = nearest(p, node.getLeft(), ret);
            if (node.getRight() != null && ret.distanceTo(p) > node.getRight().getRect().distanceTo(p)) {
                ret = nearest(p, node.getRight(), ret);
            }
        } else {
            ret = nearest(p, node.getRight(), ret);
            if (node.getLeft() != null && ret.distanceTo(p) > node.getLeft().getRect().distanceTo(p)) {
                ret = nearest(p, node.getLeft(), ret);
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
