package seng302.util.map;

import seng302.util.enumerator.Interval;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Common interval mappings.
 * Only IntervalUtil.java should use this.
 *
 * @author jps100, plr37, ljm163
 */
public class XinityIntervalMap {
    private static Map<Interval, List<Integer>> intervalToSemitoneAndNumberMap;
    private static Map<List<Integer>, Interval> semitoneAndNumberToIntervalMap;
    private static Map<Integer, List<Interval>> semitoneToIntervalsMap;

    /**
     * Map of interval to semitone and interval number.
     * @return the intervalToSemitoneAndNumberMap
     */
    public static Map<Interval, List<Integer>> getIntervalToSemitoneAndNumberMap() {
        if (intervalToSemitoneAndNumberMap == null) {
            Map<Interval, List<Integer>> map = new HashMap<>();
            map.put(Interval.PERFECTUNISON, Arrays.asList(0, 1));
            map.put(Interval.DIMINISHEDSECOND, Arrays.asList(0, 2));
            map.put(Interval.MINORSECOND, Arrays.asList(1, 2));
            map.put(Interval.AUGMENTEDUNISON, Arrays.asList(1, 1));
            map.put(Interval.MAJORSECOND, Arrays.asList(2, 2));
            map.put(Interval.DIMINISHEDTHIRD, Arrays.asList(2, 3));
            map.put(Interval.MINORTHIRD, Arrays.asList(3, 3));
            map.put(Interval.AUGMENTEDSECOND, Arrays.asList(3, 2));
            map.put(Interval.MAJORTHIRD, Arrays.asList(4, 3));
            map.put(Interval.DIMINISHEDFOURTH, Arrays.asList(4, 4));
            map.put(Interval.PERFECTFOURTH, Arrays.asList(5, 4));
            map.put(Interval.AUGMENTEDTHIRD, Arrays.asList(5, 3));
            map.put(Interval.AUGMENTEDFOURTH, Arrays.asList(6, 4));
            map.put(Interval.DIMINISHEDFIFTH, Arrays.asList(6, 5));
            map.put(Interval.PERFECTFIFTH, Arrays.asList(7, 5));
            map.put(Interval.DIMINISHEDSIXTH, Arrays.asList(7, 6));
            map.put(Interval.MINORSIXTH, Arrays.asList(8, 6));
            map.put(Interval.AUGMENTEDFIFTH, Arrays.asList(8, 5));
            map.put(Interval.MAJORSIXTH, Arrays.asList(9, 6));
            map.put(Interval.DIMINISHEDSEVENTH, Arrays.asList(9, 7));
            map.put(Interval.MINORSEVENTH, Arrays.asList(10, 7));
            map.put(Interval.AUGMENTEDSIXTH, Arrays.asList(10, 6));
            map.put(Interval.MAJORSEVENTH, Arrays.asList(11, 7));
            map.put(Interval.DIMINISHEDOCTAVE, Arrays.asList(11, 8));
            map.put(Interval.PERFECTOCTAVE, Arrays.asList(12, 8));
            map.put(Interval.AUGMENTEDSEVENTH, Arrays.asList(12, 7));
            map.put(Interval.DIMINISHEDNINTH, Arrays.asList(12, 9));
            map.put(Interval.MINORNINTH, Arrays.asList(13, 9));
            map.put(Interval.AUGMENTEDOCTAVE, Arrays.asList(13, 8));
            map.put(Interval.MAJORNINTH, Arrays.asList(14, 9));
            map.put(Interval.DIMINISHEDTENTH, Arrays.asList(14, 10));
            map.put(Interval.MINORTENTH, Arrays.asList(15, 10));
            map.put(Interval.AUGMENTEDNINTH, Arrays.asList(15, 9));
            map.put(Interval.MAJORTENTH, Arrays.asList(16, 10));
            map.put(Interval.DIMINISHEDELEVENTH, Arrays.asList(16, 11));
            map.put(Interval.PERFECTELEVENTH, Arrays.asList(17, 11));
            map.put(Interval.AUGMENTEDTENTH, Arrays.asList(17, 10));
            map.put(Interval.AUGMENTEDELEVENTH, Arrays.asList(18, 11));
            map.put(Interval.DIMINISHEDTWELFTH, Arrays.asList(18, 12));
            map.put(Interval.PERFECTTWELFTH, Arrays.asList(19, 12));
            map.put(Interval.DIMINISHEDTHIRTEENTH, Arrays.asList(19, 13));
            map.put(Interval.MINORTHIRTEENTH, Arrays.asList(20, 13));
            map.put(Interval.AUGMENTEDTWELFTH, Arrays.asList(20, 12));
            map.put(Interval.MAJORTHIRTEENTH, Arrays.asList(21, 13));
            map.put(Interval.DIMINISHEDFOURTEENTH, Arrays.asList(21, 14));
            map.put(Interval.MINORFOURTEENTH, Arrays.asList(22, 14));
            map.put(Interval.AUGMENTEDTHIRTEENTH, Arrays.asList(22, 13));
            map.put(Interval.MAJORFOURTEENTH, Arrays.asList(23, 14));
            map.put(Interval.DIMINISHEDFIFTEENTH, Arrays.asList(23, 15));
            map.put(Interval.PERFECTFIFTEENTH, Arrays.asList(24, 15));
            map.put(Interval.AUGMENTEDFOURTEENTH, Arrays.asList(24, 14));
            intervalToSemitoneAndNumberMap = map;
        }
        return Collections.unmodifiableMap(intervalToSemitoneAndNumberMap);
    }

