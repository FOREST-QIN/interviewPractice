package jan.thirtyOne;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    addStrings("11", "123");
  }

  Map<TreeNode, TreeNode> graph;
  public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
    List<Integer> res = new ArrayList<>();
    if (root == null) return res;
    // parent
    this.graph = new HashMap<>();
    getParent(root, null);

    int step = 0;

    Set<TreeNode> visited = new HashSet<>();
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(target);
    visited.add(target);

    while (!q.isEmpty()) {
      if (step == k) {
        while (!q.isEmpty()) {
          res.add(q.poll().val);
        }
        return res;
      }
      int size = q.size();
      for (int i = 0; i < size; i++) {
        TreeNode cur = q.poll();
        if (graph.get(cur) != null && !visited.contains(graph.get(cur))) {
          q.offer(graph.get(cur));
          visited.add(graph.get(cur));
        }
        if (cur.left != null && !visited.contains(cur.left)) {
          q.offer(cur.left);
          visited.add(cur.left);
        }
        if (cur.right != null && !visited.contains(cur.right)) {
          q.offer(cur.right);
          visited.add(cur.right);
        }
      }
      step++;
    }
    return res;
  }

  private void getParent(TreeNode root, TreeNode parent) {
    if (root == null) return;
    graph.put(root, parent);
    getParent(root.left, root);
    getParent(root.right, root);
  }


  Map<Integer, PriorityQueue<Node>> map;
  int minCol;
  public List<List<Integer>> verticalTraversal(TreeNode root) {
    this.map = new HashMap<>();
    this.minCol = 0;
    vertical(root, 0, 0);
    List<List<Integer>> res = new ArrayList<>();

    while (map.containsKey(minCol)) {
      PriorityQueue<Node> minHeap = map.get(minCol);
      List<Integer> cur = new ArrayList<>();
      while (!minHeap.isEmpty()) {
        cur.add(minHeap.poll().val);
      }
      res.add(cur);
      minCol++;
    }

    return res;
  }

  private void vertical(TreeNode root, int col ,int row) {
    if (root == null) return;
    vertical(root.left, col - 1, row + 1);
    Node node = new Node(row, root.val);
    map.putIfAbsent(col, new PriorityQueue<>());
    map.get(col).offer(node);
    minCol = Math.min(minCol, col);

    vertical(root.right, col + 1, row + 1);
  }

  static class Node implements Comparable<Node>{
    int row;
    int val;

    public Node(int row, int val) {
      this.row = row;
      this.val = val;
    }


    @Override
    public int compareTo(Node o) {
      if (this.row != o.row) {
        return Integer.compare(this.row, o.row);
      } else {
        return Integer.compare(this.val, o.val);
      }
    }
  }



  public String addStrings(String num1, String num2) {
    char[] one = reverse(num1);
    char[] two = reverse(num2);
    int i = 0;
    int j = 0;
    char[] res = new char[num1.length() + num2.length()];
    int k = 0;
    int carry = 0;

    while (i < one.length || j < two.length || carry != 0) {
      int cur = 0;
      if (i < one.length) {
        cur += one[i] - '0';
        i++;
      }
      if (j < two.length) {
        cur += two[j] - '0';
        j++;
      }
      cur += carry;
      res[k++] = (char)(cur % 10 + '0');
      carry = cur / 10;
    }
    i = 0;
    j = k - 1;
    while (i <= j) {
      char tmp = res[i];
      res[i] = res[j];
      res[j] = tmp;
      i++;
      j--;
    }
    return new String(res, 0, k);
  }

  private char[] reverse(String s) {
    char[] arr = s.toCharArray();
    int i = 0;
    int j = arr.length - 1;
    while (i <= j) {
      char tmp = arr[i];
      arr[i] = arr[j];
      arr[j] = tmp;
      i++;
      j--;
    }
    return arr;
  }


  public List<Integer> rightSideView(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) return res;
    rightSide(root, 0, res);
    return res;
  }

  private void rightSide(TreeNode root, int level, List<Integer> res) {
    if (root == null) return;
    if (level == res.size()) res.add(root.val);
    rightSide(root.right, level + 1, res);
    rightSide(root.left, level + 1, res);
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
