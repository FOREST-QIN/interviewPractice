package jan.eleven;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    int[] array = new int[]{2,7,4,1,8,1};
    int res = lastStoneWeight(array);
    System.out.println(res);
  }



  public String[] findRelativeRanks(int[] score) {
    int n = score.length;
    String[] res = new String[n];

    PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(score[b], score[a]));
    for (int i = 0; i < n; i++) maxHeap.offer(i);
    int step = 1;
    while (!maxHeap.isEmpty()) {
      int cur = maxHeap.poll();
      if (step == 1) {
        res[cur] = "Gold Medal";
      } else if (step == 2) {
        res[cur] = "Sliver Medal";
      } else if (step == 3) {
        res[cur] = "Bronze Medal";
      } else {
        res[cur] = String.valueOf(step);
      }
      step++;
    }
    return res;
  }


  public int lastStoneWeight(int[] stones) {
    if (stones.length == 1) return stones[0];
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
    for (int stone : stones) maxHeap.offer(stone);

    int tmp = 0;
    while (maxHeap.size() >= 2) {
      int maxOne = maxHeap.poll();
      int maxTwo = maxHeap.poll();
      if (maxOne == maxTwo) continue;
      maxHeap.offer(maxOne - maxTwo);
    }
    return maxHeap.isEmpty() ? 0 : maxHeap.peek();
  }

  public int[] kWeakestRows(int[][] mat, int k) {
    PriorityQueue<Node> minHeap = new PriorityQueue<>((a, b) -> {
      if (a.num == b.num) return Integer.compare(a.row, b.row);
      return Integer.compare(a.num, b.num);
    });

    for (int i = 0; i < mat.length; i++) {
      int count = 0;
      for (int num : mat[i]) count += num;
      minHeap.offer(new Node(count, i));
    }
    int[] res = new int[k];
    for (int i = 0; i < k; i++) {
      res[i] = minHeap.poll().row;
    }
    return res;
  }

  static class Node {
    int num;
    int row;

    public Node(int num, int row) {
      this.num = num;
      this.row = row;
    }
  }

}
