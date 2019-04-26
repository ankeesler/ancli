package com.marshmallow.ancli;

import java.io.InputStream;
import java.util.Map;
import java.util.function.BiFunction;

import org.yaml.snakeyaml.Yaml;

/**
 * An object to read an YAML {@link InputStream} and produce a {@link Cli} object.
 *
 * <p>
 * Created April 21, 2019
 * </p>
 *
 * @author Andrew
 */
public class CliYamlReader {

  private final InputStream yamlStream;

  /**
   * Create a {@link CliYamlReader} with a {@link InputStream} from which to parse the CLI XML data.
   *
   * @param yamlStream The {@link InputStream} which contains the CLI XML data
   */
  public CliYamlReader(InputStream yamlStream) {
    this.yamlStream = yamlStream;
  }

  /**
   * Read a {@link Cli} object from the YAML {@link InputStream} provided to the constructor.
   *
   * @return A {@link Cli} object from the YAML {@link InputStream} provided to the constructor
   * @throws Exception if something goes wrong
   */
  public Cli read() throws Exception {
    final Object obj = new Yaml().load(yamlStream);
    final Map objMap = cast(obj, Map.class);

    final String name = cast(objMap.get("name"), String.class);
    if (name == null) {
      throw new Exception("Could not get 'name' from root of YAML");
    }

    final Cli cli = new Cli(name);

    final MutableList root = cli.getRoot();

    final String description = cast(objMap.get("description"), String.class);
    if (description != null) {
      root.setDescription(description);
    }

    parseList(root, objMap);

    return cli;
  }

  private static void parseLists(
          final MutableList root,
          final Map objMap
  ) throws Exception {
    final java.util.List lists
            = cast(objMap.get("lists"), java.util.List.class);
    if (lists == null) {
      return;
    }

    for (final Object list : lists) {
      final Map listsMap = cast(list, Map.class);

      final String name = cast(listsMap.get("name"), String.class);
      final MutableList child = root.addList(name);

      final String description = cast(listsMap.get("description"), String.class);
      if (description != null) {
        child.setDescription(description);
      }

      parseList(child, listsMap);
    }
  }

  private static void parseList(
          final MutableList root,
          final Map objMap
  ) throws Exception {
    parseFlags(root, objMap);
    parseCommands(root, objMap);
    parseLists(root, objMap);
  }

  private static void parseFlags(
          final MutableListOrCommand root,
          final Map objMap
  ) throws Exception {
    final java.util.List flagsList
            = cast(objMap.get("flags"), java.util.List.class);
    if (flagsList == null) {
      return;
    }

    for (final Object flag : flagsList) {
      final Map flagMap = cast(flag, Map.class);
      parseFlag(root, flagMap);
    }
  }

  private static void parseFlag(
          final MutableListOrCommand root,
          final Map objMap
  ) throws Exception {
    final String shortFlag = cast(objMap.get("shortFlag"), String.class);
    final MutableFlag flag = root.addFlag(shortFlag);

    final String longFlag = cast(objMap.get("longFlag"), String.class);
    if (longFlag != null) {
      flag.setLongFlag(longFlag);
    }

    final String description = cast(objMap.get("description"), String.class);
    if (description != null) {
      flag.setDescription(description);
    }

    final Map argument = cast(objMap.get("argument"), Map.class);
    if (argument != null) {
      parseArgument(flag::setArgument, argument);
    }
  }

  private static void parseArgument(
          final BiFunction<String, ArgumentType<?>, MutableArgument> argumentCreator,
          final Map objMap
  ) throws Exception {
    final String name = cast(objMap.get("name"), String.class);
    final String type = cast(objMap.get("type"), String.class);
    final MutableArgument argument =
            argumentCreator.apply(name, ReaderUtilities.getArgumentType(type));

    final String description = cast(objMap.get("description"), String.class);
    if (description != null) {
      argument.setDescription(description);
    }
  }

  private static void parseCommands(
          final MutableList root,
          final Map objMap
  ) throws Exception {
    final java.util.List commands
            = cast(objMap.get("commands"), java.util.List.class);
    if (commands == null) {
      return;
    }

    for (final Object command : commands) {
      final Map commandMap = cast(command, Map.class);
      parseCommand(root, commandMap);
    }
  }

  private static void parseCommand(
          final MutableList root,
          final Map objMap
  ) throws Exception {
    final String name = cast(objMap.get("name"), String.class);
    final String actionClassName
            = cast(objMap.get("action"), String.class);
    final String actionCreatorClassName
            = cast(objMap.get("actionCreator"), String.class);
    if ((actionClassName == null && actionCreatorClassName == null)
            || (actionClassName != null && actionCreatorClassName != null)) {
      throw new Exception("Must specify 'action' or 'actionCreator' for command");
    }
    final MutableCommand command = root.addCommand(name, null);
    ReaderUtilities.setAction(
            command,
            actionClassName != null ? actionClassName : actionCreatorClassName,
            actionClassName == null);

    final String description = cast(objMap.get("description"), String.class);
    if (description != null) {
      command.setDescription(description);
    }

    final java.util.List arguments
            = cast(objMap.get("arguments"), java.util.List.class);
    if (arguments != null) {
      for (final Object argument : arguments) {
        final Map argumentMap = cast(argument, (Map.class));
        parseArgument(command::addArgument, argumentMap);
      }
    }

    parseFlags(command, objMap);
  }

  private static <T> T cast(final Object thing, final Class<T> clazz) throws Exception {
    if (thing == null) {
      return null;
    } else if (!clazz.isInstance(thing)) {
      final String msg = String.format("Expected class %s to be %s",
              thing.getClass().getName(),
              clazz.getName());
      throw new Exception(msg);
    } else {
      return clazz.cast(thing);
    }
  }

}
