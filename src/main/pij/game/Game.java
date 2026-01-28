package pij.game;

import pij.model.*;
import pij.util.MoveExecutor;
import pij.util.MoveParser;
import pij.util.MoveValidator;

/**
 *
 *
 * @author Yubeen Lee
 */
public class Game {
  private final Board board;
  private final Dictionary dictionary;
  private final TileBag tileBag;
  private final Player[] players = new Player[2];
  private final int[] scoreBoard = new int[2];
  private int currentPlayerIndex;
  private final boolean isOpenGame;
  private int numOfConsecutivePass;
  private int numOfPlaceMoveMade;

  public Game(Board board, TileBag tileBag, Dictionary dictionary,
              Player player1, Player player2, boolean isOpenGame) {
    this.board = board;
    this.dictionary = dictionary;
    this.tileBag = tileBag;
    this.players[0] = player1;
    this.players[1] = player2;
    this.currentPlayerIndex = 0;
    this.isOpenGame = isOpenGame;
    this.numOfConsecutivePass = 0;
    this.numOfPlaceMoveMade = 0;
  }

  public void run() {
    while (!isGameOver()) {
      playOneTurn();
    }
    GameUI.showGameResult(updateScoreBoard());
  }

  private void playOneTurn() {
    //TODO: different display for computer player
    if (numOfConsecutivePass == 0) {
      GameUI.showBoard(board); //show board if previous move is not pass
    }

    GameUI.showRack(isOpenGame, players, currentPlayerIndex);

    while (true) {
      Move move = MoveParser.parse(GameUI.promptMove());

      if (move == null) {
        GameUI.showError_IllegalMoveFormat();
        continue;
      }

      GameUI.showMove(move);

      if (numOfPlaceMoveMade == 0) {
        move.setIsFirstMove(true);
      }

      if (MoveValidator.isMoveValid(move, players[currentPlayerIndex], board, dictionary)) {
        handleMove(move);
        break;
      } else {
        GameUI.showError_InvalidMove(move);
      }
    }
  }


  private void handleMove(Move move) {
    if (move.getMoveType() == MoveType.PASS) {
      numOfConsecutivePass++;
    } else {
      MoveExecutor.applyMove(move, board, players[currentPlayerIndex], tileBag);
      numOfConsecutivePass = 0;
      numOfPlaceMoveMade++;
      GameUI.showScore(updateScoreBoard());
    }
    updateCurrentPlayerIndex();
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
