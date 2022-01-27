package jan.twentySix;

public class SparseVector {

  Node dummy = new Node(-1, 0);
  int[] nums;
  SparseVector(int[] nums) {
    Node cur = dummy;
    this.nums = nums;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != 0) {
        cur.next = new Node(i, nums[i]);
        cur = cur.next;
      }
    }
  }

  // Return the dotProduct of two sparse vectors
  public int dotProduct(SparseVector vec) {
    int sum = 0;
    Node cur = dummy.next;
    while (cur != null) {
      sum += cur.vale * vec.nums[cur.index];
      cur = cur.next;
    }
    return sum;
  }

  static class Node {
    int index;
    int vale;
    Node next;

    public Node(int index, int vale) {
      this.index = index;
      this.vale = vale;
    }
  }

}
