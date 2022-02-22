package feb.twentyOne;

import java.util.HashMap;
import java.util.Map;

public class Solution {
  Map<Integer, Integer> map;
  int count;
  int max;
  public int[] findMode(TreeNode root) {
    this.map = new HashMap<>();
    this.count = 0;
    this.max = 0;
    traverse(root);
    int[] res = new int[count];
    int index = 0;
    for (int key : map.keySet()) {
      if (map.get(key) == max) res[index++] = key;
    }
    return res;
  }

  private void traverse(TreeNode root) {
    if (root == null) return;
    traverse(root.left);
    map.put(root.val, map.getOrDefault(root.val, 0) + 1);
    if (map.get(root.val) == max) {
      count++;
    } else if (map.get(root.val) > max) {
      count = 1;
      max = map.get(root.val);
    }
    traverse(root.right);
  }

  public TreeNode sortedArrayToBST(int[] nums) {
    if (nums == null || nums.length == 0) return null;
    return recover(nums, 0, nums.length - 1);
  }

  private TreeNode recover(int[] nums, int left, int right) {
    if (left == right) return new TreeNode(nums[left]);
    if (left > right) return null;
    int mid = left + (right - left) / 2;
    TreeNode root = new TreeNode(nums[mid]);
    root.left = recover(nums, left, mid - 1);
    root.right = recover(nums, mid + 1, right);
    return root;
  }

  TreeNode prev;
  TreeNode[] swap;
  public void recoverTree(TreeNode root) {
    this.prev = null;
    this.swap = new TreeNode[2];
    recover(root);
    if (swap[0] != null && swap[1] != null) {
      int tmp = swap[0].val;
      swap[0].val = swap[1].val;
      swap[1].val = tmp;
    }
  }

  private void recover(TreeNode root) {
    if (root == null) return;
    recover(root.left);
    if (prev != null && prev.val > root.val) {
      if (swap[0] == null) {
        swap[0] = prev;
      }
      swap[1] = root;
    }
    prev = root;
    recover(root.right);
  }


  int res;
  public int kthSmallest(TreeNode root, int k) {
    this.res = 0;
    this.count = 0;
    helper(root, k);
    return res;
  }

  private void helper(TreeNode root, int k) {
    if (root == null) return;
    helper(root.left, k);
    count++;
    if (count == k) {
      res = root.val;
      return;
    }
    helper(root.right, k);
  }



  public TreeNode deleteNode(TreeNode root, int key) {
    if (root == null) return null;
    if (root.val == key) {
      // remove;
      if (root.left == null) return root.right;
      if (root.right == null) return root.left;
      TreeNode minNode = getMinNode(root.right);
      root.right = deleteNode(root.right, minNode.val);
      minNode.left = root.left;
      minNode.right = root.right;
      return minNode;
    } else if (root.val > key) {
      root.left = deleteNode(root.left, key);
    } else {
      root.right = deleteNode(root.right, key);
    }
    return root;
  }

  private TreeNode getMinNode(TreeNode root) {
    TreeNode cur = root;
    while (cur.left != null) cur = cur.left;
    return cur;
  }


  public TreeNode insertIntoBST(TreeNode root, int val) {
    if (root == null) return new TreeNode(val);
    if (root.val < val) {
      root.right = insertIntoBST(root.right, val);
    } else if (root.val > val) {
      root.left = insertIntoBST(root.left, val);
    }
    return root;
  }

  public boolean isValidBST(TreeNode root) {
    if (root == null) return true;
    return check(root, null, null);
  }

  private boolean check(TreeNode root, Integer min, Integer max) {
    if (root == null) return true;
    if (min != null && root.val <= min) return false;
    if (max != null && root.val >= max) return false;
    if (!check(root.left, min, root.val)) return false;
    return check(root.right, root.val, max);
  }

  int minDifference;

  public int getMinimumDifference(TreeNode root) {
    this.minDifference = Integer.MAX_VALUE;
    getMinDiff(root);
    return minDifference;
  }

  private void getMinDiff(TreeNode root) {
    if (root == null) return;
    getMinDiff(root.left);
    if (prev != null) minDifference = Math.min(minDifference, Math.abs(prev.val - root.val));
    prev = root;
    getMinDiff(root.right);
  }



  public TreeNode searchBST(TreeNode root, int val) {
    if (root == null) return null;
    if (root.val == val) {
      return root;
    }
    if (root.val > val) {
      return searchBST(root.left, val);
    }
    return searchBST(root.right, val);
  }



  public int shipWithinDays(int[] weights, int days) {
    int left = 0, right = 0;
    for (int w : weights) {
      left = Math.max(left, w);
      right += w;
    }
    while (left < right - 1) {
      int mid = left + (right - left) / 2;
      int needs = getDays(weights, mid);
      if (needs > days) left = mid + 1;
      else right = mid;
    }
    if (getDays(weights, right) <= days) return right;
    return left;
  }


  private int getDays(int[] weights, int cap) {
    int count = 1;
    int cur = cap;
    for (int w : weights) {
      if (w > cur) {
        count++;
        cur = cap;
      }
      cur -= w;
    }
    return count;
  }

  public int minEatingSpeed(int[] piles, int h) {
    int left = 1;
    int right = 0;
    for (int p : piles) right = Math.max(right, p);
    while (left < right) {
      int mid = left + (right - left) / 2;
      if (canEat(piles, mid, h)) {
        right = mid;
      } else {
        left = mid + 1;
      }
    }
    if (canEat(piles, left, h)) return left;
    return right;
  }

  private boolean canEat(int[] piles, int k, int h) {
    int count = 0;
    for (int p : piles) {
      count += p / k;
      if (p % k != 0) count++;
    }
    return count <= h;
  }


