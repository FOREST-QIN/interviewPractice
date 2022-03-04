package feb.twentySeven;

import java.util.*;

public class Solution {

  public int singleNumber(int[] nums) {
    if (nums.length == 1) return nums[0];
    int cur = nums[0];
    for (int i = 1; i < nums.length; i++) {
      cur ^= nums[i];
    }
    return cur;
  }


  public ListNode removeElements(ListNode head, int val) {
    if (head == null) return null;
    if (head.val != val) {
      head.next = removeElements(head.next, val);
    } else {
      return removeElements(head.next, val);
    }
    return head;
  }




  public void reverseString(char[] s) {
    if (s == null || s.length == 0) return;
    int i = 0;
    int j = s.length - 1;
    while (i <= j) {
      swap(s, i++, j--);
    }
  }

  private void swap(char[] s, int i, int j) {
    char tmp = s[i];
    s[i] = s[j];
    s[j] = tmp;
  }

  public List<Integer> postorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) return res;
    postOrder(root, res);
    return res;
  }

  private void postOrder(TreeNode root, List<Integer> res) {
    if (root == null) return;
    postOrder(root.left, res);
    postOrder(root.right, res);
    res.add(root.val);
  }

  public int searchInsert(int[] nums, int target) {
    if (nums == null || nums.length == 0) return 0;
    int m = nums.length;
    if (nums[m - 1] < target) return m;
    int l = 0;
    int r = m - 1;
    while (l < r) {
      int mid = l + (r - l) / 2;
      if (nums[mid] == target) {
        return mid;
      } else if (nums[mid] > target) {
        r = mid;
      } else {
        l = mid + 1;
      }
    }
    return l;
  }

  public int maxProduct(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int m = nums.length;
    int res = nums[0];
    int min = nums[0];
    int max = nums[0];
    for (int i = 1; i < m; i++) {
      int curMax = Math.max(nums[i] * min, nums[i] * max);
      int curMin = Math.min(nums[i] * min, nums[i] * max);
      max = Math.max(curMax, nums[i]);
      min = Math.min(curMin, nums[i]);
      res = Math.max(res, max);
    }
    return res;
  }


  public boolean containsDuplicate(int[] nums) {
    if (nums == null || nums.length <= 1) return false;
    Set<Integer> set = new HashSet<>();
    for (int num : nums) {
      if (set.contains(num)) return true;
      set.add(num);
    }
    return false;
  }

  public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, amount + 1);
    dp[0] = 0;
    for (int i = 1; i <= amount; i++) {
      for (int coin : coins) {
        if (coin > i) continue;
        dp[i] = Math.min(dp[i], dp[i - coin] = 1);
      }
    }
    return dp[amount] == amount + 1 ? -1 : dp[amount];
  }

  public int strStr(String haystack, String needle) {
    if (needle.length() > haystack.length()) return -1;
    if (haystack.equals(needle) || haystack.startsWith(needle)) return 0;
    for (int i = 0; i <= haystack.length() - needle.length(); i++) {
      int j = 0;
      while (j < needle.length() && haystack.charAt(i + j) == needle.charAt(j)) {
        j++;
      }
      if (j == needle.length()) return i;
    }
    return -1;
  }

  public int rob(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int prevTwo = 0;
    int prevOne = nums[0];
    for (int i = 1; i < nums.length; i++) {
      int cur = Math.max(nums[i] + prevTwo, prevOne);
      prevTwo = prevOne;
      prevOne = cur;
    }
    return prevOne;
  }

  public int reverse(int x) {
    if (x == 0) return x;
    boolean isNegative = x < 0;
    x = Math.abs(x);
    long res = 0;
    while (x > 0) {
      res = res * 10 + x % 10;
      x /= 10;
    }
    if (res > Integer.MAX_VALUE) return 0;
    return isNegative ? -(int) res : (int) res;
  }

  public boolean hasCycle(ListNode head) {
    if (head == null || head.next == null) return false;
    ListNode slow = head;
    ListNode fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (slow == fast) return true;
    }
    return false;
  }

  public int climbStairs(int n) {
    if (n == 1) return 1;
    if (n == 2) return 2;
    int prevTwo = 1;
    int prevOne = 2;
    for (int i = 3; i <= n; i++) {
      int cur = prevOne + prevTwo;
      prevTwo = prevOne;
      prevOne = cur;
    }
    return prevOne;
  }

  public int widthOfBinaryTree(TreeNode root) {
    if (root == null) return 0;
    Deque<TreeNode> q = new LinkedList<>();
    q.offerFirst(root);
    Map<TreeNode, Integer> map = new HashMap<>();
    map.put(root, 0);
    int max = 1;
    while (!q.isEmpty()) {
      int size = q.size();
      max = Math.max(max, map.get(q.peekFirst()) - map.get(q.peekLast()) + 1);
      for (int i = 0; i < size; i++) {
        TreeNode cur = q.pollLast();
        int base = map.get(cur);
        if (cur.left != null) {
          q.offerFirst(cur.left);
          map.put(cur.left, base * 2);
        }
        if (cur.right != null) {
          q.offerFirst(cur.right);
          map.put(cur.right, base * 2 + 1);
        }
      }
    }
    return max;
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

    ListNode(int x) {
      val = x;
      next = null;
    }
  }

}
