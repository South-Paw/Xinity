package seng302.command.play;

import seng302.Environment;

import seng302.command.Command;
import seng302.util.IntervalUtil;
import seng302.util.NoteUtil;
import seng302.util.PlayServiceUtil;
import seng302.util.SemitoneUtil;
import seng302.util.TempoUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.enumerator.Interval;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.List;

/**
 * PlayInterval Command.
 * Plays the audio of the two notes in an interval given interval and starting note.
 *
 * @author plr37, ljm163
 */
public class PlayInterval implements Command {
    private List<String> arguments;

    private XinityNote tonic;
    private XinityNote endNote;
    private Integer numberOfSemitones;

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                    "Play Interval",
                    CommandCategory.PLAY,
                    "playInterval(note, interval)",
                    "Given an note and interval"
                            + ", the command will play the two notes of the interval.",
                    "interval\n    An interval from the interval list.\n"
                            + "note\n    A valid note.",
                    "playInterval(C4, \"augmented second\")\n    "
                            + "Plays the two notes in the augmented second interval.\n"
                            + "playInterval(D5, \"major 9th\")\n    "
                            + "Plays the two notes in the major 9th interval.",
                    "playInterval(C4, \"augmented second\")"
            );
        }

        return commandManual;
    }

    /**
     * Constructor that takes and assigns arguments.
     *
     * @param args Arguments from the command.
     */
    public PlayInterval(List<String> args) {
        this.arguments = args;
    }

    /**
     * Execute command.
     *
     * @param env The Environment
     */
    public void execute(Environment env) {
        if (arguments.size() != 2) {
            env.error(Error.INVALIDINTERVALARGUMENTS.getError());
            return;
        }
        try {
            tonic = new XinityNote(arguments.get(0));
        } catch (Exception e) {
            env.error(Error.INVALIDNOTE.getError());
            return;
        }


        Interval interval = Interval.fromString(arguments.get(1));
        if (interval == null) {
            try {
                numberOfSemitones = Integer.parseInt(arguments.get(1));
                if (numberOfSemitones > 24 || numberOfSemitones < 0) {
                    env.error(Error.PLAYINTERVALINVALIDNUMSEMITONES.getError());
                    return;
                }
            } catch (Exception e) {
                env.error(Error.INTERVALDOESNOTEXIST.getError());
                return;
            }
        }

        if (numberOfSemitones == null) {
            endNote = IntervalUtil.interval(tonic, interval);
        } else {
            endNote = SemitoneUtil.neighbouringNote(numberOfSemitones, tonic);
        }

        if (!tonic.isValidMidi() || !endNote.isValidMidi()) {
            env.error(Error.INVALIDMIDINOTE.getError());
            return;
        }

        // Play interval
        PlayServiceUtil.playInterval(
                NoteUtil.convertToMidi(tonic), NoteUtil.convertToMidi(endNote));

        env.println(tonic.getNote() + " and " + endNote.getNote());
    }
}
