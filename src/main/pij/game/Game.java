package pij.game;

import pij.model.Board;
import pij.model.Move;
import pij.model.Player;
import pij.model.TileBag;
import pij.util.BoardLoader;
import pij.util.MoveExecutor;
import pij.util.MoveParser;
import pij.util.MoveValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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

  public static final String RESET = "\u001B[0m";
  public static final String BLUE = "\u001B[34m";
  public static final String PINK = "\u001B[95m";

  private static final String PLAYER1_NAME = BLUE + "Player 1" + RESET;
  private static final String PLAYER2_NAME = PINK + "Player 2" + RESET;

  private static final String OPTION_BOARD =
      "Would you like to _l_oad a board or use the _d_efault board?";
  private static final String OPTION_PLAYER1 =
      "Is " + PLAYER1_NAME + " a _h_uman player or a _c_omputer player?";
  private static final String OPTION_PLAYER2 =
      "Is " + PLAYER2_NAME + " a _h_uman player or a _c_omputer player?";
  private static final String OPTION_OPEN_CLOSE =
      "Would you like to play an _o_pen or a _c_losed game?";

  private static final String ERR_INVALID_DEFAULT_BOARD =
      "The default board is invalid. Game exiting...";
  private static final String ERR_INVALID_FILE =
      "The file is not a valid file. ";
  private static final String PROMPT_BOARD_FILE =
      "Please enter the file name of the board: ";

  public static final String RESOURCE_FOLDER_NAME = "resources";
  public static final String DEFAULT_BOARD_FILE_NAME = "defaultBoard.txt";
  private static final String WORD_LIST_FILE_NAME = "wordlist.txt";

  private final Board board;
  private final TileBag tileBag;
  private final Player[] players;
  private int currentPlayer;
  private final boolean isOpenGame;
  private final List<String> words;
  private int numOfConsecutivePass;

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


    Path filePath = Paths.get(Game.RESOURCE_FOLDER_NAME, WORD_LIST_FILE_NAME);
    List<String> lines;
    try {
      lines = Files.readAllLines(filePath);
      this.words = lines;
    } catch (IOException e) {
      throw new IllegalArgumentException("Couldn't load word list");
    }

    this.board = tmpBoard;
    this.tileBag = new TileBag();
    this.players = new Player[2];
    this.players[0] = new Player(promptToSelectOption(OPTION_PLAYER1, 'h', 'c') == 'h', tileBag);
    this.players[1] = new Player(promptToSelectOption(OPTION_PLAYER2, 'h', 'c') == 'h', tileBag);
    this.currentPlayer = 0;
    this.isOpenGame = promptToSelectOption(OPTION_OPEN_CLOSE, 'o', 'c') == 'o';
    this.numOfConsecutivePass = 0;
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
    int moveCount = 0;
    Move move = null;
    while (!isGameOver()) {//TODO: check tile bag, pass twice
      //TODO: different display for computer player
      if (move == null || move.getLetters() != null && move.getDirectedPosition() != null) {
        board.showBoard();
        board.showStartPosition();
      }
      if (isOpenGame) {
        System.out.println("OPEN GAME: " + playerName(otherPlayerIndex(currentPlayer)) + "'s tiles:");
        System.out.print("OPEN GAME: ");
        players[otherPlayerIndex(currentPlayer)].showRack();
      }
      System.out.println("It's your turn, " + playerName(currentPlayer) + "! Your tiles:");
      players[currentPlayer].showRack();

      String moveString;
      move = null;
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
          if (moveCount == 0) {
            move.setIsFirstMove(true);
          }
          isMoveValid = MoveValidator.isMoveValid(move, players[currentPlayer], board, words);
          if (!isMoveValid) {
            System.out.println("The board does not permit word " + move.getLetters() +
                " at position " + move.getDirectedPosition() + ". Please try again.");
          } else {
            MoveExecutor.makeMove(move, board, players, currentPlayer, tileBag);
            if (move.getDirectedPosition() == null && move.getLetters() == null) {
              numOfConsecutivePass++;
            } else {
              numOfConsecutivePass = 0;
              System.out.println(playerName(0) + " score: " + players[0].getScore());
              System.out.println(playerName(1) + " score: " + players[1].getScore());
            }
            currentPlayer = otherPlayerIndex(currentPlayer);
            moveCount++;
          }
        }
      }
    }
    showGameResult();
  }

  private String playerName(int playerIndex) {
    return (playerIndex == 0 ? PLAYER1_NAME : PLAYER2_NAME);
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

  private boolean isGameOver() {
    return numOfConsecutivePass >= 4 || tileBag.size() == 0;
  }

  private void showGameResult() {
    System.out.println("Game Over!");
    System.out.println(playerName(0) + " scored " + players[0].getScore() + " points.");
    System.out.println(playerName(1) + " scored " + players[1].getScore() + " points.");
    if (players[0].getScore() > players[1].getScore()) {
      System.out.println(playerName(0) + " wins!");
    } else if (players[0].getScore() < players[1].getScore()) {
      System.out.println(playerName(1) + " wins!");
    } else {
      System.out.println("It's a draw!");
    }
  }

}
