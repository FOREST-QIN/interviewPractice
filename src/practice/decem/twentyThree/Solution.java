package practice.decem.twentyThree;

import org.junit.Test;

public class Solution {
  @Test
  public void test() {
    LRUCache lru = new LRUCache(2);
    lru.put(1,1);
    lru.put(2,2);
    lru.get(1);
    lru.put(3,3);
    int i = lru.get(2);
  }

}
