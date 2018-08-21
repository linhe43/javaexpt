package geeksforgeeks;

import java.lang.reflect.Method;

public class InheritanceTest {

    public static void main(String[] args) {
        Base x = new Derived();
        Derived y = new Derived();

        Method[] methods = x.getClass().getMethods();
        x.sf(1);
        Base z = (Base) y;
        z.sf(1);
        y.sf(1);
    }

}

class Base {
    Base() {
        System.out.println("in base");
    }
    void f(int i) {
        System.out.println(i);
    }

    static void sf(int i) {
        System.out.println(i);
    }
}

class Derived extends Base {
    Derived() {
        System.out.println("in derived");
    }
    void f(int i) {
        System.out.println(2 * i);
    }

    static void sf(int i) {
        System.out.println(2 * i);
    }
}
