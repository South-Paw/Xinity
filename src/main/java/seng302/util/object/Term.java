package seng302.util.object;

/**
 * Term Object.
 *
 * @author plr37, avh17, adg62
 */
public class Term {
    private String name;
    private String language;
    private String description;
    private String category;

    /**
     * Constructor. Creates a new Term object.
     *
     * @param name The term's name.
     * @param language The term's language.
     * @param description The term's description.
     * @param category The term's category.
     */
    public Term(String name, String language, String description, String category) {
        this.name = name;
        this.language = language;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public String getLanguage() {
        return language;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }
}
