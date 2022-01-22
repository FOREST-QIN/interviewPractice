package jan.twentyTwo;

import org.junit.Test;

import java.util.Arrays;

public class Solution {

  @Test
  public void test() {
    int[] array = {1,3,3,3};
    int res = minimumCost(array);
    System.out.println(res);
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
