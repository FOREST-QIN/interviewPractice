package CS501.assignment2;

import java.util.Scanner;

public class C3Q3 {
  public static void main(String[] args) {
    System.out.println("(Game: multiply three numbers) The program in Listing 3.1, \n" +
        "AdditionQuiz.java, generates two integers and prompts the user to \n" +
        "enter the product of these two inte- gers. Revise the program to \n" +
        "generate three single-digit integers and prompt the user to \n" +
        "enter the multiplication of these three integers.\n");
    System.out.println("===================================");
    System.out.println("=========R U N N I N G=============");
    System.out.println("===================================");
    Scanner input = new Scanner(System.in);
    System.out.println("Enter a,b,c,d,e,f: ");
    double a = input.nextDouble();
    double b = input.nextDouble();
    double c = input.nextDouble();
    double d = input.nextDouble();
    double e = input.nextDouble();
    double f = input.nextDouble();

    double x = 0.0;
    double y = 0.0;

    if ((a * b - b * c) != 0) {
      x = (e * d - b * f) / (a * d - b * c);
      y = (a * f - e * c) / (a * d - b * c);
      System.out.println("x is " + x + " and y is " + y);
    } else {
      System.out.println("The equation has no solution.");
    }
  }
}
