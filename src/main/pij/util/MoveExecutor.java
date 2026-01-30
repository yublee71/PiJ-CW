package pij.util;

import pij.model.*;

/**
 * Applies the given move to the board and updates the player's score and rack.
 * The move is assumed to be valid
 * (i.e., has passed {@link MoveValidator#isMoveValid(Move, Player, Board, Dictionary)}).
 * Pass moves are ignored.
 *
 * @author Yubeen Lee
 */
public class MoveExecutor {
  public static void applyMove(Move move, Board board, Player player, TileBag tileBag) {
    if (move.getMoveType() == MoveType.PASS) {
      return;
    }

    int[] score = {0};
    int[] premiumWordFactor = {1};
    Tile[][] tileGrid = board.getTileGrid();
    Square[][] squareGrid = board.getSquareGrid();
    String letters = move.getLetters();

    BoardIterator.iterateBackwards(move.getMoveStartPosition(),
        move.getMoveDirection(),
        board,
        (tile) -> score[0] += (tile.getValue()));

    BoardIterator.iterateForward(move.getMoveStartPosition(), move.getMoveDirection(), board,
        (tile) -> score[0] += (tile.getValue()),
        ((position, i) -> {
          Square square = squareGrid[position.row()][position.col()];
          int squareValue = square.value();
          SquareType squareType = square.type();
          Tile tile = player.removeTileFromRack(letters.charAt(i));
          if (tile.isWildcard()) {
            tile.setChosenLetter(letters.charAt(i));
          }
          int tileValue = tile.getValue();
          int premiumLetterFactor = 1;

          if (squareType == SquareType.WORD_PREMIUM) {
            premiumWordFactor[0] *= squareValue;
          } else if (squareType == SquareType.LETTER_PREMIUM) {
            premiumLetterFactor = squareValue;
          }
          score[0] += tileValue * premiumLetterFactor;
          tileGrid[position.row()][position.col()] = tile;
          player.addTileToRack(tileBag.drawTile());
          return true;
        }),
        letters.length());

    player.addScore(score[0] * premiumWordFactor[0] + (letters.length() == 7 ? 60 : 0));
  }
}
