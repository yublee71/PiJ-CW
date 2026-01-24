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

    //if s is not valid, return null
    if (s == null || s.isEmpty()) {
      return null;
    }
    if (s.length() < 2) {
      return null;
    }

    //if s has alphabet at the beginning, use string as it is to create position and direction is vertical
    if (s.charAt(0) >= 'a' && s.charAt(0) <= 'z') {
      startPosition = Position.stringToPosition(s);
      direction = Direction.VERTICAL;
    } else if (s.charAt(s.length() - 1) >= 'a' && s.charAt(s.length() - 1) <= 'z') {
      //if s has alphabet at the end, modify string to create position and direction is horizontal
      startPosition = Position.stringToPosition(s.substring(s.length() - 1) + s.substring(0, s.length() - 1));
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
