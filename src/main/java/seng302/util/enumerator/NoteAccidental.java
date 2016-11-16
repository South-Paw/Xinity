package seng302.util.enumerator;

/**
 * Enum class for storing the string representations of music accidentals.
 *
 * @author avh17, wrs35, ljm163
 */
public enum NoteAccidental {
    UNKNOWN("unknown"),
    NATURAL(""),
    FLAT("b"),
    SHARP("#"),
    DOUBLEFLAT("bb"),
    DOUBLESHARP("x");

    private String musicalRepresentation;

    NoteAccidental(String musicalRepresentation) {
        this.musicalRepresentation = musicalRepresentation;
    }

    /**
     * Returns the string representation of the NoteAccidental.
     *
     * @return string musical representation
     */
    public String toMusicalRepresentation() {
        return musicalRepresentation;
    }

    /**
     * Given a string, method converts into a NoteType object and returns it.
     *
     * @param type the given accidental
     * @return the NoteAccidental accidental
     */
    public static NoteAccidental fromMusicalRepresentation(String type) {
        if (type != null) {
            for (NoteAccidental noteAccidental : NoteAccidental.values()) {
                if (type.equals(noteAccidental.musicalRepresentation)) {
                    return noteAccidental;
                }
            }
        }
        return null;
    }

    /**
     * Returns true if the given string is a sharp or flat.
     *
     * @param accidental given accidental
     * @return returns true if string is a sharp or flat
     */
    public static Boolean isSimpleAccidental(NoteAccidental accidental) {
        return (accidental != null)
                && (accidental.equals(NoteAccidental.FLAT)
                || accidental.equals(NoteAccidental.SHARP));
    }
}