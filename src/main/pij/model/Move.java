package pij.model;

/**
 *
 *
 * @author Yubeen Lee
 */
public class Move {
  private final String letters;
  private final DirectedPosition directedPosition;
  private boolean isFirstMove;

  public Move(String letters, DirectedPosition directedPosition) {
    this.letters = letters;
    this.directedPosition = directedPosition;
    this.isFirstMove = false;
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

  public DirectedPosition getDirectedPosition() {
    return directedPosition;
  }

  public void setIsFirstMove(boolean isFirstMove) {
    this.isFirstMove = isFirstMove;
  }

  public boolean isFirstMove() {
    return isFirstMove;
  }
}
