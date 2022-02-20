package feb.nineteen;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    int[][] board = {{1, 1}, {1, 0}};
    // [[0,0,0],[0,1,1]]
    //1
    //1
    //1
    int res = largestIsland(board);
  }



  public int numBusesToDestination(int[][] routes, int source, int target) {
    int n = routes.length;
    Map<Integer, Set<Integer>> toRoutes = new HashMap<>();
    for (int i = 0; i < n; i++) {
      for (int stop : routes[i]) {
        toRoutes.computeIfAbsent(stop, k -> new HashSet<>()).add(i);
      }
    }
    Queue<int[]> q = new LinkedList<>();
    q.offer(new int[]{source, 0});
    Set<Integer> visitedStop = new HashSet<>();
    visitedStop.add(source);
    boolean[] visitedRoutes = new boolean[n];

    while (!q.isEmpty()) {
      int[] cur = q.poll();
      int stop = cur[0];
      int step = cur[1];
      if (stop == target) return step;
      for (int route : toRoutes.get(stop)) {
        if (visitedRoutes[route]) continue;
        for (int next : routes[route]) {
          if (!visitedStop.contains(next)) {
            visitedStop.add(next);
            q.offer(new int[]{next, step + 1});
          }
        }
        visitedRoutes[route] = true;
      }
    }
    return -1;
  }


  public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
    // step 1: get tree node's parent and store it in a map
    List<Integer> res = new ArrayList<>();
    if (root == null || target == null) return res;
    // get parent
    Map<TreeNode, TreeNode> map = new HashMap<>();
    getParent(root, null, map);
    Set<TreeNode> visited = new HashSet<>();

    Queue<TreeNode> q = new LinkedList<>();
    q.offer(target);
    visited.add(target);
    int step = 0;
    while (!q.isEmpty()) {
      if (step == k) break;
      int size = q.size();
      for (int i = 0; i < size; i++) {
        TreeNode cur = q.poll();
        if (cur.left != null && !visited.contains(cur.left)) {
          q.offer(cur.left);
          visited.add(cur.left);
        }
        if (cur.right != null && !visited.contains(cur.right)) {
          q.offer(cur.right);
          visited.add(cur.right);
        }
        if (map.get(cur) != null && !visited.contains(map.get(cur))) {
          q.offer(map.get(cur));
          visited.add(map.get(cur));
        }
      }
      step++;
    }
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


  public int minMutation(String start, String end, String[] bank) {
    if (start.equals(end)) return 0;
    Set<String> dict = new HashSet<>();
    for (String mut : bank) dict.add(mut);
    if (!dict.contains(end)) return -1;

    char[] gene = {'A', 'C', 'G', 'T'};
    int step = 0;
    Queue<String> q = new LinkedList<>();
    q.offer(start);

    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        String cur = q.poll();
        if (cur.equals(end)) return step;
        char[] arr = cur.toCharArray();
        for (int j = 0; j < arr.length; j++) {
          char tmp = arr[j];
          for (char ch : gene) {
            if (ch == tmp) continue;
            arr[j] = ch;
            String newWord = new String(arr);
            if (dict.contains(newWord)) {
              q.offer(newWord);
              dict.remove(newWord);
            }
            arr[j] = tmp;
          }
        }
      }
      step++;
    }
    return -1;
  }


  public int findJudge(int n, int[][] trust) {
    int[] count = new int[n + 1];
    for (int[] t : trust) {
      count[t[0]]--;
      count[t[1]]++;
    }
    for (int i = 1; i <= n; i++) {
      if (count[i] == n - 1) return i;
    }
    return -1;
  }

  public boolean possibleBipartition(int n, int[][] dislikes) {
    int[][] graph = new int[n][n];
    for (int[] d : dislikes) {
      graph[d[0] - 1][d[1] - 1] = 1;
      graph[d[1] - 1][d[0] - 1] = 1;
    }
    int[] group = new int[n];
    for (int i = 0; i < n; i++) {
      if (group[i] == 0 && !isPossible(graph, group, i, 1)) return false;
    }
    return true;
  }

  private boolean isPossible(int[][] graph, int[] group, int i, int color) {
    group[i] = color;
    for (int next = 0; next < graph.length; next++) {
      if (graph[i][next] == 1) {
        if (group[next] == color) return false;
        if (group[next] == 0 && !isPossible(graph, group, next, -color)) return false;
      }
    }
    return true;
  }

  public boolean isBipartite(int[][] graph) {
    int n = graph.length;
    int[] colors = new int[n];
    for (int i = 0; i < n; i++) {
      if (colors[i] == 0 && !dfs(graph, colors, i, 1)) return false;
    }
    return true;
  }

  private boolean dfs(int[][] graph, int[] colors, int i, int color) {
    colors[i] = color;
    for (int nei : graph[i]) {
      if (colors[nei] == -color) continue;
      if (colors[nei] == color || !dfs(graph, colors, nei, -color)) return false;
    }
    return true;
  }


  public List<Integer> eventualSafeNodes(int[][] graph) {
    List<Integer> res = new ArrayList<>();
    int m = graph.length;
    int[] visited = new int[m];
    // 0 -> unvisited
    // 1 -> visiting
    // 2 -> safe
    for (int i = 0; i < m; i++) {
      if (isSafe(i, graph, visited)) res.add(i);
    }
    return res;
  }

  private boolean isSafe(int index, int[][] graph, int[] visited) {
    if (visited[index] == 2) return true;
    if (visited[index] == 1) return false;
    visited[index] = 1;
    for (int nei : graph[index]) {
      if (!isSafe(nei, graph, visited)) return false;
    }
    visited[index] = 2;
    return true;
  }


  public int[] findOrder(int n, int[][] prerequisites) {
    if (n == 0 || prerequisites == null) return new int[]{};
    int[] res = new int[n];
    int[] count = new int[n];
    Map<Integer, List<Integer>> map = new HashMap<>();
    for (int[] req : prerequisites) {
      int pre = req[1];
      int next = req[0];
      count[next]++;
      map.computeIfAbsent(pre, k -> new ArrayList<>()).add(next);
    }
    int index = 0;
    Queue<Integer> q = new LinkedList<>();
    for (int i = 0; i < n; i++) {
      if (count[i] == 0) q.offer(i);
    }

    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        int cur = q.poll();
        res[index++] = cur;
        if (map.containsKey(cur)) {
          for (int next : map.get(cur)) {
            count[next]--;
            if (count[next] == 0) q.offer(next);
          }
        }
      }
    }
    return index == n ? res : new int[]{};
  }

  public boolean canFinish(int n, int[][] prerequisites) {
    if (n == 0 || prerequisites == null || prerequisites.length == 0) return true;
    Map<Integer, List<Integer>> map = new HashMap<>();
    int[] count = new int[n];

    for (int[] req : prerequisites) {
      int pre = req[1];
      int next = req[0];
      count[next]++;
      map.computeIfAbsent(pre, k -> new ArrayList<>()).add(next);
    }

    Queue<Integer> q = new LinkedList<>();
    for (int i = 0; i < n; i++) {
      if (count[i] == 0) q.offer(i);
    }
    int took = 0;

    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        int cur = q.poll();
        took++;
        if (map.containsKey(cur)) {
          for (int next : map.get(cur)) {
            count[next]--;
            if (count[next] == 0) q.offer(next);
          }
        }
      }
    }
    return took == n;
  }


  int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
  int m;

  public int largestIsland(int[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    this.m = grid.length;
    int index = 2;
    int res = 0;
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] == 1) {
          int area = dfs(grid, i, j, index);
          map.put(index, area);
          res = Math.max(map.get(index), res);
          index++;
        }
      }
    }


    for (int i = 0; i < m; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] == 0) {
          res = Math.max(res, getNeighborArea(grid, i, j, map));
        }
      }
    }
    return res;
  }

  private int getNeighborArea(int[][] grid, int i, int j, Map<Integer, Integer> map) {
    int area = 1;
    Set<Integer> seen = new HashSet<>();
    for (int[] dir : DIRS) {
      if (isValid(i + dir[0], j + dir[1])) {
        int index = grid[i + dir[0]][j + dir[1]];
        if (!seen.contains(index)) {
          seen.add(index);
          area += map.getOrDefault(index, 0);
        }
      }
    }
    return area;
  }

  private boolean isValid(int x, int y) {
    return x >= 0 && y >= 0 && x < m && y < m;
  }

  private int dfs(int[][] grid, int x, int y, int index) {
    if (x < 0 || y < 0 || x >= m || y >= m || grid[x][y] != 1) return 0;
    int area = 1;
    grid[x][y] = index;
    for (int[] dir : DIRS) {
      area += dfs(grid, x + dir[0], y + dir[1], index);
    }
    return area;
  }

  public int maxDistance(int[][] grid) {
    if (grid == null || grid.length == 0) return -1;
    this.m = grid.length;
    Queue<int[]> q = new LinkedList<>();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < m; j++) {
        if (grid[i][j] == 1) {
          q.offer(new int[]{i, j});
          grid[i][j] = -1;
        }
      }
    }

    int max = -1;
    int step = 0;
    while (!q.isEmpty()) {
      step++;
      int size = q.size();
      for (int i = 0; i < size; i++) {
        int[] cur = q.poll();
        for (int[] dir : DIRS) {
          int r = cur[0] + dir[0];
          int c = cur[1] + dir[1];
          if (r >= 0 && c >= 0 && r < m && c < m && grid[r][c] == 0) {
            grid[r][c] = step;
            q.offer(new int[]{r, c});
            max = Math.max(step, max);
          }
        }
      }
    }
    return max;
  }

  int n;

  public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
    this.m = image.length;
    this.n = image[0].length;
    int startColor = image[sr][sc];
    boolean[][] visited = new boolean[m][n];
    flood(image, sr, sc, startColor, newColor, visited);
    return image;
  }

  private void flood(int[][] image, int i, int j, int startColor, int newColor, boolean[][] visited) {
    if (i < 0 || j < 0 || i >= m || j >= n || image[i][j] != startColor || visited[i][j]) return;
    image[i][j] = newColor;
    visited[i][j] = true;
    for (int[] dir : DIRS) {
      flood(image, i + dir[0], j + dir[1], startColor, newColor, visited);
    }
  }

  public int maxAreaOfIsland(int[][] grid) {
    this.m = grid.length;
    this.n = grid[0].length;
    int max = 0;

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] == 1) {
          max = Math.max(max, getMaxArea(grid, i, j));
        }
      }
    }
    return max;
  }

  private int getMaxArea(int[][] grid, int i, int j) {
    if (i < 0 || j < 0 || i >= m || j >= n || grid[i][j] == 0) return 0;
    int cur = 1;
    grid[i][j] = 0;

    for (int[] dir : DIRS) {
      cur += getMaxArea(grid, i + dir[0], j + dir[1]);
    }
    return cur;
  }

  public int findCircleNum(int[][] isConnected) {
    /*
    [[1,1,0]
     [1,1,0],
     [0,0,1]]

     */
    int n = isConnected.length;
    boolean[] visited = new boolean[n];
    int count = 0;
    for (int i = 0; i < n; i++) {
      if (!visited[i]) {
        count++;
        visited[i] = true;
        connect(isConnected, visited, i);
      }
    }
    return count;
  }

  private void connect(int[][] matrix, boolean[] visited, int cur) {
    for (int i = 0; i < matrix.length; i++) {
      if (matrix[cur][i] == 1 && !visited[i]) {
        visited[i] = true;
        connect(matrix, visited, i);
      }
    }
  }

  public int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    this.m = grid.length;
    this.n = grid[0].length;
    int res = 0;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] == '1') {
          res++;
          // dfs
          island(grid, i, j);
        }
      }
    }
    return res;
  }

  private void island(char[][] grid, int i, int j) {
    if (i < 0 || j < 0 || i >= m || j >= n || grid[i][j] == '0') return;
    grid[i][j] = '0';
    for (int[] dir : DIRS) {
      island(grid, i + dir[0], j + dir[1]);
    }
  }

  public Node copyRandomList(Node head) {
    if (head == null) return null;
    Map<Node, Node> map = new HashMap<>();
    return copy(head, map);
  }

  private Node copy(Node node, Map<Node, Node> map) {
    if (node == null) return null;
    if (map.containsKey(node)) return map.get(node);
    Node cur = new Node(node.val);
    map.put(node, cur);

    cur.next = copy(node.next, map);
    cur.random = copy(node.random, map);
    return cur;
  }

  public Node cloneGraph(Node node) {
    /*
    clone(Node node) return cloned node
          clone node
              /   \
           clone(neighbors)
    Map<Node, Node> key: original node, value: clone node
    Set<Node> record nodes I already copied
    a -> b
      <-

     base case: node is null, return null
                node is copied before, return map.get(node)
     current layer:
              create copy node
              put node into map
              for (sub : node.neighbors) copy.neighbors.add(su)

     */
    Map<Node, Node> map = new HashMap<>();
    return clone(node, map);
  }

  private Node clone(Node node, Map<Node, Node> map) {
    if (node == null) return null;
    if (map.containsKey(node)) return map.get(node);
    Node copy = new Node(node.val);

    return copy;
  }

  public int findNumberOfLIS(int[] nums) {
    int m = nums.length;
    int max = 0;
    int res = 0;
    int[] len = new int[m];
    int[] cnt = new int[m];

    for (int i = 0; i < m; i++) {
      len[i] = cnt[i] = 1;
      for (int j = 0; j < i; j++) {
        if (nums[i] > nums[j]) {
          if (len[i] == len[j] + 1) cnt[i] += cnt[j];
          if (len[i] < len[j] + 1) {
            len[i] = len[j] + 1;
            cnt[i] = cnt[j];
          }
        }
      }
      if (max == len[i]) res += cnt[i];
      if (max < len[i]) {
        max = len[i];
        res = cnt[i];
      }
    }
    return res;
  }

  public int lengthOfLIS(int[] nums) {
    int[] m = new int[nums.length];
    int len = 0;
    for (int num : nums) {
      int i = Arrays.binarySearch(m, 0, len, num);
      if (i < 0) i = -(i + 1);
      m[i] = num;
      if (i == len) len++;
    }
    return len;
  }

  /*
  "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
    cat sand dog
             i
               ""
  index 0     /    \
            cat    cats
           /     \
        cat sand
        /
      cat sand dog
                  i

  base case: i == s.length() represent I get a word break
              res.add(break word)
  recursion rule:
             index
                i[index, s.length())
                  set.contains(s[index, i])
                      word + s[index, i] + " "
                      call recurion,  i + 1,
                      word - " " - s[index + i]




   */
  public List<String> wordBreak(String s, List<String> wordDict) {
    List<String> res = new ArrayList<>();
    if (s == null || s.length() == 0) return res;
    Set<String> set = new HashSet<>(wordDict);
    StringBuilder prefix = new StringBuilder();
    wordBreak(s, 0, prefix, res, set);
    return res;
  }

  private void wordBreak(String s, int index, StringBuilder prefix, List<String> res, Set<String> dict) {
    if (index == s.length()) {
      res.add(prefix.substring(0, prefix.length() - 1));
      return;
    }
    for (int i = index; i < s.length(); i++) {
      if (dict.contains(s.substring(index, i + 1))) {
        prefix.append(s.substring(index, i + 1));
        prefix.append(" ");
        wordBreak(s, i + 1, prefix, res, dict);
        prefix.deleteCharAt(prefix.length() - 1);
        int size = i - index + 1;
        prefix.delete(prefix.length() - size, prefix.length());
      }
    }

  }

  static class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
      this.val = val;
      this.next = null;
      this.random = null;
    }
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
