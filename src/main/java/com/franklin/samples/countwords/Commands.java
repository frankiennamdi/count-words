package com.franklin.samples.countwords;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;

/**
 * command for the {@link WordsCounter}
 */
@ShellComponent
public final class Commands {

  private final WordsCounter wordsCounter = new WordsCounter();

  @ShellMethod(value = "Count words that are not in the common words file.", prefix = "-")
  public void countWords(@ShellOption(help = "Path to common file.") String commonFile,
                         @ShellOption(help = "Path to file to count.") String countFile
  ) {
    wordsCounter.countWordExcludingCommon(new File(commonFile), new File(countFile))
            .forEach(System.out::println);
  }
}
