package pij.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 *
 * @author Yubeen Lee
 */
public class TileTest {
  @Test
  void testTileCreation_Normal() {
    Tile tile = new Tile('A', 1);
    assertEquals("A1", tile.toString());
  }

  @Test
  void testTileCreation_Wildcard() {
    Tile tile = new Tile('_', 8);
    assertEquals("_8", tile.toString());
  }

  @Test
  void testTileCreation_InvalidCharacter() {
    assertThrows(IllegalArgumentException.class,
        () -> {
          Tile tile = new Tile('1', 3);
        });
  }

  @Test
  void testTileCreation_InvalidValue() {
    assertThrows(IllegalArgumentException.class,
        () -> {
          Tile tile = new Tile('A', 0);
        });
  }

  @Test
  void testSetChosenLetter_Wildcard() {
    Tile tile = new Tile('_', 8);
    tile.setChosenLetter('a');
    assertEquals("a8", tile.toString());
  }

  @Test
  void testSetChosenLetter_NonWildcard() {
    Tile tile = new Tile('A', 1);
    assertThrows(IllegalStateException.class,
        () -> {
          tile.setChosenLetter('a');
        });
  }

  @Test
  void testSetChosenLetter_Wildcard_Reassign() {
    Tile tile = new Tile('_', 8);
    tile.setChosenLetter('a');
    assertThrows(IllegalStateException.class,
        () -> {
          tile.setChosenLetter('b');
        });
  }

  @Test
  void testSetChosenLetter_Wildcard_Null() {
    Tile tile = new Tile('_', 8);
    assertThrows(IllegalArgumentException.class,
        () -> {
          tile.setChosenLetter(null);
        });
  }
}
