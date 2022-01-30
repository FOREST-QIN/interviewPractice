package jan.twentyNine;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Weekly {

  @Test
  public void test() {
    String res = subStrHash("xmmhdakfursinye", 96, 45, 15, 21);
    System.out.println(res);
  }


  public String subStrHash(String s, int power, int modulo, int k, int hashValue) {
    if (s == null || s.length() == 0) return s;
    int m = s.length();
    int[] arr = new int[m];
    for (int i = 0; i < m; i++) {
      arr[i] = s.charAt(i) - 'a' + 1;
    }
    for (int i = k - 1; i < m; i++) {
      int hash = getHash(arr, i - k + 1, i, modulo, power);
      if (hash == hashValue) {
        return s.substring(i - k + 1, i + 1);
      }
    }
    return "";
  }

  private int getHash(int[] arr, int i, int j, int mod, int p) {
    int res = 0;
    int pow = 0;
    while (i <= j) {
      res = (res + arr[i] * (int) (Math.pow(p, pow++)) % mod ) % mod;
      i++;
    }
    return (int) res;
  }


  public List<Integer> maxScoreIndices(int[] nums) {
    List<Integer> res = new ArrayList<>();
    if (nums == null || nums.length == 0) return res;
    int n = nums.length;

    int[] left = new int[n + 1];
    int[] right = new int[n + 1];

    left[0] = 0;
    for (int i = 1; i <= n; i++) {
      left[i] = left[i - 1];
      if (nums[i - 1] == 0) left[i]++;
    }
    right[n] = 0;
    for (int i = n - 1; i >= 0; i--) {
      right[i] = right[i + 1];
      if (nums[i] == 1) right[i]++;
    }


    int highest = Integer.MIN_VALUE;
    for (int i = 0; i <= n; i++) {
      int cur = left[i] + right[i];
      if (cur > highest) {
        highest = cur;
        res = new ArrayList<>();
        res.add(i);
      } else if (cur == highest) {
        res.add(i);
      }
    }
    return res;
  }

  public int findFinalValue(int[] nums, int original) {
    if (nums == null || nums.length == 0) return original;
    boolean flag = true;
    while (flag) {
      boolean isFound = false;
      for (int num : nums) {
        if (num == original) {
          original *= 2;
          isFound = true;
        }
      }
      if (!isFound) {
        flag = false;
      }
    }
    return original;
  }


}
