package pij.util;

import pij.model.Position;

public interface EmptyTileHandler {
  boolean handle(Position position, int index);
}
