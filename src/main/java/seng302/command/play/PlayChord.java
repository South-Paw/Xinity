package seng302.command.play;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.ChordUtil;
import seng302.util.NoteUtil;
import seng302.util.PlayServiceUtil;
import seng302.util.enumerator.ChordQuality;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.List;

/**
 * Play Chord command class.
 *
 * @author mvj14
 */
public class PlayChord implements Command {

    private List<String> arguments;
    private XinityNote root;
    private Integer rootMidi;
    private ChordQuality chordQuality;
    private List<XinityNote> chord;
    private Boolean isArpeggio;
    private List<XinityNote> chordArray;
    private List<Integer> noteList;
    private String output;

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                "Play Chord",
                CommandCategory.PLAY,
                "playChord(note, quality[, arpeggio|unison])",
                "Plays a chord based on a given note and quality.",
                "note\n    A valid note.\n"
                    + "quality\n    Can be one of the following; \"major\", \"minor\" or "
                    + "\"natural minor\".\n"
                    + "arpeggio\n    Plays the notes of the chord sequentially.\n"
                    + "unison\n    Plays the notes of the chord simultaneously. Played by default.",
                "playChord(C4, major)\n    Plays the chord of C4 Major.\n"
                    + "playChord(E4, \"natural minor\", arpeggio)\n    Plays the arpeggio chord of "
                    + "E4 Natural Minor.",
                "playChord(C4, major)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor for class.
     *
     * @param args Arguments from the environment.
     */
    public PlayChord(List<String> args) {
        arguments = args;
        isArpeggio = false;
        noteList = new ArrayList<>();
        chordArray = new ArrayList<>();
    }

    /**
     * Execute the Play scale command.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        if (arguments.size() < 2 || arguments.size() > 3) {
            env.error(Error.CHORDINVALIDNUMARGS.getError());
            return;
        }

        String quality = arguments.get(1).toLowerCase();

        if (ChordQuality.fromString(quality) == null) {
            env.error(Error.CHORDINVALIDQUALITY.getError());
            return;
        }

        try {
            root = new XinityNote(arguments.get(0));
            if (!root.isValidMidi()) {
                env.error(Error.INVALIDMIDINOTE.getError());
                return;
            }
        } catch (Exception e) {
            env.error(e.getMessage());
            return;
        }

        chordQuality = ChordQuality.fromString(quality);

        chord = ChordUtil.chord(root, chordQuality);
        if (chord == null) {
            env.error(Error.CHORDDOESNOTEXIST.getError());
            return;
        }

        output = ChordUtil.getChordString(chord, root.hasOctave());

        if (arguments.size() == 3) {
            if (arguments.get(2).toLowerCase().equals("arpeggio")) {
                isArpeggio = true;
            } else if (arguments.get(2).toLowerCase().equals("unison")) {
                isArpeggio = false;
            } else {
                env.error(Error.PLAYCHORDINVALIDARP.getError());
                return;
            }
        }

        env.println(output);

        if (isArpeggio) {
            PlayServiceUtil.playArpeggioChord(chord);
        } else {
            PlayServiceUtil.playUnisonChord(chord);
        }
    }
}
