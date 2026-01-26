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
