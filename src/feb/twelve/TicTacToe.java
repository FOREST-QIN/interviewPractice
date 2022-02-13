package feb.twelve;

public class TicTacToe {
  // rows: rows[] record how many
  // 1 / -1
  // rows[i] == size | -
  // dia / anti
  int[] rows;
  int[] cols;
  int dia;
  int antiDia;
  int n;

  public TicTacToe(int n) {
    this.n = n;
    this.rows = new int[n];
    this.cols = new int[n];
    this.dia = 0;
    this.antiDia = 0;
  }

  public int move(int row, int col, int player) {
    int cur = player == 1 ? 1 : -1;
    rows[row] += cur;
    cols[col] += cur;
    if (row == col) dia += cur;
    if (row == rows.length - 1 - col) antiDia += cur;
    if (Math.abs(rows[row]) == n ||
        Math.abs(cols[col]) == n ||
        Math.abs(dia) == n ||
        Math.abs(antiDia) == n
    ) {
      return player;
    }
    return 0;
  }
}

