package pij.game;

import pij.model.Board;
import pij.model.Player;
import pij.model.Square;
import pij.model.Tile;

import java.util.Scanner;

/**
 *
 *
 * @author Yubeen Lee
 */
public class GameUI {
  private static final String RESET = "\u001B[0m";
  private static final String BLUE = "\u001B[34m";
  private static final String PINK = "\u001B[95m";

  private static final Scanner SCANNER = new Scanner(System.in);

  private static final String PLAYER1_NAME = BLUE + "Player 1" + RESET;
  private static final String PLAYER2_NAME = PINK + "Player 2" + RESET;

  public static void showGameBanner() {
    System.out.println("""
        ============                     ============
        ============ S c r a e B B K l e ============
        ============                     ============
        """);
  }

  public static boolean userChoseDefaultBoard() {
    return promptToSelectOption(
        "Would you like to _l_oad a board or use the _d_efault board?",
        'l', 'd') == 'd';
  }

  public static boolean userChoseHumanPlayer(int playerIndex) {
    return (promptToSelectOption("Is " + playerName(playerIndex)
            + " a _h_uman player or a _c_omputer player?",
        'h', 'c') == 'h');
  }

  public static boolean userChoseOpenGame() {
    return (promptToSelectOption(
        "Would you like to play an _o_pen or a _c_losed game?",
        'o', 'c') == 'o');
  }

  public static String promptBoardFileName() {
    System.out.print("Please enter the file name of the board: ");
    return (SCANNER.nextLine());
  }

  public static String promptMove() {
    System.out.print("""
        Please enter your move in the format: "word,square" (without the quotes)
        For example, for suitable tile rack and board configuration, a downward move
        could be "HI,f4" and a rightward move could be "HI,4f".
        
        In the word, upper-case letters are standard tiles and lower-case letters
        are wildcards.
        Entering "," passes the turn.
        """);
    return (SCANNER.nextLine());
  }

  public static void showBoard(Board board) {
    int rows = board.getRows();
    int columns = board.getColumns();
    Tile[][] tileGrid = board.getTileGrid();
    Square[][] squareGrid = board.getSquareGrid();

    printColumnIndex(columns);
    String formattedRow;
    for (int row = 0; row < rows; row++) {
      formattedRow = String.format("%2d", row + 1);
      System.out.print(formattedRow + " ");
      for (int col = 0; col < columns; col++) {
        if (tileGrid[row][col] == null) {
          System.out.print(squareGrid[row][col].toBoardString());
        } else {
          System.out.print(tileGrid[row][col].toBoardString());
        }
      }
      System.out.println(" " + formattedRow);
    }
    printColumnIndex(columns);
    System.out.println("Start position: " + board.getStartPosition());
  }

  private static void printColumnIndex(int columns) {
    System.out.println();
    System.out.print("   ");
    for (int col = 0; col < columns; col++) {
      System.out.print(" " + (char) ('a' + col) + " ");
    }
    System.out.println();
    System.out.println();
  }

  public static void showRack(boolean isOpenGame, Player[] players, int currentPlayerIndex) {
    int otherPlayerIndex = 1 - currentPlayerIndex;

    if (isOpenGame) {
      System.out.println("OPEN GAME: " + playerName(otherPlayerIndex) + "'s tiles:");
      System.out.print("OPEN GAME: ");
      players[otherPlayerIndex].showRack();
    }
    System.out.println("It's your turn, " + playerName(currentPlayerIndex) + "! Your tiles:");
    players[currentPlayerIndex].showRack();
  }

  public static void showScore(int[] scoreBoard) {
    System.out.println(playerName(0) + " score: " + scoreBoard[0]);
    System.out.println(playerName(1) + " score: " + scoreBoard[1]);
  }

  public static void showGameResult(int[] scoreBoard) {
    System.out.println("Game Over!");

    System.out.println(playerName(0) + " scored " + scoreBoard[0] + " points.");
    System.out.println(playerName(1) + " scored " + scoreBoard[1] + " points.");

    if (scoreBoard[0] > scoreBoard[1]) {
      System.out.println(playerName(0) + " wins!");
    } else if (scoreBoard[0] < scoreBoard[1]) {
      System.out.println(playerName(1) + " wins!");
    } else {
      System.out.println("It's a draw!");
    }
  }

  private static String playerName(int playerIndex) {
    return (playerIndex == 0 ? PLAYER1_NAME : PLAYER2_NAME);
  }


  private static char promptToSelectOption(String optionMessage, char option1, char option2) {
    String userChoice;

    System.out.println(optionMessage);
    while (true) {
      System.out.print("Please enter your choice (" + option1 + "/" + option2 + "): ");
      userChoice = SCANNER.nextLine();
      if (userChoice.length() != 1 ||
          (userChoice.charAt(0) != option1 && userChoice.charAt(0) != option2)) {
        System.out.println("This is not a valid choice.");
      } else
        return userChoice.charAt(0);
    }
  }
}
