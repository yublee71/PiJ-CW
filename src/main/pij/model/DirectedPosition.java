package pij.model;

/**
 *
 *
 * @author Yubeen Lee
 */
public class DirectedPosition {
  Position startPosition;
  Direction direction;
  String originalString;

  public static DirectedPosition stringToDirectedPosition(String s) {
    Position startPosition = null;
    Direction direction = null;

    if (s == null || s.isEmpty()) {
      return null;
    }
    if (s.length() < 2) {
      return null;
    }

    if (s.charAt(0) >= 'a' && s.charAt(0) <= 'z') {
      startPosition = Position.stringToPosition(s);
      direction = Direction.VERTICAL;
    } else if (s.charAt(s.length() - 1) >= 'a' && s.charAt(s.length() - 1) <= 'z') {
      startPosition = Position.stringToPosition(s.substring(s.length() - 1)
          + s.substring(0, s.length() - 1));
      direction = Direction.HORIZONTAL;
    }

    if (startPosition == null)
      return null;
    else
      return new DirectedPosition(startPosition, direction, s);
  }

  public DirectedPosition(Position startPosition, Direction direction, String s) {
    this.startPosition = startPosition;
    this.direction = direction;
    this.originalString = s;
  }

  public Position getStartPosition() {
    return startPosition;
  }

  public Direction getDirection() {
    return direction;
  }

  @Override
  public String toString() {
    return originalString;
  }
}
