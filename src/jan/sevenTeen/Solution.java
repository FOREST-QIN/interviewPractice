package jan.sevenTeen;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    int[][] matrix = {{1,3,1},{1,5,1},{4,2,1}};
    int res = minPathSum(matrix);
  }


  Map<Integer, Integer> memo;
  public int numWays(int n, int k) {
    this.memo = new HashMap<>();
    return getWays(n, k);
  }

  private int getWays(int n, int k) {
    if (n == 1) return k;
    if (n == 2) return k * k;

    if (memo.containsKey(n)) return memo.get(n);
    memo.put(n, (k - 1) * (getWays(n - 1, k) + getWays(n - 2, k)));
    return memo.get(n);
  }


  public int minDistance(String one, String two) {
    int m = one.length();
    int n = two.length();

    int[][] dp = new int[m + 1][n + 1];

    for (int i = 0; i <= m; i++) {
      for (int j = 0; j <= n; j++) {
        if (i == 0) {
          dp[i][j] = j;
        } else if (j == 0) {
          dp[i][j] = i;
        } else if (one.charAt(i - 1) == two.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1];
        } else {
          dp[i][j] = min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]) + 1;
        }
      }
    }
    return dp[m][n];
  }

  private int min(int a, int b, int c) {
    return Math.min(a, Math.min(b, c));
  }

  public int climbStairs(int n) {
    if (n == 1) return 1;
    if (n == 2) return 2;

    int prevOne = 1;
    int prevTwo = 2;
    int res = 0;
    for (int i = 3; i <= n; i++) {
      res = prevOne + prevTwo;
      prevOne = prevTwo;
      prevTwo = res;
    }
    return res;
  }

  public int minPathSum(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;
    for (int i = 1; i < m; i++) {
      grid[i][0] += grid[i - 1][0];
    }
    for (int i = 1; i < n; i++) {
      grid[0][i] += grid[0][i - 1];
    }

    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        grid[i][j] += Math.min(grid[i - 1][j], grid[i][j - 1]);
      }
    }
    return grid[m - 1][n - 1];
  }

  public int uniquePathsWithObstacles(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;
    if (grid[0][0] == 1) return 0;

    grid[0][0] = 1;
    for (int i = 1; i < m; i++) {
      grid[i][0] = (grid[i][0] == 0 && grid[i - 1][0] == 1) ? 1 : 0;
    }
    for (int i = 1; i < n; i++) {
      grid[0][i] = (grid[0][i] == 0 && grid[0][i - 1] == 1) ? 1 : 0;
    }

    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        if (grid[i][j] == 0) {
          grid[i][j] = grid[i - 1][j] + grid[i][j - 1];
        } else {
          grid[i][j] = 0;
        }
      }
    }
    return grid[m - 1][n - 1];
  }

  public int uniquePaths(int m, int n) {
    if (m == 1 || n == 1) return 1;

    int[][] memo = new int[m][n];
    for (int i = 0; i < m; i++) {
      memo[i][0] = 1;
    }
    for (int i = 0; i < n; i++) {
      memo[0][i] = 1;
    }
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        memo[i][j] = memo[i - 1][j] + memo[i][j - 1];
      }
    }
    return memo[m - 1][n - 1];
  }


  public boolean canJump(int[] nums) {
    if (nums == null || nums.length <= 1) return true;
    int m = nums.length;
    int max = 0;
    for (int i = 0; i < m; i++) {
      if (i == m - 1) return true;
      max = Math.max(max, i + nums[i]);
      if (max == i) return false;
    }
    return true;
  }

  public int maxSubArray(int[] nums) {
    int m = nums.length;
    int prev = nums[0];
    int max = nums[0];
    for (int i = 1; i < m; i++) {
        int cur = Math.max(prev, 0) + nums[i];
        max = Math.max(max, cur);
        prev = cur;
    }
    return max;
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

  public boolean isMatch(String s, String p) {
    int m = s.length();
    int n = p.length();
    boolean[][] memo = new boolean[m + 1][n + 1];
    memo[0][0] = true;
    for (int i = 0; i < n; i++) {
      if (p.charAt(i) == '*') memo[0][i + 1] = memo[0][i];
    }

    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (p.charAt(j) == '*') {
          memo[i][j] = memo[i - 1][j] || memo[i][j - 1];
        } else if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?') {
          memo[i][j] = memo[i - 1][j - 1];
        }
      }
    }
    return memo[m][n];
  }

  public int trap(int[] height) {
    if (height == null || height.length == 0) return 0;
    int m = height.length;
    int l = 0;
    int r = m - 1;
    int lMax = 0;
    int rMax = 0;
    int res = 0;

    while (l < r) {
      lMax = Math.max(lMax, height[l]);
      rMax = Math.max(rMax, height[r]);
      int curLevel = Math.min(lMax, rMax);
      res += Math.max(curLevel - height[l], 0);
      res += Math.max(curLevel - height[r], 0);
      if (height[l] <= height[r]) {
        l++;
      } else {
        r--;
      }
    }
    return res;
  }

  public int longestValidParentheses(String s) {
    if (s == null || s.length() == 0) return 0;
    int n = s.length();
    int max = 0;
    Deque<Integer> stack = new ArrayDeque<>();
    stack.offerFirst(-1);
    for (int i = 0; i < n; i++) {
      char ch = s.charAt(i);
      if (ch == '(') {
        stack.offerFirst(i);
      } else {
        stack.pollFirst();
        if (stack.isEmpty()) {
          stack.offerFirst(i);
        } else {
          max = Math.max(max, i - stack.peekFirst());
        }
      }
    }
    return max;
  }

  public List<String> generateParenthesis(int n) {
    List<String> res = new ArrayList<>();
    if (n == 0) return res;
    generate(0, 0, n, new StringBuilder(), res);
    return res;
  }

  private void generate(int l, int r, int n, StringBuilder prefix, List<String> res) {
    if (l == n && r == n) {
      res.add(new String(prefix));
      return;
    }
    if (l < n) {
      prefix.append("(");
      generate(l + 1, r, n, prefix, res);
      prefix.deleteCharAt(prefix.length() - 1);
    }

    if (l > r) {
      prefix.append(")");
      generate(l, r + 1, n, prefix, res);
      prefix.deleteCharAt(prefix.length() - 1);
    }

  }




}
