package CS501.assignment3;

import java.util.Arrays;
import java.util.Scanner;

public class Seven {
  public static void main(String[] args) {
    System.out.println("Function Description:\n");
    System.out.println("User could enter 10 numbers which are separate by space, ");
    System.out.println("the function will display the minimum number and its index.");
    System.out.println("In the same time, the function will display your numbers after sorted(Revise ).");
    Scanner input = new Scanner(System.in);
    int repeat = 1;
    while (repeat != 0) {
      System.out.println("Please enter 10 numbers like (1.4 1.1 1.3 1.5 ...)");
      String nums = input.nextLine();
      boolean isTenNumbers = isTenNumbers(nums);
      if (!isTenNumbers) {
        System.out.println("The input is not correct! Please try again.");
      } else {
        String[] strs = nums.split(" ");
        double[] arr = new double[10];
        int index = 0;
        while (index < strs.length) {
          arr[index] = Double.parseDouble(strs[index]);
          index++;
        }
        double minNumber = findMin(arr);
        int minIndex = findTheSmallestElementIndex(arr);
        reviseSelectionSort(arr);
        System.out.println("The min number is: " + minNumber);
        System.out.println("The min number's index is: " + minIndex);
        System.out.println("The sorted list is: " + Arrays.toString(arr));
      }
      System.out.println("##################################################");
      System.out.println("##########  Repeat program: enter 1.  ############");
      System.out.println("##########  Exit program: enter 0.    ############");
      System.out.println("##################################################");
      repeat = Integer.parseInt(input.nextLine());
    }
  }

  private static boolean isTenNumbers(String str) {
    String[] arr = str.split(" ");
    return arr.length == 10;
  }

  private static void reviseSelectionSort(double[] list) {
    for (int i = list.length - 1; i > 0; i--) {
      double curMax = list[i];
      int index = i;
      for (int j = i - 1; j >= 0; j--) {
        if (list[j] > curMax) {
          curMax = list[j];
          index = j;
        }
      }
      swap(list, index, i);
    }
  }

  private static double findMin(double[] arr) {
    double min = arr[0];
    for (double num : arr) {
      min = Math.min(min, num);
    }
    return min;
  }

  private static int findTheSmallestElementIndex(double[] array) {
    int res = 0;
    for (int i = 0; i < array.length; i++) {
      if (array[i] < array[res]) res = i;
    }
    return res;
  }

  private static void swap(double[] arr, int i, int j) {
    double tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }

}
