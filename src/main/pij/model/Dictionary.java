package pij.model;

import java.util.Locale;
import java.util.Set;

/**
 *
 *
 * @author Yubeen Lee
 */
public class Dictionary {
  private final Set<String> words;

  public Dictionary(Set<String> words) {
    this.words = words;
  }

  public boolean contains(String word) {
    return words.contains(word.trim().toLowerCase(Locale.ROOT));
  }
}