  public boolean searchMatrix(int[][] matrix, int target) {
    if (matrix == null || matrix.length == 0) return false;
    int m = matrix.length;
    int n = matrix[0].length;
    int r = 0;
    int c = n - 1;
    while (r < m && c >= 0) {
      if (target > matrix[r][c]) {
        r++;
      } else if (target < matrix[r][c]) {
        c--;
      } else {
        return true;
      }
    }
    return false;
  }

  public boolean searchMatrixOne(int[][] matrix, int target) {
    if (matrix == null || matrix.length == 0) return false;
    int m = matrix.length;
    int n = matrix[0].length;

    int l = 0;
    int r = m * n - 1;
    int mid = l + (r - l) / 2;
    while (l <= r) {
      mid = l + (r - l) / 2;
      int row = mid / n;
      int col = mid % n;
      if (matrix[row][col] == target) {
        return true;
      } else if (target > matrix[row][col]) {
        l = mid + 1;
      } else {
        r = mid - 1;
      }
    }
    return false;
  }

  public int mySqrt(int x) {
    if (x == 0 || x == 1) return x;
    int left = 1;
    int right = x;
    while (left < right) {
      int mid = left + (right - left) / 2;
      if (mid <= x / mid && (mid + 1) > x / (mid + 1)) return mid;
      if (mid > x / mid) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }
    return left;
  }

  public int peakIndexInMountainArray(int[] arr) {
    if (arr == null || arr.length == 0) return -1;
    int l = 0;
    int r = arr.length - 1;
    while (l < r) {
      int mid = l + (r - l) / 2;
      if (arr[mid] < arr[mid + 1]) {
        l = mid + 1;
      } else {
        r = mid;
      }
    }
    return l;
  }


  public int findPeakElement(int[] nums) {
    if (nums.length == 1) return 0;
    int l = 0;
    int r = nums.length - 1;
    int mid = l + (r - l) / 2;
    while (l < r) {
      mid = l + (r - l) / 2;
      if (nums[mid] < nums[mid + 1]) l = mid + 1;
      else r = mid;
    }
    return l;
  }


  public int findMin(int[] nums) {
    return findMin(nums, 0, nums.length - 1);
  }

  private int findMin(int[] nums, int left, int right) {
    if (left == right) return nums[left];
    if (left > right) return Integer.MAX_VALUE;
    if (nums[left] < nums[right]) {
      return nums[left];
    } else {
      int mid = left + (right - left) / 2;
      int lRes = findMin(nums, left, mid);
      int rRes = findMin(nums, mid + 1, right);
      return Math.min(lRes, rRes);
    }
  }


  public boolean search(int[] nums, int target) {
    if (nums == null || nums.length == 0) return false;

    int l = 0;
    int r = nums.length - 1;

    while (l <= r) {
      int mid = l + (r - l) / 2;
      if (nums[mid] == target) return true;
      while (l < mid && nums[l] == nums[mid] && nums[r] == nums[mid]) {
        l++;
        r--;
      }
      if (nums[l] <= nums[mid]) {
        if (target >= nums[l] && target < nums[mid]) {
          r = mid - 1;
        } else {
          l = mid + 1;
        }
      } else {
        if (target > nums[mid] && target <= nums[r]) {
          l = mid + 1;
        } else {
          r = mid - 1;
        }
      }
    }
    return false;
  }


  public int searchOne(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;
    return binarySearch(nums, 0, nums.length - 1, target);
  }

  private int binarySearch(int[] nums, int left, int right, int target) {
    if (left == right) return nums[left] == target ? left : -1;
    if (left > right) return -1;
    // do binary search
    if (nums[left] < nums[right]) {
      while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) return mid;
        if (target > nums[mid]) {
          left = mid + 1;
        } else {
          right = mid - 1;
        }
      }
      return -1;
    } else {
      int mid = left + (right - left) / 2;
      int lMid = binarySearch(nums, left, mid, target);
      int rMid = binarySearch(nums, mid + 1, right, target);
      return lMid == -1 ? rMid : lMid;
    }
  }


  public int searchB(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;
    int l = 0;
    int r = nums.length - 1;

    while (l <= r) {
      int mid = l + (r - l) / 2;
      if (nums[mid] == target) return mid;
      if (target > nums[mid]) {
        l = mid + 1;
      } else {
        r = mid - 1;
      }
    }
    return -1;
  }

  public int[] searchRange(int[] nums, int target) {
    return new int[]{findTheFirst(nums, target), findTheLast(nums, target)};
  }

  private int findTheFirst(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;
    int l = 0;
    int r = nums.length - 1;
    while (l < r - 1) {
      int mid = l + (r - l) / 2;
      if (nums[mid] == target) {
        r = mid;
      } else if (target > nums[mid]) {
        l = mid + 1;
      } else {
        r = mid - 1;
      }
    }
    if (nums[l] == target) return l;
    if (nums[r] == target) return r;
    return -1;
  }

  private int findTheLast(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;
    int l = 0;
    int r = nums.length - 1;
    while (l < r - 1) {
      int mid = l + (r - l) / 2;
      if (nums[mid] == target) {
        l = mid;
      } else if (target > nums[mid]) {
        l = mid + 1;
      } else {
        r = mid - 1;
      }
    }
    if (nums[r] == target) return r;
    if (nums[l] == target) return l;
    return -1;
  }


  public int searchInsert(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;
    int m = nums.length;
    int l = 0;
    int r = m - 1;
    while (l < r - 1) {
      int mid = l + (r - l) / 2;
      if (nums[mid] == target) return mid;
      if (target > nums[mid]) {
        l = mid + 1;
      } else {
        r = mid;
      }
    }
    if (nums[l] >= target) return l;
    else if (nums[r] >= target) return r;
    return m;
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
