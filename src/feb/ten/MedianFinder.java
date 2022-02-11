package feb.ten;

import java.util.Collections;
import java.util.PriorityQueue;

public class MedianFinder {
  PriorityQueue<Integer> small;
  PriorityQueue<Integer> large;

  public MedianFinder() {
    this.small = new PriorityQueue<>(Collections.reverseOrder());
    this.large = new PriorityQueue<>();
  }

  public void addNum(int num) {
    if (small.isEmpty() || num < small.peek()) {
      small.offer(num);
    } else {
      large.offer(num);
    }

    if (large.size() > small.size()) {
      small.offer(large.poll());
    } else if (small.size() - large.size() >= 2) {
      large.offer(small.poll());
    }

  }

  public double findMedian() {
    int size = getSize();
    if (size % 2 == 0) {
      return (small.peek() + large.peek()) / 2.0;
    } else {
      return small.peek();
    }
  }

  private int getSize() {
    return small.size() + large.size();
  }



}
