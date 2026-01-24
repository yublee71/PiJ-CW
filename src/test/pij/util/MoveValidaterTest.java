package pij.util;

import org.junit.jupiter.api.Test;
import pij.model.Tile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
  void testIsLettersInRack_valid_oneLetter() {
    List<Tile> rack = new ArrayList<>();

    rack.add(new Tile('A', 1));

    assertTrue(MoveValidator.isLettersInRack(rack, "A"));
  }

  @Test
  void testIsLettersInRack_valid_oneWildcard() {
    List<Tile> rack = new ArrayList<>();

    rack.add(new Tile('_', 1));

    assertTrue(MoveValidator.isLettersInRack(rack, "a"));
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

  @Test
  void testIsLettersInRack_invalid_nullRack() {
    assertThrows(NullPointerException.class, () ->
        MoveValidator.isLettersInRack(null, "ABc"));
  }

  @Test
  void testIsLettersInRack_invalid_nullLetters() {
    List<Tile> rack = new ArrayList<>();

    rack.add(new Tile('A', 1));
    rack.add(new Tile('B', 1));
    rack.add(new Tile('C', 1));

    assertThrows(NullPointerException.class, () ->
        MoveValidator.isLettersInRack(rack, null));
  }

  @Test
  void testIsLettersInRack_invalid_emptyRack() {
    List<Tile> rack = new ArrayList<>();
    assertFalse(MoveValidator.isLettersInRack(rack, "A"));
  }
}
