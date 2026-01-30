package pij.model;

/**
 * Represents a move made by a player.
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

  public String getLetters() {
    return letters;
  }

  public DirectedPosition getDirectedPosition() {
    return directedPosition;
  }

  public Position getMoveStartPosition() {
    return directedPosition.getStartPosition();
  }

  public Direction getMoveDirection() {
    return directedPosition.getDirection();
  }

  public MoveType getMoveType() {
    return moveType;
  }

  public boolean isFirstMove() {
    return isFirstMove;
  }

  public void setIsFirstMove(boolean isFirstMove) {
    this.isFirstMove = isFirstMove;
  }

  @Override
  public String toString() {
    if (moveType == MoveType.PASS)
      return "Pass Move!";
    return "Letters: " + letters + " at position " + directedPosition;
  }
}
