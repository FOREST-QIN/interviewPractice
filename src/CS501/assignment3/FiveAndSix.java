package CS501.assignment3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FiveAndSix {
  public static void main(String[] args) {

    System.out.println("program Two: Display primes\n" +
        "First, this program will print all prime numbers from 1 to 50 and \n" +
        "print the count of prime numbers from 1 to 1000." +
        "Then the program will take a input from user to check if it is a prime number of not,\n" +
        "if its not a prime number, the program will display all prime factors of this number." );
    System.out.println();
    System.out.println("###########################################################");
    System.out.println("####################### S T A R T ########################");
    System.out.println("###########################################################");
    Scanner input = new Scanner(System.in);
    int repeatInt = 1;
    int program = 0;
    int[] res = new int[1000];
    while (repeatInt != 0) {
      System.out.println("Prime numbers from 1 to 50:");
      List<Integer> primes = new ArrayList<>();
      for (int i = 2; i <= 50; i++) {
        if (isPrime(i)) primes.add(i);
      }
      System.out.println(primes.toString());
      int count = 0;
      for (int i = 2; i  <= 1000; i++) {
        if (isPrime(i)) {
          count++;
        }
      }
      System.out.println("Count of prime numbers from 1 to 1000: " + count);
      System.out.println("Input a number, the program will check the input is prime or not.");
      System.out.println("If if not prime, the program will display all prime factors.");
      program = input.nextInt();
      if (isPrime(program)) {
        System.out.println("This number is prime!");
      } else {
        findFactors(program);
      }
      System.out.print("Do you want to rerun the program? Push 0 to leave");
      repeatInt = input.nextInt();
    }
  }

  public static void findFactors(int num) {
    int cur = num;
    StringBuilder prefix = new StringBuilder();
    for (int i = 2; i <= num / 2; i++) {
      while (cur % i == 0) {
        prefix.append(i).append(" ");
        cur /= i;
      }
    }
    System.out.println("All factors of input number " + num + " is " + prefix.toString());
  }


  public static void displayPrimeNumber() {

    System.out.println(
        "(Display pyramid) Write a program that\n" +
            " prompts the user to enter an integer\n" +
            " from 1 to 15 and displays a pyramid, \n" +
            "as shown in the following sample run:\n");
    int limitPrimeCount = 50;
    int limitNumInRow = 8;
    int count = 0;
    int number = 0;
    System.out.println("The first 50 prime numbers are: ");

    while (count < limitPrimeCount) {
      boolean flag = true;
      for (int i = 2; i <= number / 2; i++) {
        if (number % i == 0) {
          flag = false;
          break;
        }
      }
      if (flag) {
        count++;
        if (count % limitNumInRow == 0) {
          System.out.println(number);
        } else {
          System.out.print(number + " ");
        }
      }
      number++;
    }
  }

  public static int createPrime(int[] array) {
    int count = 0;
    int index = 0;
    for (int i = 2; i <= 1000; i++) {
      if (isPrime(i)) {
        array[index++] = i;
        count++;
      }
    }
    return count;
  }

  public static int primeNumberLessThan10000() {
    int count = 0;
    for (int i = 2; i <= 10000; i++) {
      if (isPrime(i)) count++;
    }
    return count;
  }

  public static boolean isPrime(int num) {
    for (int i = 2; i * i <= num; i++) {
      if (num % i == 0) return false;
    }
    return true;
  }
}
