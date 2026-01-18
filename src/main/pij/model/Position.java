package pij.model;

/**
 *
 *
 * @author Yubeen Lee
 */
public class Position {
  private final int col;
  private final int row;

  public Position(int col, int row) {
    this.col = col;
    this.row = row;
  }

  public int getCol() {
    return col;
  }

  public int getRow() {
    return row;
  }

  @Override
  public String toString() {
    return "" + (char) (col + 'a') + (row + 1);
  }
}
