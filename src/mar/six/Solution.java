package mar.six;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    String[] words = {"xbc","pcxbcf","xb","cxbc","pcxbc"};
    int res = longestStrChain(words);
  }

  public boolean uniqueOccurrences(int[] arr) {
    if (arr == null || arr.length == 0) return true;
    Map<Integer, Integer> count = new HashMap<>();
    for (int num : arr) {
      count.put(num, count.getOrDefault(num, 0) + 1);
    }
    Set<Integer> set = new HashSet<>();
    for (int freq : count.values()) {
      if (!set.add(freq)) return false;
    }
    return true;
  }

  public int findShortestSubArray(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int res = 0;
    int degree = 0;
    Map<Integer, Integer> count = new HashMap<>();
    Map<Integer, Integer> first = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      first.putIfAbsent(nums[i], i);
      count.put(nums[i], count.getOrDefault(nums[i], 0) + 1);
      if (count.get(nums[i]) > degree) {
        degree = count.get(nums[i]);
        res = i - first.get(nums[i]) + 1;
      } else if (count.get(nums[i]) == degree) {
        res = Math.min(res, i - first.get(nums[i]) + 1);
      }
    }
    return res;
  }


  public List<String> alertNames(String[] keyName, String[] keyTime) {
    Map<String, TreeSet<Integer>> name2Time = new HashMap<>();
    int m = keyName.length;
    for (int i = 0; i < m; i++) {
      int time = timeToMin(keyTime[i]);
      name2Time.computeIfAbsent(keyName[i], k -> new TreeSet<>()).add(time);
    }
    TreeSet<String> names = new TreeSet<>();
    for (Map.Entry<String, TreeSet<Integer>> entry : name2Time.entrySet()) {
      Deque<Integer> dq = new ArrayDeque<>();
      for (int time : new ArrayList<>(entry.getValue())) {
        dq.offerFirst(time);
        if (dq.peekFirst() - dq.peekLast() > 60) {
          dq.pollLast();
        }
        if (dq.size() >= 3) {
          names.add(entry.getKey());
          break;
        }
      }
    }
    return new ArrayList<>(names);
  }

  private int timeToMin(String time) {
    String[] arr = time.split(":");
    int hour = Integer.parseInt(arr[0]);
    int min = Integer.parseInt(arr[1]);
    return hour * 60 + min;
  }


  public int longestStrChain(String[] words) {
    /*
    sort by words.length;
    int[] dp
    dp[i] represents the longest chain length ends at words[i]

    for any word: try every pre-word, if is predecessor, dp[i] = Math.max(dp[j] + 1, dp[i]);
     */
    Map<Integer, Set<String>> lengthToString = new HashMap<>();
    for (String word : words) {
      lengthToString.computeIfAbsent(word.length(), k -> new HashSet<>()).add(word);
    }
    Map<String, List<String>> preds = new HashMap<>();
    for (String word : words) {
      if (preds.containsKey(word)) continue;
      if (!lengthToString.containsKey(word.length() - 1)) continue;
      preds.put(word, new ArrayList<>());
      for (String tmp : lengthToString.get(word.length() - 1)) {
        if (isPred(tmp, word)) preds.get(word).add(tmp);
      }
    }

    int m = words.length;
    int max = 1;
    Arrays.sort(words, (a, b) -> Integer.compare(a.length(), b.length()));
    int[] dp = new int[m];
    Map<String, Integer> word2Index = new HashMap<>();
    for (int i = 0; i < words.length; i++) word2Index.put(words[i], i);

    for (int i = 0; i < m; i++) {
      String cur = words[i];
      dp[i] = 1;
      if (!preds.containsKey(cur)) {
        continue;
      }

      for (String pred : preds.get(cur)) {
        dp[i] = Math.max(dp[word2Index.get(pred)] + 1, dp[i]);
      }
      max = Math.max(max, dp[i]);
    }
    return max;
  }

  private boolean isPred(String one, String two) {
    boolean diff = false;
    int i = 0;
    int j = 0;
    while (i < one.length() && j < two.length()) {
      if (one.charAt(i) != two.charAt(j)) {
        if (diff) return false;
        diff = true;
      } else {
        i++;
      }
      j++;
    }
    return true;
  }

  public String removeDuplicates(String s, int k) {
    int i = 0;
    int m = s.length();
    int[] count = new int[m];
    char[] stack = s.toCharArray();
    for (int j = 0; j < m; i++, j++) {
      stack[i] = stack[j];
      count[i] = i > 0 && stack[i - 1] == stack[i] ? count[i - 1] + 1 : 1;
      if (count[i] == k) i -= k;
    }
    return new String(stack, 0, i);
  }

}
