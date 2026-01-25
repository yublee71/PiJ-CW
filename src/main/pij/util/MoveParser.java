package pij.util;

import pij.model.DirectedPosition;
import pij.model.Move;

/**
 *
 *
 * @author Yubeen Lee
 */
public class MoveParser {
  public static Move parse(String moveString) {
    if (moveString == null || moveString.isEmpty())
      return null;

    //if no comma found return null
    int commaIndex;
    commaIndex = moveString.indexOf(',');
    if (commaIndex == -1)
      return null;

    //if first part or second part is invalid return null
    String letters;
    String movePositionString;

    letters = moveString.substring(0, commaIndex);
    movePositionString = moveString.substring(commaIndex + 1);

    //if both empty, the move is pass
    if (letters.isEmpty() && movePositionString.isEmpty()) {
      return Move.pass();
    }

    if (letters.isEmpty() || movePositionString.isEmpty()) {
      return null;
    }

    //TODO: letter validation with player rack and board


    DirectedPosition directedPosition = DirectedPosition.stringToDirectedPosition(movePositionString);
    if (directedPosition == null) {
      return null;
    }

    return Move.place(letters, directedPosition);
  }
}
