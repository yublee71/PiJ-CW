package pij.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 *
 * @author Yubeen Lee
 */
public class TileBagTest {
  @Test
  void testTileBagSize() {
    TileBag tileBag = new TileBag();
    assertEquals(100, tileBag.size());
  }
}
