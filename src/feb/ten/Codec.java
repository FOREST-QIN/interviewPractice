package feb.ten;

import java.util.*;

public class Codec {

  static String SPLIT = ",";
  static String NULL = "#";
  // Encodes a tree to a single string.
  public String serialize(Node root) {
    if (root  == null) return "";
    StringBuilder prefix = new StringBuilder();
    serialize(root, prefix);
    return prefix.toString();
  }

  private void serialize(Node root, StringBuilder prefix) {
    if (root == null) {
      prefix.append(NULL).append(SPLIT);
      return;
    }
    prefix.append(root.val).append(SPLIT);
    prefix.append(root.children.size()).append(SPLIT);
    for (Node child : root.children) {
      serialize(child, prefix);
    }
  }

  // Decodes your encoded data to tree.
  public Node deserialize(String data) {
    if (data.equals("")) return null;
    Queue<String> q = new LinkedList<>(Arrays.asList(data.split(SPLIT)));
    return deserialize(q);
  }

  private Node deserialize(Queue<String> q) {
    if (q.isEmpty()) return null;
    String cur = q.poll();
    int size = Integer.parseInt(q.poll());
    Node root = new Node(Integer.parseInt(cur), new ArrayList<>());
    for (int i = 0; i < size; i++) {
      root.children.add(deserialize(q));
    }
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

  class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
      val = _val;
    }

    public Node(int _val, List<Node> _children) {
      val = _val;
      children = _children;
    }
  }

}
