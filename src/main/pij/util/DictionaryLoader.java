package pij.util;

import pij.model.Dictionary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Utility class for loading a dictionary from a file.
 *
 * @author Yubeen Lee
 */
public class DictionaryLoader {
  /**
   * Loads a dictionary from the given file path.
   * Each line represents a word and is converted to a lower case.
   *
   * @param filePath the path to the dictionary file
   * @return a {@code Dictionary}
   * @throws IllegalStateException if the file cannot be loaded
   */
  public static Dictionary loadDictionary(Path filePath) {
    List<String> lines;
    try {
      lines = Files.readAllLines(filePath);
      Set<String> words = new HashSet<>();
      for (String line : lines) {
        line = line.trim().toLowerCase(Locale.ROOT);
        if (!line.isEmpty()) {
          words.add(line);
        }
      }
      return new Dictionary(words);
    } catch (IOException e) {
      throw new IllegalStateException("Couldn't load the word list");
    }
  }
}
