package pij.game;

import pij.model.*;
import pij.util.MoveExecutor;
import pij.util.MoveParser;
import pij.util.MoveValidator;

import java.util.ArrayList;
import java.util.List;

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

    if (players[currentPlayerIndex].isHuman())
      humanPlayerTurn();
    else
      computerPlayerTurn();
  }

  private void humanPlayerTurn() {
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

  private void computerPlayerTurn() {
    Player player = players[currentPlayerIndex];
    List<Tile> rack = player.getRack();

    Move move = null;
    List<String> stringList = getAllPossibleStringsFromRack(rack);

    for (String s : stringList) {
      System.out.println(s);
      for (int i = 0; i < board.getRows(); i++) {
        for (int j = 0; j < board.getColumns(); j++) {
          DirectedPosition directedPositionHorizontal
              = new DirectedPosition(new Position(j, i), Direction.HORIZONTAL, "");
          Move tmpMove = Move.place(s, directedPositionHorizontal);
          if (MoveValidator.isMoveValid(tmpMove, player, board, dictionary)) {
            move = tmpMove;
          }
          DirectedPosition directedPositionVertical
              = new DirectedPosition(new Position(j, i), Direction.VERTICAL, "");
          tmpMove = Move.place(s, directedPositionVertical);
          if (MoveValidator.isMoveValid(tmpMove, player, board, dictionary)) {
            move = tmpMove;
          }
        }
      }
    }
    if (move == null) {
      move = Move.pass();
    }

    GameUI.showMove(move);
    if (numOfPlaceMoveMade == 0) {
      move.setIsFirstMove(true);
    }
    handleMove(move);
  }

  //using three tiles for now
  private static List<String> getAllPossibleStringsFromRack(List<Tile> rack) {
    List<String> stringList = new ArrayList<>();

    int added = 0;
    for (int i = 0; i < rack.size() && added < 3; i++) {
      Tile tile = rack.get(i);
      if (!tile.isWildcard()) {
        stringList.add("" + tile.getLetter());
        added++;
      }
    }

    stringList.add(stringList.get(0) + stringList.get(1));
    stringList.add(stringList.get(0) + stringList.get(2));
    stringList.add(stringList.get(1) + stringList.get(0));
    stringList.add(stringList.get(1) + stringList.get(2));
    stringList.add(stringList.get(2) + stringList.get(0));
    stringList.add(stringList.get(2) + stringList.get(1));
    stringList.add(stringList.get(0) + stringList.get(1) + stringList.get(2));
    stringList.add(stringList.get(0) + stringList.get(2) + stringList.get(1));
    stringList.add(stringList.get(1) + stringList.get(0) + stringList.get(2));
    stringList.add(stringList.get(1) + stringList.get(2) + stringList.get(0));
    stringList.add(stringList.get(2) + stringList.get(0) + stringList.get(1));
    stringList.add(stringList.get(2) + stringList.get(1) + stringList.get(0));
    return stringList;
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
