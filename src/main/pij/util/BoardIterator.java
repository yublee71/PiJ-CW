package pij.util;

import pij.model.Board;
import pij.model.Direction;
import pij.model.Position;
import pij.model.Tile;

/**
 * Utility class for iterating over the tile grid on the board in a specified direction.
 * Supports separate handling for existing tiles and empty positions
 * using {@link ExistingTileHandler} and {@link EmptyTileHandler}.
 *
 * @author Yubeen Lee
 */
public class BoardIterator {
  /**
   * Iterates backwards from the given position along the specified direction,
   * applying the {@code ExistingTileHandler} to each tile encountered until the board edge
   * or an empty square is reached.
   *
   * @param position  the starting position
   * @param direction the direction to iterate (HORIZONTAL or VERTICAL)
   * @param board     the board to iterate over
   * @param handler   handler for existing tiles
   */
  public static void iterateBackwards(Position position, Direction direction,
                                      Board board, ExistingTileHandler handler) {
    int startCol = position.col();
    int startRow = position.row();
    Tile[][] tileGrid = board.getTileGrid();

    if (direction == Direction.HORIZONTAL) {
      int col = startCol - 1;
      while (col >= 0 && tileGrid[startRow][col] != null) {
        handler.handle(tileGrid[startRow][col]);
        col--;
      }
    } else {
      int row = startRow - 1;
      while (row >= 0 && tileGrid[row][startCol] != null) {
        handler.handle(tileGrid[row][startCol]);
        row--;
      }
    }
  }

  /**
   * Iterates forward from the given position along the specified direction,
   * applying {@code existingTileHandler} to existing tiles and
   * {@code emptyTileHandler} to empty positions.
   * Iteration stops when {@code emptyTileHandler} returns {@code false}
   * or when all the new tiles have been processed (i.e. iterateLimit has been reached).
   *
   * @param position            the starting position
   * @param direction           the direction to iterate (HORIZONTAL or VERTICAL)
   * @param board               the board to iterate over
   * @param existingTileHandler handler for existing tiles
   * @param emptyTileHandler    handler for empty positions
   * @param iterateLimit        the number of new tiles to handle
   * @return the number of new tiles that were successfully handled
   */
  public static int iterateForward(Position position, Direction direction, Board board,
                                   ExistingTileHandler existingTileHandler,
                                   EmptyTileHandler emptyTileHandler,
                                   int iterateLimit) {
    int startCol = position.col();
    int startRow = position.row();
    Tile[][] tileGrid = board.getTileGrid();
    int boardCols = board.getColumns();
    int boardRows = board.getRows();

    int iterateIndex = 0;

    while (startCol < boardCols && startRow < boardRows) {
      Tile tile = tileGrid[startRow][startCol];
      if (iterateIndex >= iterateLimit && tile == null) {
        break;
      }

      if (tile == null) {
        if (!emptyTileHandler.handle(new Position(startCol, startRow), iterateIndex))
          break;
        iterateIndex++;
      } else {
        existingTileHandler.handle(tile);
      }

      if (direction == Direction.HORIZONTAL)
        startCol++;
      else
        startRow++;
    }
    return iterateIndex;
  }
}
