package seng302.util.enumerator;

/**
 * Class for storing enum of chord styles. May be extend if different variants are allowed.
 *
 * @author ljm163
 */

public enum ChordStyle {
    ARPEGGIO("arpeggio"),
    UNISON("unison"),
    BOTH("both");

    private String text;

    ChordStyle(String text) {
        this.text = text;
    }

    public String toString() {
        return this.text;
    }

    /**
     * Gets a valid chord style.
     * @param style The given chord style.
     * @return The valid chord style or null.
     */
    public static ChordStyle fromString(String style) {
        if (style != null) {
            for (ChordStyle chordStyle: ChordStyle.values()) {
                if (style.equalsIgnoreCase(chordStyle.text)) {
                    return chordStyle;
                }
            }
        }
        return null;
    }
}