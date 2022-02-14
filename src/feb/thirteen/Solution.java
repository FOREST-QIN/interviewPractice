package feb.thirteen;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public static void main(String[] args) {
    Trie root = new Trie();
    root.insert("apple");
    root.search("apple");
    root.search("app");
    root.startsWith("app");
    root.insert("app");
    root.search("app");
  }

  public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);

    while (!q.isEmpty()) {
      int size = q.size();
      List<Integer> curLayer = new ArrayList<>();
      for (int i = 0; i < size; i++) {
        TreeNode cur = q.poll();
        if (cur.left != null) q.offer(cur.left);
        if (cur.right != null) q.offer(cur.right);
        curLayer.add(cur.val);
      }
      res.add(curLayer);
    }
    return res;
  }

  public void reverseString(char[] s) {
    int i = 0;
    int j = s.length - 1;
    while (i <= j) {
      char tmp = s[i];
      s[i] = s[j];
      s[j] = tmp;
      i++;
      j--;
    }
  }

  private void reverse(int[] nums, int i, int j) {
    while (i <= j) {
      swap(nums, i++, j--);
    }
  }

  private void swap(int[] nums, int i, int j) {
    int tmp = nums[i];
    nums[i] = nums[j];
    nums[j] = tmp;
  }


  public ListNode oddEvenList(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode odd = new ListNode(0);
    ListNode o = odd;
    ListNode even = new ListNode(0);
    ListNode e = even;

    boolean isEven = false;
    while (head != null) {
      if (isEven) {
        e.next = head;
        e = e.next;
      } else {
        o.next = head;
        o = o.next;
      }
      head = head.next;
      isEven = !isEven;
    }
    e.next = null;
    o.next = even.next;
    return odd.next;
  }

  int m;
  int n;
  int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
  char CANNOT_FLIPPED = '*';
  /*
  [
  ["O","X","X","O","X"]
  ["X","O","O","X","O"],
  ["X","O","X","O","X"],
  ["O","X","O","O","O"],
  ["X","X","O","X","O"]]

   [["O","X","X","O","X"],
   ["X","X","X","X","O"],
   ["X","X","X","O","X"],
   ["O","X","O","O","O"],
   ["X","X","O","X","O"]]


   */

  public void rotate(int[] nums, int k) {
    if (nums == null || nums.length <= 1) return;
    k = k % nums.length;
    reverse(nums, 0, nums.length - 1);
    reverse(nums, 0, k - 1);
    reverse(nums, k, nums.length - 1);
  }



  public void solve(char[][] board) {
    if (board == null || board.length == 0) return;
    this.m = board.length;
    this.n = board[0].length;
    for (int i = 0; i < m; i++) {
      helper(board, i, 0);
      helper(board, i, n - 1);
    }
    for (int i = 0; i < n; i++) {
      helper(board, 0, i);
      helper(board, m - 1, i);
    }

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (board[i][j] == 'O') board[i][j] = 'X';
      }
    }
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (board[i][j] == CANNOT_FLIPPED) board[i][j] = 'O';
      }
    }
  }

  private void helper(char[][] board, int i, int j) {
    if (i < 0 || j < 0 || i >= m || j >= n || board[i][j] == 'X' || board[i][j] == CANNOT_FLIPPED) return;
    board[i][j] = CANNOT_FLIPPED;
    for (int[] dir : DIRS) {
      int x = i + dir[0];
      int y = j + dir[1];
      helper(board, x, y);
    }
  }


  public int singleNumber(int[] nums) {
    if (nums.length == 1) return nums[0];
    int cur = nums[0];
    for (int i = 1; i < nums.length; i++) {
      cur = cur ^ nums[i];
    }
    return cur;
  }


  public int myAtoi(String s) {
    if (s == null) return 0;
    int len = s.length();
    if (len == 0) return 0;
    int idx = 0;
    while (idx < len && s.charAt(idx) == ' ') idx++;
    if (idx == len) return 0;
    int sign = 1;
    char c = s.charAt(idx);
    if (c == '-' || c == '+') {
      if (c == '-') sign = -1;
      idx++;
    }
    int res = 0;
    while (idx < len && Character.isDigit(s.charAt(idx))) {
      int digit = s.charAt(idx - '0');
      if (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
        return sign == -1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
      }
      res = res * 10 + digit;
      idx++;
    }
    return sign * res;
  }

  public boolean searchMatrix(int[][] matrix, int target) {
    /*
        each row in ascending order left -> right
        each col in ascending order top -> bottom

        at start matrix[0][cols - 1];
        if (target < matrix[i][j]) j--;
        if (target > matrix[i][j] i++;
        if (target == matrix[i][j] return true

     */
    if (matrix == null || matrix.length == 0) return false;
    int m = matrix.length;
    int n = matrix[0].length;

    int i = 0;
    int j = n - 1;

    while (i < m && j >= 0) {
      if (target > matrix[i][j]) {
        i++;
      } else if (target < matrix[i][j]) {
        j--;
      } else {
        return true;
      }
    }
    return false;
  }


  public String countAndSay(int n) {
    if (n < 1 || n > 30) return "";
    if (n == 1) return "1";
    StringBuilder res = new StringBuilder();
    res.append(1);
    for (int i = 2; i <= n; i++) {
      StringBuilder cur = new StringBuilder();
      int slow = 0;
      int fast = 1;
      while (fast < res.length()) {
        if (res.charAt(slow) != res.charAt(fast)) {
          cur.append(fast - slow);
          cur.append(res.charAt(slow));
          slow = fast;
        }
        fast++;
      }
      cur.append(fast - slow);
      cur.append(res.charAt(slow));
      res = cur;
    }
    return res.toString();
  }


  public List<Integer> countSmaller(int[] nums) {
    List<Integer> res = new ArrayList<>();
    if (nums == null || nums.length == 0) return res;

    int[] countArray = new int[nums.length];
    int[] helperArray = new int[nums.length];
    int[] indexArray = new int[nums.length];

    for (int i = 0; i < nums.length; i++) indexArray[i] = i;
    countArray(nums, countArray, indexArray, helperArray, 0, nums.length - 1);
    for (int j : countArray) {
      res.add(j);
    }
    return res;
  }

  public void countArray(int[] array, int[] countArray, int[] indexArray, int[] helperArray, int left, int right) {
    if (left >= right) return;
    int mid = left + (right - left) / 2;
    countArray(array, countArray, indexArray, helperArray, left, mid);
    countArray(array, countArray, indexArray, helperArray, mid + 1, right);
    merge(array, countArray, indexArray, helperArray, left, mid, right);
  }

  private void merge(int[] array, int[] countArray, int[] indexArray, int[] helperArray, int left, int mid, int right) {
    copyArray(indexArray, helperArray, left, right);
    int l = left;
    int r = mid + 1;
    int cur = left;
    while (l <= mid) {
      if (r > right || array[helperArray[r]] >= array[helperArray[l]]) {
        countArray[helperArray[l]] += (r - mid - 1);
        indexArray[cur++] = helperArray[l++];
      } else {
        indexArray[cur++] = helperArray[r++];
      }
    }
  }

  private void copyArray(int[] array, int[] helper, int left, int right) {
    for (int i = left; i <= right; i++) {
      helper[i] = array[i];
    }
  }

  public int divide(int dividend, int divisor) {
    if (dividend == 1 << 31 && divisor == -1) return (1 << 31) - 1;
    int a = Math.abs(dividend);
    int b = Math.abs(divisor);
    int res = 0;
    int x = 0;
    while (a - b >= 0) {
      for (x = 0; a - (b << x << 1) >= 0; x++) ;
      res += 1 << x;
      a -= b << x;
    }
    return (dividend > 0) == (divisor > 0) ? res : -res;
  }

  public List<List<String>> partition(String s) {
    List<List<String>> res = new ArrayList<>();
    boolean[][] dp = new boolean[s.length()][s.length()];
    for (int i = 0; i < s.length(); i++) {
      for (int j = 0; j <= i; j++) {
        if (s.charAt(i) == s.charAt(j) && (i - j <= 2 || dp[j + 1][i - 1])) dp[j][i] = true;
      }
    }
    helper(s, 0, new ArrayList<>(), res, dp);
    return res;
  }

  private void helper(String s, int index, List<String> cur, List<List<String>> res, boolean[][] dp) {
    if (index == s.length()) {
      res.add(new ArrayList<>(cur));
      return;
    }
    for (int i = index; i < s.length(); i++) {
      if (dp[index][i]) {
        cur.add(s.substring(index, i + 1));
        helper(s, i + 1, cur, res, dp);
        cur.remove(cur.size() - 1);
      }
    }
  }


  public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    ListNode slow = dummy;
    ListNode fast = dummy;
    dummy.next = head;
    for (int i = 0; i <= n; i++) {
      fast = fast.next;
    }
    while (fast != null) {
      fast = fast.next;
      slow = slow.next;
    }
    slow.next = slow.next.next;
    return dummy.next;
  }

  public Node connect(Node root) {
    if (root == null) return root;
    Queue<Node> q = new LinkedList<>();
    q.offer(root);

    while (!q.isEmpty()) {
      int size = q.size();
      Node right = null;
      for (int i = 0; i < size; i++) {
        Node cur = q.poll();
        cur.next = right;
        if (cur.right != null) q.offer(cur.right);
        if (cur.left != null) q.offer(cur.left);
        right = cur;
      }
    }
    return root;
  }

  public int mySqrt(int x) {
    if (x == 0 || x == 1) return x;
    int left = 1;
    int right = x;
    while (left < right) {
      int mid = left + (right - left) / 2;
      if (mid <= x / mid && (mid + 1) > x / (mid + 1)) {
        return mid;
      } else if (mid > x / mid) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }
    return left;
  }

  public int largestRectangleArea(int[] heights) {
    if (heights == null || heights.length == 0) return 0;
    Deque<Integer> stack = new ArrayDeque<>();
    int max = 0;
    for (int i = 0; i <= heights.length; i++) {
      int h = i == heights.length ? 0 : heights[i];
      while (!stack.isEmpty() && heights[stack.peekFirst()] >= h) {
        int cur = heights[stack.pollFirst()];
        int left = stack.isEmpty() ? 0 : stack.peekFirst() + 1;
        max = Math.max(max, (i - left) * cur);
      }
      stack.offerFirst(i);
    }
    return max;
  }

  public int majorityElement(int[] nums) {
    int tmp = nums[0];
    int count = 1;

    for (int i = 1; i < nums.length; i++) {
      // nums[i] == tmp, count++
      // nums[i] != tmp : count > 0 count --
      //                  count = 0 tmp nums[i], count = 1;
      if (nums[i] == tmp) {
        count++;
      } else {
        if (count > 0) {
          count--;
        } else {
          tmp = nums[i];
          count = 1;
        }
      }
    }
    return tmp;
  }


  public int countPrimes(int n) {
    boolean[] isPrime = new boolean[n];
    int count = 0;
    for (int i = 2; i * i <= n; i++) {
      if (!isPrime[i]) {
        for (int j = 2 * i; j < n; j += i) {
          isPrime[j] = true;
        }
      }
    }
    for (int i = 2; i < isPrime.length; i++) {
      if (!isPrime[i]) count++;
    }
    return count;
  }


  public boolean isSymmetric(TreeNode root) {
    if (root == null) return true;
    return check(root.left, root.right);
  }

  private boolean check(TreeNode one, TreeNode two) {
    if (one == null && two == null) {
      return true;
    } else if (one == null || two == null) {
      return false;
    } else if (one.val != two.val) {
      return false;
    }
    return (check(one.left, two.left) && check(one.right, two.right));
  }


  public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
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

  public void setZeroes(int[][] matrix) {
    if (matrix == null || matrix.length == 0) return;
    int m = matrix.length;
    int n = matrix[0].length;
    int[] rows = new int[m];
    int[] cols = new int[n];

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (matrix[i][j] == 0) {
          rows[i] = 1;
          cols[j] = 1;
        }
      }
    }

    for (int i = 0; i < m; i++) {
      if (rows[i] == 1) {
        for (int j = 0; j < n; j++) matrix[i][j] = 0;
      }
    }
    for (int i = 0; i < n; i++) {
      if (cols[i] == 1) {
        for (int j = 0; j < m; j++) matrix[j][i] = 0;
      }
    }

  }


  public int missingNumber(int[] nums) {
    /*
    approach one: get the sum between [0, n] and traverse nums and -num
     0 1 2 3 4 5 6 7 8
    [9,0,4,2,3,5,6,7,1]

     */
    int n = nums.length;
    for (int i = 0; i < n; i++) {
      if (nums[i] == n) continue;
      while (nums[i] != i && nums[i] != n) {
        swap(nums, i, nums[i]);
      }
    }

    for (int i = 0; i < n; i++) {
      if (i != nums[i]) return i;
    }
    return n;
  }




  public int numSquares(int n) {

    // getMinSquareNum(int n)
    // for (1 : n)
    /*
          int cur min num
          for (i * i < n ) {
              min (curMin, getMinSquareNum(n - i * i))
          }
          dp[n + 1] dp[i] represents the min nums of square to get i
          dp[0] 0;
          dp[1] 1
          fill dp = MAX;
          for (int i = 2; i <= n; i++) {
              for (int j = 1; j * j <= i; j++) {
                 if (dp[i - j * j] != MAX) {
                    dp[i] = min(dp[i], dp[i - j * j] + 1)
                 }

              }


          }

         13
         0 1 2 3 4 5 6 7 8 9 10 11 12 13
         0 1 2 3 1 2 3 4 2 1  2  3 3  2

                    dp[13 - 1] = 3
                    dp[13 - 2*2}=1
                    dp[13 - 3*3] 4
                    1 / 2
     */
    if (n == 0 || n == 1) return n;
    int[] dp = new int[n + 1];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;
    dp[1] = 1;

    for (int i = 2; i <= n; i++) {
      for (int j = 1; j * j <= i; j++) {
        if (dp[i - j * j] != Integer.MIN_VALUE) {
          dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
        }
      }
    }
    return dp[n];
  }


  public int removeDuplicates(int[] nums) {
    /*
    1,1,1,1,2,3,4,4,5
      i
      j
    i : [0, i) no duplicate increasing elements
    j : to explore the whole array
    case one: nums[j] == nums[i - 1], j++;
    case two nums[j] != nums[i - 1] , nums[i++] = nums[j]
     */
    if (nums == null || nums.length == 0) return 0;
    int i = 1;
    int j = 1;
    while (j < nums.length) {
      // case one: two chars is the same
      if (nums[i - 1] != nums[j]) {
        nums[i++] = nums[j];
      }
      j++;
    }
    return i;
  }


  public List<List<Integer>> getSkyline(int[][] buildings) {
    BuildingPoint[] buildingPoints = new BuildingPoint[buildings.length * 2];
    int index = 0;
    for (int[] building : buildings) {
      buildingPoints[index] = new BuildingPoint();
      buildingPoints[index].x = building[0];
      buildingPoints[index].height = building[2];
      buildingPoints[index].isStart = true;

      buildingPoints[index + 1] = new BuildingPoint();
      buildingPoints[index + 1].x = building[1];
      buildingPoints[index + 1].height = building[2];
      buildingPoints[index + 1].isStart = false;
      index += 2;
    }
    Arrays.sort(buildingPoints);

    TreeMap<Integer, Integer> maxHeap = new TreeMap<>();
    maxHeap.put(0, 1);
    int prevMaxHeight = 0;
    List<List<Integer>> res = new ArrayList<>();
    for (BuildingPoint point : buildingPoints) {
      if (point.isStart) {
        maxHeap.compute(point.height, (key, value) -> {
          if (value != null) {
            return value + 1;
          }
          return 1;
        });
      } else {
        maxHeap.compute(point.height, (key, value) -> {
          if (value == 1) {
            return null;
          }
          return value - 1;
        });
      }
      int curMaxHeight = maxHeap.lastKey();
      if (prevMaxHeight != curMaxHeight) {
        res.add(Arrays.asList(point.x, curMaxHeight));
        prevMaxHeight = curMaxHeight;
      }
    }
    return res;
  }

  static class BuildingPoint implements Comparable<BuildingPoint> {
    int x;
    boolean isStart;
    int height;

    @Override
    public int compareTo(BuildingPoint o) {
      if (this.x != o.x) {
        return this.x - o.x;
      } else {
        return (this.isStart ? -this.height : this.height) - (o.isStart ? -o.height : o.height);
      }
    }
  }

  public boolean isValidBST(TreeNode root) {
    if (root == null || (root.left == null && root.right == null)) return true;
    return check(root, null, null);
  }

  private boolean check(TreeNode root, Integer min, Integer max) {
    if (root == null) return true;

    if (min != null && root.val <= min) return false;
    if (max != null && root.val >= max) return false;
    if (!check(root.left, min, root.val)) return false;
    return check(root.right, root.val, max);
  }

  public String largestNumber(int[] nums) {
    // corner case check
    if (nums == null || nums.length == 0) return "";
    String[] arr = new String[nums.length];
    for (int i = 0; i < nums.length; i++) {
      arr[i] = String.valueOf(nums[i]);
    }

    /*
    30
    3
    330
    303

     */
    Arrays.sort(arr, (a, b) -> (b + a).compareTo(a + b));
    if (arr[0].charAt(0) == '0') return "0";
    StringBuilder prefix = new StringBuilder();
    for (String s : arr) prefix.append(s);
    return prefix.toString();
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
    public Node left;
    public Node right;
    public Node next;

    public Node() {
    }

    public Node(int _val) {
      val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
      val = _val;
      left = _left;
      right = _right;
      next = _next;
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
