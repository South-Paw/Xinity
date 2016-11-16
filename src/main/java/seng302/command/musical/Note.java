package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.NoteUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;

import java.util.List;

/**
 * Note command.
 * Find the corresponding note for a given midi number.
 *
 * @author plr37
 */
public class Note implements Command {
    private List<String> arguments;

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                "Note",
                CommandCategory.MUSICAL,
                "note(midi)",
                "Converts the given midi into it's corresponding note.",
                "midi\n    A valid midi between 1 and 127.",
                "note(60)\n    Returns the corresponding note of midi 60.",
                "note(60)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor that takes and assigns arguments and sets up the classicalTable.
     *
     * @param args Arguments from the command.
     */
    public Note(List<String> args) {
        arguments = args;
    }

    /**
     * Execute the note command.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        try {
            if (arguments.size() == 1) {
                Integer inputMidi = Integer.parseInt(arguments.get(0));

                if (inputMidi < 0 || inputMidi > 127) {
                    env.error(Error.INVALIDMIDINOTE.getError());
                } else {
                    env.println(NoteUtil.convertToNote(inputMidi));
                }
            } else {
                env.error(Error.INVALIDNOTEARGUMENTS.getError());
            }
        } catch (Exception e) {
            env.error(Error.INVALIDMIDINOTE.getError());
        }
    }
}

