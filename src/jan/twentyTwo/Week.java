package jan.twentyTwo;

import org.junit.Test;

import java.util.*;

public class Week {

  @Test
  public void test() {
    int[][] stats = {{2, 1, 2},
        {1, 2, 2},
        {2, 0, 2}};
    int res = maximumGood(stats);
    System.out.println(res);
  }

  int[][] statements;
  int n;
  int max;

  public int maximumGood(int[][] statements) {
    /*
      0 1 2
   0 [2,1,2],
   1 [1,2,2],
   2 [2,0,2]

   0: g{1
      b{2
   1: g{0,2
      b{
   2: g{
      b{0, 1

    for anyone, there only could have to possibility
    good,       or bad
     tell truth
              0
             /  \
            1

     */
    if (statements == null || statements.length == 0) return 0;
    this.statements = statements;
    this.n = statements.length;
    this.max = 0;
    for (int i = 0; i < n; i++) {
      dfs(i, new boolean[n], 0);
    }
    return max;
  }

  private void dfs(int person, boolean[] visited, int curGood) {
    if (visited[person]) {
      max = Math.max(max, curGood);
      return;
    }
    // assume current people is good, we need mark all people this one think is bad as true;
    for (int i = 0; i < n; i++) {
      if (statements[person][i] == 0) {
        visited[i] = true;
      }
    }
    visited[person] = true;
    curGood++;
    for (int i = 0; i < n; i++) {
      if (i == person) continue;
      dfs(i, visited, curGood);
    }

  }

  static class Person {
    int id;
    Set<Integer> good = new HashSet<>();
    Set<Integer> bad = new HashSet<>();

    public Person(int id) {
      this.id = id;
    }
  }

  public List<Integer> findLonely(int[] nums) {
    List<Integer> res = new ArrayList<>();
    if (nums == null || nums.length == 0) return res;
    Map<Integer, Integer> map = new HashMap<>();

    for (int num : nums) {
      map.put(num, map.getOrDefault(num, 0) + 1);
    }

    for (int num : nums) {
      if (map.get(num) == 1) {
        if (!map.containsKey(num - 1) && !map.containsKey(num + 1)) {
          res.add(num);
        }
      }
    }
    return res;
  }

  public int[] rearrangeArray(int[] nums) {
    /*
    3,1,-2,-5,2,-4

     */
    if (nums == null || nums.length == 0) return nums;

    int m = nums.length;
    int[] positive = new int[m / 2];
    int p = 0;
    int[] negative = new int[m / 2];
    int n = 0;

    for (int num : nums) {
      if (num < 0) {
        negative[n++] = num;
      } else {
        positive[p++] = num;
      }
    }
    p = 0;
    n = 0;

    for (int i = 0; i < m; i += 2) {
      nums[i] = positive[p++];
      nums[i + 1] = negative[n++];
    }
    return nums;
  }


  public int countElements(int[] nums) {
    if (nums == null || nums.length <= 2) return 0;

    int res = 0;
    int m = nums.length;

    for (int i = 0; i < m; i++) {
      int cur = nums[i];
      boolean smaller = false;
      boolean bigger = false;
      for (int j = 0; j < m; j++) {
        if (j == i) continue;
        if (nums[j] > cur) {
          if (smaller) {
            res++;
            break;
          }
          bigger = true;
        } else if (nums[j] < cur) {
          if (bigger) {
            res++;
            break;
          }
          smaller = true;
        }
      }
    }

    return res;
  }

}
