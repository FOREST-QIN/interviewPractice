package feb.twentySix;

import org.junit.Test;

public class Solution {

  @Test
  public void test() {
    int[][] tires = {{2, 3}, {3, 4}};
    minimumFinishTime(tires, 5, 4);
    System.out.println(minTime);
  }

  int minTime;
  public int minimumFinishTime(int[][] tires, int changeTime, int numLaps) {
    /*

     */

    this.minTime = Integer.MAX_VALUE;
    dfs(tires, -1, changeTime, 0, 0, numLaps);
    return minTime;
  }

  private void dfs(int[][] tires, int prevTire, int changeTime, int sum, int curLaps, int numLaps) {
    if (curLaps == numLaps + 1) {
      minTime = Math.min(minTime, sum);
      return;
    }
    for (int i = 0; i < tires.length; i++) {
      int curTime = getTime(tires[i], curLaps);
      if (prevTire != -1 && i != prevTire) curTime += changeTime;
      dfs(tires, i, changeTime, curTime, curLaps + 1, numLaps);
    }

  }

  private int getTime(int[] tire, int laps) {
    return tire[0] * (int) Math.pow(tire[0], laps);
  }

  public long minimumTime(int[] time, int totalTrips) {
//    if (time.length == 1) {
//      if (totalTrips >= time[0]) {
//        int total = totalTrips / time[0];
//        total += totalTrips % time[0] == 0 ? 0 : 1;
//        return total;
//      } else {
//        return time[0];
//      }
//    }
    long sum = 0;
    for (int t : time) sum += t;

    long left = 1;
    long right = sum * totalTrips;

    while (left < right - 1) {
      long mid = left + (right - left) / 2;
      long trips = getTrips(time, mid);
      if (trips == totalTrips) {
        right = mid;
      } else if (trips > totalTrips) {
        right = mid;
      } else {
        left = mid + 1;
      }
    }
    if (getTrips(time, left) >= totalTrips) return left;
    return right;
  }

  private long getTrips(int[] times, long cur) {
    long res = 0;
    for (int time : times) {
      res += cur / time;
    }
    return res;
  }



  public int prefixCount(String[] words, String pref) {
    int count = 0;
    for (String word : words) {
      if (word.startsWith(pref)) count++;
    }
    return count;
  }


  public int minSteps(String s, String t) {
    int[] sCount = new int[26];
    int[] tCount = new int[26];
    for (char ch : s.toCharArray()) {
      sCount[ch - 'a']++;
    }
    for (char ch : t.toCharArray()) {
      tCount[ch - 'a']++;
    }
    int res = 0;
    for (int i = 0; i < 26; i++) {
      res += Math.abs(sCount[i] - tCount[i]);
    }
    return res;
  }


}
