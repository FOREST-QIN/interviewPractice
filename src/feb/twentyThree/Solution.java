package feb.twentyThree;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    int[] arr = {1, 2, 1, 2, 3};
    int res = subarraysWithKDistinct(arr, 2);
  }

  int index = 0;
  public int calculate(String s) {
    if (s == null || s.length() == 0) return 0;
    int tmp = 0;
    int sign = 1;
    int res = 0;
    while (index < s.length()) {
      char ch = s.charAt(index++);
      if (Character.isDigit(ch)) {
        tmp = tmp * 10 + ch - '0';
      } else if (ch == '(') {
        tmp = calculate(s);
      } else if (ch == ')') {
        break;
      } else if (ch == '+' || ch == '-') {
        res += sign * tmp;
        tmp = 0;
        sign = (ch == '+') ? 1 : -1;
      }
    }
    res += sign * tmp;
    return res;
  }

  private int cal(int num, int tmp, char op) {
    if (op == '+') return num + tmp;
    if (op == '-') return num - tmp;
    if (op == '*') return num * tmp;
    return num / tmp;
  }

  public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    Map<Integer, Map<Integer, Integer>> prices = new HashMap<>();
    for (int[] f : flights) {
      prices.computeIfAbsent(f[0], key -> new HashMap<>()).put(f[1], f[2]);
    }
