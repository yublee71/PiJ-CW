package pij.util;

import pij.model.Board;
import pij.model.Direction;
import pij.model.Position;
import pij.model.Tile;

/**
 *
 *
 * @author Yubeen Lee
 */
public class BoardIterator {
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
        emptyTileHandler.handle(new Position(startCol, startRow), iterateIndex);
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
