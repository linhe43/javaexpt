package intervals;

public class Interval {
    public int start, end;

    public Interval(int start, int end) {
        this.start = start;
        this.end =end;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", start, end);
    }
}
