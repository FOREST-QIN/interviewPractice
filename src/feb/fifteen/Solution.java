package feb.fifteen;

public class Solution {
  public boolean isMatch(String s, String p) {
    // use dynamic programming to solve this problem
    if (p == null || p.length() == 0) return s == null || s.length() == 0;
    /*
    Imagine I have a table
    dp[s.length + 1][p.length() + 1],
    dp[i][j] represents whether the first i characters from s, is match the first j character from p

    if I have this table, I could fill this dp table by traversing.
    base case: dp[0][0] = true, two empty string is matched
    the other base cas is whether pattern could match 0 s
    when current p's j char is *, dp[0][j] = dp[0][j - 2]

    traverse [1, m] i from s,
              [1, n] j from pattern
            if (s.charAt(i - 1) == p.charAt(j - 1) || )
          case one: two char is the
          dp[i][j] = 1) s.charAt(i - 1) == p.charAt(j - 1) || current pattern char is .
                        dp[i - 1][j - 1]
                     2) match 0's previous character or matched j character,
                        dp[i][j - 2] || (s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.')
                         && dp[i - 1][j]

     */

    int m = s.length();
    int n = p.length();
    return false;
  }
}
