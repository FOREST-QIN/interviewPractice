package jan.twelve;

import org.junit.Test;

public class Solution {

  @Test
  public void test() {
    char[][] matrix = {
        {'0', '1'},
        };
    int res = maximalSquare(matrix);
  }


  public int maximalSquare(char[][] matrix) {
    if (matrix.length == 0) return 0;
    int max = 0;
    int m = matrix.length;
    int n = matrix[0].length;

    int[][] dp = new int[m][n];

    for (int i = 0; i < m; i++) {
      dp[i][0] = matrix[i][0] - '0';
      max = Math.max(max, dp[i][0]);
    }

    for (int j = 0; j < n; j++) {
      dp[0][j] = matrix[0][j] - '0';
      max = Math.max(max, dp[0][j]);
    }

    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        if (matrix[i][j] == '1') {
          dp[i][j] = min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]) + 1;
          max = Math.max(max, dp[i][j]);
        }
      }
    }
    return max * max;
  }

  private int min(int a, int b, int c) {
    return Math.min(a, Math.min(b, c));
  }



}
