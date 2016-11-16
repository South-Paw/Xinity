package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.IntervalUtil;
import seng302.util.NoteUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.enumerator.Interval;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.List;

/**
 * Given 2 input notes, command finds the interval name.
 *
 * @author mvj14
 */
public class FindInterval implements Command {
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
                "Find Interval",
                CommandCategory.MUSICAL,
                "findInterval(noteA, noteB)",
                "Returns the interval between noteA and noteB, providing noteB is equal to or"
                        + " higher than noteA.",
                "noteA\n    A valid note.\n"
                        + "noteB\n    A valid note equal to or higher than noteA.",
                "findInterval(a, b)\n    Returns the interval of A4 and B4 which is Major Second.\n"
                        + "findInterval(c6, g#7)\n    Returns the interval of C6 and G#7 which "
                        + "is Minor Thirteenth.",
                "findInterval(a, b)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor for class.
     *
     * @param args Given arguments from the invoker.
     */
    public FindInterval(List<String> args) {
        arguments = args;
    }

    /**
     * Execute the find interval command.
     *
     * @param env The Environment.
     */
    public void execute(Environment env) {
        if (arguments.size() <= 1) {
            env.error(Error.FINDINTERVALTOOFEWARGUMENTS.getError());
        } else if (arguments.size() > 2) {
            env.error(Error.FINDINTERVALTOOMANYARGUMENTS.getError());
        } else {
            try {
                XinityNote note1 = new XinityNote(arguments.get(0));
                XinityNote note2 = new XinityNote(arguments.get(1));

                Integer midi1 = NoteUtil.convertToMidi(note1);
                Integer midi2 = NoteUtil.convertToMidi(note2);

                if (midi1 > midi2) {
                    env.error(Error.FINDINTERVALNOTEHIGHER.getError());
                    return;
                }
                if (!NoteUtil.isValidMidiRange(midi1) || !NoteUtil.isValidMidiRange(midi2)) {
                    env.error(Error.INVALIDMIDINOTE.getError());
                    return;
                }
                if ((midi2 - midi1) > 24) {
                    env.error(Error.FINDINTERVALINVALIDINTERVAL.getError());
                }
                env.println(Interval.toString(IntervalUtil.findInterval(note1, note2)));

            } catch (Exception e) {
                env.error(Error.FINDINTERVALINVALIDARGUMENTS.getError());
            }
        }
    }
}
