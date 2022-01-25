package jan.twentyFour;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    char[][] board = {
        {'o', 'a', 'b', 'n'},
        {'o', 't', 'a', 'e'},
        {'a', 'h', 'k', 'r'},
        {'a', 'f', 'l', 'v'}
    };
    String[] array = {"oa", "oaa"};
    List<String> res = findWords(board, array);
  }


  static int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
  int m;
  int n;

  private TrieNode root;

  public List<String> findWords(char[][] board, String[] words) {
    this.m = board.length;
    this.n = board[0].length;
    root = new TrieNode();
    addWords(words);
    Set<String> set = new HashSet<>();

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        if (root.children.containsKey(board[i][j])) {
          backtrack(board, i, j, root, set);
        }
      }
    }
    List<String> res = new ArrayList<>();
    res.addAll(set);
    return res;
  }

  private void backtrack(char[][] board, int i, int j, TrieNode cur, Set<String> words) {
    char ch = board[i][j];
    if (!cur.children.containsKey(ch)) return;
    TrieNode node = cur.children.get(ch);
    if (node.word != null) {
      words.add(node.word);
    }
    board[i][j] = '#';
    for (int[] dir : DIRS) {
      int x = i + dir[0];
      int y = j + dir[1];
      if (x < 0 || y < 0 || x >= m || y >= n || !node.children.containsKey(board[x][y])) continue;
      backtrack(board, x, y, node, words);
    }
    board[i][j] = ch;
    if (node.children.isEmpty()) {
      cur.children.remove(ch);
    }
  }

  private void addWords(String[] words) {
    for (String word : words) {
      add(word);
    }
  }

  private void add(String word) {
    TrieNode cur = root;
    for (char ch : word.toCharArray()) {
      if (!cur.children.containsKey(ch)) {
        cur.children.put(ch, new TrieNode());
      }
      cur = cur.children.get(ch);
    }
    cur.word = word;
  }

  static class TrieNode {
    String word;
    Map<Character, TrieNode> children;

    TrieNode() {
      children = new HashMap<>();
      word = null;
    }
  }



  public boolean exist(char[][] board, String word) {
    if (board == null || board.length == 0) return false;
    int m = board.length;
    int n = board[0].length;
    boolean[][] visited = new boolean[m][n];

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (dfs(i, j, board, visited, 0, word)) return true;
      }
    }
    return false;
  }

  private boolean dfs(int i, int j, char[][] board, boolean[][] visited, int index, String word) {
    if (index == word.length()) return true;
    if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || visited[i][j] || word.charAt(index) != board[i][j])
      return false;
    visited[i][j] = true;
    for (int[] dir : DIRS) {
      if (dfs(i + dir[0], j + dir[1], board, visited, index + 1, word)) return true;
    }
    visited[i][j] = false;
    return false;
  }


  public ListNode mergeKLists(ListNode[] lists) {
    if (lists == null || lists.length == 0) return null;

    PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));

    ListNode dummy = new ListNode(1);
    ListNode cur = dummy;
    for (ListNode node : lists) {
      if (node != null) {
        minHeap.offer(node);
      }
    }

    while (!minHeap.isEmpty()) {
      ListNode node = minHeap.poll();
      cur.next = node;
      cur = cur.next;
      node = node.next;
      if (node != null) minHeap.offer(node);
    }
    return dummy.next;
  }

  static class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }
  }

}
