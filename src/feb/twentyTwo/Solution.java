package feb.twentyTwo;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    ListNode head = new ListNode(7);
    head.next = new ListNode(2);
    head.next.next = new ListNode(4);
    head.next.next.next = new ListNode(3);

    ListNode two = new ListNode(5);
    two.next = new ListNode(6);
    two.next.next = new ListNode(4);
    ListNode res = addTwoNumbers(head, two);
  }

  public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
    if (firstList == null || firstList.length == 0) return new int[0][0];
    if (secondList == null || secondList.length == 0) return new int[0][0];
    List<int[]> res = new ArrayList<>();
    int i = 0;
    int j = 0;
    while (i < firstList.length && j < secondList.length) {
      int[] first = firstList[i];
      int[] second = secondList[j];

      int start = Math.max(first[0], second[0]);
      int end = Math.min(first[1], second[1]);
      if (start <= second[1] && start <= first[1] && end >= first[0] && end >= second[0]) {
        res.add(new int[]{start, end});
      }
      if (first[1] < second[1]) {
        i++;
      } else {
        j++;
      }
    }
    return res.toArray(new int[res.size()][]);
  }

  public boolean isLongPressedName(String name, String typed) {
    /*
    saeed
     i
    ssaaedd
      j
     if (char[i] == char[j]) {
      i++;
      j++;
     } else {
      move j pointer until find the char[i]
     */
    if (name.equals(typed)) return true;
    // name
    int i = 0;
    // typed
    int j = 0;
    while (i < name.length() && j < typed.length()) {
      if (name.charAt(i) != typed.charAt(j)) {
        if (j == 0) return false;
        while (j < typed.length() && typed.charAt(j - 1) == typed.charAt(j)) j++;
        if (j >= typed.length() || name.charAt(i) != typed.charAt(j)) return false;
      }
      i++;
      j++;
    }
    while (j < typed.length()) {
      if (typed.charAt(j) != name.charAt(name.length() - 1)) return false;
      j++;
    }
    return i == name.length() && j == typed.length();
  }

  public String reverseOnlyLetters(String s) {
    if (s == null || s.length() <= 1) return s;
    char[] arr = s.toCharArray();
    int i = 0;
    int j = s.length() - 1;

    while (i <= j) {
      while (i <= j && !Character.isLowerCase(arr[i]) && !Character.isUpperCase(arr[i])) {
        i++;
      }
      while (i <= j && !Character.isLowerCase(arr[j]) && !Character.isUpperCase(arr[j])) {
        j--;
      }
      if (i < j) swap(arr, i++, j--);
      else break;
    }
    return new String(arr);
  }

  private void swap(char[] arr, int i, int j) {
    char tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }

  public int findContentChildren(int[] children, int[] cookies) {
    Arrays.sort(children);
    Arrays.sort(cookies);

    int child = 0;
    for (int i = 0; i < cookies.length && child < children.length; i++) {
      if (cookies[i] >= children[child]) child++;
    }
    return child;
  }

  public boolean isPalindrome(String s) {
    if (s == null || s.length() <= 1) return true;
    int l = 0;
    int r = s.length() - 1;
    while (l <= r) {
      while (l < r && !Character.isDigit(s.charAt(l))
          && !Character.isLowerCase(s.charAt(l))
          && !Character.isUpperCase(s.charAt(l))) {
        l++;
      }
      while (l < r && !Character.isDigit(s.charAt(r))
          && !Character.isLowerCase(s.charAt(r))
          && !Character.isUpperCase(s.charAt(r))) {
        r--;
      }
      if (Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r))) return false;
      l++;
      r--;
    }
    return true;
  }


  public int trap(int[] height) {
    if (height == null || height.length <= 1) return 0;
    int m = height.length;
    int l = 0;
    int r = m - 1;
    int lMax = height[l];
    int rMax = height[r];
    int res = 0;

    while (l <= r) {
      lMax = Math.max(lMax, height[l]);
      rMax = Math.max(rMax, height[r]);
      int cur = Math.min(lMax, rMax);

      res += Math.max(0, cur - height[l]);
      res += Math.max(0, cur - height[r]);
      if (height[l] <= height[r]) {
        l++;
      } else {
        r--;
      }
    }
    return res;
  }

  public int maxArea(int[] height) {
    if (height == null || height.length <= 1) return 0;
    int i = 0;
    int j = height.length - 1;
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
    if (one != null) cur.next = one;
    if (two != null) cur.next = two;
    return dummy.next;
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
      if (node.next != null) minHeap.offer(node.next);
      cur = cur.next;
    }
    return dummy.next;
  }

  public ListNode detectCycle(ListNode head) {
    if (head == null || head.next == null) return null;
    ListNode slow = head;
    ListNode fast = head;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (slow == fast) {
        slow = head;
        while (slow != fast) {
          slow = slow.next;
          fast = fast.next;
        }
        return slow;
      }
    }
    return null;
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


  // 1 -> 2 -> 3 -> 4
  public ListNode reverseList(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode newHead = reverseList(head.next);
    head.next.next = head;
    head.next = null;
    return newHead;
  }

  public ListNode swapPairs(ListNode head) {
    /*
    1 - 2 -( 3 - 4)
     ListNode next = head.next;

     head.next = head;
     head.next = swapPairs(next.next);
     return head;
     */
    if (head == null || head.next == null) return head;
    ListNode next = head.next;
    ListNode newNext = swapPairs(next.next);
    next.next = head;
    head.next = newNext;
    return next;
  }


  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    if (l1 == null || l2 == null) return l1 == null ? l2 : l1;
    ListNode one = reverse(l1);
    ListNode two = reverse(l2);
    ListNode dummy = new ListNode(0);
    ListNode cur = dummy;
    int carry = 0;
    while (one != null || two != null || carry != 0) {
      int sum = 0;
      if (one != null) {
        sum += one.val;
        one = one.next;
      }
      if (two != null) {
        sum += two.val;
        two = two.next;
      }
      sum += carry;
      cur.next = new ListNode(sum % 10);
      carry = sum / 10;
      cur = cur.next;
    }
    ListNode next = dummy.next;
    dummy.next = null;
    ListNode res = reverse(next);
    ListNode res1 = res;
    return res1;
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

  public ListNode addTwoNumbersOne(ListNode one, ListNode two) {
    if (one == null || two == null) return one == null ? two : one;
    ListNode dummy = new ListNode(0);
    ListNode cur = dummy;
    int carry = 0;
    while (one != null || two != null || carry != 0) {
      int sum = 0;
      if (one != null) {
        sum += one.val;
        one = one.next;
      }
      if (two != null) {
        sum += two.val;
        two = two.next;
      }
      sum += carry;
      cur.next = new ListNode(sum % 10);
      carry = sum / 10;
      cur = cur.next;
    }
    return dummy.next;
  }

  public int subarraySum(int[] nums, int k) {
    /*
    map
    key, val of prefix
    value: count

    index i:
    prefix sum = a
    map.contains a - k;

        1 1 1,   2
(0,1)         i
(1,1)
(2,1)
(3,1)
    prefix 3
    res 2

     */
    if (nums == null || nums.length == 0) return 0;
    //  prefix,   count
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);
    int prefix = 0;
    int res = 0;
    for (int num : nums) {
      prefix += num;
      int curTarget = prefix - k;
      if (map.containsKey(curTarget)) res += map.get(curTarget);
      map.put(prefix, map.getOrDefault(prefix, 0) + 1);
    }
    return res;
  }

  public int[] twoSum(int[] nums, int target) {
    if (nums == null || nums.length == 0) return new int[2];
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      int curTarget = target - nums[i];
      if (map.containsKey(curTarget)) return new int[]{map.get(curTarget), i};
      map.put(nums[i], i);
    }
    return new int[2];
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
