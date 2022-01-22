package jan.eight;

import org.junit.Test;

import java.util.Arrays;

public class Week {
  @Test
  public void test() {
    /*
    ["tqnu","jld","gnb","bux","qut"]
["mtqnu","n","l","jauw","ubh","qutn","cqkbs","nkrog","bhl","qjld"]
     */
    String[] s = {"tqnu","jld","gnb","bux","qut"};
    String[] t = {"mtqnu","n","l","jauw","ubh","qutn","cqkbs","nkrog","bhl","qjld"};
    int res = wordCount(s, t);
    System.out.println(res);
  }

  public int wordCount(String[] startWords, String[] targetWords) {
    /*
    two letters can be transformed,

    there count is equal
    or
    there only one difference in count, and this char is not in start word

     */

    int count = 0;

    for (String target : targetWords) {
      for (String start : startWords) {
        if ((start.length() > target.length()) || (target.length() - start.length() > 1)) continue;
        if (check(start, target)) count++;
      }
    }
    return count;
  }

  private boolean check(String s, String t) {
    if (s.equals(t)) return true;
    int[] start = getCount(s);
    int[] target = getCount(t);
    if (Arrays.equals(start, target)) return true;

    int ch = -1;
    for (int i = 0; i < 26; i++) {
      if (start[i] != target[i]) {
        if (ch != -1) return false;
        if (target[i] != 0) return false;
        ch = i;
      }
    }
    start[ch]++;
    return Arrays.equals(start, target);
  }

  private int[] getCount(String word) {
    int[] count = new int[26];
    for (int i = 0; i < word.length(); i++) {
      count[word.charAt(i) - 'a']++;
    }
    return count;
  }


  public int minSwaps(int[] nums) {
    /*
     0 1 2 3 4 5 6 7 8 9
    [1,1,0,0,1,1,1,0,0,1]
       i     j

    [0,1,1,1,0,0,1,1,0,0,1,1,1,0,0,1,1,0]
                             i         j

     sliding window max size is 3
     3 0
     get max zero in this sliding window
     */
    if (nums == null || nums.length == 0) return 0;
    int size = nums.length;
    int[] array = new int[2 * size];
    for (int i = 0; i < array.length; i++) {
      array[i] = nums[i % size];
    }
    int ones = 0;
    for (int num : nums) {
      if (num == 1) ones++;
    }

    int cur = 0;
    int max = 0;
    int i = 0;
    int j = 0;
    while (j < array.length) {
      if (j - i < ones) {
        cur += array[j];
        j++;
      } else {
        max = Math.max(max, cur);
        cur += array[j];
        cur -= array[i];

        j++;
        i++;
      }
    }
    return ones - max;
  }


  public boolean checkValid(int[][] matrix) {
    if (matrix == null || matrix.length == 0) return true;
    int n = matrix.length;
    boolean[] visited = new boolean[n + 1];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (visited[matrix[i][j]]) return false;
        visited[matrix[i][j]] = true;
      }
      for (int x = 1; x <= n; x++) {
        if (!visited[x]) return false;
      }
      Arrays.fill(visited, false);
    }

    for (int j = 0; j < n; j++) {
      for (int i = 0; i < n; i++) {
        if (visited[matrix[i][j]]) return false;
        visited[matrix[i][j]] = true;
      }
      for (int x = 1; x <= n; x++) {
        if (!visited[x]) return false;
      }
      Arrays.fill(visited, false);
    }
    return true;
  }
}
