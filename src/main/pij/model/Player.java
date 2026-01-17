package pij.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author Yubeen Lee
 */
public class Player {
  private final List<Tile> rack = new ArrayList<>();
  private int score;
  private boolean isHuman;

  public Player(boolean isHuman) {
    score = 0;
  }

  public int getScore() {
    return score;
  }

  public boolean isHuman() {
    return isHuman;
  }
}
