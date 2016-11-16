package seng302.util.enumerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Enum for types of scales, must have constructor containing a list of all accepted permutations
 * of the scale name, with the proper name leading (capitalized), and the interval pattern.
 * The interval patterns for pentatonic and blues scale types relate to the the indices of the
 * major/minor scale that are included.
 */

public enum ScaleType {
    MAJOR(
            Arrays.asList("Major"),
            Arrays.asList(2, 2, 1, 2, 2, 2, 1, 0)),
    MINOR(
            Arrays.asList("Minor", "natural minor"),
            Arrays.asList(2, 1, 2, 2, 1, 2, 2, 0)),
    MELODICMINOR(
            Arrays.asList("Melodic Minor", "melodic min", "mel min", "melodic minor",
                    "Jazz Minor", "Jazz Min"),
            Arrays.asList(2, 1, 2, 2, 2, 2, 1, 0)),
    PENTATONICMAJOR(
            Arrays.asList("Pentatonic Major", "penta major", "penta maj", "pentatonic maj"),
            Arrays.asList(0, 1, 2, 4, 5, 7)),
    PENTATONICMINOR(
            Arrays.asList("Pentatonic Minor", "penta minor", "penta min", "pentatonic min"),
            Arrays.asList(0, 2, 3, 4, 6, 7)),
    HARMONICMINOR(
            Arrays.asList("Harmonic Minor"),
            Arrays.asList(2, 1, 2, 2, 1, 3, 1, 0)),
    BLUES(
            Arrays.asList("Blues Scale", "blues", "blue"),
            Arrays.asList(0, 2, 3, 4, 4, 6, 7)),
    // MAJOR MODES
    IONIAN(
            Arrays.asList("Ionian", "I", "1"),
            Arrays.asList(2, 2, 1, 2, 2, 2, 1, 0)),
    DORIAN(
            Arrays.asList("Dorian", "II", "2"),
            Arrays.asList(2, 1, 2, 2, 2, 1, 2, 0)),
    PHRYGIAN(
            Arrays.asList("Phrygian", "III", "3"),
            Arrays.asList(1, 2, 2, 2, 1, 2, 2, 0)),
    LYDIAN(
            Arrays.asList("Lydian", "IV", "4"),
            Arrays.asList(2, 2, 2, 1, 2, 2, 1, 0)),
    MIXOLYDIAN(
            Arrays.asList("Mixolydian", "V", "5"),
            Arrays.asList(2, 2, 1, 2, 2, 1, 2, 0)),
    AEOLIAN(
            Arrays.asList("Aeolian", "VI", "6"),
            Arrays.asList(2, 1, 2, 2, 1, 2, 2, 0)),
    LOCRIAN(
            Arrays.asList("Locrian", "VII", "7"),
            Arrays.asList(1, 2, 2, 1, 2, 2, 2, 0)),

    // MELODIC MINOR MODES
    MINORMAJOR(
            Arrays.asList("Minormajor", "I", "1"),
            Arrays.asList(2, 1, 2, 2, 2, 2, 1, 0)),
    DORIANB2(
            Arrays.asList("Dorian b2", "II", "2"),
            Arrays.asList(1, 2, 2, 2, 2, 1, 2, 0)),
    LYDIAN5(
            Arrays.asList("Lydian #5", "III", "3"),
            Arrays.asList(2, 2, 2, 2, 1, 2, 1, 0)),
    LYDIANDOMINANT(
            Arrays.asList("Lydian dominant", "IV", "4"),
            Arrays.asList(2, 2, 2, 1, 2, 1, 2, 0)),
    MIXOLYDIANB6(
            Arrays.asList("Mixolydian b6", "V", "5"),
            Arrays.asList(2, 2, 1, 2, 1, 2, 2, 0)),
    LOCRIAN2(
            Arrays.asList("Locrian #2", "VI", "6"),
            Arrays.asList(2, 1, 2, 1, 2, 2, 2, 0)),
    ALTERED(
            Arrays.asList("Altered", "VII", "7"),
            Arrays.asList(1, 2, 1, 2, 2, 2, 2, 0)),

    // HARMONIC MINOR MODES
    MIXOLYDIANB2B6(
            Arrays.asList("Mixolydian b2 b6", "V", "5"),
            Arrays.asList(1, 3, 1, 2, 1, 2, 2, 0));

    private List<String> text;
    private List<Integer> scalePattern;

    ScaleType(List<String> text, List<Integer> value) {
        this.text = text;
        this.scalePattern = value;
    }

    public String toString() {
        return this.text.get(0);
    }

    /**
     * Get the interval pattern for a Scale Type e.g Major, Minor
     *
     * @return The given scale type's interval pattern.
     */
    public List<Integer> getScalePattern() {
        return scalePattern;
    }

    /**
     * Gets a list of the possible names.
     *
     * @return the possible names.
     */
    public List<String> getPossibleNames() {
        return text;
    }

    /**
     * Gets a valid scale type.
     * @param scale The scale type being passed in.
     * @return The valid scale type or null.
     */
    public static ScaleType fromString(String scale) {
        if (scale != null) {
            for (ScaleType scaleType : ScaleType.values()) {
                for (String string : scaleType.text) {
                    if (scale.equalsIgnoreCase(string)) {
                        return scaleType;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns the names of the Scale.
     *
     * @return list of the names of the scales.
     */
    public static List<String> getScaleNames() {
        List<String> scales = new ArrayList<>();
        for (ScaleType scaleType : ScaleType.values()) {
            scales.add(scaleType.text.get(0));
        }
        return scales;
    }
}