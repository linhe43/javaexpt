package util;

public class ArrayUtil {

    public static <T> void print(T[] arr) {
        if (arr == null)
            throw new NullPointerException();

        for (T t : arr) {
            System.out.print(t + " ");
        }
        System.out.println();
    }

    public static void print(int[] arr) {
        if (arr == null)
            throw new NullPointerException();

        for (int t : arr) {
            System.out.print(t + " ");
        }
        System.out.println();
    }

    public static <T> void println(T[] arr) {
        if (arr == null)
            throw new NullPointerException();

        for (T t : arr) {
            System.out.println(t);
        }
        System.out.println();
    }

}
