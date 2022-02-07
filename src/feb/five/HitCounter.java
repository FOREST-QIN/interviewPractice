package feb.five;

import java.util.Deque;
import java.util.LinkedList;

public class HitCounter {

  Deque<Integer> q;

  public HitCounter() {
    this.q = new LinkedList<>();
  }

  public void hit(int timestamp) {
    q.offerLast(timestamp);
    while (!q.isEmpty() && q.peekFirst() <= timestamp - 300) {
      q.pollFirst();
    }
  }

  public int getHits(int timestamp) {
    while (!q.isEmpty() && q.peekFirst() <= timestamp - 300) {
      q.pollFirst();
    }
    return q.size();
  }

}
