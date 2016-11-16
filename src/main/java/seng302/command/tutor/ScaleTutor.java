package seng302.command.tutor;

import seng302.Environment;
import seng302.command.Command;
import seng302.controllers.MainSceneController;
import seng302.controllers.scaletutor.ScaleTutorSceneController;
import seng302.tutor.ScaleTutorTestGenerator;
import seng302.tutor.SignatureTutorTestGenerator;
import seng302.tutor.Tutor;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * Class for handling the Scale Tutor command.
 *
 * @author ljm163
 */
public class ScaleTutor implements Command {

    private static ScaleTutor scaleTutor;
    private List<String> arguments;

    private Integer scaleTests = 1;
    private Integer numberOfOctaves = 1;
    private String direction = "up";
    private String playStyle = "straight";
    private Boolean includeModes = false;
    private Boolean includeHarmonic = false;
    private List<String> directions = Arrays.asList("up", "down", "both");
    private List<String> playStyles = Arrays.asList("straight", "swing");

    private static CommandManual commandManual;
    private ScaleTutorTestGenerator tutor = new ScaleTutorTestGenerator();

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                "Scale Tutor",
                CommandCategory.TUTOR,
                "scaleTutor([number][, direction][, octaves][, playStyle][, modes])",
                "Run a Scale Recognition Test in the Scale Tutor tab.",
                "number\n    Optional param, defaults to 1. The \"x\" character followed by the "
                    + "number of tests to run (see examples if unclear).\n"
                    + "direction\n    Optional param, defaults to \"up\". The direction the "
                    + "notes are to be played in. Can be \"up\", \"down\" or \"both\".\n"
                    + "octaves\n    Optional param, defaults to 1. The number of octaves to "
                    + "use in the test.\n"
                    + "playStyle\n   Optional param, defaults to \"straight\". The style to run "
                    + "the test in, can be \"straight\" or \"swing\".\n"
                    + "modes\n   Optional param, flag to include scale modes.\n"
                    + "harmonic\n   Optional param, flag to include harmonic minor scales.\n",
                "scaleTutor()\n    Run the scale tutor with default arguments.\n"
                    + "scaleTutor(x10)\n    Run the scale tutor with 10 tests.\n"
                    + "scaleTutor(updown, 4, swing)\n    Run the scale tutor with the default "
                    + "number of tests, playing updown swing with 4 octaves.",
                "scaleTutor(x10)"
            );
        }

        return commandManual;
    }

    /**
     * Gets the class instance.
     *
     * @return The class instance.
     */
    public static ScaleTutor getInstance() {
        return scaleTutor;
    }

    /**
     * The constructor for the class with no passed arguments.
     */
    public ScaleTutor() {
        scaleTutor = this;
    }

    /**
     * The constructor for the class with passed arguments.
     *
     * @param args The arguments given in the command.
     */
    public ScaleTutor(List<String> args) {
        arguments = args;
        scaleTutor = this;
    }

    /**
     * The execute function.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        if (arguments != null) {
            List<String> dummyList = new ArrayList<>();
            dummyList.addAll(arguments);
            for (String argument: arguments) {
                if (isNumberOfScales(argument)) {
                    scaleTests = Integer.parseInt(argument.substring(1));
                    dummyList.remove(argument);
                } else if (isDirection(argument)) {
                    direction = argument;
                    dummyList.remove(argument);
                } else if (isPlayStyle(argument)) {
                    playStyle = argument;
                    dummyList.remove(argument);
                } else if (isModes(argument)) {
                    includeModes = true;
                    dummyList.remove(argument);
                } else if (isHarmonic(argument)) {
                    includeHarmonic = true;
                    dummyList.remove(argument);
                } else {
                    try {
                        numberOfOctaves = Integer.parseInt(argument);
                        dummyList.remove(argument);
                    } catch (Exception e) {
                        env.error(Error.SCALETUTORINVALIDARGS.getError());
                        return;
                    }
                }
            }

            if (dummyList.size() > 0) {
                env.error(Error.SCALETUTORINVALIDARGS.getError());
                return;
            }
        }

        //Switch to scale tutor tab, reset to defaults, start pitch comparison test
        env.println("Starting Scale Tutor...");
        tutor.startTutorTest(scaleTests);
    }

    /**
     * Used to determine whether the argument is a valid number of scales parameter.
     *
     * @param argument The argument.
     * @return A boolean value indicating whether the argument is valid.
     */
    private Boolean isNumberOfScales(String argument) {
        //Check whether the argument has the 'x' identifier in front of it
        if (argument.substring(0, 1).equals("x")) {
            try {
                Integer.parseInt(argument.substring(1));
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Used to determine whether the argument is a valid scale direction parameter.
     *
     * @param argument The argument.
     * @return A boolean value indicating whether the argument is valid.
     */
    private Boolean isDirection(String argument) {
        return directions.contains(argument.toLowerCase());
    }

    /**
     * Used to determine whether the argument is a valid scale play style parameter.
     *
     * @param argument The argument.
     * @return A boolean value indicating whether the argument is valid.
     */
    private Boolean isPlayStyle(String argument) {
        return playStyles.contains(argument.toLowerCase());
    }

    /**
     * Used to determine if an argument is the mode flag.
     *
     * @param argument The argument.
     * @return A boolean value.
     */
    private Boolean isModes(String argument) {
        return argument.equalsIgnoreCase("modes");
    }

    /**
     * Used to determine if an argument is the harmonic flag.
     *
     * @param argument The argument.
     * @return A boolean value.
     */
    private Boolean isHarmonic(String argument) {
        return argument.equalsIgnoreCase("harmonic");
    }

    /**
     * Get the tutor.
     *
     * @return The tutor object.
     */
    public ScaleTutorTestGenerator getTutor() {
        return tutor;
    }

    /**
     * Used to get the number of octaves.
     *
     * @return The number of octaves.
     */
    public Integer getNumberOfOctaves() {
        return numberOfOctaves;
    }

    /**
     * Used to get the direction.
     *
     * @return The direction.
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Used to get the play style.
     *
     * @return The play style.
     */
    public String getPlayStyle() {
        return playStyle;
    }

    /**
     * Used to determine whether to use modes.
     *
     * @return A boolean value.
     */
    public Boolean includeModes() {
        return includeModes;
    }

    /**
     * Used to determine include harmonic minor scales.
     *
     * @return A boolean value.
     */
    public Boolean includeHarmonic() {
        return includeHarmonic;
    }

}