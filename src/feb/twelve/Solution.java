package feb.twelve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

  /*
  0, 0, 1, 1, 2, 2
        i
            j
            k

  i [0,i) 0
    [i, k) white
  j (j, nums.length - 1] 2 blue
  k to explore


  0 red
  1 white
  2 blue
  red -> white -> blue

   */

  public void sortColors(int[] nums) {
    if (nums == null || nums.length <= 1) return;
    // [0, i) 0
    int i = 0;
    // (j, nums.length - 1] 2
    int j = nums.length - 1;
    // [i, k) 1
    int k = 0;
    // [k, j] unexplored area
    while (k <= j) {
      if (nums[k] == 0) {
        swap(nums, i++, k++);
      } else if (nums[k] == 1) {
        k++;
      } else {
        swap(nums, k, j--);
      }
    }
  }

  private void swap(int[] nums, int i, int j) {
    int tmp = nums[i];
    nums[i] = nums[j];
    nums[j] = tmp;
  }


  /*
          1
         1 1
       1

       i (1-index) (i - 2)
last rows (i) + last rows(i - 1)
      1     1
      2    1 1
      3   1 2 1
      4  1 3 3 1
   */
  public List<List<Integer>> generate(int numRows) {
    List<List<Integer>> res = new ArrayList<>();
    if (numRows == 0) return res;
    res.add(Arrays.asList(1));
    if (numRows == 1) return res;
    for (int i = 2; i <= numRows; i++) {
      /*
      1
      1
      0
       */
      List<Integer> cur = new ArrayList<>();
      cur.add(1);

      for (int count = 1; count <= i - 2; count++) {
        int sum = res.get(i - 1 - 1).get(count) + res.get(i - 1 - 1).get(count - 1);
        cur.add(sum);
      }
      cur.add(1);
      res.add(cur);
    }
    return res;
  }



  public boolean isAnagram(String s, String t) {
    /*
      abcd
      bdac
     */
    /*
    all letters are lower letters
    int[26] sCount : record the count of each characters from 'a' - 'z'

    linear scan whether two count array is the same
     */
    if (s.equals(t)) return true;
    // use helper function to get string's count array
    int[] sCount = getCountArray(s);
    int[] tCount = getCountArray(t);
    return Arrays.equals(sCount, tCount);
  }

  private int[] getCountArray(String s) {
    int[] count = new int[26];
    for (int i = 0; i < s.length(); i++) {
      count[s.charAt(i) - 'a']++;
    }
    return count;
  }


  //  gas = [1,2,3,4,5], cost = [3,4,5,1,2]
  /*
      gas =  [1, 2, 3, 4,     5],
      cost = [3, 4, 5, 1,      2]
             -2 -2 -2  3 -6  3
                            -3
                          i
              start = 3
              sum = 0
              minSum = -6;

              >= 0



                 i
             start 1;
             sum -4;
             minSum -;
        2  3  4
        3  4  3
       -1 -1  1


   */
  public int canCompleteCircuit(int[] gas, int[] cost) {
    int n = gas.length;
    int sum = 0;
    int curSum = 0;
    int start = 0;
    for (int i = 0; i < n; i++) {
      sum += gas[i] - cost[i];
      curSum += gas[i] - cost[i];
      if (curSum < 0) {
        start = i + 1;
        curSum = 0;
      }
    }
    if (sum < 0) return -1;
    return start == n ? 0 : start;
  }
}
