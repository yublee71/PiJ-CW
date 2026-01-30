package pij.util;

import pij.model.Board;
import pij.model.Position;
import pij.model.Square;
import pij.model.SquareType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Utility class for loading a scrabble board from a file.
 *
 * @author Yubeen Lee
 */
public class BoardLoader {
  private static final String INVALID_BOARD_CONFIG = "Invalid board configuration: ";

  /**
   * Loads a {@code Board} from the given file path.
   *
   * @param filePath the path to the board file
   * @return a {@code Board}
   * @throws IllegalArgumentException if the file cannot be loaded or
   *                                  if the board configuration is invalid.
   */
  public static Board loadBoard(Path filePath) {
    List<String> lines;

    try {
      lines = Files.readAllLines(filePath);
    } catch (IOException e) {
      throw new IllegalArgumentException("This is not a valid file.");
    }

    if (lines.size() < 3) {
      throw new IllegalArgumentException("File is too short. Board configuration is missing.");
    }

    int columns;
    int rows;
    try {
      columns = Integer.parseInt(lines.get(0));
      rows = Integer.parseInt(lines.get(1));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(INVALID_BOARD_CONFIG + "invalid column and/or row count.");
    }

    if (lines.size() != rows + 3) {
      throw new IllegalArgumentException(INVALID_BOARD_CONFIG + "Rows do not match line count.");
    }

    Position startPosition = Position.stringToPosition(lines.get(2));
    if (startPosition == null) {
      throw new IllegalArgumentException(INVALID_BOARD_CONFIG + "Start position is invalid.");
    }

    Board board = new Board(columns, rows, startPosition);

    for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
      String line = lines.get(rowIndex + 3);
      parseLine(board, line, rowIndex, columns);
    }

    return board;
  }

  private static void parseLine(Board board, String line, int rowIndex, int columns) {
    int colIndex = 0;
    int i = 0;
    while (i < line.length() && colIndex < columns) {
      char c = line.charAt(i);
      int end;
      int value;

      if (c == '.') {
        board.putSquare(colIndex, rowIndex, new Square(1, SquareType.STANDARD));
        i++;
      } else if (c == '[') {
        end = line.indexOf(']', i + 1);
        if (end == -1) {
          throw new IllegalArgumentException(INVALID_BOARD_CONFIG + "missing closing bracket ']'");
        }
        try {
          value = Integer.parseInt(line.substring(i + 1, end));
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException(INVALID_BOARD_CONFIG + "invalid square value.");
        }
        board.putSquare(colIndex, rowIndex, new Square(value, SquareType.LETTER_PREMIUM));
        i = end + 1;
      } else if (c == '<') {
        end = line.indexOf('>', i + 1);
        if (end == -1) {
          throw new IllegalArgumentException(INVALID_BOARD_CONFIG + "missing closing bracket '>'");
        }
        try {
          value = Integer.parseInt(line.substring(i + 1, end));
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException(INVALID_BOARD_CONFIG + "invalid square value.");
        }
        board.putSquare(colIndex, rowIndex, new Square(value, SquareType.WORD_PREMIUM));
        i = end + 1;
      } else {
        throw new IllegalArgumentException(INVALID_BOARD_CONFIG + "invalid square format.");
      }
      colIndex++;
    }
    if (i != line.length() || colIndex != columns) {
      throw new IllegalArgumentException(INVALID_BOARD_CONFIG + "line length does not match column count.");
    }
  }
}
