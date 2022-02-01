package jan.thirty;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Codec {
  String NULL = "#";
  String SEP = ",";
  // Encodes a tree to a single string.
  public String serialize(TreeNode root) {
    StringBuilder prefix = new StringBuilder();
    serialize(root, prefix);
    return prefix.toString();
  }

  private void serialize(TreeNode root, StringBuilder prefix) {
    if (root == null) {
      prefix.append(NULL).append(SEP);
      return;
    }
    prefix.append(root.val).append(SEP);
    serialize(root.left, prefix);
    serialize(root.right, prefix);
  }

  // Decodes your encoded data to tree.
  public TreeNode deserialize(String data) {
    Queue<String> q = new LinkedList<>(Arrays.asList(data.split(SEP)));
    return deserialize(q);
  }

  private TreeNode deserialize(Queue<String> q) {
    if (q.isEmpty()) return null;
    String cur = q.poll();
    if (cur.equals(NULL)) return null;
    TreeNode root = new TreeNode(Integer.parseInt(cur));
    root.left = deserialize(q);
    root.right = deserialize(q);
    return root;
  }

  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }
}
