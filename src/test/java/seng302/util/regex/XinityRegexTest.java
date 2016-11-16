package seng302.util.regex;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Note Regex tests.
 *
 * @author adg62
 */
public class XinityRegexTest {
    /** Simple note, does not take double sharps/flats. */
    String xinitySimpleNote = "([a-gA-G]{1})([#|b]?)(([-]?[0-9]+)?)?";

    /** Full note, accepts double sharps and flats. */
    String xinityFullNote = "([a-gA-G]){1}(x|b{1,2}|#{1,2})?(-?[0-9]+)?";

    /** Simple Note: Check valid values do pass regex. */
    @Test
    public void simpleNoteA4Valid() {
        assertTrue("A4".matches(xinitySimpleNote));
    }

    @Test
    public void simpleNoteC4Valid() {
        assertTrue("A4".matches(xinitySimpleNote));
    }

    @Test
    public void simpleNoteCValid() {
        assertTrue("C".matches(xinitySimpleNote));
    }

    @Test
    public void simpleNoteCNegative1Valid() {
        assertTrue("C-1".matches(xinitySimpleNote));
    }

    @Test
    public void simpleNoteG9Valid() {
        assertTrue("G9".matches(xinitySimpleNote));
    }

    @Test
    public void simpleNoteESharpValid() {
        assertTrue("E#".matches(xinitySimpleNote));
    }

    @Test
    public void simpleNoteEFlatValid() {
        assertTrue("Eb".matches(xinitySimpleNote));
    }

    /** Simple Note: Check values don't pass regex. */
    @Test
    public void simpleNoteAAInvalid() {
        assertFalse("Aa".matches(xinitySimpleNote));
    }

    @Test
    public void simpleNoteGDoubleSharpInvalid() {
        assertFalse("G##".matches(xinitySimpleNote));
    }

    @Test
    public void simpleNoteGXInvalid() {
        assertFalse("Gx".matches(xinitySimpleNote));
    }

    @Test
    public void simpleNoteGDoubleFlatInvalid() {
        assertFalse("Gbb".matches(xinitySimpleNote));
    }

    @Test
    public void simpleNote4Invalid() {
        assertFalse("4".matches(xinitySimpleNote));
    }

    @Test
    public void simpleNoteY4Invalid() {
        assertFalse("Y4".matches(xinitySimpleNote));
    }

    @Test
    public void simpleNoteHSharp4Invalid() {
        assertFalse("H#4".matches(xinitySimpleNote));
    }

    /** Full Note: Check valid values do pass regex. */
    @Test
    public void fullNoteA4Valid() {
        assertTrue("A4".matches(xinityFullNote));
    }

    @Test
    public void fullNoteC4Valid() {
        assertTrue("A4".matches(xinityFullNote));
    }

    @Test
    public void fullNoteCValid() {
        assertTrue("C".matches(xinityFullNote));
    }

    @Test
    public void fullNoteCNegative1Valid() {
        assertTrue("C-1".matches(xinityFullNote));
    }

    @Test
    public void fullNoteG9Valid() {
        assertTrue("G9".matches(xinityFullNote));
    }

    @Test
    public void fullNoteESharpValid() {
        assertTrue("E#".matches(xinityFullNote));
    }

    @Test
    public void fullNoteEDoubleSharpValid() {
        assertTrue("E##".matches(xinityFullNote));
    }

    @Test
    public void fullNoteEDoubleSharp9Valid() {
        assertTrue("E##9".matches(xinityFullNote));
    }

    @Test
    public void fullNoteEFlatValid() {
        assertTrue("Eb".matches(xinityFullNote));
    }

    @Test
    public void fullNoteEDoubleFlatValid() {
        assertTrue("Ebb".matches(xinityFullNote));
    }

    @Test
    public void fullNoteEDoubleFlat2Valid() {
        assertTrue("Ebb2".matches(xinityFullNote));
    }

    /** Simple Note: Check values don't pass regex. */
    @Test
    public void fullNoteAAInvalid() {
        assertFalse("Aa".matches(xinityFullNote));
    }

    @Test
    public void fullNote4Invalid() {
        assertFalse("4".matches(xinityFullNote));
    }

    @Test
    public void fullNoteY4Invalid() {
        assertFalse("Y4".matches(xinityFullNote));
    }

    @Test
    public void fullNoteHSharp4Invalid() {
        assertFalse("H#4".matches(xinityFullNote));
    }

    @Test
    public void fullNoteGarbageInvalid() {
        assertFalse("a4sd45kj".matches(xinityFullNote));
    }
}
