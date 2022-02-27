package CS501.assignment3;

import java.util.Scanner;

public class FiveAndSix {
  public static void main(String[] args) {
    System.out.println("Introduction: \n" +
        "Here is the introduction of this programs program One: Display all\n" +
        " factors in non decreasing order User could push 1 to use this program,\n" +
        " then push a integer number factors will be displayed." );
    System.out.println();
    System.out.println("program Two: Display primes\n" +
        "User could push 2 to use this program Console will display the first\n" +
        "50 primes number then the user could push 1 to show prime number between\n" +
        " 2 and 1000 or just push a number between 2 and 1000 to check whether it is prime.");
    System.out.println();
    System.out.println("program Three: Display all prime numbers less than 10000.\n" +
        "User could push 3 to use it.");
    System.out.println();
    System.out.println("###########################################################");
    System.out.println("####################### S T A R T ########################");
    System.out.println("###########################################################");
    Scanner input = new Scanner(System.in);
    int repeatInt = 1;
    int program = 0;
    int[] res = new int[1000];
    while (repeatInt != 0) {
      System.out.println("Choose a programme 1, 2 or 3");
      program = input.nextInt();
      if (program == 1) {
        System.out.println("Please enter an integer:");
        int num = input.nextInt();
        findFactors(num);
      } else if (program == 2) {
        displayPrimeNumber();
        System.out.println("push 1 to show prime number between 2 and 1000 or just push a number\n" +
            "between 2 and 1000 to check whether it is prime.");
        int num = input.nextInt();
        int count = createPrime(res);
        if (num == 1) {
          int limit = 8;
          System.out.println(count + "prime numbers between 2 and 1000: \n");
          for (int i = 0; i < count; i++) {
            if (i % limit == 0) {
              System.out.println(res[i]);
            } else {
              System.out.print(res[i] + " ");
            }
            System.out.println("");
          }
        } else if (num >= 2 && num <= 1000) {
          int isP = java.util.Arrays.binarySearch(res,0, count, num);
          if (isP >= 0) {
            System.out.println(num + " is a prime number!");
          } else {
            System.out.println(num + "is not a prime number!");
          }
        } else {
          System.out.println("Input number is not valid!");
        }
      } else {
        int count = primeNumberLessThan10000();
        System.out.println("The total count of prime numbers less than 10000 is: " + count);
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
    for (int i = 2; i <= num / 2; i++) {
      if (num % i == 0) return false;
    }
    return true;
  }
}
