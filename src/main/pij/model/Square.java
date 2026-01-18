package pij.model;

/**
 *
 *
 * @author Yubeen Lee
 */
public class Square {
  public static final int MIN_VALUE = -9;
  public static final int MAX_VALUE = 99;
  private final int value;
  private final SquareType type;

  public Square(int value, SquareType type) {
    if (value < MIN_VALUE || value > MAX_VALUE) {
      throw new IllegalArgumentException("Invalid square value: " + value);
    }
    if (type == null) {
      throw new IllegalArgumentException("Square type cannot be null.");
    }
    this.value = value;
    this.type = type;
  }

  @Override
  public String toString() {
    if (type == SquareType.STANDARD) {
      return ".";
    } else if (type == SquareType.LETTER_PREMIUM) {
      return "[" + value + "]";
    } else if (type == SquareType.WORD_PREMIUM) {
      return "<" + value + ">";
    } else {
      return "";
    }
  }
}
