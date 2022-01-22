package jan.five;

import java.util.*;

public class Codec {

  String NULL = "#";
  String SEP = ",";
  // Encodes a tree to a single string.
  public String serialize(Node root) {
    StringBuilder sb = new StringBuilder();
    serialize(root, sb);
    return sb.toString();
  }

  private void serialize(Node root, StringBuilder sb) {
    if (root == null) {
      sb.append(NULL).append(SEP);
      return;
    }
    sb.append(root.val).append(SEP).append(root.children.size()).append(SEP);
    for (Node child : root.children) {
      serialize(child, sb);
    }
  }

  // Decodes your encoded data to tree.
  public Node deserialize(String data) {
    Queue<String> q = new LinkedList<>(Arrays.asList(data.split(SEP)));
    return deserialize(q);
  }

  private Node deserialize(Queue<String> q) {
    if (q.isEmpty()) return null;
    String cur = q.poll();
    if (cur.equals(NULL)) return null;
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

  static class Node {
    public int val;
    public List<Node> children;

    public Node() {
    }

    public Node(int _val) {
      val = _val;
    }

    public Node(int _val, List<Node> _children) {
      val = _val;
      children = _children;
    }
  }

}
