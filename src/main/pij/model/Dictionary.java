package pij.model;

import java.util.Locale;
import java.util.Set;

/**
 * Represents a dictionary of valid words.
 * All words are stored in lowercase, and lookup is case-insensitive.
 *
 * @author Yubeen Lee
 */
public class Dictionary {
  private final Set<String> words;

  /**
   * Creates a dictionary with the given set of words.
   *
   * @param words a set of strings, expected to be in the lowercase
   */
  public Dictionary(Set<String> words) {
    this.words = words;
  }

  /**
   * Checks whether the dictionary contains the given word.
   * The lookup is case-insensitive and ignores leading and trailing whitespace.
   *
   * @param word the word to check
   * @return true if the word exists in the dictionary, false otherwise
   */
  public boolean contains(String word) {
    return words.contains(word.trim().toLowerCase(Locale.ROOT));
  }
}
