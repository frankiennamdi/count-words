package com.franklin.samples.countwords;

import com.beust.jcommander.Parameter;

public class Args {
  @Parameter(names = "-c", description = "command to run")
  public String command;
}
