package pij.model;

/**
 *
 *
 * @author Yubeen Lee
 */
public class Move {
  private final String letters;
  private final DirectedPosition directedPosition;

  public Move(String letters, DirectedPosition directedPosition) {
    this.letters = letters;
    this.directedPosition = directedPosition;
  }

  @Override
  public String toString() {
    if (letters == null || directedPosition == null)
      return "Pass Move!";
    return "Letters: " + letters + " at position " + directedPosition;
  }

  public String getLetters() {
    return letters;
  }

}
