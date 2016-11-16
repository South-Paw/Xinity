package seng302.util.object;

import seng302.util.enumerator.CommandCategory;

/**
 * Command Manual objects hold all relevant command information. This is instead of saving all the
 * strings of information at the top of each command class. It also allows us to retrieve each
 * field with ease and is mainly used with the DSL Reference GUI. The format of the variables is
 * based off of the `man` page found in the linux terminal.
 *
 * @author adg62
 */
public class CommandManual {
    private String name;
    private CommandCategory category;
    private String synopsis;
    private String description;
    private String arguments;
    private String examples;
    private String quickAdd;

    /**
     * Constructor for a CommandManual object.
     *
     * @param name Name of the command.
     * @param category Category of the command.
     * @param synopsis Full command string with all arguments.
     * @param description A full description of the command.
     * @param arguments A full list of arguments and their descriptions.
     * @param examples A list of valid example commands.
     * @param quickAdd A copy of a valid command, this is copied into the input field for running.
     */
    public CommandManual(String name, CommandCategory category, String synopsis, String description,
                         String arguments, String examples, String quickAdd) {
        this.name = name;
        this.category = category;
        this.synopsis = synopsis;
        this.description = description;
        this.arguments = arguments;
        this.examples = examples;
        this.quickAdd = quickAdd;
    }

    public String getName() {
        return name;
    }

    public CommandCategory getCategory() {
        return category;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getDescription() {
        return description;
    }

    public String getArguments() {
        return arguments;
    }

    public String getExamples() {
        return examples;
    }

    public String getQuickAdd() {
        return quickAdd;
    }
}
