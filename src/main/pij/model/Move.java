package pij.model;

/**
 *
 *
 * @author Yubeen Lee
 */
public class Move {
  private final String letters;
  private final Position startPosition;

  public Move(String letters, Position startPosition) {
    this.letters = letters;
    this.startPosition = startPosition;
  }

  @Override
  public String toString() {
    if (letters == null || startPosition == null)
      return ",";
    return letters + ',' + startPosition;
  }
}
