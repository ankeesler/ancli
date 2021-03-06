package com.marshmallow.ancli;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a {@link Action} used to validate whether or not the "bing-home-bacon" command
 * was issued in the {@link CliXmlTest}.
 *
 * <p>
 * Note that the methods offered on this class are <code>static</code> and they track the
 * <em>total</em> number of times that this {@link Action} has been run across <em>all</em>
 * instances of this class.
 * </p>
 *
 * <p>
 * Created Oct 4, 2017
 * </p>
 *
 * @author Andrew
 */
public class BringHomeBaconTestCliAction implements Action {

  // This is a static field so that we can count how many times this command was run over multiple
  // instances of this class.
  private static List<ArgumentValues> runs = new ArrayList<ArgumentValues>();

  /**
   * Reset the number of times that this {@link Action} has been run to 0.
   */
  public static void resetRunCount() {
    runs.clear();
  }

  /**
   * Get the number of times that this {@link Action} has been run.
   *
   * @return The number of times that this {@link Action} has been run across all instances of
   *     this class
   */
  public static int getRunCount() {
    return runs.size();
  }

  /**
   * Get the arguments that were passed to the 0-indexed run instance across all instances
   * of this class.
   *
   * @param runNumber The 0-indexed run instance across all instances of this class
   * @return The arguments that were passed to the 0-indexed run instance across all instances of
   *     this class
   */
  public static ArgumentValues getRunArguments(int runNumber) {
    return runs.get(runNumber);
  }

  @Override
  public void run(ArgumentValues flags, ArgumentValues arguments) {
    runs.add(arguments);
  }
}
