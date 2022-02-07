package feb.six;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {

    String dir = "/home/";
    String res = simplifyPath(dir);
  }

  public String simplifyPath(String path) {
    if (path == null || path.length() == 0) return "/";
    Deque<String> deque = new ArrayDeque<>();
    String[] arr = path.split("/");
    for (String dir : arr) {
      if (dir.equals("") || dir.equals(".")) continue;
      if (dir.equals("..")) {
        deque.pollFirst();
      } else {
        deque.offerFirst(dir);
      }
    }



    StringBuilder prefix = new StringBuilder();
    while (!deque.isEmpty()) {
      prefix.append("/");
      prefix.append(deque.pollLast());
    }

    return prefix.isEmpty() ? "/" : prefix.toString();
  }


  public int[][] kClosest(int[][] points, int k) {
    if (points == null || points.length == 0) return points;

    int left = 0;
    int right = points.length - 1;
    int pivotIndex = points.length;
    while (pivotIndex != k) {
      pivotIndex = partition(points, left, right);
      if (pivotIndex < k) {
        left = pivotIndex;
      } else if (pivotIndex == k) {
        break;
      } else {
        right = pivotIndex - 1;
      }
    }
    return Arrays.copyOfRange(points, 0, k);
  }

  private int partition(int[][] points, int left, int right) {
    int[] pivot = choosePivot(points, left, right);
    int dist = getDistance(pivot);
    while (left <= right) {
      if (getDistance(points[left]) >= dist) {
        int[] tmp = points[left];
        points[left] = points[right];
        points[right] = tmp;
        right--;
      } else {
        left++;
      }
    }
    return left;
  }


  private int getDistance(int[] point) {
    return point[0] * point[0] + point[1] * point[1];
  }

  private int[] choosePivot(int[][] points, int left, int right) {
    return points[left + (right - left) / 2];
  }


  Node first;
  Node last;
  public Node treeToDoublyList(Node root) {
    if (root == null) return root;
    inOrder(root);
    first.left = last;
    last.right = first;
    return first;
  }

  private void inOrder(Node root) {
    if (root == null) return;

    inOrder(root.left);
    if (last == null) {
      first = root;
    } else {
      last.right = root;
      root.left = last;
    }
    last = root;
    inOrder(root.right);
  }


  public int subarraySum(int[] nums, int k) {
    if (nums == null || nums.length == 0) return 0;

    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);

    int sum = 0;
    int prefix = 0;

    for (int i = 0; i < nums.length; i++) {
      prefix += nums[i];
      int target = prefix - k;
      if (map.containsKey(target)) {
        sum += map.get(target);
      }
      map.put(prefix, map.getOrDefault(prefix, 0) + 1);
    }
    return sum;
  }


  public int minAddToMakeValid(String s) {
    if (s == null || s.length() == 0) return 0;

    int left = 0;
    int unmatched = 0;

    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '(') {
        left++;
        unmatched++;
      } else {
        if (left == 0) {
          unmatched++;
        } else {
          unmatched--;
          left--;
        }
      }
    }
    return unmatched;
  }


  public boolean validWordAbbreviation(String word, String abbr) {
    if (word.equals(abbr)) return true;

    int i = 0;
    int j = 0;

    while (i < word.length() && j < abbr.length()) {
      if (Character.isDigit(abbr.charAt(j))) {
        if (abbr.charAt(j) == '0') return false;
        int sum = 0;
        while (j < abbr.length() && Character.isDigit(abbr.charAt(j))) {
          sum = sum * 10 + abbr.charAt(j) - '0';
          j++;
        }
        i += sum;
      } else {
        if (word.charAt(i) != abbr.charAt(j)) return false;
        i++;
        j++;
      }
    }

    return i == word.length() && j == abbr.length();

  }


  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) return root;

    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);

    if (left != null && right != null) return root;
    return left == null ? right : left;
  }





  int sum;

  public int rangeSumBST(TreeNode root, int low, int high) {
    if (root == null) return 0;
    sum = 0;
    range(root, low, high);
    return sum;
  }

  private void range(TreeNode root, int low, int high) {
    if (root == null) return;

    if (root.val >= low && root.val <= high) {
      sum += root.val;
    }
    if (root.val > low) {
      range(root.left, low, high);
    }

    if (root.val < high) {
      range(root.right, low, high);
    }

  }

  public int[] findBuildings(int[] heights) {
    if (heights == null || heights.length == 0) return heights;
    int m = heights.length;
    int[] res = new int[m];
    int index = m - 2;
    res[m - 1] = m - 1;
    int max = heights[m - 1];

    for (int i = m - 2; i >= 0; i--) {
      if (heights[i] > max) {
        res[index--] = i;
        max = heights[i];
      }
    }

    int i = 0;
    index++;

    while (index < m) {
      res[i++] = res[index++];
    }
    return Arrays.copyOfRange(res, 0, i);
  }


  public List<List<Integer>> verticalOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;

    Map<Integer, List<Integer>> map = new HashMap<>();
    Queue<TreeNode> q = new LinkedList<>();
    Map<TreeNode, Integer> weight = new HashMap<>();
    int min = 0;
    q.offer(root);
    weight.put(root, 0);

    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        TreeNode cur = q.poll();
        int w = weight.get(cur);
        map.putIfAbsent(w, new ArrayList<>());
        map.get(w).add(cur.val);
        min = Math.min(w, min);
        if (cur.left != null) {
          q.offer(cur.left);
          weight.put(cur.left, w - 1);
        }
        if (cur.right != null) {
          q.offer(cur.right);
          weight.put(cur.right, w + 1);
        }
      }
    }
    while (map.containsKey(min)) {
      res.add(map.get(min++));
    }
    return res;
  }


  public boolean validPalindrome(String s) {
    if (s == null || s.length() == 0) return true;
    return validPalindrome(s, 0, s.length() - 1, false);
  }

  private boolean validPalindrome(String s, int i, int j, boolean isDeleted) {
    while (i <= j) {
      if (s.charAt(i) != s.charAt(j)) {
        if (isDeleted) return false;
        return validPalindrome(s, i, j - 1, true) || validPalindrome(s, i + 1, j, true);
      }
      i++;
      j--;
    }
    return true;
  }


  public String minRemoveToMakeValid(String s) {
    if (s == null || s.length() == 0) return s;
    int m = s.length();
    char[] res = new char[m];
    int index = 0;

    int left = 0;
    int matched = 0;
    for (char ch : s.toCharArray()) {
      if (ch == '(') {
        left++;
      } else if (ch == ')') {
        if (left > 0) {
          left--;
          matched++;
        } else {
          continue;
        }
      }
      res[index++] = ch;
    }
    System.out.println(new String(res, 0, index));
    int i = 0;
    for (int j = 0; j < index; j++) {
      if (res[j] == '(') {
        if (matched > 0) {
          matched--;
        } else {
          continue;
        }
      }
      char ch = res[j];
      res[i++] = res[j];
    }
    return new String(res, 0, i);
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




  static class Node {
    public int val;
    public Node left;
    public Node right;

    public Node() {}

    public Node(int _val) {
      val = _val;
    }

    public Node(int _val,Node _left,Node _right) {
      val = _val;
      left = _left;
      right = _right;
    }
  };

}
