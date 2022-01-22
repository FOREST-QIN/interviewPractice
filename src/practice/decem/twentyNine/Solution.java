package practice.decem.twentyNine;

import java.util.*;

public class Solution {

  public int countNodes(TreeNode root) {
    TreeNode l = root, r = root;
    int hl = 0, hr = 0;
    while (l != null) {
      hl++;
      l = l.left;
    }
    while (r != null) {
      hr++;
      r = r.right;
    }
    if (hl == hr) return (int) Math.pow(2, hl) - 1;
    return 1 + countNodes(root.left) + countNodes(root.right);
  }

  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) return root;
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    if (left != null && right != null) return root;
    return left == null ? right : left;
  }


  public String serialize(TreeNode root) {
    StringBuilder sb = new StringBuilder();
    traverse(root, sb);
    return sb.toString();
  }

  String NULL = "#";
  String SEP = ",";

  private void traverse(TreeNode root, StringBuilder sb) {
    if (root == null) {
      sb.append(NULL).append(SEP);
      return;
    }
    sb.append(root.val).append(SEP);
    traverse(root.left, sb);
    traverse(root.right, sb);
  }
  // Decodes your encoded data to tree.
  public TreeNode deserialize(String data) {
    Queue<String> q = new ArrayDeque<>();
    for (String s : data.split(",")) {
      q.offer(s);
    }
    return helper(q);
  }

  private TreeNode helper(Queue<String> q) {
    if (q.isEmpty()) return null;
    String first = q.poll();
    if (first.equals(NULL)) return null;
    TreeNode root = new TreeNode(Integer.parseInt(first));
    root.left = helper(q);
    root.right = helper(q);
    return root;
  }










  public int maxSumBST(TreeNode root) {
    int[] max = {Integer.MIN_VALUE};
    helper(root, max);
    return max[0];
  }

  private int[] helper(TreeNode root, int[] max) {
    if (root == null) {
      return new int[]{1, Integer.MAX_VALUE, Integer.MIN_VALUE, 0};
    }
    int[] left = helper(root.left, max);
    int[] right = helper(root.right, max);
    int[] res = new int[4];

    if (left[0] == 1 && right[0] == 1 && root.val > left[2] && root.val < right[1]) {
      res[0] = 1;
      res[1] = Math.min(left[1], root.val);
      res[2] = Math.max(right[2], root.val);
      res[3] = left[3] + right[3] + root.val;
      max[0] = Math.max(max[0], res[3]);
    } else {
      res[0] = 0;
    }
    return res;
  }


  public List<TreeNode> generateTrees(int n) {
    if (n == 0) return new ArrayList<>();
    return build(1, n);
  }

  private List<TreeNode> build(int lo, int hi) {
    List<TreeNode> res = new ArrayList<>();
    if (lo > hi) {
      res.add(null);
      return res;
    }
    for (int i = lo; i <= hi; i++) {
      List<TreeNode> left = build(lo, i - 1);
      List<TreeNode> right = build(i + 1, hi);
      for (TreeNode l : left) {
        for (TreeNode r : right) {
          TreeNode root = new TreeNode(i);
          root.left = l;
          root.right = r;
          res.add(root);
        }
      }
    }
    return res;
  }


  public int numTrees(int n) {
    int[] dp = new int[n + 1];
    dp[0] = 1;
    dp[1] = 1;

    for (int i = 2; i <= n; i++) {
      int count = 0;
      for (int j = 0; j < i; j++) {
        count += dp[j] * dp[i - j - 1];
      }
      dp[i] = count;
    }
    return dp[n];
  }

  public TreeNode deleteNode(TreeNode root, int key) {
    if (root == null) return null;
    if (root.val == key) {
      if (root.left == null) {
        return root.right;
      } else if (root.right == null) {
        return root.left;
      } else {
        TreeNode min = getMin(root.right);
        root.right = deleteNode(root.right, min.val);
        min.left = root.left;
        min.right = root.right;
        root = min;
      }
    } else if (root.val > key) {
      root.left = deleteNode(root.left, key);
    } else {
      root.right = deleteNode(root.right, key);
    }
    return root;
  }

  private TreeNode getMin(TreeNode root) {
    while (root.left != null) root = root.left;
    return root;
  }

  public TreeNode insertIntoBST(TreeNode root, int val) {
    if (root == null) return new TreeNode(val);
    if (root.val < val) {
      root.right = insertIntoBST(root.right, val);
    }
    if (root.val > val) {
      root.left = insertIntoBST(root.left, val);
    }
    return root;
  }

  public boolean isValidBST(TreeNode root) {
    return helper(root, null, null);
  }

  private boolean helper(TreeNode root, TreeNode min, TreeNode max) {
    if (root == null) return true;
    if (min != null && root.val <= min.val) return false;
    if (max != null && root.val >= max.val) return false;

    return helper(root.left, min, root) && helper(root.right, root, max);
  }


  public TreeNode convertBST(TreeNode root) {
    int[] sum = new int[1];
    traverse(root, sum);
    return root;
  }

  private void traverse(TreeNode root, int[] sum) {
    if (root == null) return;
    traverse(root.right, sum);
    sum[0] += root.val;
    root.val = sum[0];
    traverse(root.left, sum);
  }

  public int kthSmallest(TreeNode root, int k) {
    int[] rank = new int[1];
    int[] res = new int[1];
    traverse(root, k, rank, res);
    return res[0];
  }

  private void traverse(TreeNode root, int k, int[] rank, int[] res) {
    if (root == null) return;
    traverse(root.left, k, rank, res);
    rank[0]++;
    if (rank[0] == k) {
      res[0] = root.val;
      return;
    }
    traverse(root.right, k, rank, res);
  }


  public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
    Map<String, Integer> memo = new HashMap<>();
    List<TreeNode> res = new ArrayList<>();
    traverse(root, memo, res);
    return res;
  }

  private String traverse(TreeNode root, Map<String, Integer> memo, List<TreeNode> res) {
    if (root == null) return "#";
    String left = traverse(root.left, memo, res);
    String right = traverse(root.right, memo, res);
    String cur = left + "," + right + "," + root.val;
    int freq = memo.getOrDefault(cur, 0);
    if (freq == 1) res.add(root);

    memo.put(cur, freq + 1);
    return cur;
  }

  int idx;

  public TreeNode buildTree(int[] inorder, int[] postorder) {
      Map<Integer, Integer> map = getMap(inorder);
      idx = inorder.length - 1;
      return build(map, postorder, 0, postorder.length - 1);
  }

  private TreeNode build(Map<Integer, Integer> inorder, int[] post, int start, int end) {
    if (start > end) return null;
    TreeNode root = new TreeNode(post[idx--]);
    int index = inorder.get(root.val);
    root.right = build(inorder, post, index + 1, end);
    root.left = build(inorder, post, start, index - 1);
    return root;
  }

  private Map<Integer, Integer> getMap(int[] inorder) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) {
      map.put(inorder[i], i);
    }
    return map;
  }








  /*
  preorder = [3,9,20,15,7],
  inorder = [9,3,15,20,7]
   */


  public TreeNode constructMaximumBinaryTree(int[] nums) {
    return build(nums, 0, nums.length - 1);
  }

  private TreeNode build(int[] nums, int l, int r) {
    if (l > r) return null;

    int index = -1;
    int max = Integer.MIN_VALUE;

    for (int i = l; i <= r; i++) {
      if (nums[i] > max) {
        index = i;
        max = nums[i];
      }
    }

    TreeNode root = new TreeNode(max);
    root.left = build(nums, l, index - 1);
    root.right = build(nums, index + 1, r);
    return root;
  }

  public class TreeNode {
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
