package CS501.assignment2;



import java.util.Scanner;

public class C3Q27 {
  public static void main(String[] args) {
    System.out.println("(Geometry: points in triangle?) Suppose a right triangle is \n" +
        "placed in a plane as shown below. The right-angle point is placed \n" +
        "at (0, 0), and the other two points are placed at (200, 0) and (0, 100). \n" +
        "Write a program that prompts the user to enter a point with x- and y-coordinates \n" +
        "and determines whether the point is inside the triangle. Here are the sample runs:\n");
    System.out.println("===================================");
    System.out.println("=========R U N N I N G=============");
    System.out.println("===================================");
    Scanner input = new Scanner(System.in);
    System.out.println("Enter a point's x- and y- coordinates: ");
    double x = input.nextDouble();
    double y = input.nextDouble();
    // equation is - 0.5 * x + 100 = y
    // if (x, y) in the triangle, 2.0 * x + y  200
    if (x > 0.0 && y > 0 && (2.0 * y + x) < 200) {
      System.out.println("The point is in the triangle.");
    } else {
      System.out.println("The point is not in the triangle.");
    }
  }
}
