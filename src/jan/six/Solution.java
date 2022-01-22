package jan.six;

import org.junit.jupiter.api.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    /*
    "593290172167"
7
4
     */
    String s =  "593290172167";
    int a = 7;
    int b = 4;

    String res = findLexSmallestString(s, a, b);
    System.out.println(res);
  }

  public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
    List<Integer> res = new ArrayList<>();
    if (pid.isEmpty()) return res;

    Map<Integer, List<Integer>> tree = new HashMap<>();
    int m = pid.size();

    for (int i = 0; i < m; i++) {
      int child = pid.get(i);
      int parent = ppid.get(i);
      tree.putIfAbsent(parent, new ArrayList<>());
      tree.get(parent).add(child);
    }

    Queue<Integer> q = new LinkedList<>();
    q.offer(kill);
    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        int cur = q.poll();
        if (tree.containsKey(cur)) {
          for (int nei : tree.get(cur)) {
            q.offer(nei);
          }
        }
        res.add(cur);
      }
    }

    return res;
  }

  int[][] DIRS = {{0, 1},{1, 0}};
  int endRow = 0;
  int endCol = 0;
  public int[][] findFarmland(int[][] land) {
    int m = land.length;
    int n = land[0].length;
    List<int[]> res = new ArrayList<>();
    int index = 0;

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (land[i][j] == 1) {
          int[] cur = new int[4];

          cur[0] = i;
          cur[1] = j;
          findFarmland(land, i, j);
          cur[2] = endRow;
          cur[3] = endCol;
          res.add(cur);
          endRow = endCol = 0;
        }
      }
    }
    int[][] result = new int[res.size()][];
    for (int i = 0; i < res.size(); i++) {
      result[i] = res.get(i);
    }
    return result;
  }

  private void findFarmland(int[][] land, int i, int j) {
    if (i < 0 || j < 0 || i >= land.length || j >= land[0].length || land[i][j] == 0) return;

    land[i][j] = 0;
    endRow = Math.max(endRow, i);
    endCol = Math.max(endCol, j);
    for (int[] dir : DIRS) {
      findFarmland(land, i + dir[0], j + dir[1]);
    }
  }



  public String findLexSmallestString(String s, int a, int b) {
    if (s == null || s.length() == 0) return s;
    int size = s.length();
    b %= size;

    String res = s;
    Set<String> memo = new HashSet<>();

    Queue<String> q = new LinkedList<>();
    q.offer(s);
    memo.add(s);

    while (!q.isEmpty()) {
      String cur = q.poll();

      String add = add(cur, a);
      String rotate = rotate(cur, b);

      if (!memo.contains(add)) {

        if (res.compareTo(add) > 0) {
          res = add;
        }
        memo.add(add);
        q.offer(add);
      }
      if (!memo.contains(rotate)) {

        if (res.compareTo(rotate) > 0) {
          res = rotate;
        }
        memo.add(rotate);
        q.offer(rotate);
      }
    }
    return res;
  }

  private String add(String s, int a) {
    char[] array = s.toCharArray();

    for (int i = 1; i < s.length(); i += 2) {
      int digit = array[i] - '0';
      digit += a;
      digit %= 10;
      array[i] = (char) (digit + '0');
    }
    return String.valueOf(array);
  }

  private String rotate(String s, int b) {
    char[] array = s.toCharArray();
    reverse(array, 0, b - 1);
    reverse(array, b, array.length - 1);
    reverse(array, 0, array.length - 1);
    return String.valueOf(array);
  }

  private void reverse(char[] array, int i, int j) {
    while (i <= j) {
      swap(array, i++, j--);
    }
  }

  private void swap(char[] array, int i, int j) {
    char tmp = array[i];
    array[i] = array[j];
    array[j] = tmp;
  }

  public int maxLevelSum(TreeNode root) {
    if (root == null) return 0;
    if (root.left == null && root.right == null) return 1;

    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);

    int level = 1;
    int max = Integer.MIN_VALUE;
    int index = 0;

    while (!q.isEmpty()) {
      int size = q.size();
      int sum = 0;

      for (int i = 0; i < size; i++) {
        TreeNode cur = q.poll();
        sum += cur.val;
        if (cur.left != null) q.offer(cur.left);
        if (cur.right != null) q.offer(cur.right);
      }
      if (sum > max) {
        max = sum;
        index = level;
      }
      level++;
    }
    return index;
  }


  int maxHeight;
  TreeNode res;
  public TreeNode subtreeWithAllDeepest(TreeNode root) {
    this.maxHeight = getMaxHeight(root);
    getRes(root, 0);
    return res;
  }

  private int getRes(TreeNode root, int level) {
    if (root == null) return level;
    int left = getRes(root.left, level + 1);
    int right = getRes(root.right, level + 1);

    if (left == maxHeight && right == maxHeight) res = root;

    return Math.max(left, right);
  }


  private int getMaxHeight(TreeNode root) {
    if (root == null) return 0;
    int left = getMaxHeight(root.left);
    int right = getMaxHeight(root.right);
    return Math.max(left, right) + 1;
  }


  public int pseudoPalindromicPaths (TreeNode root) {
    return dfs(root, 0);
  }

  private int dfs(TreeNode root, int count) {
    if (root == null) return 0;
    count ^= 1 << (root.val - 1);
    int res = dfs(root.left, count) + dfs(root.right, count);
    if (root.left == root.right && (count & (count - 1)) == 0) res++;
    return res;
  }




  public boolean canVisitAllRooms(List<List<Integer>> rooms) {
    int n = rooms.size();
    boolean[] visited = new boolean[n];
    dfs(rooms.get(0), 0, rooms, visited);
    for (int i = 0; i < n; i++) {
      if (!visited[i]) return false;
    }
    return true;
  }

  private void dfs(List<Integer> keys, int room, List<List<Integer>> rooms, boolean[] visited) {
    visited[room] = true;
    for (int key : keys) {
      if (!visited[key]) {
        dfs(rooms.get(key), key, rooms, visited);
      }
    }
  }


  public List<List<Integer>> levelOrder(Node root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;

    Queue<Node> q = new LinkedList<>();
    q.offer(root);
    while (!q.isEmpty()) {
      List<Integer> curLayer = new ArrayList<>();
      int size = q.size();
      for (int i = 0; i < size; i++) {
        Node cur = q.poll();
        for (Node child : cur.children) {
          q.offer(child);
        }
        curLayer.add(cur.val);
      }
      res.add(curLayer);
    }
    return res;
  }


  int m;
  int n;

  public int maxAreaOfIsland(int[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    this.m = grid.length;
    this.n = grid[0].length;
    int res = 0;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] == 1) {
          res = Math.max(res, dfs(grid, i, j));
        }
      }
    }
    return res;
  }

  private int dfs(int[][] grid, int i, int j) {
    if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] == 0) return 0;
    grid[i][j] = 0;
    return
        dfs(grid, i + 1, j) +
            dfs(grid, i - 1, j) +
            dfs(grid, i, j + 1) +
            dfs(grid, i, j - 1) + 1;
  }

  public int findDistance(TreeNode root, int p, int q) {
    if (root == null) return 0;
    TreeNode lca = findLCA(root, p, q);
    int left = getDis(lca, p, 0);
    int right = getDis(lca, q, 0);
    return left + right;
  }


  private int getDis(TreeNode root, int target, int dis) {
    if (root == null) return 0;
    if (root.val == target) return dis;
    int left = getDis(root.left, target, dis + 1);
    int right = getDis(root.right, target, dis + 1);
    return left == 0 ? right : left;
  }

  private TreeNode findLCA(TreeNode root, int p, int q) {
    if (root == null || root.val == p || root.val == q) return root;
    TreeNode left = findLCA(root.left, p, q);
    TreeNode right = findLCA(root.right, p, q);
    if (left != null && right != null) return root;
    return left == null ? right : left;
  }

  static class Node {
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
