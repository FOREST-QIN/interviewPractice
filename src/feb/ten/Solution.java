package feb.ten;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    String s = "ADOBECODEBANC";
    String t = "ABC";
    String res = minWindow(s, t);
  }

  public List<List<Integer>> findLeaves(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    find(root, res);
    return res;
  }

  public int find(TreeNode root, List<List<Integer>> res) {
    if (root == null) return 0;
    int left = find(root.left, res);
    int right = find(root.right, res);
    int cur = Math.max(left, right) + 1;
    if (res.size() < cur) res.add(new ArrayList<>());
    res.get(cur - 1).add(root.val);
    return cur;
  }

  public boolean canFinish(int n, int[][] prerequisites) {
    if (n == 0 || prerequisites == null || prerequisites.length == 0) return true;
    Map<Integer, List<Integer>> graph = new HashMap<>();
    int[] count = new int[n];

    for (int[] req : prerequisites) {
      int next = req[0];
      int from = req[1];
      graph.computeIfAbsent(from, k -> new ArrayList<>()).add(next);
      count[next]++;
    }
    int took = 0;
    Queue<Integer> q = new LinkedList<>();
    for (int i = 0; i < n; i++) {
      if (count[i] == 0) q.offer(i);
    }

    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        took++;
        int cur = q.poll();
        if (graph.containsKey(cur)) {
          for (int next : graph.get(cur)) {
            count[next]--;
            if (count[next] == 0) q.offer(next);
          }
        }
      }
    }
    return took == n;
  }

  public int maxPoints(int[][] points) {
    if (points == null || points.length == 0) return 0;
    int m = points.length;
    int res = 0;
    for (int i = 0; i < m; i++) {
      int seedX = points[i][0];
      int seedY = points[i][1];
      int most = 0;
      int sameX = 0;
      Map<Double, Integer> map = new HashMap<>();
      for (int j = 0; j < m; j++) {
        if (j == i) continue;
        int curX = points[j][0];
        int curY = points[j][1];
        if (seedX == curX) {
          sameX++;
        } else {
          double slope = (seedY - curY + 0.0) / (seedX - curX);
          map.put(slope, map.getOrDefault(slope, 0) + 1);
          most = Math.max(most, map.get(slope));
        }
      }
      most = Math.max(most, sameX) + 1;
      res = Math.max(most, res);
    }
    return res;
  }

  public String minWindow(String s, String t) {
    if (s.equals(t)) return s;
    if (s.length() < t.length()) return "";
    int[] dict = new int[256];
    int count = 0;
    for (int i = 0; i < t.length(); i++) {
      if (dict[t.charAt(i)] == 0) count++;
      dict[t.charAt(i)]++;
    }
    for (int i = 0; i < 256; i++) {
      if (dict[i] == 0) dict[i] = Integer.MIN_VALUE;
    }
    int m = s.length();
    int match = 0;
    int i = 0;
    int j = 0;
    int l = 0;
    int r = 0;
    int min = m + 1;

    while (j < m) {
      char cur = s.charAt(j++);
      if (dict[cur] != Integer.MIN_VALUE) {
        dict[cur]--;
        if (dict[cur] == 0) match++;
      }
      while (match == count) {
        if (j - i < min) {
          l = i;
          r = j;
          min = j - i;
        }
        char prev = s.charAt(i++);
        if (dict[prev] != Integer.MIN_VALUE) {
          if (dict[prev] == 0) match--;
          dict[prev]++;
        }
      }
    }
    return l == 0 && r == 0 ? "" : s.substring(l, r);
  }


  public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, amount + 1);
    dp[0] = 0;
    for (int i = 1; i <= amount; i++) {
      for (int coin : coins) {
        if (i < coin) continue;
        dp[i] = Math.min(dp[i], dp[i - amount] + 1);
      }
    }
    return dp[amount] == amount + 1 ? -1 : dp[amount];
  }



  public int[] searchRange(int[] nums, int target) {
    if (nums == null || nums.length == 0) return new int[]{-1, -1};
    return new int[]{first(nums, target), last(nums, target)};
  }

  private int first(int[] nums, int target) {
    if (nums.length == 0) return -1;
    int left = 0;
    int right = nums.length -1;
    while (left < right - 1) {
      int mid = left + (right - left) / 2;
      if (nums[mid] == target) {
        right = mid;
      } else if (target > nums[mid]) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }
    if (nums[left] == target) return left;
    if (nums[right] == target) return right;
    return -1;
  }

  private int last(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;
    int left = 0;
    int right = nums.length - 1;
    while (left < right - 1) {
      int mid = left + (right - left) / 2;
      if (nums[mid] == target) {
        left = mid;
      } else if (target > nums[mid]) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }
    if (nums[right] == target) return right;
    if (nums[left] == target) return left;
    return -1;
  }


  public boolean isPalindrome(String s) {
    if (s == null || s.length() <= 1) return true;

    int i = 0;
    int j = s.length() - 1;

    while (i <= j) {
      while (i < j && !Character.isDigit(s.charAt(i)) && !Character.isLowerCase(s.charAt(i)) && !Character.isUpperCase(s.charAt(i))) {
        i++;
      }
      while (i < j && !Character.isDigit(s.charAt(i)) && !Character.isLowerCase(s.charAt(j)) && !Character.isUpperCase(s.charAt(j))) {
        j--;
      }
      if (Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(j))) {
        return false;
      }
      i++;
      j--;
    }
    return true;
  }


  public int climbStairs(int n) {
    if (n == 1 || n == 2) return n;
    int prevTwo = 1;
    int prevOne = 2;

    for (int i = 3; i <= n; i++) {
      int tmp = prevOne + prevTwo;
      prevTwo = prevOne;
      prevOne = tmp;
    }
    return prevOne;
  }

  public int firstUniqChar(String s) {
    if (s == null || s.length() == 0) return -1;
    int[] count = new int[26];
    for (int i = 0; i < s.length(); i++) {
      count[s.charAt(i) - 'a']++;
    }
    for (int i = 0; i < s.length(); i++) {
      if (count[s.charAt(i) - 'a'] == 1) return i;
    }
    return -1;
  }

  public List<String> wordBreak(String s, List<String> wordDict) {
    List<String> res = new ArrayList<>();
    if (s == null || s.length() == 0) return res;
    StringBuilder prefix = new StringBuilder();
    Set<String> set = new HashSet<>(wordDict);
    wordBreak(s, 0, prefix, res, set);
    return res;
  }

  private void wordBreak(String s, int index, StringBuilder prefix, List<String> res, Set<String> dict) {
    if (index == s.length()) {
      res.add(new String(prefix.substring(0, prefix.length() - 1)));
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




  public boolean canJump(int[] nums) {
    if (nums == null || nums.length <= 1) return true;
    int max = nums[0];
    int cur = nums[0];

    for (int i = 0; i <= max; i++) {
      if (i >= nums.length - 1) return true;
      if (i + nums[i] >= nums.length - 1) return true;
      max = Math.max(i + nums[i], max);
    }
    return false;
  }

  public int rob(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int m = nums.length;
    int prevTwo = 0;
    int prevOne = nums[0];

    for (int i = 1; i < m; i++) {
      int tmp = Math.max(nums[i] + prevTwo, prevOne);
      prevTwo = prevOne;
      prevOne = tmp;
    }
    return prevOne;
  }


  public void merge(int[] one, int m, int[] two, int n) {
    int index = m + n - 1;

    int i = m - 1;
    int j = n - 1;

    while (i >= 0 && j >= 0) {
      if (one[i] < two[j]) {
        one[index--] = two[j--];
      } else {
        one[index--] = one[i--];
      }
    }

    while (j >= 0) {
      one[index--] = two[j--];
    }

  }



  public ListNode mergeKLists(ListNode[] lists) {
    if (lists == null || lists.length == 0) return null;
    ListNode dummy = new ListNode(0);
    ListNode cur = dummy;
    PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));

    for (ListNode node : lists) {
      if (node != null) {
        minHeap.offer(node);
      }
    }

    while (!minHeap.isEmpty()) {
      ListNode node = minHeap.poll();
      cur.next = node;
      if (node.next != null) {
        minHeap.offer(node.next);
      }
      cur = cur.next;
    }
    return dummy.next;
  }

  public boolean isMatch(String s, String p) {
    if (p == null || p.length() == 0) return s == null || s.length() == 0;
    int m = s.length();
    int n = p.length();
    boolean[][] dp = new boolean[m + 1][n + 1];
    dp[0][0] = true;
    for (int i = 2; i <= n; i++) {
      if (p.charAt(i - 1) == '*') {
        dp[0][i] = dp[0][i - 2];
      }
    }
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.') {
          dp[i][j] = dp[i - 1][j - 1];
        } else if (p.charAt(j - 1) == '*') {
          dp[i][j] = dp[i][j - 2]
              || (s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.') && dp[i - 1][j];
        }
      }
    }
    return dp[m][n];
  }

  public ListNode mergeTwoLists(ListNode one, ListNode two) {
    if (one == null || two == null) return one == null ? two : one;
    ListNode dummy = new ListNode(0);
    ListNode cur = dummy;

    while (one != null && two != null) {
      if (one.val <= two.val) {
        cur.next = one;
        one = one.next;
      } else {
        cur.next = two;
        two = two.next;
      }
      cur = cur.next;
    }
    if (one != null) {
      cur.next = one;
    }
    if (two != null) {
      cur.next = two;
    }
    return dummy.next;
  }


  public Node cloneTree(Node root) {
    if (root == null) return null;
    Node cur = new Node(root.val);
    for (Node sub : root.children) {
      cur.children.add(cloneTree(sub));
    }
    return cur;
  }


  public void rotate(int[][] matrix) {
    int m = matrix.length;
    for (int i = 0; i < m; i++) {
      for (int j = i; j < m; j++) {
        int tmp = matrix[i][j];
        matrix[i][j] = matrix[j][i];
        matrix[j][i] = tmp;
      }
    }
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < m / 2; j++) {
        int tmp = matrix[i][j];
        matrix[i][j] = matrix[i][m - 1 - j];
        matrix[i][m - 1 - j] = tmp;
      }
    }

  }


  int max;

  public int maxPathSum(TreeNode root) {
    this.max = Integer.MIN_VALUE;
    getMax(root);
    return max;
  }

  private int getMax(TreeNode root) {
    if (root == null) return 0;
    int left = Math.max(getMax(root.left), 0);
    int right = Math.max(getMax(root.right), 0);
    max = Math.max(max, left + right + root.val);
    return root.val + Math.max(left, right);
  }

  public int maxArea(int[] height) {
    if (height == null || height.length <= 1) return 0;
    int m = height.length;
    int i = 0;
    int j = m - 1;
    int res = 0;

    while (i < j) {
      int cur = Math.min(height[i], height[j]);
      res = Math.max(res, cur * (j - i));
      if (height[i] <= height[j]) {
        i++;
        while (i < j && height[i] <= height[i - 1]) i++;
      } else {
        j--;
        while (i < j && height[j] <= height[j + 1]) j--;
      }
    }
    return res;
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


  static class Node {
    public int val;
    public List<Node> children;


    public Node() {
      children = new ArrayList<Node>();
    }

    public Node(int _val) {
      val = _val;
      children = new ArrayList<Node>();
    }

    public Node(int _val, ArrayList<Node> _children) {
      val = _val;
      children = _children;
    }
  }

  static class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }
  }

}

