package feb.eighteen;

public class NumMatrix {
  int[][] prefix;
  int m;
  int n;
  int[][] matrix;
  public NumMatrix(int[][] matrix) {
    this.m = matrix.length;
    this.n = matrix[0].length;
    this.prefix = new int[m][n];
    this.matrix = matrix;
    prefix[0][0] = matrix[0][0];
    for (int i = 1; i < m; i++) {
      prefix[i][0] = matrix[i][0] + prefix[i - 1][0];
    }
    for (int i = 1; i < n; i++) {
      prefix[0][i] = matrix[0][i] + prefix[0][i - 1];
    }
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        prefix[i][j] = matrix[i][j] + prefix[i - 1][j] + prefix[i][j - 1] - prefix[i - 1][j - 1];
      }
    }
  }

  public int sumRegion(int row1, int col1, int row2, int col2) {
    if (row1 == row2 && col1 == col2) return matrix[row1][col1];
    if (row1 == 0 && col1 == 0) return prefix[row2][col2];
    int res = prefix[row2][col2];

    if (row1 == 0) {
      res -= prefix[row2][col1 - 1];
    } else if (col1 == 0) {
      res -= prefix[row1 - 1][col2];
    } else {
      res = res - prefix[row2][col1 - 1] - prefix[row1 - 1][col2] + prefix[row1 - 1][col1 - 1];
    }
    return res;
  }

}
