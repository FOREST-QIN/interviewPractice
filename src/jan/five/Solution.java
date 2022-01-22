package jan.five;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(7);
    root.left.left = new TreeNode(7);
    root.left.right = new TreeNode(-8);
    root.right = new TreeNode(0);

    int res = maxLevelSum(root);

  }

  int max;
  Map<Integer, Integer> map;
  public int maxLevelSum(TreeNode root) {
    this.map = new HashMap<>();
    this.max = 0;
    maxLevelSum(root, 1);

    int res = 0;
    int cur = Integer.MIN_VALUE;
    for (int i = 1; i <= max; i++) {
      if (map.get(i) > cur) {
        cur = map.get(i);
        res = i;
      }
    }
    return res;
  }

  private void maxLevelSum(TreeNode root, int level) {
    if (root == null) return;
    max = Math.max(level, max);
    map.put(level, map.getOrDefault(level, 0) + root.val);
    maxLevelSum(root.left, level + 1);
    maxLevelSum(root.right, level + 1);
  }

  public TreeNode correctBinaryTree(TreeNode root) {
    return correctBinaryTree(root, new HashSet<>());
  }

  private TreeNode correctBinaryTree(TreeNode root, Set<TreeNode> visited) {
    if (root == null || (root.right != null && visited.contains(root.right))) return null;
    visited.add(root);
    root.right = correctBinaryTree(root.right, visited);
    root.left = correctBinaryTree(root.left, visited);
    return root;
  }


  int res;

  public int goodNodes(TreeNode root) {
    this.res = 0;
    getGood(root, Integer.MIN_VALUE);
    return res;
  }

  private void getGood(TreeNode root, int max) {
    if (root == null) return;
    if (root.val >= max) res++;
    getGood(root.left, Math.max(root.val, max));
    getGood(root.right, Math.max(root.val, max));
  }

  public TreeNode findNearestRightNode(TreeNode root, TreeNode u) {
    if (root == null) return null;

    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);
    boolean flag = false;
    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        TreeNode cur = q.poll();
        if (cur == u) {
          if (i == size - 1) return null;
          flag = true;
        } else if (flag) {
          return cur;
        }
        if (cur.left != null) q.offer(cur.left);
        if (cur.right != null) q.offer(cur.right);
      }
    }
    return null;
  }


  public TreeNode removeLeafNodes(TreeNode root, int target) {
    if (root == null) return null;

    root.left = removeLeafNodes(root.left, target);
    root.right = removeLeafNodes(root.right, target);

    if (root.val == target) {
      if (root.left == null && root.right == null) return null;
    }
    return root;
  }

  public NodeCopy copyRandomBinaryTree(Node root) {
    Map<Node, NodeCopy> map = new HashMap<>();

    return copy(root, map);
  }

  private NodeCopy copy(Node root, Map<Node, NodeCopy> map) {
    if (root == null) return null;
    NodeCopy newRoot = map.get(root);
    if (newRoot == null) {
      newRoot = new NodeCopy(root.val);
      map.put(root, newRoot);
    }

    if (root.random != null) {
      NodeCopy rand = map.get(root.random);
      if (rand == null) {
        rand = new NodeCopy(root.random.val);
        map.put(root.random, rand);
      }
      newRoot.random = rand;
    }
    newRoot.left = copy(root.left, map);
    newRoot.right = copy(root.right, map);

    return newRoot;
  }


  static class NodeCopy {
    int val;
    NodeCopy left;
    NodeCopy right;
    NodeCopy random;

    NodeCopy() {
    }

    NodeCopy(int val) {
      this.val = val;
    }

    NodeCopy(int val, NodeCopy left, NodeCopy right, NodeCopy random) {
      this.val = val;
      this.left = left;
      this.right = right;
      this.random = random;
    }
  }

  static class Node {
    int val;
    Node left;
    Node right;
    Node random;

    Node() {
    }

    Node(int val) {
      this.val = val;
    }

    Node(int val, Node left, Node right, Node random) {
      this.val = val;
      this.left = left;
      this.right = right;
      this.random = random;
    }
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
