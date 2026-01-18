package pij.model;

import org.junit.jupiter.api.Test;

/**
 *
 *
 * @author Yubeen Lee
 */
public class PlayerTest {
  @Test
  void testPlayerCreation() {
    Player player = new Player(true, new TileBag());
    player.showRack();
  }
}
