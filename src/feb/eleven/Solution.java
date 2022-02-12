package feb.eleven;

import org.junit.Test;

import java.util.*;

public class Solution {
  @Test
  public void test() {
    int[] arr = new int[]{10,9,2,5,3,7,101,18};
    int res = lengthOfLIS(arr);
  }

  public boolean isValidSudoku(char[][] board) {
    return true;
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


  public int longestConsecutive(int[] nums) {
    int max = 0;
    Set<Integer> set = new HashSet<>();
    for (int num : nums) set.add(num);
    for (int i = 0; i < nums.length; i++) {
      int count = 1;
      int num = nums[i];
      while (set.contains(--num)) {
        count++;
        set.remove(num);
      }
      num = nums[i];
      while (set.contains(++num)) {
        count++;
        set.remove(num);
      }
      max = Math.max(max, count);
    }
    return max;
  }

  /*
  [0, i) non-zero index
  j
    i
  j
   0 1 2 3  4
   1,3,12, 0,0
          i
              j
            7,1,5,3,6,4

             haveStock : prevDay I have stock's max Profit I could get
                         prevDay I don't have stock, I buy stock today

case1: maxProfit in current I have stock;
case2: maxProfit int current day I don't hava stock


   */
  public void moveZeroes(int[] nums) {
    if (nums == null || nums.length == 0) return;
    int i = 0;
    int j = 0;
    /**

     1,3,12,0,0
     i
     j

     */
    while (j < nums.length) {
      // case 1: nums[j] == 0 j++,
      // case 2: nums[j] != 0 nums[i++] j++
      if (nums[j] != 0) {
        nums[i++] = nums[j];
      }
      j++;
    }
    while (i < nums.length) {
      nums[i++] = 0;
    }
  }

  public int evalRPN(String[] tokens) {
    if (tokens == null || tokens.length == 0) return 0;
    Deque<Integer> stack = new ArrayDeque<>();

    for (String elem : tokens) {
      if (elem.equals("+") || elem.equals("-") || elem.equals("*") || elem.equals("/")) {
        int second = stack.pollFirst();
        int first = stack.pollFirst();
        int cur = 0;
        if (elem.equals("+")) {
          cur = first + second;
        } else if (elem.equals("-")) {
          cur = first - second;
        } else if (elem.equals("*")) {
          cur = first * second;
        } else {
          cur = first / second;
        }
        stack.offerFirst(cur);
      } else {
        stack.offerFirst(Integer.parseInt(elem));
      }
    }

    return stack.pollFirst();
  }

  public ListNode reverseList(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode prev = null;
    while (head != null) {
      ListNode next = head.next;
      head.next = prev;
      prev = head;
      head = next;
    }
    return prev;
  }


  public int maxProduct(int[] nums) {
    if (nums == null || nums.length == 0) return 0;

    int m = nums.length;
    int res = nums[0];
    int max = nums[0];
    int min = nums[0];

    for (int i = 1; i < m; i++) {
      int curMax = Math.max(max * nums[i], min * nums[i]);
      int curMin = Math.min(min * nums[i], max * nums[i]);
      max = Math.max(curMax, nums[i]);
      min = Math.min(curMin, nums[i]);
      res = Math.max(res, max);
    }
    return res;
  }

  public int uniquePaths(int m, int n) {
    if (m == 1 || n == 1) return 1;
    int[] dp = new int[n];
    for (int i = 0; i < n; i++) {
      dp[i] = 1;
    }
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        dp[j] = dp[j - 1] + dp[j];
      }
    }

