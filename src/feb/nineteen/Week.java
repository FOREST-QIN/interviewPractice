package feb.nineteen;

import org.junit.Test;

public class Week {

  @Test
  public void test() {
    int res = countEven(1000);
    System.out.println(res);
  }

  public long coutPairs(int[] nums, int k) {
    int[] count = new int[nums.length];
    int i = nums.length - 2;
    count[nums.length - 1] = nums[nums.length - 1] % k == 0 ? 1 : 0;
    while (i >= 0) {
      if (nums[i] % k == 0) {
        count[i] = count[i + 1] + 1;
      } else {
        count[i] = count[i + 1];
      }
      i--;
    }

    int res = 0;
    for (int index = 0; index < count.length - 1; index++) {
      if (nums[index] % k == 0) {
        res += nums.length - index + 1;
      } else {
        res += count[index + 1];
      }
    }

    return res;
  }
  //  aaaabbbbb
  //  bbbabbaa
  public String repeatLimitedString(String s, int repeatLimit) {
    if (s == null || s.length() == 0) return s;
    char[] arr = s.toCharArray();
    int[] count = new int[26];
    for (char ch : arr) count[ch - 'a']++;
    int index = 0;
    for (int i = 25; i >= 0; i--) {
      while (count[i] > 0) {
        arr[index++] = (char)(i + 'a');
        count[i]--;
      }
    }
    index = 0;
    while (index < arr.length) {
      int record = 0;
      int idx = index;
      while (idx < arr.length && arr[idx] == arr[index] && record < repeatLimit) {
        record++;
        idx++;
      }
      if (idx < arr.length && arr[idx] == arr[index]) {
        int j = idx;
        while (j < arr.length && arr[idx] == arr[j]) j++;
        if (j < arr.length) swap(arr, idx, j);
        if (j == arr.length) return new String(arr, 0, idx);
      }
      index = idx;
    }
    return new String(arr, 0, index);
  }

  private void swap(char[] arr, int i, int j) {
    char tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }

  private void reverse(char[] arr) {
    int i = 0;
    int j = arr.length - 1;
    while (i <= j) {
      char tmp = arr[i];
      arr[i] = arr[j];
      arr[j] = tmp;
      i++;
      j--;
    }
  }


  public ListNode mergeNodes(ListNode head) {
    if (head == null || head.next == null) return null;
    ListNode dummy = new ListNode(0);
    ListNode d = dummy;

    ListNode cur = head;
    while (cur != null) {
      ListNode next = cur.next;
      int sum = 0;
      while (next != null && next.val != 0) {
        sum += next.val;
        next = next.next;
      }
      d.next = new ListNode(sum);
      d = d.next;
      if (next.next == null) break;
      cur = next;

    }
    return dummy.next;
  }


  public int countEven(int num) {
    int count = 0;
    for (int i = 1; i <= num; i++) {
      if (isDigitEven(i)) count++;
    }
    return count;
  }

  private boolean isDigitEven(int num) {
    int sum = 0;
    while (num != 0) {
      sum += num % 10;
      num /= 10;
    }
    return sum % 2 == 0;
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
