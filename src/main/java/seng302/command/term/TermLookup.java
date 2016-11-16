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
 * Term Lookup command.
 * Given a term name, retrieves the details of that term if it's in the Terms Dictionary.
 *
 * @author plr37, adg62
 */
public class TermLookup implements Command {
    private List<String> arguments;

    private String termLayout = "Term: %s\nLanguage: %s\nMeaning: %s\nCategory: %s";

    private static CommandManual commandManual;

    /**
     * Get the command manual for this command.
     *
     * @return The command manual object for this command.
     */
    public static CommandManual getCommandManual() {
        if (commandManual == null) {
            commandManual = new CommandManual(
                "Lookup Term",
                CommandCategory.TERM,
                "termLookup(term)",
                "Retrieves the entire definition of the given term.",
                "term\n    A term defined in the dictionary.",
                "termLookup(arpeggio)\n    Retrieves the entire definition of arpeggio.\n"
                        + "termLookup(allegro)\n    "
                        + "Retrieves the entire definition of allegro.",
                "termLookup(arpeggio)"
            );
        }

        return commandManual;
    }

    /**
     * Command constructor.
     *
     * @param args Given from the invoker.
     */
    public TermLookup(List<String> args) {
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
                Term term = TermsUtil.readTerm(termName);

                if (term != null) {
                    String name = term.getName();
                    String language = term.getLanguage();
                    String meaning = term.getDescription();
                    String category = term.getCategory();

                    env.println(String.format(termLayout, name, language, meaning, category));
                } else {
                    env.error(String.format(Error.TERMNOTDEFINED.getError(), termName));
                }
            }
        } catch (Exception e) {
            env.error(Error.TERMLOOKUP.getError());
        }
    }
}
