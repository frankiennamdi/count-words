package com.franklin.samples.countwords;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.text.BreakIterator;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class WordsCounter {

  Stream<String> countWordExcludingCommon(File commonWordsFile, File fileToCount) {
    try (FileReader commonWordReader = new FileReader(commonWordsFile);
         FileReader fileToCountReader = new FileReader(fileToCount)) {
      Set<String> commonWordSet = new HashSet<>();
      final AtomicInteger longestWord = new AtomicInteger(0);
      iteratorToStream(commonWordReader.getIterator()).forEach(commonWordSet::add);

      return iteratorToStream(fileToCountReader.getIterator())
              .map(this::splitToWordsInUsLocale)
              .flatMap(List::stream)
              .filter(word -> !commonWordSet.contains(word))
              .peek(word -> {
                if (word.length() > longestWord.get()) {
                  longestWord.set(word.length());
                }
              })
              .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
              .entrySet()
              .stream()
              .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
              .map(stringLongEntry -> String.format("%s %d", StringUtils.rightPad(stringLongEntry.getKey(), longestWord.get()),
                      stringLongEntry.getValue()));
    }
  }

  private List<String> splitToWordsInUsLocale(String text) {
    BreakIterator breakIterator = BreakIterator.getWordInstance(Locale.US);
    breakIterator.setText(text);
    int wordBoundaryIndex = breakIterator.first();
    int prevIndex = 0;
    List<String> result = new ArrayList<>();
    while (wordBoundaryIndex != BreakIterator.DONE) {
      String word = text.substring(prevIndex, wordBoundaryIndex);
      if (isWord(word)) {
        result.add(word);
      }
      prevIndex = wordBoundaryIndex;
      wordBoundaryIndex = breakIterator.next();
    }
    return result;
  }

  private static boolean isWord(String word) {
    if (word.length() == 1) {
      return Character.isLetterOrDigit(word.charAt(0));
    }
    return !"".equals(word.trim());
  }

  private Stream<String> iteratorToStream(Iterator<String> sourceIterator) {
    return StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(sourceIterator, Spliterator.ORDERED),
            false);
  }
}
