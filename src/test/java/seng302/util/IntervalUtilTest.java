package seng302.util;

import org.junit.Test;
import seng302.util.enumerator.Interval;
import seng302.util.object.XinityNote;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IntervalUtilTest {
    @Test
    public void getSemitonePerfectUnison() {
        Integer semitones = IntervalUtil.getSemitones(Interval.PERFECTUNISON);
        assertEquals((Integer) 0, semitones);
    }
    @Test
    public void getSemitonePerfectFourth() {
        Integer semitones = IntervalUtil.getSemitones(Interval.PERFECTFOURTH);
        assertEquals((Integer) 5, semitones);
    }
    @Test
    public void getSemitonePerfectFifth() {
        Integer semitones = IntervalUtil.getSemitones(Interval.PERFECTFIFTH);
        assertEquals((Integer) 7, semitones);
    }
    @Test
    public void getSemitonePerfectOctave() {
        Integer semitones = IntervalUtil.getSemitones(Interval.PERFECTOCTAVE);
        assertEquals((Integer) 12, semitones);
    }
    @Test
    public void getSemitoneMinorSecond() {
        Integer semitones = IntervalUtil.getSemitones(Interval.MINORSECOND);
        assertEquals((Integer) 1, semitones);
    }
    @Test
    public void getSemitoneMinorThird() {
        Integer semitones = IntervalUtil.getSemitones(Interval.MINORTHIRD);
        assertEquals((Integer) 3, semitones);
    }
    @Test
    public void getSemitoneMinorSixth() {
        Integer semitones = IntervalUtil.getSemitones(Interval.MINORSIXTH);
        assertEquals((Integer) 8, semitones);
    }
    @Test
    public void getSemitoneMinorSeventh() {
        Integer semitones = IntervalUtil.getSemitones(Interval.MINORSEVENTH);
        assertEquals((Integer) 10, semitones);
    }

    @Test
    public void getSemitoneMajorSecond() {
        Integer semitones = IntervalUtil.getSemitones(Interval.MAJORSECOND);
        assertEquals((Integer) 2, semitones);
    }
    @Test
    public void getSemitoneMajorThird() {
        Integer semitones = IntervalUtil.getSemitones(Interval.MAJORTHIRD);
        assertEquals((Integer) 4, semitones);
    }
    @Test
    public void getSemitoneMajorSixth() {
        Integer semitones = IntervalUtil.getSemitones(Interval.MAJORSIXTH);
        assertEquals((Integer) 9, semitones);
    }
    @Test
    public void getSemitoneMajorSeventh() {
        Integer semitones = IntervalUtil.getSemitones(Interval.MAJORSEVENTH);
        assertEquals((Integer) 11, semitones);
    }
    @Test
    public void intervalPerfectUnisonA4() throws Exception {
        XinityNote tonic = new XinityNote("A4");
        XinityNote upperNote =  IntervalUtil.interval(tonic, Interval.PERFECTUNISON);
        assertEquals(new XinityNote("A4"), upperNote);
    }
    @Test
    public void intervalMajor2ndBSharp() throws Exception {
        XinityNote tonic = new XinityNote("B#");
        XinityNote upperNote = IntervalUtil.interval(tonic, Interval.MAJORSECOND);
        assertEquals(new XinityNote("Cx5"), upperNote);
    }
    @Test
    public void intervalMinorThirteenthG5() throws Exception {
        XinityNote tonic = new XinityNote("G5");
        XinityNote upperNote = IntervalUtil.interval(tonic, Interval.MINORTHIRTEENTH);
        assertEquals(new XinityNote("Eb7"), upperNote);
    }
    @Test
    public void intervalDiminished10thE2() throws Exception {
        XinityNote tonic = new XinityNote("E2");
        XinityNote upperNote = IntervalUtil.interval(tonic, Interval.DIMINISHEDTENTH);
        assertEquals(new XinityNote("Gb3"), upperNote);
    }
    @Test
    public void getInterval0() throws Exception {
        Integer semitones = 0;
        assertEquals(Interval.PERFECTUNISON, IntervalUtil.getInterval(semitones));
    }
    @Test
    public void getInterval3() throws Exception {
        Integer semitones = 3;
        assertEquals(Interval.MINORTHIRD, IntervalUtil.getInterval(semitones));
    }
    @Test
    public void getInterval6() throws Exception {
        Integer semitones = 6;
        assertEquals(Interval.DIMINISHEDFIFTH, IntervalUtil.getInterval(semitones));
    }
    @Test
    public void getInterval12() throws Exception {
        Integer semitones = 12;
        assertEquals(Interval.PERFECTOCTAVE, IntervalUtil.getInterval(semitones));
    }
    @Test
    public void getInterval15() throws Exception {
        Integer semitones = 15;
        assertEquals(Interval.MINORTENTH, IntervalUtil.getInterval(semitones));
    }
    @Test
    public void getInterval18() throws Exception {
        Integer semitones = 18;
        assertEquals(Interval.DIMINISHEDTWELFTH, IntervalUtil.getInterval(semitones));
    }
    @Test
    public void getInterval21() throws Exception {
        Integer semitones = 21;
        assertEquals(Interval.MAJORTHIRTEENTH, IntervalUtil.getInterval(semitones));
    }
    @Test
    public void getInterval24() throws Exception {
        Integer semitones = 24;
        assertEquals(Interval.PERFECTFIFTEENTH, IntervalUtil.getInterval(semitones));
    }

    // hasEnharmonic(String interval) TESTS BEGIN //

    @Test
    public void hasEnharmonicPerfectUnison() throws Exception {
        assertTrue(IntervalUtil.hasEnharmonic(Interval.PERFECTUNISON));
    }

    @Test
    public void hasEnharmonicDiminishedSecond() throws Exception {
        assertTrue(IntervalUtil.hasEnharmonic(Interval.DIMINISHEDSECOND));
    }

    @Test
    public void hasEnharmonicMajorSecond() throws Exception {
        assertTrue(IntervalUtil.hasEnharmonic(Interval.MAJORSECOND));
    }

    @Test
    public void hasEnharmonicMinorFourteenth() throws Exception {
        assertFalse(IntervalUtil.hasEnharmonic(Interval.MINORFOURTEENTH));
    }

    @Test
    public void hasEnharmonicDiminishedFifteenth() throws Exception {
        assertTrue(IntervalUtil.hasEnharmonic(Interval.DIMINISHEDFIFTEENTH));
    }

    @Test
    public void hasEnharmonicAugmentedThirteenth() throws Exception {
        assertFalse(IntervalUtil.hasEnharmonic(Interval.AUGMENTEDTHIRTEENTH));
    }

    //getEnharmonic(String interval) tests begin

    @Test
    public void getEnharmonicPerfectUnison() throws Exception {
        assertEquals(Interval.DIMINISHEDSECOND, IntervalUtil.getEnharmonic(Interval.PERFECTUNISON));
    }

    @Test
    public void getEnharmonicMajorSecond() throws Exception {
        assertEquals(Interval.DIMINISHEDTHIRD, IntervalUtil.getEnharmonic(Interval.MAJORSECOND));
    }

    @Test
    public void getEnharmonicDiminishedSeventh() throws Exception {
        assertEquals(Interval.MAJORSIXTH, IntervalUtil.getEnharmonic(Interval.DIMINISHEDSEVENTH));
    }

    @Test
    public void getEnharmonicMinorSixth() throws Exception {
        assertEquals(null, IntervalUtil.getEnharmonic(Interval.MINORSIXTH));
    }

    @Test
    public void getEnharmonicPerfectFourth() throws Exception {
        assertEquals(null, IntervalUtil.getEnharmonic(Interval.PERFECTFOURTH));
    }

    @Test
    public void getEnharmonicMinorThirteenth() throws Exception {
        assertEquals(null, IntervalUtil.getEnharmonic(Interval.MINORTHIRTEENTH));
    }

    // findInterval - second note is always higher

    @Test
    public void findIntervalCGSharp() throws Exception {
        Interval interval;
        interval = IntervalUtil.findInterval(new XinityNote("C"), new XinityNote("G#"));
        assertEquals(Interval.AUGMENTEDFIFTH, interval);
    }

    @Test
    public void findIntervalE4C5() throws Exception {
        Interval interval;
        interval = IntervalUtil.findInterval(new XinityNote("E4"), new XinityNote("C5"));
        assertEquals(Interval.MINORSIXTH, interval);
    }

    @Test
    public void findIntervalCD() throws Exception {
        Interval interval;
        interval = IntervalUtil.findInterval(new XinityNote("C"), new XinityNote("D"));
        assertEquals(Interval.MAJORSECOND, interval);
    }

    @Test
    public void findIntervalCB() throws Exception {
        Interval interval;
        interval = IntervalUtil.findInterval(new XinityNote("C"), new XinityNote("B"));
        assertEquals(Interval.MAJORSEVENTH, interval);
    }

    @Test
    public void findIntervalCSharpD() throws Exception {
        Interval interval;
        interval = IntervalUtil.findInterval(new XinityNote("C#"), new XinityNote("D"));
        assertEquals(Interval.MINORSECOND, interval);
    }

    @Test
    public void findIntervalC4C5() throws Exception {
        Interval interval;
        interval = IntervalUtil.findInterval(new XinityNote("C4"), new XinityNote("C5"));
        assertEquals(Interval.PERFECTOCTAVE, interval);
    }

    @Test
    public void findIntervalB4C5() throws Exception {
        Interval interval;
        interval = IntervalUtil.findInterval(new XinityNote("B4"), new XinityNote("C5"));
        assertEquals(Interval.MINORSECOND, interval);
    }

    @Test
    public void findIntervalC3C5() throws Exception {
        Interval interval;
        interval = IntervalUtil.findInterval(new XinityNote("C3"), new XinityNote("C5"));
        assertEquals(Interval.PERFECTFIFTEENTH, interval);
    }

    @Test
    public void findIntervalC3Gb3() throws Exception {
        Interval interval;
        interval = IntervalUtil.findInterval(new XinityNote("C3"), new XinityNote("Gb3"));
        assertEquals(Interval.DIMINISHEDFIFTH, interval);
    }

    @Test
    public void findIntervalC3FSharp3() throws Exception {
        Interval interval;
        interval = IntervalUtil.findInterval(new XinityNote("C3"), new XinityNote("F#3"));
        assertEquals(Interval.AUGMENTEDFOURTH, interval);
    }

    @Test
    public void findIntervalDGSharp() throws Exception {
        Interval interval;
        interval = IntervalUtil.findInterval(new XinityNote("D"), new XinityNote("G#"));
        assertEquals(Interval.AUGMENTEDFOURTH, interval);
    }

    @Test
    public void findIntervalDAb() throws Exception {
        Interval interval;
        interval = IntervalUtil.findInterval(new XinityNote("D"), new XinityNote("Ab"));
        assertEquals(Interval.DIMINISHEDFIFTH, interval);
    }
}
