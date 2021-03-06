package com.marshmallow.ancli;

/**
 * This represents a command passed on the command line. This could be something like "log" or
 * "commit" or "integrate" or "update." Each one of these objects has a name, some {@link Flag}'s
 * and an {@link Action} associated with it. The {@link Action} is run (via
 * {@link Action#run(ArgumentValues, ArgumentValues)} whenever this {@link Command} is passed on
 * the command line. An object of this type can also optionally have a description.
 *
 * <p>
 * Created Oct 14, 2017
 * </p>
 *
 * @author Andrew
 */
public interface Command extends ListOrCommand {

  /**
   * Get the {@link Action} associated with this {@link Command}. This is the {@link Action} that
   * is called (via {@link Action#run(ArgumentValues, ArgumentValues)} whenever this
   * {@link Command} is passed on the command line.
   *
   * @return The {@link Action} associated with this {@link Command}
   */
  public Action getAction();

  /**
   * Get the {@link Argument}'s associated with this {@link Command}. This API must return the
   * {@link Argument}'s in the same order in which they are added (see
   * {@link MutableCommand#addArgument}).
   *
   * @return The {@link Argument}'s associated with this {@link Command}
   */
  public Argument[] getArguments();
}