package feb.six;

public class SparseVector {
  int[] arr;
  Node root;
  SparseVector(int[] nums) {
    arr = nums;
    root = new Node(-1, 0);
    Node cur = root;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != 0) {
        cur.next = new Node(i, nums[i]);
        cur = cur.next;
      }
    }
  }

  // Return the dotProduct of two sparse vectors
  public int dotProduct(SparseVector vec) {
    Node cur = root.next;
    int res = 0;
    while (cur != null) {
      res += cur.value * vec.arr[cur.index];
      cur = cur.next;
    }
    return res;
  }

  static class Node {
    int index;
    int value;
    Node next;

    public Node(int index, int value) {
      this.index = index;
      this.value = value;
    }
  }

}
