package feb.seven;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Solution {

    @Test
    public void test() {
        int[] arr = {3,2,1,5,6,4};
        int res = findKthLargest(arr, 2);
    }



    public int calculate(String s) {

        if (s == null || s.length() == 0) return 0;

        int num = 0;
        int tmp = 0;
        int res = 0;
        char op = '+';

        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                tmp = tmp * 10 + (ch - '0');
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

    public double myPow(double x, int n) {
        if (n == 0) return 1;
        if (x == 0 || x == 1 || n == 1) return x;
        double num = myPow(x, n / 2);
        if (n % 2 == 0) return num * num;
        if (n < 0) {
            return num * num / x;
        } else {
            return num * num * x;
        }
    }

    public int findKthLargest(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;
        int index = nums.length;

        while (index != k) {
            index = partition(nums, left, right);
            if (index == k) {
                break;
            } else if (index > k) {
                right = index - 1;
            } else {
                left = index + 1;
            }
        }
        return nums[index];
    }

    private int partition(int[] arr, int left, int right) {
        int pivot = left + (right - left) / 2;
        int base = arr[pivot];

        while (left <= right) {
            if (arr[left] > base) {
                left++;
            } else {
                swap(arr, left, right--);
            }
        }
        return left;
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    public String simplifyPath(String path) {
        if (path == null || path.length() == 0) return "/";
        String[] arr = path.split("/");
        Deque<String> deque = new ArrayDeque<>();

        for (String str : arr) {
            if (str.equals("") || str.equals(".")) continue;
            if (str.equals("..")) {
                deque.pollFirst();
            } else {
                deque.offerFirst(str);
            }
        }
        if (deque.size() == 0) return "/";
        StringBuilder prefix = new StringBuilder();
        while (!deque.isEmpty()) {
            prefix.append("/");
            prefix.append(deque.pollLast());
        }
        return prefix.toString();
    }

    public int[][] kClosest(int[][] points, int k) {
        if (points == null || points.length == 0) return points;
        int left = 0;
        int right = points.length - 1;
        int pivotIndex = points.length;
        while (pivotIndex != k) {
            pivotIndex = partition(points, left, right);
            if (pivotIndex < k) {
                left = pivotIndex;
            } else if (pivotIndex > k) {
                right = pivotIndex - 1;
            } else {
                break;
            }
        }
        return Arrays.copyOfRange(points, 0, k);
    }

    private int partition(int[][] points, int left, int right) {
        int[] pivot = choosePivot(points, left, right);
        int dist = getDistance(pivot);
        while (left <= right) {
            if (getDistance(points[left]) < dist) {
                left++;
            } else {
                int[] tmp = points[left];
                points[left] = points[right];
                points[right] = tmp;
                right--;
            }
        }
        return left;
    }

    private int getDistance(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }

    private int[] choosePivot(int[][] points, int left, int right) {
        return points[left + (right - left) / 2];
    }

}
