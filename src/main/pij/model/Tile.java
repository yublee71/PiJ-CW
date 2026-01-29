package pij.model;

/**
 * Represents a tile in the scrabble game.
 * A tile can have a letter ('A'~'Z') or be a wildcard ('_').
 * Each tile has a point value (1~12).
 * Wildcards can be assigned a chosen letter ('a'~'z') during the game.
 *
 * @author Yubeen Lee
 */
public class Tile {
  private final char letter;
  private final int value;
  private final boolean isWildcard;
  private Character chosenLetter;

  /**
   * Constructs a {@code Tile} with the given letter and value.
   *
   * @param letter the character for the new {@code Tile} ('A'~'Z' or '_' for wildcard)
   * @param value  the point value of the new {@code Tile} (1~12)
   * @throws IllegalArgumentException if the letter is invalid or value is out of range
   */
  public Tile(char letter, int value) {
    if ((letter < 'A' || letter > 'Z') && letter != '_') {
      throw new IllegalArgumentException("Invalid letter: " + letter);
    }
    if (value < 1 || value > 12) {
      throw new IllegalArgumentException("Invalid value: " + value);
    }
    this.letter = letter;
    this.value = value;
    isWildcard = (letter == '_');
    chosenLetter = null;
  }

  /**
   * Sets the chosen letter for wildcard {@code Tile}.
   *
   * @param chosenLetter the letter chosen by a player ('a'~'z')
   * @throws IllegalStateException    if the tile is not a wildcard or the letter is already set
   * @throws IllegalArgumentException if the chosen letter is null or invalid
   */
  public void setChosenLetter(Character chosenLetter) {
    if (!isWildcard) {
      throw new IllegalStateException("Cannot set chosen letter for a non-wildcard tile.");
    }
    if (this.chosenLetter != null) {
      throw new IllegalStateException("Chosen letter has already been set.");
    }
    if (chosenLetter == null) {
      throw new IllegalArgumentException("Chosen letter cannot be null.");
    }
    if (chosenLetter < 'a' || chosenLetter > 'z') {
      throw new IllegalArgumentException("Invalid letter: " + chosenLetter);
    }
    this.chosenLetter = chosenLetter;
  }

  /**
   * Getter for the letter of this {@code Tile}
   * For wildcard, returns the chosen letter if set.
   *
   * @return the letter of this {@code Tile}
   */
  public char getLetter() {
    if (isWildcard && chosenLetter != null) {
      return chosenLetter;
    }
    return letter;
  }

  /**
   * Getter for the value of this {@code Tile}
   *
   * @return the value of this {@code Tile}
   */
  public int getValue() {
    return value;
  }

  /**
   * Checks if this {@code Tile} is wildcard
   *
   * @return true if wildcard, false otherwise
   */
  public boolean isWildcard() {
    return isWildcard;
  }

  /**
   * Returns a string representation of this {@code Tile}, combining the letter and the value.
   *
   * @return a string representation of this {@code Tile}
   */
  @Override
  public String toString() {
    return "" + getLetter() + value;
  }

  /**
   * Returns a formatted string representation of this {@code Tile} for board display.
   * The string is left-aligned and padded to a width of 3.
   *
   * @return a formatted string representation of this {@code Tile}
   */
  public String toBoardString() {
    return String.format("%-3s", this);
  }

  /**
   * Returns a bracked string representation of this {@code Tile} for rack display.
   *
   * @return a bracketed string representation of this {@code Tile}
   */
  public String toBracketedString() {
    return "[" + this + "]";
  }
}
