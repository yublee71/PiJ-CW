package pij.util;

import pij.model.Board;
import pij.model.DirectedPosition;
import pij.model.Move;
import pij.model.Player;

/**
 *
 *
 * @author Yubeen Lee
 */
public class MoveValidator {
  public static Move moveValidate(Board board, Player player, String moveString) {
    if (moveString == null || moveString.isEmpty())
      return null;

    //if no comma found return null
    int commaIndex;
    commaIndex = moveString.indexOf(',', 0);
    if (commaIndex == -1)
      return null;

    //if first part or second part is invalid return null
    String letters;
    String movePositionString;

    letters = moveString.substring(0, commaIndex);
    movePositionString = moveString.substring(commaIndex + 1);

    if (letters.isEmpty() || movePositionString.isEmpty()) {
      return null;
    }

    //TODO: letter validation with player rack


    DirectedPosition directedPosition = DirectedPosition.stringToDirectedPosition(movePositionString);
    if (directedPosition == null || !board.isPositionInBoard(directedPosition.getStartPosition())) {
      return null;
    }

    return new Move(null, directedPosition);
  }
}
