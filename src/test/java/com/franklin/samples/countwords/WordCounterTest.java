package com.franklin.samples.countwords;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Optional;

import static com.franklin.samples.countwords.TestSupport.resourceToPath;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class WordCounterTest {

  @Test
  public void testCountWord() throws FileNotFoundException, URISyntaxException {
    WordsCounter wordsCounter = new WordsCounter();
    Optional<String> alice =
            wordsCounter.countWordExcludingCommon(resourceToPath("common.txt").toFile(),
                    resourceToPath("alice.txt").toFile())
                    .filter(entry -> entry.contains("Alice"))
                    .findFirst();

    assertThat(alice.isPresent(), is(true));
    assertThat(alice.get(), containsString("386"));

  }
}
