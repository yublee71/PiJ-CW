package pij.util;

import pij.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 *
 *
 * @author Yubeen Lee
 */
public class MoveValidator {
  public static boolean isMoveValid(Move move, Player player, Board board, Dictionary dictionary) {
    // if pass return true
    if (move.getMoveType() == MoveType.PASS)
      return true;
    return isLettersInRack(player.getRack(), move.getLetters()) &&
        isPlacementValid(move, board, dictionary);
  }

  private static boolean isLettersInRack(List<Tile> rack, String letters) {
    Objects.requireNonNull(rack, "Player rack cannot be null");
    Objects.requireNonNull(letters, "Letters cannot be null");

    if (letters.isEmpty()) {
      return true;
    }
    if (rack.isEmpty()) {
      return false;
    }

    List<Tile> rackCopy = new ArrayList<>(rack);
    for (char c : letters.toCharArray()) {
      boolean found = false;
      for (int i = 0; i < rackCopy.size(); i++) {
        char r = rackCopy.get(i).getLetter();
        if ((c >= 'a' && c <= 'z' && r == '_') || c == r) {
          found = true;
          rackCopy.remove(i);
          break;
        }
      }
      if (!found) {
        return false;
      }
    }
    return true;
  }

  private static boolean isMoveStartPositionValid(Board board, Position moveStart) {
    return board.isPositionInBoard(moveStart)
        && board.getTileGrid()[moveStart.row()][moveStart.col()] == null;
  }

  public static boolean isPlacementValid(Move move, Board board, Dictionary dictionary) {
    Objects.requireNonNull(move, "Move cannot be null");
    Objects.requireNonNull(board, "Board cannot be null");

    //Check if the start position is inside the board range
    //plus, if the firstmove, then check if it includes start position
    //Check if there is any tile in that placement or right before and after and letters are all included
    //Check if there is any tile in adjacent places

    //if start position is out of board or already occupied return false
    if (!isMoveStartPositionValid(board, move.getMoveStartPosition())) {
      return false;
    }

    String word = buildWord(move, board);
    //TODO: check start position for the first move
    //TODO: test
    System.out.println(word);
    return word != null && (word.length() > move.getLetters().length() || move.isFirstMove());
//        && dictionary.contains(word);
  }

  private static String buildWord(Move move, Board board) {
    String preWord = prependExistingTiles(move, board);
    String word = addNewTiles(move, board);

    if (word == null)
      return null;

    return preWord + word;
  }

  private static String prependExistingTiles(Move move, Board board) {
    Direction moveDirection = move.getDirectedPosition().getDirection();
    int startCol = move.getMoveStartPosition().col();
    int startRow = move.getMoveStartPosition().row();
    Tile[][] tileGrid = board.getTileGrid();

    StringBuilder word = new StringBuilder();

    if (moveDirection == Direction.HORIZONTAL) {
      int col = startCol - 1;
      while (col >= 0 && tileGrid[startRow][col] != null) {
        word.append(tileGrid[startRow][col].getLetter());
        col--;
      }
    } else {
      int row = startRow - 1;
      while (row >= 0 && tileGrid[row][startCol] != null) {
        word.append(tileGrid[row][startCol].getLetter());
        row--;
      }
    }

    return word.reverse().toString().toLowerCase(Locale.ROOT);
  }

  private static String addNewTiles(Move move, Board board) {
    Direction moveDirection = move.getMoveDirection();
    int startCol = move.getMoveStartPosition().col();
    int startRow = move.getMoveStartPosition().row();
    String letters = move.getLetters();
    Tile[][] tileGrid = board.getTileGrid();
    int boardCols = board.getColumns();
    int boardRows = board.getRows();

    StringBuilder word = new StringBuilder();
    int letterIndex = 0;

    while (startCol < boardCols && startRow < boardRows) {
      Tile tile = tileGrid[startRow][startCol];
      if (letterIndex >= letters.length() && tile == null) {
        break;
      }

      if (tile == null) {
        if (!isAdjacentFree(moveDirection, startCol, startRow, boardCols, boardRows, tileGrid)) {
          break;
        }
        word.append(letters.charAt(letterIndex));
        letterIndex++;
      } else {
        word.append(tile.getLetter());
      }

      if (moveDirection == Direction.HORIZONTAL)
        startCol++;
      else
        startRow++;
    }

    if (letterIndex < letters.length())
      return null;

    return word.toString().toLowerCase(Locale.ROOT);
  }

  private static boolean isAdjacentFree(Direction moveDirection, int col, int row,
                                        int boardCols, int boardRows, Tile[][] tileGrid) {
    if (moveDirection == Direction.HORIZONTAL) {
      boolean topFree = (row - 1 < 0) || (tileGrid[row - 1][col] == null);
      boolean bottomFree = (row + 1 >= boardRows) || (tileGrid[row + 1][col] == null);
      return topFree && bottomFree;
    } else {
      boolean leftFree = (col - 1 < 0) || (tileGrid[row][col - 1] == null);
      boolean rightFree = (col + 1 >= boardCols) || (tileGrid[row][col + 1] == null);
      return leftFree && rightFree;
    }
  }
}
