package pij.util;

import pij.model.DirectedPosition;
import pij.model.Move;

/**
 * Utility class for parsing a string representation of {@code Move}.
 *
 * @author Yubeen Lee
 */
public class MoveParser {
  /**
   * Parses a string representation of a move to new {@code Move}.
   * Expected format: word,square
   * (e.g. "HI,f4" for a downward move and "HI,4f" for a rightward move)
   * If both the word and position are empty (","), returns a pass move.
   * Otherwise, returns a place move.
   *
   * @param moveString the string to Parse
   * @return a {@code Move} if valid, or null if the format is invalid
   */
  public static Move parse(String moveString) {
    if (moveString == null || moveString.isEmpty())
      return null;

    int commaIndex;
    commaIndex = moveString.indexOf(',');
    if (commaIndex == -1)
      return null;

    String letters;
    String movePositionString;

    letters = moveString.substring(0, commaIndex);
    movePositionString = moveString.substring(commaIndex + 1);

    if (letters.isEmpty() && movePositionString.isEmpty()) {
      return Move.pass();
    }

    if (letters.isEmpty() || movePositionString.isEmpty()) {
      return null;
    }

    DirectedPosition directedPosition = DirectedPosition.stringToDirectedPosition(movePositionString);
    if (directedPosition == null) {
      return null;
    }

    return Move.place(letters, directedPosition);
  }
}
