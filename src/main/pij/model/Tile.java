package pij.model;

/**
 *
 *
 * @author Yubeen Lee
 */
public class Tile {
  private final char letter; // 'A'~'Z', '_' for wildcards
  private final int value; // 1~12 depending on the letter
  private final boolean isWildcard;
  private Character chosenLetter; // 'a'~'z' according to user's choice; null if not chosen

  /**
   * @param letter
   * @param value
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
   * @param chosenLetter
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

  @Override
  public String toString() {
    if (isWildcard && chosenLetter != null) {
      return "" + chosenLetter + value;
    }
    return "" + letter + value;
  }

  public String toBoardString() {
    return String.format("%-3s", this);
  }

  public String toBracketedString() {
    return "[" + this + "]";
  }

  public char getLetter() {
    return letter;
  }
}
