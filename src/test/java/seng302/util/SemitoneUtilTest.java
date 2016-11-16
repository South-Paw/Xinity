package seng302.util;

import org.junit.Test;
import seng302.util.object.XinityNote;

import static org.junit.Assert.*;

/**
 * @author ljm163, adg62
 */
public class SemitoneUtilTest {
    @Test
    public void middleCIncrement1() throws Exception {
        XinityNote note = new XinityNote("C4");
        XinityNote neighbouringNote = SemitoneUtil.neighbouringNote(1, note);

        assertEquals(new XinityNote("C#4"), neighbouringNote);
    }

    @Test
    public void A9Increment1() throws Exception {
        XinityNote note = new XinityNote("A9");
        XinityNote neighbouringNote = SemitoneUtil.neighbouringNote(1, note);

        assertEquals(new XinityNote("A#9"), neighbouringNote);
    }

    @Test
    public void CNegative1DeIncrement1() throws Exception {
        XinityNote note = new XinityNote("C-1");
        XinityNote neighbouringNote = SemitoneUtil.neighbouringNote(-1, note);

        assertEquals(new XinityNote("B-2"), neighbouringNote);
    }

    @Test
    public void CNegative1Increment1() throws Exception {
        XinityNote note = new XinityNote("C-1");
        XinityNote neighbouringNote = SemitoneUtil.neighbouringNote(1, note);

        assertEquals(new XinityNote("C#-1"), neighbouringNote);
    }

    @Test
    public void FIncrement10() throws Exception {
        XinityNote note = new XinityNote("F");
        XinityNote neighbouringNote = SemitoneUtil.neighbouringNote(10, note);

        assertEquals(new XinityNote("D#5"), neighbouringNote);
    }

    @Test
    public void D6Increment4() throws Exception {
        XinityNote note = new XinityNote("D6");
        XinityNote neighbouringNote = SemitoneUtil.neighbouringNote(4, note);

        assertEquals(new XinityNote("F#6"), neighbouringNote);
    }

    @Test
    public void D6Increment12() throws Exception {
        XinityNote note = new XinityNote("D6");
        XinityNote neighbouringNote = SemitoneUtil.neighbouringNote(12, note);

        assertEquals(new XinityNote("D7"), neighbouringNote);
    }

    @Test
    public void Fb2Increment25() throws Exception {
        XinityNote note = new XinityNote("Fb2");
        XinityNote neighbouringNote = SemitoneUtil.neighbouringNote(25, note);

        assertEquals(new XinityNote("F4"), neighbouringNote);
    }
}