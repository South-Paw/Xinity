package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.IntervalUtil;
import seng302.util.NoteUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.List;

/**
 * Interval command class.
 * Find the resulting note when given a tonic (starting note) and an interval.
 *
 * @author mvj14
 *
 */
public class Interval implements Command {
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
                "Interval",
                CommandCategory.MUSICAL,
                "interval(note, interval)",
                "Given a note, the command will find the second note in the interval.",
                "interval\n    An interval from the interval list.\n"
                        + "note\n    A valid note.",
                "interval(C4, \"augmented second\")\n    Finds the second note in the augmented "
                        + "second interval.\n"
                        + "interval(D5, \"major 9th\")\n    Finds the second note in the major "
                        + "9th interval.",
                "interval(C4, \"major 2nd\")"
            );
        }

        return commandManual;
    }

    /**
     * Constructor for class.
     *
     * @param args Arguments from the environment.
     */
    public Interval(List<String> args) {
        arguments = args;
    }

    /**
     * Execute the Interval command.
     *
     * @param env The Environment.
     */
    public void execute(Environment env) {
        if (arguments.size() != 2) {
            env.error(Error.INTERVALINVALIDARGUMENTS.getError());
            return;
        }

        XinityNote tonic;
        try {
            tonic = new XinityNote(arguments.get(0));
        } catch (Exception e) {
            env.error(Error.INTERVALINVALIDARGUMENTS.getError());
            return;
        }

        seng302.util.enumerator.Interval interval;
        interval = seng302.util.enumerator.Interval.fromString(arguments.get(1));

        if (interval == null) {
            env.error(Error.INTERVALDOESNOTEXIST.getError());
            return;
        }

        try {
            XinityNote finalNote = IntervalUtil.interval(tonic, interval);
            if (finalNote == null) {
                env.error(Error.INTERVALDOESNOTEXIST.getError());
                return;
            }
            if (!finalNote.isValidMidi()) {
                env.error(Error.INVALIDMIDINOTE.getError());
                return;
            }

            if (tonic.hasOctave()) {
                env.println(finalNote.getNote());
            } else {
                env.println(finalNote.getNote().replaceAll("\\d", ""));
            }

        } catch (Exception e) {
            env.error(Error.INTERVALDOESNOTEXIST.getError());
        }
    }
}
