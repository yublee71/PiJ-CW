package pij.util;

import pij.model.Position;

/**
 * Functional interface for handling an empty position on the board.
 * Implementations define what to do when an empty board position is encountered during iteration.
 * The handler can return false to stop the iteration early.
 */
public interface EmptyTileHandler {
  /**
   * Performs an action on the given empty position.
   *
   * @param position the position of the empty square on the board
   * @param index    the index of the letter in the word being placed
   * @return true to continue iteration, false to stop
   */
  boolean handle(Position position, int index);
}
