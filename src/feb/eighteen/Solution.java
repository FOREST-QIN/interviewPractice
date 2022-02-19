package feb.eighteen;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    NumMatrix numMatrix = new NumMatrix(new int[][]{
        {8,-4,5},
        {-1,4,4},
        {-2, 3, 1},
        {-4,4,3}});
    int one = numMatrix.sumRegion(0, 1, 0, 2);
    int two = numMatrix.sumRegion(1, 1, 1, 2);
    int three = numMatrix.sumRegion(0, 1, 0, 2);
  }


  public int minSwap(int[] one, int[] two) {
    int m = one.length;
    int[] swap = new int[m];
    int[] no_swap = new int[m];
    swap[0] = 1;
    for (int i = 1; i < m; i++) {
      no_swap[i] = swap[i] = m;
      if (one[i - 1] < one[i] && two[i - 1] < two[i]) {
        swap[i] = swap[i - 1] + 1;
        no_swap[i] = no_swap[i - 1];
      }
      if (one[i - 1] < two[i] && two[i - 1] < one[i]) {
        swap[i] = Math.min(swap[i], no_swap[i - 1] + 1);
        no_swap[i] = Math.min(no_swap[i], swap[i - 1]);
      }
    }
    return Math.min(swap[m - 1], no_swap[m - 1]);
  }

  public int deleteAndEarn(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int[] values = new int[10001];
    for (int num : nums) values[num] += num;

    int prevTwo = 0;
    int prevOne = 0;
    for (int val : values) {
      int tmp = Math.max(val + prevTwo, prevOne);
      prevTwo = prevOne;
      prevOne = tmp;
    }
    return prevOne;
  }

  public int maxProfit(int[] prices) {
    /*
    dpPre: 0;
    hadStock's maxProfit: have: Integer.MIN_VALUE;
    noStock : 0;
    tmp = noStock
    buy : max(hadStock, dpPre - prices[i]
    sell: max(noSock, hadStock + prices[i])
    dpPre = tmp;
     */
    int m = prices.length;
    int noStock = 0;
    int hadStock = Integer.MIN_VALUE;
    int prevSold = 0;
    for (int i = 0; i < m; i++) {
      int tmp = noStock;
      noStock = Math.max(noStock, hadStock + prices[i]);
      hadStock = Math.max(hadStock, prevSold - prices[i]);
      prevSold = tmp;
    }
    return noStock;
  }

  public int rob(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    if (nums.length == 1) return nums[0];
    return Math.max(rob(nums, 0, nums.length - 2), rob(nums, 1, nums.length - 1));
  }

  private int rob(int[] nums, int start, int end) {
    int prevTwo = 0;
    int prevOne = nums[start];
    for (int i = start + 1; i <= end; i++) {
      int tmp = Math.max(nums[i] + prevTwo, prevOne);
      prevTwo = prevOne;
      prevOne = tmp;
    }
    return prevOne;
  }


  public int countSquares(int[][] matrix) {
    int res = 0;
    int m = matrix.length;
    int n = matrix[0].length;
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        if (matrix[i][j] == 1) {
          matrix[i][j] = min(matrix[i - 1][j], matrix[i][j - 1], matrix[i - 1][j - 1]) + 1;
        }
      }
    }
    for (int[] ints : matrix) {
      for (int j = 0; j < n; j++) {
        res += ints[j];
      }
    }
    return res;
  }

  private int min(int a, int b, int c) {
    return Math.min(a, Math.min(b, c));
  }


  public int maximalSquare(char[][] matrix) {
    if (matrix == null || matrix.length == 0) return 0;
    int m = matrix.length;
    int n = matrix[0].length;
    int maxSide = 0;
    int[][] dp = new int[m][n];
    for (int i = 0; i < m; i++) {
      dp[i][0] = matrix[i][0] == '1' ? 1 : 0;
      maxSide = Math.max(maxSide, dp[i][0]);
    }
    for (int i = 0; i < n; i++) {
      dp[0][i] = matrix[0][i] == '1' ? 1 : 0;
      maxSide = Math.max(maxSide, dp[0][i]);
    }
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        if (matrix[i][j] == '1') {
          dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
          maxSide = Math.max(dp[i][j], maxSide);
        }
      }
    }
    return maxSide * maxSide;
  }


  public int maximalRectangle(char[][] matrix) {
    if (matrix == null || matrix.length == 0) return 0;
    int[][] histogram = getHistogram(matrix);
    int res = 0;
    for (int i = 0; i < matrix.length; i++) {
      // get the largest histogram
      // helper function
      res = Math.max(res, getLargest(histogram[i]));
    }
    return res;
  }

  private int getLargest(int[] arr) {
    int res = 0;
    Deque<Integer> deque = new ArrayDeque<>();

    for (int i = 0; i <= arr.length; i++) {
      int cur = i == arr.length ? 0 : arr[i];
      while (!deque.isEmpty() && arr[deque.peekFirst()] >= cur) {
        int height = arr[deque.pollFirst()];
        int left = deque.isEmpty() ? 0 : deque.peekFirst() + 1;
        res = Math.max(res, height * (i - left));
      }
      deque.offerFirst(i);
    }
    return res;
  }


  private int[][] getHistogram(char[][] matrix) {
    int m = matrix.length;
    int n = matrix[0].length;
    int[][] prefix = new int[m][n];
    for (int i = 0; i < n; i++) {
      prefix[0][i] = matrix[0][i] - '0';
    }
    for (int i = 1; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (matrix[i][j] == '1') {
          prefix[i][j] = prefix[i - 1][j] + 1;
        }
      }
    }
    return prefix;
  }


  int[][] memo;
  int n;
  public int minFallingPathSum(int[][] matrix) {
    this.n = matrix.length;
    this.memo = new int[n][n];
    for (int[] arr : memo) Arrays.fill(arr, Integer.MAX_VALUE);
    int res = Integer.MAX_VALUE;
    for (int i = 0; i < n; i++) {
      res = Math.min(res, minFalling(matrix, 0, i));
    }
    return res;
  }

  private int minFalling(int[][] matrix, int i, int j) {
    // out of bound, return maxValue
    if (i < 0 || j < 0 || i >= n || j >= n) return Integer.MAX_VALUE;
    if (memo[i][j] != Integer.MAX_VALUE) return memo[i][j];
    if (i == n - 1) return memo[i][j] = matrix[i][j];

    memo[i][j] = matrix[i][j] + min(minFalling(matrix, i + 1, j - 1), minFalling(matrix, i + 1, j),
        minFalling(matrix, i + 1, j + 1));
    return memo[i][j];
  }




  int m;

  public int calculateMinimumHP(int[][] dungeon) {
    this.m = dungeon.length;
    this.n = dungeon[0].length;
    memo = new int[m][n];
    for (int[] arr : memo) Arrays.fill(arr,-1);
    return dp(dungeon, 0, 0);
  }

  // the min start blood from i,j to bottom right corner
  private int dp(int[][] grid, int i, int j) {
    if (i == m - 1 && j == n - 1) {
      return grid[i][j] >= 0 ? 1 : -grid[i][j] + 1;
    }
    if (i == m || j == n) return Integer.MAX_VALUE;
    if (memo[i][j] != -1) return memo[i][j];
    int res = Math.min(dp(grid, i, j + 1), dp(grid, i + 1, j)) - grid[i][j];
    memo[i][j] = res <= 0 ? 1 : res;
    return memo[i][j];
  }


  int[][] dp;
  public int minimumTotal(List<List<Integer>> triangle) {
    this.dp = new int[triangle.size()][triangle.size()];
    for (int[] arr : dp) Arrays.fill(arr, Integer.MIN_VALUE);
    return helper(0,0, triangle);
  }

  private int helper(int r, int c, List<List<Integer>> triangle) {
    if (r == triangle.size()) return 0;
    if (dp[r][c] != Integer.MIN_VALUE) return dp[r][c];

    return dp[r][c] = triangle.get(r).get(c) + Math.min(helper(r + 1, c, triangle), helper(r + 1, c + 1, triangle));
  }

  public int minPathSum(int[][] grid) {
    /*
    1 4 5
    2 5 1
    6 2 1
    grid[i][j] = minPath sum from grid[0][0] to grid[i][j]
    base case:

    grid[i][j] = grid[i][j] + min(grid[i - 1][j], grid[i][j - 1])
    return
     */
    if (grid == null || grid.length == 0) return 0;
    int m = grid.length;
    int n = grid[0].length;
    // top row
    for (int i = 1; i < n; i++) {
      grid[0][i] += grid[0][i - 1];
    }
    // left col
    for (int i = 1; i < m; i++) {
      grid[i][0] += grid[i - 1][0];
    }
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        grid[i][j] = grid[i][j] + Math.min(grid[i - 1][j], grid[i][j - 1]);
      }
    }
    return grid[m - 1][n - 1];
  }

  public int uniquePathsWithObstacles(int[][] grid) {
    if (grid == null || grid.length == 0
        || grid[0][0] == 1
        || grid[grid.length - 1][grid[0].length - 1] == 1) return 0;
    int m = grid.length;
    int n = grid[0].length;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] == 1) grid[i][j] = -1;
      }
    }
    for (int i = 0; i < n; i++) {
      if (i > 0 && grid[0][i - 1] == -1) grid[0][i] = -1;
      else if (grid[0][i] != -1) grid[0][i] = 1;

    }
    for (int i = 0; i < m; i++) {
      if (i > 0 && grid[i - 1][0] == -1) grid[i][0] = -1;
      else if (grid[i][0] != -1) grid[i][0] = 1;
    }

    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        if (grid[i][j] != -1) {
          if (grid[i - 1][j] != -1) grid[i][j] += grid[i - 1][j];
          if (grid[i][j - 1] != -1) grid[i][j] += grid[i][j - 1];
          if (grid[i][j] == 0) grid[i][j] = -1;
        }
      }
    }

    return grid[m - 1][n - 1] == -1 ? 0 : grid[m - 1][n - 1];
  }

  public int uniquePaths(int m, int n) {
    /*
    move down or right
    top-left to bottom-right
    dp[j] is the unique paths from top-left to [i][j]
    base case
    i == 0 || j == 0 dp[i][j] = 1;
    induction rule
    i [1, m)
      j [1, n)
    dp[j] = dp[j] + dp[j - 1]

     */
    if (m == 1 || n == 1) return 1;
    int[] dp = new int[n];
    for (int i = 0; i < n; i++) {
      dp[i] = 1;
    }
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        dp[j] = dp[j] + dp[j - 1];
      }
    }
    return dp[n - 1];
  }

  public int maxSubArray(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    /*
    dp[i] represents the max sub array sum ends at index i;
    base case: dp[0] = nums[0]
    i => [1, nums.length)
    dp[i] = Math.max(dp[i - 1], 0) + nums[i]
    max = Math.max(max, dp[i])

     */
    int m = nums.length;
    int max = nums[0];
    int prev = nums[0];
    for (int i = 1; i < m; i++) {
      int tmp = Math.max(prev, 0) + nums[i];
      max = Math.max(tmp, max);
      prev = tmp;
    }
    return max;
  }



  public int longestSubsequence(int[] arr, int difference) {
    /*
    arr = [1,2,3,4], difference = 1
           i
           memo[nums[i] - difference] + 1 compare to memo[i]
    map<the subsequence end element, length>

    linear scan:
    f(n) represents the  Longest Arithmetic Subsequence of Given Difference when the end element is n
    f(n) =max( map<n - difference> + 1, VS map(n))
        map[n] = f(n)

    memo[i] represents the  Longest Arithmetic Subsequence of Given Difference when the end element is n
     */
    // corner case check
    if (arr == null || arr.length == 0) return 0;
    // key is the end value, value is the longest length
    Map<Integer, Integer> map = new HashMap<>();
    // linear scan
    int res = 0;
    for (int i = 0; i < arr.length; i++) {
      int curTarget = arr[i] - difference;
      if (!map.containsKey(curTarget)) {
        map.put(arr[i], 1);
      } else {
        int curLength = map.get(curTarget) + 1;
        if (!map.containsKey(arr[i])) map.put(arr[i], curLength);
        else map.put(arr[i], Math.max(curLength, map.get(arr[i])));
      }
      res = Math.max(res, map.get(arr[i]));
    }
    return res;
  }

  public int tribonacci(int n) {
    if (n == 0) return 0;
    if (n == 1 || n == 2) return 1;
    int prevThree = 0;
    int prevTwo = 1;
    int prevOne = 1;

    for (int i = 3; i <= n; i++) {
      int cur = prevThree + prevTwo + prevOne;
      prevThree = prevTwo;
      prevTwo = prevOne;
      prevOne = cur;
    }
    return prevOne;
  }


  /*      0   1 2 3 4  5  6 7  8  9
  cost = [1,100,1,1,1,100,1,1,100,1]
                                 i

  prevTwo = 105
  prevOne = 6

  prevTwo => minCost in the last two steps
  prevOne => minCost in the last one step
  base case:
  prevTwo: cost[0]
  prevOne: cost[1]
  f(n) => minCost when current index is n
       => cost[i] + min(f(n - 1), f(n - 2))
   */
  public int minCostClimbingStairs(int[] cost) {
    if (cost == null || cost.length == 0) return 0;
    int prevTwo = cost[0];
    int prevOne = cost[1];

    for (int i = 2; i < cost.length; i++) {
      int cur = cost[i] + Math.min(prevTwo, prevOne);
      prevTwo = prevOne;
      prevOne = cur;
    }

    return Math.min(prevOne, prevTwo);
  }


  public int climbStairs(int n) {
    /*
    base case
    1 stair: 1
    2 stairs:  2

    n => from (n - 1) -> 1
         from (n - 2) -> 2
    f(n) => f(n - 1) + f(n - 2)
    f(n) is ways to climb n stairs
    prevTwo(1) = 1
    prevOne(2) = 2
    for [3, n]
     */
    // corner case:
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

}
