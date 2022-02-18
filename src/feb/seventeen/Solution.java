package feb.seventeen;

import java.util.*;

public class Solution {

  public boolean canPartitionKSubsets(int[] nums, int k) {
    if (k > nums.length) return false;
    int sum = 0;
    for (int num : nums) sum += num;
    if (sum % k != 0) return false;
    boolean[] visited = new boolean[nums.length];
    Arrays.sort(nums);
    return dfs(nums, 0, nums.length - 1, visited, sum / k, k);
  }

  private boolean dfs(int[] nums, int sum, int st, boolean[] visited, int target, int round) {
    if (round == 0) return true;
    if (sum == target && dfs(nums, 0, nums.length - 1, visited, target, round - 1)) {
      return true;
    }
    for (int i = st; i >= 0; i--) {
      if (!visited[i] && sum + nums[i] <= target) {
        visited[i] = true;
        if (dfs(nums, sum + nums[i], i - 1, visited, target, round)) return true;
        visited[i] = false;
      }
    }
    return false;
  }


  int[][] DIRS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
  public int shortestBridge(int[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    int m = grid.length;
    int n = grid[0].length;
    Queue<int[]> q = new LinkedList<>();
    boolean isFound = false;
    for (int i = 0; i < m; i++) {
      if (isFound) break;
      for (int j = 0; j < n; j++) {
        if (grid[i][j] == 1) {
          dfs(grid, i, j, q);
          isFound = true;
          break;
        }
      }
    }

    int step = 0;
    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        int[] cur = q.poll();
        int x = cur[0];
        int y = cur[1];
        for (int[] dir : DIRS) {
          int curX = x + dir[0];
          int curY = y + dir[1];
          if (curX >= 0 && curX < m && curY >= 0 && curY < n) {
            if (grid[curX][curY] == 0) {
              q.offer(new int[]{curX, curY});
              grid[curX][curY] = 2;
            } else if (grid[curX][curY] == 1) {
              return step;
            }
          }

        }
      }
      step++;
    }
    return step;
  }

  private void dfs(int[][] grid, int i, int j, Queue<int[]> q) {
    if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] != 1) return;
    grid[i][j] = 2;
    q.offer(new int[]{i, j});
    for (int[] dir : DIRS) {
      dfs(grid, i + dir[0], j + dir[1], q);
    }
  }

  int m;
  int n;
  public int cutOffTree(List<List<Integer>> forest) {
    this.m = forest.size();
    this.n = forest.get(0).size();
    int[][] matrix = new int[m][n];
    TreeMap<Integer, int[]> map = new TreeMap<>();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        matrix[i][j] = forest.get(i).get(j);
        if (matrix[i][j] > 1) map.put(matrix[i][j], new int[]{i, j});
      }
    }
    int x = 0;
    int y = 0;
    int res = 0;
    for (int t : map.keySet()) {
      int[] pos = map.get(t);
      boolean[][] visited = new boolean[m][n];
      int step = bfs(x, y, pos[0], pos[1], matrix, visited);
      if (step == -1) return -1;
      res += step;
      matrix[x][y] = 1;
      x = pos[0];
      y = pos[1];
    }
    return res;
  }

  private int bfs(int r, int c, int x, int y, int[][] matrix, boolean[][] visited) {
    Queue<int[]> queue = new ArrayDeque<>();
    queue.offer(new int[]{r, c});
    visited[r][c] = true;
    int step = 0;
    while (!queue.isEmpty()) {
      int size = queue.size();
      for (int i = 0; i < size; ++i) {
        int[] cur = queue.poll();
        if (cur[0] == x && cur[1] == y) {
          return step;
        }
        for (int[] dir : DIRS) {
          int nr = dir[0] + cur[0];
          int nc = dir[1] + cur[1];
          if (0 <= nr && nr < m && 0 <= nc && nc < n && matrix[nr][nc] > 0 && !visited[nr][nc]) {
            visited[nr][nc] = true;
            queue.offer(new int[]{nr, nc});
          }
        }
      }
      step++;
    }
    return -1;
  }

  public int[][] updateMatrix(int[][] mat) {
    int m = mat.length;
    int n = mat[0].length;
    Queue<int[]> q = new LinkedList<>();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (mat[i][j] == 0) q.offer(new int[]{i, j});
        else mat[i][j] = -1;
      }
    }

    while (!q.isEmpty()) {
      int[] cur = q.poll();
      for (int[] dir : DIRS) {
        int r = cur[0] + dir[0];
        int c = cur[1] + dir[1];
        if (r < 0 || c < 0 || r >= m || c >= n || mat[r][c] != -1) continue;
        mat[r][c] = mat[cur[0]][cur[1]] + 1;
        q.offer(new int[]{r, c});
      }
    }
    return mat;
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
            if (tmp == ch) continue;
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





  char visited = '*';

  public boolean exist(char[][] board, String word) {
    if (board == null || board.length == 0) return false;
    this.m = board.length;
    this.n = board[0].length;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (exist(board, i, j, word, 0)) return true;
      }
    }
    return false;
  }

  private boolean exist(char[][] board, int r, int c, String s, int index) {
    if (index == s.length()) return true;
    if (r < 0 || c < 0 || r >= m || c >= n || board[r][c] == visited || board[r][c] != s.charAt(index)) return false;
    char tmp = board[r][c];
    board[r][c] = visited;
    for (int[] dir : DIRS) {
      if (exist(board, r + dir[0], c + dir[1], s, index + 1)) return true;
    }
    board[r][c] = tmp;
    return false;
  }


  public void solveSudoku(char[][] board) {
    if (board == null || board.length != 9 || board[0].length != 9) return;
    int[] rows = new int[9];
    int[] cols = new int[9];
    int[] boxes = new int[9];
    List<int[]> blanks = new ArrayList<>();


    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        char c = board[i][j];
        if (c == '.') {
          blanks.add(new int[]{i, j});
          continue;
        }
        int b = 3 * (i / 3) + (j / 3);
        int mask = 1 << (c - '1');
        rows[i] |= mask;
        cols[j] |= mask;
        boxes[b] |= mask;
      }
    }
    solveSudokuHelper(board, rows, cols, boxes, blanks, 0);
  }

  private boolean solveSudokuHelper(char[][] board, int[] rows, int[] cols, int[] boxes, List<int[]> blanks,
                                    int idx) {
    if (idx == blanks.size()) {
      return true;
    }

    int[] cell = blanks.get(idx);
    int i = cell[0];
    int j = cell[1];
    int b = 3 * (i / 3) + (j / 3);

    for (char c = '1'; c <= '9'; c++) {
      int mask = 1 << (c - '1');

      // Check if the number already used in row, column and sub-box.
      if (((rows[i] & mask) != 0) || ((cols[j] & mask) != 0) || ((boxes[b] & mask) != 0)) {
        continue;
      }

      // Add the cell to rows, cols and boxes.
      rows[i] |= mask;
      cols[j] |= mask;
      boxes[b] |= mask;
      board[i][j] = c;

      if (solveSudokuHelper(board, rows, cols, boxes, blanks, idx + 1)) {
        return true;
      }

      // Backtrack
      // Remove the cell to rows, cols and boxes.
      rows[i] &= ~mask;
      cols[j] &= ~mask;
      boxes[b] &= ~mask;
    }

    return false;
  }


  public List<String> generateParenthesis(int n) {
    List<String> res = new ArrayList<>();
    if (n <= 0) return res;
    StringBuilder prefix = new StringBuilder();
    generate(0, 0, n, prefix, res);
    return res;
  }

  private void generate(int l, int r, int n, StringBuilder prefix, List<String> res) {
    if (l == n && r == n) {
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


  public List<String> letterCasePermutation(String s) {
    List<String> res = new ArrayList<>();
    if (s == null || s.length() == 0) return res;
    char[] array = s.toCharArray();
    lCP(array, 0, res);
    return res;
  }

  private void lCP(char[] array, int index, List<String> res) {
    if (index == array.length) {
      res.add(new String(array));
      return;
    }
    if (Character.isDigit(array[index])) {
      lCP(array, index + 1, res);
    } else {
      char ch = Character.toLowerCase(array[index]);
      array[index] = Character.toLowerCase(ch);
      lCP(array, index + 1, res);
      array[index] = Character.toUpperCase(ch);
      lCP(array, index + 1, res);
    }
  }

  public List<List<Integer>> permuteUnique(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    if (nums == null || nums.length == 0) return res;
    permuteUnique(nums, 0, res);
    return res;
  }

  private void permuteUnique(int[] nums, int index, List<List<Integer>> res) {
    if (index == nums.length) {
      List<Integer> cur = new ArrayList<>();
      for (int num : nums) cur.add(num);
      res.add(cur);
      return;
    }
    Set<Integer> set = new HashSet<>();
    for (int i = index; i < nums.length; i++) {
      if (set.contains(nums[i])) continue;
      set.add(nums[i]);
      swap(nums, i, index);
      permuteUnique(nums, index + 1, res);
      swap(nums, i, index);
    }

  }

  private void swap(int[] nums, int i, int j) {
    int tmp = nums[i];
    nums[i] = nums[j];
    nums[j] = tmp;
  }

  public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    if (nums == null || nums.length == 0) return res;
    permute(nums, 0, res);
    return res;
  }

  private void permute(int[] nums, int index, List<List<Integer>> res) {
    if (index == nums.length) {
      List<Integer> cur = new ArrayList<>();
      for (int num : nums) cur.add(num);
      res.add(cur);
      return;
    }
    for (int i = index; i < nums.length; i++) {
      swap(nums, i, index);
      permute(nums, index + 1, res);
      swap(nums, i, index);
    }
  }


  public List<List<Integer>> combinationSum3(int k, int n) {
    List<List<Integer>> res = new ArrayList<>();
    if (k <= 0 || n == 0) return res;
    combinationsSum3(k, n, 9, new ArrayList<>(), res);
    return res;
  }

  private void combinationsSum3(int k, int n, int num, List<Integer> cur, List<List<Integer>> res) {
    if (n <= 0) {
      if (k == 0 && n == 0) res.add(new ArrayList<>(cur));
      return;
    }

    for (int i = num; i >= 1; i--) {
      cur.add(i);
      combinationsSum3(k - 1, n - i, i - 1, cur, res);
      cur.remove(cur.size() - 1);
    }
  }

  public List<List<Integer>> subsetsWithDup(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    if (nums == null || nums.length == 0) return res;
    Arrays.sort(nums);
    subsetWithDup(nums, 0, new ArrayList<>(), res);
    return res;
  }

  private void subsetWithDup(int[] nums, int index, List<Integer> cur, List<List<Integer>> res) {
    if (index == nums.length) {
      res.add(new ArrayList<>(cur));
      return;
    }
    cur.add(nums[index]);
    subsetWithDup(nums, index + 1, cur, res);
    cur.remove(cur.size() - 1);
    while (index < nums.length - 1 && nums[index] == nums[index + 1]) index++;
    subsetWithDup(nums, index + 1, cur, res);
  }


  public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    if (nums == null || nums.length == 0) return res;
    subsets(nums, 0, new ArrayList<>(), res);
    return res;
  }

  private void subsets(int[] nums, int index, List<Integer> cur, List<List<Integer>> res) {
    if (index == nums.length) {
      res.add(new ArrayList<>(cur));
      return;
    }
    cur.add(nums[index]);
    subsets(nums, index + 1, cur, res);
    cur.remove(cur.size() - 1);
    subsets(nums, index + 1, cur, res);
  }

  public List<List<Integer>> combine(int n, int k) {
    List<List<Integer>> res = new ArrayList<>();
    if (n <= 0 || k < 0) return res;
    comb(n, k, new ArrayList<>(), res);
    return res;
  }

  private void comb(int n, int k, List<Integer> cur, List<List<Integer>> res) {
    if (k == 0) {
      res.add(new ArrayList<>(cur));
      return;
    }
    for (int i = n; i >= 1; i--) {
      cur.add(i);
      comb(i - 1, k - 1, cur, res);
      cur.remove(cur.size() - 1);
    }
  }

  /*
  1


  n-1

   */

  public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    List<List<Integer>> res = new ArrayList<>();
    if (candidates == null || candidates.length == 0 || target == 0) return res;
    Arrays.sort(candidates);
    List<Integer> cur = new ArrayList<>();
    comb2(candidates, 0, target, cur, res);
    return res;
  }

  private void comb2(int[] candidates, int index, int target, List<Integer> cur, List<List<Integer>> res) {
    if (target <= 0) {
      if (target == 0) {
        res.add(new ArrayList<>(cur));
      }
      return;
    }
    for (int i = index; i < candidates.length; i++) {
      if (i > index && candidates[i] == candidates[i - 1]) continue;
      cur.add(candidates[i]);
      comb2(candidates, i + 1, target - candidates[i], cur, res);
      cur.remove(cur.size() - 1);
    }

  }


  public List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<List<Integer>> res = new ArrayList<>();
    // corner case
    if (candidates == null || candidates.length == 0 || target == 0) return res;
    Arrays.sort(candidates);
    combination(candidates, 0, target, new ArrayList<>(), res);
    return res;
  }

  private void combination(int[] candidates, int index, int target, List<Integer> cur, List<List<Integer>> res) {
    if (target == 0) {
      res.add(new ArrayList<>(cur));
      return;
    }
    for (int i = index; i < candidates.length; i++) {
      if (candidates[i] > target) {
        break;
      }
      cur.add(candidates[i]);
      combination(candidates, i, target - candidates[i], cur, res);
      cur.remove(cur.size() - 1);
    }

  }


  /*
   2   3
  abc  def
  => find all combinations of 23
  ad ae af bd be bf
              ""
      2      /  |   |
           a    b    c
          /|\
      3 d e f    .... .

    level : how many digits I have in my input
    state: 4 states
    n: input's length

  TC: O(4^n)
  SC: O(N)
   */
  public List<String> letterCombinations(String digits) {
    List<String> res = new ArrayList<>();
    if (digits == null || digits.length() == 0) return res;
    Map<Character, String> map = new HashMap<>();
    // build the map
    map.put('2', "abc");
    map.put('3', "def");
    map.put('4', "ghi");
    map.put('5', "jkl");
    map.put('6', "mno");
    map.put('7', "pqrs");
    map.put('8', "tuv");
    map.put('9', "wxyz");

    StringBuilder prefix = new StringBuilder();
    dfs(digits, 0, prefix, res, map);
    return res;
  }

  private void dfs(String digits, int index, StringBuilder prefix, List<String> res, Map<Character, String> map) {
    if (index == digits.length()) {
      res.add(prefix.toString());
      return;
    }

    for (char ch : map.get(digits.charAt(index)).toCharArray()) {
      prefix.append(ch);
      dfs(digits, index + 1, prefix, res, map);
      prefix.deleteCharAt(prefix.length() - 1);
    }
  }


}
