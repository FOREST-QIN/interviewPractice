package feb.eight;

import org.junit.jupiter.api.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    int[] arr = {-2, 0, 1, 1, 2};
    List<List<Integer>> res = threeSum(arr);
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

  public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    Arrays.sort(nums);
    int m = nums.length;
    for (int i = 0; i + 2 < m; i++) {
      if (nums[i] > 0) break;
      if (i > 0 && nums[i] == nums[i - 1]) {
        continue;
      }
      int j = i + 1;
      int k = m - 1;
      int target = -nums[i];
      while (j < k) {
        int sum = nums[j] + nums[k];
        if (sum == target) {
          res.add(Arrays.asList(nums[i],nums[j], nums[k]));
          j++;
          k--;
          while (j < k && nums[j] == nums[j - 1]) j++;
          while (j < k && nums[k] == nums[k + 1]) k--;
        } else if (sum > target) {
          k--;
        } else {
          j++;
        }
      }
    }
    return res;
  }


  int max;
  int left;
  int right;
  public String longestPalindrome(String s) {
    if (s == null || s.length() == 0) return s;
    this.max = 0;
    this.left = 0;
    this.right = 0;

    for (int i = 0; i < s.length() - 1; i++) {
      bloom(s, i, i);
      bloom(s, i, i + 1);
    }
    return s.substring(left, right + 1);
  }

  private void bloom(String s, int l, int r) {
    while (l >= 0 && r < s.length()) {
      if (s.charAt(l) == s.charAt(r)) {
        l--;
        r++;
      } else {
        break;
      }
    }
    /*
    0 1 2 3 4
   -1,      1
     */
    if (r - l - 1 > max) {
      left = l + 1;
      right = r - 1;
      max = r - l - 1;
    }
  }


  public int subarraySum(int[] nums, int k) {
    if (nums == null || nums.length == 0) return 0;
    Map<Integer, Integer> count = new HashMap<>();
    count.put(0, 1);
    int prefix = 0;
    int res = 0;

    for (int num : nums) {
      prefix += num;
      int curTarget = prefix - k;
      if (count.containsKey(curTarget)) {
        res += count.get(curTarget);
      }
      count.put(prefix, count.getOrDefault(prefix, 0) + 1);
    }
    return res;
  }

  public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
    if (startTime == null || startTime.length == 0) return 0;
    int n = startTime.length;
    int[][] jobs = new int[n][3];
    for (int i = 0; i < n; i++) {
      jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
    }
    Arrays.sort(jobs, (a, b) -> Integer.compare(a[1], b[1]));
    TreeMap<Integer, Integer> dp = new TreeMap<>();
    dp.put(0, 0);
    for (int[] job : jobs) {
      int cur = dp.floorEntry(job[0]).getValue() + job[2];
      if (cur > dp.lastEntry().getValue()) {
        dp.put(job[1], cur);
      }
    }
    return dp.lastEntry().getValue();
  }

  public int maxProfit(int[] prices) {
    int m = prices.length;
    int dp0 = 0;
    int dp1 = Integer.MIN_VALUE;
    int dpPre = 0;

    for (int i = 0; i < m; i++) {
      int tmp = dp0;
      dp0 = Math.max(dp0, dp1 + prices[i]);
      dp1 = Math.max(dp1, dpPre - prices[i]);
      dpPre = tmp;
    }
    return dp0;
  }



  public int minMeetingRooms(int[][] intervals) {
    if (intervals == null || intervals.length == 0) return 0;
    int m = intervals.length;
    int[] start = new int[m];
    int[] end = new int[m];

    for (int i = 0; i < m; i++) {
      start[i] = intervals[i][0];
      end[i] = intervals[i][1];
    }
    Arrays.sort(start);
    Arrays.sort(end);

    int i = 0;
    int j = 0;
    int res = 0;
    int count = 0;

    while (i < m && j < m) {
      if (start[i] < end[j]) {
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

  public int lengthOfLongestSubstring(String s) {
    if (s == null || s.length() == 0) return 0;
    int m = s.length();
    int[] dict = new int[256];
    int max = 1;
    int i = 0;
    int j = 0;

    while (j < s.length()) {
      char ch = s.charAt(j);
      dict[ch]++;
      j++;

      while (dict[ch] != 1) {
        dict[s.charAt(i++)]--;
      }
      max = Math.max(j - i, max);
    }
    return max;
  }


  public int maxSubArray(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int m = nums.length;
    int max = nums[0];
    int prev = nums[0];

    for (int i = 1; i < m; i++) {
      prev = Math.max(0, prev) + nums[i];
      max = Math.max(prev, max);
    }
    return max;
  }


  public boolean isValid(String s) {
    if (s == null || s.length() == 0) return true;
    Map<Character, Character> map = new HashMap<>();
    map.put('}', '{');
    map.put(')', '(');
    map.put(']', '[');

    Deque<Character> stack = new ArrayDeque<>();
    for (char ch : s.toCharArray()) {
      if (ch == '(' || ch == '[' || ch == '{') {
        stack.offerFirst(ch);
      } else {
        if (stack.isEmpty() || stack.peekFirst() != map.get(ch)) return false;
        stack.pollFirst();
      }
    }
    return stack.isEmpty();
  }

  public ListNode addTwoNumbers(ListNode one, ListNode two) {
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
      if (carry != 0) sum += carry;
      cur.next = new ListNode(sum % 10);
      carry = sum / 10;
      cur = cur.next;
    }
    return dummy.next;
  }

  public double findMedianSortedArrays(int[] one, int[] two) {
    int l = (one.length + two.length + 1) / 2;
    int r = (one.length + two.length + 2) / 2;

    int lRes = find(one, 0, two, 0, l);
    int rRes = find(one, 0, two, 0, r);
    return (lRes + rRes) / 2.0;
  }

  private int find(int[] one, int i, int[] two, int j, int k) {
    if (i >= one.length) return two[j + k - 1];
    if (j >= two.length) return one[i + k - 1];
    if (k == 1) return Math.min(one[i], two[j]);

    int oRes = i + k / 2 - 1 >= one.length ? Integer.MAX_VALUE : one[i + k / 2 - 1];
    int tRes = j + k / 2 - 1 >= two.length ? Integer.MAX_VALUE : two[j + k / 2 - 1];

    if (oRes <= tRes) {
      return find(one, i + k / 2, two, j, k - k / 2);
    } else {
      return find(one, i, two, j + k / 2, k - k / 2);
    }

  }


  int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

  public int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) return 0;

    int res = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        if (grid[i][j] == '1') {
          dfs(grid, i, j);
          res++;
        }
      }
    }
    return res;
  }

  private void dfs(char[][] grid, int row, int col) {
    if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || grid[row][col] == '0') return;
    grid[row][col] = '0';
    for (int[] dir : DIRS) {
      dfs(grid, row + dir[0], col + dir[1]);
    }
  }

  public int trap(int[] height) {
    if (height == null || height.length == 0) return 0;
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

      res += Math.max(cur - height[l], 0);
      res += Math.max(cur - height[r], 0);
      if (height[l] <= height[r]) {
        l++;
      } else {
        r--;
      }
    }
    return res;
  }


  public int[][] merge(int[][] intervals) {
    if (intervals == null || intervals.length == 0) return intervals;
    // [0, i) j to explore
    Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
    int i = 1;
    int j = 1;

    while (j < intervals.length) {
      int[] cur = intervals[j];
      int start = cur[0];
      int end = cur[1];

      if (start >= intervals[i - 1][0] && start <= intervals[i - 1][1]) {
        intervals[i - 1][1] = Math.max(intervals[i - 1][1], end);
      } else {
        intervals[i++] = intervals[j];
      }
      j++;
    }
    return Arrays.copyOfRange(intervals, 0, i);
  }


  public int[] twoSum(int[] nums, int target) {
    if (nums == null || nums.length < 2) return new int[]{};
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      int curTarget = target - nums[i];
      if (map.containsKey(curTarget)) {
        return new int[]{map.get(curTarget), i};
      }
      map.put(nums[i], i);
    }
    return new int[]{};
  }

  int index = 0;

  public int calculate(String s) {
    if (s == null || s.length() == 0) return 0;
    int tmp = 0;
    int num = 0;
    int res = 0;
    char op = '+';

    while (index < s.length()) {
      char ch = s.charAt(index++);
      if (Character.isDigit(ch)) {
        tmp = tmp * 10 + ch - '0';
      } else if (ch == '(') {
        tmp = calculate(s);
      } else if (ch == ')') {
        break;
      } else if (ch != ' ') {
        num = cal(num, tmp, op);
        if (ch == '+' || ch == '-') {
          res += num;
          num = 0;
        }
        tmp = 0;
        op = ch;
      }
    }
    return res + cal(num, tmp, op);
  }

  private int cal(int num, int tmp, char op) {
    if (op == '+') return num + tmp;
    if (op == '-') return num - tmp;
    if (op == '*') return num * tmp;
    return num / tmp;
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
