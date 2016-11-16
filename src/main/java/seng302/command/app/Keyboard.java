package seng302.command.app;

import seng302.Environment;
import seng302.command.Command;
import seng302.controllers.MainSceneController;
import seng302.util.enumerator.CommandCategory;
import seng302.util.object.CommandManual;

/**
 * Keyboard command. Displays the keyboard GUI in a separate window.
 *
 * @author ljm163
 */
public class Keyboard implements Command {

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                "Keyboard",
                CommandCategory.PROJECT,
                "keyboard()",
                "Launches the application's keyboard.",
                "",
                "keyboard()\n    Launch the keyboard.",
                "keyboard()"
            );
        }

        return commandManual;
    }

    /**
     * Command executor.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        MainSceneController.getInstance().openKeyboard(null);
    }
}
