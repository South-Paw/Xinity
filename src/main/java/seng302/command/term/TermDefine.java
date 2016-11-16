package seng302.command.term;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.TermsUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;

import java.util.List;

/**
 * Term Define command.
 * Adds a given term with its language, meaning and category to the Terms Dictionary.
 *
 * @author plr37, adg62
 */
public class TermDefine implements Command {
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
                "Define Term",
                CommandCategory.TERM,
                "termDefine(term, language, meaning, category)",
                "Defines a term given the meaning, language, and category and stores the term in "
                    + "the dictionary.",
                "term\n    The name of the defined term.\n"
                    + "language\n    The origin language of the defined term.\n"
                    + "meaning\n   The meaning or description of the defined term.\n"
                    + "category\n    The category of the defined term.",
                "termDefine(arpeggio, German, \"notes of a chord played in rapid succession\", "
                    + "chord)\n    Defines the term arpeggio in the dictionary.\n"
                    + "termDefine(allegro, Italian, \"a brisk and lively tempo\", tempo)\n    "
                    + "Defines the term allegro in the dictionary.",
                "termDefine(arpeggio, \"Chord are played or sung in sequence one after the other\","
                    + " Italian, Musical)"
            );
        }

        return commandManual;
    }

    /**
     * Command constructor.
     *
     * @param args Given from the invoker.
     */
    public TermDefine(List<String> args) {
        arguments = args;
    }

    /**
     * Execute the term define command.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        try {
            if (arguments.size() != 4) {
                env.error(Error.TERMDEFINEINVALIDNUMARGS.getError());
            } else {
                String term = arguments.get(0);
                String language = arguments.get(1);
                String meaning = arguments.get(2);
                String category = arguments.get(3);

                Boolean result = TermsUtil.addTerm(term, language, meaning, category);

                if (!result) {
                    env.println("Updated \"" + term + "\" in dictionary.");
                } else {
                    env.println("Added \"" + term + "\" to dictionary.");
                }
            }
        } catch (Exception e) {
            env.error(Error.TERMFAILEDTOADDTERM.getError());
        }
    }
}
