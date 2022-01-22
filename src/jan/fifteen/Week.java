package jan.fifteen;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Week {

  @Test
  public void test() {
//    int[][] array = {{29, 1},{90, 5},{41, 5},{46, 3},{49, 5},{49, 2},{6, 5},{36, 5},{83, 1},{60, 2},{97, 3},{54, 2},{
//    42, 5},{42, 2},{73, 4},{38, 4},{16, 4},{44, 2},{81, 2},{76, 3},{60, 4},{83, 4},{94, 2},{13, 5},{7, 2},{77, 2},{18, 2},{
//    91, 2},{43, 4},{84, 2},{45, 1},{42, 5},{54, 4},{18, 4},{96, 5},{44, 3},{55, 4},{49, 3},{86, 2},{41, 3},{54, 3},{
//    66, 2},{22, 3},{35, 5},{89, 3},{61, 2},{1, 3},{72, 1},{13, 3},{70, 4},{12, 4},{35, 5},{16, 3},{67, 3},{70, 3},{5, 4},{
//    74, 4},{36, 1},{47, 2},{72, 1}};
    int[][] array = {{1,1},{2,2},{3,3},{4,4},{5,5}};
    long res = mostPoints(array);
    System.out.println(res);
  }


  Map<Integer, Long> memo;

  public long mostPoints(int[][] questions) {
    // memo is the max score we could get [0, key]
    /*
              use dfs, each layer we have two options, earn the points, skip k problems
                0     1     2     3     4
              [1,1},{2,2},{3,3},{4,4},{5,5]
                    0(0)
                  /   \
                2(1)   1(0)
                / \
     */
    this.memo = new HashMap<>();
    long[] max = {Integer.MIN_VALUE};
    return dfs(questions, 0, 0, max);
  }

  private long dfs(int[][] questions, int index, long curPoints, long[] max) {
    if (index >= questions.length) {
      return curPoints;
    }
    if (memo.containsKey(index)) return memo.get(index);
    // get points and skip
    int skip = questions[index][1];
    int points = questions[index][0];
    long left = dfs(questions, index + skip + 1, curPoints + points, max);
    // skip
    long right = dfs(questions, index + 1, curPoints, max);
    memo.put(index, curPoints);
    return Math.max(left, right);
  }


  public int minMoves(int target, int maxDoubles) {
    /*
    19, 1
    9, 1
    4
    10
    5 2
    2 1

    if current is odd, and we have maxDoubles, this operation steps is 2

    if we still have maxDoubles
      num is odd, operations is 2
      num is even, operations is 1
      if (cur
    if we don't have it
      move is current - 1
     */
    int steps = 0;
    while (target != 1) {
      if (maxDoubles > 0) {
        if (target % 2 == 0) {
          steps += 1;
        } else {
          steps += 2;
        }
        maxDoubles--;
        target /= 2;
      } else {
        steps += (target - 1);
        target = 1;
      }
    }
    return steps;
  }


  public String[] divideString(String s, int k, char fill) {

    int n = s.length();
    if (n % k == 0) {
      String[] res = new String[n / k];
      int index = 0;

      for (int i = 0; i < n; i += k) {
        res[index++] = s.substring(i, i + k);
      }
      return res;
    } else {
      String[] res = new String[n / k + 1];
      int index = 0;
      for (int i = 0; i < k * (n / k); i += k) {
        res[index++] = s.substring(i, i + k);
      }
      int remainder = n % k;
      StringBuilder prefix = new StringBuilder();
      prefix.append(s.substring(n - remainder, n));
      for (int i = 0; i < k - remainder; i++) prefix.append(fill);
      res[index] = prefix.toString();
      return res;
    }


  }

}
