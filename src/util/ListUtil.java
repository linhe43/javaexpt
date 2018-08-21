package util;

import java.util.List;

public class ListUtil {

    public static <T> void printList(List<T> list) {
        if (list == null)
            throw new NullPointerException();

        for (T t : list) {
            System.out.print(t + " ");
        }
        System.out.println();
    }

}
