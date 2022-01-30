package jan.twentyNine;

import java.util.*;

public class Solution {

  public List<String> topKFrequent(String[] words, int k) {
    List<String> list = new ArrayList<>();
    Map<String, Integer> counts = new HashMap<>();

    for (String word : words) {
      counts.put(word, counts.getOrDefault(word, 0) + 1);
    }

    PriorityQueue<String> minHeap = new PriorityQueue<>((a, b) -> {
      if (Objects.equals(counts.get(a), counts.get(b))) return a.compareTo(b);
      return Integer.compare(counts.get(b), counts.get(a));
    });

    for (String word : counts.keySet()) {
      minHeap.offer(word);
    }
    for (int i = 0; i < k; i++) {
      list.add(minHeap.poll());
    }
    return list;
  }




  public Node lowestCommonAncestor(Node p, Node q) {
    Node p1 = p;
    Node q1 = q;
    while (p1 != q1) {
      p1 = p1 == null ? q : p1.parent;
      q1 = q1 == null ? p : q1.parent;
    }
    return q1;
  }

  public List<List<String>> suggestedProducts(String[] products, String searchWord) {
    Arrays.sort(products);
    Trie root = new Trie();
    for (int i = 0; i < products.length; i++) {
      root.addWords(products[i], i);
    }
    return root.searchWord(searchWord, products);
  }

static class Trie {
  Trie[] children;
  List<Integer> indices;

  public Trie() {
    this.children = new Trie[26];
    this.indices = new ArrayList<>();
  }

  public void addWords(String word, int index) {
    Trie cur = this;
    for (char c : word.toCharArray()) {
      if (cur.children[c - 'a'] == null) {
        cur.children[c - 'a'] = new Trie();
      }
      if (cur.children[c - 'a'].indices.size() < 3) {
        cur.children[c - 'a'].indices.add(index);
      }
      cur = cur.children[c - 'a'];
    }
  }

  public List<List<String>> searchWord(String searchWord, String[] products) {
    List<List<String>> res = new ArrayList<>();
    Trie cur = this;
    for (char c : searchWord.toCharArray()) {
      List<String> wordList = new ArrayList<>();
      if (cur == null || cur.children[c - 'a'] == null) {
        cur = null;
        res.add(wordList);
        continue;
      }
      for (int index : cur.children[c - 'a'].indices) {
        wordList.add(products[index]);
      }
      cur = cur.children[c - 'a'];
      res.add(wordList);
    }
    return res;
  }

}


  public int findPeakElement(int[] nums) {
    int l = 0;
    int r = nums.length - 1;

    while (l < r) {
      int mid = l + (r - l) / 2;
      if (nums[mid] > nums[mid + 1]) {
        r = mid;
      } else {
        l = mid + 1;
      }
    }
    return l;
  }

  int max = Integer.MIN_VALUE;

  public int maxPathSum(TreeNode root) {
    getMax(root);
    return max;
  }

  private int getMax(TreeNode root) {
    if (root == null) return 0;
    int left = Math.max(getMax(root.left), 0);
    int right = Math.max(getMax(root.right), 0);
    max = Math.max(max, left + right + root.val);
    return Math.max(left, right) + root.val;
  }

  public int maxArea(int[] height) {
    if (height == null || height.length == 0) return 0;
    int m = height.length;

    int i = 0;
    int j = m - 1;
    int max = 0;

    while (i <= j) {
      int cur = Math.min(height[i], height[j]);
      max = Math.max(cur * (j - i), max);
      while (i < m && height[i] <= cur) {
        i++;
      }
      while (j >= 0 && height[j] <= cur) {
        j--;
      }
    }
    return max;
  }


  int m;
  int n;
  int[][] memo;

  public int calculateMinimumHP(int[][] dungeon) {
    this.m = dungeon.length;
    this.n = dungeon[0].length;
    this.memo = new int[m][n];
    for (int[] row : memo) Arrays.fill(row, -1);

    return dp(dungeon, 0, 0);
  }

  private int dp(int[][] grid, int i, int j) {
    if (i == m - 1 && j == n - 1) {
      return grid[i][j] < 0 ? -grid[i][j] + 1 : 1;
    }
    if (i == m || j == n) return Integer.MAX_VALUE;

    if (memo[i][j] != -1) return memo[i][j];

    int res = Math.min(dp(grid, i + 1, j), dp(grid, i, j + 1)) - grid[i][j];
    memo[i][j] = res <= 0 ? 1 : res;
    return memo[i][j];
  }


  public String alienOrder(String[] words) {
    Map<Character, List<Character>> graph = new HashMap<>();
    Map<Character, Integer> counts = new HashMap<>();

    for (String word : words) {
      for (char ch : word.toCharArray()) {
        if (!graph.containsKey(ch)) {
          graph.put(ch, new ArrayList<>());
          counts.put(ch, 0);
        }
      }
    }
    // build graph and count
    int m = words.length;
    for (int i = 0; i < m - 1; i++) {
      String first = words[i];
      String second = words[i + 1];

      if (first.length() > second.length() && first.startsWith(second)) return "";
      for (int j = 0; j < Math.min(first.length(), second.length()); j++) {
        if (first.charAt(j) != second.charAt(j)) {
          graph.get(first.charAt(j)).add(second.charAt(j));
          counts.put(second.charAt(j), counts.get(second.charAt(j)) + 1);
          break;
        }
      }
    }

    StringBuilder prefix = new StringBuilder();
    Queue<Character> q = new LinkedList<>();
    for (char ch : graph.keySet()) {
      if (counts.get(ch) == 0) q.offer(ch);
    }

    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        char cur = q.poll();
        prefix.append(cur);
        for (char next : graph.get(cur)) {
          counts.put(next, counts.get(next) - 1);
          if (counts.get(next) == 0) q.offer(next);
        }
      }
    }
    return prefix.length() == counts.size() ? prefix.toString() : "";
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
  public Node parent;
}

}
