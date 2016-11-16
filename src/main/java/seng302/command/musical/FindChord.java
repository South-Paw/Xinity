package seng302.command.musical;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.ChordUtil;
import seng302.util.NoteUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * FindChord command class. Given the notes of a chord as the command's arguments, the command will
 * determine the chord name. The command will manage any chord inversions given.
 *
 * @author plr37, ljm163
 */
public class FindChord implements Command {
    private List<String> arguments;
    private String result;
    private Boolean inversion;
    private Boolean enharmonics;

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                    "Find Chord",
                    CommandCategory.MUSICAL,
                    "findChord(note1, note2, note3, [note4, ], [inversion, ], [noEnharmonic, ])",
                    "Determines if the given notes produce a valid chord and returns it.",
                    "note1-4\n    A valid note.\n"
                            + "inversion\n    "
                            + "Optional parameter, displays what inversion is given.\n"
                            + "noEnharmonic\n    Restricts output to one chord.",
                    "findChord(E, G, C)\n"
                            + "    Finds the chord C major\n"
                            + "findChord(E, G, C, inversion)\n"
                            + "    Finds the chord C major 1st inversion\n"
                            + "findChord(C, E, G)\n"
                            + "    Finds the chord C major",
                    "findChord(C, E, G)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor for class.
     *
     * @param args Arguments from the environment.
     */
    public FindChord(List<String> args) {
        arguments = args;
        inversion = false;
        enharmonics = true; // used in chord finder (enhanced)
    }

    /**
     * Execute the FindChord command.
     *
     * @param env the environment.
     */
    public void execute(Environment env) {
        Integer numberOfNotes = 3;

        // accept only 3, 4, 5 or 6 arguments
        if (arguments.size() < 3 || arguments.size() > 6) {
            env.error(Error.FINDCHORDINVALIDARGUMENTS.getError());
            return;
        }

        // 3 notes with flag, or 4 notes
        if (arguments.size() == 4) {
            if (arguments.get(3).equalsIgnoreCase("inversion")) {
                inversion = true;
            } else if (arguments.get(3).equalsIgnoreCase("noEnharmonic")) {
                enharmonics = false;
            } else {
                numberOfNotes = 4;
            }
        }

        // 3 notes with both flags, or 4 notes with one flag
        if (arguments.size() == 5) {
            if (arguments.get(3).equalsIgnoreCase("inversion")
                    && arguments.get(4).equalsIgnoreCase("noEnharmonic")) {

                numberOfNotes = 3;
                inversion = true;
                enharmonics = false;

            } else if (arguments.get(3).equalsIgnoreCase("noEnharmonic")
                    && arguments.get(4).equalsIgnoreCase("inversion")) {

                numberOfNotes = 3;
                inversion = true;
                enharmonics = false;

            } else if (arguments.get(4).equalsIgnoreCase("inversion")) {
                numberOfNotes = 4;
                inversion = true;
            } else if (arguments.get(4).equalsIgnoreCase("noEnharmonic")) {
                numberOfNotes = 4;
                enharmonics = false;
            } else {
                env.error(Error.FINDCHORDINVALIDARGUMENTS.getError());
                return;
            }
        }

        // 4 notes are given with both flags
        if (arguments.size() == 6) {
            if (arguments.get(4).equalsIgnoreCase("inversion")
                    && arguments.get(5).equalsIgnoreCase("noEnharmonic")) {
                numberOfNotes = 4;
                inversion = true;
                enharmonics = false;
            } else if (arguments.get(4).equalsIgnoreCase("noEnharmonic")
                    && arguments.get(5).equalsIgnoreCase("inversion")) {
                numberOfNotes = 4;
                inversion = true;
                enharmonics = false;
            } else {
                env.error(Error.FINDCHORDINVALIDARGUMENTS.getError());
                return;
            }
        }


        // findChord command accepts 3 or 4 notes and an optional inversion and enharmonic flags.
        for (String arg : arguments.subList(0, numberOfNotes)) {
            try {
                XinityNote note = new XinityNote(arg);
                if (note.hasOctave()) {
                    env.error(Error.FINDCHORDNOOCTAVES.getError());
                    return;
                }
            } catch (Exception e) {
                env.error(Error.INVALIDNOTE.getError());
                return;
            }
        }

        Set<String> chords = ChordUtil.findChord(arguments.subList(0, numberOfNotes), inversion);
        String output;
        if (chords != null && chords.size() != 0) {
            if (enharmonics) {
                output = chords.toString();
                output = output.substring(1, output.length() - 1); // remove brackets from list
            } else {
                output = chords.iterator().next(); // first chord of array
            }
            env.println(output);
        } else {
            env.error(Error.FINDCHORDINVALIDARGUMENTS.getError());
        }
    }
}