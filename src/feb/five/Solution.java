package feb.five;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    String s = "00011000";
    int res = minFlipsMonoIncr(s);
  }

  public int minAddToMakeValid(String s) {
    return 0;
  }


  public int jump(int[] nums) {
    int m = nums.length;
    int curMax = 0;
    int max = 0;
    int jumps = 0;

    for (int i = 0; i < m; i++) {
      if (i == m - 1) return jumps;
      max = Math.max(max, nums[i] + i);
      if (curMax == i) {
        jumps++;
        curMax = max;
      }
    }
    return jumps;
  }


  public int[] asteroidCollision(int[] asteroids) {
    if (asteroids == null || asteroids.length == 0) return asteroids;

    Deque<Integer> stack = new ArrayDeque<>();

    for (int ast : asteroids) {
      tag : {
        while (!stack.isEmpty() && ast < 0 && stack.peekFirst() > 0) {
          if (stack.peekFirst() < -ast) {
            stack.pollFirst();
            continue;
          } else if (stack.peekFirst() == -ast) {
            stack.pollFirst();
          }
          break tag;
        }
        stack.offerFirst(ast);
      }
    }

    int size = stack.size();
    int[] res = new int[size];
    for (int i = 0; i < size; i++) {
      res[i] = stack.pollLast();
    }
    return res;
  }

  public List<List<String>> solveNQueens(int n) {
    List<List<String>> res = new ArrayList<>();
    if (n == 0) return res;
    char[][] board = new char[n][n];
    for (char[] arr : board) Arrays.fill(arr, '.');
    dfs(board, 0, res);
    return res;
  }

  private void dfs(char[][] board, int row, List<List<String>> res) {
    if (row == board.length) {
      List<String> cur = new ArrayList<>();
      for (char[] arr : board) {
        cur.add(new String(arr));
      }
      res.add(cur);
      return;
    }

    int n = board[0].length;
    for (int c = 0; c < n; c++) {
      if (!isValid(board, row, c)) continue;
      board[row][c] = 'Q';
      dfs(board, row + 1, res);
      board[row][c] = '.';
    }

  }

  private boolean isValid(char[][] board, int row, int col) {
    int n = board.length;

    for (int i = 0; i < n; i++) {
      if (board[i][col] == 'Q') return false;
    }
    for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
      if (board[i][j] == 'Q') return false;
    }
    for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
      if (board[i][j] == 'Q') return false;
    }
    return true;
  }



  public void merge(int[] one, int m, int[] two, int n) {
    int i = m - 1;
    int j = n - 1;

    for (int index = m + n - 1; index >= 0; index--) {
      if (j < 0) {
        break;
      }
      if (i >= 0 && one[i] > two[j]) {
        one[index] = one[i--];
      } else {
        one[index] = two[j--];
      }
    }
  }


  public int minFlipsMonoIncr(String s) {
    /*
    0 0 0 1 1 0 0 0
    0 0 0 1 2 2 2
    0 0 0 0 0 1 2

    if 0, zeroEnd is the same, one is Math.min(zero, one)
    if 1, zeroEnd + 1
    min zero + 1, one

    zeroEnd
    zeroOrOneEnd
     */
    int m = s.length();
    int zero = 0;
    int oneOrZero = 0;

    for (int i = 0; i < m; i++) {
      if (s.charAt(i) == '0') {
        oneOrZero = Math.min(oneOrZero + 1, zero);
      } else {
        zero = zero + 1;
      }
    }
    return Math.min(oneOrZero, zero);
  }


  public int maximalSquare(char[][] matrix) {
    if (matrix == null || matrix.length == 0) return 0;

    int m = matrix.length;
    int n = matrix[0].length;

    int[][] dp = new int[m][n];
    int max = 0;

    for (int i = 0; i < m; i++) {
      dp[i][0] = matrix[i][0] - '0';
      max = Math.max(max, dp[i][0]);
    }
    for (int i = 0; i < n; i++) {
      dp[0][i] = matrix[0][i] - '0';
      max = Math.max(max, dp[0][i]);
    }
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {

        if (matrix[i][j] == '1') {
          dp[i][j] = min(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]) + 1;
          max = Math.max(max, dp[i][j]);
        }
      }
    }

    return max * max;
  }

  private int min(int a, int b, int c) {
    return Math.min(a, Math.min(b, c));
  }

  Set<String> set;
  public List<String> wordBreak(String s, List<String> wordDict) {
    List<String> res = new ArrayList<>();
    if (s == null || s.length() == 0 || wordDict.size() == 0) return res;
    set = new HashSet<>(wordDict);
    StringBuilder prefix = new StringBuilder();
    helper(s, 0, prefix, res);
    return res;
  }

  private void helper(String s, int index, StringBuilder prefix, List<String> res) {
    if (index == s.length()) {
      if (prefix.length() > 0) {
        res.add(new String(prefix.substring(0, prefix.length() - 1)));
      }
      return;
    }

    for (int i = index; i < s.length(); i++) {
      if (set.contains(s.substring(index, i + 1))) {
        prefix.append(s, index, i + 1);
        prefix.append(" ");
        helper(s, i + 1, prefix, res);
        prefix.deleteCharAt(prefix.length() - 1);
        prefix.delete(prefix.length() - 1 - (i - index), prefix.length());
      }
    }

  }


  public String[] reorderLogFiles(String[] logs) {
    if (logs == null || logs.length == 0) return logs;

    Arrays.sort(logs, (a, b) -> {
      String[] arr = a.split(" ", 2);
      String[] brr = b.split(" ", 2);

      boolean aIsDigit = Character.isDigit(arr[1].charAt(0));
      boolean bIsDigit = Character.isDigit(brr[1].charAt(0));

      if (!aIsDigit && !bIsDigit) {
        int comp = arr[1].compareTo(brr[1]);
        if (comp != 0) return comp;
        return arr[0].compareTo(brr[0]);
      }
      if (!aIsDigit && bIsDigit) {
        return -1;
      } else if (aIsDigit && !bIsDigit) {
        return 1;
      } else {
        return 0;
      }
    });
    return logs;
  }

  public boolean wordBreakOne(String s, List<String> wordDict) {
    /*
     */
    if (s == null || s.length() == 0) return false;
    Set<String> dict = new HashSet<>(wordDict);
    int m = s.length();
    // dp[i] represents when string s's length is i, whether it could be break;
    /*
   0 1 2 3 4 5 6 7 8 9 10
     c a t s a n g d a g
   t f

  ["cats","dog","sand","and","cat"]
     */

    boolean[] dp = new boolean[m + 1];
    dp[0] = true;

    for (int i = 1; i <= m; i++) {

      for (int j = i - 1; j >= 0; j--) {
        if (dp[j] && dict.contains(s.substring(j, i))) {
          dp[i] = true;
          break;
        }
      }
    }
    return dp[m];
  }


}
