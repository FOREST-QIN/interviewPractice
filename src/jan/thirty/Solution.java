package jan.thirty;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    String[] words = {"i", "love", "leetcode", "i", "love", "coding"};
    List<String> res = topKFrequent(words, 2);

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
              || ((s.charAt(i - 1) == p.charAt(j - 2)) || p.charAt(j- 2) == '.') && dp[i - 1][j];
        }
      }
    }
    return dp[m][n];
  }


  public Node copyRandomList(Node head) {
    if (head == null) return head;
    Map<Node, Node> map = new HashMap<>();
    Node cur = head;
    while (cur != null) {
      Node copy = map.get(cur);
      if (copy == null) {
        copy = new Node(cur.val);
        map.put(cur, copy);
      }

      if (cur.next != null) {
        if (!map.containsKey(cur.next)) {
          map.put(cur.next, new Node(cur.next.val));
        }
        copy.next = map.get(cur.next);
      }
      if (cur.random != null) {
        if (!map.containsKey(cur.random)) {
          map.put(cur.random, new Node(cur.random.val));
        }
        copy.random = map.get(cur.random);
      }
      cur = cur.next;
    }
    return map.get(head);
  }


  public void rotate(int[][] matrix) {
    if (matrix == null || matrix.length == 0) return;
    int n = matrix.length;
    for (int i = 0; i < n; i++) {
      for (int j = i; j < n; j++) {
        int tmp = matrix[i][j];
        matrix[i][j] = matrix[j][i];
        matrix[j][i] = tmp;
      }
    }
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n/2; j++) {
        int tmp = matrix[i][j];
        matrix[i][j] = matrix[i][n - 1 - j];
        matrix[i][n - 1 - j] = tmp;
      }
    }

  }


  public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    if (list1 == null || list2 == null) return list1 == null ? list2 : list1;
    ListNode dummy = new ListNode(0);
    ListNode cur = dummy;

    while (list1 != null && list2 != null) {
      if (list1.val < list2.val) {
        cur.next = list1;
        list1 = list1.next;
      } else {
        cur.next = list2;
        list2 = list2.next;
      }
      cur = cur.next;
    }

    if (list1 != null) {
      cur.next = list1;
    }
    if (list2 != null) {
      cur.next = list2;
    }
    return dummy.next;
  }


  public boolean containsDuplicate(int[] nums) {
    if (nums == null || nums.length == 0) return false;
    Set<Integer> set = new HashSet<>();
    for (int num : nums) {
      if (set.contains(num)) return true;
      set.add(num);
    }
    return false;
  }


  public List<String> topKFrequent(String[] words, int k) {
    List<String> res = new ArrayList<>();
    if (words == null || words.length == 0) return res;
    Map<String, Integer> counts = new HashMap<>();
    for (String word : words) {
      counts.put(word, counts.getOrDefault(word, 0) + 1);
    }

    PriorityQueue<String> minHeap = new PriorityQueue<>((a, b) -> {
      if (counts.get(a) != counts.get(b)) return Integer.compare(counts.get(a), counts.get(b));
      return b.compareTo(a);
    });

    for (String word : counts.keySet()) {
      minHeap.offer(word);
      if (minHeap.size() > k) minHeap.poll();
    }
    while (!minHeap.isEmpty()) {
      res.add(minHeap.poll());
    }

    int i = 0;
    int j = k - 1;
    while (i <= j) {
      String tmp = res.get(i);
      res.set(i, res.get(j));
      res.set(j, tmp);
      i++;
      j--;
    }
    return res;
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

  static class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
      this.val = val;
      this.next = null;
      this.random = null;
    }
  }

}
