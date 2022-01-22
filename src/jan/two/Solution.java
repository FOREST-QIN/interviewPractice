package jan.two;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {

  public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
    Set<Integer> set = new HashSet<>();
    List<TreeNode> res = new ArrayList<>();
    for (int i : to_delete) set.add(i);
    if (!set.contains(root.val)) res.add(root);
    delNodes(root, set, res);
    return res;
  }

  private void delNodes(TreeNode root, Set<Integer> set, List<TreeNode> res) {
    if (root == null) return;

    delNodes(root.left, set, res);
    delNodes(root.right, set, res);

    if (root.left != null && set.contains(root.left.val)) root.left = null;
    if (root.right != null && set.contains(root.right.val)) root.right = null;

    if (set.contains(root.val)) {
      if (root.left != null) res.add(root.left);
      if (root.right != null) res.add(root.right);
    }
  }

  public int rangeSumBST(TreeNode root, int low, int high) {
    int[] res = new int[1];
    rangeSum(root, low, high, res);
    return res[0];
  }

  private void rangeSum(TreeNode root, int low, int high, int[] res) {
    if (root == null) return;
    if (root.val >= low && root.val <= high) res[0] += root.val;
    if (root.val < high) rangeSum(root.right, low, high, res);
    if (root.val > low) rangeSum(root.left, low, high, res);
  }



  public boolean isValidBST(TreeNode root) {
    if (root == null || (root.left == null && root.right == null)) return true;
    return isValidBST(root, null, null);
  }

  private boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
    if (root == null) return true;
    if (min != null && root.val <= min.val) return false;
    if (max != null && root.val >= max.val) return false;
    return isValidBST(root.left, min, root) && isValidBST(root.right, root, max);
  }

  TreeNode prev;
  TreeNode[] swap;
  public void recoverTree(TreeNode root) {
    prev = null;
    swap = new TreeNode[2];
    inorder(root);
    if (swap[0] != null && swap[1] != null) {
      int tmp = swap[0].val;
      swap[0].val = swap[1].val;
      swap[1].val = tmp;
    }
  }

  private void inorder(TreeNode root) {
    if (root == null) return;
    inorder(root.left);
    if (prev != null && prev.val > root.val) {
      if (swap[0] == null) {
        swap[0] = prev;
      }
      swap[1] = root;
    }
    prev = root;
    inorder(root.right);
  }


  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    int pVal = p.val;
    int qVal = q.val;

    TreeNode node = root;

    while (node != null) {
      int cur = node.val;
      if (cur > pVal && cur > qVal) {
        node = node.left;
      } else if (cur < pVal && cur < qVal) {
        node = node.right;
      } else {
        return node;
      }
    }
    return null;
  }

  public int kthSmallest(TreeNode root, int k) {
    int[] rank = new int[1];
    int[] res = new int[1];
    traverse(root, k, res, rank);
    return res[0];
  }

  private void traverse(TreeNode root, int k, int[] res, int[] rank) {
    if (root == null) return;
    traverse(root.left, k, res, rank);
    rank[0]++;
    if (rank[0] == k) {
      res[0] = root.val;
      return;
    }
    traverse(root.right, k, res, rank);
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
