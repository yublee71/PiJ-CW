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

  public Board(int columns, int rows) {
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
  }
}
