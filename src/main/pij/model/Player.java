package pij.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author Yubeen Lee
 */
public class Player {
  public static final int MAX_RACK_SIZE = 7;
  private final List<Tile> rack = new ArrayList<>();
  private int score;
  private boolean isHuman;

  public Player(boolean isHuman, TileBag tileBag) {
    score = 0;
    for (int i = 0; i < MAX_RACK_SIZE; i++) {
      rack.add(tileBag.drawTile());
    }
  }

  public int getScore() {
    return score;
  }

  public boolean isHuman() {
    return isHuman;
  }

  public void showRack() {
    for (int i = 0; i < rack.size(); i++) {
      System.out.print(rack.get(i).toBracketedString());
      if (i < rack.size() - 1) {
        System.out.print(", ");
      }
    }
    System.out.println();
  }
}
