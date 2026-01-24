package pij.util;

import pij.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 *
 * @author Yubeen Lee
 */
public class MoveValidator {
  public static boolean isMoveValid(Move move, Player player, Board board) {
    return isLettersInRack(player.getRack(), move.getLetters()) &&
        isPlacementValid(move, board) &&
        isWordValid(move, board);
//    System.out.println("Player 1 score: " + players[0].getScore());
//    System.out.println("Player 2 score: " + players[1].getScore());
  }

  public static boolean isLettersInRack(List<Tile> rack, String letters) {
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

  public static boolean isPlacementValid(Move move, Board board) {
    Objects.requireNonNull(move, "Move cannot be null");
    Objects.requireNonNull(board, "Board cannot be null");

    Direction moveDirection = move.getDirectedPosition().getDirection();
    Position moveStartPosition = move.getDirectedPosition().getStartPosition();
    int lettersLength = move.getLetters().length();
    Tile[][] tileGrid = board.getTileGrid();

    if (moveDirection == Direction.HORIZONTAL) {
      if (moveStartPosition.col() + lettersLength > board.getColumns()) {
        return false;
      }
      for (int i = 0; i < lettersLength; i++) {
        if (tileGrid[moveStartPosition.row()][i] != null)
          return false;
        //TODO: test
      }
    }

    if (moveDirection == Direction.VERTICAL) {
      if (moveStartPosition.row() + lettersLength > board.getRows()) {
        return false;
      }
      for (int i = 0; i < lettersLength; i++) {
        if (tileGrid[i][moveStartPosition.col()] != null)
          return false;
        //TODO: test
      }
    }
    return true;
  }

  public static boolean isWordValid(Move move, Board board) {
    //TODO: word validation
    return true;
  }
}
