package pij.model;

/**
 *
 *
 * @author Yubeen Lee
 */
public class Board {
  private final int columns;
  private final int rows;
  private final Tile[][] tileGrid;
  private final Square[][] squareGrid;
  private final Position startPosition;

  public Board(int columns, int rows, String startPosition) {
    if (columns < 7 || columns > 26) {
      throw new IllegalArgumentException("Invalid number of columns: " + columns +
          ". Must be between 7 and 26.");
    }
    if (rows < 10 || rows > 99) {
      throw new IllegalArgumentException("Invalid number of rows: " + rows +
          ". Must be between 10 and 99.");
    }
    if (columns * rows < 192) {
      throw new IllegalArgumentException("Board is too small: " + (columns * rows) +
          " squares. Must have at least 192 squares.");
    }
    this.columns = columns;
    this.rows = rows;

    tileGrid = new Tile[columns][rows];
    squareGrid = new Square[columns][rows];

    if (startPosition == null) {
      throw new IllegalArgumentException("Start position cannot be null.");
    }
    if (startPosition.length() != 2) {
      throw new IllegalArgumentException("Start position must be a pair of letters.");
    }
    if (startPosition.charAt(0) - 'a' < 0 || startPosition.charAt(0) - 'a' >= columns) {
      throw new IllegalArgumentException("Invalid start position column: " + startPosition.charAt(0));
    }
    if (startPosition.charAt(1) - '1' < 0 || startPosition.charAt(1) - '1' >= rows) {
      throw new IllegalArgumentException("Invalid start position column: " + startPosition.charAt(1));
    }
    this.startPosition = new Position(startPosition.charAt(0) - 'a', startPosition.charAt(1) - '1');
  }
}
