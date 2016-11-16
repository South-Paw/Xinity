package seng302.command.term;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.TermsUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;
import seng302.util.object.Term;

import java.util.List;

/**
 * Term Category command.
 * Given a term name, retrieves the category of that term if it's in the Terms Dictionary.
 *
 * @author plr37, adg62
 */
public class TermCategory implements Command {
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
                "Category of Term",
                CommandCategory.TERM,
                "termCategory(term)",
                "Retrieves the category of the given term.",
                "term\n    A term defined in the dictionary.",
                "termCategory(arpeggio)\n    Retrieves the category of arpeggio.\n"
                        + "termCategory(allegro)\n    Retrieves the category of allegro.",
                "termCategory(arpeggio)"
            );
        }

        return commandManual;
    }

    /**
     * Command constructor.
     *
     * @param args Given from the invoker.
     */
    public TermCategory(List<String> args) {
        arguments = args;
    }

    /**
     * Execute the term category command.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        try {
            if (arguments.size() != 1) {
                env.error(Error.TERMLOOKUP.getError());
            } else {
                String termName = arguments.get(0);
                Term termsCategory = TermsUtil.readTerm(termName);

                if (termsCategory != null) {
                    env.println("The category of \"" + termName + "\" is: \""
                            + termsCategory.getCategory() + "\"");
                } else {
                    env.error(String.format(Error.TERMNOTDEFINED.getError(), termName));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            env.error(Error.TERMLOOKUP.getError());
        }
    }
}
