package pij.game;

import pij.model.*;
import pij.util.MoveExecutor;
import pij.util.MoveParser;
import pij.util.MoveValidator;

import java.util.ArrayList;
import java.util.Collections;
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
      if (move != null)
        break;
      for (int i = board.getRows() - 1; i >= 0 && move == null; i--) {
        for (int j = 0; j < board.getColumns(); j++) {
          Position position = new Position(j, i);
          char col = (char) ('a' + j);
          String row = Integer.toString((i + 1));
          Move tmpMoveHorizontal = Move.place(s, new DirectedPosition(position, Direction.HORIZONTAL, row + col));
          Move tmpMoveVertical = Move.place(s, new DirectedPosition(position, Direction.VERTICAL, col + row));
          if (numOfPlaceMoveMade == 0) {
            tmpMoveHorizontal.setIsFirstMove(true);
            tmpMoveVertical.setIsFirstMove(true);
          }
          if (MoveValidator.isMoveValid(tmpMoveHorizontal, player, board, dictionary)) {
            move = tmpMoveHorizontal;
            break;
          }
          if (MoveValidator.isMoveValid(tmpMoveVertical, player, board, dictionary)) {
            move = tmpMoveVertical;
            break;
          }
        }
      }
    }
    if (move == null) {
      move = Move.pass();
    }

    GameUI.showMove(move);
    handleMove(move);
  }

  public static List<String> getAllPossibleStringsFromRack(List<Tile> rack) {
    List<Character> letters = new ArrayList<>();

    for (Tile tile : rack) {
      if (!tile.isWildcard()) {
        letters.add(tile.getLetter());
      } else {
        letters.add('s');//computer player always uses the wildcard tile as 's' for simplification
      }
    }

    int size = letters.size();
    char[] sequence = new char[size];
    boolean[] visited = new boolean[size];

    List<String> result = new ArrayList<>();

    for (int len = 1; len <= size; len++) {
      dfs(0, len, letters, sequence, visited, result);
    }

    Collections.reverse(result);
    return result;
  }

  private static void dfs(int depth, int len, List<Character> letters, char[] sequence,
                          boolean[] visited, List<String> result) {
    if (depth == len) {
      result.add(new String(sequence, 0, len));
      return;
    }
    for (int i = 0; i < letters.size(); i++) {
      if (!visited[i]) {
        visited[i] = true;
        sequence[depth] = letters.get(i);
        dfs(depth + 1, len, letters, sequence, visited, result);
        visited[i] = false;
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
    return numOfConsecutivePass >= 4 ||
        (tileBag.size() == 0 && (players[0].getRack().isEmpty() || players[1].getRack().isEmpty()));
  }

  private int[] updateScoreBoard() {
    scoreBoard[0] = players[0].getScore();
    scoreBoard[1] = players[1].getScore();
    return scoreBoard;
  }
}
