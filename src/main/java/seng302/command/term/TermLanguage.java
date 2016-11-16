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
 * Term Language command.
 * Given a term name, retrieves the language of that term if it's in the Terms Dictionary.
 *
 * @author plr37, adg62
 */
public class TermLanguage implements Command {
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
                "Language of Term",
                CommandCategory.TERM,
                "termLanguage(term)",
                "Retrieves the language of the given term.",
                "term\n    A term defined in the dictionary.",
                "termLanguage(arpeggio)\n    Retrieves the language of arpeggio.\n"
                        + "termLanguage(allegro)\n    Retrieves the language of allegro.",
                "termLanguage(arpeggio)"
            );
        }

        return commandManual;
    }

    /**
     * Command constructor.
     *
     * @param args Given from the invoker.
     */
    public TermLanguage(List<String> args) {
        arguments = args;
    }

    /**
     * Execute the term language command.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        try {
            if (arguments.size() != 1) {
                env.error(Error.TERMLOOKUP.getError());
            } else {
                String termName = arguments.get(0);
                Term termsLanguage = TermsUtil.readTerm(termName);

                if (termsLanguage != null) {
                    env.println("The source language of \"" + termName + "\" is: \""
                            + termsLanguage.getLanguage() + "\"");
                } else {
                    env.error(String.format(Error.TERMNOTDEFINED.getError(), termName));
                }
            }
        } catch (Exception e) {
            env.error(Error.TERMLOOKUP.getError());
        }
    }
}
