package jan.twenty;

import org.junit.Test;

import java.util.Arrays;

public class Solution {

  @Test
  public void test() {
    int[] piles = {312884470};
    int res = minEatingSpeed(piles, 312884469);
  }




  public ListNode addTwoNumbers(ListNode one, ListNode two) {
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
      cur = cur.next;
      carry = sum / 10;
    }
    return dummy.next;
  }


  public int[][] merge(int[][] intervals) {
    if (intervals == null || intervals.length == 0) return intervals;

    Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
    int index = 0;
    for (int i = 1; i < intervals.length; i++) {
      int start = intervals[i][0];
      int end = intervals[i][1];

      if (start <= intervals[index][1]) {
        intervals[index][1] = Math.max(intervals[index][1], end);
      } else {
        index++;
        intervals[index][0] = start;
        intervals[index][1] = end;
      }
    }
    return Arrays.copyOf(intervals, index + 1);
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
          res++;
          dfs(grid, i, j);
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


  public int trap(int[] height) {
    if (height == null || height.length == 0) return 0;

    int m = height.length;
    int res = 0;
    int l = 0;
    int r = m - 1;
    int lMax = 0;
    int rMax = 0;

    while (l <= r) {
      lMax = Math.max(lMax, height[l]);
      rMax = Math.max(rMax, height[r]);

      int cur = Math.min(lMax, rMax);
      res += Math.max(cur - height[l], 0);
      res += Math.max(cur - height[r], 0);

      if (height[l] < height[r]) {
        l++;
      } else {
        r--;
      }
    }
    return res;
  }

  public int minEatingSpeed(int[] piles, int h) {
    /*
    27 : 8
    basic idea: sum them up / h, if (sum % h != 0, res++;
    !but, over flow
    what is better idea:
    could we don't do sum, avoid overflow

    what if we sort it first?

    30,11,23,4,20 || h = 6
    4, 11, 20, 23, 30
    i      20       j
    try to find the smallest k, use h hours
     */
    if (piles == null || piles.length == 0) return 0;
    // since we want to do binary search, we have to sort it first;
    Arrays.sort(piles);
    int m = piles.length;
    int l = 1;
    int r = piles[m - 1];

    while (l < r) {
      int mid = l + (r - l) / 2;
      int consumeHour = getHours(piles, mid);
      if (consumeHour <= h) {
        r = mid;
      } else {
        l = mid + 1;
      }
    }

    return l;
  }

  private int getHours(int[] piles, int hour) {
    /*
    every pile should have two situations,

    pile < hour,  consume 1
    pile = hour, consume 1;
    pile > hour, consume pile / hour, and if (pile % hour != 0) should + 1;

    so, consumeHours should be;
    consumeHour += pile / hour; case two, case three
    consumeHour += (pile % hour == 0) ? 0 : 1; case one, case three;
     */
    int consumeHour = 0;
    for (int pile : piles) {
      consumeHour += pile / hour;
      consumeHour += (pile % hour == 0) ? 0 : 1;
    }
    return consumeHour;
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


