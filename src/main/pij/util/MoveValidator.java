package pij.util;

import pij.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Validates the given move for the given player, board, and dictionary.
 * Pass moves are always valid.
 *
 * @author Yubeen Lee
 */
public class MoveValidator {
  public static boolean isMoveValid(Move move, Player player, Board board, Dictionary dictionary) {
    if (move.getMoveType() == MoveType.PASS)
      return true;
    return isLettersInRack(player.getRack(), move.getLetters()) &&
        isPlacementValid(move, board, dictionary);
  }

  public static boolean isLettersInRack(List<Tile> rack, String letters) {
    if (letters.isEmpty()) {
      return true;
    }
    if (rack.isEmpty()) {
      return false;
    }

    List<Tile> rackCopy = new ArrayList<>(rack);
    for (char letter : letters.toCharArray()) {
      boolean found = false;
      for (int i = 0; i < rackCopy.size(); i++) {
        Tile tileInRack = rackCopy.get(i);
        if ((letter >= 'a' && letter <= 'z' && tileInRack.isWildcard())
            || letter == tileInRack.getLetter()) {
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


  public static boolean isPlacementValid(Move move, Board board, Dictionary dictionary) {
    if (!isMoveStartPositionValid(board, move.getMoveStartPosition())) {
      return false;
    }

    if (move.isFirstMove() && !isBoardStartPositionIncludedInMove(move, board.getStartPosition())) {
      return false;
    }

    String word = buildWord(move, board);
    return word != null && (word.length() > move.getLetters().length() || move.isFirstMove())
        && dictionary.contains(word);
  }

  private static boolean isMoveStartPositionValid(Board board, Position moveStart) {
    return board.isPositionInBoard(moveStart)
        && board.getTileGrid()[moveStart.row()][moveStart.col()] == null;
  }

  private static boolean isBoardStartPositionIncludedInMove(Move move, Position startPosition) {
    int moveStartCol = move.getMoveStartPosition().col();
    int moveStartRow = move.getMoveStartPosition().row();
    int moveLength = move.getLetters().length();
    Direction moveDirection = move.getMoveDirection();

    if (moveDirection == Direction.HORIZONTAL) {
      return startPosition.col() >= moveStartCol &&
          startPosition.col() < moveStartCol + moveLength;
    } else {
      return startPosition.row() >= moveStartRow &&
          startPosition.row() < moveStartRow + moveLength;
    }
  }

  private static String buildWord(Move move, Board board) {
    String preWord = prependExistingLetters(move, board);
    String word = addNewLetters(move, board);

    if (word == null)
      return null;

    return preWord + word;
  }

  private static String prependExistingLetters(Move move, Board board) {
    StringBuilder word = new StringBuilder();

    BoardIterator.iterateBackwards(
        move.getMoveStartPosition(),
        move.getMoveDirection(),
        board,
        (tile) -> word.append(tile.getLetter()));

    return word.reverse().toString().toLowerCase(Locale.ROOT);
  }

  private static String addNewLetters(Move move, Board board) {
    StringBuilder word = new StringBuilder();
    String letters = move.getLetters();

    int iterateIndex = BoardIterator.iterateForward(
        move.getMoveStartPosition(),
        move.getMoveDirection(),
        board,
        (tile) -> word.append(tile.getLetter()),
        (position, i) -> {
          if (!isAdjacentFree(move.getMoveDirection(), position, board)) {
            return false;
          }
          word.append(letters.charAt(i));
          return true;
        },
        letters.length()
    );

    if (iterateIndex < letters.length())
      return null;

    String finalWord = word.toString().toLowerCase(Locale.ROOT);
    if (finalWord.length() < letters.length())
      return null;

    return finalWord;
  }

  private static boolean isAdjacentFree(Direction moveDirection, Position position,
                                        Board board) {
    int row = position.row();
    int col = position.col();
    int boardRows = board.getRows();
    int boardCols = board.getColumns();
    Tile[][] tileGrid = board.getTileGrid();

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
