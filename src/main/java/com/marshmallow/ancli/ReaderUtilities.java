package com.marshmallow.ancli;

import java.io.InputStream;

/**
 * This is a utilities class to be used by various *Reader classes.
 *
 * <p>
 * Created April 21, 2019
 * </p>
 *
 * @author Andrew
 */
final class ReaderUtilities {

  private ReaderUtilities() { }

  /**
   * Returns an {@link ArgumentType} given a string.
   *
   * @param type The argument type name (e.g., STRING, NUMBER, etc.)
   * @return The {@link ArgumentType} for the provided name
   */
  public static ArgumentType<?> getArgumentType(final String type) {
    // TODO: get rid of this horrible hardcoding!
    switch (type) {
      case "STRING":
        return ArgumentType.STRING;
      case "NUMBER":
        return ArgumentType.NUMBER;
      case "BOOLEAN":
        return ArgumentType.BOOLEAN;
      default:
        throw new IllegalArgumentException("No known ArgumentType for type" + type);
    }
  }

  /**
   * Setup a {@link Command}'s {@link Action}, either from a {@link Action}
   * or a {@link ActionCreator}.
   *
   * @param command The command on which to set the action
   * @param className The class name for the {@link Action} or
   * {@link ActionCreator}
   * @param isActionCreator Whether or not the class name is an
   * {@link ActionCreator}
   * @throws Exception when the provided class name is not an instance of the
   *     correct class
   */
  public static void setAction(final MutableCommand command,
                                final String className,
                                boolean isActionCreator) throws Exception {
    final String commandName = command.getName();
    final Class<?> clazz = Class.forName(className);
    if ((isActionCreator && !ActionCreator.class.isAssignableFrom(clazz))
            || (!isActionCreator && !Action.class.isAssignableFrom(clazz))) {
      throw new Exception("Class " + clazz.getName() + " for command " + commandName
              + " is not an instance of " + Action.class.getName());
    }
    Action action = (isActionCreator
            ? ((ActionCreator) clazz.newInstance()).createAction(commandName)
            : (Action) clazz.newInstance());
    command.setAction(action);
  }

}
