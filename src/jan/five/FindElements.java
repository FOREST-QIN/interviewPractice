package jan.five;

import java.util.HashSet;
import java.util.Set;

public class FindElements {

  TreeNode root;
  Set<Integer> set;

  public FindElements(TreeNode root) {
    this.root = root;
    this.set = new HashSet<>();
    root.val = 0;
    recover(root);
  }

  private void recover(TreeNode root) {
    if (root == null) return;
    int val = root.val;
    set.add(root.val);
    if (root.left != null) {
      root.left.val = 2 * val + 1;
      recover(root.left);
    }
    if (root.right != null) {
      root.right.val = 2 * val + 2;
      recover(root.right);
    }
  }


  public boolean find(int target) {
    return set.contains(target);
  }

  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
      this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }
  }

}
