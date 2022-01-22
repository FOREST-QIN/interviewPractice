package jan.thirteen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
  int count = 0;
  public int totalNQueens(int n) {
    boolean[] cols = new boolean[n];
    boolean[] d1 = new boolean[2 * n];
    boolean[] d2 = new boolean[2 * n];
    dfs(0, cols, d1, d2, n);
    return count;
  }

  private void dfs(int row, boolean[] cols, boolean[] d1, boolean[] d2, int n) {
    if (row == n) count++;
    for (int col = 0; col < n; col++) {
      int id1 = col - row + 1;
      int id2 = col + row;
      if (cols[col] || d1[id1] || d2[id2]) continue;
      cols[col] = true;
      d1[id1] = true;
      d2[id2] = true;
      dfs(row + 1, cols, d1, d2, n);
      cols[col] = false;
      d1[id1] = false;
      d2[id2] = false;
    }
  }

  public List<Integer> findSubstring(String s, String[] words) {
    Map<String, Integer> counts = new HashMap<>();
    for (String str : words) counts.put(str, counts.getOrDefault(str, 0) + 1);
    List<Integer> res = new ArrayList<>();
    int n = s.length();
    int num = words.length;
    int len = words[0].length();

    int targetLen = num * len;
    for (int i = 0; i <=  n - targetLen; i++) {
      Map<String, Integer> seen = new HashMap<>();
      int j = 0;
      while (j < num) {
        String word = s.substring(i + j * len, i + (j + 1) * len);
        if (counts.containsKey(word)) {
          seen.put(word, seen.getOrDefault(word, 0) + 1);
          if (seen.get(word) > counts.get(word)) break;
        } else {
          break;
        }
        j++;
      }
      if (j == num) res.add(i);
    }
    return res;
  }



}
