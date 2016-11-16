package seng302.util.enumerator;

import java.util.Arrays;
import java.util.List;

/**
 * Enumerator class for Chord Functions.
 */
public enum ChordFunctionEnum {
    I(Arrays.asList("I", "1"), ChordQuality.MAJORSEVENTH),
    II(Arrays.asList("II", "2"), ChordQuality.MINORSEVENTH),
    III(Arrays.asList("III", "3"), ChordQuality.MINORSEVENTH),
    IV(Arrays.asList("IV", "4"), ChordQuality.MAJORSEVENTH),
    V(Arrays.asList("V", "5"), ChordQuality.SEVENTH),
    VI(Arrays.asList("VI", "6"), ChordQuality.MINORSEVENTH),
    VII(Arrays.asList("VII", "7"), ChordQuality.HALFDIMINISHEDSEVENTH);

    private List<String> function;
    private ChordQuality quality;

    ChordFunctionEnum(List<String> function, ChordQuality quality) {
        this.function = function;
        this.quality = quality;
    }

    /**
     * Gets the chord functions.
     *
     * @return The chord functions.
     */
    public List<String> getChordFunction() {
        return function;
    }

    /**
     * Gets the chord quality.
     *
     * @return The chord quality.
     */
    public ChordQuality getQuality() {
        return quality;
    }

    /**
     * Returns a chord function from a string input.
     *
     * @param inputFunction the given input.
     * @return the chord function.
     */
    public static ChordFunctionEnum fromString(String inputFunction) {
        if (inputFunction != null) {
            for (ChordFunctionEnum chordFunctionEnum : ChordFunctionEnum.values()) {
                for (String function : chordFunctionEnum.getChordFunction()) {
                    if (inputFunction.equalsIgnoreCase(function)) {
                        return chordFunctionEnum;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns a string representation of the chord function.
     *
     * @return The string representation.
     */
    public String toString() {
        return this.function.get(0);
    }
}
