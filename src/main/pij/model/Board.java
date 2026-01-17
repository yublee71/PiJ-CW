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

    tileGrid = new Tile[rows][columns];
    squareGrid = new Square[rows][columns];

    if (startPosition == null) {
      throw new IllegalArgumentException("Start position cannot be null.");
    }
    if (startPosition.length() < 2) {
      throw new IllegalArgumentException("Start position must be at least 2 characters long.");
    }

    char startColChar = startPosition.charAt(0);
    int startColIndex = startColChar - 'a';

    int startRowIndex;
    try {
      startRowIndex = Integer.parseInt(startPosition.substring(1)) - 1;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid row format in start position: " + startPosition);
    }

    if (startColIndex < 0 || startColIndex >= columns) {
      throw new IllegalArgumentException("Invalid start position column: " + startColChar);
    }
    if (startRowIndex < 0 || startRowIndex >= rows) {
      throw new IllegalArgumentException("Invalid start position row: " + startRowIndex);
    }

    this.startPosition = new Position(startColIndex, startRowIndex);
  }
}
