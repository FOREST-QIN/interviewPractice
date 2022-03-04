package feb.twentyEight;

import org.junit.Test;

public class Solution {

  @Test
  public void test() {
    validWordAbbreviation(
        "internationalization",
        "i12iz4n");
  }



  public boolean validWordAbbreviation(String word, String abbr) {
    if (word.equals(abbr)) return true;
    int i = 0;
    int j = 0;

    while (i < word.length() && j < abbr.length()) {
      if (Character.isDigit(abbr.charAt(j))) {
        int count = 0;
        if (abbr.charAt(j) == '0') return false;
        while (j < abbr.length() && Character.isDigit(abbr.charAt(j))) {
          count = count * 10 + abbr.charAt(j) - '0';
          j++;
        }
        i += count;
      } else {
        if (word.charAt(i) != abbr.charAt(j)) return false;
        i++;
        j++;
      }
    }
    return i == word.length() && j == abbr.length();
  }
}
