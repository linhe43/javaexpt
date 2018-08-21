package util;

public class ArrayUtil {

    public static <T> void printArray(T[] arr) {
        if (arr == null)
            throw new NullPointerException();

        for (T t : arr) {
            System.out.print(t + " ");
        }
        System.out.println();
    }

}
