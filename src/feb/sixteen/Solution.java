package feb.sixteen;

import org.junit.Test;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Solution {
  @Test
  public void test() {
    Vector2D vec = new Vector2D(new int[][]{{1, 2}, {3}, {4}});
    int one = vec.next();
    int two = vec.next();
    int three = vec.next();
    boolean b1 = vec.hasNext();
    boolean b2 = vec.hasNext();
    int four = vec.next();
    boolean b3 = vec.hasNext();
  }

  public int maxDepth(TreeNode root) {
    if (root == null) return 0;
    int left = maxDepth(root.left);
    int right = maxDepth(root.right);
    return Math.max(left, right) + 1;
  }

  public void wiggleSort(int[] nums) {
    if (nums == null || nums.length == 0) return;
    Arrays.sort(nums);
    // 0 1 2
    int i = (nums.length - 1) / 2;
    int j = nums.length - 1;
    int[] res = new int[nums.length];
    int index = 0;
    while (i >= 0 || j > (nums.length - 1) / 2) {
      if (index % 2 == 0) {
        res[index++] = nums[i--];
      } else {
        res[index++] = nums[j--];
      }
    }
    for (index = 0; index < nums.length; index++) nums[index] = res[index];
  }


  public void deleteNode(ListNode node) {
    node.val = node.next.val;
    node.next = node.next.next;
  }


  public boolean increasingTriplet(int[] nums) {
    int k = 3;
    int[] small = new int[k - 1];
    Arrays.fill(small, Integer.MAX_VALUE);
    for (int num : nums) {
      int i = 0;
      while (i < small.length && small[i] < num) i++;
      if (i < small.length) small[i] = num;
      else return true;
    }
    return false;
  }


  public int reverseBits(int n) {
    int res = 0;
    for (int i = 0; i < 32; i++) {
      res += n & 1;
      n >>>= 1;
      if (i < 31) {
        res <<= 1;
      }
    }
    return res;
  }

  public int hammingWeight(int n) {
    int count = 0;
    while (n != 0) {
      count += (n & 1);
      n >>>= 1;
    }
    return count;
  }

  public int trailingZeroes(int n) {
    int r = 0;
    // 1 2 3 4 5 6 7 8 9 10
    // n = 2   2
    while (n > 0) {
      n /= 5;
      r += n;
    }
    return r;
  }

  public TreeNode sortedArrayToBST(int[] nums) {
    if (nums == null || nums.length == 0) return null;
    return build(nums, 0, nums.length - 1);
  }

  private TreeNode build(int[] nums, int left, int right) {
    if (left > right) return null;
    int mid = left + (right - left) / 2;
    TreeNode root = new TreeNode(nums[mid]);
    root.left = build(nums, left, mid - 1);
    root.right = build(nums, mid + 1, right);
    return root;
  }


  static int[][] DIRS = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
  int m;
  int n;

  public int kthSmallest(int[][] matrix, int k) {
    if (matrix == null || matrix.length == 0) return 0;
    m = matrix.length;
    n = matrix[0].length;
    PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(matrix[a[0]][a[1]], matrix[b[0]][b[1]]));
    boolean[][] visited = new boolean[m][n];
    visited[0][0] = true;
    minHeap.offer(new int[]{0, 0});

    for (int i = 0; i < k; i++) {
      int[] cur = minHeap.poll();
      int r = cur[0];
      int c = cur[1];
      if (r + 1 < m && !visited[r + 1][c]) {
        minHeap.offer(new int[]{r + 1, c});
        visited[r + 1][c] = true;
      }
      if (c + 1 < n && !visited[r][c + 1]) {
        minHeap.offer(new int[]{r, c + 1});
        visited[r][c + 1] = true;
      }
    }
    int[] pos = minHeap.peek();
    return matrix[pos[0]][pos[1]];
  }


  private int countLessOrEqual(int[][] matrix, int x) {
    int cnt = 0;
    int c = n - 1;
    for (int r = 0; r < m; r++) {
      while (c >= 0 && matrix[r][c] > x) c--;
      cnt += (c + 1);
    }
    return cnt;
  }

  public ListNode sortList(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode prev = null, slow = head, fast = head;

    while (fast != null && fast.next != null) {
      prev = slow;
      slow = slow.next;
      fast = fast.next.next;
    }
    prev.next = null;
    ListNode one = sortList(head);
    ListNode two = sortList(slow);

    return merge(one, two);
  }

  private ListNode merge(ListNode one, ListNode two) {
    ListNode dummy = new ListNode(0);
    ListNode cur = dummy;

    while (one != null && two != null) {
      if (one.val < two.val) {
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

}
