package com.marshmallow.ancli;

/**
 * This is a dummy {@link Action} class to be used in CLI tests.
 *
 * <p>
 * Created Sep 9, 2017
 * </p>
 *
 * @author Andrew
 */
public class TestCliAction implements Action {

  private boolean ran = false;
  private ArgumentValues flags = null;
  private ArgumentValues arguments = null;

  @Override
  public void run(ArgumentValues flags, ArgumentValues arguments) {
    ran = true;
    this.flags = flags;
    this.arguments = arguments;
  }

  public boolean getRan() {
    return ran;
  }

  public ArgumentValues getFlags() {
    return flags;
  }

  public ArgumentValues getArguments() {
    return arguments;
  }

}
