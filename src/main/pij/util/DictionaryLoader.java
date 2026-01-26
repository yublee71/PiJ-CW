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
 *
 *
 * @author Yubeen Lee
 */
public class DictionaryLoader {
  public static Dictionary loadDictionary(Path filePath) {
    List<String> lines;
    try {
      lines = Files.readAllLines(filePath);
      Set<String> words = new HashSet<>();
      for (String line : lines) {
        line = line.trim().toLowerCase(Locale.ROOT);
        words.add(line);
      }
      return new Dictionary(words);
    } catch (IOException e) {
      throw new IllegalArgumentException("Couldn't load word list");
    }
  }
}
