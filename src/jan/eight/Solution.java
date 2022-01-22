package jan.eight;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solution {

  public int countServers(int[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    int m = grid.length;
    int n = grid[0].length;
    int[] row = new int[m];
    int[] col = new int[n];

    int total = 0;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] == 1) {
          total++;
          row[i]++;
          col[j]++;
        }
      }
    }

    for (int i = 0; i < m; i++) {
      if (row[i] != 1) continue;
      for (int j = 0; j < n; j++) {
        if (col[j] != 1) continue;
        if (grid[i][j] == 1) total--;
      }
    }
    return total;
  }

  public int[][] highestPeak(int[][] isWater) {
    if (isWater == null || isWater.length == 0) return isWater;
    int m = isWater.length;
    int n = isWater[0].length;
    int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    Queue<int[]> q = new LinkedList<>();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (isWater[i][j] == 1) {
          isWater[i][j] = 0;
          q.offer(new int[]{i, j});
        } else {
          isWater[i][j] = -1;
        }
      }
    }

    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        int[] cur = q.poll();
        int x = cur[0];
        int y = cur[1];
        for (int[] dir : DIRS) {
          int neiX = x + dir[0];
          int neiY = y + dir[1];
          if (neiX >= 0 && neiX < m && neiY >= 0 && neiY < n && isWater[neiX][neiY] < 0) {
            isWater[neiX][neiY] = isWater[x][y] + 1;
            q.offer(new int[]{neiX, neiY});
          }
        }
      }
    }
    return isWater;
  }


  public List<Integer> rightSideView(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) return res;
    dfs(root, 0, res);
    return res;
  }

  private void dfs(TreeNode root, int level, List<Integer> res) {
    if (root == null) return;
    if (res.size() == level) res.add(root.val);
    dfs(root.right, level + 1, res);
    dfs(root.left, level + 1, res);
  }


  int height;
  int cols;
  public List<List<String>> printTree(TreeNode root) {
    List<List<String>> res = new ArrayList<>();
    if (root == null) return res;
    this.height = getHeight(root) - 1;
    this.cols = (int) Math.pow(2, height + 1) - 1;

    for (int i = 0; i <= height; i++) {
      List<String> cur = new ArrayList<>();
      for (int j = 0; j < cols; j++) cur.add("");
      res.add(cur);
    }
    dfs(root, 0, (cols - 1) / 2, res);
    return res;
  }

  private void dfs(TreeNode root, int r, int c, List<List<String>> res) {
    if (root == null || r < 0 || r > height || c < 0 || c >= cols) return;
    List<String> cur = res.get(r);
    cur.set(c, String.valueOf(root.val));
    dfs(root.left, r + 1, c - (int) Math.pow(2, height - r - 1), res);
    dfs(root.right, r + 1, c + (int) Math.pow(2, height - r - 1), res);
  }

  private int getHeight(TreeNode root) {
    if (root == null) return 0;
    int left = getHeight(root.left);
    int right = getHeight(root.right);
    return Math.max(left, right) + 1;
  }

  public void wallsAndGates(int[][] rooms) {
    if (rooms == null || rooms.length == 0) return;
    int m = rooms.length;
    int n = rooms[0].length;

    int[][] DIRS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    Queue<int[]> q = new LinkedList<>();

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (rooms[i][j] == 0) {
          q.offer(new int[]{i, j});
        }
      }
    }
    /*
    [[4,-1,0,1],
    [3,2,1,-1],
    [1,-1,2,-1],
    [0,-1,3,4]]
     */
    while (!q.isEmpty()) {
      int[] cur = q.poll();
      int x = cur[0];
      int y = cur[1];
      for (int[] dir : DIRS) {
        int neiX = x + dir[0];
        int neiY = y + dir[1];

        if (neiX < 0 || neiX >= m || neiY < 0 || neiY >= n || rooms[neiX][neiY] != Integer.MAX_VALUE) continue;
        rooms[neiX][neiY] = rooms[x][y] + 1;
        q.offer(new int[]{neiX, neiY});
      }
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
