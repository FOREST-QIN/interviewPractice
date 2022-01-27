package jan.twentySix;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    int sum = uniqueLetterString("AAAAA");

  }

  int sum = 0;

  public int rangeSumBST(TreeNode root, int low, int high) {
    if (root == null) return 0;
    range(root, low, high);
    return sum;
  }

  private void range(TreeNode root, int low, int high) {
    if (root == null) return;
    if (root.val >= low && root.val <= high) sum += root.val;
    if (root.val > low) {
      range(root.left, low, high);
    }
    if (root.val < high) {
      range(root.right, low, high);
    }
  }


  public boolean isPalindrome(int x) {
    if (x < 0 || (x % 10 == 0 && x != 0)) return false;

    int reverseNum = 0;
    while (x > reverseNum) {
      reverseNum = reverseNum * 10 + x % 10;
      x /= 10;
    }
    return x == reverseNum || x == reverseNum / 10;

  }

  public int[] topKFrequent(int[] nums, int k) {
    int[] res = new int[k];
    Map<Integer, Integer> count = new HashMap<>();
    for (int num : nums) {
      count.put(num, count.getOrDefault(num, 0) + 1);
    }

    PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b.getValue(), a.getValue()));

    for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
      maxHeap.offer(entry);
    }

    for (int i = 0; i < k; i++) {
      res[i] = maxHeap.poll().getKey();
    }
    return res;
  }

  public List<List<Integer>> verticalOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;

    Map<Integer, List<Integer>> map = new HashMap<>();
    Queue<TreeNode> q = new LinkedList<>();
    Map<TreeNode, Integer> weight = new HashMap<>();

    q.offer(root);
    weight.put(root, 0);
    int min = 0;

    while (!q.isEmpty()) {
      TreeNode cur = q.poll();
      int w = weight.get(cur);
      map.putIfAbsent(w, new ArrayList<>());
      map.get(w).add(cur.val);

      if (cur.left != null) {
        q.offer(cur.left);
        weight.put(cur.left, w - 1);
      }
      if (cur.right != null) {
        q.offer(cur.right);
        weight.put(cur.right, w + 1);
      }
      min = Math.min(min, w);
    }
    while (map.containsKey(min)) {
      res.add(map.get(min++));
    }
    return res;
  }


  public int uniqueLetterString(String s) {
    int sum = 0;
    int cur = 0;
    int m = s.length();
    int[] last = new int[26];
    int[] prev = new int[26];

    Arrays.fill(last, -1);
    Arrays.fill(prev, -1);

    char[] array = s.toCharArray();
    for (int i = 0; i < m; i++) {
      char ch = s.charAt(i);
      int l = last[ch - 'A'] - prev[ch - 'A'];
      int f = i - last[ch - 'A'] - 1;
      cur += f - l + 1;
      sum += cur;
      prev[ch - 'A'] = last[ch - 'A'];
      last[ch - 'A'] = i;
    }
    return sum;
  }


  public int search(int[] nums, int target) {
    /*
    [4,5,6,7,0,1,2],
     l           r
     */
    if (nums == null || nums.length == 0) return -1;
    return helper(nums, 0, nums.length - 1, target);
  }

  private int helper(int[] nums, int left, int right, int target) {
    if (left > right) return -1;
    if (left == right) return nums[left] == target ? left : -1;

    if (nums[left] > nums[right]) {
      int mid = left + (right - left) / 2;
      int lRes = helper(nums, left, mid, target);
      int rRes = helper(nums, mid + 1, right, target);
      return lRes == -1 ? rRes : lRes;
    }

    while (left <= right) {
      int mid = left + (right - left) / 2;

      if (nums[mid] == target) {
        return mid;
      } else if (target > nums[mid]) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }
    return -1;
  }


  public List<String> letterCombinations(String digits) {
    List<String> res = new ArrayList<>();
    if (digits == null || digits.length() == 0) return res;
    Map<Character, String> map = getMap();

    dfs(digits, 0, new StringBuilder(), res, map);
    return res;
  }

  private void dfs(String digits, int index, StringBuilder prefix, List<String> res, Map<Character, String> map) {
    if (index == digits.length()) {
      res.add(prefix.toString());
      return;
    }

    for (char ch : map.get(digits.charAt(index)).toCharArray()) {
      prefix.append(ch);
      dfs(digits, index + 1, prefix, res, map);
      prefix.deleteCharAt(prefix.length() - 1);
    }

  }


  private Map<Character, String> getMap() {
    Map<Character, String> map = new HashMap<>();
    map.put('2', "abc");
    map.put('3', "def");
    map.put('4', "ghi");
    map.put('5', "jkl");
    map.put('6', "mno");
    map.put('7', "pqrs");
    map.put('8', "tuv");
    map.put('9', "wxyz");
    return map;
  }


  public List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> res = new ArrayList<>();
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return res;
    int m = matrix.length;
    int n = matrix[0].length;
    int left = 0;
    int right = n - 1;
    int top = 0;
    int bottom = m - 1;

    while (left < right && top < bottom) {
      for (int i = left; i < right; i++) {
        res.add(matrix[top][i]);
      }
      for (int i = top; i < bottom; i++) {
        res.add(matrix[i][right]);
      }
      for (int i = right; i > left; i--) {
        res.add(matrix[bottom][i]);
      }
      for (int i = bottom; i > top; i--) {
        res.add(matrix[i][left]);
      }
      left++;
      right--;
      top++;
      bottom--;
    }
    if (left > right || top > bottom) {
      return res;
    }
    if (left == right) {
      for (int i = top; i <= bottom; i++) {
        res.add(matrix[i][left]);
      }
    } else {
      for (int i = left; i <= right; i++) {
        res.add(matrix[bottom][i]);
      }
    }
    return res;
  }


  public int countBinarySubstrings(String s) {
    /*
     0 0 1 1 0 0 1 1
            i

   (0,2)
     */
    int zero = 0;
    int one = 0;
    int ans = 0;
    int i = 0;
    int m = s.length();
    while (i < m) {
      while (i < m && s.charAt(i) == '1') {
        one++;
        i++;
      }
      ans += Math.min(one, zero);
      zero = 0;
      while (i < m && s.charAt(i) == '0') {
        zero++;
        i++;
      }
      ans += Math.min(one, zero);
      one = 0;
    }
    return ans;
  }

  public int[] maxSlidingWindow(int[] nums, int k) {
    /*
      0 1  2  3 4 5
      1,3,-1,-3,5,3,6,7
               [6]
      3, 3,5 5 6 7

     use a deque to record the index of elements could be max of in the future could be max
   */
    int m = nums.length;
    int[] res = new int[m - k + 1];
    int index = 0;
    Deque<Integer> deque = new ArrayDeque<>();

    for (int i = 0; i < m; i++) {
      while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
        deque.pollLast();
      }
      if (!deque.isEmpty() && deque.peekFirst() <= i - k) {
        deque.pollFirst();
      }
      deque.offerLast(i);
      if (i >= k - 1) {
        res[index++] = nums[deque.peekFirst()];
      }
    }
    return res;
  }


  public class TreeNode {
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

}
