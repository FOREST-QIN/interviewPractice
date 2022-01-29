package jan.twentyEight;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    int[] arr = new int[]{1, 2, 3, 4};
    int[] res = productExceptSelf(arr);
  }

  public int[] findBuildings(int[] heights) {
    if (heights == null || heights.length == 0) return heights;
    int m = heights.length;
    int[] res = new int[m];
    int index = m - 1;
    int max = 0;
    for (int i = m - 1; i < m;i--) {
      if (heights[i] > max) {
        res[index--] = i;
        max = heights[i];
      }
    }
    return Arrays.copyOfRange(res, index, m);
  }


  public int[] productExceptSelf(int[] nums) {
    if (nums == null || nums.length == 0) return nums;
    int m = nums.length;
    int[] left = new int[m];
    left[0] = 1;
    /*
    1, 2, 3, 4
 l: 0  0  0  0
    1  1  2  6
         left[i] * right
         right = right ( nums[i]
    right

     */
    for (int i = 1; i < m; i++) {
      left[i] = left[i - 1] * nums[i - 1];
    }
    int right = nums[m - 1];
    nums[m - 1] = left[m - 1];
    for (int i = m - 2; i >= 0; i--) {
      int tmp = nums[i];
      nums[i] = left[i] * right;
      right = tmp * right;
    }
    return nums;
  }


  static final int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
  int m;
  int n;
  public char[][] updateBoard(char[][] board, int[] click) {
    if (board == null || board.length == 0) return board;
    if (board[click[0]][click[1]] == 'M') {
      board[click[0]][click[1]] = 'X';
      return board;
    }
    this.m = board.length;
    this.n = board[0].length;
    boolean[][] visited = new boolean[m][n];
    dfs(board, click[0], click[1], visited);
    return board;
  }

  private void dfs(char[][] board, int i, int j, boolean[][] visited) {
    if (i < 0 || j < 0 || i >= m || j >= n || visited[i][j] || board[i][j] == 'M') return;

    visited[i][j] = true;
    int count = 0;
    for (int[] dir : DIRS) {
      int neiX = i + dir[0];
      int neiY = j + dir[1];
      if (neiX < 0 || neiY < 0 || neiX >= m || neiY >= n) continue;
      if (board[neiX][neiY] == 'M') count++;
    }
    if (count != 0) {
      board[i][j] = (char) (count + '0');
      return;
    } else {
      board[i][j] = 'B';
      for (int[] dir : DIRS) {
        int neiX = dir[0] + i;
        int neiY = dir[1] + j;
        dfs(board, neiX, neiY, visited);
      }
    }


  }


  public int[] findOrder(int n, int[][] prerequisites) {
    int[] res = new int[n];
    int[] count = new int[n];
    Map<Integer, List<Integer>> graph = new HashMap<>();
    for (int[] courses : prerequisites) {
      int to = courses[0];
      int from = courses[1];
      count[to]++;
      graph.putIfAbsent(from, new ArrayList<>());
      graph.get(from).add(to);
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

        if (graph.containsKey(cur)) {
          for (int next : graph.get(cur)) {
            count[next]--;
            if (count[next] == 0) q.offer(next);
          }
        }
      }
    }

    return index == n ? res : new int[]{};
  }


  public String simplifyPath(String path) {
    // step 1: base case check

    // step 2: separate the path by '/'
    // use a stack to store directory

    // case one: path is ".", "", ignore it
    // case two: path is "..", stack poll one
    // case other case: offer it to stack

    // use a stringbuilder to build "/"
    if (path == null || path.length() == 0) return path;
    String[] array = path.split("/");
    Deque<String> stack = new ArrayDeque<>();

    for (String p : array) {
      if (p.equals(".") || p.equals("")) {
        continue;
      } else if (p.equals("..")) {
        if (!stack.isEmpty()) stack.pollFirst();
      } else {
        stack.offerFirst(p);
      }
    }

    if (stack.isEmpty()) return "/";
    StringBuilder prefix = new StringBuilder();
    while (!stack.isEmpty()) {
      prefix.append("/").append(stack.pollLast());
    }

    return prefix.toString();
  }


}
