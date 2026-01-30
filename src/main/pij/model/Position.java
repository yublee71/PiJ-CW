package pij.model;

/**
 * Represents a position on the board.
 *
 * @author Yubeen Lee
 */
public record Position(int col, int row) {
  /**
   * Parses a string representation of a position to new {@code Position}.
   * Expected format: lowercase letter + 1-based row number (e.g. "a1", "b13").
   *
   * @param startPositionString the string to parse
   * @return a {@code Position} if valid, or null if the format is invalid
   */
  public static Position stringToPosition(String startPositionString) {
    if (startPositionString == null)
      return null;

    if (startPositionString.length() < 2) {
      return null;
    }

    char startColChar = startPositionString.charAt(0);
    if (startColChar < 'a' || startColChar > 'z') {
      return null;
    }

    int startRowIndex;
    try {
      startRowIndex = Integer.parseInt(startPositionString.substring(1)) - 1;
    } catch (NumberFormatException e) {
      return null;
    }

    if (startRowIndex < 0) {
      return null;
    }

    return new Position(startColChar - 'a', startRowIndex);
  }

  @Override
  public String toString() {
    return (char) (col + 'a') + Integer.toString(row + 1);
  }
}
