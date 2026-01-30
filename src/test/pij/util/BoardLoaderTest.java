package pij.util;

import org.junit.jupiter.api.Test;
import pij.game.GameConfig;
import pij.game.GameUI;
import pij.model.Board;
import pij.model.Tile;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 *
 * @author Yubeen Lee
 */
public class BoardLoaderTest {
  @Test
  void testLoadBoard_Default() {
    Path defaultBoardFilePath
        = Paths.get(GameConfig.RESOURCE_FOLDER_NAME, GameConfig.DEFAULT_BOARD_FILE_NAME);
    Board board = BoardLoader.loadBoard(defaultBoardFilePath);
    board.printSquareGrid();
    board.printTileGrid();
    GameUI.showBoard(board);
    board.putTile(3, 5, new Tile('A', 1));
    GameUI.showBoard(board);
  }

  @Test
  void testLoadBoard_NonExistingFile() {
    Path filePath = Paths.get(GameConfig.RESOURCE_FOLDER_NAME, "nonexisting.txt");
    Throwable exception = assertThrows(IllegalArgumentException.class,
        () -> {
          Board board = BoardLoader.loadBoard(filePath);
        });
    System.out.println(exception.getMessage());
  }
  //TODO: Add tests for invalid board configurations - col,row,startPosition
}
