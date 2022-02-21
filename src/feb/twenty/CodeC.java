package feb.twenty;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class CodeC {

  String SPLIT = ",";
  String NULL = "#";
  // Encodes a tree to a single string.
  public String serialize(TreeNode root) {
    if (root == null) return "";
    StringBuilder prefix = new StringBuilder();
    serialize(root, prefix);
    return prefix.toString();
  }

  private void serialize(TreeNode root, StringBuilder prefix) {
    if (root == null) {
      prefix.append(NULL).append(SPLIT);
      return;
    }
    prefix.append(root.val).append(SPLIT);
    serialize(root.left, prefix);
    serialize(root.right, prefix);
  }


  // Decodes your encoded data to tree.
  public TreeNode deserialize(String data) {
    if (data == "") return null;
    Queue<String> q = new LinkedList<>(Arrays.asList(data.split(SPLIT)));
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
