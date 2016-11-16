package seng302.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author adg62, jps100, ljm163
 */
public class TempoUtilTest {
    @Before
    public void setUp() throws Exception {
        TempoUtil.resetTempo();
    }

    @Test
    public void getTempo() throws Exception {
        Integer tempo = TempoUtil.getTempo();

        assertEquals(120, (int) tempo);
    }

    @Test
    public void setTempo() throws Exception {
        TempoUtil.setTempo(140);

        Integer tempo = TempoUtil.getTempo();

        assertEquals(140, (int) tempo);
    }

    @Test
    public void getCrotchet() throws Exception {
        Integer crotchet = TempoUtil.getCrotchet();

        assertEquals(500, (int) crotchet);
    }

    @Test
    public void resetTempo() throws Exception {
        TempoUtil.setTempo(140);

        Integer tempo = TempoUtil.getTempo();
        Integer crotchet = TempoUtil.getCrotchet();

        assertEquals(140, (int) tempo);
        assertEquals(429, (int) crotchet);

        setUp();

        Integer tempo2 = TempoUtil.getTempo();
        Integer crotchet2 = TempoUtil.getCrotchet();

        assertEquals(120, (int) tempo2);
        assertEquals(500, (int) crotchet2);
    }

    @Test
    public void swingFirstSplitDefaultTempo() {
        TempoUtil.resetTempo();

        Float firstSplit = ((float) 2 / (float) 3) * TempoUtil.getCrotchet();

        assertEquals(firstSplit, TempoUtil.getFirstSwingSplit());
    }

    @Test
    public void swingSecondSplitDefaultTempo() {
        TempoUtil.resetTempo();

        Float secondSplit = ((float) 1 / (float) 3) * TempoUtil.getCrotchet();

        assertEquals(secondSplit, TempoUtil.getSecondSwingSplit());
    }

    @Test
    public void swingFirstSplit300Tempo() {
        TempoUtil.resetTempo();
        TempoUtil.setTempo(300);

        Float firstSplit = ((float) 2 / (float) 3) * TempoUtil.getCrotchet();

        assertEquals(firstSplit, TempoUtil.getFirstSwingSplit());
    }

    @Test
    public void swingSecondSplit300Tempo() {
        TempoUtil.resetTempo();
        TempoUtil.setTempo(300);

        Float secondSplit = ((float) 1 / (float) 3) * TempoUtil.getCrotchet();

        assertEquals(secondSplit, TempoUtil.getSecondSwingSplit());
    }

}