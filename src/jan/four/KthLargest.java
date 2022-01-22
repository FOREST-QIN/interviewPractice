package jan.four;

import java.util.PriorityQueue;

public class KthLargest {

  private PriorityQueue<Integer> minHeap;
  private int k;
  private int count;

  public KthLargest(int k, int[] nums) {
    this.k = k;
    this.count = 0;
    this.minHeap = new PriorityQueue<>();

    if (nums.length == 0) return;
    while (count < k && count < nums.length) {
      minHeap.offer(nums[count++]);
    }
    for (int i = count; i < nums.length; i++) {
      if (minHeap.peek() < nums[count]) {
        minHeap.poll();
        minHeap.offer(nums[i]);
      }
    }

  }

  public int add(int val) {
    if (count < k) {
      minHeap.offer(val);
      count++;
    } else {
      if (minHeap.peek() < val) {
        minHeap.poll();
        minHeap.offer(val);
      }
    }
    return minHeap.peek();
  }

}
