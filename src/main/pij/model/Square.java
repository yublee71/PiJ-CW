package pij.model;

/**
 * Represents a square on the board.
 *
 * @author Yubeen Lee
 */
public record Square(int value, SquareType type) {
  public static final int MIN_VALUE = -9;
  public static final int MAX_VALUE = 99;

  public Square {
    if (value < MIN_VALUE || value > MAX_VALUE) {
      throw new IllegalArgumentException("Invalid square value: " + value);
    }
    if (type == null) {
      throw new IllegalArgumentException("Square type cannot be null.");
    }
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
