package mar.five;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
  @Test
  public void test() {
    char[] chars = {'a', 'a', 'a', 'b', 'b', 'c', 'c', 'c'};
    int res = compress(chars);
  }

  public String reformatDate(String date) {
    StringBuilder prefix = new StringBuilder();
    String[] array = date.split(" ");
    Map<String, String> map = getMap();
    int day = 0;
    int i = 0;
    while (i < array[0].length() && Character.isDigit(array[0].charAt(i))) {
      day = day * 10 + array[0].charAt(i) - '0';
      i++;
    }
    prefix.append(array[2]).append("-");
    prefix.append(map.get(array[1])).append("-");
    if (day < 10) prefix.append("0");
    prefix.append(day);
    return prefix.toString();
  }

  private Map<String, String> getMap() {
    Map<String, String> map = new HashMap<>();
    map.put("Jan", "01");
    map.put("Feb", "02");
    map.put("Mar", "03");
    map.put("Apr", "04");
    map.put("May", "05");
    map.put("Jun", "06");
    map.put("Jul", "07");
    map.put("Aug", "08");
    map.put("Sep", "09");
    map.put("Oct", "10");
    map.put("Nov", "11");
    map.put("Dec", "12");
    return map;
  }


  public int compress(char[] chars) {
    /*
    ["a","a","b","b","c","c","c"]
      i
      j
              k
      k - j => get count
      if count > 1
          chars[i++] = chars[j];
          while (count != 0) {
            chars[i++] = count % 10;
            count /= 10;
            i = k;
     */
    int i = 0;
    int j = 0;
    int m = chars.length;
    while (j < m) {
      int k = j;
      while (k < m && chars[j] == chars[k]) k++;
      String count = Integer.toString(k - j);
      chars[i++] = chars[j];
      if (k - j > 1) {
        int l = 0;
        while (l < count.length()) {
          chars[i++] = count.charAt(l--);
        }
      }
      j = k;
    }
    return i;
  }


  int index = 0;

  public int calculate(String s) {
    if (s == null || s.length() == 0) return 0;

    int tmp = 0;
    int res = 0;
    int sign = 1;
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


  public String decodeString(String s) {
    int count = 0;
    StringBuilder prefix = new StringBuilder();
    while (index < s.length()) {
      // meet digit
      char ch = s.charAt(index);
      if (Character.isDigit(ch)) {
        count = count * 10 + ch - '0';
        index++;
      } else if (Character.isAlphabetic(ch)) {
        prefix.append(ch);
        index++;
      } else if (ch == '[') {
        index++;
        prefix.append(decodeString(s).repeat(count));
        count = 0;
      } else {
        index++;
        break;
      }
    }
    return prefix.toString();
  }

  public List<String> fullJustify(String[] words, int maxWidth) {
    List<String> res = new ArrayList<>();
    if (words == null || words.length == 0) return res;
    int index = 0;
    while (index < words.length) {
      // find the last word could add to string
      int last = index + 1;
      // count how many characters I have currently
      int count = words[index].length();
      while (last < words.length) {
        // if current words' char sum and spaces sum > maxWidth
        // break
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
        prefix.append(" ".repeat(Math.max(0, maxWidth - prefix.length())));
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
}
