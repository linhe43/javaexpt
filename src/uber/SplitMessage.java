package uber;

import java.util.*;

// SMS Split
// Input: char_limit = 20, message = "Hey Chengyu, your Uber is arriving now!"
// "asdfoijaspdifjoiajpsdofijpsadjfpoaisjdpfoasdjpfoiajspofdij"
// Output: ["Hey Chengyu, (1/3)", "your Uber is (2/3)", "arriving now! (3/3)"]

// Requirements:
// 1) Do not split a word across messages -- unless the word will not
// fit in a single message, then you can split it across two messages or truncate the word.
// 2) Metadata (ie. ` (1/3)`) counts against the character limit. For example "Hey Chengyu, (1/3)" contains 18 characters.

public class SplitMessage {
    public static void main(String args[] ) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        String message = "Hey Chengyu, your Uber is arriving now!";
        int charLimit = 20;

        String[] words = message.split(" ");

        // calcuate the lower bound;
        int lb = Math.round(message.length() / charLimit);

        // the upper bound;
        int hb = words.length;

        // repeart to get the solution;
        for (int digitNum = getDigitNum(lb); digitNum <= getDigitNum(hb); digitNum++) {
            int pageNum = 1;
            List<StringBuilder> list = new ArrayList<>();
            list.add(new StringBuilder());
            int curIdx = 0;
            for (String word : words) {
                int metaLen = 3 + digitNum + getDigitNum(pageNum);
                if (list.get(curIdx).length() + word.length() + 1 + metaLen > charLimit) {
                    pageNum++;
                    list.add(new StringBuilder());
                    curIdx++;
                }
                list.get(curIdx).append(word).append(" ");
            }

            // add meta information into each StringBuilder in the list;
            for (int i = 0; i < list.size(); i++) {
                list.get(i).append("(").append(i + 1).append("/").append(pageNum).append(")");
            }

            if (getDigitNum(list.size()) == digitNum) {
                printList(list);
                break;
            }
        }
    }

    private static void printList(List<StringBuilder> list) {
        for (StringBuilder sb : list) {
            System.out.println(sb.toString());
        }
    }

    private static int getDigitNum(int i) {
        return (int) Math.round(Math.log10(i));
    }
}