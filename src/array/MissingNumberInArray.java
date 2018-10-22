package array;

import java.util.*;

/**
 * Java program to find missing elements in a Integer array containing
 * numbers from 1 to 100.
 *
 * @author Lin He
 */
public class MissingNumberInArray {

    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);
        List<Integer> list = new ArrayList<>();
        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }
        
        int[] nums = new int[list.size() - 1];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = list.get(i);
        }
        int count = list.get(list.size() - 1);


        // method one
        printMissingNumber(nums, count);


        // method two
        int missing = getMissingNumber(nums, count);
        System.out.printf("Missing number in array %s is %d %n",
                Arrays.toString(nums), missing);
    }

    /**
     * A general method to find missing values from an integer array in Java.
     * This method will work even if array has more than one missing element.
     */
    private static void printMissingNumber(int[] numbers, int count) {
        BitSet bitSet = new BitSet(count);

        for (int number : numbers) {
            bitSet.set(number - 1);
        }

        System.out.printf("Missing numbers in integer array %s, with total number %d is %n",
                Arrays.toString(numbers), count);
        int lastMissingIndex = 0;


        while (true) {
            int missingIndex = bitSet.nextClearBit(lastMissingIndex);
            if (missingIndex >= count) {
                break;
            }
            System.out.println(++missingIndex);
            lastMissingIndex = missingIndex;
        }

    }

    /**
     * Java method to find missing number in array of size n containing
     * numbers from 1 to n only.
     * can be used to find missing elements on integer array of
     * numbers from 1 to 100 or 1 - 1000
     */
    private static int getMissingNumber(int[] numbers, int totalCount) {
        int expectedSum = totalCount * ((totalCount + 1) / 2);
        int actualSum = 0;
        for (int i : numbers) {
            actualSum += i;
        }

        return expectedSum - actualSum;
    }

}