package com.franklin.samples.countwords;

import com.franklin.samples.countwords.ScriptShellApplicationRunner;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.shell.Shell;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.franklin.samples.countwords.TestSupport.resourceToPath;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;


public class ScriptShellApplicationRunnerTest {

  @Test
  public void testRun() throws IOException, URISyntaxException {
    Shell mockShell = mock(Shell.class);
    ConfigurableEnvironment environment = mock(ConfigurableEnvironment.class);

    when(environment.getPropertySources()).thenReturn(mock(MutablePropertySources.class));
    ScriptShellApplicationRunner scriptShellApplicationRunner = new ScriptShellApplicationRunner(mockShell, environment);
    ArgumentCaptor<ScriptShellApplicationRunner.StringInputProvider> stringInputProvider =
            ArgumentCaptor.forClass(ScriptShellApplicationRunner.StringInputProvider.class);

    String commonWordsFileAbsolutePath = resourceToPath("common.txt").toAbsolutePath().toString();
    String countWordsFileAbsolutePath = resourceToPath("alice.txt").toAbsolutePath().toString();

    String command = String.format("count-words -common-file %s -count-file %s", commonWordsFileAbsolutePath,
            countWordsFileAbsolutePath);
    scriptShellApplicationRunner.run("-c", command);
    verify(mockShell).run(stringInputProvider.capture());
    assertThat(stringInputProvider.getValue().getCommand(), is(command));
  }
}
