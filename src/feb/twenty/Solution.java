package feb.twenty;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(3);
    root.left.left = new TreeNode(2);
    root.right.left = new TreeNode(2);
    root.right.right = new TreeNode(4);
    TreeNode res = removeLeafNodes(root, 2);
  }

  int res;
  public int distributeCoins(TreeNode root) {
    getCoins(root);
    return res;
  }

  private int getCoins(TreeNode root) {
    if (root == null) return 0;
    int left = getCoins(root.left);
    int right = getCoins(root.right);
    res += Math.abs(left) + Math.abs(right);
    return root.val + left + right - 1;
  }

  public int rob(TreeNode root) {
    int[] res = robTree(root);
    return Math.max(res[0], res[1]);
  }

  // [rob current node, not rob current node]
  public int[] robTree(TreeNode root) {
    if (root == null) return new int[]{0, 0};
    int[] left = robTree(root.left);
    int[] right = robTree(root.right);
    int[] res = new int[2];
    res[0] = root.val + left[1] + right[1];
    res[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
    return res;
  }


  public int minCameraCover(TreeNode root) {
    return (dfs(root) < 1 ? 1 : 0) + res;
  }

  private int dfs(TreeNode root) {
    if (root == null) return 2;
    int left = dfs(root.left);
    int right = dfs(root.right);
    if (left == 0 || right == 0) {
      res++;
      return 1;
    }
    return left == 1 || right == 1 ? 2 : 0;
  }

  int max;
  public int longestUnivaluePath(TreeNode root) {
    if (root == null) return 0;
    this.max = 0;
    getLen(root, root.val);
    return max;
  }

  private int getLen(TreeNode root, int val) {
    if (root == null) return 0;
    int left = getLen(root.left, root.val);
    int right = getLen(root.right, root.val);
    max = Math.max(max, left + right + 1);
    if (root.val != val) return 0;
    return Math.max(left, right) + 1;
  }



  public int diameterOfBinaryTree(TreeNode root) {
    if (root == null) return 0;
    diameter(root);
    return max;
  }

  private int diameter(TreeNode root) {
    if (root == null) return 0;
    int left = diameter(root.left);
    int right = diameter(root.right);
    max = Math.max(max, left + right);
    return 1 + Math.max(left, right);
  }



  public int maxPathSum(TreeNode root) {
    if (root == null) return 0;
    this.max = Integer.MIN_VALUE;
    maxPath(root);
    return max;
  }

  private int maxPath(TreeNode root) {
    if (root == null) return 0;
    int left = Math.max(maxPath(root.left), 0);
    int right = Math.max(maxPath(root.right), 0);
    int cur = root.val + left + right;
    max = Math.max(cur, max);
    return root.val + Math.max(left, right);
  }

  public int[] findFrequentTreeSum(TreeNode root) {
     this.max = 0;
     Map<Integer, Integer> map = new HashMap<>();
     find(root, map);
     int count = 0;
     for (int key : map.keySet()) {
       if (map.get(key) == max) count++;
     }
     int[] res = new int[count];
     int index = 0;
     for (int key : map.keySet()) {
       if (map.get(key) == max) res[index++] = key;
     }
     return res;
  }

  private int find(TreeNode root, Map<Integer, Integer> map) {
    if (root == null) return 0;
    int left = find(root.left, map);
    int right = find(root.right, map);
    int cur = left + right + root.val;
    map.put(cur, map.getOrDefault(cur, 0) + 1);
    max = Math.max(max, map.get(cur));
    return cur;
  }


  List<String> paths;

  public List<String> binaryTreePaths(TreeNode root) {
    this.paths = new ArrayList<>();
    if (root == null) return paths;
    binaryPaths(root, new StringBuilder());
    return paths;
  }

  private void binaryPaths(TreeNode root, StringBuilder prefix) {
    if (root == null) return;
    int tmp = prefix.length();
    // "abc2
    if (root.left == null && root.right == null) {
      prefix.append(root.val);
      paths.add(prefix.toString());
      prefix.delete(tmp, prefix.length());
      return;
    }
    prefix.append(root.val).append("->");
    binaryPaths(root.left, prefix);
    binaryPaths(root.right, prefix);
    prefix.delete(tmp, prefix.length());
  }




  public int sumNumbers(TreeNode root) {
    if (root == null) return 0;
    sumNumber(root, 0);
    return res;
  }

  private void sumNumber(TreeNode root, int cur) {
    if (root == null) return;
    if (root.left == null && root.right == null) {
      cur = cur * 10 + root.val;
      res += cur;
      return;
    }
    sumNumber(root.left, cur * 10 + root.val);
    sumNumber(root.right, cur * 10 + root.val);
  }


  Map<Integer, Integer> map;

  public int pathSum(TreeNode root, int targetSum) {
    if (root == null) return 0;
    this.map = new HashMap<>();
    map.put(0, 1);
    path(root, targetSum, 0);
    return res;
  }

  private void path(TreeNode root, int targetSum, int prefix) {
    if (root == null) return;
    int temp = prefix + root.val;
    if (map.containsKey(temp - targetSum)) res += map.get(temp - targetSum);
    map.put(temp, map.getOrDefault(temp, 0) + 1);
    path(root.left, targetSum, prefix + root.val);
    path(root.right, targetSum, prefix + root.val);
    map.put(temp, map.get(temp) - 1);
  }


  public boolean hasPathSum(TreeNode root, int targetSum) {
    return has(root, targetSum);
  }

  private boolean has(TreeNode root, int targetSum) {
    if (root == null) return false;
    if (targetSum < 0) return false;
    if (root.left == null && root.right == null && targetSum == 0) return true;

    boolean left = has(root.left, targetSum - root.val);
    if (left) return true;
    return has(root.right, targetSum - root.val);
  }


  public TreeNode removeLeafNodes(TreeNode root, int target) {
    if (root == null) return null;
    root.left = removeLeafNodes(root.left, target);
    root.right = removeLeafNodes(root.right, target);
    if (root.left == null && root.right == null && root.val == target) return null;
    return root;
  }


  public List<Integer> getRow(int rowIndex) {
    if (rowIndex == 0) return Arrays.asList(1);
    if (rowIndex == 1) return Arrays.asList(1, 1);
    List<Integer> prev = Arrays.asList(1, 1);
    for (int i = 2; i <= rowIndex; i++) {
      List<Integer> cur = new ArrayList<>();
      cur.add(1);
      for (int j = 1; j < prev.size(); j++) {
        cur.add(prev.get(j - 1) + prev.get(j));
      }
      cur.add(1);
      prev = cur;
    }
    return prev;
  }

  public TreeNode pruneTree(TreeNode root) {
    int res = prune(root);
    return res == 0 ? null : root;
  }

  private int prune(TreeNode root) {
    if (root == null) return 0;
    int left = prune(root.left);
    int right = prune(root.right);
    int cur = left + right;
    cur += root.val == 1 ? 1 : 0;
    if (left == 0) root.left = null;
    if (right == 0) root.right = null;
    return cur;
  }

  public boolean leafSimilar(TreeNode root1, TreeNode root2) {
    if (root1 == null && root2 == null) return true;
    if (root1 == null || root2 == null) return false;
    List<Integer> one = new ArrayList<>();
    List<Integer> two = new ArrayList<>();
    getLeaves(root1, one);
    getLeaves(root2, two);
    return one.equals(two);
  }

  private void getLeaves(TreeNode root, List<Integer> res) {
    if (root == null) return;
    if (root.left == null && root.right == null) {
      res.add(root.val);
      return;
    }
    getLeaves(root.left, res);
    getLeaves(root.right, res);
  }


  public List<List<Integer>> levelOrderBottom(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);

    while (!q.isEmpty()) {
      List<Integer> list = new ArrayList<>();
      int size = q.size();
      for (int i = 0; i < size; i++) {
        TreeNode cur = q.poll();
        list.add(cur.val);
        if (cur.left != null) q.offer(cur.left);
        if (cur.right != null) q.offer(cur.right);
      }
      res.add(list);
    }
    int i = 0;
    int j = res.size() - 1;

    while (i <= j) {
      List<Integer> tmp = res.get(i);
      res.set(i, res.get(j));
      res.set(j, tmp);
      i++;
      j--;
    }
    return res;
  }

  public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);

    while (!q.isEmpty()) {
      List<Integer> list = new ArrayList<>();
      int size = q.size();
      for (int i = 0; i < size; i++) {
        TreeNode cur = q.poll();
        list.add(cur.val);
        if (cur.left != null) q.offer(cur.left);
        if (cur.right != null) q.offer(cur.right);
      }
      res.add(list);
    }
    return res;
  }

  public boolean isUnivalTree(TreeNode root) {
    if (root == null) return true;
    int base = root.val;
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);

    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        TreeNode cur = q.poll();
        if (cur.val != base) return false;
        if (cur.left != null) q.offer(cur.left);
        if (cur.right != null) q.offer(cur.right);
      }
    }
    return true;
  }

  public boolean isSubtree(TreeNode root, TreeNode subRoot) {
    if (root == null && subRoot == null) return true;
    if (root == null || subRoot == null) return false;
    return isSub(root, subRoot);
  }

  private boolean isSub(TreeNode root, TreeNode subRoot) {
    if (root == null) return false;
    if (isSameTree(root, subRoot)) return true;
    return isSub(root.left, subRoot) || isSub(root.right, subRoot);
  }


  public boolean isSameTree(TreeNode p, TreeNode q) {
    if (p == null && q == null) {
      return true;
    } else if (p == null || q == null) {
      return false;
    } else if (p.val != q.val) {
      return false;
    }

    return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
  }


  int min = Integer.MAX_VALUE;

  public int minDepth(TreeNode root) {
    if (root == null) return 0;
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


  public int maxDepth(TreeNode root) {
    if (root == null) return 0;
    int left = maxDepth(root.left);
    int right = maxDepth(root.right);
    return Math.max(left, right) + 1;
  }

  public boolean isSymmetric(TreeNode root) {
    if (root == null) return true;
    return check(root.left, root.right);
  }

  private boolean check(TreeNode one, TreeNode two) {
    if (one == null && two == null) return true;
    if (one == null || two == null) return false;
    if (one.val != two.val) return false;
    return check(one.left, two.right) && check(one.right, two.left);
  }


  int deepestLevel = 0;
  int sum = 0;

  public int deepestLeavesSum(TreeNode root) {
    if (root == null) return 0;
    deepest(root, 0);
    return sum;
  }

  private void deepest(TreeNode root, int level) {
    if (root == null) return;
    if (root.left == null && root.right == null) {
      if (level == deepestLevel) {
        sum += root.val;
      } else if (level > deepestLevel) {
        deepestLevel = level;
        sum = root.val;
      }
      return;
    }
    deepest(root.left, level + 1);
    deepest(root.right, level + 1);
  }

  public List<List<Integer>> verticalTraversal(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    Map<Integer, List<Pair>> map = new HashMap<>();
    Queue<Pair> q = new LinkedList<>();
    q.offer(new Pair(root, 0, 0));
    int min = 0;

    while (!q.isEmpty()) {
      Pair cur = q.poll();
      min = Math.min(min, cur.col);
      map.computeIfAbsent(cur.col, k -> new ArrayList<>()).add(cur);
      if (cur.node.left != null) q.offer(new Pair(cur.node.left, cur.col - 1, cur.row + 1));
      if (cur.node.right != null) q.offer(new Pair(cur.node.right, cur.col + 1, cur.row + 1));
    }

    while (map.containsKey(min)) {
      List<Pair> cur = map.get(min++);
      Collections.sort(cur, (a, b) -> {
        if (a.row != b.row) return Integer.compare(a.row, b.row);
        return Integer.compare(a.node.val, b.node.val);
      });
      List<Integer> list = new ArrayList<>();
      for (Pair p : cur) list.add(p.node.val);
      res.add(list);
    }
    return res;
  }

  static class Pair {
    TreeNode node;
    int col;
    int row;

    public Pair(TreeNode node, int col, int row) {
      this.node = node;
      this.col = col;
      this.row = row;
    }
  }


  public List<Integer> postorder(Node root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) return res;
    postorder(root, res);
    return res;
  }

  private void postorder(Node root, List<Integer> res) {
    if (root == null) return;
    for (Node child : root.children) postorder(child, res);
    res.add(root.val);
  }

  public List<Integer> preorder(Node root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) return res;
    preorder(root, res);
    return res;
  }

  private void preorder(Node root, List<Integer> res) {
    if (root == null) return;
    res.add(root.val);
    for (Node child : root.children) preorder(child, res);
  }

  public List<List<Integer>> levelOrder(Node root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;

    Queue<Node> q = new LinkedList<>();
    q.offer(root);

    while (!q.isEmpty()) {
      int size = q.size();
      List<Integer> curLayer = new ArrayList<>();
      for (int i = 0; i < size; i++) {
        Node cur = q.poll();
        curLayer.add(cur.val);
        for (Node child : cur.children) q.offer(child);
      }
      res.add(curLayer);
    }
    return res;
  }

  public List<Integer> postorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    postorder(root, res);
    return res;
  }

  private void postorder(TreeNode root, List<Integer> res) {
    if (root == null) return;
    postorder(root.left, res);
    postorder(root.right, res);
    res.add(root.val);
  }

  public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    preorder(root, res);
    return res;
  }

  private void preorder(TreeNode root, List<Integer> res) {
    if (root == null) return;
    res.add(root.val);
    preorder(root.left, res);
    preorder(root.right, res);
  }


  public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    inorder(root, res);
    return res;
  }

  private void inorder(TreeNode root, List<Integer> res) {
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

  class Node {
    public int val;
    public List<Node> children;

    public Node() {
    }

    public Node(int _val) {
      val = _val;
    }

    public Node(int _val, List<Node> _children) {
      val = _val;
      children = _children;
    }
  }

}
