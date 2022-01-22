package practice.decem.two;

import java.util.*;

public class Solution {

  int[] nums;
  int[] original;
  List<int[]> list;
  Random rand;

  public Solution(int[] nums) {
    this.rand = new Random();
    this.nums = nums;
    this.list = new ArrayList<>();
    this.original = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
      original[i] = nums[i];
    }
    Set<int[]> set = new HashSet<>();
    dfs(nums, 0, set);
    list.addAll(set);
  }

  public int[] reset() {
    return original;
  }

  public int[] shuffle() {
    return list.get(rand.nextInt(list.size()));
  }

  private void dfs(int[] array, int index, Set<int[]> set) {
    if (index == array.length) {
      set.add(Arrays.copyOf(array, array.length));
      return;
    }

    for (int i = index; i < array.length; i++) {
      swap(array, i, index);
      dfs(array, index + 1, set);
      swap(array, i, index);
    }
  }

  private void swap(int[] array, int i, int j) {
    int tmp = array[i];
    array[i] = array[j];
    array[j] = tmp;
  }

}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int[] param_1 = obj.reset();
 * int[] param_2 = obj.shuffle();
 */
