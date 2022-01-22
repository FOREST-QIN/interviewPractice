package practice.decem.twentyFive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

  public List<List<String>> solveNQueens(int n) {
    List<List<String>> res = new ArrayList<>();
    if (n == 0) return res;

    char[][] board = new char[n][n];
    for (char[] arr : board) Arrays.fill(arr, '.');
    dfs(board, 0, res);
    return res;
  }

  private void dfs(char[][] board, int row, List<List<String>> res) {
    if (row == board.length) {
      List<String> cur = new ArrayList<>();
      for (char[] arr : board) {
        cur.add(new String(arr));
      }
      res.add(cur);
      return;
    }

    int n = board[0].length;
    for (int c = 0; c < n; c++) {
      if (!isValid(board, row, c)) continue;
      board[row][c] = 'Q';
      dfs(board, row + 1, res);
      board[row][c] = '.';
    }
  }

  private boolean isValid(char[][] board, int row, int col) {
    int n = board.length;

    for (int i = 0; i < n; i++) {
      if (board[i][col] == 'Q') return false;
    }

    for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
      if (board[i][j] == 'Q') return false;
    }

    for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
      if (board[i][j] == 'Q') return false;
    }
    return true;
  }


}
