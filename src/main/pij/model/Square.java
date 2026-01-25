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

  public SquareType getType() {
    return type;
  }

  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return switch (type) {
      case STANDARD -> ".";
      case LETTER_PREMIUM -> "[" + value + "]";
      case WORD_PREMIUM -> "<" + value + ">";
    };

  }

  public String toBoardString() {
    String formattedValue = String.format("%2d", value);
    return switch (type) {
      case STANDARD -> " . ";
      case LETTER_PREMIUM -> formattedValue + ".";
      case WORD_PREMIUM -> formattedValue + "!";
    };
  }
}
