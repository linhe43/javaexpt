package leetcode;

import util.ListUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordSearchII {
    public List<String> findWords(char[][] board, String[] words) {
        List<String> ret = new ArrayList<>();
        if (board == null || board.length == 0 || words == null || words.length == 0) {
            return ret;
        }

        // construct the prefix tree using words;
        PrefixTrieNode tree = new PrefixTrieNode(' ');
        for (String word : words) {
            char[] chArr = word.toCharArray();
            PrefixTrieNode cur = tree;
            for (char ch : chArr) {
                if (cur.children[ch - 'a'] == null) {
                    cur.children[ch - 'a'] = new PrefixTrieNode(ch);
                }
                cur = cur.children[ch - 'a'];
            }
            cur.word = word;
        }

        // search words;
        Set<String> set = new HashSet<>();
        int m = board.length, n = board[0].length;
        for (int i  = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                boolean[][] visited = new boolean[m][n];
                findWords(board, visited, i, j, m, n, tree.children[board[i][j] - 'a'], set);
            }
        }

        ret.addAll(set);
        return ret;
    }

    private void findWords(char[][] board, boolean[][] visited, int row, int col, int rowNum, int colNum,
                           PrefixTrieNode node, Set<String> set) {
        if (node == null) return;
        if (node.word != null) set.add(node.word);
        visited[row][col] = true;

        if (row + 1 < rowNum && !visited[row + 1][col]) {
            findWords(board, visited, row + 1, col, rowNum, colNum, node.children[board[row + 1][col] - 'a'], set);
        }
        if (row - 1 >= 0 && !visited[row - 1][col]) {
            findWords(board, visited, row - 1, col, rowNum, colNum, node.children[board[row - 1][col] - 'a'], set);
        }
        if (col + 1 < colNum && !visited[row][col + 1]) {
            findWords(board, visited, row, col + 1, rowNum, colNum, node.children[board[row][col + 1] - 'a'], set);
        }
        if (col - 1 >= 0 && !visited[row][col - 1]) {
            findWords(board, visited, row, col - 1, rowNum, colNum, node.children[board[row][col - 1] - 'a'], set);
        }
    }

    public static void main(String[] args) {
        String[] words = {"oath","pea","eat","rain"};
        char[][] board = {
                {'o', 'a', 'a', 'n'},
                {'e', 't', 'a', 'e'},
                {'i', 'h', 'k', 'r'},
                {'i', 'f', 'l', 'v'}};
        words = new String[] {"aaa"};
        board = new char[][] {{'a', 'a'}};
        words = new String[] {"abcdefg","gfedcbaaa","eaabcdgfa","befa","dgc","ade"};
        board = new char[][] {{'a','b','c'}, {'a','e','d'}, {'a','f','g'}};
        List<String> ret = new WordSearchII().findWords(board, words);
        ListUtil.println(ret);
    }
}

class PrefixTrieNode {
    char val;
    PrefixTrieNode[] children;
    String word;

    public PrefixTrieNode(char val) {
        this.val = val;
        children = new PrefixTrieNode[26];
    }
}
