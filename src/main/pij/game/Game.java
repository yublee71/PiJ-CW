package pij.game;

import pij.model.*;
import pij.util.*;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 *
 * @author Yubeen Lee
 */
public class Game {
  private static final String ERR_INVALID_DEFAULT_BOARD =
      "The default board is invalid. Game exiting...";
  private static final String ERR_INVALID_FILE =
      "The file is not a valid file. ";

  private final Board board;
  private final Dictionary dictionary;
  private final TileBag tileBag;
  private final Player[] players = new Player[2];
  private final int[] scoreBoard = new int[2];
  private int currentPlayerIndex;
  private final boolean isOpenGame;
  private int numOfConsecutivePass;

  public Game() {
    GameUI.showGameBanner();

    Board tmpBoard = null;
    Path boardFilePath;
    boolean isDefaultBoard = GameUI.userChoseDefaultBoard();
    if (isDefaultBoard) {
      boardFilePath = Paths.get(GameConfig.RESOURCE_FOLDER_NAME, GameConfig.DEFAULT_BOARD_FILE_NAME);
      try {
        tmpBoard = BoardLoader.loadBoard(boardFilePath);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(ERR_INVALID_DEFAULT_BOARD, e);
      }
    } else {
      boolean isBoardLoaded = false;
      String fileName;
      while (!isBoardLoaded) {
        fileName = GameUI.promptBoardFileName();
        boardFilePath = Paths.get(GameConfig.RESOURCE_FOLDER_NAME, fileName);
        try {
          tmpBoard = BoardLoader.loadBoard(boardFilePath);
          isBoardLoaded = true;
        } catch (IllegalArgumentException e) {
          System.out.print(ERR_INVALID_FILE);
        }
      }
    }

    Path wordListFilePath = Paths.get(GameConfig.RESOURCE_FOLDER_NAME, GameConfig.WORD_LIST_FILE_NAME);
    this.dictionary = DictionaryLoader.loadDictionary(wordListFilePath);

    this.board = tmpBoard;
    this.tileBag = new TileBag();
    this.players[0] = new Player(GameUI.userChoseHumanPlayer(0), tileBag);
    this.players[1] = new Player(GameUI.userChoseHumanPlayer(1), tileBag);
    this.currentPlayerIndex = 0;
    this.isOpenGame = GameUI.userChoseOpenGame();
    this.numOfConsecutivePass = 0;
  }

  public void run() {
    int placeMoveCount = 0;
    Move move = null;
    while (!isGameOver()) {//TODO: check tile bag, pass twice
      //TODO: different display for computer player
      if (move == null || move.getMoveType() != MoveType.PASS) {
        GameUI.showBoard(board);
      }
      GameUI.showRack(isOpenGame, players, currentPlayerIndex);
      move = null;
      boolean isMoveValid = false;
      while (move == null || !isMoveValid) {
        move = MoveParser.parse(GameUI.promptMove());
        if (move == null) {
          System.out.println("Illegal move format");
        } else {
          System.out.print("The move is:    ");
          System.out.println(move);
          if (move.getMoveType() == MoveType.PASS) {
            numOfConsecutivePass++;
            updateCurrentPlayerIndex();
            isMoveValid = true;
          } else {
            if (placeMoveCount == 0) {
              move.setIsFirstMove(true);
            }
            isMoveValid = MoveValidator.isMoveValid(move, players[currentPlayerIndex], board, dictionary);
            if (!isMoveValid) {
              System.out.println("The board does not permit word " + move.getLetters() +
                  " at position " + move.getDirectedPosition() + ". Please try again.");
            } else {
              MoveExecutor.makeMove(move, board, players, currentPlayerIndex, tileBag);
              numOfConsecutivePass = 0;
              placeMoveCount++;
              GameUI.showScore(updateScoreBoard());
              updateCurrentPlayerIndex();
            }
          }
        }
      }
    }
    GameUI.showGameResult(updateScoreBoard());
  }

  private void updateCurrentPlayerIndex() {
    currentPlayerIndex = 1 - currentPlayerIndex;
  }

  private boolean isGameOver() {
    return numOfConsecutivePass >= 4 || tileBag.size() == 0;
  }

  private int[] updateScoreBoard() {
    scoreBoard[0] = players[0].getScore();
    scoreBoard[1] = players[1].getScore();
    return scoreBoard;
  }
}
