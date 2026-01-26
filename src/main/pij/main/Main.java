package pij.main;

import pij.game.Game;
import pij.game.GameSetup;

public class Main {
  public static void main() {
    Game game = GameSetup.create();
    game.run();
  }
}
