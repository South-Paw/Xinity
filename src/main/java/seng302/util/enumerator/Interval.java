package seng302.util.enumerator;

import java.util.Arrays;
import java.util.List;

/**
 * Class for grouping equivalent interval names into enums.
 * This enum was moved out of XinityIntervalMap, as it made sense to have a separate enum class.
 *
 * @author ljm163
 */
public enum Interval {

    PERFECTUNISON(Arrays.asList("Perfect Unison", "Perfect Uni", "Per Unison","Per Uni",
            "Unison")),
    PERFECTFOURTH(Arrays.asList("Perfect Fourth", "Perfect 4th", "Perfect 4", "Per Fourth",
            "Per 4th", "Per 4")),
    PERFECTFIFTH(Arrays.asList("Perfect Fifth", "Perfect 5th", "Perfect 5", "Per Fifth",
            "Per 5th", "Per 5")),
    PERFECTOCTAVE(Arrays.asList("Perfect Octave", "Perfect Oct", "Perfect Eighth",
            "Perfect 8th", "Perfect 8", "Per Octave", "Per Oct", "Per 8th", "Per 8",
            "Per Eighth", "Octave", "Oct")),
    PERFECTELEVENTH(Arrays.asList("Perfect Eleventh", "Perfect 11th", "Perfect 11",
            "Per Eleventh", "Per 11th", "Per 11")),
    PERFECTTWELFTH(Arrays.asList("Perfect Twelfth", "Perfect 12th", "Perfect 12", "Per Twelfth",
            "Per 12th", "Per 12")),
    PERFECTFIFTEENTH(Arrays.asList("Perfect Fifteenth", "Perfect 15th", "Perfect 15",
            "Per Fifteenth", "Per 15th", "Per 15")),

    MAJORSECOND(Arrays.asList("Major Second", "Major 2nd", "Major 2", "Maj Second", "Maj 2nd",
            "Maj 2")),
    MAJORTHIRD(Arrays.asList("Major Third", "Major 3rd", "Major 3", "Maj Third", "Maj 3rd",
            "Maj 3")),
    MAJORSIXTH(Arrays.asList("Major Sixth", "Major 6th", "Major 6", "Maj Sixth", "Maj 6th",
            "Maj 6")),
    MAJORSEVENTH(Arrays.asList("Major Seventh", "Major 7th", "Major 7", "Maj Seventh",
            "Maj 7th", "Maj 7")),
    MAJORNINTH(Arrays.asList("Major Ninth", "Major 9th", "Major 9", "Maj Ninth", "Maj 9th",
            "Maj 9")),
    MAJORTENTH(Arrays.asList("Major Tenth", "Major 10th", "Major 10", "Maj Tenth", "Maj 10th",
            "Maj 10")),
    MAJORTHIRTEENTH(Arrays.asList("Major Thirteenth", "Major 13th", "Major 13",
            "Maj Thirteenth", "Maj 13th", "Maj 13")),
    MAJORFOURTEENTH(Arrays.asList("Major Fourteenth", "Major 14th", "Major 14",
            "Maj Fourteenth", "Maj 14th", "Maj 14")),

    MINORSECOND(Arrays.asList("Minor Second", "Minor 2nd", "Minor 2", "Min Second", "Min 2nd",
            "Min 2")),
    MINORTHIRD(Arrays.asList("Minor Third", "Minor 3rd", "Minor 3", "Min Third", "Min 3rd",
            "Min 3")),
    MINORSIXTH(Arrays.asList("Minor Sixth", "Minor 6th", "Minor 6", "Min Sixth", "Min 6th",
            "Min 6")),
    MINORSEVENTH(Arrays.asList("Minor Seventh", "Minor 7th", "Minor 7", "Min Seventh",
            "Min 7th", "Min 7")),
    MINORNINTH(Arrays.asList("Minor Ninth", "Minor 9th", "Minor 9", "Min Ninth", "Min 9th",
            "Min 9")),
    MINORTENTH(Arrays.asList("Minor Tenth", "Minor 10th", "Minor 10", "Min Tenth", "Min 10th",
            "Min 10")),
    MINORTHIRTEENTH(Arrays.asList("Minor Thirteenth", "Minor 13th", "Minor 13",
            "Min Thirteenth", "Min 13th", "Min 13")),
    MINORFOURTEENTH(Arrays.asList("Minor Fourteenth", "Minor 14th", "Minor 14",
            "Min Fourteenth", "Min 14th", "Min 14")),

