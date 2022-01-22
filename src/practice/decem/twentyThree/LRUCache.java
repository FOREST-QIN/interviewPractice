package practice.decem.twentyThree;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {

  final int capacity;
  Map<Integer, ListNode> map;
  ListNode head;
  ListNode tail;

  public LRUCache(int capacity) {
    this.capacity = capacity;
    this.map = new HashMap<>();
  }

  public int get(int key) {
    if (!map.containsKey(key)) return -1;
    ListNode node = map.get(key);

    moveToHead(node);

    return node.val;
  }

  public void put(int key, int value) {
        /*
    1,1 <-> 2,2 -> null
     t
     H


        */
    ListNode node = map.get(key);
    if (node == null) {
      node = new ListNode(key, value);
      if (map.size() == capacity) {
        map.remove(tail.key);
        tail = tail.prev;
        if (tail != null) tail.next = null;
      }
    }

    node.val = value;
    map.put(key, node);
    if (head == null) head = node;
    moveToHead(node);

  }


  private void moveToHead(ListNode node) {
    if (node.key != head.key) {
      if (node.prev != null) node.prev.next = node.next;
      if (node.next != null) node.next.prev = node.prev;

      node.next = head;
      head.prev = node;
      head = node;
    }

    // if (head == tail) tail = tail.prev;
    if (tail == null) tail = head;

  }

  static class ListNode {
    int key;
    int val;
    ListNode prev;
    ListNode next;

    ListNode(int key, int val) {
      this.key = key;
      this.val = val;
    }

  }

}
