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
 * Term Meaning command.
 * Given a term, retrieves the meaning of that term if it's in the Terms Dictionary.
 *
 * @author plr37, adg62
 */
public class TermMeaning implements Command {
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
                "Meaning of Term",
                CommandCategory.TERM,
                "termMeaning(term)",
                "Retrieves the meaning of the given term.",
                "term\n    A term defined in the dictionary.",
                "termMeaning(arpeggio)\n    Retrieves the meaning of arpeggio.\n"
                        + "termMeaning(allegro)\n    Retrieves the meaning of allegro.",
                "termMeaning(arpeggio)"
            );
        }

        return commandManual;
    }

    /**
     * Command constructor.
     *
     * @param args Given from the invoker.
     */
    public TermMeaning(List<String> args) {
        arguments = args;
    }

    /**
     * Execute the term meaning command.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        try {
            if (arguments.size() != 1) {
                env.error(Error.TERMLOOKUP.getError());
            } else {
                String termName = arguments.get(0);
                Term termsMeaning = TermsUtil.readTerm(termName);

                if (termsMeaning != null) {
                    env.println("The meaning of \"" + termName + "\" is \""
                            + termsMeaning.getDescription() + "\"");
                } else {
                    env.error(String.format(Error.TERMNOTDEFINED.getError(), termName));
                }
            }
        } catch (Exception e) {
            env.error(Error.TERMLOOKUP.getError());
        }
    }
}
