package seng302.command.app;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.TempoUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;

import java.util.List;

/**
 * SetTempo command.
 * Sets the app's tempo to the given tempo, after it has been checked with range (20-400).
 *
 * @author ljm163
 */
public class SetTempo implements Command {
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
                "Set Tempo",
                CommandCategory.PROJECT,
                "setTempo(tempo[, f])",
                "Sets the current Project's tempo to the given speed if it's within the range "
                        + "of 20 to 400.\nFor values outside of this range an `f` argument can "
                        + "be specified to indicate an override of the default range.",
                "tempo\n    Value of the new bpm.\n"
                        + "f\n    Optional argument to override the range.",
                "setTempo(120)\n   Set the tempo to a speed of 120 bpm.\n"
                        + "setTempo(500, f)\n   Override the range checking and set the tempo "
                        + "to a speed of 500 bpm.",
                "setTempo(120)"
            );
        }

        return commandManual;
    }

    /**
     * Constructor that takes and assigns arguments.
     *
     * @param args Arguments from the command.
     */
    public SetTempo(List<String> args) {
        this.arguments = args;
    }

    /**
     * Execute command.
     *
     * @param env The Environment
     */
    public void execute(Environment env) {
        Integer lowerBound = 20;
        Integer upperBound = 400;

        try {
            if (arguments.size() == 2) {
                // "f" override is given as second parameter and the tempo is positive.
                if (arguments.get(1).equals("f") && Integer.parseInt(arguments.get(0)) > 0) {
                    TempoUtil.setTempo(Integer.parseInt(arguments.get(0)));

                    env.println("Tempo set to: " + TempoUtil.getTempo() + " bpm");
                } else {
                    env.error(Error.SETTEMPOINVALIDARGS.getError());
                }
            } else if (arguments.size() == 1) {
                Integer tempo = Integer.parseInt(arguments.get(0));

                // Check tempo lies within "sensible range".
                if (tempo >= lowerBound && tempo <= upperBound) {
                    TempoUtil.setTempo(Integer.parseInt(arguments.get(0)));

                    env.println("Tempo set to: " + TempoUtil.getTempo() + " bpm");
                } else {
                    env.error(String.format(Error.SETTEMPOOUTSIDERANGE.getError(),
                            lowerBound, upperBound));
                }
            } else {
                env.error(Error.SETTEMPOINVALIDARGS.getError());
            }
        } catch (Exception e) {
            env.error(Error.SETTEMPOINVALIDTEMPO.getError());
        }
    }
}
