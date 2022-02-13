package feb.twelve;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Week {

  @Test
  public void test() {
    int[] arr = {4,1,6,5};
    long res = minimumRemoval(arr);
    System.out.println(res);
  }

  public long minimumRemoval(int[] beans) {
    // levea the
    // 4 make it as close to average as possible, this way, removes step is the closest
    // min(cur - 4, to zero)
    /*
    4, 1, 6, 4
    0  1  2  0
    2, 10, 3, 2
    2

    4,1,6,5
    1,4,5,6

    2,2,3,10;
    2 4 7 17
      4  13 - right Count * count + 1'
     */
    if (beans == null || beans.length <= 1) return 0;
    Arrays.sort(beans);
    if (beans[0] == beans[beans.length - 1]) return 0;
    long[] prefix = new long[beans.length];
    prefix[0] = beans[0];
    for (int i = 1; i < beans.length; i++) {
      prefix[i] = prefix[i - 1] + beans[i];
    }
    long total = prefix[prefix.length - 1];
    long min = total;

    for (int i = 0; i < beans.length - 1; i++) {
      long removeLeft = prefix[i];
      long rightTotal = total - removeLeft;
      long rightCount = beans.length - 1 - i;
      long rightMin = beans[i + 1];
      long right = rightTotal - rightCount * rightMin;
      min = Math.min(min, removeLeft + right);
      //                     4 + (13 - 2 * 3)
    }
    return min;
  }

  public int minimumOperations(int[] nums) {
    /*
    X y X y X y X y
    3,1,3,2,4,3
    0 0 0 1

    // find the most common on even , and keep it
    find the most common on odd which is not even
     */
    if (nums.length == 1) return 0;
    if (nums.length == 2) {
      if (nums[0] == nums[1]) return 1;
      return 0;
    }
    Map<Integer, Integer> even = new HashMap<>();
    int evenTotal = 0;
    int evenCount = 0;
    int evenTmp = 0;
    Map<Integer, Integer> odd = new HashMap<>();
    int oddTotal = 0;
    int oddCount = 0;
    int oddTmp = 0;
    for (int i = 0; i < nums.length; i++) {
      if (i % 2 == 0) {
        even.put(nums[i], even.getOrDefault(nums[i], 0) + 1);
        if (even.get(nums[i]) > evenCount) {
          evenCount = even.get(nums[i]);
          evenTmp = nums[i];
        }
        evenTotal++;
      } else {
        odd.put(nums[i], odd.getOrDefault(nums[i], 0) + 1);
        if (odd.get(nums[i]) > oddCount) {
          oddCount = odd.get(nums[i]);
          oddTmp = nums[i];
        }
        oddTotal++;
      }
    }
    int res = Integer.MAX_VALUE;
    // get the most common one from odd, get the most common one from even which is not equal to odd
    for (int key : even.keySet()) {
      if (key != oddTmp) {
        res = Math.min(evenTotal - even.get(key) + oddTotal - oddCount, res);
      }
    }
    for (int key : odd.keySet()) {
      if (key != evenTmp) {
        res = Math.min(oddTotal - odd.get(key) + evenTotal - evenCount, res);
      }
    }

    return res == Integer.MAX_VALUE ? Math.min(oddTotal, evenTotal) : res;
  }



  public int countOperations(int num1, int num2) {
    int step = 0;
    while (num1 != 0 && num2 != 0) {
      if (num1 >= num2) {
        num1 -= num2;
      } else {
        num2 -= num1;
      }
      step++;
    }
    return step;
  }

}
