import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    LineSegment[] segments;

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
        List<LineSegment> segmentList = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int p = j + 1; p < points.length - 1; p++) {
                    for (int q = p + 1; q < points.length; q++) {
                        double s1 = points[i].slopeTo(points[j]);
                        double s2 = points[i].slopeTo(points[p]);
                        double s3 = points[i].slopeTo(points[q]);
                        if (s1 == s2 && s2 == s3) {
                            segmentList.add(new LineSegment(points[i], points[q]));
                        }
                    }
                }
            }
        }

        segments = segmentList.toArray(new LineSegment[0]);
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
        return segments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In("points.txt");
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
