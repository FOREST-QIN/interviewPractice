package feb.twentyFive;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Class {

  @Test
  public void test() {
    helper(1,3);

  }

  private void helper(int base, int range) {
    Random random = new Random();

    double average = 0;
    List<Integer> res = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      res.add(base + random.nextInt(range));
    }
    int sum = 0;
    for (int num : res) {
      sum += num;
    }
    average = sum / 10.0;
    System.out.println(res.toString());
    System.out.println(average);
  }

}
