package util;

public class MatrixUtil {

    public static <T> void print(T[][] mat) {
        if (mat == null || mat.length == 0) return;
        for (T[] row : mat) {
            for (T t : row) {
                System.out.print(t + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        String[][] mat = new String[][] {
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"}
        };

        MatrixUtil.print(mat);
    }

}
