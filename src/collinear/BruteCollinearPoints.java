package collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    private LineSegment[] segments;

    /**
     * finds all line segments containing 4 points
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
        validatePoints(points);

        if (points.length < 4) {
            segments = new LineSegment[0];
            return;
        }

        Arrays.sort(points);
        List<Point[]> segmentList = new ArrayList<Point[]>();
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                double s1 = points[i].slopeTo(points[j]);
                for (int p = j + 1; p < points.length - 1; p++) {
                    double s2 = points[i].slopeTo(points[p]);
                    for (int q = points.length - 1; q >= p + 1; q--) {
                        double s3 = points[i].slopeTo(points[q]);
                        if (s1 == s2 && s2 == s3) {
                            segmentList.add(new Point[] {points[i], points[q]});
                        }
                    }
                }
            }
        }

        if (segmentList.isEmpty()) {
            segments = new LineSegment[0];
            return;
        }

        List<Point[]> finalList = new ArrayList<Point[]>();
        finalList.add(segmentList.get(0));
        int curIdx = 0;
        for (int i = 1; i < segmentList.size(); i++) {
            Point[] tmp = segmentList.get(i);
            if (tmp[0].slopeTo(tmp[1]) == tmp[0].slopeTo(finalList.get(curIdx)[1])) {
                continue;
            } else {
                finalList.add(tmp);
                curIdx++;
            }
        }

        segments = new LineSegment[finalList.size()];
        for (int i = 0; i < finalList.size(); i++) {
            segments[i] = new LineSegment(finalList.get(i)[0], finalList.get(i)[1]);
        }
    }

    private void validatePoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null) {
                    throw new IllegalArgumentException();
                }
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

    }

    /**
     * the number of line segments
     * @return
     */
    public int numberOfSegments() {
        return segments.length;
    }

    /**
     * the line segments
     * @return
     */
    public LineSegment[] segments() {
        return segments.clone();
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
