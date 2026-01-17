package pij.model;

/**
 *
 *
 * @author Yubeen Lee
 */
public class Square {
  private final int value;
  private final SquareType type;

  public Square(int value, SquareType type) {
    if (value < -9 || value > 99) {
      throw new IllegalArgumentException("Invalid square value: " + value);
    }
    if (type == null) {
      throw new IllegalArgumentException("Square type cannot be null.");
    }
    this.value = value;
    this.type = type;
  }
}
