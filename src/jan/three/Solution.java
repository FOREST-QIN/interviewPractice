package jan.three;

import java.util.*;

public class Solution {
  Map<Integer, Integer> memo;
  public int deepestLeavesSum(TreeNode root) {
    int res = 0;
    int i = 0;
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);
    while (!q.isEmpty()) {
      for (i = q.size() - 1, res = 0; i >= 0; i--) {
        TreeNode cur = q.poll();
        res += cur.val;
        if (cur.left != null) q.offer(cur.left);
        if (cur.right != null) q.offer(cur.right);
      }
    }
    return res;
  }


  public int minDepth(TreeNode root) {
    if (root == null) return 0;
    if (root.right == null && root.left == null) return 1;

    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);
    int step = 1;
    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        TreeNode cur = q.poll();
        if (cur.left == null && cur.right == null) return step;
        if (cur.left != null) q.offer(cur.left);
        if (cur.right != null) q.offer(cur.right);
      }
      step++;
    }
    return step;
  }


  int min;
  long res;
  public int findSecondMinimumValue(TreeNode root) {
    this.min = root.val;
    this.res = Long.MAX_VALUE;
    dfs(root);
    return res < Long.MAX_VALUE ? (int) res : -1;
  }

  private void dfs(TreeNode root) {
    if (root != null) {
      if (min < root.val && root.val < res) res = root.val;
      else if (min == root.val) {
        dfs(root.left);
        dfs(root.right);
      }
    }
  }



  private int dis(TreeNode root) {
    if (root == null) return 0;
    int left = dis(root.left);
    int right = dis(root.right);

    res += Math.abs(left) + Math.abs(right);
    return root.val + left + right - 1;
  }


  public void flatten(TreeNode root) {
    if (root == null) return;
    flatten(root.right);
    flatten(root.left);

    TreeNode l = root.left;
    TreeNode r = root.right;

    root.left = null;
    root.right = l;

    TreeNode cur = root;
    while (cur.right != null) cur = cur.right;
    cur.right = r;
  }

  public TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    TreeNode tmp = root.left;
    root.left = root.right;
    root.right = tmp;

    invertTree(root.left);
    invertTree(root.right);
    return root;
  }

  public boolean isSymmetric(TreeNode root) {
    if (root == null || (root.left == null && root.right == null)) return true;

    return isSymmetric(root.left, root.right);
  }

  private boolean isSymmetric(TreeNode l, TreeNode r) {
    if (l == null && r == null) return true;
    else if (l == null || r == null) return false;
    else if (l.val != r.val) return false;
    return isSymmetric(l.left, r.right) && isSymmetric(l.right, r.left);
  }


  public TreeNode deleteNode(TreeNode root, int key) {
    if (root == null) return null;
    if (root.val == key) {
      if (root.left == null) return root.right;
      if (root.right == null) return root.left;
      TreeNode min = getMin(root.right);
      root.right = deleteNode(root.right, min.val);
      min.left = root.left;
      min.right = root.right;
      root = min;
    } else if (root.val > key) {
      root.left = deleteNode(root.left, key);
    } else {
      root.right = deleteNode(root.right, key);
    }
    return root;
  }

  private TreeNode getMin(TreeNode root) {
    while (root.left != null) root = root.left;
    return root;
  }

  int count = 0;
  int target;
  Map<Integer, Integer> map;

  public int pathSum(TreeNode root, int targetSum) {
    this.target = targetSum;
    this.map = new HashMap<>();
    pathSumHelper(root, 0);
    return count;
  }

  private void pathSumHelper(TreeNode root, int curSum) {
    if (root == null) return;
    curSum += root.val;
    if (curSum == target) count++;
    count += map.getOrDefault(curSum - target, 0);
    map.put(curSum, map.getOrDefault(curSum, 0) + 1);
    pathSumHelper(root.left, curSum);
    pathSumHelper(root.right, curSum);
    map.put(curSum, map.get(curSum) - 1);
  }

  public Node construct(int[][] grid) {
    return helper(grid, 0, 0, grid.length);
  }

  private Node helper(int[][] grid, int x, int y, int len) {
    if (len == 1) return new Node(grid[x][y] != 0, true, null, null, null, null);

    Node root = new Node();
    Node topLeft = helper(grid, x, y, len / 2);
    Node topRight = helper(grid, x, y + len / 2, len / 2);
    Node bottomLeft = helper(grid, x + len / 2, y, len / 2);
    Node bottomRight = helper(grid, x + len / 2, y + len / 2, len / 2);

    if (topLeft.isLeaf
        && topRight.isLeaf
        && bottomLeft.isLeaf
        && bottomRight.isLeaf
        && topLeft.val == topRight.val
        && topRight.val == bottomLeft.val
        && bottomLeft.val == bottomRight.val) {
      root.isLeaf = true;
      root.val = topLeft.val;
    } else {
      root.topLeft = topLeft;
      root.topRight = topRight;
      root.bottomLeft = bottomLeft;
      root.bottomRight = bottomRight;
    }

    return root;
  }

  int index;

  public TreeNode bstFromPreorder(int[] preorder) {
    this.index = 0;
    return bstFromPreorder(preorder, Integer.MAX_VALUE);
  }

  private TreeNode bstFromPreorder(int[] array, int bound) {
    if (index == array.length || array[index] > bound) return null;
    TreeNode root = new TreeNode(array[index++]);
    root.left = bstFromPreorder(array, root.val);
    root.right = bstFromPreorder(array, bound);
    return root;
  }


  public List<Integer> boundaryOfBinaryTree(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) return res;

    res.add(root.val);
    fromLeftBoundary(root.left, res);
    addLeaves(root.left, res);
    addLeaves(root.right, res);
    fromRightBoundary(root.right, res);
    return res;
  }

  private void fromRightBoundary(TreeNode root, List<Integer> res) {
    if (root == null) return;

    if (root.right != null) {
      fromRightBoundary(root.right, res);
      res.add(root.val);
    } else if (root.left != null) {
      fromRightBoundary(root.left, res);
      res.add(root.val);
    }
  }

  private void fromLeftBoundary(TreeNode root, List<Integer> res) {
    if (root == null) return;

    if (root.left != null) {
      res.add(root.val);
      fromLeftBoundary(root.left, res);
    } else if (root.right != null) {
      res.add(root.val);
      fromLeftBoundary(root.right, res);
    }
  }

  private void addLeaves(TreeNode root, List<Integer> res) {
    if (root == null) return;
    addLeaves(root.left, res);
    if (root.left == null && root.right == null) res.add(root.val);
    addLeaves(root.right, res);
  }

  static class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;


    public Node() {
      this.val = false;
      this.isLeaf = false;
      this.topLeft = null;
      this.topRight = null;
      this.bottomLeft = null;
      this.bottomRight = null;
    }

    public Node(boolean val, boolean isLeaf) {
      this.val = val;
      this.isLeaf = isLeaf;
      this.topLeft = null;
      this.topRight = null;
      this.bottomLeft = null;
      this.bottomRight = null;
    }

    public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
      this.val = val;
      this.isLeaf = isLeaf;
      this.topLeft = topLeft;
      this.topRight = topRight;
      this.bottomLeft = bottomLeft;
      this.bottomRight = bottomRight;
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
