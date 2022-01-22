package practice.decem.twentyOne;

import org.junit.Test;

public class Solution {
  @Test
  public void test() {
    LFUCache l = new LFUCache(2);
    /*
["LFUCache","put","put","get","put","get","get","put","get","get","get"]
[   [2],    [1,1],[2,2], [1], [3,3], [2],   [3], [4,4],[1], [3],    [4]]
     */
    l.put(1, 1);
    l.put(2, 2);
    l.get(1);
    l.put(3,3);
    l.get(2);
    l.get(3);
    l.put(4,4);
    l.get(1);
    l.get(3);
    l.get(4);
  }
}
