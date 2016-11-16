package seng302.command.app;

import org.apache.commons.lang3.text.WordUtils;
import seng302.App;
import seng302.Environment;
import seng302.command.Command;
import seng302.util.PlayServiceUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.map.CommandManualMap;
import seng302.util.map.SongMap;
import seng302.util.object.CommandManual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * The help command.
 *
 * @author avh17.
 */
public class Help implements Command {

    private static CommandManual commandManual;
    private List<String> arguments;
    private Boolean displayAll;
    private List<CommandManual> map = CommandManualMap.getManualsMapSortedAlphabetically();
    private Map<String, CommandManual> rawMap = CommandManualMap.getRawMap();

    private List<List<String>> app = new ArrayList<>();
    private List<List<String>> musical = new ArrayList<>();
    private List<List<String>> play = new ArrayList<>();
    private List<List<String>> tutor = new ArrayList<>();
    private List<List<String>> term = new ArrayList<>();

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                "Help",
                CommandCategory.PROJECT,
                "help([commandName])",
                "Returns the list of all commands.",
                "commandName\n    The capitalized name of the command wrapped in quotes.\n",
                "help()\n    Returns the list of all commands.",
                "help()"
            );
        }
        return commandManual;
    }

    /**
     * Constructor that takes and assigns arguments.
     *
     * @param args Arguments from the command.
     */
    public Help(List<String> args) {
        this.arguments = args;
        this.displayAll = false;
    }

    /**
     * Constructor that takes and assigns arguments.
     */
    public Help() {
        this.displayAll = true;
    }

    /**
     * Execute function.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        // Populate lists
        if (app.size() == 0) {
            populateCommandLists();
        }
        
        // Display all commands 
        if (displayAll) {
            if (App.getInServerMode()) {
                env.println("<br>--- App ---<br>");
                for (List<String> command: app) {
                    env.println(command.get(0) + "<br>");
                }

                env.println("<br>--- Musical ---<br>");
                for (List<String> command: musical) {
                    env.println(command.get(0) + "<br>");
                }

                env.println("<br>--- Play ---<br>");
                for (List<String> command: play) {
                    env.println(command.get(0) + "<br>");
                }

                env.println("<br>--- Tutor ---<br>");
                for (List<String> command: tutor) {
                    env.println(command.get(0) + "<br>");
                }

                env.println("<br>--- Term ---<br>");
                for (List<String> command: term) {
                    env.println(command.get(0) + "<br>");
                }
            } else {
                env.println("\n--- App ---\n");
                for (List<String> command: app) {
                    env.println(command.get(0));
                }

                env.println("\n--- Musical ---\n");
                for (List<String> command: musical) {
                    env.println(command.get(0));
                }

                env.println("\n--- Play ---\n");
                for (List<String> command: play) {
                    env.println(command.get(0));
                }

                env.println("\n--- Tutor ---\n");
                for (List<String> command: tutor) {
                    env.println(command.get(0));
                }

                env.println("\n--- Term ---\n");
                for (List<String> command: term) {
                    env.println(command.get(0));
                }
            }
        } else {
            for (String command : arguments) {
                try {
                    if (command.equalsIgnoreCase("beethoven")) {
                        PlayServiceUtil.playSong(SongMap.getBeethoven());
                    } else if (command.equalsIgnoreCase("you cant see me")) {
                        PlayServiceUtil.playSong(SongMap.getJohnCena());
                    } else if (command.equalsIgnoreCase("the north remembers")) {
                        PlayServiceUtil.playSong(SongMap.getGameOfThrones());
                    } else {
                        env.println(
                            rawMap.get(WordUtils.capitalize(command)).getName() + "\n");
                        env.println(
                            rawMap.get(WordUtils.capitalize(command)).getSynopsis() + "\n");
                        env.println(
                            rawMap.get(WordUtils.capitalize(command)).getDescription() + "\n");
                        env.println(
                            rawMap.get(WordUtils.capitalize(command)).getArguments() + "\n");
                    }
                } catch (Exception e) {
                    env.error("Command does not exist.");
                }

            }
        }
    }

    /**
     * Populates the command lists.
     */
    private void populateCommandLists() {
        for (CommandManual manual : map) {
            if (manual.getCategory() == CommandCategory.PROJECT) {
                app.add(Arrays.asList(manual.getSynopsis(), manual.getDescription()));
            } else if (manual.getCategory() == CommandCategory.MUSICAL) {
                musical.add(Arrays.asList(manual.getSynopsis(), manual.getDescription()));
            } else if (manual.getCategory() == CommandCategory.PLAY) {
                play.add(Arrays.asList(manual.getSynopsis(), manual.getDescription()));
            } else if (manual.getCategory() == CommandCategory.TUTOR) {
                tutor.add(Arrays.asList(manual.getSynopsis(), manual.getDescription()));
            } else if (manual.getCategory() == CommandCategory.TERM) {
                term.add(Arrays.asList(manual.getSynopsis(), manual.getDescription()));
            }
        }
    }
}
