package feb.five;

import org.junit.Test;

import java.util.*;

public class week {

  @Test
  public void test() {
    String s = "1100101";
    System.out.println(minimumTime(s));
  }


  public int minimumTime(String s) {
    if (s == null || s.length() == 0) return 0;
    int m = s.length();
    int[][] dp = new int[m][m];
    /*
   0 1
   0
   1
   min dp[5][5]

   i-j

   !i !j && 为1, 左 + 右 + 2
            is 0, 左 + 右
    i, j, 为 1，1 + 左， 1 + right
     */


    return 0;
  }





  public long smallestNumber(long num) {
    if (num == 0) return num;
    List<Integer> arr = new ArrayList<>();
    boolean flag = num < 0;
    num = Math.abs(num);

    while (num > 0) {
      arr.add((int) (num % 10));
      num /= 10;
    }

    Collections.sort(arr);
    long res = 0;
    if (flag) {
      for (int i = arr.size() - 1; i >= 0; i--) {
        res = res * 10 + arr.get(i);
      }
      return -res;
    }
    if (arr.get(0) == 0) {

      for (int i = 1; i < arr.size(); i++) {
        if (arr.get(i) > 0) {
          arr.set(0, arr.get(i));
          arr.set(i, 0);
          break;
        }
      }
    }
    for (int i = 0; i < arr.size(); i++) {
      res = res * 10 + arr.get(i);
    }
    return res;
  }



  public int[] sortEvenOdd(int[] nums) {
    if (nums == null || nums.length <= 2) return nums;
    int m = nums.length;
    int[] odd;
    int i = 0;
    int[] even;
    int j = 0;
    if (m % 2 == 0) {
      odd = new int[m / 2];
    } else {
      odd = new int[m / 2 + 1];
    }
    even = new int[m / 2];

    for (int k = 0; k < m; k++) {
      if (k % 2 == 0) {
        even[j++] = nums[k];
      } else {
        odd[i++] = nums[k];
      }
    }

    Arrays.sort(even);
    Arrays.sort(odd);
    reverse(even);
    i = 0;
    j = 0;
    for (int k = 0; k < m; k++) {
      if (k % 2 == 0) {
        nums[k] = odd[i++];
      } else {
        nums[k] = even[j++];
      }
    }
    return nums;
  }

  private void reverse(int[] arr) {
    int i = 0;
    int j = arr.length - 1;
    while (i <= j) {
      int tmp = arr[i];
      arr[i] = arr[j];
      arr[j] = tmp;
      i++;
      j--;
    }
  }

}
