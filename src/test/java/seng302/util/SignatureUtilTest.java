package seng302.util;

import org.junit.Test;
import seng302.util.enumerator.ScaleType;

import static org.junit.Assert.*;

/**
 *
 * @author plr37
 */
public class SignatureUtilTest {
    // DETERMINESCALE TESTS BEGING //

    // Major scale //
    @Test
    public void determineScaleMajorInvalid() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("invalid", ScaleType.MAJOR);

        assertNull(expected);
    }

    @Test
    public void determineScaleMajor0() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("0#", ScaleType.MAJOR);

        assertEquals("C", expected);
    }

    @Test
    public void determineScaleMajor2Sharop() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("2#", ScaleType.MAJOR);

        assertEquals("D", expected);
    }

    @Test
    public void determineScaleMajor3sharp() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("3#", ScaleType.MAJOR);

        assertEquals("A", expected);
    }

    @Test
    public void determineScaleMajor4sharp() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("4#", ScaleType.MAJOR);

        assertEquals("E", expected);
    }

    @Test
    public void determineScaleMajor5sharp() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("5#", ScaleType.MAJOR);

        assertEquals("B", expected);
    }

    @Test
    public void determineScaleMajor6sharp() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("6#", ScaleType.MAJOR);

        assertEquals("F#", expected);
    }

    @Test
    public void determineScaleMajor7sharp() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("7#", ScaleType.MAJOR);

        assertEquals("C#", expected);
    }

    @Test
    public void determineScaleMajor1Flat() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("1b", ScaleType.MAJOR);

        assertEquals("F", expected);
    }

    @Test
    public void determineScaleMajor2Flat() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("2b", ScaleType.MAJOR);

        assertEquals("Bb", expected);
    }

    @Test
    public void determineScaleMajor3Flat() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("3b", ScaleType.MAJOR);

        assertEquals("Eb", expected);
    }

    @Test
    public void determineScaleMajor4Flat() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("4b", ScaleType.MAJOR);

        assertEquals("Ab", expected);
    }

    @Test
    public void determineScaleMajor5Flat() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("5b", ScaleType.MAJOR);

        assertEquals("Db", expected);
    }

    @Test
    public void determineScaleMajor6Flat() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("6b", ScaleType.MAJOR);

        assertEquals("Gb", expected);
    }

    @Test
    public void determineScaleMajor7Flat() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("7b", ScaleType.MAJOR);

        assertEquals("Cb", expected);
    }


    // Minor Scale //
    @Test
    public void determineScaleMinorInvalid() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("invalid", ScaleType.MINOR);

        assertNull(expected);
    }

    @Test
    public void determineScaleMinor0() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("0", ScaleType.MINOR);

        assertEquals("A", expected);
    }

    @Test
    public void determineScaleMinor1Sharp() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("1#", ScaleType.MINOR);

        assertEquals("E", expected);
    }

    @Test
    public void determineScaleMinor2Sharp() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("2#", ScaleType.MINOR);

        assertEquals("B", expected);
    }

    @Test
    public void determineScaleMinor3Sharp() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("3#", ScaleType.MINOR);

        assertEquals("F#", expected);
    }

    @Test
    public void determineScaleMinor4Sharp() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("4#", ScaleType.MINOR);

        assertEquals("C#", expected);
    }

    @Test
    public void determineScaleMinor5Sharp() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("5#", ScaleType.MINOR);

        assertEquals("G#", expected);
    }

    @Test
    public void determineScaleMinor6Sharp() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("6#", ScaleType.MINOR);

        assertEquals("D#", expected);
    }

    @Test
    public void determineScaleMinor7Sharp() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("7#", ScaleType.MINOR);

        assertEquals("A#", expected);
    }

    @Test
    public void determineScaleMinor1Flat() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("1b", ScaleType.MINOR);

        assertEquals("D", expected);
    }

    @Test
    public void determineScaleMinor2Flat() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("2b", ScaleType.MINOR);

        assertEquals("G", expected);
    }

    @Test
    public void determineScaleMinor3Flat() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("3b", ScaleType.MINOR);

        assertEquals("C", expected);
    }

    @Test
    public void determineScaleMinor4Flat() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("4b", ScaleType.MINOR);

        assertEquals("F", expected);
    }

    @Test
    public void determineScaleMinor5Flat() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("5b", ScaleType.MINOR);

        assertEquals("Bb", expected);
    }

    @Test
    public void determineScaleMinor6Flat() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("6b", ScaleType.MINOR);

        assertEquals("Eb", expected);
    }

    @Test
    public void determineScaleMinor7Flat() throws Exception {
        String expected = SignatureUtil.determineScaleFromKeySignature("7b", ScaleType.MINOR);

        assertEquals("Ab", expected);
    }

    @Test
    public void determineScaleCIonian() throws Exception {
        String result = SignatureUtil.determineScaleFromKeySignature("C", ScaleType.IONIAN);

        assertEquals(null, result);
    }
}
