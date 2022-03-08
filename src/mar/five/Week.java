package mar.five;

import org.junit.Test;

import java.util.*;

public class Week {
  @Test
  public void test() {
    int[] arr = {96,44,99,25,61,84,88,18,19,33,60,86,52,19,32,47,35,50,94,17,29,98,22,21,72,100,40,84};
    long res = minimalKSum(arr, 35);
  }

  public TreeNode createBinaryTree(int[][] descriptions) {
    Set<Integer> parents = new HashSet<>();
    for (int[] des : descriptions) {
      parents.add(des[0]);
    }
    Map<Integer, TreeNode> map = new HashMap<>();
    for (int[] des : descriptions) {
      int parent = des[0];
      int child = des[1];
      boolean isLeft = des[2] == 1;
      map.putIfAbsent(parent, new TreeNode(parent));
      parents.remove(child);
      TreeNode cur = map.get(child);
      if (cur == null) cur = new TreeNode(child);
      if (isLeft) {
        map.get(parent).left = cur;
      } else {
        map.get(parent).right = cur;
      }
      map.put(child, cur);
    }
    for (int[] des : descriptions) {
      if (parents.contains(des[0])) return map.get(des[0]);
    }
    return null;
  }


  public long minimalKSum(int[] nums, int k) {
    Arrays.sort(nums);
    /*
    [1,4,10,25,25]
    [0,2,5 ,14, 0]
     */
    long res = 0;
    int m = nums.length;
    int[] diff = new int[m];
    for (int i = 0; i < m; i++) {
      if (i == 0) {
        diff[i] = Math.max(nums[0] - 1, 0);
      } else {
        diff[i] = Math.max(0, nums[i] - nums[i - 1] - 1);
      }
    }
    int index = 0;
    while (k > 0 && index < m) {
      if (diff[index] == 0) {
        index++;
        continue;
      }
      long cur = Math.min(diff[index], k);
      if (index == 0) {
        long count = 1;
        res += cur + cur * (cur - 1) / 2;
      } else {

        long prev = (long) nums[index - 1] * (1 + nums[index - 1]) / 2;
        long total = (long) (nums[index - 1] + cur) * (1 + nums[index - 1] + cur) / 2;
        res += (total - prev);
      }
      k -= cur;
      index++;
    }
    int start = nums[m - 1] + 1;
    if (k > 0) {
      res += (long) (nums[m - 1] + k) * (1 + nums[m - 1] + k) / 2;
      res -= (long) (nums[m - 1]) * (1 + nums[m - 1]) / 2;
    }
    return res;
  }

  public List<String> cellsInRange(String s) {
    // find diff in col
    // find diff int row
    List<String> res = new ArrayList<>();
    String[] arr = s.split(":");
    String one = arr[0];
    String two = arr[1];

    char c1 = one.charAt(0);
    char r1 = one.charAt(1);
    char c2 = two.charAt(0);
    char r2 = two.charAt(1);

    for (char c = c1; c <= c2; c++) {
      for (char r = r1; r <= r2; r++) {
        res.add(c + "" + r);
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
