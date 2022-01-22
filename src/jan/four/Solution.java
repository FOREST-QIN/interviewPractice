package jan.four;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    int n = 6;
    int[][] edges = {{0, 1}, {0,2}, {2, 3}, {2,4}, {2,5}};
    int[] res = sumOfDistancesInTree(n, edges);
    System.out.println(Arrays.toString(res));
  }

  List<Integer> flipped;
  int index;
  int[] voyage;

  public List<Integer> flipMatchVoyage(TreeNode root, int[] voyage) {
    this.flipped = new ArrayList<>();
    this.index = 0;
    this.voyage = voyage;
    dfs(root);
    if (!flipped.isEmpty() && flipped.get(0) == -1) {
      flipped.clear();
      flipped.add(-1);
    }
    return flipped;
  }

  private void dfs(TreeNode node) {
    if (node != null) {
      if (node.val != voyage[index++]) {
        flipped.clear();
        flipped.add(-1);
        return;
      }
      if (index < voyage.length && node.left != null && node.left.val != voyage[index]) {
        flipped.add(node.val);
        dfs(node.right);
        dfs(node.left);
      } else {
        dfs(node.left);
        dfs(node.right);
      }
    }
  }

  String NULL = "#";
  String SEP = ",";

  public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
    Map<String, Integer> memo = new HashMap<>();
    List<TreeNode> res = new ArrayList<>();
    traverse(root, memo, res);
    return res;
  }

  private String traverse(TreeNode root, Map<String, Integer> memo, List<TreeNode> res) {
    if (root == null) return NULL;
    String left = traverse(root.left, memo, res);
    String right = traverse(root.right, memo, res);
    String cur = left + SEP + right + SEP + root.val;
    int freq = memo.getOrDefault(cur, 0);
    if (freq == 1) res.add(root);
    memo.put(cur, memo.getOrDefault(cur, 0) + 1);
    return cur;
  }


  int[] res;
  int[] count;
  List<HashSet<Integer>> tree;

  public int[] sumOfDistancesInTree(int n, int[][] edges) {
    this.tree = new ArrayList<>();
    this.res = new int[n];
    this.count = new int[n];

    for (int i = 0; i < n; i++) tree.add(new HashSet<>());

    for (int[] edge : edges) {
      tree.get(edge[0]).add(edge[1]);
      tree.get(edge[1]).add(edge[0]);
    }
    dfs(0, -1);
    dfs2(0, -1);
    return res;
  }

  private void dfs(int root, int pre) {
    for (int i : tree.get(root)) {
      if (i == pre) continue;
      dfs(i, root);
      count[root] += count[i];
      res[root] += res[i] + count[i];
    }
    count[root]++;
  }

  private void dfs2(int root, int pre) {
    for (int i : tree.get(root)) {
      if (i == pre) continue;
      res[i] = res[root] - count[i] + count.length - count[i];
      dfs2(i, root);
    }
  }



  public List<Integer> closestKValues(TreeNode root, double target, int k) {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> Math.abs(a - target) < Math.abs(b - target) ? -1 : 1);
    inorder(root, minHeap, k);
    return new ArrayList<>(minHeap);
  }

  private void inorder(TreeNode root, PriorityQueue<Integer> min, int k) {
    if (root == null) return;
    inorder(root.left, min, k);
    min.offer(root.val);
    if (min.size() > k) min.poll();
    inorder(root.right, min, k);
  }


  public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;

    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);

    while (!q.isEmpty()) {
      List<Integer> curLayer = new ArrayList<>();
      int size = q.size();
      for (int i = 0; i < size; i++) {
        TreeNode node = q.poll();
        curLayer.add(node.val);
        if (node.left != null) q.offer(node.left);
        if (node.right != null) q.offer(node.right);
      }
      res.add(curLayer);
    }
    return res;
  }

  public TreeNode canMerge(List<TreeNode> trees) {
    Map<Integer, TreeNode> map = new HashMap<>();
    Map<Integer, Integer> count = new HashMap<>();
    for (TreeNode node : trees) {
      map.put(node.val, node);
      count.put(node.val, count.getOrDefault(node.val, 0) + 1);
      if (node.left != null) {
        count.put(node.left.val, count.getOrDefault(node.left.val, 0) + 1);
      }
      if (node.right != null) {
        count.put(node.right.val, count.getOrDefault(node.right.val, 0) + 1);
      }
    }

    for (TreeNode node : trees) {
      if (count.get(node.val) == 1) {
        return merge(node, map, Integer.MIN_VALUE, Integer.MAX_VALUE) && map.size() == 1 ? node : null;
      }
    }
    return null;
  }

  private boolean merge(TreeNode root, Map<Integer, TreeNode> map, int min, int max) {
    if (root == null) return true;
    if (root.val <= min || root.val >= max) return false;
    if (root.left == null && root.right == null) {
      if (map.containsKey(root.val) && map.get(root.val) != root) {
        TreeNode tmp = map.get(root.val);
        root.left = tmp.left;
        root.right = tmp.right;
        map.remove(root.val);
      }
    }
    return merge(root.left, map, min, root.val) && merge(root.right, map, root.val, max);
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
