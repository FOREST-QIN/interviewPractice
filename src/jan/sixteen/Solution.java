package jan.sixteen;

import java.util.ArrayList;
import java.util.List;

public class Solution {


  public boolean isMatch(String s, String p) {
    if (p == null || p.length() == 0) return s == null || s.length() == 0;
    int m = s.length();
    int n = p.length();
    boolean[][] memo = new boolean[m + 1][n + 1];
    memo[0][0] = true;
    for (int j = 2; j <= n; j++) {
      if (p.charAt(j - 1) == '*') memo[0][j] = memo[0][j - 2];
    }
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.') {
          memo[i][j] = memo[i - 1][j - 1];
        } else if (p.charAt(j - 1) == '*') {
          memo[i][j] =
              memo[i][j - 2]
              || ((s.charAt(i - 1) == p.charAt(j - 2)) || p.charAt(j - 2) == '.') && memo[i - 1][j];
        }
      }
    }
    return memo[m][n];
  }




  public double findMedianSortedArrays(int[] n1, int[] n2) {
    /*
    total length is even
   total length is odd
     4
     2,3
     */
    int left = (n1.length + n2.length + 1) / 2;
    int right = (n1.length + n2.length + 2) / 2;
    int lRes = find(n1, 0, n2, 0, left);
    int rRes = find(n1, 0, n2, 0, right);
    return (lRes + rRes) / 2.0;
  }

  private int find(int[] n1, int i, int[] n2, int j, int k) {
    if (i >= n1.length) return n2[j + k - 1];
    if (j >= n2.length) return n1[i + k - 1];

    if (k == 1) return Math.min(n1[i], n2[j]);
    int one = (i + k / 2 - 1 >= n1.length) ? Integer.MAX_VALUE : n1[i + k / 2 - 1];
    int two = (j + k / 2 - 1 >= n2.length) ? Integer.MAX_VALUE : n2[j + k / 2 - 1];

    if (one <= two) {
      return find(n1, i + k / 2, n2, j, k - k / 2);
    } else {
      return find(n1, i, n2, j + k / 2, k - k / 2);
    }

  }


  public boolean isSameTree(TreeNode p, TreeNode q) {
    if (p == null && q == null) return true;
    if (p == null || q == null) return false;
    if (p.val != q.val) return false;

    return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
  }


  TreeNode prev;
  TreeNode[] swap;
  public void recoverTree(TreeNode root) {
    prev = null;
    swap = new TreeNode[2];
    recover(root);
    if (swap[0] != null && swap[1] != null) {
      int tmp = swap[0].val;
      swap[0].val = swap[1].val;
      swap[1].val = tmp;
    }
  }

  private void recover(TreeNode root) {
    if (root == null) return;
    recover(root.left);
    if (prev != null && prev.val > root.val) {
      if (swap[0] == null) {
        swap[0] = prev;
      }
      swap[1] = root;
    }
    prev = root;
    recover(root.right);
  }

  public boolean isValidBST(TreeNode root) {
    return isBST(root, null, null);
  }

  private boolean isBST(TreeNode root, Integer min, Integer max) {
    if (root == null) return true;
    if ((max != null && root.val >= max) ||(min != null &&  root.val <= min)) return false;
    boolean left = isBST(root.left, min, root.val);
    if (!left) return false;
    return isBST(root.right, root.val, max);
  }

  public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    inorder(root, res);
    return res;
  }

  private void inorder(TreeNode root, List<Integer> res) {
    // base case
    if (root == null) return;
    inorder(root.left, res);
    res.add(root.val);
    inorder(root.right, res);
  }

  static class TreeNode {
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
