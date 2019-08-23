package com.franklin.samples.countwords;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.Assert.assertThat;
import static com.franklin.samples.countwords.TestSupport.resourceToPath;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = {InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=" + false,
        "spring.main.banner-mode=off"})
public class CommandsTest {

  @Rule
  public OutputCapture outputCapture = new OutputCapture();

  @Autowired
  private Shell shell;

  @Test
  public void testCountWords() throws FileNotFoundException, URISyntaxException {

    String commonWordsFileAbsolutePath = resourceToPath("common.txt").toAbsolutePath().toString();
    String countWordsFileAbsolutePath = resourceToPath("alice.txt").toAbsolutePath().toString();

    String command = String.format("count-words -common-file %s -count-file %s", commonWordsFileAbsolutePath,
            countWordsFileAbsolutePath);
    shell.evaluate(() -> command);
    String consoleOutput = outputCapture.toString();
    assertThat(consoleOutput, matchesPattern("(?s).*Alice.*386(?s).*"));
  }

}
