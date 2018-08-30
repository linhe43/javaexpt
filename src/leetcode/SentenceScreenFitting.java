package leetcode;

public class SentenceScreenFitting {

    public int wordsTyping(String[] sentence, int rows, int cols) {
        if (sentence == null || sentence.length == 0) return 0;
        for (String s : sentence) {
            if (s.length() > cols) return 0;
        }
        if (sentence.length == 1) {
            return rows * ((cols + 1) / (sentence[0].length() + 1));
        }

        int n = sentence.length;
        int[] dp = new int[n];
        int cnt = 0, curIdx = 0, end = rows * cols;
        boolean flag = false;
        while (true) {
            if (curIdx == 0) {
                if (flag) {
                    int sNum = cols - (dp[n - 1] + sentence[n - 1].length()) % cols;
                    sNum = sNum == cols ? 0 : sNum;
                    if (sNum - 1 >= sentence[curIdx].length()) {
                        dp[curIdx] = dp[n - 1] + sentence[n - 1].length() + 1;
                    } else {
                        dp[curIdx] = (dp[n - 1] / cols + 1) * cols;
                    }
                }
            } else {
                int sNum = cols - (dp[curIdx - 1] + sentence[curIdx - 1].length()) % cols;
                sNum = sNum == cols ? 0 : sNum;
                if (sNum - 1 >= sentence[curIdx].length()) {
                    dp[curIdx] = dp[curIdx - 1] + sentence[curIdx - 1].length() + 1;
                } else {
                    dp[curIdx] = (dp[curIdx - 1] / cols + 1) * cols;
                }
            }
            if (dp[curIdx] + sentence[curIdx].length() > end) break;
            flag = true;
            cnt++;
            curIdx++;
            curIdx %= n;
        }

        return cnt / n;
    }

    public static void main(String[] args) {
        String[] sentence = new String[] {"hello", "world"};
        int rows = 2, cols = 8;
//        sentence = new String[] {"a", "bcd", "e"};
//        rows = 3; cols = 6;
//        sentence = new String[] {"I", "had", "apple", "pie"};
//        rows = 4; cols = 5;
//        sentence = new String[] {"h"};
//        rows = 2; cols = 3;
        sentence = new String[] {"hello"};
        rows = 10000; cols = 1;
        sentence = new String[] {"a"};
        rows = 20000; cols = 20000;
        System.out.println(new SentenceScreenFitting().wordsTyping(sentence, rows, cols));
    }
}
