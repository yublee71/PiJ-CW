package pij.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 *
 * @author Yubeen Lee
 */
public class BoardTest {
  @Test
  public void testBoardCreation() {
    Board board = new Board(16, 14, "d14");
    assertEquals(3, board.getStartPosition().getCol());
    assertEquals(13, board.getStartPosition().getRow());
  }
}
