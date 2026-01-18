package pij.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author Yubeen Lee
 */
public class TileBag {
  private final List<Tile> tiles = new ArrayList<>();
  private static final Map<Character, TileInfo> TILE_MAP = Map.ofEntries(
      Map.entry('A', new TileInfo(1, 8)),
      Map.entry('B', new TileInfo(3, 2)),
      Map.entry('C', new TileInfo(4, 2)),
      Map.entry('D', new TileInfo(2, 4)),
      Map.entry('E', new TileInfo(2, 9)),
      Map.entry('F', new TileInfo(4, 3)),
      Map.entry('G', new TileInfo(3, 4)),
      Map.entry('H', new TileInfo(4, 3)),
      Map.entry('I', new TileInfo(1, 9)),
      Map.entry('J', new TileInfo(11, 1)),
      Map.entry('K', new TileInfo(6, 2)),
      Map.entry('L', new TileInfo(1, 4)),
      Map.entry('M', new TileInfo(3, 2)),
      Map.entry('N', new TileInfo(1, 7)),
      Map.entry('O', new TileInfo(1, 7)),
      Map.entry('P', new TileInfo(3, 2)),
      Map.entry('Q', new TileInfo(12, 1)),
      Map.entry('R', new TileInfo(1, 6)),
      Map.entry('S', new TileInfo(1, 4)),
      Map.entry('T', new TileInfo(1, 5)),
      Map.entry('U', new TileInfo(1, 5)),
      Map.entry('V', new TileInfo(4, 2)),
      Map.entry('W', new TileInfo(4, 2)),
      Map.entry('X', new TileInfo(9, 1)),
      Map.entry('Y', new TileInfo(5, 2)),
      Map.entry('Z', new TileInfo(9, 1)),
      Map.entry('_', new TileInfo(8, 2))
  );


  public TileBag() {
    TILE_MAP.forEach((letter, info) -> {
      for (int i = 0; i < info.count(); i++) {
        tiles.add(new Tile(letter, info.value()));
      }
    });
    Collections.shuffle(tiles);
  }

  public int size() {
    return tiles.size();
  }

  public Tile drawTile() {
    if (tiles.isEmpty()) {
      return null;
    }
    return tiles.removeLast();
  }
}