//    Set<Integer> visited = new HashSet<>();
    PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
    minHeap.offer(new int[]{0, src, 0});
    int[] visited = new int[n];
    Arrays.fill(visited, Integer.MAX_VALUE);
    while (!minHeap.isEmpty()) {
      int[] cur = minHeap.poll();
      int price = cur[0];
      int city = cur[1];
      int stop = cur[2];
      visited[city] = stop;
      if (city == dst) return price;
      if (stop <= k) {
        Map<Integer, Integer> nei = prices.getOrDefault(city, new HashMap<>());
        for (int a : nei.keySet()) {
          if (visited[a] <= stop) continue;
          minHeap.offer(new int[]{price + nei.get(a), a, stop + 1});
        }
      }
    }
    return -1;
  }

  public List<List<String>> accountsMerge(List<List<String>> accounts) {
    List<List<String>> res = new ArrayList<>();
    if (accounts.size() == 0) return res;
    Map<String, Set<String>> graph = new HashMap<>();
    Map<String, String> mailToName = new HashMap<>();
    buildGraph(accounts, graph, mailToName);

    Set<String> visited = new HashSet<>();
    for (String mail : mailToName.keySet()) {
      String name = mailToName.get(mail);
      List<String> list = new ArrayList<>();
      if (visited.add(mail)) {
        dfs(list, mail, graph, visited);
        Collections.sort(list);
        list.add(0, name);
        res.add(list);
      }
    }
    return res;
  }

  private void dfs(List<String> mails, String mail, Map<String, Set<String>> graph, Set<String> visited) {
    mails.add(mail);
    for (String nei : graph.get(mail)) {
      if (visited.add(nei)) {
        dfs(mails, nei, graph, visited);
      }
    }
  }

  private void buildGraph(List<List<String>> accounts, Map<String, Set<String>> graph, Map<String, String> mailToName) {
    for (List<String> list : accounts) {
      String name = list.get(0);
      for (int i = 1; i < list.size(); i++) {
        String mail = list.get(i);
        mailToName.put(mail, name);
        graph.computeIfAbsent(mail, k -> new HashSet<>());
        if (i == 1) continue;
        String prev = list.get(i - 1);
        graph.get(prev).add(mail);
        graph.get(mail).add(prev);
      }
    }
  }


  Map<Integer, Integer> closePos = new HashMap<>();

  public String decodeString(String s) {
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '[') {
        stack.offerFirst(i);
      } else if (s.charAt(i) == ']') {
        closePos.put(stack.pollFirst(), i);
      }
    }
    return solve(s, 0, s.length() - 1);
  }

  private String solve(String s, int l, int r) {
    StringBuilder prefix = new StringBuilder();
    int num = 0;
    while (l <= r) {
      char ch = s.charAt(l);
      if (Character.isDigit(ch)) {
        num = num * 10 + ch - '0';
      } else if (ch == '[') {
        prefix.append(solve(s, l + 1, closePos.get(l) - 1).repeat(num));
        l = closePos.get(l);
        num = 0;
      } else {
        prefix.append(ch);
      }
      l++;
    }
    return prefix.toString();
  }


  public int scoreOfParentheses(String s) {
    if (s == null || s.length() == 0) return 0;
    /*
    [
    every time I found (, I put it into my stack
    if I found )
    maintain a base
    if ), base + one
    if (
            int cur
                cur = 4
              (()(()()))
                       i
                ()
               / \
             ()  ()
                 /\
               ()  ()
       [ 0,1,1

     */
    Deque<Integer> stack = new ArrayDeque<>();
    int cur = 0;
    for (char ch : s.toCharArray()) {
      if (ch == '(') {
        stack.offerFirst(cur);
        cur = 0;
      } else {
        cur = stack.pollFirst() + Math.max(cur * 2, 1);
      }
    }
    return cur;
  }


  public int findMin(int[] nums) {
    if (nums == null || nums.length == 0) return -1;
    int m = nums.length;
    int l = 0;
    int r = m - 1;
    if (nums[l] < nums[r]) return nums[l];
    while (l < r) {
      int mid = l + (r - l) / 2;
      if (nums[mid] > nums[r]) {
        l = mid + 1;
      } else if (nums[mid] < nums[r]) {
        r = mid;
      } else {
        r--;
      }
    }
    return nums[l];
  }


  public List<Integer> majorityElement(int[] nums) {
    List<Integer> res = new ArrayList<>();
    if (nums == null || nums.length == 0) return res;
    int first = Integer.MAX_VALUE;
    int firstCount = 0;
    int second = Integer.MAX_VALUE;
    int secondCount = 0;

    for (int num : nums) {
      if (num == first) {
        firstCount++;
      } else if (num == second) {
        secondCount++;
      } else if (firstCount == 0) {
        first = num;
        firstCount = 1;
      } else if (secondCount == 0) {
        second = num;
        secondCount = 1;
      } else {
        secondCount--;
        firstCount--;
      }
    }

    firstCount = 0;
    secondCount = 0;
    for (int num : nums) {
      if (num == first) firstCount++;
      else if (num == second) secondCount++;
    }
    if (firstCount > nums.length / 3) res.add(first);
    if (secondCount > nums.length / 3) res.add(second);
    return res;
  }


  public int majorityElementI(int[] nums) {
    int tmp = nums[0];
    int count = 1;
    for (int i = 1; i < nums.length; i++) {
      if (nums[i] == tmp) {
        count++;
      } else {
        if (count > 0) {
          count--;
        } else {
          count = 1;
          tmp = nums[i];
        }
      }
    }
    return tmp;
  }


  public int subarraysWithKDistinct(int[] nums, int k) {
    return atMost(nums, k) - atMost(nums, k - 1);
  }

  private int atMost(int[] nums, int k) {
    int i = 0;
    int res = 0;
    Map<Integer, Integer> count = new HashMap<>();
    for (int j = 0; j < nums.length; j++) {
      if (count.getOrDefault(nums[j], 0) == 0) k--;
      count.put(nums[j], count.getOrDefault(nums[j], 0) + 1);
      while (k < 0) {
        count.put(nums[i], count.get(nums[i]) - 1);
        if (count.get(nums[i]) == 0) k++;
        i++;
      }
      res += j - i + 1;
    }
    return res;
  }


  public int[] sortedSquares(int[] nums) {
    int m = nums.length;
    int[] res = new int[m];
    int left = 0;
    int right = m - 1;
    for (int i = m - 1; i >= 0; i--) {
      int square;
      if (Math.abs(nums[left]) > Math.abs(nums[right])) {
        square = nums[left];
        left++;
      } else {
        square = nums[right];
        right--;
      }
      res[i] = square * square;
    }
    return res;
  }

  /*
   -1, 2, 1, -4
   step1: sort
   -4, -1, 1, 2
    i      j  k
  -4 + nums[j] + nums[k] = 1
      nums[j] + nums[k] = 5

    res

   */
  public int threeSumClosest(int[] nums, int target) {
    Arrays.sort(nums);
    int m = nums.length;
    int res = nums[0] + nums[1] + nums[m - 1];
    for (int i = 0; i + 2 < m; i++) {
      int j = i + 1;
      int k = m - 1;
      while (j < k) {
        int sum = nums[i] + nums[j] + nums[k];
        if (sum > target) {
          k--;
          while (j < k && nums[k] == nums[k + 1]) k--;
        } else if (sum < target) {
          j++;
          while (j < k && nums[j] == nums[j - 1]) j++;
        } else {
          return sum;
        }
        if (Math.abs(sum - target) < Math.abs(res - target)) {
          res = sum;
        }
      }
    }
    return res;
  }


  /*
  [-1,0,1,2,-1,-4]
  step 1: sort int ascending order
  -4, -1, -1, 0, 1, 2
               i
  i pointer
    find 2 sum are equals to -nums[i]

   nums[i] = -4
   nums[j] + nums[k] = 4;

   */
  public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    // O(n^2 + nlogn)
    // O(1)
    if (nums == null || nums.length == 0) return res;
    int m = nums.length;
    // sort : O(nlgn)
    Arrays.sort(nums);
    // O(n)
    for (int i = 0; i + 2 < m; i++) {
      if (nums[i] > 0) break;
      // O(n)
      if (i > 0 && nums[i] == nums[i - 1]) continue;
      int j = i + 1;
      int k = m - 1;
      int target = -nums[i];
      while (j < k) {
        int sum = nums[j] + nums[k];
        if (sum == target) {
          res.add(Arrays.asList(nums[i], nums[j], nums[k]));
          j++;
          k--;
          while (j < k && nums[j] == nums[j - 1]) j++;
          while (j < k && nums[k] == nums[k + 1]) k--;
        } else if (sum > target) {
          k--;
        } else {
          j++;
        }
      }
    }
    return res;
  }


  public int[] twoSum(int[] numbers, int target) {
    if (numbers == null || numbers.length == 0) return new int[]{};
    int i = 0;
    int j = numbers.length - 1;
    while (i < j) {
      int sum = numbers[i] + numbers[j];
      if (sum == target) {
        return new int[]{i + 1, j + 1};
      } else if (sum > target) {
        j--;
      } else {
        i++;
      }
    }
    return new int[]{};
  }
}
