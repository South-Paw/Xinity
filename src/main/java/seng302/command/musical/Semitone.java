package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.SemitoneUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.List;

/**
 * Semitone Command, yields the name of the note a given number of semitones higher
 * than the given note.
 *
 * @author ljm163
 */
public class Semitone implements Command {
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
                "Semitone",
                CommandCategory.MUSICAL,
                "semitone(number, note)",
                "Calculates the semitone for a given note, depending on the number value. Number "
                        + "value can be a positive or negative whole number and the command will "
                        + "produce a note that number of semitones above or below the given note.",
                "number\n    A positive or negative number that indicates how many semitones away "
                        + "to go.\n"
                        + "note\n    A valid note.",
                "semitone(12, C4)\n    Will produce a note that is 12 semitones above C4.\n"
                        + "semitone(-4, E5)\n    Will produce a note that is 4 semitones below E5.",
                "semitone(6, C4)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor that takes and assigns arguments.
     *
     * @param args Arguments from the command.
     */
    public Semitone(List<String> args) {
        arguments = args;
    }

    /**
     * Execute function.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        try {
            if (arguments.size() == 2) {
                XinityNote inputNote = new XinityNote(arguments.get(1));

                if (inputNote.getIsDouble()) {
                    env.error(Error.SEMITONESIMPLENOTE.getError());
                } else {
                    XinityNote nextNote = SemitoneUtil.neighbouringNote(
                            Integer.parseInt(arguments.get(0)),
                            inputNote);

                    if (nextNote == null) {
                        env.error(Error.SEMITONEMIDIRANGE.getError());
                    } else {
                        env.println(nextNote.getNote());
                    }
                }
            } else if (arguments.size() == 0) {
                env.error(Error.SEMITONENOARGS.getError());
            } else {
                env.error(Error.SEMITONEINVALIDARGS.getError());
            }
        } catch (Exception e) {
            env.error(Error.SEMITONEINVALIDARGS.getError());
        }
    }
}