    DIMINISHEDSECOND(Arrays.asList("Diminished Second", "Diminished 2nd", "Diminished 2",
            "Dim Second", "Dim 2nd", "Dim 2")),
    DIMINISHEDTHIRD(Arrays.asList("Diminished Third", "Diminished 3rd", "Diminished 3",
            "Dim Third", "Dim 3rd", "Dim 3")),
    DIMINISHEDFOURTH(Arrays.asList("Diminished Fourth", "Diminished 4th", "Diminished 4",
            "Dim Fourth", "Dim 4th", "Dim 4")),
    DIMINISHEDFIFTH(Arrays.asList("Diminished Fifth", "Diminished 5th", "Diminished 5",
            "Dim Fifth", "Dim 5th", "Dim 5")),
    DIMINISHEDSIXTH(Arrays.asList("Diminished Sixth", "Diminished 6th", "Diminished 6",
            "Dim Sixth", "Dim 6th", "Dim 6")),
    DIMINISHEDSEVENTH(Arrays.asList("Diminished Seventh", "Diminished 7th", "Diminished 7",
            "Dim Seventh", "Dim 7th", "Dim 7")),
    DIMINISHEDOCTAVE(Arrays.asList("Diminished Octave", "Diminished Oct", "Diminished 8",
            "Dim Octave", "Dim Oct", "Dim 8")),
    DIMINISHEDNINTH(Arrays.asList("Diminished Ninth", "Diminished 9th", "Diminished 9",
            "Dim Ninth", "Dim 9th", "Dim 9")),
    DIMINISHEDTENTH(Arrays.asList("Diminished Tenth", "Diminished 10th", "Diminished 10",
            "Dim Tenth", "Dim 10th", "Dim 10")),
    DIMINISHEDELEVENTH(Arrays.asList("Diminished Eleventh", "Diminished 11th", "Diminished 11",
            "Dim Eleventh", "Dim 11th", "Dim 11")),
    DIMINISHEDTWELFTH(Arrays.asList("Diminished Twelfth", "Diminished 12th", "Diminished 12",
            "Dim Twelfth", "Dim 12th", "Dim 12")),
    DIMINISHEDTHIRTEENTH(Arrays.asList("Diminished Thirteenth", "Diminished 13th",
            "Diminished 13", "Dim Thirteenth", "Dim 13th", "Dim 13")),
    DIMINISHEDFOURTEENTH(Arrays.asList("Diminished Fourteenth", "Diminished 14th",
            "Diminished 14", "Dim Fourteenth", "Dim 14th", "Dim 14")),
    DIMINISHEDFIFTEENTH(Arrays.asList("Diminished Fifteenth", "Diminished 15th",
            "Diminished 15", "Dim Fifteenth", "Dim 15")),

    AUGMENTEDUNISON(Arrays.asList("Augmented Unison", "Augmented Uni", "Aug Unison",
            "Aug Uni")),
    AUGMENTEDSECOND(Arrays.asList("Augmented Second", "Augmented 2nd", "Augmented 2",
            "Aug Second", "Aug 2nd", "Aug 2")),
    AUGMENTEDTHIRD(Arrays.asList("Augmented Third", "Augmented 3rd", "Augmented 3",
            "Aug Third", "Aug 3rd", "Aug 3")),
    AUGMENTEDFOURTH(Arrays.asList("Augmented Fourth", "Augmented 4th", "Augmented 4",
            "Aug Fourth", "Aug 4th", "Aug 4")),
    AUGMENTEDFIFTH(Arrays.asList("Augmented Fifth", "Augmented 5th", "Augmented 5",
            "Aug Fifth", "Aug 5th", "Aug 5")),
    AUGMENTEDSIXTH(Arrays.asList("Augmented Sixth", "Augmented 6th", "Augmented 6",
            "Aug Sixth", "Aug 6th", "Aug 6")),
    AUGMENTEDSEVENTH(Arrays.asList("Augmented Seventh", "Augmented 7th", "Augmented 7",
            "Aug Seventh", "Aug 7th", "Aug 7")),
    AUGMENTEDOCTAVE(Arrays.asList("Augmented Octave", "Augmented Oct", "Augmented Eighth",
            "Augmented 8th", "Augmented 8", "Aug Octave", "Aug Oct",
            "Aug Eighth", "Aug 8th", "Aug 8")),
    AUGMENTEDNINTH(Arrays.asList("Augmented Ninth", "Augmented 9th", "Augmented 9",
            "Aug Ninth", "Aug 9th", "Aug 9")),
    AUGMENTEDTENTH(Arrays.asList("Augmented Tenth", "Augmented 10th", "Augmented 10",
            "Aug Tenth", "Aug 10th", "Aug 10")),
    AUGMENTEDELEVENTH(Arrays.asList("Augmented Eleventh", "Augmented 11th", "Augmented 11",
            "Aug Eleventh", "Aug 11th", "Aug 11")),
    AUGMENTEDTWELFTH(Arrays.asList("Augmented Twelfth", "Augmented 12th", "Augmented 12",
            "Aug Twelfth", "Aug 12th", "Aug 12")),
    AUGMENTEDTHIRTEENTH(Arrays.asList("Augmented Thirteenth", "Augmented 13th", "Augmented 13",
            "Aug Thirteenth", "Aug 13th", "Aug 13")),
    AUGMENTEDFOURTEENTH(Arrays.asList("Augmented Fourteenth", "Augmented 14th", "Augmented 14",
            "Aug Fourteenth", "Aug 14th", "Aug 14"));


    private List<String> permutations;

    /**
     * Interval Enum constructor.
     *
     * @param permutations List
     */
    Interval(List<String> permutations) {
        this.permutations = permutations;
    }

    public static String toString(Interval interval) {
        return interval.permutations.get(0);
    }

    public static List<Interval> getAllIntervals() {
        return Arrays.asList(Interval.values());
    }

    /**
     * Return the matching Interval.
     *
     * @param text String
     * @return Interval
     */
    public static Interval fromString(String text) {
        if (text != null) {
            for (Interval interval : Interval.values()) {
                for (String string : interval.permutations) {
                    if (text.equalsIgnoreCase(string)) {
                        return interval;
                    }
                }
            }
        }
        return null;
    }
}