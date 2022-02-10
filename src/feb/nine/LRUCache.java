package feb.nine;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
  final int capacity;
  Map<Integer, Node> map;
  Node head;
  Node tail;
  volatile int x = 0;

  public LRUCache(int capacity) {
    this.capacity = capacity;
    this.map = new HashMap<>();
  }

  public int get(int key) {
    if (!map.containsKey(key)) return -1;
    Node node = map.get(key);
    putToHead(node);
    return node.val;
  }

  public void put(int key, int value) {
    Node node = map.get(key);
    if (node == null) {
      node = new Node(key, value);
      if (map.size() == capacity) {
        map.remove(tail.key);
        tail = tail.prev;
        if (tail != null) tail.next = null;
      }
    }
    node.val = value;
    map.put(key, node);
    if (head == null) head = node;
    putToHead(node);
  }

  private void putToHead(Node node) {
    if (node.key != head.key) {
      if (node.next != null) node.next.prev = node.prev;
      if (node.prev != null) node.prev.next = node.next;
      node.next = head;
      head.prev = node;
      head = node;
    }
    if (head == tail) tail = tail.prev;
    if (tail == null) tail = head;
  }

  static class Node {
    int key;
    int val;
    Node prev;
    Node next;

    public Node(int key, int val) {
      this.key = key;
      this.val = val;
    }
  }

  public static void main(String[] args) {
    LRUCache lruCache = new LRUCache(3);
    lruCache.put(2,2);

  }

}
