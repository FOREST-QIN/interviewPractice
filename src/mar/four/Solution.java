package mar.four;

import org.junit.Test;

import java.util.*;

public class Solution {

  @Test
  public void test() {
    String str = "3[a]2[bc]";
    String res = decodeString(str);
  }

  public List<String> generateParenthesis(int n) {
    List<String> res = new ArrayList<>();
    if (n == 0) return res;
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
      prefix.append('(');
      generate(l + 1, r, n, prefix, res);
      prefix.deleteCharAt(prefix.length() - 1);
    }

    if (r < l) {
      prefix.append(')');
      generate(l, r + 1, n, prefix, res);
      prefix.deleteCharAt(prefix.length() - 1);
    }

  }


  public String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    StringBuilder prefix = new StringBuilder();
    prefix.append(strs[0]);
    for (int i = 0; i < strs.length; i++) {
      while (!strs[i].startsWith(prefix.toString())) {
        prefix.deleteCharAt(prefix.length() - 1);
      }
    }
    return prefix.toString();
  }

  public int calculate(String s) {
    if (s == null || s.length() == 0) return 0;
    int num = 0;
    int tmp = 0;
    int res = 0;
    char op = '+';
    for (char ch : s.toCharArray()) {
      if (Character.isDigit(ch)) {
        tmp = tmp * 10 + ch - '0';
      } else if (ch != ' ') {
        num = cal(num, tmp, op);
        if (ch == '+' || ch == '-') {
          res += num;
          num = 0;
        }
        tmp = 0;
        op = ch;
      }
    }
    return res + cal(num, tmp, op);
  }

  private int cal(int num, int tmp, char op) {
    if (op == '+') return num + tmp;
    if (op == '-') return num - tmp;
    if (op == '/') return num / tmp;
    return num * tmp;
  }


  int index = 0;
  public String decodeString(String s) {
    int count = 0;
    StringBuilder prefix = new StringBuilder();
    while (index < s.length()) {
      char ch = s.charAt(index);
      if (Character.isDigit(ch)) {
        count = count * 10 + ch - '0';
        index++;
      } else if (ch == '[') {
        index++;
        prefix.append(decodeString(s).repeat(count));
        count = 0;
      } else if (ch == ']') {
        index++;
        break;
      } else {
        prefix.append(ch);
        index++;
      }
    }

    return prefix.toString();
  }


  int i = 0;
  int j = 0;
  int max = 0;
  public String longestPalindrome(String s) {
    for (int p = 0; p < s.length(); p++) {
      longest(s, p, p);
      longest(s, p, p + 1);
    }
    return s.substring(i, j + 1);
  }

  private void longest(String s, int l, int r) {
    while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
      l--;
      r++;
    }
    if (r - l - 1 > max) {
      max = r - l - 1;
      i = l + 1;
      j = r - 1;
    }
  }

  public boolean validPalindrome(String s) {
    return isValid(s, 0, s.length() - 1, false);
  }

  private boolean isValid(String s, int i, int j, boolean deleted) {
    while (i <= j) {
      if (s.charAt(i) != s.charAt(j)) {
        if (deleted) return false;
        return isValid(s, i, j - 1, true) || isValid(s, i + 1, j, true);
      }
      i++;
      j--;
    }
    return true;
  }

  public int lengthOfLongestSubstring(String s) {
    if (s == null || s.length() == 0) return 0;
    int[] count = new int[128];
    int i = 0;
    int j = 0;
    int res = 0;
    while (j < s.length()) {
      char ch = s.charAt(j);
      j++;
      count[ch]++;

      while (count[ch] > 1) {
        char left = s.charAt(i);
        i++;
        count[left]--;
      }
      res = Math.max(res, j - i);
    }
    return res;
  }


  public List<String> fullJustify(String[] words, int maxWidth) {
    List<String> res = new ArrayList<>();
    if (words == null || words.length == 0) return res;
    int index = 0;
    while (index < words.length) {
      int last = index + 1;
      int count = words[index].length();
      while (last < words.length) {
        if (words[last].length() + count + 1 > maxWidth) break;
        count += words[last].length() + 1;
        last++;
      }
      StringBuilder prefix = new StringBuilder();
      prefix.append(words[index]);
      int diff = last - index - 1;
      if (last == words.length || diff == 0) {
        for (int i = index + 1; i < last; i++) {
          prefix.append(" ");
          prefix.append(words[i]);
        }
        for (int i = prefix.length(); i < maxWidth; i++) {
          prefix.append(" ");
        }
      } else {
        int space = (maxWidth - count) / diff;
        int r = (maxWidth - count) % diff;
        for (int i = index + 1; i < last; i++) {
          for (int k = 0; k < space; k++) {
            prefix.append(" ");
          }
          if (r > 0) {
            prefix.append(" ");
            r--;
          }
          prefix.append(" ");
          prefix.append(words[i]);
        }
      }
      res.add(prefix.toString());
      index = last;
    }
    return res;
  }

  public boolean isValid(String s) {
    if (s == null || s.length() == 0) return true;
    Deque<Character> stack = new ArrayDeque<>();
    Map<Character, Character> map = getMap();
    for (char ch : s.toCharArray()) {
      if (!map.containsKey(ch)) {
        stack.offerFirst(ch);
      } else {
        if (stack.isEmpty() || stack.peekFirst() != map.get(ch)) return false;
        stack.pollFirst();
      }
    }
    return stack.isEmpty();
  }

  private Map<Character, Character> getMap() {
    Map<Character, Character> map = new HashMap<>();
    map.put(')', '(');
    map.put(']', '[');
    map.put('}', '{');
    return map;
  }

  public String minRemoveToMakeValid(String s) {
    if (s == null || s.length() == 0) return s;
    StringBuilder removeRight = new StringBuilder();
    int left = 0;
    int unmatched = 0;
    for (char ch : s.toCharArray()) {
      if (ch == '(') {
        left++;
        unmatched++;
      } else if (ch == ')') {
        if (unmatched > 0) {
          unmatched--;
        } else {
          continue;
        }
      }
      removeRight.append(ch);
    }
    int validLeft = left - unmatched;
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < removeRight.length(); i++) {
      char ch = removeRight.charAt(i);
      if (ch == '(') {
        if (validLeft > 0) {
          validLeft--;
        } else {
          continue;
        }
      }
      res.append(ch);
    }
    return res.toString();
  }


  public String originalDigits(String s) {
    int[] count = new int[26];
    for (int i = 0; i < s.length(); i++) {
      count[s.charAt(i) - 'a']++;
    }
    int[] num = new int[10];
    num[0] = count['z' - 'a'];
    num[2] = count['w' - 'a'];
    num[4] = count['u' - 'a'];
    num[6] = count['x' - 'a'];
    num[8] = count['g' - 'a'];
    num[3] = count['h' - 'a'] - num[8];
    num[5] = count['f' - 'a'] - num[4];
    num[7] = count['s' - 'a'] - num[6];
    num[9] = count['i' - 'a'] - num[5] - num[6] - num[8];
    num[1] = count['n' - 'a'] - num[7] - 2 * num[9];
    StringBuilder prefix = new StringBuilder();
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < num[i]; j++) {
        prefix.append(i);
      }
    }
    return prefix.toString();
  }

  public int kthFactor(int n, int k) {
    if (k == 1) return 1;
    int count = 1;

    for (int factor = 2; factor <= n; factor++) {
      if (n % factor == 0) {
        count++;
      }
      if (count == k) return factor;
    }
    return -1;
  }

  public int countBinarySubstrings(String s) {
    int cur = 1;
    int pre = 0;
    int res = 0;
    for (int i = 1; i < s.length(); i++) {
      if (s.charAt(i) == s.charAt(i - 1)) cur++;
      else {
        res += Math.min(cur, pre);
        pre = cur;
        cur = 1;
      }
    }
    return res + Math.min(cur, pre);
  }

  public List<List<String>> suggestedProducts(String[] products, String searchWord) {
    List<List<String>> res = new ArrayList<>();
    Arrays.sort(products);
    for (int i = 1; i <= searchWord.length(); i++) {
      List<String> cur = new ArrayList<>();
      String sub = searchWord.substring(0, i);
      for (String product : products) {
        if (product.startsWith(sub)) cur.add(product);
        if (cur.size() >= 3) break;
      }
      res.add(cur);
    }
    return res;
  }

  public int minFlipsMonoIncr(String s) {
    int m = s.length();
    int zero = 0;
    int oneOrZero = 0;
    for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      if (ch == '0') {
        oneOrZero = Math.min(oneOrZero + 1, zero);
      } else {
        zero++;
      }
    }
    return Math.min(zero, oneOrZero);
  }


  public String[] reorderLogFiles(String[] logs) {
    if (logs == null || logs.length == 0) return logs;
    Arrays.sort(logs, (a, b) -> {
      String[] aArr = a.split(" ", 2);
      String[] bArr = b.split(" ", 2);

      if (Character.isDigit(aArr[1].charAt(0)) && Character.isDigit(bArr[1].charAt(0))) {
        return 0;
      } else if (Character.isDigit(aArr[1].charAt(0)) && Character.isAlphabetic(bArr[1].charAt(0))) {
        return 1;
      } else if (Character.isAlphabetic(aArr[1].charAt(0)) && Character.isDigit(bArr[1].charAt(0))) {
        return -1;
      } else {
        if (aArr[1].equals(bArr[1])) return aArr[0].compareTo(bArr[0]);
        return aArr[1].compareTo(bArr[1]);
      }
    });
    return logs;
  }

  public int uniqueLetterString(String s) {
    int[][] index = new int[26][2];
    for (int i = 0; i < 26; i++) Arrays.fill(index[i], -1);
    int res = 0;
    int m = s.length();
    int mod = (int) Math.pow(10, 9) + 7;
    for (int i = 0; i < m; i++) {
      int ch = s.charAt(i) - 'A';
      res = (res + (i - index[ch][1]) * (index[ch][1] - index[ch][0]) % mod) % mod;
      index[ch] = new int[]{index[ch][1], i};
    }
    for (int ch = 0; ch < 26; ch++) {
      res = (res + (m - index[ch][1]) * (index[ch][1] - index[ch][0]) % mod) % mod;
    }
    return res;
  }


}
