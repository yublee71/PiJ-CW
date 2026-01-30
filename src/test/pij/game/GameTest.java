package pij.game;

import org.junit.jupiter.api.Test;
import pij.model.Player;
import pij.model.TileBag;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 *
 * @author Yubeen Lee
 */
public class GameTest {
  @Test
  public void getAllPossibleStringsFromRack() {
    Player player = new Player(false, new TileBag());
    List<String> list = Game.getAllPossibleStringsFromRack(player.getRack());
    for (String s : list) {
      System.out.println(s);
    }
    assertEquals(13699, list.size());
  }
}
