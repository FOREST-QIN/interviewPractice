package jan.nine;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    int[] res = findOrder(2, new int[][]{{1,0}});
  }


  public List<Boolean> checkIfPrerequisite(int n, int[][] prerequisites, int[][] queries) {
    boolean[][] graph = new boolean[n][n];
    for (int i = 0; i < n; i++) {
      graph[i][i] = true;
    }
    for (int[] pair : prerequisites) {
      graph[pair[0]][pair[1]] = true;
    }
    boolean[] visited = new boolean[n];
    for (int i = 0; i < n; i++) {
      fill(graph, i, visited);
    }
    List<Boolean> res = new ArrayList<>();
    for (int[] q : queries) {
      res.add(graph[q[0]][q[1]]);
    }
    return res;
  }

  private void fill(boolean[][] graph, int i, boolean[] visited) {
    if (visited[i]) return;
    visited[i] = true;
    for (int j = 0; j < graph.length; j++) {
      if (graph[i][j]) {
        fill(graph, j, visited);
        for (int k = 0; k < graph[j].length; k++) {
          if (graph[j][k]) graph[i][k] = true;
        }
      }
    }
  }


  public List<Integer> eventualSafeNodes(int[][] graph) {
    int n = graph.length;
    int[] color = new int[n];
    List<Integer> res = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      if (dfs(graph, i, color)) res.add(i);
    }
    return res;
  }

  private boolean dfs(int[][] graph, int start, int[] color) {
    if (color[start] != 0) return color[start] == 1;
    color[start] = 2;
    for (int next : graph[start]) {
      if (!dfs(graph, next, color)) return false;
    }
    color[start] = 1;
    return true;
  }

  public int[] loudAndRich(int[][] richer, int[] quiet) {
    int n = quiet.length;
    Map<Integer, List<Integer>> graph = new HashMap<>();
    for (int i = 0; i < n; i++) graph.put(i, new ArrayList<>());
    for (int[] pair : richer) graph.get(pair[1]).add(pair[0]);

    int[] res = new int[n];
    Arrays.fill(res, - 1);
    for (int i = 0; i < n; i++) {
      dfs(graph, i, res, quiet);
    }
    return res;
  }

  private int dfs(Map<Integer, List<Integer>> graph, int i, int[] res, int[] quiet) {
    if (res[i] >= 0) return res[i];
    res[i] = i;
    for (int nei : graph.get(i)) {
      if (quiet[res[i]] > quiet[dfs(graph, nei, res, quiet)]) {
        res[i] = res[nei];
      }
    }
    return res[i];
  }

  public int minimumSemesters(int n, int[][] relations) {
    int[] pre = new int[n + 1];
    Map<Integer, List<Integer>> graph = new HashMap<>();
    for (int[] edge : relations) {
      int prevCourse = edge[0];
      int nextCourse = edge[1];
      pre[nextCourse]++;
      graph.putIfAbsent(prevCourse, new ArrayList<>());
      graph.get(prevCourse).add(nextCourse);
    }
    Queue<Integer> q = new LinkedList<>();
    for (int i = 1; i <= n; i++) {
      if (pre[i] == 0) q.offer(i);
    }
    if (q.isEmpty()) return -1;
    int step = 0;
    int studied = 0;
    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        studied++;
        int cur = q.poll();
        if (!graph.containsKey(cur)) continue;
        for (int next : graph.get(cur)) {
          pre[next]--;
          if (pre[next] == 0) q.offer(next);
        }
      }
      step++;
    }

    return studied == n ? step : -1;
  }


  public int[] findOrder(int numCourses, int[][] prerequisites) {
    int[] res = new int[numCourses];
    int[] pre = new int[numCourses];
    getPreDependencies(pre, prerequisites);
    Map<Integer, List<Integer>> graph = buildGraph(prerequisites);
    Queue<Integer> q = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
      if (pre[i] == 0) q.offer(i);
    }
    int index = 0;

    while (!q.isEmpty() && index < numCourses) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        int cur = q.poll();
        res[index++] = cur;
        if (graph.containsKey(cur)) {
          for (int next : graph.get(cur)) {
            pre[next]--;
            if (pre[next] == 0) q.offer(next);
          }
        }
      }
    }
    return index == numCourses ? res : new int[]{};
  }

  private void getPreDependencies(int[] pre, int[][] prerequisites) {
    for (int[] edge : prerequisites) {
      pre[edge[0]]++;
    }
  }

  private Map<Integer, List<Integer>> buildGraph(int[][] prerequisites) {
    Map<Integer, List<Integer>> graph = new HashMap<>();
    for (int[] edge : prerequisites) {
      int pre = edge[1];
      int course = edge[0];
      graph.putIfAbsent(pre, new ArrayList<>());
      graph.get(pre).add(course);
    }
    return graph;
  }

}
