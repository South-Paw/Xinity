package seng302.util.enumerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class for grouping common chord qualities with different quality names into enums.
 * The second part of each enum is a list of corresponding intervals.
 * Other classes can create a 'ChordQuality' type by using 'ChordQuality.fromString(major)'.
 *
 * @author ljm163
 */

public enum ChordQuality {
    MAJORTRIAD(Arrays.asList("Major", "Major Triad", "Maj", "Maj Triad"),
            Arrays.asList(
                    Interval.PERFECTUNISON,
                    Interval.MAJORTHIRD,
                    Interval.PERFECTFIFTH)),
    MINORTRIAD(Arrays.asList("Minor", "Minor Triad", "Natural Minor", "Min", "Min Triad",
            "Nat Min", "Nat Minor", "Nat Min"),
            Arrays.asList(
                    Interval.PERFECTUNISON,
                    Interval.MINORTHIRD,
                    Interval.PERFECTFIFTH)),
    AUGMENTEDTRIAD(Arrays.asList("Augmented Triad", "Aug Triad"),
             Arrays.asList(
                     Interval.PERFECTUNISON,
                     Interval.MAJORTHIRD,
                     Interval.AUGMENTEDFIFTH)),
    MAJORSEVENTH(Arrays.asList("Major 7th", "Major Seventh", "Maj Seventh", "Maj 7th", "Maj7",
            "Major 7"),
            Arrays.asList(
                    Interval.PERFECTUNISON,
                    Interval.MAJORTHIRD,
                    Interval.PERFECTFIFTH,
                    Interval.MAJORSEVENTH)),
    MINORSEVENTH(Arrays.asList("Minor 7th", "Minor Seventh", "Min Seventh", "Min 7th", "Min7",
            "Minor 7"),
            Arrays.asList(
                    Interval.PERFECTUNISON,
                    Interval.MINORTHIRD,
                    Interval.PERFECTFIFTH,
                    Interval.MINORSEVENTH)),
    SEVENTH(Arrays.asList("7th", "Dominant Seventh", "Seventh", "Dominant 7th",
            "Dom Seventh", "Dom 7th", " Dom7", "Dominant 7"),
            Arrays.asList(
                    Interval.PERFECTUNISON,
                    Interval.MAJORTHIRD,
                    Interval.PERFECTFIFTH,
                    Interval.MINORSEVENTH)),
    HALFDIMINISHEDSEVENTH(Arrays.asList("Half Diminished", "Half Diminished Seventh",
            "Half Diminished 7th", "Half Dim Seventh", "Half Dim 7th", "Half Dim", "Half Dim7",
            "Half Diminished 7"),
            Arrays.asList(
                    Interval.PERFECTUNISON,
                    Interval.MINORTHIRD,
                    Interval.DIMINISHEDFIFTH,
                    Interval.MINORSEVENTH)),
    MAJORSIXTH(Arrays.asList("Major 6th", "Major Sixth", "Maj Sixth", "Maj 6th", "Maj6",
            "Major 6"),
            Arrays.asList(
                    Interval.PERFECTUNISON,
                    Interval.MAJORTHIRD,
                    Interval.PERFECTFIFTH,
                    Interval.MAJORSIXTH)),
    MINORSIXTH(Arrays.asList("Minor 6th", "Minor Sixth", "Min Sixth", "Min 6th", "Min6",
            "Minor 6"),
            Arrays.asList(
                    Interval.PERFECTUNISON,
                    Interval.MINORTHIRD,
                    Interval.PERFECTFIFTH,
                    Interval.MAJORSIXTH)),
    DIMINISHEDSEVENTH(Arrays.asList("Diminished 7th", "Diminished Seventh", "Dim Seventh",
            "Dim 7th", "Dim7", "Diminished 7"),
            Arrays.asList(
                    Interval.PERFECTUNISON,
                    Interval.MINORTHIRD,
                    Interval.DIMINISHEDFIFTH,
                    Interval.DIMINISHEDSEVENTH));

    private List<String> quality;
    private List<Interval> intervals;

    ChordQuality(List<String> quality, List<Interval> intervals) {
        this.quality = quality;
        this.intervals = intervals;
    }

    /**
     * Get the chord interval pattern for a chord quality.
     *
     * @return The given chord quality's interval pattern.
     */
    public List<Interval> getChordIntervals() {
        return intervals;
    }

    /**
     * Get the chord qualities for a chord.
     *
     * @return The chords qualities.
     */
    public List<String> getChordQualities() {
        return quality;
    }

    /**
     * Gets a valid chord quality.
     *
     * @param inputQuality The chord quality string being passed in.
     * @return ChordQuality type chord quality or null if input is invalid.
     */
    public static ChordQuality fromString(String inputQuality) {
        if (inputQuality != null) {
            for (ChordQuality chordQuality : ChordQuality.values()) {
                for (String string : chordQuality.quality) {
                    if (inputQuality.equalsIgnoreCase(string)) {
                        return chordQuality;
                    }
                }
            }
        }
        return null;
    }


    public String toString() {
        return this.quality.get(0);
    }

    /**
     * Given a list of intervals returns the quality of the expected chord.
     *
     * @param intervals a list of intervals.
     * @return chord quality.
     */
    public static ChordQuality getQuality(List<Interval> intervals) {
        for (ChordQuality chordQuality : ChordQuality.values()) {
            if (chordQuality.intervals.equals(intervals)) {
                return chordQuality;
            }
        }
        return null;
    }

    /**
     * Given a list of intervals returns the quality of the expected chord as a string.
     *
     * @param intervals a list of intervals.
     * @return String - chord quality.
     */
    public static String getQualityString(List<Interval> intervals) {
        for (ChordQuality chordQuality : ChordQuality.values()) {
            if (chordQuality.intervals.equals(intervals)) {
                return chordQuality.quality.get(0);
            }
        }
        return null;
    }
}
