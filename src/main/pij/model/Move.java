package pij.model;

/**
 *
 *
 * @author Yubeen Lee
 */
public class Move {
  private final MoveType moveType;
  private final String letters;
  private final DirectedPosition directedPosition;
  private boolean isFirstMove;

  private Move(MoveType moveType, String letters, DirectedPosition directedPosition) {
    this.moveType = moveType;
    this.letters = letters;
    this.directedPosition = directedPosition;
    this.isFirstMove = false;
  }

  public static Move place(String letters, DirectedPosition directedPosition) {
    return new Move(MoveType.PLACE, letters, directedPosition);
  }

  public static Move pass() {
    return new Move(MoveType.PASS, null, null);
  }

  @Override
  public String toString() {
    if (moveType == MoveType.PASS)
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

  public MoveType getMoveType() {
    return moveType;
  }
}