    /**
     * Map of semitone and interval number to Interval.
     * @return semitoneAndNumberToIntervalMap
     */
    public static Map<List<Integer>, Interval> getSemitoneAndNumberToIntervalMap() {
        if (semitoneAndNumberToIntervalMap == null) {
            Map<List<Integer>, Interval> map = new HashMap<>();
            map.put(Arrays.asList(0, 1), Interval.PERFECTUNISON);
            map.put(Arrays.asList(0, 2), Interval.DIMINISHEDSECOND);
            map.put(Arrays.asList(1, 2), Interval.MINORSECOND);
            map.put(Arrays.asList(1, 1), Interval.AUGMENTEDUNISON);
            map.put(Arrays.asList(2, 2), Interval.MAJORSECOND);
            map.put(Arrays.asList(2, 3), Interval.DIMINISHEDTHIRD);
            map.put(Arrays.asList(3, 3), Interval.MINORTHIRD);
            map.put(Arrays.asList(3, 2), Interval.AUGMENTEDSECOND);
            map.put(Arrays.asList(4, 3), Interval.MAJORTHIRD);
            map.put(Arrays.asList(4, 4), Interval.DIMINISHEDFOURTH);
            map.put(Arrays.asList(5, 4), Interval.PERFECTFOURTH);
            map.put(Arrays.asList(5, 3), Interval.AUGMENTEDTHIRD);
            map.put(Arrays.asList(6, 4), Interval.AUGMENTEDFOURTH);
            map.put(Arrays.asList(6, 5), Interval.DIMINISHEDFIFTH);
            map.put(Arrays.asList(7, 5), Interval.PERFECTFIFTH);
            map.put(Arrays.asList(7, 6), Interval.DIMINISHEDSIXTH);
            map.put(Arrays.asList(8, 6), Interval.MINORSIXTH);
            map.put(Arrays.asList(8, 5), Interval.AUGMENTEDFIFTH);
            map.put(Arrays.asList(9, 6), Interval.MAJORSIXTH);
            map.put(Arrays.asList(9, 7), Interval.DIMINISHEDSEVENTH);
            map.put(Arrays.asList(10, 7), Interval.MINORSEVENTH);
            map.put(Arrays.asList(10, 6), Interval.AUGMENTEDSIXTH);
            map.put(Arrays.asList(11, 7), Interval.MAJORSEVENTH);
            map.put(Arrays.asList(11, 8), Interval.DIMINISHEDOCTAVE);
            map.put(Arrays.asList(12, 8), Interval.PERFECTOCTAVE);
            map.put(Arrays.asList(12, 7), Interval.AUGMENTEDSEVENTH);
            map.put(Arrays.asList(12, 9), Interval.DIMINISHEDNINTH);
            map.put(Arrays.asList(13, 9), Interval.MINORNINTH);
            map.put(Arrays.asList(13, 8), Interval.AUGMENTEDOCTAVE);
            map.put(Arrays.asList(14, 9), Interval.MAJORNINTH);
            map.put(Arrays.asList(14, 10), Interval.DIMINISHEDTENTH);
            map.put(Arrays.asList(15, 10), Interval.MINORTENTH);
            map.put(Arrays.asList(15, 9), Interval.AUGMENTEDNINTH);
            map.put(Arrays.asList(16, 10), Interval.MAJORTENTH);
            map.put(Arrays.asList(16, 11), Interval.DIMINISHEDELEVENTH);
            map.put(Arrays.asList(17, 11), Interval.PERFECTELEVENTH);
            map.put(Arrays.asList(17, 10), Interval.AUGMENTEDTENTH);
            map.put(Arrays.asList(18, 11), Interval.AUGMENTEDELEVENTH);
            map.put(Arrays.asList(18, 12), Interval.DIMINISHEDTWELFTH);
            map.put(Arrays.asList(19, 12), Interval.PERFECTTWELFTH);
            map.put(Arrays.asList(19, 13), Interval.DIMINISHEDTHIRTEENTH);
            map.put(Arrays.asList(20, 13), Interval.MINORTHIRTEENTH);
            map.put(Arrays.asList(20, 12), Interval.AUGMENTEDTWELFTH);
            map.put(Arrays.asList(21, 13), Interval.MAJORTHIRTEENTH);
            map.put(Arrays.asList(21, 14), Interval.DIMINISHEDFOURTEENTH);
            map.put(Arrays.asList(22, 14), Interval.MINORFOURTEENTH);
            map.put(Arrays.asList(22, 13), Interval.AUGMENTEDTHIRTEENTH);
            map.put(Arrays.asList(23, 14), Interval.MAJORFOURTEENTH);
            map.put(Arrays.asList(23, 15), Interval.DIMINISHEDFIFTEENTH);
            map.put(Arrays.asList(24, 15), Interval.PERFECTFIFTEENTH);
            map.put(Arrays.asList(24, 14), Interval.AUGMENTEDFOURTEENTH);
            semitoneAndNumberToIntervalMap = map;
        }
        return Collections.unmodifiableMap(semitoneAndNumberToIntervalMap);
    }


