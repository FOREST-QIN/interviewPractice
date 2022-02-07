package feb.five;

import java.util.Arrays;

public class Bitset {
  int[] arr;
  int[] flipped;
  int one;
  public Bitset(int size) {
    this.arr = new int[size];
    this.flipped = new int[size];
    Arrays.fill(flipped, 1);
    this.one = 0;
  }

  public void fix(int idx) {
    if (arr[idx] == 0) {
      arr[idx] = 1;
      one++;
    }
    if (flipped[idx] == 1) flipped[idx] = 0;
  }

  public void unfix(int idx) {
    if (arr[idx] == 1) {
      arr[idx] = 0;
      one--;
    }
    if (flipped[idx] == 0) flipped[idx] = 1;
  }

  public void flip() {
    int[] tmp = arr;
    arr = flipped;
    flipped = tmp;
    one = arr.length - one;
  }

  public boolean all() {
    return one == arr.length;
  }

  public boolean one() {
    return one >= 1;
  }

  public int count() {
    return one;
  }

  public String toString() {
    StringBuilder prefix = new StringBuilder();

    for (int num : arr) {
      prefix.append(num);
    }

    return prefix.toString();
  }

}
