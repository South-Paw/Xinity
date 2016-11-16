package seng302.util.enumerator;

import javafx.scene.transform.Scale;

import java.util.Arrays;
import java.util.List;

/**
 * Enum for types of Scale modes of the major scale.
 *
 * @author plr37
 */
public enum ScaleMode {
    // Major Modes
    IONIAN(Arrays.asList("Ionian", "I"), 1, ScaleGroup.MAJORMODES),
    DORIAN(Arrays.asList("Dorian", "II"), 2, ScaleGroup.MAJORMODES),
    PHRYGIAN(Arrays.asList("Phrygian", "III"), 3, ScaleGroup.MAJORMODES),
    LYDIAN(Arrays.asList("Lydian", "IV"), 4, ScaleGroup.MAJORMODES),
    MIXOLYDIAN(Arrays.asList("Mixolydian", "V"), 5, ScaleGroup.MAJORMODES),
    AEOLIAN(Arrays.asList("Aeolian", "VI"), 6, ScaleGroup.MAJORMODES),
    LOCRIAN(Arrays.asList("Locrian", "VII"), 7, ScaleGroup.MAJORMODES),

    // Melodic Minor Modes
    MINORMAJOR(Arrays.asList("Minormajor", "I"), 1, ScaleGroup.MELODICMINORMODES),
    DORIANB2(Arrays.asList("Dorian b2", "II"), 2, ScaleGroup.MELODICMINORMODES),
    LYDIAN5(Arrays.asList("Lydian #5", "III"), 3, ScaleGroup.MELODICMINORMODES),
    LYDIANDOMINANT(Arrays.asList("Lydian dominant", "IV"), 4, ScaleGroup.MELODICMINORMODES),
    MIXOLYDIANB6(Arrays.asList("Mixolydian b6", "V"), 5, ScaleGroup.MELODICMINORMODES),
    LOCRIAN2(Arrays.asList("Locrian #2", "VI"), 6, ScaleGroup.MELODICMINORMODES),
    ALTERED(Arrays.asList("Altered", "VII"), 7, ScaleGroup.MELODICMINORMODES),

    // Harmonic Minor Modes
    MIXOLYDIANB2B6(Arrays.asList("Mixolydian b2 b6", "V"), 5, ScaleGroup.HARMONIC);

    private List<String> mode;
    private Integer degree;
    private ScaleGroup scaleGroup;

    ScaleMode(List<String> text, Integer semitone, ScaleGroup scaleGroup) {
        this.mode = text;
        this.degree = semitone;
        this.scaleGroup = scaleGroup;
    }

    public String toString() {
        return this.mode.get(0);
    }

    /**
     * Gets the scale group of the scale mode.
     * @return the scale group
     */
    public ScaleGroup getScaleGroup() {
        return this.scaleGroup;
    }

    /**
     * Gets the semitone for a scale mode.
     * @return Integer semitone
     */
    public Integer getDegree() {
        return degree;
    }

    /**
     * Gets a valid scale mode from a String matching a mode.
     * @param mode The scale mode being passed in.
     * @return The valid scale mode or null.
     */
    public static ScaleMode fromString(String mode) {
        if (mode != null) {
            for (ScaleMode scaleMode : ScaleMode.values()) {
                for (String string : scaleMode.mode) {
                    if (mode.equalsIgnoreCase(string)) {
                        return scaleMode;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets a valid scale mode from an Integer matching a mode degree.
     * @param degree Integer
     * @return The valid scale mode or null.
     */
    public static ScaleMode fromDegreeMajor(Integer degree) {
        if (degree != null) {
            for (ScaleMode scaleMode : ScaleMode.values()) {
                if (degree.equals(scaleMode.degree)) {
                    return scaleMode;
                }
            }
        }
        return null;
    }


    /**
     * Gets a valid melodic minor scale mode from an Integer matching a mode degree.
     * @param degree Integer
     * @return The valid scale mode or null.
     */
    public static ScaleMode fromDegreeMelodicMinor(Integer degree) {
        if (degree != null) {
            for (Integer i = MINORMAJOR.ordinal(); i < ALTERED.ordinal() + 1; i++) {
                if (degree == ScaleMode.values()[i].getDegree()) {
                    return ScaleMode.values()[i];
                }
            }
        }
        return null;
    }
}
