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
    //TODO: tileBag null check
    score = 0;
    for (int i = 0; i < MAX_RACK_SIZE; i++) {
      //TODO: check if it is possible to add
      rack.add(tileBag.drawTile());
    }
    this.isHuman = true; //TODO: reflect argument value
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

  public List<Tile> getRack() {
    return rack;
  }

  public Tile removeTileFromRack(char c) {
    for (int i = 0; i < rack.size(); i++) {
      if (c >= 'a' && c <= 'z' && rack.get(i).getLetter() == '_') {
        rack.get(i).setChosenLetter(c);
        return rack.remove(i);
      }
      if (c == rack.get(i).getLetter()) {
        return rack.remove(i);
      }
    }
    return null;
  }

  public void addTileToRack(Tile tile) {
    if (tile != null) {
      rack.add(tile);
    }
  }

  public void addScore(int score) {
    this.score += score;
  }
}
