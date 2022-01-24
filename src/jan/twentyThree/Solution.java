package jan.twentyThree;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    int res = firstMissingPositive(new int[]{3, 4, -1, 1});
  }

  public List<List<String>> groupAnagrams(String[] strs) {
    List<List<String>> res = new ArrayList<>();
    if (strs == null || strs.length == 0) return res;
    Map<String, List<String>> map = new HashMap<>();

    for (String str : strs) {
      int[] count = new int[26];
      for (int i = 0; i < str.length(); i++) {
        count[str.charAt(i) - 'a']++;
      }
      String hash = String.valueOf(Arrays.hashCode(count));
      map.putIfAbsent(hash, new ArrayList<>());
      map.get(hash).add(str);
    }

    res.addAll(map.values());
    return res;
  }


  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) return root;

    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    if (left != null && right != null) return root;
    return left == null ? right : left;
  }



  Random rand = new Random();
  int[] prefixSums;
  int totalSum;

  public Solution(int[] w) {
    this.prefixSums = new int[w.length];
    this.totalSum = 0;

    prefixSums[0] = w[0];
    totalSum = w[0];

    for (int i = 1; i < w.length; i++) {
      prefixSums[i] = w[i] + prefixSums[i - 1];
      totalSum += w[i];
    }
  }

  public int pickIndex() {
    int target = rand.nextInt(totalSum) + 1;
    int i = 0;
    int j = prefixSums.length - 1;
    while (i < j) {
      int mid = i + (j - i) / 2;
      if (prefixSums[mid] < target) {
        i = mid + 1;
      } else {
        j = mid;
      }
    }
    return i;
  }


  public int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    for (int num : nums) {
      if (minHeap.size() < k) {
        minHeap.offer(num);
      } else if (num > minHeap.peek()) {
        minHeap.poll();
        minHeap.offer(num);
      }
    }
    return minHeap.peek();
  }

  public List<String> generateParenthesis(int n) {
    List<String> res = new ArrayList<>();
    if (n == 0) return res;
    StringBuilder prefix = new StringBuilder();
    dfs(0, 0, n, prefix, res);
    return res;
  }

  private void dfs(int l, int r, int n, StringBuilder prefix, List<String> res) {
    if (l == n && r == l) {
      res.add(prefix.toString());
      return;
    }
    if (l < n) {
      prefix.append("(");
      dfs(l + 1, r, n, prefix, res);
      prefix.deleteCharAt(prefix.length() - 1);
    }

    if (r < l) {
      prefix.append(")");
      dfs(l, r + 1, n, prefix, res);
      prefix.deleteCharAt(prefix.length() - 1);
    }

  }

  public String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    StringBuilder prefix = new StringBuilder();
    prefix.append(strs[0]);
    for (int i = 1; i < strs.length; i++) {
      while (!strs[i].startsWith(prefix.toString())) {
        prefix.deleteCharAt(prefix.length() - 1);
      }
    }
    return prefix.toString();
  }


  public int firstMissingPositive(int[] nums) {
    int n = nums.length;
    for (int i = 0; i < n; i++) {
      if (nums[i] <= 0 || nums[i] > n) {
        nums[i] = n + 1;
      }
    }

    for (int i = 0; i < n; i++) {
      int num = Math.abs(nums[i]);
      if (num > n) continue;
      num--;
      if (nums[num] > 0) {
        nums[num] = -nums[num];
      }
    }
    for (int i = 0; i < n; i++) {
      if (nums[i] >= 0) return i + 1;
    }
    return n + 1;
  }


  public int reverse(int x) {
    int rev = 0;
    while (x != 0) {
      int pop = x % 10;
      x /= 10;
      if (rev > Integer.MAX_VALUE / 10 || rev == Integer.MAX_VALUE / 10 && pop > 7) return 0;
      if (rev < Integer.MIN_VALUE / 10 || rev == Integer.MIN_VALUE / 10 && pop < -8) return 0;
      rev = rev * 10 + pop;
    }
    return rev;
  }


  public void nextPermutation(int[] nums) {
    if (nums == null || nums.length == 0) return;
    /*
    traverse from back to head, find the first nums[i] smaller than nums[i] + 1;
    then, traver from back to i, find the first larget than nums[i], then swap them
     */
    int n = nums.length;
    int index = n - 2;
    while (index >= 0 && nums[index] >= nums[index + 1]) {
      index--;
    }
    if (index < 0) {
      reverse(nums, 0, n - 1);
      return;
    }
    int i = n - 1;
    while (i > index) {
      if (nums[i] > nums[index]) {
        swap(nums, i, index);
        reverse(nums, index + 1, n - 1);
        return;
      }
      i--;
    }
  }

  private void reverse(int[] array, int i, int j) {
    while (i <= j) {
      swap(array, i++, j--);
    }
  }

  private void swap(int[] array, int i, int j) {
    int tmp = array[i];
    array[i] = array[j];
    array[j] = tmp;
  }

  public int minMeetingRooms(int[][] intervals) {
    /*

    [0,30],[5,10],[15,20]]

                 15     /20
        5   /10
     0                     /30
    ======================

     */
    int n = intervals.length;
    int[] begin = new int[n];
    int[] end = new int[n];

    for (int i = 0; i < n; i++) {
      begin[i] = intervals[i][0];
      end[i] = intervals[i][1];
    }

    Arrays.sort(begin);
    Arrays.sort(end);

    int count = 0;
    int res = 0;
    int i = 0;
    int j = 0;
    while (i < n && j < n) {
      if (begin[i] < end[j]) {
        count++;
        i++;
      } else {
        count--;
        j++;
      }
      res = Math.max(res, count);
    }
    return res;
  }


  public boolean validPalindrome(String s) {
    return check(s, 0, s.length() - 1, false);
  }

  private boolean check(String s, int i, int j, boolean flag) {
    while (i <= j) {
      if (s.charAt(i) == s.charAt(j)) {
        i++;
        j--;
      } else {
        if (flag) return false;
        return check(s, i + 1, j, true) || check(s, i, j - 1, true);
      }
    }
    return true;
  }


  int i;
  int j;
  int max;

  public String longestPalindrome(String s) {
    max = 0;

    for (int i = 0; i < s.length(); i++) {
      getLongest(s, i, i);
      getLongest(s, i, i + 1);
    }
    return s.substring(i, j + 1);
  }

  private void getLongest(String s, int l, int r) {
    while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
      l--;
      r++;
    }
    if (r - l - 1 > max) {
      max = r - l - 1;
      j = r - 1;
      i = l + 1;
    }
  }


  public List<String> subdomainVisits(String[] cpdomains) {
    List<String> res = new ArrayList<>();
    if (cpdomains == null || cpdomains.length == 0) return res;

    Map<String, Integer> map = new HashMap<>();

    for (String domain : cpdomains) {
      int count = 0;
      // find space
      int i = 0;
      while (i < domain.length() && domain.charAt(i) != ' ') {
        i++;
      }
      count = Integer.parseInt(domain.substring(0, i));
      String dom = domain.substring(i + 1, domain.length());
      map.put(dom, map.getOrDefault(dom, 0) + count);
      for (int j = dom.length() - 1; j >= 0; j--) {
        if (dom.charAt(j) == '.') {

          map.put(dom.substring(j + 1), map.getOrDefault(dom.substring(j + 1), 0) + count);
        }
      }
    }

    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      res.add(entry.getValue() + " " + entry.getKey());
    }
    return res;
  }

  public int numBusesToDestination(int[][] routes, int source, int target) {
    // create map: <station, List<bus>>
    Map<Integer, List<Integer>> map = new HashMap<>();
    // how many bus could reach current station
    for (int i = 0; i < routes.length; i++) {
      for (int station : routes[i]) {
        map.putIfAbsent(station, new ArrayList<>());
        map.get(station).add(i);
      }
    }

    Set<Integer> visitedBus = new HashSet<>();
    Set<Integer> visitedStation = new HashSet<>();
    Queue<Integer> q = new LinkedList<>();
    q.offer(source);
    int level = 0;

    while (!q.isEmpty()) {
      int size = q.size();
      while (size-- > 0) {
        int curStation = q.poll();
        if (curStation == target) return level;
        for (int bus : map.get(curStation)) {
          if (visitedBus.contains(bus)) continue;
          visitedBus.add(bus);
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


  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }

}
