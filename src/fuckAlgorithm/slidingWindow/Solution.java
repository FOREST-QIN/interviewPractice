package fuckAlgorithm.slidingWindow;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Solution {

  @Test
  public void test() {
    String res = minWindow("aa", "aa");

  }

  public int lengthOfLongestSubstring(String s) {
    int[] window = new int[256];
    int left = 0, right = 0, res = 0;
    while (right < s.length()) {
      char ch = s.charAt(right);
      window[ch]++;
      right++;
      while (window[ch] > 1) {
        char d = s.charAt(left);
        window[d]--;
        left++;
      }
      res = Math.max(res, right - left);
    }
    return res;
  }


  public List<Integer> findAnagrams(String s, String p) {
    List<Integer> res = new ArrayList<>();
    int[] need = new int[26];
    int[] window = new int[26];
    int left = 0, right = 0, valid = 0;

    for (char ch : p.toCharArray()) {
      if (need[ch - 'a']++ == 0) valid++;
    }

    while (right < s.length()) {
      char ch = s.charAt(right);
      right++;
      if (need[ch - 'a'] != 0) {
        window[ch - 'a']++;
        if (window[ch - 'a'] == need[ch - 'a']) valid--;
      }

      while (right - left >= p.length()) {
        if (valid == 0) res.add(left);
        char d = s.charAt(left);
        left++;
        if (need[d - 'a'] != 0) {
          if (need[d - 'a'] == window[d - 'a']) valid++;
          window[d - 'a']--;
        }
      }
    }

    return res;
  }


  public boolean checkInclusion(String t, String s) {
    int[] need = new int[26];
    int[] window = new int[26];
    int left = 0, right = 0, valid = 0;

    for (char ch : t.toCharArray()) {
      if (need[ch - 'a']++ == 0) valid++;
    }

    while (right < s.length()) {
      char ch = s.charAt(right);
      right++;
      if (need[ch - 'a'] != 0) {
        window[ch - 'a']++;
        if (need[ch - 'a'] == window[ch - 'a']) valid--;
      }

      while (right - left >= t.length()) {
        if (valid == 0) return true;
        char d = s.charAt(left);
        left++;
        if (need[d - 'a'] != 0) {
          if (window[d - 'a'] == need[d - 'a']) valid++;
          window[d - 'a']--;
        }
      }
    }
    return false;
  }


  public String minWindow(String s, String t) {
    int[] need = new int[256];
    int[] window = new int[256];
    int left = 0, right = 0, valid = 0, start = 0, len = Integer.MAX_VALUE;

    for (char ch : t.toCharArray()) {
      if (need[ch] == 0) valid++;
      need[ch]++;
    }

    while (right < s.length()) {
      char ch = s.charAt(right);
      if (need[ch] != 0) {
        window[ch]++;
        if (window[ch] == need[ch]) {
          valid--;
        }
      }
      right++;

      while (valid == 0) {
        if (right - left < len) {
          start = left;
          len = right - left;
        }
        char d = s.charAt(left);
        if (need[d] != 0) {
          if (window[d] == need[d]) {
            valid++;
          }
          window[d]--;
        }
        left++;
      }
    }
    return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
  }


  private void slidingWindow(String s, String t) {
    int[] need = new int[26];
    int[] window = new int[26];

    for (char c : t.toCharArray()) need[c - 'a']++;

    int left = 0;
    int right = 0;
    int valid = 0;

    while (right < s.length()) {
      char c = s.charAt(right);
      right++;


    }

  }

}
