package seng302.command.term;

import seng302.Environment;
import seng302.command.Command;
import seng302.util.TermsUtil;
import seng302.util.enumerator.CommandCategory;
import seng302.util.enumerator.Error;
import seng302.util.object.CommandManual;

import java.util.List;

/**
 * Term Remove command.
 * Given a term, removes that term if it's in the Terms Dictionary.
 *
 * @author plr37, adg62
 */
public class TermRemove implements Command {
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
                "Remove Term",
                CommandCategory.TERM,
                "termRemove(term)",
                "Removes the given term from the term dictionary.",
                "term\n    A term defined in the dictionary.",
                "termRemove(arpeggio)\n    Removes arpeggio from the dictionary.\n"
                        + "termRemove(allegro)\n    Removes allegro from the dictionary.",
                "termRemove(arpeggio)"
            );
        }

        return commandManual;
    }

    /**
     * Command constructor.
     *
     * @param args Given from the invoker.
     */
    public TermRemove(List<String> args) {
        arguments = args;
    }

    /**
     * Execute the term remove command.
     *
     * @param env The environment.
     */
    public void execute(Environment env) {
        try {
            if (arguments.size() != 1) {
                env.error(Error.TERMREMOVE.getError());
            } else {
                String termName = arguments.get(0);
                Boolean removed = TermsUtil.removeTerm(termName);

                if (removed) {
                    env.println("\"" + termName + "\" was removed.");
                } else {
                    env.error(String.format(Error.TERMNOTDEFINEDNOTREMOVED.getError(), termName));
                }
            }
        } catch (Exception e) {
            env.error(Error.TERMREMOVE.getError());
        }
    }
}
