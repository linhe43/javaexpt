package unittest;

public class UnitTestDemo {

    public int func(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;

        return func(n - 1) + func(n - 2);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: UnitTestDemo N");
            return;
        }

        int n = 0;
        try {
            n = Integer.parseInt(args[0]);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.format("The N-th Fibonacci number is: %d%n", new UnitTestDemo().func(n));
    }

}