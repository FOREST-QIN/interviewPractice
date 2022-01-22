package practice.decem.thirty;

import java.util.*;

public class Solution {

  /*
set



   */

  public List<List<Integer>> verticalOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;
    Map<Integer, ArrayList<Integer>> map = new HashMap<>();
    Queue<TreeNode> q = new LinkedList<>();
    Map<TreeNode, Integer> weight = new HashMap<>();

    q.offer(root);
    weight.put(root, 0);
    int min = 0;
    while (!q.isEmpty()) {
      TreeNode node = q.poll();
      int w = weight.get(node);
      if (!map.containsKey(w)) map.put(w, new ArrayList<>());
      map.get(w).add(node.val);
      if (node.left != null) {
        q.offer(node.left);
        weight.put(node.left, w - 1);
      }
      if (node.right != null) {
        q.offer(node.right);
        weight.put(node.right, w + 1);
      }
      min = Math.min(min, w);
    }
    while (map.containsKey(min)) res.add(map.get(min++));
    return res;
  }


  String NULL = "#";
  String SEP = ",";

  // Encodes a tree to a single string.
  public String serialize(TreeNode root) {
    StringBuilder sb = new StringBuilder();
    ser(root, sb);
    return sb.toString();
  }

  private void ser(TreeNode root, StringBuilder sb) {
    if (root == null) {
      sb.append(NULL).append(SEP);
      return;
    }
    sb.append(root.val).append(SEP);
    ser(root.left, sb);
    ser(root.right, sb);
  }

  // Decodes your encoded data to tree.
  public TreeNode deserialize(String data) {
    Queue<String> q = new LinkedList<>();
    for (String s : data.split(",")) q.offer(s);
    return des(q);
  }

  private TreeNode des(Queue<String> q) {
    if (q.isEmpty()) return null;
    String cur = q.poll();
    if (cur.equals(NULL)) return null;
    TreeNode root = new TreeNode(Integer.parseInt(cur));
    root.left = des(q);
    root.right = des(q);
    return root;
  }

  public int maxPathSum(TreeNode root) {
    if (root == null) return 0;
    int[] max = {Integer.MIN_VALUE};
    mP(root, max);
    return max[0];
  }

  private int mP(TreeNode root, int[] max) {
    if (root == null) return 0;
    int left = Math.max(mP(root.left, max), 0);
    int right = Math.max(mP(root.right, max), 0);

    max[0] = Math.max(max[0], left + right + root.val);
    return Math.max(left, right) + root.val;
  }


  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) return root;
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    if (left != null && right != null) return root;
    return left == null ? right : left;
  }

  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }

}
