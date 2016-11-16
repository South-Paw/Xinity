package seng302;

/**
 * Command environment handles command interactions with the application.
 * Example: if a command calls a println function, the environment ensures that it ends up in the
 * transcript area.
 *
 * @author adg62
 */
public class Environment {
    private String output = "";

    public String getOutput() {
        return output;
    }

    /**
     * Print line which includes new line at end.
     *
     * @param string The string (command output) we're printing to the transcript area.
     */
    public void println(String string) {
        output += String.format("%s%n", string);
    }

    /**
     * Error which adds command errors to the transcript area.
     *
     * @param errorMessage The string of the error we're printing to the transcript area.
     */
    public void error(String errorMessage) {
        output += String.format("[ERROR] %s%n", errorMessage);
    }
}
