package feb.sixteen;

public class Vector2D {

  int r;
  int c;
  int[][] vec;
  int m;
  public Vector2D(int[][] vec) {
    this.vec = vec;
    this.r = 0;
    this.c = 0;
    this.m = vec.length;
  }

  public int next() {
    int res = vec[r][c++];
    hasNext();
    return res;
  }

  public boolean hasNext() {

    while (r < m && c == vec[r].length) {
      r++;
      c = 0;
    }
    if (r >= m) return false;
    return true;
  }

}
