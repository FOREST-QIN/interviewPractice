package practice.decem.twentyEight;

import java.util.*;

public class Solution {

  public TreeNode buildTree(int[] preorder, int[] inorder) {
    return build(preorder, 0, preorder.length - 1,
        inorder, 0, inorder.length - 1);
  }

  private TreeNode build(int[] preorder, int preStart, int preEnd,
                         int[] inorder, int inStart, int inEnd) {
    if (preStart > preEnd) return null;

    int rootVal = preorder[preStart];
    int index = 0;
    for (int i = inStart; i <= inEnd; i++) {
      if (inorder[i] == rootVal) {
        index = i;
        break;
      }
    }

    int leftSize = index - inStart;
    TreeNode root = new TreeNode(rootVal);
    root.left = build(preorder, preStart + 1, preStart + leftSize,
        inorder, inStart, index - 1);
    root.right = build(preorder, preStart + leftSize + 1, preEnd,
        inorder, index + 1, inEnd);
    return root;
  }

  public TreeNode constructMaximumBinaryTree(int[] nums) {
    return build(nums, 0, nums.length - 1);
  }

  private TreeNode build(int[] nums, int l, int r) {
    if (l > r) return null;

    int index = -1;
    int max = Integer.MIN_VALUE;
    for (int i = l; i <= r; i++) {
      if (nums[i] > max) {
        index = i;
        max = nums[i];
      }
    }

    TreeNode root = new TreeNode(nums[index]);
    root.left = build(nums, l, index - 1);
    root.right = build(nums, index + 1, r);
    return root;
  }

  public void flatten(TreeNode root) {
    if (root == null) return;
    flatten(root.left);
    flatten(root.right);

    TreeNode left = root.left;
    TreeNode right = root.right;

    root.left = null;
    root.right = left;

    TreeNode p = root;
    while (p.right != null) p = p.right;
    p.right = right;
  }

  public Node connect(Node root) {
    if (root == null) return null;
    connectNode(root.left, root.right);
    return root;
  }

  private void connectNode(Node node1, Node node2) {
    if (node1 == null || node2 == null) return;

    node1.next = node2;

    connectNode(node1.left, node1.right);
    connectNode(node2.left, node2.right);
    connectNode(node1.right, node2.left);
  }

  public TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    TreeNode tmp = root.left;
    root.left = root.right;
    root.right = tmp;

    invertTree(root.left);
    invertTree(root.right);
    return root;
  }


  public int openLock(String[] deadends, String target) {
    Set<String> deads = new HashSet<>();
    Collections.addAll(deads, deadends);

    Set<String> q1 = new HashSet<>();
    Set<String> q2 = new HashSet<>();
    Set<String> visited = new HashSet<>();

    int step = 0;
    q1.add("0000");
    q2.add(target);

    while (!q1.isEmpty() && !q2.isEmpty()) {
      Set<String> tmp = new HashSet<>();
      for (String cur : q1) {
        if (deads.contains(cur)) continue;
        if (q2.contains(cur)) return step;
        visited.add(cur);
        for (int i = 0; i < 4; i++) {
          String up = plusOne(cur, i);
          String down = minusOne(cur, i);
          if (!visited.contains(up)) tmp.add(up);
          if (!visited.contains(down)) tmp.add(down);
        }
      }
      step++;
      q1 = q2;
      q2 = tmp;
    }
    return -1;
  }

  private String plusOne(String str, int i) {
    char[] arr = str.toCharArray();
    if (arr[i] == '9') arr[i] = '0';
    else arr[i]++;
    return new String(arr);
  }

  private String minusOne(String str, int i) {
    char[] arr = str.toCharArray();
    if (arr[i] == '0') arr[i] = '9';
    else arr[i]--;
    return new String(arr);
  }


  public int numBusesToDestination(int[][] routes, int source, int target) {
    Map<Integer, ArrayList<Integer>> map = new HashMap<>();
    for (int i = 0; i < routes.length; i++) {
      for (int station : routes[i]) {
        map.putIfAbsent(station, new ArrayList<>());
        map.get(station).add(i);
      }
    }

    Set<Integer> visitedBuses = new HashSet<>();
    Set<Integer> visitedStation = new HashSet<>();
    Queue<Integer> q = new ArrayDeque<>();
    q.offer(source);
    int level = 0;

    while (!q.isEmpty()) {
      int size = q.size();
      while (size-- > 0) {
        int curStation = q.poll();
        if (curStation == target) return level;
        for (int bus : map.get(curStation)) {
          if (visitedBuses.contains(bus)) continue;
          visitedBuses.add(bus);
          for (int station : routes[bus]) {
            if (visitedStation.contains(station)) continue;
            visitedStation.add(station);
            q.offer(station);
          }
        }
      }
      level++;
    }
    return -1;
  }

  static class Station {
    int route;
    int bus;

    public Station(int route, int bus) {
      this.route = route;
      this.bus = bus;
    }
  }

  int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

  public int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    int m = grid.length;
    int n = grid[0].length;
    int res = 0;

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] == '1') {
          dfs(grid, i, j);
          res++;
        }
      }
    }
    return res;
  }

  private void dfs(char[][] grid, int i, int j) {
    if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] == '0') return;

    grid[i][j] = '0';
    for (int[] dir : DIRS) {
      dfs(grid, i + dir[0], j + dir[1]);
    }
  }


  public int findLength(int[] nums1, int[] nums2) {
    int res = 0;
    int[][] memo = new int[nums1.length + 1][nums2.length + 1];
    for (int i = 1; i <= nums1.length; i++) {
      for (int j = 1; j <= nums2.length; j++) {
        if (nums1[i - 1] == nums2[j - 1]) {
          memo[i][j] = memo[i - 1][j - 1] + 1;
          res = Math.max(res, memo[i][j]);
        }
      }
    }
    return res;
  }

  public int[] maxSlidingWindow(int[] nums, int k) {
    int[] res = new int[nums.length - k + 1];
    int index = 0;
    Deque<Integer> deque = new ArrayDeque<>();
    for (int i = 0; i < nums.length; i++) {
      while (!deque.isEmpty() && nums[deque.peekFirst()] <= nums[i]) {
        deque.pollFirst();
      }
      if (!deque.isEmpty() && deque.peekLast() <= i - k) {
        deque.pollLast();
      }
      deque.offerFirst(i);
      if (i >= k - 1) {
        res[index++] = nums[deque.peekLast()];
      }
    }
    return res;
  }


  public class TreeNode {
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

  static class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val) {
      val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
      val = _val;
      left = _left;
      right = _right;
      next = _next;
    }
  }

}
