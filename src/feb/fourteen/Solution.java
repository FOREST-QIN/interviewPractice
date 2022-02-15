package feb.fourteen;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class Solution {

  @Test
  public void test() {
    assertTrue(false);
    System.out.println("sssss");
  }

  public ListNode sortList(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode prev = null;
    ListNode slow = head;
    ListNode fast = head;

    while (fast != null && fast.next != null) {
      prev = slow;
      slow = slow.next;
      fast = fast.next.next;
    }

    return null;
  }

  public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    inorder(root, res);
    return res;
  }

  private void inorder(TreeNode root, List<Integer> res) {
    if (root == null) return;
    inorder(root.left, res);
    res.add(root.val);
    inorder(root.right, res);
  }

  public int longestSubstring(String s, int k) {
    if (s == null || s.length() == 0 || k > s.length()) return 0;
    int[] counts = new int[26];
    int max = 0;
    for (int u = 1; u <= 26; u++) {
      Arrays.fill(counts, 0);
      int left = 0;
      int right = 0;
      int unique = 0;
      int kOrMore = 0;
      while (right < s.length()) {

        char c = s.charAt(right);
        int idx = c - 'a';
        counts[idx]++;
        if (counts[idx] == 1) unique++;
        if (counts[idx] == k) kOrMore++;
        right++;

        while (unique > u) {
          char prev = s.charAt(left);
          int ix = prev - 'a';

          counts[ix]--;
          if (counts[ix] == 0) unique--;
          if (counts[ix] == k - 1) kOrMore--;
          left++;
        }
        if (unique == u && kOrMore == unique) max = Math.max(max, right - left);
      }
    }
    return max;
  }

  public int titleToNumber(String columnTitle) {
    int res = 0;
    for (int i = 0; i < columnTitle.length(); i++) {
      res = res * 26 + (columnTitle.charAt(i) - 'A' + 1);
    }
    return res;
  }

  public List<String> findMissingRanges(int[] nums, int lower, int upper) {
    List<String> res = new ArrayList<>();
    int next = lower;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] == next) {
        next = nums[i] + 1;
        continue;
      }
      res.add(getRange(next, nums[i] - 1));
      next = nums[i] + 1;
    }
    if (next <= upper) res.add(getRange(next, upper));
    return res;
  }

  private String getRange(int n1, int n2) {
    return (n1 == n2) ? String.valueOf(n1) : new String(n1 + "->" + n2);
  }


  public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
    if (root == null) return null;
    if (root.val <= p.val) {
      return inorderSuccessor(root.right, p);
    } else {
      TreeNode left = inorderSuccessor(root.left, p);
      return left != null ? left : root;
    }
  }


  public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    if (headA == null || headB == null) return null;
    ListNode a = headA;
    ListNode b = headB;

    while (a != b) {
      a = a == null ? headB : a.next;
      b = b == null ? headA : b.next;
    }
    return a;
  }


  public int lengthOfLongestSubstringKDistinct(String s, int k) {
    if (s == null || s.length() == 0 || k == 0) return 0;
    if (s.length() <= k) return s.length();
    int[] count = new int[256];
    int distinct = 0;
    int i = 0;
    int j = 0;
    int max = 0;
    while (j < s.length()) {
      char cur = s.charAt(j);
      if (count[cur] == 0) distinct++;
      count[cur]++;
      if (distinct == k) max = Math.max(max, j - i + 1);

      while (distinct > k) {
        char prev = s.charAt(i++);
        count[prev]--;
        if (count[prev] == 0) distinct--;
      }
      j++;
    }
    return max == 0 ? s.length() : max;
  }


  public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    if (nums == null) return res;
    subsets(nums, 0, new ArrayList<>(), res);
    return res;
  }

  private void subsets(int[] nums, int index, List<Integer> cur, List<List<Integer>> res) {
    if (index == nums.length) {
      res.add(new ArrayList<>(cur));
      return;
    }
    cur.add(nums[index]);
    subsets(nums, index + 1, cur, res);
    cur.remove(cur.size() - 1);
    subsets(nums, index + 1, cur, res);
  }


  public int[] plusOne(int[] digits) {
    if (digits == null || digits.length == 0) return digits;
    int m = digits.length;
    int[] res = new int[m + 1];
    int index = m;
    int carry = 1;
    int i = m - 1;
    while (i >= 0 || carry != 0) {
      int cur = 0;
      if (i >= 0) cur += digits[i];
      cur += carry;
      res[index--] = cur % 10;
      carry = cur / 10;
      i--;
    }
    return Arrays.copyOfRange(res, index + 1, m + 1);
  }


  public int[] intersect(int[] one, int[] two) {
    if (one == null || two == null || one.length == 0 || two.length == 0) return new int[]{};
    int max = Math.max(one.length, two.length);
    int[] res = new int[max];
    int index = 0;
    Map<Integer, Integer> map = new HashMap<>();
    for (int num : one) {
      map.put(num, map.getOrDefault(num, 0) + 1);
    }
    for (int num : two) {
      if (map.containsKey(num) && map.get(num) > 0) {
        res[index++] = num;
        map.put(num, map.get(num) - 1);
      }
    }
    return Arrays.copyOf(res, index);
  }


  public boolean hasCycle(ListNode head) {
    if (head == null || head.next == null) return false;
    ListNode slow = head;
    ListNode fast = head;
    while (fast.next != null) {
      fast = fast.next.next;
      slow = slow.next;
      if (slow == fast) return true;
    }
    return false;
  }


  public int strStr(String haystack, String needle) {
    if (needle == null || haystack.length() < needle.length()) return -1;
    if (needle.length() == 0 || haystack.equals(needle)) return 0;

    int m = haystack.length();
    int n = needle.length();

    for (int i = 0; i <= m - n; i++) {
      int idx = 0;
      while (idx < n && haystack.charAt(i + idx) == needle.charAt(idx)) {
        idx++;
      }
      if (idx == needle.length()) {
        return i;
      }
    }
    return -1;
  }


  Map<Integer, Integer> map;

  public TreeNode buildTree(int[] preorder, int[] inorder) {
    /*
    preorder = [3,9,20,15,7],
                    i
    inorder = [9,3,15,20,7]
                 j
                 3
               9

      traverse preorder from head to tail, current is root
      left subtree is [0,j)
      right subtree is [j+1, n)
     */
    this.map = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) {
      map.put(inorder[i], i);
    }
    return build(preorder, 0, inorder, 0, inorder.length - 1);
  }

  private TreeNode build(int[] preorder, int cur, int[] inorder, int left, int right) {
    if (cur >= preorder.length || left > right) return null;
    TreeNode root = new TreeNode(preorder[cur]);
    int index = map.get(root.val);
    root.left = build(preorder, cur + 1, inorder, left, index - 1);
    root.right = build(preorder, cur + index - left + 1, inorder, index + 1, right);
    return root;
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
