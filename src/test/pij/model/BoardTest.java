package pij.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 *
 * @author Yubeen Lee
 */
public class BoardTest {
  @Test
  public void testBoardCreation() {
    Board board = new Board(16, 14, new Position(3, 13));
    assertEquals(3, board.getStartPosition().getCol());
    assertEquals(13, board.getStartPosition().getRow());
  }

  @Test
  public void testBoardCreation_InvalidStartPosition_ColTooLow() {
    Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
      new Board(16, 14, new Position(-1, 13));
    });
    System.out.println(exception.getMessage());
  }

  @Test
  public void testBoardCreation_InvalidStartPosition_ColTooHigh() {
    Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
      new Board(16, 14, new Position(16, 13));
    });
    System.out.println(exception.getMessage());
  }

  @Test
  public void testBoardCreation_InvalidStartPosition_RowTooLow() {
    Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
      new Board(16, 14, new Position(3, -1));
    });
    System.out.println(exception.getMessage());
  }

  @Test
  public void testBoardCreation_InvalidStartPosition_RowTooHigh() {
    Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
      new Board(16, 14, new Position(3, 14));
    });
    System.out.println(exception.getMessage());
  }
}
