package pij.util;

import org.junit.jupiter.api.Test;
import pij.model.Board;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 *
 * @author Yubeen Lee
 */
public class BoardLoaderTest {
  @Test
  void testLoadBoard_Default() {
    Board board = BoardLoader.loadBoard();
  }

  @Test
  void testLoadBoard_FileName() {
    Board board = BoardLoader.loadBoard("defaultBoard.txt");
  }

  @Test
  void testLoadBoard_EmptyFileName() {
    Throwable exception = assertThrows(IllegalArgumentException.class,
        () -> {
          Board board = BoardLoader.loadBoard("");
        });
    System.out.println(exception.getMessage());
  }

  @Test
  void testLoadBoard_NonExistingFile() {
    Throwable exception = assertThrows(IllegalArgumentException.class,
        () -> {
          Board board = BoardLoader.loadBoard("nonexisting.txt");
        });
    System.out.println(exception.getMessage());
  }
}
