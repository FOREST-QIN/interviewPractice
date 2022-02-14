package CS501.assignment2;

public class C4Q3 {
  static double RADIUS = 6371.01;

  public static void main(String[] args) {
    System.out.println("(Geography: estimate areas) Use the GPS locations for Atlanta, Georgia; \n Orlando, Florida; Savannah, Georgia; and Charlotte, North Carolina in the figure in Section 4.1 \n to compute the estimated area enclosed by these four cities. \n(Hint: Use the formula in Programming Exercise 4.2 to compute the distance between two cities.\n Divide the polygon into two triangles and use the formula in\n Programming Exercise 2.19 to compute the area of a triangle.)");
    System.out.println("===================================");
    System.out.println("=========R U N N I N G=============");
    System.out.println("===================================");
    double atlantaGeoY = -84.3879824;
    double atlantaGeoX = 33.7489954;

    double orlandoFLY = -81.3792364999;
    double orlandoFLX = 28.5383355;

    double SavannahGeoY = -81.09983419999998;
    double SavannahGeoX = 32.0835407;

    double charlotteNCY = -80.84312669999997;
    double charlotteNCX = 35.2270869;

    double oneSide1 = getDistance(atlantaGeoX, atlantaGeoY, SavannahGeoX, SavannahGeoY);
    double oneSide2 = getDistance(SavannahGeoX, SavannahGeoY, charlotteNCX, charlotteNCY);
    double oneSide3 = getDistance(charlotteNCX, charlotteNCY, atlantaGeoX, atlantaGeoY);

    double twoSide1 = getDistance(atlantaGeoX, atlantaGeoY, orlandoFLX, orlandoFLY);
    double twoSide2 = getDistance(orlandoFLX, orlandoFLY, SavannahGeoX, SavannahGeoY);
    double twoSide3 = getDistance(SavannahGeoX, SavannahGeoY, atlantaGeoX, atlantaGeoY);

    boolean isOneTriangle = isTriangle(oneSide1, oneSide2, oneSide3);
    boolean isTwoTriangle = isTriangle(twoSide1, twoSide2, twoSide3);



    if (isOneTriangle && isTwoTriangle) {
      double oneArea = getArea(oneSide1, oneSide2, oneSide3);
      double twoArea = getArea(twoSide1, twoSide2, twoSide3);
      System.out.println("Triangle one is " + oneSide1 + " - " + oneSide2 + " - " + oneSide3 + " area : " + oneArea);
      System.out.println("Triangle two is " + twoSide1 + " - " + twoSide2 + " - " + twoSide3 + " area : " + twoArea);
      double total = oneArea + twoArea;
      System.out.println("The area is: " + total);
    } else {
      System.out.println("The coordinates are incorrect.");
    }

  }

  public static double getDistance(double x1, double y1, double x2, double y2) {
    return RADIUS *
        Math.acos(Math.sin(Math.toRadians(x1)) * Math.sin(Math.toRadians(x2)) +
            Math.cos(Math.toRadians(x1)) * Math.cos(Math.toRadians(x2)) * Math.cos(Math.toRadians(y1 - y2)));
  }

  public static double getArea(double s1, double s2, double s3) {
    double s = (s1 + s2 + s3) / 2.0;
    return Math.pow(s * (s - s1) * (s - s2) * (s - s3), 0.5);
  }

  public static boolean isTriangle(double s1, double s2, double s3) {
    return s1 + s2 > s3 && s1 + s3 > s2 && s2 + s3 > s1;
  }

}
