package pij.model;

/**
 *
 *
 * @author Yubeen Lee
 */
public class Board {
  public static final int MIN_COLUMNS = 7;
  public static final int MAX_COLUMNS = 26;
  public static final int MIN_ROWS = 10;
  public static final int MAX_ROWS = 99;
  public static final int MIN_SIZE = 192;
  private final int columns;
  private final int rows;
  private final Tile[][] tileGrid;
  private final Square[][] squareGrid;
  private final Position startPosition;

  public Board(int columns, int rows, Position startPosition) {
    if (columns < MIN_COLUMNS || columns > MAX_COLUMNS) {
      throw new IllegalArgumentException("Invalid number of columns: " + columns +
          ". Must be between " + MIN_COLUMNS + " and " + MAX_COLUMNS + ".");
    }
    if (rows < MIN_ROWS || rows > MAX_ROWS) {
      throw new IllegalArgumentException("Invalid number of rows: " + rows +
          ". Must be between " + MIN_ROWS + " and " + MAX_ROWS + ".");
    }
    if (columns * rows < MIN_SIZE) {
      throw new IllegalArgumentException("Board is too small: " + (columns * rows) +
          " squares. Must have at least " + MIN_SIZE + " squares.");
    }

    if (startPosition == null) {
      throw new IllegalArgumentException("Start position cannot be null.");
    }
    if (startPosition.getCol() < 0 || startPosition.getCol() >= columns ||
        startPosition.getRow() < 0 || startPosition.getRow() >= rows) {
      throw new IllegalArgumentException("Invalid start position: " + startPosition);
    }

    this.columns = columns;
    this.rows = rows;
    this.tileGrid = new Tile[rows][columns];
    this.squareGrid = new Square[rows][columns];
    this.startPosition = startPosition;
  }

  public Position getStartPosition() {
    return startPosition;
  }

  public void putSquare(int col, int row, Square square) {
    if (col < 0 || col >= columns || row < 0 || row >= rows) {
      throw new IllegalArgumentException("Square position out of bounds.");
    }
    squareGrid[row][col] = square;
  }

  public void putTile(int col, int row, Tile tile) {
    if (col < 0 || col >= columns || row < 0 || row >= rows) {
      throw new IllegalArgumentException("Tile position out of bounds.");
    }
    tileGrid[row][col] = tile;
  }

  public void printSquareGrid() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        if (squareGrid[row][col] != null) {
          System.out.print(squareGrid[row][col]);
        }
      }
      System.out.println();
    }
  }

  public void printTileGrid() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        if (tileGrid[row][col] != null) {
          System.out.print(tileGrid[row][col]);
        }
      }
    }
  }

  public void showBoard() {
    printColumnIndex();
    String formattedRow;
    for (int row = 0; row < rows; row++) {
      formattedRow = String.format("%2d", row + 1);
      System.out.print(formattedRow + " ");
      for (int col = 0; col < columns; col++) {
        if (tileGrid[row][col] == null) {
          System.out.print(squareGrid[row][col].toBoardString());
        } else {
          System.out.print(tileGrid[row][col].toBoardString());
        }
      }
      System.out.println(" " + formattedRow);
    }
    printColumnIndex();
  }

  private void printColumnIndex() {
    System.out.print("   ");
    for (int col = 0; col < columns; col++) {
      System.out.print(" " + (char) ('a' + col) + " ");
    }
    System.out.println();
  }
}
