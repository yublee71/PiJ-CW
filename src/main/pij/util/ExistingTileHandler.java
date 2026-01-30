package pij.util;

import pij.model.Tile;

/**
 * Functional interface for handling an existing {@link Tile} on the board.
 * Implementations define what to do with a tile that is already placed.
 */
public interface ExistingTileHandler {
  /**
   * Performs an action on the given tile.
   *
   * @param tile the {@link Tile} to handle
   */
  void handle(Tile tile);
}
