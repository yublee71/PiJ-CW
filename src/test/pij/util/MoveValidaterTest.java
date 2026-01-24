package pij.util;

import org.junit.jupiter.api.Test;
import pij.model.Tile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 *
 * @author Yubeen Lee
 */
public class MoveValidaterTest {
  @Test
  void testIsLettersInRack_valid() {
    List<Tile> rack = new ArrayList<>();

    rack.add(new Tile('A', 1));
    rack.add(new Tile('B', 1));
    rack.add(new Tile('C', 1));

    assertTrue(MoveValidator.isLettersInRack(rack, "ABC"));
  }

  @Test
  void testIsLettersInRack_valid_wildcard() {
    List<Tile> rack = new ArrayList<>();

    rack.add(new Tile('A', 1));
    rack.add(new Tile('B', 1));
    rack.add(new Tile('_', 1));

    assertTrue(MoveValidator.isLettersInRack(rack, "ABc"));
  }

  @Test
  void testIsLettersInRack_invalid() {
    List<Tile> rack = new ArrayList<>();

    rack.add(new Tile('A', 1));
    rack.add(new Tile('B', 1));
    rack.add(new Tile('C', 1));

    assertFalse(MoveValidator.isLettersInRack(rack, "ABCD"));
  }

  @Test
  void testIsLettersInRack_invalid_wildcard() {
    List<Tile> rack = new ArrayList<>();

    rack.add(new Tile('A', 1));
    rack.add(new Tile('B', 1));
    rack.add(new Tile('C', 1));

    assertFalse(MoveValidator.isLettersInRack(rack, "ABc"));
  }
}
