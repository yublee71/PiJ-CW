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
    // if pass return true
    if (move.getLetters() == null && move.getDirectedPosition() == null)
      return true;
    return isLettersInRack(player.getRack(), move.getLetters()) &&
        isPlacementValid(move, board) &&
        isWordValid(move, board);
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
    String letters = move.getLetters();
    int lettersLength = move.getLetters().length();
    Tile[][] tileGrid = board.getTileGrid();

    StringBuilder word = new StringBuilder();
    int col = moveStartPosition.col();
    int row = moveStartPosition.row();
    int i = 0;
    if (moveDirection == Direction.HORIZONTAL) {
      while (i < lettersLength && col < board.getColumns()) {
        if (tileGrid[row][col] == null) {
          word.append(letters.charAt(i));
          i++;
        } else {
          word.append(tileGrid[row][col].getLetter());
        }
        col++;
      }
    } else if (moveDirection == Direction.VERTICAL) {
      while (i < lettersLength && row < board.getRows()) {
        if (tileGrid[row][col] == null) {
          word.append(letters.charAt(i));
          i++;
        } else {
          word.append(tileGrid[row][col].getLetter());
        }
        row++;
      }
      //TODO: test
    }
    //TODO: word validation
//    System.out.println(word);
    return i == lettersLength;
  }

  public static boolean isWordValid(Move move, Board board) {
    //TODO: word validation
    return true;
  }
}
