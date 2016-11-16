package seng302.util;

import seng302.util.enumerator.Interval;
import seng302.util.enumerator.NoteAccidental;
import seng302.util.map.XinityIntervalMap;
import seng302.util.map.XinityNoteMap;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Interval Utility.
 *
 * @author mvj14, plr37, ljm163, jps100
 */
public final class IntervalUtil {

    /** Private Constructor. */
    private IntervalUtil() {}

    /**
     * Returns the upper Xinity note of given the tonic and interval name.
     *
     * @param tonic the starting note
     * @param interval the interval between the two notes
     * @return the upper note of the interval
     */
    public static XinityNote interval(XinityNote tonic, Interval interval) {
        Integer semitones;
        Integer intervalNumber;
        semitones = XinityIntervalMap.getIntervalToSemitoneAndNumberMap().get(interval).get(0);
        intervalNumber = XinityIntervalMap.getIntervalToSemitoneAndNumberMap().get(interval).get(1);

        List<String> letters = Arrays.asList("C", "D", "E", "F", "G", "A", "B");

        // index of the upper letter of the interval in the letters list
        Integer nextIndex = (letters.indexOf(tonic.getLetter()) + intervalNumber - 1) % 7;
        String upperNoteLetter = letters.get(nextIndex);

        // how many loops through the letters to get the upper letter, determines the new octave
        Integer octaveIncrement = (letters.indexOf(tonic.getLetter()) + intervalNumber - 1) / 7;
        Integer upperNoteOctave = tonic.getOctave() + octaveIncrement;

        try {
            // semitones from tonic to upperNote (ignoring accidental)
            Integer semitonesToUpperNoteLetter = SemitoneUtil.findSemitones(
                    tonic, new XinityNote(upperNoteLetter + upperNoteOctave));

            // get diff between calculated semitones and interval semitones to determine accidental
            Integer difference = semitones - semitonesToUpperNoteLetter;
            NoteAccidental accidental = XinityNoteMap.getIntegerToAccidentalMap().get(difference);
            if (accidental == null) {
                return null;
            }
            return new XinityNote(
                    upperNoteLetter + accidental.toMusicalRepresentation() + upperNoteOctave);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns the Interval between two given notes.
     *
     * @param note1 The first Xinity note
     * @param note2 The second Xinity note
     * @return the Interval between note1 and note2
     */
    public static Interval findInterval(XinityNote note1, XinityNote note2) {
        Integer semitones = SemitoneUtil.findSemitones(note1, note2);
        Integer intervalNumber = getIntervalNumber(note1, note2);
        return XinityIntervalMap.getSemitoneAndNumberToIntervalMap().get(
                Arrays.asList(semitones, intervalNumber));
    }

    /**
     * Get the number of semitones from a given interval.
     * @param interval the given Interval
     * @return the number of semitones of that interval
     */
    public static Integer getSemitones(Interval interval) {
        return XinityIntervalMap.getIntervalToSemitoneAndNumberMap().get(interval).get(0);
    }

    /**
     * Finds the interval from a given number of semitones.
     *
     * @param semitones The number of semitones of the interval.
     * @return The interval.
     */
    public static Interval getInterval(Integer semitones) {
        return XinityIntervalMap.getSemitoneToIntervalsMap().get(semitones).get(0);
    }

    /**
     * Gets the list of ordered Intervals. Enharmonic equivalents
     * are not included.
     *
     * @return The list of ordered Intervals
     */
    public static List<String> getIntervals() {
        List<String> intervals = new ArrayList<>();
        Map<Integer, List<Interval>> map =
                XinityIntervalMap.getSemitoneToIntervalsMap();
        for (List<Interval> interval : map.values()) {
            intervals.add(Interval.toString(interval.get(0)));
        }
        return intervals;
    }

    /**
     * Checks if an Interval has an enharmonic equivalent.
     *
     * @param interval The given Interval
     * @return Boolean
     */
    public static Boolean hasEnharmonic(Interval interval) {
        Integer semitones;
        semitones = XinityIntervalMap.getIntervalToSemitoneAndNumberMap().get(interval).get(0);
        return semitones != null
                && XinityIntervalMap.getSemitoneToIntervalsMap().get(semitones).size() > 1;
    }

    /**
     * Gets the enharmonic equivalent of an interval if it exists.
     * Returns null if an equivalent does not exist.
     *
     * @param interval the given Interval
     * @return the enharmonic equivalent Interval
     */
    public static Interval getEnharmonic(Interval interval) {
        Interval equivalent = null;
        Integer semitones;
        List<Interval> enharmonics;

        semitones = XinityIntervalMap.getIntervalToSemitoneAndNumberMap().get(interval).get(0);
        enharmonics = new ArrayList<>(XinityIntervalMap.getSemitoneToIntervalsMap().get(semitones));
        if (enharmonics.size() > 1) {
            if (interval.equals(enharmonics.get(0))) {
                equivalent = enharmonics.get(1);
            } else {
                equivalent = enharmonics.get(0);
            }
        }
        return equivalent;
    }

    /**
     * Returns the interval number. The number of letters between the two notes.
     * E.g. C, E has an interval number of 3 because C, D, E. If intervals span multiple octaves,
     * interval number is incremented by 7 letters multiplied by the difference in octaves.
     *
     * @param note1 the first Xinity note
     * @param note2 the second Xinity note
     * @return the interval number (number of letters in interval)
     */
    private static Integer getIntervalNumber(XinityNote note1, XinityNote note2) {
        List<String> letters = Arrays.asList("C", "D", "E", "F", "G", "A", "B");
        String letter1 = note1.getLetter();
        String letter2 = note2.getLetter();

        Integer intervalNumber  = letters.indexOf(letter2) - letters.indexOf(letter1) + 1;

        Integer octave2 = note2.getOctave();
        Integer octave1 = note1.getOctave();
        Integer octaveDiff = octave2 - octave1;

        intervalNumber += (octaveDiff) * 7; // when b4 to c5 etc
        return intervalNumber;
    }
}