    /**
     * Map of semitone to Intervals.
     * @return semitoneToIntervalsMap
     */
    public static Map<Integer, List<Interval>> getSemitoneToIntervalsMap() {
        if (semitoneToIntervalsMap == null) {
            Map<Integer, List<Interval>> map = new HashMap<>();
            map.put(0, Arrays.asList(Interval.PERFECTUNISON, Interval.DIMINISHEDSECOND));
            map.put(1, Arrays.asList(Interval.MINORSECOND));
            map.put(2, Arrays.asList(Interval.MAJORSECOND, Interval.DIMINISHEDTHIRD));
            map.put(3, Arrays.asList(Interval.MINORTHIRD));
            map.put(4, Arrays.asList(Interval.MAJORTHIRD, Interval.DIMINISHEDFOURTH));
            map.put(5, Arrays.asList(Interval.PERFECTFOURTH));
            map.put(6, Arrays.asList(Interval.DIMINISHEDFIFTH));
            map.put(7, Arrays.asList(Interval.PERFECTFIFTH, Interval.DIMINISHEDSIXTH));
            map.put(8, Arrays.asList(Interval.MINORSIXTH));
            map.put(9, Arrays.asList(Interval.MAJORSIXTH, Interval.DIMINISHEDSEVENTH));
            map.put(10, Arrays.asList(Interval.MINORSEVENTH));
            map.put(11, Arrays.asList(Interval.MAJORSEVENTH, Interval.DIMINISHEDOCTAVE));
            map.put(12, Arrays.asList(Interval.PERFECTOCTAVE, Interval.DIMINISHEDNINTH));
            map.put(13, Arrays.asList(Interval.MINORNINTH));
            map.put(14, Arrays.asList(Interval.MAJORNINTH, Interval.DIMINISHEDTENTH));
            map.put(15, Arrays.asList(Interval.MINORTENTH));
            map.put(16, Arrays.asList(Interval.MAJORTENTH, Interval.DIMINISHEDELEVENTH));
            map.put(17, Arrays.asList(Interval.PERFECTELEVENTH));
            map.put(18, Arrays.asList(Interval.DIMINISHEDTWELFTH));
            map.put(19, Arrays.asList(Interval.PERFECTTWELFTH, Interval.DIMINISHEDTHIRTEENTH));
            map.put(20, Arrays.asList(Interval.MINORTHIRTEENTH));
            map.put(21, Arrays.asList(Interval.MAJORTHIRTEENTH, Interval.DIMINISHEDFOURTEENTH));
            map.put(22, Arrays.asList(Interval.MINORFOURTEENTH));
            map.put(23, Arrays.asList(Interval.MAJORFOURTEENTH, Interval.DIMINISHEDFIFTEENTH));
            map.put(24, Arrays.asList(Interval.PERFECTFIFTEENTH));
            semitoneToIntervalsMap = map;
        }
        return Collections.unmodifiableMap(semitoneToIntervalsMap);
    }
}