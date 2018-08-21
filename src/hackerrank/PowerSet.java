package hackerrank;

import util.ArrayUtil;
import util.ListUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PowerSet {

    public List<String[]> getPowerSet(String[] arr) {
        if (arr == null) return null;

        List<String[]> ret = new ArrayList<>();
        if (arr.length == 0) return ret;

        getPowerSetUtil(arr, 0, ret);

        return ret;
    }

    private void getPowerSetUtil(String[] arr, int lo, List<String[]> list) {
        if (lo == arr.length - 1) {
            list.add(new String[] { arr[lo] });
            return;
        }

        String cur = arr[lo];
        getPowerSetUtil(arr, lo + 1, list);
        int idx = list.size() - 1;
        while (idx >= 0) {
            List<String> tmp = new ArrayList<>();
            tmp.add(cur);
            tmp.addAll(Arrays.asList(list.get(idx)));
            list.add(tmp.toArray(new String[0]));
            idx--;
        }
        list.add(new String[] { cur });

        if (lo == 0) list.add(new String[0]);
    }

    public static void main(String[] args) {
        String[] set = new String[] {"a", "b", "c", "d", "e"};
        List<String[]> pSet = new PowerSet().getPowerSet(set);

        for (String[] arr : pSet) {
            ArrayUtil.printArray(arr);
        }
    }
}
