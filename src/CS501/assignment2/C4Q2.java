package CS501.assignment2;

import java.util.Scanner;

public class C4Q2 {
  public static void main(String[] args) {
    System.out.println("Write a program that prompts the user to enter the latitude and longitude of \n" +
        "two points on the earth in degrees and displays its great circle distance. The average radius of \n" +
        "the earth is 6,371.01 km. Note you need to convert the degrees into radi- ans using the Math.toRadians \n" +
        "method since the Java trigonometric methods use radians. The latitude and longitude degrees in the formula \n" +
        "are for north and west. Use negative to indicate south and east degrees. Here is a sample run:\n");
    System.out.println("===================================");
    System.out.println("=========R U N N I N G=============");
    System.out.println("===================================");
    Scanner input = new Scanner(System.in);
    System.out.println("Enter point 1 (latitude and longitude) in the degrees: ");
    double oneLatitude = input.nextDouble();
    double oneLongitude = input.nextDouble();
    System.out.println("Enter point 2 (latitude and longitude) in the degrees: ");
    double twoLatitude = input.nextDouble();
    double twoLongitude = input.nextDouble();

    double d = 6371.01 * Math.acos((Math.sin(Math.toRadians(oneLatitude)) * Math.sin(Math.toRadians(oneLongitude)))
        + (Math.cos(Math.toRadians(oneLatitude)) * Math.cos(Math.toRadians(oneLongitude)) * Math.cos(Math.toRadians(twoLongitude) - Math.toRadians(twoLatitude))));
    System.out.println("The distance between the two points is " + d + "km");
  }
}
