package com.franklin.samples.countwords;

import com.beust.jcommander.JCommander;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.shell.Input;
import org.springframework.shell.InputProvider;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Non interactive script runner
 */
@Order(InteractiveShellApplicationRunner.PRECEDENCE - 100)
@Component
public class ScriptShellApplicationRunner implements CommandLineRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScriptShellApplicationRunner.class);

  private Shell shell;

  private final ConfigurableEnvironment environment;

  @Autowired
  public ScriptShellApplicationRunner(Shell shell, ConfigurableEnvironment environment) {
    this.shell = shell;
    this.environment = environment;
  }

  @Override
  public void run(String... arguments) {
    try {
      String[] commandArguments = Arrays.stream(arguments).filter(argument ->
              !argument.contains("spring.shell.command")).toArray(String[]::new);
      Args args = new Args();
      JCommander.newBuilder()
              .addObject(args)
              .build()
              .parse(commandArguments);
      if (StringUtils.isNotBlank(args.command)) {
        InteractiveShellApplicationRunner.disable(environment);
        shell.run(new StringInputProvider(args.command));
      }
    } catch (Exception exception) {
      LOGGER.info(exception.getMessage());
    }
  }

  class StringInputProvider implements InputProvider {

    private boolean done;

    private String command;

    StringInputProvider(String command) {
      this.command = command;
    }

    public String getCommand() {
      return command;
    }

    @Override
    public Input readInput() {
      if (!done) {
        done = true;
        return () -> command;
      } else {
        return null;
      }
    }
  }
}


