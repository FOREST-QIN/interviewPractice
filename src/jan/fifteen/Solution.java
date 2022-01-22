package jan.fifteen;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

  @Test
  public void test() {
    int res = minCost(new int[][]{{17,2,17}, {16,16,5},{14,3,19}});
    System.out.println(res);
  }

  public int[] singleNumber(int[] nums) {
    int xor = 0;
    int res1 = 0;
    int res2 = 0;

    for (int num : nums) xor ^= num;
    int lsb = (xor & ~(xor - 1));
    for (int num : nums) {
      if ((num & lsb) != 0)
        res1 = res1 ^ num;
      else
        res2 = res2 ^ num;
    }
    return new int[]{res1, res2};
  }



  public int minCost(int[][] costs) {
    // for every level, get the min and not last level one
    int n = costs.length;
    // dp[i][j] represents from top to [i, j] the min sum, house i, paint j, the min cost

    for (int i = 1; i < n; i++) {
      costs[i][0] += Math.min(costs[i - 1][1], costs[i - 1][2]);
      costs[i][1] += Math.min(costs[i - 1][0], costs[i - 1][2]);
      costs[i][2] += Math.min(costs[i - 1][0], costs[i - 1][1]);
    }
    return Math.min(costs[n - 1][0], Math.min(costs[n - 1][1], costs[n - 1][2]));
  }



  public List<List<Integer>> getFactors(int n) {
    List<List<Integer>> res = new ArrayList<>();
    List<Integer> cur = new ArrayList<>();
    getFactors(n, 2, cur, res);
    return res;
  }

  private void getFactors(int target, int factor, List<Integer> cur, List<List<Integer>> res) {
    while (factor * factor <= target) {
      if (target % factor == 0) {
        cur.add(factor);
        List<Integer> curRes = new ArrayList<>(cur);
        curRes.add(target / factor);
        res.add(curRes);
        getFactors(target / factor, factor, cur, res);
        cur.remove(cur.size() - 1);
      }
      factor++;
    }
  }


  List<String> result;

  public List<String> findStrobogrammatic(int n) {
    result = new ArrayList<>();
    find(new char[n], 0, n - 1);
    return result;
  }

  private void find(char[] array, int l, int r) {
    if (l > r) {
      result.add(new String(array));
      return;
    }
    if (l == r) {
      array[l] = '0';
      result.add(new String(array));
      array[l] = '1';
      result.add(new String(array));
      array[l] = '8';
      result.add(new String(array));
      return;
    }
    if (l != '0') {
      array[l] = '0';
      array[r] = '0';
      find(array, l + 1, r - 1);
    }
    array[l] = '1';
    array[r] = '1';
    find(array, l + 1, r - 1);
    array[l] = '8';
    array[r] = '8';
    find(array, l + 1, r - 1);
    array[l] = '6';
    array[r] = '9';
    find(array, l + 1, r - 1);
    array[l] = '9';
    array[r] = '6';
    find(array, l + 1, r - 1);
  }



  public int shortestWordDistance(String[] wordsDict, String word1, String word2) {
    long dist = Integer.MAX_VALUE;
    long i1 = dist;
    long i2 = dist;
    boolean isSame = word1.equals(word2);

    for (int i = 0; i < wordsDict.length; i++) {
      if (wordsDict[i].equals(word1)) {
        if (isSame) {
          i2 = i1;
          i1 = i;
        } else {
          i1 = i;
        }
      } else if (wordsDict[i].equals(word2)) {
        i2 = i;
      }
      if (i1 != Integer.MAX_VALUE && i2 != Integer.MAX_VALUE) {
        dist = Math.min(dist, Math.abs(i1 - i2));
      }
    }
    return (int) dist;
  }


  Map<String, List<Integer>> memo;
  public List<Integer> diffWaysToCompute(String expression) {
    char[] array = expression.toCharArray();
    memo = new HashMap<>();
    return dfs(array, 0, array.length - 1);
  }
  /*
            dfs(i, j) how many different result we could get between i,j
            only when we meet operators, we call dfs
            base case, when current range has no op, we could directly return the value
            we should maintain a memo
            2 - 1 - 1
            i       j

           / \      / \
        2    1-1  2-1  1
             / \   /\
            1  1  2 1
   */
  private List<Integer> dfs(char[] array, int left, int right) {
    /*
    012
    abc
    right - left + 1
     */
    String str = String.valueOf(array, left, right - left + 1);
    if (memo.containsKey(str)) {
      return memo.get(str);
    }
    List<Integer> values = new ArrayList<>();
    if (!hasOperator(array, left, right)) {
      values.add(Integer.parseInt(str));
    } else {
      for (int i = left; i <= right; i++) {
        char ch = array[i];
        if (!Character.isDigit(ch)) {
          List<Integer> leftList = dfs(array, left, i - 1);
          List<Integer> rightList = dfs(array, i + 1, right);
          for (int l : leftList) {
            for (int r : rightList) {
              switch (ch) {
                case '+' -> values.add(l + r);
                case '-' -> values.add(l - r);
                case '*' -> values.add(l * r);
              }
            }
          }
        }
      }
    }
    memo.put(str, values);
    return values;
  }



  private boolean hasOperator(char[] array, int i, int j) {
    for (; i <= j; i++) {
      switch (array[i]) {
        case '+','-','*' -> {
          return true;
        }
      }
    }
    return false;
  }


  public int computeArea(int a, int b, int c, int d, int e, int f, int g, int h) {
    int abcd = (a - c) * (b - d);
    int efgh = (e - g) * (f - h);
    int common = overlap(a,c,e,g) * overlap(b,d,f,h);
    return abcd + efgh - common;
  }

  private int overlap(int a, int c, int e, int g) {
    if (c < e || a > g) return 0;
    return Math.min(c, g) - Math.max(a, e);
  }

  public String fractionToDecimal(int numerator, int denominator) {
    if (numerator == 0) return "0";
    StringBuilder prefix = new StringBuilder();
    if (numerator < 0 ^ denominator < 0) prefix.append("-");
    long dividend = Math.abs(Long.valueOf(numerator));
    long divisor = Math.abs(Long.valueOf(denominator));
    prefix.append(String.valueOf(dividend / divisor));
    long remainder = dividend % divisor;
    if (remainder == 0) return prefix.toString();
    prefix.append(".");
    Map<Long, Integer> map = new HashMap<>();
    while (remainder != 0) {
      if (map.containsKey(remainder)) {
        prefix.insert(map.get(remainder), "(");
        prefix.append(")");
        break;
      }
      map.put(remainder, prefix.length());
      remainder *= 10;
      prefix.append(String.valueOf(remainder / divisor));
      remainder %= divisor;
    }
    return prefix.toString();
  }
}
