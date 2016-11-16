package seng302.util;

import seng302.util.object.Term;
import seng302.workspace.Project;

/**
 * Terms Utility.
 *
 * @author adg62, jps100
 */
public final class TermsUtil {

    /** Private Constructor. */
    private TermsUtil() {}

    /**
     * Adds a term to the term dictionary. Will overwrite the previous value of the key if one
     * existed with the same name.
     *
     * @param name Term's name.
     * @param language Term's language.
     * @param description Term's description.
     * @param category Term's category.
     * @return True if the term was added. False if an existing term was updated.
     */
    public static Boolean addTerm(String name, String language, String description,
                                  String category) {
        Term term = new Term(name, language, description, category);

        return Project.getInstance().putTerm(name, term);
    }

    /**
     * Attempts to remove the term with the given name.
     *
     * @param name Term to be removed.
     * @return True if the term existed, false if the term didn't exist.
     */
    public static Boolean removeTerm(String name) {
        return Project.getInstance().removeTerm(name);
    }

    /**
     * Read one term from the term dictionary.
     *
     * @param name Term we're looking up.
     * @return Null if term doesn't exist in the mapping. Or an object of the term's name, language,
     *      description and source if it does.
     */
    public static Term readTerm(String name) {
        return Project.getInstance().getTerm(name);
    }

    /**
     * Empty the terms in the terms dictionary.
     */
    public static void clearAllTerms() {
        Project.getInstance().resetTerms();
    }
}
