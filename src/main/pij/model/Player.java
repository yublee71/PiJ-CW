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
  private final boolean isHuman;

  public Player(boolean isHuman, TileBag tileBag) {
    if (tileBag == null) {
      throw new IllegalStateException("Cannot create a player when the tile bag is null");
    }

    score = 0;
    for (int i = 0; i < MAX_RACK_SIZE; i++) {
      Tile tile = tileBag.drawTile();
      if (tile != null) {
        rack.add(tile);
      }
    }
    this.isHuman = isHuman;
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

  public Tile removeTileFromRack(char charToRemove) {
    for (int i = 0; i < rack.size(); i++) {
      Tile tile = rack.get(i);
      if (charToRemove >= 'a' && charToRemove <= 'z' && tile.isWildcard()
          || charToRemove == tile.getLetter()) {
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
