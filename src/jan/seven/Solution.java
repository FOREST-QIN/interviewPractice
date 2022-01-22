package jan.seven;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    int nodes = 7;
    int[] parent = {-1, 0, 0, 1, 2, 2, 2};
    int[] value = {1, -2, 4, 0, -2, -1, -1};
    int res = deleteTreeNodes(nodes, parent, value);
    System.out.println(res);
  }

  public int deleteTreeNodes(int nodes, int[] parent, int[] value) {
    Map<Integer, Set<Integer>> tree = new HashMap<>();
    for (int i = 0; i < nodes; i++) {
      tree.putIfAbsent(nodes, new HashSet<>());
      tree.putIfAbsent(parent[i], new HashSet<>());
      tree.get(parent[i]).add(i);
    }
    return dfs(tree, 0, value)[1];
  }

  private int[] dfs(Map<Integer, Set<Integer>> tree, int index, int[] values) {
    int total = values[index];
    int count = 1;
    if (tree.containsKey(index)) {
      for (int child : tree.get(index)) {
        int[] next = dfs(tree, child, values);
        total += next[0];
        count += next[1];
      }
    }
    return new int[]{total, total == 0 ? 0 : count};
  }



  private void dfs(int[][] cities, int cur, boolean[] visited) {
    for (int i = 0; i < visited.length; i++) {
      if (cities[cur][i] == 1 && !visited[i]) {
        visited[i] = true;
        dfs(cities, i, visited);
      }
    }
  }


  int result = 0;
  Map<Integer, List<Integer>> graph;

  public int treeDiameter(int[][] edges) {
    this.graph = new HashMap<>();
    for (int[] edge : edges) {
      graph.putIfAbsent(edge[0], new ArrayList<>());
      graph.putIfAbsent(edge[1], new ArrayList<>());
      graph.get(edge[0]).add(edge[1]);
    }
    helper(0);
    return result;
  }

  public int helper(int node) {
    if (!graph.containsKey(node)) return 0;
    int max1 = 0;
    int max2 = 0;

    for (int child : graph.get(node)) {
      int tmp = helper(child);
      if (tmp > max1) {
        max2 = max1;
        max1 = tmp;
      } else if (tmp > max2) {
        max2 = tmp;
      }
    }
    result = Math.max(result, max1 + max2);
    return max1 + 1;
  }

  public boolean canReach(int[] arr, int start) {
    if (arr == null || arr.length == 0) return false;
    boolean[] visited = new boolean[arr.length];
    return canReach(arr, start, visited);
  }

  private boolean canReach(int[] arr, int index, boolean[] visited) {
    if (index < 0 || index >= arr.length || visited[index]) return false;
    if (arr[index] == 0) return true;

    visited[index] = true;

    return canReach(arr, index + arr[index], visited) || canReach(arr, index - arr[index], visited);
  }


  public int getImportance(List<Employee> employees, int id) {
    if (employees.isEmpty()) return 0;
    Map<Integer, List<Integer>> tree = new HashMap<>();
    Map<Integer, Integer> importance = new HashMap<>();
    for (Employee emp : employees) {
      int curId = emp.id;
      int curImp = emp.importance;
      importance.put(curImp, curImp);
      tree.putIfAbsent(curId, new ArrayList<>());
      for (int sub : emp.subordinates) tree.get(curId).add(sub);
    }

    int sum = 0;
    Queue<Integer> q = new LinkedList<>();
    q.offer(id);

    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        int cur = q.poll();
        sum += importance.get(cur);
        for (int sub : tree.get(cur)) q.offer(sub);
      }
    }

    return sum;
  }

  int m;
  int n;
  int[][] DIRS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

  public int closedIsland(int[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    this.m = grid.length;
    this.n = grid[0].length;
    for (int i = 0; i < m; i++) {
      dfs(grid, i, 0);
      dfs(grid, i, n - 1);
    }
    for (int i = 0; i < n; i++) {
      dfs(grid, 0, i);
      dfs(grid, m - 1, i);
    }

    int res = 0;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] == 0) {
          res++;
          dfs(grid, i, j);
        }
      }
    }
    return res;
  }

  private void dfs(int[][] grid, int i, int j) {
    if (i < 0 || j < 0 || i >= m || j >= n || grid[i][j] == 1) return;
    grid[i][j] = 1;
    for (int[] dir : DIRS) {
      dfs(grid, i + dir[0], j + dir[1]);
    }
  }

  public List<Integer> largestValues(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) return res;

    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);

    while (!q.isEmpty()) {
      int max = Integer.MIN_VALUE;
      int size = q.size();
      for (int i = 0; i < size; i++) {
        TreeNode cur = q.poll();
        max = Math.max(max, cur.val);
        if (cur.left != null) q.offer(cur.left);
        if (cur.right != null) q.offer(cur.right);
      }
      res.add(max);
    }
    return res;
  }

  public String[] expand(String s) {
    if (s == null || s.length() == 0) return null;
    List<List<Character>> list = preprocess(s);

    Set<String> set = new HashSet<>();
    StringBuilder sb = new StringBuilder();
    dfs(list, 0, sb, set);
    int i = 0;
    String[] res = new String[set.size()];
    for (String str : set) res[i++] = str;
    Arrays.sort(res);
    return res;
  }

  private void dfs(List<List<Character>> list, int index, StringBuilder sb, Set<String> set) {
    if (index == list.size()) {
      set.add(sb.toString());
      return;
    }

    for (char ch : list.get(index)) {
      sb.append(ch);
      dfs(list, index + 1, sb, set);
      sb.deleteCharAt(sb.length() - 1);
    }
  }

  private List<List<Character>> preprocess(String s) {
    List<List<Character>> list = new ArrayList<>();

    char[] array = s.toCharArray();

    int i = 0;
    while (i < array.length) {
      char ch = array[i];
      List<Character> curLayer = new ArrayList<>();
      if (ch == '{') {
        int end = i;
        while (end < array.length && array[end] != '}') end++;
        int[] chars = new int[26];
        for (int j = i + 1; j < end; j++) {
          if (Character.isLowerCase(array[j])) chars[array[j] - 'a']++;
        }
        for (int j = 0; j < 26; j++) {
          if (chars[j] != 0) curLayer.add((char) (j + 'a'));
        }
        i = end + 1;
        list.add(curLayer);
      } else if (Character.isLowerCase(array[i])) {
        curLayer.add(array[i]);
        i++;
        list.add(curLayer);
      } else {
        i++;
      }
    }

    return list;
  }

  int maxLevel;
  int res;

  public int findBottomLeftValue(TreeNode root) {
    this.maxLevel = 0;
    this.res = root.val;
    findMostLeft(root, 0);
    return res;
  }

  private void findMostLeft(TreeNode root, int level) {
    if (root == null) return;

    findMostLeft(root.left, level + 1);
    if (level > maxLevel) {
      maxLevel = level;
      res = root.val;
    }
    findMostLeft(root.right, level + 1);
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

  static class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;
  }
}
