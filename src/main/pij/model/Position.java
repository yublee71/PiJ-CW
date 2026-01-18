package pij.model;

/**
 *
 *
 * @author Yubeen Lee
 */
public record Position(int col, int row) {

  @Override
  public String toString() {
    return "" + (char) (col + 'a') + (row + 1);
  }
}
