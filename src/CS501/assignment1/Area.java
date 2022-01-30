package CS501.assignment1;

import java.util.Scanner;

public class Area {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);


    System.out.print("Please input x1, y1\n");
    double x1 = input.nextDouble();
    double y1 = input.nextDouble();
    System.out.print("Please input x2, y2\n");
    double x2 = input.nextDouble();
    double y2 = input.nextDouble();
    System.out.print("Please input x3, y3\n");
    double x3 = input.nextDouble();
    double y3 = input.nextDouble();

    double side1 = Math.pow(((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)), 0.5);
    double side2 = Math.pow(((x3 - x1) * (x3 - x1) + (y3 - y1) * (y3 - y1)), 0.5);
    double side3 = Math.pow(((x2 - x3) * (x2 - x3) + (y2 - y3) * (y2 - y3)), 0.5);
    double s = (side1 + side2 + side3) / 2;
    double area = Math.pow((s * (s - side1) * (s - side2) * (s - side3)), 0.5);
    System.out.print("The area of the triangle is " + area);
  }
}
