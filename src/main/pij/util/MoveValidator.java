package pij.util;

import pij.model.Board;
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
    String startPositionString;

    letters = moveString.substring(0, commaIndex);
    startPositionString = moveString.substring(commaIndex + 1);

    if (letters.isEmpty() || startPositionString.isEmpty()) {
      return null;
    }

    //TODO: letter validation with player rack
    //TODO: startPosition validation

    return new Move(null, null);
  }
}
