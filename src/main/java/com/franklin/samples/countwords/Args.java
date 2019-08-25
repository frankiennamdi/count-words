package com.franklin.samples.countwords;

import com.beust.jcommander.Parameter;

/**
 * The argument for the application
 */
public class Args {
  @Parameter(names = "-c", description = "command to run")
  public String command;
}
