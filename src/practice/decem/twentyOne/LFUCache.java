package practice.decem.twentyOne;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class LFUCache {
  Map<Integer, Node> map;
  int capacity;
  PriorityQueue<Node> minHeap;

  public LFUCache(int capacity) {
    this.map = new HashMap<>();
    this.capacity = capacity;
    this.minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.count, b.count));
  }

  public int get(int key) {
    if (capacity == 0) return -1;
    if (!map.containsKey(key)) return -1;
    Node cur = map.get(key);
    cur.count++;
    Node tmp = minHeap.poll();
    minHeap.offer(tmp);
    return cur.val;
  }

  public void put(int key, int value) {
    if (capacity == 0) return;
    if (map.containsKey(key)) {
      map.get(key).val = value;
      map.get(key).count++;
      Node tmp = minHeap.poll();
      minHeap.offer(tmp);
    } else {
      if (minHeap.size() == capacity) {
        Node min = minHeap.poll();
        map.remove(min.key);
      }
      Node node = new Node(key, value, 1);
      map.put(key, node);
      minHeap.offer(node);
    }
  }

  static class Node {
    int key;
    int val;
    int count;

    public Node(int key, int val, int count) {
      this.key = key;
      this.val = val;
      this.count = count;
    }
  }

}