    return dp[n - 1];
  }


  public boolean isMatch(String s, String p) {
    int m = s.length();
    int n = p.length();
    // dp[i][j] represents, is first i characters in s are matched by the first j characters in my pattern

    boolean[][] dp = new boolean[m + 1][n + 1];
    dp[0][0] = true;
    for (int i = 1; i <= n; i++) {
      if (p.charAt(i - 1) == '*') {
        dp[0][i] = dp[0][i - 1];
      }
    }
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (p.charAt(j - 1) == '*') {
          dp[i][j] = dp[i - 1][j] || dp[i][j - 1] || dp[i - 1][j - 1];
        } else if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?') {
          dp[i][j] = dp[i - 1][j - 1];
        }
      }
    }
    return dp[m][n];
  }

  public int findDuplicate(int[] nums) {
    int res = 0;
    for (int num : nums) {
      int index = num - 1;
      if (nums[index] < 0) {
        res = num;
        break;
      }
      nums[index] = -nums[index];
    }
    for (int i = 0; i < nums.length; i++) {
      nums[i] = Math.abs(nums[i]);
    }
    return res;
  }


  public boolean isPalindrome(ListNode head) {
    if (head == null || head.next == null) return true;
    ListNode mid = findMiddle(head);
    ListNode midNext = mid.next;
    mid.next = null;
    ListNode newHead = reverse(midNext);

    while (head != null && newHead != null) {
      if (head.val != newHead.val) return false;
      head = head.next;
      newHead = newHead.next;
    }
    return true;
  }

  private ListNode reverse(ListNode head) {
    ListNode prev = null;
    while (head != null) {
      ListNode next = head.next;
      head.next = prev;
      prev = head;
      head = next;
    }
    return prev;
  }

  private ListNode findMiddle(ListNode head) {
    ListNode slow = head;
    ListNode fast = head;

    while (fast.next != null && fast.next.next != null) {
      fast = fast.next.next;
      slow = slow.next;
    }
    return slow;
  }


  public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    if (nums == null || nums.length == 0) return res;
    permute(nums, 0, res);
    return res;
  }

  private void permute(int[] nums, int index, List<List<Integer>> res) {
    if (index == nums.length) {
      List<Integer> cur = new ArrayList<>();
      for (int num : nums) {
        cur.add(num);
      }
      res.add(cur);
      return;
    }

    for (int i = index; i < nums.length; i++) {
      swap(nums, i, index);
      permute(nums, index + 1, res);
      swap(nums, i, index);
    }

  }

  private void swap(int[] nums, int i, int j) {
    int tmp = nums[i];
    nums[i] = nums[j];
    nums[j] = tmp;
  }


  static int[][] DIRS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

  public int longestIncreasingPath(int[][] matrix) {
    int m = matrix.length;
    int n = matrix[0].length;
    int[][] memo = new int[m][n];
    int max = 0;

    for (int[] arr : memo) Arrays.fill(arr, -1);
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        max = Math.max(max, dfs(matrix, i, j, memo));
      }
    }
    return max;
  }

  private int dfs(int[][] matrix, int i, int j, int[][] memo) {
    if (memo[i][j] != -1) return memo[i][j];
    int count = 0;
    for (int[] dir : DIRS) {
      int r = i + dir[0];
      int c = j + dir[1];
      if (r < 0 || c < 0 || r >= matrix.length || c >= matrix[0].length || matrix[r][c] <= matrix[i][j]) continue;
      count = Math.max(count, dfs(matrix, r, c, memo));
    }
    memo[i][j] = count + 1;
    return memo[i][j];
  }

  public List<String> fizzBuzz(int n) {
    List<String> res = new ArrayList<>();
    for (int i = 1; i <= n; i++) {
      if (i % 3 == 0 && i % 5 == 0) {
        res.add("FizzBuzz");
      } else if (i % 3 == 0) {
        res.add("Fizz");
      } else if (i % 5 == 0) {
        res.add("Buzz");
      } else {
        res.add(String.valueOf(i));
      }
    }
    return res;
  }

  public int numDecodings(String s) {
    int m = s.length();
    int[] ways = new int[m + 1];
    ways[0] = 1;
    for (int i = 1; i <= m; i++) {
      char cur = s.charAt(i - 1);
      if (cur >= '1' && cur <= '9') {
        ways[i] += ways[i - 1];
      }
      if (i > 1) {
        char prev = s.charAt(i - 2);
        if ((prev == '1' && cur >= '0' && cur <= '9') || (prev == '2' && cur >= '0' && cur <= '6')) {
          ways[i] += ways[i - 2];
        }
      }
    }
    return ways[m];
  }


  public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);
    boolean isEven = true;

    while (!q.isEmpty()) {
      List<Integer> curLayer = new LinkedList<>();
      int size = q.size();
      for (int i = 0; i < size; i++) {
        TreeNode cur = q.poll();
        if (isEven) {
          curLayer.add(cur.val);
        } else {
          curLayer.add(0, cur.val);
        }
        if (cur.left != null) {
          q.offer(cur.left);
        }
        if (cur.right != null) {
          q.offer(cur.right);
        }
      }
      res.add(curLayer);
      isEven = !isEven;
    }
    return res;
  }


  public void gameOfLife(int[][] board) {
    if (board == null || board.length == 0) return;
    int m = board.length;
    int n = board[0].length;

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (board[i][j] % 10 == 0) continue;
        for (int[] dir : DIRS) {
          int nx = i + dir[0];
          int ny = j + dir[0];
          if (nx < 0 || ny < 0 || nx >= m || ny >= n) continue;
          board[nx][ny] += 10;
        }
      }
    }
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        int old = board[i][j] % 10;
        int count = board[i][j] / 10;
        if (old == 1) {
          if (count < 2) {
            board[i][j] = 0;
          } else if (count == 2 || count == 3) {
            board[i][j] = 1;
          } else {
            board[i][j] = 0;
          }
        } else if (old == 0) {
          if (count == 3) {
            board[i][j] = 1;
          } else {
            board[i][j] = 0;
          }
        }
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
