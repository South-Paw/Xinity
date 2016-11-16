package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.EnharmonicUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.List;

/**
 * EnharmonicSimple command returns the enharmonic equivalent of a given "simple" note.
 * A simple note is defined as having at most one sharp or flat.
 *
 * @author wrs35
 */
public class SimpleEnharmonic implements Command {
    List<String> arguments;

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                "Simple Enharmonic",
                CommandCategory.MUSICAL,
                "simpleEnharmonic(note)",
                "Displays the simple enharmonic equivalent for a given note.",
                "note\n    A valid note.",
                "simpleEnharmonic(C5)\n    Returns the simple enharmonic of C5.",
                "simpleEnharmonic(C4)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor that takes and assigns arguments.
     *
     * @param args Arguments from the command.
     */
    public SimpleEnharmonic(List<String> args) {
        arguments = args;
    }

    /**
     * Used to execute the simple enharmonic command.
     *
     * @param env The environment for the function
     */
    public void execute(Environment env) {
        XinityNote note;

        // Get note from arguments
        try {
            note = new XinityNote(arguments.get(0));
            // Make the note capitalized
        } catch (Exception exception) {
            env.error(Error.SIMPLEENHARMOINICINVALIDARGS.getError());
            return;
        }

        XinityNote returnNote = EnharmonicUtil.findSimpleEnharmonicEquivalent(note);

        if (returnNote == null) {
            env.println("There is no simple enharmonic equivalent for this note");
        } else {
            env.println(returnNote.getNote());
        }
    }
}
