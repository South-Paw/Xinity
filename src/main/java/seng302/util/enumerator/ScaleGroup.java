package seng302.util.enumerator;

import java.util.EnumSet;

/**
 * Enum that groups different scaleTypes.
 *
 * @author plr37
 */
public enum ScaleGroup {
    NATURAL(EnumSet.of(ScaleType.MAJOR, ScaleType.MINOR)),
    MELODIC(EnumSet.of(ScaleType.MELODICMINOR)),
    HARMONIC(EnumSet.of(ScaleType.HARMONICMINOR)),
    HARMONICMINORMODES(EnumSet.of(ScaleType.MIXOLYDIANB2B6)),
    MAJORMODES(EnumSet.of(ScaleType.IONIAN, ScaleType.DORIAN, ScaleType.PHRYGIAN,
            ScaleType.LYDIAN, ScaleType.MIXOLYDIAN, ScaleType.AEOLIAN, ScaleType.LOCRIAN)),
    MELODICMINORMODES(EnumSet.of(ScaleType.MINORMAJOR, ScaleType.DORIANB2, ScaleType.LYDIAN5,
            ScaleType.LYDIANDOMINANT, ScaleType.MIXOLYDIANB6, ScaleType.LOCRIAN2,
            ScaleType.ALTERED)),
    PENTATONIC(EnumSet.of(ScaleType.PENTATONICMAJOR, ScaleType.PENTATONICMINOR)),
    JAZZ(EnumSet.of(ScaleType.BLUES));

    private EnumSet<ScaleType> group;

    ScaleGroup(EnumSet<ScaleType> scaletypes) {
        this.group = scaletypes;
    }

    public EnumSet<ScaleType> getGroup() {
        return group;
    }

    /**
     * Gets a ScaleGroup that the given scaleType belongs to.
     * @param scaleType the given scaleType
     * @return The valid ScaleGroup, null if not found
     */
    public static ScaleGroup fromScaleType(ScaleType scaleType) {
        if (scaleType != null) {
            for (ScaleGroup scaleGroup : ScaleGroup.values()) {
                for (ScaleType type : scaleGroup.group) {
                    if (scaleType == type) {
                        return scaleGroup;
                    }
                }
            }
        }
        return null;
    }

}
