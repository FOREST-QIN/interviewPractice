package jan.eighteen;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

  /*

  1 1 1 0 6
0 1 2 3 4 5  m[i] represents when current substring's length is i, how many ways could decode it
1 1 2 3 3 3
2 + 1
if s.charAt(0) == 0, return 0;
single character * m[i - 1]
+ two if valid two * m[i - 2];
single num: [1,9]
double digits [10, 26]

 1 1 4 0 6
1 1 2 3 0

  2 6 1 1 0 5 5 9 7 1 7 5 6 5 6 2
1 1 2

   */

  @Test
  public void test() {
    int res = numDecodings("2611055971756562");
    System.out.println(res);
  }

  

  public List<TreeNode> generateTrees(int n) {
    if (n == 0) return new ArrayList<>();
    this.memo = new HashMap<>();
    return build(1, n);
  }

  Map<int[], List<TreeNode>> memo;
  private List<TreeNode> build(int lo, int hi) {
    List<TreeNode> res = new ArrayList<>();
    if (lo > hi) {
      res.add(null);
      return res;
    }
    int[] cur = new int[]{lo, hi};
    if (memo.containsKey(cur)) return memo.get(cur);

    for (int i = lo; i <= hi; i++) {
      List<TreeNode> left = build(lo, i - 1);
      List<TreeNode> right = build(i + 1, hi);
      for (TreeNode l : left) {
        for (TreeNode r : right) {
          TreeNode root = new TreeNode(i);
          root.left = l;
          root.right = r;
          res.add(root);
        }
      }
    }
    memo.put(cur, res);
    return res;
  }


  public int numTrees(int n) {
    // dp[i] how many ways when we have i nodes
    int[] dp = new int[n + 1];
    dp[0] = 1;
    dp[1] = 1;
    for (int i = 2; i <= n; i++) {
      for (int j = 0; j < i; j++) {
        dp[i] += dp[j] * dp[i - j - 1];
      }
    }
    return dp[n];
  }

  public boolean isInterleave(String one, String two, String combo) {
    if (one.length() + two.length() != combo.length()) return false;
    int m = one.length();
    int n = two.length();
    boolean[] dp = new boolean[n + 1];
    for (int i = 0; i <= m; i++) {
      for (int j = 0; j <= n; j++) {
        if (i == 0 && j == 0) {
          dp[i] = true;
        } else if (i == 0) {
          dp[j] = combo.charAt(i + j - 1) == two.charAt(j - 1) && dp[j - 1];
        } else if (j == 0) {
          dp[j] = combo.charAt(i + j - 1) == one.charAt(i - 1) && dp[j];
        } else {
          dp[j] = (combo.charAt(i + j - 1) == two.charAt(j - 1) && dp[j - 1])
              || (combo.charAt(i + j - 1) == one.charAt(i - 1) && dp[j]);
        }
      }
    }
    return dp[n];
  }


  public int numDecodings(String s) {
    if (s == null || s.length() == 0 || s.charAt(0) == '0') return 0;
    int m = s.length();
    int[] dp = new int[m + 1];
    dp[0] = 1;
    dp[1] = 1;
    for (int i = 2; i <= m; i++) {
      char cur = s.charAt(i - 1);
      char prev = s.charAt(i - 2);
      int ways = 0;
      // case one: single character
      if (cur >= '1' && cur <= '9') {
        ways += dp[i - 1];
      }
      // double characters
      if ((prev == '1' && (cur >= '0' && cur <= '9')) || (prev == '2' && (cur >= '0' && cur <= '6'))) {
        ways += dp[i - 2];
      }
      dp[i] = ways;
    }
    return dp[m];
  }

  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
      this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }
  }

}
