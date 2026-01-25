package pij.game;

import pij.model.*;
import pij.util.BoardLoader;
import pij.util.MoveParser;
import pij.util.MoveValidator;

import java.util.Scanner;

/**
 *
 *
 * @author Yubeen Lee
 */
public class Game {
  private static final String GAME_BANNER = """
      ============                     ============
      ============ S c r a e B B K l e ============
      ============                     ============
      """;
  private static final String OPTION_BOARD =
      "Would you like to _l_oad a board or use the _d_efault board?";
  private static final String OPTION_PLAYER1 =
      "Is Player 1 a _h_uman player or a _c_omputer player?";
  private static final String OPTION_PLAYER2 =
      "Is Player 2 a _h_uman player or a _c_omputer player?";
  private static final String OPTION_OPEN_CLOSE =
      "Would you like to play an _o_pen or a _c_losed game?";

  private static final String ERR_INVALID_DEFAULT_BOARD =
      "The default board is invalid. Game exiting...";
  private static final String ERR_INVALID_FILE =
      "The file is not a valid file. ";
  private static final String PROMPT_BOARD_FILE =
      "Please enter the file name of the board: ";

  private static final String PLAYER1_NAME = "Player 1";
  private static final String PLAYER2_NAME = "Player 2";


  private final Board board;
  private final TileBag tileBag;
  private final Player[] players;
  private int currentPlayer;
  private boolean isGameOver;
  private final boolean isOpenGame;

  public Game() {
    System.out.println(GAME_BANNER);

    Board tmpBoard = null;
    boolean isDefaultBoard = promptToSelectOption(OPTION_BOARD, 'l', 'd') == 'd';
    if (isDefaultBoard) {
      try {
        tmpBoard = BoardLoader.loadBoard();
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(ERR_INVALID_DEFAULT_BOARD, e);
      }
    } else {
      boolean isBoardLoaded = false;
      Scanner scan = new Scanner(System.in);
      String fileName;
      while (!isBoardLoaded) {
        System.out.print(PROMPT_BOARD_FILE);
        fileName = scan.nextLine();
        try {
          tmpBoard = BoardLoader.loadBoard(fileName);
          isBoardLoaded = true;
        } catch (IllegalArgumentException e) {
          System.out.print(ERR_INVALID_FILE);
        }
      }
    }
    this.board = tmpBoard;
    this.tileBag = new TileBag();
    this.players = new Player[2];
    this.players[0] = new Player(promptToSelectOption(OPTION_PLAYER1, 'h', 'c') == 'h', tileBag);
    this.players[1] = new Player(promptToSelectOption(OPTION_PLAYER2, 'h', 'c') == 'h', tileBag);
    this.currentPlayer = 0;
    this.isOpenGame = promptToSelectOption(OPTION_OPEN_CLOSE, 'o', 'c') == 'o';
    this.isGameOver = false;
  }

  public void run() {
    Scanner scan = new Scanner(System.in);
    String moveExplanation = """
        Please enter your move in the format: "word,square" (without the quotes)
        For example, for suitable tile rack and board configuration, a downward move
        could be "HI,f4" and a rightward move could be "HI,4f".
        
        In the word, upper-case letters are standard tiles and lower-case letters
        are wildcards.
        Entering "," passes the turn.
        """;
    while (!isGameOver) {//TODO: check tile bag, pass twice
      //TODO: different display for computer player
      board.showBoard();
      board.showStartPosition();
      if (isOpenGame) {
        System.out.println("OPEN GAME: " + otherPlayerName(currentPlayer) + "'s tiles:");
        System.out.print("OPEN GAME: ");
        players[otherPlayerIndex(currentPlayer)].showRack();
      }
      System.out.println("It's your turn, " + currentPlayerName(currentPlayer) + "! Your tiles:");
      players[currentPlayer].showRack();

      String moveString;
      Move move = null;
      boolean isMoveValid = false;
      while (move == null || !isMoveValid) {
        System.out.print(moveExplanation);
        moveString = scan.nextLine();
        move = MoveParser.parse(board, moveString);
        if (move == null) {
          System.out.println("Illegal move format");
        } else {
          System.out.print("The move is:    ");
          System.out.println(move);
          isMoveValid = MoveValidator.isMoveValid(move, players[currentPlayer], board);
          if (!isMoveValid) {
            System.out.println("The board does not permit word " + move.getLetters() +
                " at position " + move.getDirectedPosition() + ". Please try again.");
          } else {
            makeMove(move);
          }
        }
      }
    }
  }

  private String currentPlayerName(int playerIndex) {
    return (playerIndex == 0 ? PLAYER1_NAME : PLAYER2_NAME);
  }

  private String otherPlayerName(int playerIndex) {
    return (playerIndex == 0 ? PLAYER2_NAME : PLAYER1_NAME);
  }

  private int otherPlayerIndex(int currentPlayer) {
    return 1 - currentPlayer;
  }

  private static char promptToSelectOption(String optionMessage, char option1, char option2) {
    Scanner scan = new Scanner(System.in);
    String userChoice;

    System.out.println(optionMessage);
    while (true) {
      System.out.print("Please enter your choice (" + option1 + "/" + option2 + "): ");
      userChoice = scan.nextLine();
      if (userChoice.length() != 1 ||
          (userChoice.charAt(0) != option1 && userChoice.charAt(0) != option2)) {
        System.out.println("This is not a valid choice.");
      } else
        return userChoice.charAt(0);
    }
  }

  //TODO: handle the very first move
  private void makeMove(Move move) {

    if (move.getDirectedPosition() == null && move.getLetters() == null) {
      currentPlayer = otherPlayerIndex(currentPlayer);
      return;
    }

    Direction moveDirection = move.getDirectedPosition().getDirection();
    Position moveStartPosition = move.getDirectedPosition().getStartPosition();
    String letters = move.getLetters();
    int lettersLength = move.getLetters().length();
    Tile[][] tileGrid = board.getTileGrid();

    int col = moveStartPosition.col();
    int row = moveStartPosition.row();
    int i = 0;
    if (moveDirection == Direction.HORIZONTAL) {
      while (i < lettersLength && col < board.getColumns()) {
        if (tileGrid[row][col] == null) {
          tileGrid[row][col] = players[currentPlayer].removeTileFromRack(letters.charAt(i));
          players[currentPlayer].addTileToRack(tileBag.drawTile());
          i++;
        }
        col++;
      }
    } else if (moveDirection == Direction.VERTICAL) {
      while (i < lettersLength && row < board.getRows()) {
        if (tileGrid[row][col] == null) {
          tileGrid[row][col] = players[currentPlayer].removeTileFromRack(letters.charAt(i));
          players[currentPlayer].addTileToRack(tileBag.drawTile());
          i++;
        }
        row++;
      }

      //TODO: update scores
      System.out.println("Player 1 score: " + players[0].getScore());
      System.out.println("Player 2 score: " + players[1].getScore());

      currentPlayer = otherPlayerIndex(currentPlayer);
    }
  }
}
