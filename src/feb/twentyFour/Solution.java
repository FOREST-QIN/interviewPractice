package feb.twentyFour;

public class Solution {

  public boolean canPartitionKSubsets(int[] nums, int k) {
    int sum = 0;
    for (int num : nums) sum += num;
    if (sum % k != 0) return false;
    int target = sum / k;
    return dfs(k, 0, 0, nums, target);
  }

  private boolean dfs(int kRemainingToFill, int curSum, int numsIdx, int[] nums, int target) {
    if (kRemainingToFill <= 0) return true;
    for (int i = numsIdx; i < nums.length; i++) {
      if (nums[i] != 0 && curSum + nums[i] <= target) {
        int tmp = nums[i];
        nums[i] = 0;
        if (curSum + tmp == target) {
          if (dfs(kRemainingToFill - 1, 0, 0, nums, target)) return true;
        } else {
          if (dfs(kRemainingToFill, curSum + tmp, i + 1, nums, target)) return true;
        }
        nums[i] = tmp;
      }
    }
    return false;
  }


  int index = 0;
  public int calculate(String s) {
     if (s == null || s.length() == 0) return 0;
     int res = 0;
     int num = 0;
     int tmp = 0;
     char op = '+';
     while (index < s.length()) {
       char ch = s.charAt(index++);
       if (Character.isDigit(ch)) {
         tmp = tmp * 10 + ch - '0';
       } else if (ch == '(') {
         tmp = calculate(s);
       } else if (ch == ')') {
         break;
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
    if (op == '*') return num * tmp;
    return num / tmp;
  }

}
