package feb.nine;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    String s = "01011";
    int res = theFinalProblem(s);
  }

  public int theFinalProblem(String target) {
    char[] arr = target.toCharArray();
    /*

     */
    int m = target.length();
    int steps = 0;
    char t = arr[m - 1];
    int index = m - 1;
    while (index >= 0) {
      while (index >= 0 && arr[index] == t) {
        index--;
      }
      steps++;
      if (index >= 0) t = arr[index];
    }
    return steps;
  }

  public int[] findOrder(int n, int[][] prerequisites) {
    int[] res = new int[n];
    int[] count = new int[n];
    Map<Integer, List<Integer>> map = new HashMap<>();
    for (int[] r : prerequisites) {
      int pre = r[1];
      int next = r[0];
      map.putIfAbsent(pre, new ArrayList<>());
      map.get(pre).add(next);
      count[next]++;
    }

    int index = 0;
    Queue<Integer> q = new LinkedList<>();
    for (int i = 0; i < n; i++) {
      if (count[i] == 0) q.offer(i);
    }

    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        int cur = q.poll();
        res[index++] = cur;
        if (map.containsKey(cur)) {
          for (int next : map.get(cur)) {
            count[next]--;
            if (count[next] == 0) q.offer(next);
          }
        }
      }

    }
    return index == n ? res : new int[]{};
  }

  public boolean containsDuplicate(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int num : set) {
      if (set.contains(num)) return true;
      set.add(num);
    }
    return false;
  }


  public int[] productExceptSelf(int[] nums) {
    if (nums == null || nums.length == 0) return nums;
    int m = nums.length;
    int[] left = new int[m];
    left[0] = 1;
    for (int i = 1; i < m; i++) {
      left[i] = left[i - 1] * nums[i - 1];
    }
    int right = 1;
    for (int i = m - 1; i >= 0; i--) {
      left[i] = left[i] * right;
      right *= nums[i];
    }
    return left;
  }


  public String alienOrder(String[] words) {
    Map<Character, List<Character>> graph = new HashMap<>();
    Map<Character, Integer> count = new HashMap<>();

    for (String word : words) {
      for (int i = 0; i < word.length(); i++) {
        graph.putIfAbsent(word.charAt(i), new ArrayList<>());
        count.putIfAbsent(word.charAt(i), 0);
      }
    }
    int m = words.length;
    for (int i = 0; i < m - 1; i++) {
      String first = words[i];
      String second = words[i + 1];

      if (first.equals(second)) continue;
      if (first.length() > second.length() && first.startsWith(second)) return "";
      for (int j = 0; j < Math.min(first.length(), second.length()); j++) {
        if (first.charAt(j) != second.charAt(j)) {
          graph.get(first.charAt(j)).add(second.charAt(j));
          count.put(second.charAt(j), count.get(second.charAt(j)) + 1);
          break;
        }
      }
    }
    StringBuilder prefix = new StringBuilder();
    Queue<Character> q = new LinkedList<>();
    for (char ch : count.keySet()) {
      if (count.get(ch) == 0) q.offer(ch);
    }

    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        char cur = q.poll();
        prefix.append(cur);
        for (char next : graph.get(cur)) {
          if (count.get(next) == 1) q.offer(next);
          count.put(next, count.get(next) - 1);
        }
      }
    }
    return prefix.length() == count.size() ? prefix.toString() : "";
  }



  public int romanToInt(String s) {
    int n = 0;
    char prev = ' ';
    for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      n += getValue(ch, prev);
      prev = ch;
    }
    return n;
  }

  private int getValue(char c, char prev) {
    switch (c) {
      case 'I':
        return 1;
      case 'V':
        return prev == 'I' ? 3 : 5;
      case 'X':
        return prev == 'I' ? 8 : 10;
      case 'L':
        return prev == 'X' ? 30 : 50;
      case 'C':
        return prev == 'X' ? 80 : 100;
      case 'D':
        return prev == 'C' ? 300 : 500;
      case 'M':
        return prev == 'C' ? 800 : 1000;
    }
    return 0;
  }

  public List<String> letterCombinations(String digits) {
    List<String> res = new ArrayList<>();
    if (digits == null || digits.length() == 0) return res;
    StringBuilder prefix = new StringBuilder();
    Map<Character, char[]> map = getMap();
    dfs(digits, 0, prefix, map, res);
    return res;
  }

  private void dfs(String digits, int index, StringBuilder prefix, Map<Character, char[]> map, List<String> res) {
    if (index == digits.length()) {
      res.add(prefix.toString());
      return;
    }
    for (char ch : map.get(digits.charAt(index))) {
      prefix.append(ch);
      dfs(digits, index + 1, prefix, map, res);
      prefix.deleteCharAt(prefix.length() - 1);
    }
  }

  private Map<Character, char[]> getMap() {
    Map<Character, char[]> map = new HashMap<>();
    map.put('2', new char[]{'a', 'b', 'c'});
    map.put('3', new char[]{'d', 'e', 'f'});
    map.put('4', new char[]{'g','h','i'});
    map.put('5', new char[]{'j', 'k', 'l'});
    map.put('6', new char[]{'m', 'n', 'o'});
    map.put('7', new char[]{'p', 'q', 'r', 's'});
    map.put('8', new char[]{'t', 'u', 'v'});
    map.put('9', new char[]{'w', 'x', 'y', 'z'});
    return map;
  }


  public int ladderLength(String beginWord, String endWord, List<String> wordList) {
    if (beginWord.equals(endWord)) return 0;
    Set<String> set = new HashSet<>(wordList);
    if (!set.contains(endWord)) return 0;

    Queue<String> one = new LinkedList<>();
    Set<String> vOne = new HashSet<>();
    one.offer(beginWord);
    vOne.add(beginWord);

    Queue<String> two = new LinkedList<>();
    Set<String> vTwo = new HashSet<>();
    two.offer(endWord);
    vTwo.add(endWord);

    int step = 0;

    while (!one.isEmpty() && !two.isEmpty()) {
      step++;
      if (one.size() > two.size()) {
        Queue<String> q = one;
        one = two;
        two = q;

        Set<String> s = vOne;
        vOne = vTwo;
        vTwo = s;
      }
      int size = one.size();
      for (int i = 0; i < size; i++) {
        char[] arr = one.poll().toCharArray();
        for (int j = 0; j < arr.length; j++) {
          for (char ch = 'a'; ch <= 'z'; ch++) {
            char tmp = arr[j];
            arr[j] = ch;
            String newWord = new String(arr);
            if (set.contains(newWord) && !vOne.contains(newWord)) {
              one.offer(newWord);
              vOne.add(newWord);
            }
            if (vTwo.contains(newWord)) return step + 1;
            arr[j] = tmp;
          }
        }
      }
    }
    return 0;
  }


  public int[] topKFrequent(int[] nums, int k) {
    if (nums == null || nums.length == 0) return nums;
    // count
    List<Integer>[] bucket = new List[nums.length + 1];
    Map<Integer, Integer> count = new HashMap<>();

    for (int num : nums) {
      count.put(num, count.getOrDefault(num, 0) + 1);
    }

    for (int key : count.keySet()) {
      int freq = count.get(key);
      if (bucket[freq] == null) {
        bucket[freq] = new ArrayList<>();
      }
      bucket[freq].add(key);
    }
    int[] res = new int[k];
    int index = 0;
    for (int i = bucket.length - 1; i >= 0; i--) {
      if (bucket[i] != null) {
        for (int num : bucket[i]) {
          if (index == k) return res;
          res[index++] = num;
        }
      }
    }
    return res;
  }


  public int findPeakElement(int[] nums) {
    if (nums.length == 1) return 0;
    int l = 0;
    int r = nums.length - 1;
    int mid = l + (r - l) / 2;
    while (l < r) {
      mid = l + (r - l) / 2;
      if (nums[mid] < nums[mid + 1]) l = mid + 1;
      else r = mid;
    }
    return l;
  }

  public double myPow(double x, int n) {
    if (n == 0) return 0;
    if (x == 0 || x == 1 || n == 1) return x;
    double num = myPow(x, n / 2);
    if (n % 2 == 0) return num * num;
    if (n < 0) {
      return num * num / x;
    } else {

      return num * num * x;
    }
  }


  public int search(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;
    return binarySearch(nums, 0, nums.length - 1, target);
  }

  private int binarySearch(int[] nums, int left, int right, int target) {
    if (left == right) return nums[left] == target ? left : -1;
    if (left > right) return -1;

    if (nums[left] < nums[right]) {
      while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) return mid;
        else if (nums[mid] > target) right = mid - 1;
        else left = mid + 1;
      }
      return -1;
    } else {
      int mid = left + (right - left) / 2;
      int lRes = binarySearch(nums, left, mid, target);
      int rRes = binarySearch(nums, mid + 1, right, target);
      return lRes == -1 ? rRes : lRes;
    }
  }


  public List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> res = new ArrayList<>();
    if (matrix == null || matrix.length == 0) return res;

    int left = 0;
    int right = matrix[0].length - 1;
    int top = 0;
    int bottom = matrix.length - 1;

    /*
    [[1,2,3,4,5,6,7,8,9,10]
    ,[11,12,13,14,15,16,17,18,19,20]]
     */

    while (left < right && top < bottom) {

      for (int i = left; i < right; i++) {
        res.add(matrix[top][i]);
      }
      for (int i = top; i < bottom; i++) {
        res.add(matrix[i][right]);
      }
      for (int i = right; i > left; i--) {
        res.add(matrix[bottom][i]);
      }
      for (int i = bottom; i > top; i--) {
        res.add(matrix[i][left]);
      }
      left++;
      right--;
      top++;
      bottom--;
    }
    if (left == right) {
      for (int i = top; i <= bottom; i++) {
        res.add(matrix[i][left]);
      }
    } else if (top == bottom) {
      for (int i = left; i <= right; i++) {
        res.add(matrix[top][i]);
      }
    }
    return res;
  }

  public List<List<String>> groupAnagrams(String[] strs) {
    List<List<String>> res = new ArrayList<>();
    if (strs == null || strs.length == 0) return res;
    Map<Integer, List<String>> map = new HashMap<>();

    for (String str : strs) {
      int code = getHashCode(str);
      map.computeIfAbsent(code, k -> new ArrayList<>()).add(str);
    }
    res.addAll(map.values());
    return res;
  }

  private int getHashCode(String word) {
    int[] count = new int[26];
    for (int i = 0; i < n; i++) {
      count[word.charAt(i) - 'a']++;
    }
    return Arrays.hashCode(count);
  }


  int m;
  int n;
  char visited = '#';
  TrieNode root;
  int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

  public List<String> findWords(char[][] board, String[] words) {
    this.m = board.length;
    this.n = board[0].length;
    this.root = new TrieNode();
    Set<String> set = new HashSet<>();
    addWords(words);

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (root.children.containsKey(board[i][j])) {
          backtrack(board, i, j, root, set);
        }
      }
    }
    return new ArrayList<>(set);
  }

  private void backtrack(char[][] board, int i, int j, TrieNode cur, Set<String> set) {
    if (i < 0 || j < 0 || i >= m || j >= n || board[i][j] == visited || !cur.children.containsKey(board[i][j])) return;
    char ch = board[i][j];
    board[i][j] = visited;
    TrieNode node = cur.children.get(ch);
    if (node.word != null) set.add(node.word);

    for (int[] dir : DIRS) {
      backtrack(board, i + dir[0], j + dir[1], node, set);
    }
    board[i][j] = ch;
    if (node.children.isEmpty()) cur.children.remove(ch);
  }

  private void addWords(String[] words) {
    for (String word : words) add(word);
  }

  private void add(String word) {
    TrieNode cur = root;
    for (char ch : word.toCharArray()) {
      if (!cur.children.containsKey(ch)) {
        cur.children.put(ch, new TrieNode());
      }
      cur = cur.children.get(ch);
    }
    cur.word = word;
  }

  static class TrieNode {

    String word;
    Map<Character, TrieNode> children;

    public TrieNode() {
      this.children = new HashMap<>();
      this.word = null;
    }
  }


  public int[] maxSlidingWindow(int[] nums, int k) {
    int m = nums.length;
    int[] res = new int[m - k + 1];
    int index = 0;
    // [f,l] <-
    Deque<Integer> deque = new ArrayDeque<>();
    for (int i = 0; i < m; i++) {
      while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
        deque.pollLast();
      }
      deque.offerLast(i);
      while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
        deque.pollFirst();
      }
      if (i >= k - 1) {
        res[index++] = nums[deque.peekFirst()];
      }
    }
    return res;
  }

  public List<String> generateParenthesis(int n) {
    List<String> res = new ArrayList<>();
    if (n == 0) return res;
    StringBuilder prefix = new StringBuilder();
    generate(0, 0, n, prefix, res);
    return res;
  }

  private void generate(int l, int r, int n, StringBuilder prefix, List<String> res) {
    if (l > n || r > n) return;
    if (l == n && r == l) {
      res.add(prefix.toString());
      return;
    }
    if (l < n) {
      prefix.append("(");
      generate(l + 1, r, n, prefix, res);
      prefix.deleteCharAt(prefix.length() - 1);
    }

    if (r < l) {
      prefix.append(")");
      generate(l, r + 1, n, prefix, res);
      prefix.deleteCharAt(prefix.length() - 1);
    }
  }


  public String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    StringBuilder prefix = new StringBuilder(strs[0]);
    for (String s : strs) {
      while (!s.startsWith(prefix.toString())) {
        prefix.deleteCharAt(prefix.length() - 1);
      }
    }
    return prefix.toString();
  }

  public int reverse(int x) {
    if (x == 0) return x;
    boolean isNegative = x < 0;
    x = Math.abs(x);
    long res = 0;
    while (x > 0) {
      res = res * 10 + x % 10;
      x /= 10;
    }
    if (res > Integer.MAX_VALUE) return 0;
    return isNegative ? -(int) res : (int) res;
  }
}
