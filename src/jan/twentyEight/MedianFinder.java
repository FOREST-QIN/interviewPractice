package jan.twentyEight;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MedianFinder {

  private PriorityQueue<Integer> smaller;
  private PriorityQueue<Integer> larger;

  public MedianFinder() {
    this.smaller = new PriorityQueue<>(Comparator.reverseOrder());
    this.larger = new PriorityQueue<>();
  }

  public void addNum(int num) {
    // in order to make sure that we could get median when the stream size is odd
    // we should make the smaller always have larger or equal size with larger heap
    if (smaller.isEmpty() || smaller.peek() >= num) {
      smaller.offer(num);
    } else {
      larger.offer(num);
    }
    if (smaller.size() - larger.size() >= 2) {
      larger.offer(smaller.poll());
    } else if (larger.size() > smaller.size()) {
      smaller.offer(larger.poll());
    }
  }

  public double findMedian() {
    if (getSize() % 2 == 0) {
      return (smaller.peek() + larger.peek()) / 2.0;
    } else {
      return (double) smaller.peek();
    }
  }

  private int getSize() {
    return smaller.size() + larger.size();
  }

}
