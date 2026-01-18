package pij.util;

import pij.model.Board;
import pij.model.Position;

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
  public static Board loadBoard(String fileName) {
    Path filePath = Paths.get("resources", fileName);
    List<String> lines;

    try {
      lines = Files.readAllLines(filePath);
      lines.forEach(System.out::println);
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

    Position startPosition = getPosition(lines.get(2), columns, rows);

    return new Board(columns, rows, startPosition);
  }

  private static Position getPosition(String startPositionString, int columns, int rows) {
    if (startPositionString.length() < 2) {
      throw new IllegalArgumentException("Start position must be at least 2 characters long.");
    }

    char startColChar = startPositionString.charAt(0);
    int startColIndex = startColChar - 'a';

    int startRowIndex;
    try {
      startRowIndex = Integer.parseInt(startPositionString.substring(1)) - 1;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid row format in start position: " + startPositionString);
    }

    if (startColIndex < 0 || startColIndex >= columns) {
      throw new IllegalArgumentException("Invalid start position column: " + startColChar);
    }
    if (startRowIndex < 0 || startRowIndex >= rows) {
      throw new IllegalArgumentException("Invalid start position row: " + startRowIndex);
    }

    return new Position(startColIndex, startRowIndex);
  }

  public static Board loadBoard() {
    return loadBoard("defaultBoard.txt");
  }
}
