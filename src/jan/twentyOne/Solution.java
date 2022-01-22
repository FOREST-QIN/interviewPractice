package jan.twentyOne;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Solution {

  public boolean isValid(String s) {
    if (s == null || s.length() == 0) return true;

    Map<Character, Character> map = getMap();
    Deque<Character> stack = new ArrayDeque<>();
    for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      if (ch == '(' || ch == '{' || ch == '[') {
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
    map.put('}', '{');
    map.put(']', '[');
    return map;
  }


  public String minRemoveToMakeValid(String s) {
    if (s == null || s.length() == 0) return s;

    int left = 0;
    int unmatched = 0;
    StringBuilder removeRight = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      if (ch == '(') {
        left++;
        unmatched++;
      } else if (ch == ')') {
        if (unmatched == 0) continue;
        unmatched--;
      }
      removeRight.append(ch);
    }

    int validLeft = left - unmatched;
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < removeRight.length(); i++) {
      char ch = removeRight.charAt(i);
      if (ch == '(') {
        if (validLeft == 0) continue;
        validLeft--;
      }
      res.append(ch);
    }
    return res.toString();
  }


  public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int left = (nums1.length + nums2.length + 1) / 2;
    int right = (nums1.length + nums2.length + 2) / 2;

    int l = binarySearch(nums1, 0, nums2, 0, left);
    int r = binarySearch(nums1, 0, nums2, 0, right);
    return (l + r) / 2.0;
  }

  private int binarySearch(int[] one, int i, int[] two, int j, int k) {
    if (i >= one.length) return two[j + k - 1];
    if (j >= two.length) return one[i + k - 1];

    if (k == 1) return Math.min(one[i], two[j]);
    int oneRes = (i + k / 2 - 1 >= one.length) ? Integer.MAX_VALUE : one[i + k / 2 - 1];
    int twoRes = (j + k / 2 - 1 >= two.length) ? Integer.MAX_VALUE : two[j + k / 2 - 1];

    if (oneRes <= twoRes) {
      return binarySearch(one, i + k / 2, two, j, k - k / 2);
    } else {
      return binarySearch(one, i, two, j + k / 2, k - k / 2);
    }
  }

}
