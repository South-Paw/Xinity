package seng302.command.play;

import static seng302.util.enumerator.ScaleType.BLUES;
import static seng302.util.enumerator.ScaleType.MAJOR;
import static seng302.util.enumerator.ScaleType.MINOR;
import static seng302.util.enumerator.ScaleType.PENTATONICMAJOR;
import static seng302.util.enumerator.ScaleType.PENTATONICMINOR;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.NoteUtil;
import seng302.util.PlayServiceUtil;
import seng302.util.ScalesUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.enumerator.ScaleDirection;
import seng302.util.enumerator.ScaleType;
import seng302.util.object.CommandManual;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.List;

/**
 * Plays the specified scale type, on the given starting note, with direction of up, down
 * or updown.
 *
 * @author wrs35, ljm163
 */
public class PlayScale implements Command {
    private List<String> arguments;
    private List<XinityNote> scaleArray;
    private XinityNote tempXinityNote;
    private XinityNote xinityStartNote;
    private ScaleType scaleType;
    private ScaleDirection scaleDirection;
    private String scaleString;
    private String scalePlayType = "straight";
    private Boolean hasOctave = false;

    private static Integer numberOfOctaves;

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                    "Play Scale",
                    CommandCategory.PLAY,
                    "playScale(note, scaleType[, scaleDirection][, octaves][, playStyle])",
                    "Plays a scale given a starting note and scale type.",
                    "note\n    A valid note.\n"
                            + "scaleType\n    "
                            + "One of the following; \"major\", \"minor\", \"natural minor\","
                            + " \"pentatonic major\", \"pentatonic minor\", \"blues\""
                            + ", \"melodic minor\", \"harmonic minor\" or a scale mode.\n"
                            + "scaleDirection\n    "
                            + "One of the following; \"up\", \"down\" or \"both\". "
                            + "\"up\" is played by default.\n"
                            + "octaves\n    The number of octaves of the scale played. "
                            + "One octave is played by default.\n"
                            + "playStyle\n    Play as the default \"straight\" or \"swing\".",
                    "playScale(C4, major, down)\n    "
                            + "Plays the scale C4 major descending.\n"
                            + "playScale(D5, minor, 2)\n    "
                            + "Plays two octaves of the scale D5 minor.",
                    "playScale(C4, major)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor class for PlayScale. Default values for 3rd argument and 4th argument are
     * used if none are given.
     *
     * @param args Arguments passed to this command.
     */
    public PlayScale(List<String> args) {
        arguments = args;
        scaleDirection = ScaleDirection.UP;
        scaleString = "";
        numberOfOctaves = 1;
    }

    /**
     * Executes the playStraightScale() command.
     *
     * @param env The Environment.
     */
    public void execute(Environment env) {
        if (arguments.size() > 1 && arguments.size() < 6) {
            //check input note
            try {
                tempXinityNote = new XinityNote(arguments.get(0));

                if (tempXinityNote.isValidMidi()) {
                    xinityStartNote = tempXinityNote;
                } else {
                    env.error(Error.INVALIDMIDINOTE.getError());
                    return;
                }
            } catch (Exception e) {
                env.error(Error.INVALIDNOTE.getError());
                return;
            }

            if (xinityStartNote.hasOctave()) {
                hasOctave = true;
            }

            // Makes the start note think it was supplied with an octave if it wasn't
            try {
                xinityStartNote = new XinityNote(xinityStartNote.getLetterAndAccidental()
                        + xinityStartNote.getOctave());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            scaleType = ScaleType.fromString(arguments.get(1));
            if (scaleType == null) {
                env.error(Error.INVALIDSCALETYPE.getError());
                return;
            }

            /* Need to check whether third argument is a direction
               or specifying number of octaves */
            if (arguments.size() == 3) {
                try {
                    // Check if numerical
                    numberOfOctaves = Integer.parseInt(arguments.get(2));
                } catch (Exception e) {
                    //Check if valid direction
                    if (ScaleDirection.fromString(arguments.get(2)) == null) {
                        if (arguments.get(2).toLowerCase().equals("swing")) {
                            scalePlayType = "swing";
                        } else if (arguments.get(2).toLowerCase().equals("straight")) {
                            scalePlayType = "straight";
                        } else {
                            env.error(String.format(Error.PLAYSCALETHIRDARGONE.getError(),
                                    arguments.get(2)));
                            return;
                        }
                    } else {
                        scaleDirection = ScaleDirection.fromString(arguments.get(2));

                    }
                }
            } else if (arguments.size() == 4) {
                /* Scale Direction is always third. Number of Octaves is always last. */
                scaleDirection = ScaleDirection.fromString(arguments.get(2));
                if (scaleDirection == null) {
                    env.error(String.format(Error.PLAYSCALETHIRDARGTWO.getError(),
                            arguments.get(2)));
                    return;
                }
                try {
                    numberOfOctaves = Integer.parseInt(arguments.get(3));
                } catch (Exception e) {
                    if (arguments.get(3).toLowerCase().equals("swing")) {
                        scalePlayType = "swing";
                    } else if (arguments.get(3).toLowerCase().equals("straight")) {
                        scalePlayType = "straight";
                    } else {
                        env.error(String.format(Error.PLAYSCALEFOURTHARG.getError(),
                                arguments.get(3)));
                        return;
                    }
                }
            } else if (arguments.size() == 5) {
                scaleDirection = ScaleDirection.fromString(arguments.get(2));
                if (scaleDirection == null) {
                    env.error(String.format(Error.PLAYSCALETHIRDARGTWO.getError(),
                            arguments.get(2)));
                    return;
                }

                try {
                    numberOfOctaves = Integer.parseInt(arguments.get(3));
                } catch (Exception e) {
                    env.error(String.format(Error.PLAYSCALEFOURTHARG.getError(),
                            arguments.get(3)));
                    return;
                }

                if (arguments.get(4).toLowerCase().equals("swing")) {
                    scalePlayType = "swing";
                } else if (arguments.get(4).toLowerCase().equals("straight")) {
                    scalePlayType = "straight";
                } else {
                    env.error(String.format(Error.PLAYSCALEFIFTHARG.getError(),
                            arguments.get(4)));
                    return;
                }
            }

            List<XinityNote> scaleArray = ScalesUtil.buildPlayingScale(
                    xinityStartNote, scaleType, scaleDirection, numberOfOctaves);

            if (scaleType == MAJOR || scaleType == MINOR) {
                if (scaleArray == null) {
                    env.error(Error.FINDSCALENOSCALE.getError());
                    return;
                }
            } else if (scaleType == PENTATONICMINOR || scaleType == PENTATONICMAJOR) {
                if (scaleArray == null) {
                    env.error(Error.INVALIDNOCORRESPONDINGPENTATONIC.getError());
                    return;
                }
            } else if (scaleType == BLUES) {
                if (scaleArray == null) {
                    env.error(Error.INVALIDNOCORRESPONDINGBLUES.getError());
                    return;
                }
            } else { // Currently: the Major Scale Modes
                if (scaleArray == null) {
                    env.error(Error.NOMODE.getError());
                    return;
                }
            }

            if (scalePlayType.equals("straight")) {
                PlayServiceUtil.playStraightScale(scaleArray);
            } else {
                PlayServiceUtil.playSwingScale(scaleArray);
            }

            //Print the found scale to the transcript as string of notes.
            env.println(ScalesUtil.getScaleString(scaleArray, hasOctave));

        } else {
            env.error(String.format(Error.PLAYSCALEINVALIDNUMARGS.getError(), arguments.size()));
        }
    }
}
