package seng302.util.enumerator;

import java.util.Arrays;
import java.util.List;

/**
 * Class for grouping common inversions with different inversion names into enums.
 * Other classes can create a 'Inversion' type by using 'Inversion.fromString(1st)'.
 *
 * @author ljm163
 */

public enum Inversion {
    ROOT(Arrays.asList("Root Position", "0", "root", "0th")),
    FIRST(Arrays.asList("1st Inversion", "1", "first", "1st")),
    SECOND(Arrays.asList("2nd Inversion", "2", "second", "2nd")),
    THIRD(Arrays.asList("3rd Inversion", "3", "third", "3rd"));

    private List<String> inversions;

    Inversion(List<String> inversions) {
        this.inversions = inversions;
    }

    /**
     * Gets the string representation of the inversion.
     *
     * @return '1st Inversion' etc.
     */
    public String toString() {
        return this.inversions.get(0);
    }

    /**
     * Gets the integer value of the inversion.
     *
     * @return 1, 2 or 3 depending on the inversion.
     */
    public Integer getInteger() {
        return Integer.parseInt(this.inversions.get(1));
    }

    /**
     * Gets a valid inversion.
     *
     * @param inputInversion The inversion string being passed in.
     * @return inversion type or null if input is invalid.
     */
    public static Inversion fromString(String inputInversion) {
        if (inputInversion != null) {
            for (Inversion inversion : Inversion.values()) {
                for (String string : inversion.inversions) {
                    if (inputInversion.equalsIgnoreCase(string)) {
                        return inversion;
                    }
                }
            }
        }
        return null;
    }
}
