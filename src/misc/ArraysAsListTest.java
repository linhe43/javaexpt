package misc;

import java.util.Arrays;
import java.util.List;

public class ArraysAsListTest {

    public static void main (String args[]) {

        // create an array of strings
        String a[] = new String[]{"abc","klm","xyz","pqr"};

        List<String> list1 = Arrays.asList(a);
        list1.add("aa");

        // printing the list
        System.out.println("The list is:" + list1);
    }

}

