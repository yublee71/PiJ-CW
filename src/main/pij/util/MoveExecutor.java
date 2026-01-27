package pij.util;

import pij.model.*;

/**
 *
 *
 * @author Yubeen Lee
 */
public class MoveExecutor {
  //TODO: handle the very first move
  public static void applyMove(Move move, Board board, Player[] players, int currentPlayer, TileBag tileBag) {
    if (move.getDirectedPosition() == null && move.getLetters() == null) {
      return;
    }

    Direction moveDirection = move.getDirectedPosition().getDirection();
    Position moveStartPosition = move.getDirectedPosition().getStartPosition();
    String letters = move.getLetters();
    int lettersLength = move.getLetters().length();
    Tile[][] tileGrid = board.getTileGrid();
    Square[][] squareGrid = board.getSquareGrid();

    int col = moveStartPosition.col();
    int row = moveStartPosition.row();
    int j = 0;
    int premiumWordFactor = 1;
    int premiumLetterFactor = 1;
    int score = 0;
    Square currentSquare;

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
        score += currentTile.getValue();
      } else {
        break;
      }
      i++;
    }


    while (j < lettersLength && col < board.getColumns() && row < board.getRows()) {
      if (tileGrid[row][col] == null) {
        tileGrid[row][col] = players[currentPlayer].removeTileFromRack(letters.charAt(j));
        players[currentPlayer].addTileToRack(tileBag.drawTile());
        currentSquare = squareGrid[row][col];
        if (currentSquare.getType() == SquareType.WORD_PREMIUM) {
          premiumWordFactor *= currentSquare.getValue();
        } else if (currentSquare.getType() == SquareType.LETTER_PREMIUM) {
          premiumLetterFactor = currentSquare.getValue();
        } else {
          premiumLetterFactor = 1;
        }
        j++;
      }
      score += tileGrid[row][col].getValue() * premiumLetterFactor;
      if (moveDirection == Direction.HORIZONTAL) {
        col++;
      } else {
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
        score += currentTile.getValue();
      } else {
        break;
      }
      k++;
    }
    players[currentPlayer].addScore(score * premiumWordFactor + (lettersLength == 7 ? 60 : 0));
  }
}
