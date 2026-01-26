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

  public static boolean isPlacementValid(Move move, Board board, Dictionary dictionary) {
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
    //if start position is out of board or already occupied return false
    if (!board.isPositionInBoard(new Position(col, row)) || tileGrid[row][col] != null)
      return false;
    //TODO: check start position for the first move
    //TODO: check the other direction
    boolean tileExists = false;

    Tile currentTile;
    int i = 1;
    while ((moveDirection == Direction.HORIZONTAL && col - i < board.getColumns()) && col - i >= 0 ||
        (moveDirection == Direction.VERTICAL && row - i < board.getRows() && row - i >= 0)) {
      if (moveDirection == Direction.HORIZONTAL)
        currentTile = tileGrid[row][col - i];
      else {
        currentTile = tileGrid[row - i][col];
      }
      if (currentTile != null) {
        tileExists = true;
        word.append(currentTile.getLetter());
        //TODO:reflect wildcard
      } else {
        break;
      }
      i++;
    }


    int j = 0;
    while (j < lettersLength && col < board.getColumns() && row < board.getRows()) {
      if (tileGrid[row][col] == null) {
        word.append(letters.charAt(j));
        j++;
      } else {
        tileExists = true;
        word.append(tileGrid[row][col].getLetter());
      }
      if (moveDirection == Direction.HORIZONTAL) {
        col++;
      } else if (moveDirection == Direction.VERTICAL) {
        row++;
      }
    }

    int k = 1;
    while ((moveDirection == Direction.HORIZONTAL && col + k < board.getColumns()) && col + k >= 0 ||
        (moveDirection == Direction.VERTICAL && row + k < board.getRows() && row + k >= 0)) {
      if (moveDirection == Direction.HORIZONTAL)
        currentTile = tileGrid[row][col + k];
      else {
        currentTile = tileGrid[row + k][col];
      }
      if (currentTile != null) {
        tileExists = true;
        word.append(currentTile.getLetter());
      } else {
        break;
      }
      k++;
    }


    String finalWord = word.toString().toLowerCase(Locale.ROOT);
    //TODO: test
    //TODO: word validation
    System.out.println(finalWord);
    return j == lettersLength && (tileExists || move.isFirstMove()) && dictionary.contains(finalWord);
  }
}
