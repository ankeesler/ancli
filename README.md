# ancli

It's a Java CLI.

## Java Initialization

```java
Cli cli = new Cli("cli-app");
MutableList rootList = cli.getRoot();
rootList.addFlag("f")
        .setDescription("This is a description");
rootList.addFlag("o")
        .setLongFlag("output")
        .setDescription("This is a description")
        .setArgument("location").setType(ArgumentType.STRING);

MutableCommand tunaCommand = root.addCommand("tuna", new CliAction() { ... });
tunaCommand.setDescription("This is the tuna command");
tunaCommand.addFlag("a").setDescription("This is a description");

MutableList fishList = root.addList("fish").setDescription(""This is the fish command list");
fishList.addCommand("marlin", new CliAction() { ... }))
        .setDescription("This is the marlin command");
...
root.parse(args);
```

## XML Initialization

```xml
<cli name="tuna" description="This is the tuna CLI">
  <flag shortFlag="a" description="This is the short a flag" />
  <flag shortFlag="b" longFlag="bacon" description="This is the long b flag" />
  <flag shortFlag="c" description="This is the short c flag with a parameter">
    <argument name="name" description="This is the name of something" type="STRING" />
  </flag>
  <flag shortFlag="d" longFlag="dog" description="This is the long d flag with a parameter">
    <argument name="age" description="This is the age of the dog" type="NUMBER" />
  </flag>
  <flag shortFlag="e" description="This is a boolean flag">
    <argument name="yesOrNo" description="This is a boolean flag" type="BOOLEAN" />
  </flag>
  <flag shortFlag="no-description-short-flag">
  </flag>
  <flag shortFlag="n" longFlag="no-description-long-flag">
    <argument name="blah" type="STRING" />
  </flag>

  <command name="fish" description="This is the tuna fish command">
    <!-- Add a duplicate flag to test that we use the second one! -->
    <flag shortFlag="o" longFlag="nooooo" />
    <flag shortFlag="o" description="The number of fish that you desire">
      <argument name="number" type="NUMBER" />
    </flag>
    <argument name="number" description="The number of the fish" type="NUMBER" />
    <argument name="name" type="STRING" />
    <actionCreator class="com.marshmallow.ancli.TestCliActionCreator" />
  </command>
  <!-- Add a duplicate command to make sure that we use the second one! -->
  <command name="marlin">
    <action class="com.marshmallow.ancli.BringHomeBaconTestCliAction" />
  </command>
  <command name="marlin" description="This is the tuna marlin command">
    <actionCreator class="com.marshmallow.ancli.TestCliActionCreator" />
  </command>
</cli>
```

## YAML Initialization

```yaml
---
name: tuna
description: This is the tuna CLI
flags:
- shortFlag: a
  description: This is the short a flag
- shortFlag: b
  longFlag: bacon
  description: This is the short c flag with a parameter
- shortFlag: c
  description: This is the short c flag with a parameter
  argument:
    name: name
    description: This is the name of something
    type: STRING
- shortFlag: d
  longFlag: dog
  description: This is the long d flag with a parameter
  argument:
    name: age
    description: This is the age of the dog
    type: NUMBER
- shortFlag: e
  description: This is a boolean flag
  argument:
    name: yesOrNo
    description: This is a boolean flag
    type: BOOLEAN
- shortFlag: no-description-short-flag
- shortFlag: n
  longFlag: no-description-long-flag
  argument:
    name: blah
    type: STRING
commands:
- name: fish
  description: This is the tuna fish command
  flags:
  - shortFlag: o
    longFlag: nooooo
  - shortFlag: o
    description: The number of fish that you desire
    argument:
      name: number
      type: NUMBER
  arguments:
  - name: number
    description: The number of the fish
    type: NUMBER
  - name: name
    type: STRING
  actionCreator: com.marshmallow.ancli.TestCliActionCreator
- name: marlin
  action: com.marshmallow.ancli.BringHomeBaconTestCliAction
- name: marlin
  description: This is the tuna marlin command
  actionCreator: com.marshmallow.ancli.TestCliActionCreator
```