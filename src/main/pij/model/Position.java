package pij.model;

/**
 *
 *
 * @author Yubeen Lee
 */
public record Position(int col, int row) {
  public static Position stringToPosition(String startPositionString) {
    if (startPositionString.length() < 2) {
      return null;
//      throw new IllegalArgumentException("Start position must be at least 2 characters long.");
    }

    char startColChar = startPositionString.charAt(0);
    if (startColChar < 'a' || startColChar > 'z') {
      return null;
//      throw new IllegalArgumentException("Invalid column format in start position: " + startPositionString);
    }

    int startRowIndex;
    try {
      startRowIndex = Integer.parseInt(startPositionString.substring(1)) - 1;
    } catch (NumberFormatException e) {
      return null;
//      throw new IllegalArgumentException("Invalid row format in start position: " + startPositionString);
    }

    if (startRowIndex < 0) {
      return null;
//      throw new IllegalArgumentException("Invalid row format in start position: " + startPositionString);
    }
    return new Position(startColChar - 'a', startRowIndex);
  }

  @Override
  public String toString() {
    return "" + (char) (col + 'a') + (row + 1);
  }
}
