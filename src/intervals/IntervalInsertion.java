package intervals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IntervalInsertion {
    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        if (intervals.isEmpty()) intervals.add(newInterval);

        int pos = binarySearch(intervals, newInterval.start);
        pos = pos < 0 ? -pos - 1 : pos;
        pos = pos - 1 < 0 ? pos : pos - 1;
        Iterator<Interval> it = intervals.subList(pos, intervals.size()).iterator();
        int start = newInterval.start;
        int end = newInterval.end;
        while (it.hasNext()) {
            Interval cur = it.next();
            if (cur.start > end) break;
            if (start > cur.end) {
                pos++;
                continue;
            }
            start = Math.min(start, cur.start);
            end = Math.max(end, cur.end);
            it.remove();
        }

        intervals.add(pos, new Interval(start, end));
        return intervals;
    }

    private int binarySearch(List<Interval> intervals, int start) {
        if (intervals.isEmpty()) return -1;

        int lo = 0, hi = intervals.size() - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (start == intervals.get(mid).start) return mid;
            else if (start < intervals.get(mid).start) hi = mid - 1;
            else lo = mid + 1;
        }

        return -lo - 1;
    }

    public static void main(String[] args) {
        List<Interval> list = new ArrayList<>();
        list.add(new Interval(1, 3));
        list.add(new Interval(6, 9));
        new IntervalInsertion().insert(list, new Interval(2, 5));
        for (Interval interval : list) {
            System.out.println(interval.toString());
        }
    }
}
