package pij.game;

import pij.model.Board;
import pij.model.Dictionary;
import pij.model.Player;
import pij.model.TileBag;
import pij.util.BoardLoader;
import pij.util.DictionaryLoader;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 *
 * @author Yubeen Lee
 */
public class GameSetup {
  public static Game create() {
    Board board;

    GameUI.showGameBanner();

    if (GameUI.userChoseDefaultBoard()) {
      Path defaultBoardFilePath
          = Paths.get(GameConfig.RESOURCE_FOLDER_NAME, GameConfig.DEFAULT_BOARD_FILE_NAME);
      board = BoardLoader.loadBoard(defaultBoardFilePath);
      // if default board loading fails, no user retry possible
    } else {
      while (true) {
        String boardFileName = GameUI.promptBoardFileName();
        Path boardFilePath = Paths.get(GameConfig.RESOURCE_FOLDER_NAME, boardFileName);
        try {
          board = BoardLoader.loadBoard(boardFilePath);
          break;
        } catch (IllegalArgumentException e) {
          GameUI.showError_InvalidFile();
        }
      }
    }

    Path wordListFilePath = Paths.get(GameConfig.RESOURCE_FOLDER_NAME, GameConfig.WORD_LIST_FILE_NAME);
    Dictionary dictionary = DictionaryLoader.loadDictionary(wordListFilePath);

    TileBag tileBag = new TileBag();

    Player player1 = new Player(GameUI.userChoseHumanPlayer(0), tileBag);
    Player player2 = new Player(GameUI.userChoseHumanPlayer(1), tileBag);

    boolean isOpenGame = GameUI.userChoseOpenGame();

    return new Game(board, tileBag, dictionary, player1, player2, isOpenGame);
  }
}
