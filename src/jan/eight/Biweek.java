package jan.eight;

import org.junit.Test;

import java.util.*;

public class Biweek {

  @Test
  public void test() {
    String[] words = {"lc", "cl", "gg"};
    int res = longestPalindrome(words);
  }

  public int longestPalindrome(String[] words) {
    if (words.length == 0) return 0;

    /*
    two letters are palindrome
    one letter itself is palindrome
    two letter as much as possible, only one self palindrome
     */
    Set<String> wordsSet = new HashSet<>(Arrays.asList(words));
    Set<String> visited = new HashSet<>();
    int twoLetterPalindrome = 0;
    int selfPalindrome = 0;

    for (String word : words) {
      if (!visited.contains(word)) {
        String reversed = reverse(word);
        if (reversed.equals(word)) {
          visited.add(word);
          selfPalindrome++;
        } else if (wordsSet.contains(reversed)) {
          visited.add(word);
          visited.add(reversed);
          twoLetterPalindrome++;
        }
      }
    }
    selfPalindrome = Math.min(selfPalindrome, 1);
    return twoLetterPalindrome * 2 + Math.min(selfPalindrome, 1) * 2;
  }

  private String reverse(String word) {
    char[] array = word.toCharArray();
    char tmp = array[0];
    array[0] = array[1];
    array[1] = tmp;
    return new String(array);
  }

  public int pairSum(ListNode head) {
    if (head == null || head.next == null) return 0;
    Map<Integer, Integer> map = new HashMap<>();
    int index = 0;
    while (head != null) {
      map.put(index++, head.val);
      head = head.next;
    }

    int max = Integer.MIN_VALUE;
    for (int i = 0; i < (index / 2) - 1; i++) {
      int curSum = map.get(i) + map.get((index - 1 - i));
      max = Math.max(max, curSum);
    }

    return max;
  }

  public String capitalizeTitle(String title) {
    if (title == null || title.length() == 0) return title;

    char[] array = title.toCharArray();
    int i = 0;
    while (i < array.length) {
      int j = i;
      while (j < array.length && array[j] != ' ') j++;
      if (j - i <= 2) {
        for (int k = i; k < j; k++) {
          array[k] = Character.toLowerCase(array[k]);
        }
      } else {
        for (int k = i; k < j; k++) {
          if (k == i) {
            array[k] = Character.toUpperCase(array[k]);
          } else {
            array[k] = Character.toLowerCase(array[k]);
          }
        }
      }
      i = j + 1;
    }

    return new String(array);
  }


  static class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }
  }

}
