package pij.model;

/**
 * Stores information about a tile.
 * The value represents the point value of the tile,
 * and the count represents how many of this tile exist in the tile bag.
 *
 * @author Yubeen Lee
 */
public record TileInfo(int value, int count) {
}
