package pij.util;

import pij.model.Board;
import pij.model.Position;
import pij.model.Square;
import pij.model.SquareType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 *
 * @author Yubeen Lee
 */
public class BoardLoader {
  //TODO: clean up exception logic
  private static final String DEFAULT_BOARD_FILENAME = "DefaultBoard.txt";
  private static final String RESOURCE_FOLDER_NAME = "resources";

  public static Board loadBoard(String fileName) {
    Path filePath = Paths.get(RESOURCE_FOLDER_NAME, fileName);
    List<String> lines;

    try {
      lines = Files.readAllLines(filePath);
//      lines.forEach(System.out::println);
    } catch (IOException e) {
      throw new IllegalArgumentException("This is not a valid file: " + fileName);
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
      throw new IllegalArgumentException("Invalid board configuration.");
    }

    if (lines.size() != rows + 3) {
      throw new IllegalArgumentException("Invalid board configuration. Rows do not match line count.");
    }

    Position startPosition = Position.stringToPosition(lines.get(2));
    if (startPosition == null) {
      throw new IllegalArgumentException("Invalid board configuration. Start position is invalid.");
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
          throw new IllegalArgumentException("Invalid board configuration: missing closing bracket ']'");
        }
        try {
          value = Integer.parseInt(line.substring(i + 1, end));
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Invalid board configuration: invalid square value.");
        }
        board.putSquare(colIndex, rowIndex, new Square(value, SquareType.LETTER_PREMIUM));
        i = end + 1;
      } else if (c == '<') {
        end = line.indexOf('>', i + 1);
        if (end == -1) {
          throw new IllegalArgumentException("Invalid board configuration: missing closing bracket '>'");
        }
        try {
          value = Integer.parseInt(line.substring(i + 1, end));
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Invalid board configuration: invalid square value.");
        }
        board.putSquare(colIndex, rowIndex, new Square(value, SquareType.WORD_PREMIUM));
        i = end + 1;
      } else {
        throw new IllegalArgumentException("Invalid board configuration: invalid square format.");
      }
      colIndex++;
    }
    if (i != line.length() || colIndex != columns) {
      throw new IllegalArgumentException("Invalid board configuration: line length does not match column count.");
    }
  }

  public static Board loadBoard() {
    return loadBoard(DEFAULT_BOARD_FILENAME);
  }
}
