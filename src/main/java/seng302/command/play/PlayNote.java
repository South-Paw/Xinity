package seng302.command.play;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.NoteUtil;
import seng302.util.PlayServiceUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.List;

/**
 * PlayNote command.
 * Outputs audio of a given note.
 *
 * @author jps100, adg62
 */
public class PlayNote implements Command {
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
                "Play Note",
                CommandCategory.PLAY,
                "play(number|note[, duration])",
                "Given a midi number or a note, command will play the given or corresponding midi "
                    + "note.",
                "number\n    A valid midi number.\n"
                    + "note\n    A valid note.\n"
                    + "duration\n    Optional, a duration to play for in milliseconds.",
                "play(C4)\n    Plays the midi equivalent of C4, 60.\n"
                    + "play(60)\n    Plays the midi note 60.",
                "play(C4)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor class for PlayNote.
     * Adds the default 2nd argument if none is given.
     *
     * @param args Arguments passed to this command.
     */
    public PlayNote(List<String> args) {
        arguments = args;
    }

    /**
     * Executes the playNote() command.
     *
     * @param env The Environment.
     */
    public void execute(Environment env) {
        // Lower bound of time in milliseconds.
        Integer lowerTimeBound = 0;

        // Upper bound of time in milliseconds.
        Integer upperTimeBound = 10000;

        if (arguments.size() >= 1) {
            if (arguments.size() > 2) {
                env.error(Error.PLAYNOTETOOMANYPARAMS.getError());
            } else if (arguments.size() == 1) {
                // play with calculated duration
                if (arguments.get(0).matches("\\d+")) {
                    // play midi
                    Integer midi = Integer.parseInt(arguments.get(0));

                    if (!NoteUtil.isValidMidiRange(midi)) {
                        env.error(Error.INVALIDMIDINOTE.getError());
                    } else {
                        env.println("Playing: " + midi);
                        playMidi(midi, -1);
                    }
                } else {
                    // play note
                    try {
                        XinityNote note = new XinityNote(arguments.get(0));
                        if (!note.isValidMidi()) {
                            env.error(Error.INVALIDMIDINOTE.getError());
                        } else {
                            env.println("Playing: " + note.getNote());
                            playMidi(NoteUtil.convertToMidi(note), -1);
                        }
                    } catch (Exception e) {
                        env.error(Error.INVALIDMIDINOTE.getError());
                    }
                }
            } else if (arguments.size() == 2) {
                // play with given duration
                Integer inputNoteDuration = Integer.parseInt(arguments.get(1));

                if ((inputNoteDuration < lowerTimeBound) || (inputNoteDuration > upperTimeBound)) {
                    env.error(Error.PLAYNOTEINVALIDDURATION.getError());
                } else {
                    if (arguments.get(0).matches("\\d+")) {
                        // play midi
                        Integer midi = Integer.parseInt(arguments.get(0));

                        if (!NoteUtil.isValidMidiRange(midi)) {
                            env.error(Error.INVALIDMIDINOTE.getError());
                        } else {
                            env.println("Playing: " + midi);
                            playMidi(midi, inputNoteDuration);
                        }
                    } else {
                        // play note
                        try {
                            XinityNote note = new XinityNote(arguments.get(0));

                            env.println("Playing: " + note.getNote());
                            playMidi(NoteUtil.convertToMidi(note), inputNoteDuration);
                        } catch (Exception e) {
                            env.error(Error.INVALIDNOTE.getError());
                        }
                    }
                }
            }
        } else {
            env.error(Error.PLAYNOTENOPARAMS.getError());
        }
    }

    private void playMidi(Integer midi, Integer duration) {
        PlayServiceUtil.playMidi(midi, duration);
    }
}
