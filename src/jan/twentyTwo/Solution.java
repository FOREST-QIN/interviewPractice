package jan.twentyTwo;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Solution {

  @Test
  public void test() {
    int[] array = {1,3,3,3};
    int res = minimumCost(array);
    System.out.println(res);
  }

  public int maxProfit(int[] prices) {
    int res = 0;
    int min = prices[0];
    for (int price : prices) {
      if (price < min) {
        min = price;
      } else if (price - min > res) {
        res = price - min;
      }
    }
    return res;
  }



  public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    int sum = 0;
    map.put(0, 1);
    int res = 0;

    for (int num : nums) {
      sum += num;
      if (map.containsKey(sum - k)) res += map.get(sum - k);
      map.put(sum, map.getOrDefault(sum, 0) + 1);
    }
    return res;
  }


  public int lengthOfLongestSubstring(String s) {
    if (s == null || s.length() == 0) return 0;

    int[] window = new int[256];
    int i = 0;
    int j = 0;
    int res = 0;

    while (j < s.length()) {
      char ch = s.charAt(j);
      j++;
      window[ch]++;

      while (window[ch] > 1) {
        char lCh = s.charAt(i);
        i++;
        window[lCh]--;
      }
      res = Math.max(res, j - i);
    }
    return res;
  }


  public int minimumCost(int[] cost) {
    if (cost == null || cost.length == 0) return 0;
    if (cost.length == 1) return cost[0];
    Arrays.sort(cost);
    int m = cost.length;
    if (cost.length <= 3) {
      return cost[m - 1] + cost[m - 2];
    }
    int res = Integer.MAX_VALUE;

    for (int i = 0; i < m - 2; i++) {
      for (int j = i + 1; j < m - 1; j++) {
        for (int k = j + 1; k < m; k++) {
          int curCost = Integer.MAX_VALUE;
          if (cost[i] <= Math.min(cost[j], cost[k])) {
            curCost = Math.min(curCost, cost[j] + cost[k]);
          }
          if (cost[j] <= Math.min(cost[i], cost[k])) {
            curCost = Math.min(curCost, cost[i] + cost[k]);
          }
          if (cost[k] <= Math.min(cost[i], cost[j])) {
            curCost = Math.min(curCost, cost[i] + cost[j]);
          }
          res = Math.min(curCost, res);
        }
      }
    }

    return res;
  }


}
