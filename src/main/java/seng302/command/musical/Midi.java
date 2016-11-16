package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.NoteUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.List;

/**
 * Midi command, returns the midi note number for a given note plus octave representation.
 *
 * @author ljm163
 */
public class Midi implements Command {
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
                "Midi",
                CommandCategory.MUSICAL,
                "midi(note)",
                "Converts the given note into it's corresponding midi number.",
                "note\n    A valid note.",
                "midi(C4)\n    Returns the midi number of C4.",
                "midi(C4)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor that takes and assigns arguments and sets up the midiTable.
     *
     * @param args Arguments from the command.
     */
    public Midi(List<String> args) {
        arguments = args;
    }

    /**
     * Execute command.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        try {
            if (arguments.size() == 1) {
                Integer converted;
                XinityNote inputNote = new XinityNote(arguments.get(0));

                try {
                    converted = NoteUtil.convertToMidi(inputNote);
                } catch (Exception ex) {
                    env.error(Error.INVALIDMIDINOTE.getError());
                    return;
                }

                if (converted == null) {
                    env.error(Error.INVALIDNOTE.getError());
                } else {
                    if (!NoteUtil.isValidMidiRange(converted)) {
                        env.error(Error.INVALIDMIDINOTE.getError());
                    } else {
                        env.println(converted.toString());
                    }
                }
            } else {
                env.error(Error.INVALIDMIDIARGUMENTS.getError());
            }
        } catch (Exception e) {
            env.error(Error.INVALIDMIDIARGUMENTS.getError());
        }
    }
}
