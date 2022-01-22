package jan.one;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    int res = maximumInvitations(new int[]{2,2,1,2});
  }

  public int maximumInvitations(int[] favorite) {
    /*
    0  1, 2  3
    2, 2, 1, 2

    0-2,
    1-2
    2-1
    3-2


    */
    if (favorite == null || favorite.length == 0) return 0;
    if (favorite.length == 1) return 1;
    int n = favorite.length;
    int max = 0;

    for (int i = 0; i < n; i++) {

      Set<Integer> visited = new HashSet<>();
      dfs(favorite, i, visited);
      int count = 0;

      max = Math.max(visited.size(), max);
    }
    return max;
  }

  private void dfs(int[] fav, int index, Set<Integer> visited) {
    if (visited.contains(index)) return;



    int des = fav[index];
    if (!visited.contains(des)) {
      visited.add(index);
      dfs(fav, des, visited);
    }
  }




  public boolean isValidBST(TreeNode root) {
    return isValidBST(root, null, null);
  }

  private boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
    if (root == null) return true;
    if (min != null && root.val <= min.val) return false;
    if (max != null && root.val >= max.val) return false;

    return isValidBST(root.left, min, root) && isValidBST(root.right, root, max);
  }

  public int goodNodes(TreeNode root) {
    int[] res = new int[1];
    helper(root, Integer.MIN_VALUE, res);
    return res[0];
  }

  private void helper(TreeNode root, int max, int[] res) {
    if (root == null) return;
    if (root.val >= max) res[0]++;
    max = Math.max(max, root.val);
    helper(root.left, max, res);
    helper(root.right, max, res);
  }


  public List<List<Integer>> findLeaves(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    getHeight(root, res);
    return res;
  }

  private int getHeight(TreeNode root, List<List<Integer>> res) {
    if (root == null) return -1;
    int left = getHeight(root.left, res);
    int right = getHeight(root.right, res);

    int curHeight = Math.max(left, right) + 1;

    if (curHeight == res.size()) res.add(new ArrayList<>());
    res.get(curHeight).add(root.val);
    return curHeight;
  }

  public int numTrees(int n) {
    int[] dp = new int[n + 1];
    dp[0] = 1;
    dp[1] = 1;

    for (int i = 2; i <= n; i++) {
      int count = 0;
      for (int j = 0; j < i; j++) {
        count += dp[j] * dp[i - j - 1];
        // 0, 2  1 1 2 0
      }
      dp[i] = count;
    }
    return dp[n];
  }


  Node first;
  Node last;

  public Node treeToDoublyList(Node root) {
    if (root == null) return root;
    dfs(root);
    last.right = first;
    first.left = last;
    return first;
  }


  private void dfs(Node root) {
    if (root == null) return;

    dfs(root.left);

    if (first == null) {
      first = root;
      last = root;
    } else {
      root.left = last;
      last.right = root;
      last = root;
    }

    dfs(root.right);
  }

  public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;
    Deque<TreeNode> s1 = new ArrayDeque<>();
    Deque<TreeNode> s2 = new ArrayDeque<>();

    s1.offerFirst(root);
    while (!s1.isEmpty()) {
      List<Integer> curLayer = new ArrayList<>();
      while (!s1.isEmpty()) {
        TreeNode cur = s1.pollFirst();
        curLayer.add(cur.val);
        if (cur.left != null) s2.offerFirst(cur.left);
        if (cur.right != null) s2.offerFirst(cur.right);
      }
      res.add(curLayer);
      curLayer = new ArrayList<>();
      while (!s2.isEmpty()) {
        TreeNode cur = s2.pollFirst();
        curLayer.add(cur.val);
        if (cur.right != null) s1.offerFirst(cur.right);
        if (cur.left != null) s1.offerFirst(cur.left);
      }
      if (!curLayer.isEmpty()) res.add(curLayer);
    }
    return res;
  }

  public List<Integer> rightSideView(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    rightView(root, res, 0);
    return res;
  }

  private void rightView(TreeNode cur, List<Integer> res, int depth) {
    if (cur == null) return;
    if (res.size() == depth) res.add(cur.val);
    rightView(cur.right, res, depth + 1);
    rightView(cur.left, res, depth + 1);
  }


  public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
    Map<TreeNode, TreeNode> map = new HashMap<>();
    getParent(root, null, map);

    Set<TreeNode> visited = new HashSet<>();
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(target);
    visited.add(target);
    int step = 0;

    while (!q.isEmpty() && step != k) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        TreeNode node = q.poll();
        if (node.left != null && !visited.contains(node.left)) {
          q.offer(node.left);
          visited.add(node.left);
        }
        if (node.right != null && !visited.contains(node.right)) {
          q.offer(node.right);
          visited.add(node.right);
        }
        if (map.get(node) != null && !visited.contains(map.get(node))) {
          q.offer(map.get(node));
          visited.add(map.get(node));
        }
      }
      step++;
    }
    List<Integer> res = new ArrayList<>();
    while (!q.isEmpty()) {
      res.add(q.poll().val);
    }
    return res;
  }

  private void getParent(TreeNode root, TreeNode parent, Map<TreeNode, TreeNode> map) {
    if (root == null) return;
    map.put(root, parent);
    getParent(root.left, root, map);
    getParent(root.right, root, map);
  }


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
      TreeNode cur = q.poll();
      int w = weight.get(cur);
      map.putIfAbsent(w, new ArrayList<>());
      map.get(w).add(cur.val);
      if (cur.left != null) {
        q.offer(cur.left);
        weight.put(cur.left, w - 1);
      }
      if (cur.right != null) {
        q.offer(cur.right);
        weight.put(cur.right, w + 1);
      }
      min = Math.min(min, w);
    }
    while (map.containsKey(min)) {
      res.add(map.get(min++));
    }
    return res;
  }

  public int maxPathSum(TreeNode root) {
    if (root == null) return 0;
    int[] max = {Integer.MIN_VALUE};
    maxPathSum(root, max);
    return max[0];
  }

  private int maxPathSum(TreeNode root, int[] max) {
    if (root == null) return 0;
    int left = Math.max(0, maxPathSum(root.left, max));
    int right = Math.max(0, maxPathSum(root.right, max));
    max[0] = Math.max(max[0], left + right + root.val);
    return Math.max(left, right) + root.val;
  }

  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == q || root == p) return root;
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

  static class Node {
    public int val;
    public Node left;
    public Node right;

    public Node() {}

    public Node(int _val) {
      val = _val;
    }

    public Node(int _val,Node _left,Node _right) {
      val = _val;
      left = _left;
      right = _right;
    }
  }

}
