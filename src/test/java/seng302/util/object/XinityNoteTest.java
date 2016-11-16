package seng302.util.object;

import org.junit.Test;
import seng302.util.enumerator.NoteAccidental;

import static org.junit.Assert.*;

/**
 * @author adg62, plr37
 */
public class XinityNoteTest {

    @Test
    public void equals() throws Exception {
        XinityNote note1 = new XinityNote("C4");
        XinityNote note2 = new XinityNote("C4");
        XinityNote note3 = new XinityNote("C");
        XinityNote note4 = new XinityNote("C5");
        XinityNote note5 = new XinityNote("C#");
        XinityNote note6 = new XinityNote("C#4");

        assertEquals(note1, note2);
        assertEquals(note1, note3);
        assertNotEquals(note1, note4);
        assertNotEquals(note4, note5);
        assertEquals(note5, note6);
    }

    @Test
    public void compareTo() throws Exception {
        XinityNote note1 = new XinityNote("C4");
        XinityNote note2 = new XinityNote("C4");
        XinityNote note3 = new XinityNote("F");
        XinityNote note4 = new XinityNote("C5");
        XinityNote note5 = new XinityNote("E#");
        XinityNote note6 = new XinityNote("C#4");

        assertEquals(0, note1.compareTo(note2));
        assertEquals(-1, note1.compareTo(note4));
        assertEquals(1, note4.compareTo(note6));
        assertEquals(1, note3.compareTo(note5));
        assertEquals(-1, note5.compareTo(note3));
    }

    @Test
    public void noteASharp4() throws Exception {
        XinityNote note = new XinityNote("A#4");

        assertTrue(note.getLetter().equals("A"));
        assertTrue(note.getAccidental().equals(NoteAccidental.SHARP));
        assertTrue(note.getOctave().toString().equals("4"));
        assertFalse(note.getIsDouble());
    }

    @Test
    public void noteGSharp9() throws Exception {
        XinityNote note = new XinityNote("G#9");

        assertTrue(note.getLetter().equals("G"));
        assertTrue(note.getAccidental().equals(NoteAccidental.SHARP));
        assertTrue(note.getOctave().toString().equals("9"));
        assertFalse(note.getIsDouble());
    }

    @Test
    public void noteALowerSharp4() throws Exception {
        XinityNote note = new XinityNote("b#4");

        assertTrue(note.getLetter().equals("B"));
        assertTrue(note.getAccidental().equals(NoteAccidental.SHARP));
        assertTrue(note.getOctave().toString().equals("4"));
        assertFalse(note.getIsDouble());
    }

    @Test
    public void noteA4() throws Exception {
        XinityNote note = new XinityNote("A9");

        assertTrue(note.getLetter().equals("A"));
        assertTrue(note.getAccidental().equals(NoteAccidental.NATURAL));
        assertTrue(note.getOctave().toString().equals("9"));
        assertFalse(note.getIsDouble());
    }

    @Test
    public void noteADoubleSharp4() throws Exception {
        XinityNote note = new XinityNote("A##4");

        assertTrue(note.getLetter().equals("A"));
        assertTrue(note.getAccidental().equals(NoteAccidental.DOUBLESHARP));
        assertTrue(note.getOctave().toString().equals("4"));
        assertTrue(note.getIsDouble());
    }

    @Test
    public void noteASharpSharp() throws Exception {
        XinityNote note = new XinityNote("A##");

        assertTrue(note.getLetter().equals("A"));
        assertTrue(note.getAccidental().equals(NoteAccidental.DOUBLESHARP));
        assertTrue(note.getOctave().toString().equals("4"));
        assertTrue(note.getIsDouble());
    }

    @Test
    public void noteADoubleSharp() throws Exception {
        XinityNote note = new XinityNote("Ax");

        assertTrue(note.getLetter().equals("A"));
        assertTrue(note.getAccidental().equals(NoteAccidental.DOUBLESHARP));
        assertTrue(note.getOctave().toString().equals("4"));
        assertTrue(note.getIsDouble());
    }

    @Test
    public void noteCLowerDoubleSharp() throws Exception {
        XinityNote note = new XinityNote("cx");

        assertTrue(note.getLetter().equals("C"));
        assertTrue(note.getAccidental().equals(NoteAccidental.DOUBLESHARP));
        assertTrue(note.getOctave().toString().equals("4"));
        assertTrue(note.getIsDouble());
    }

    @Test
    public void noteInvalidAALowercase() throws Exception {
        XinityNote note;
        try {
            note = new XinityNote("aa");
        } catch (Exception e) {
            return;
        }
        assertEquals(null, note);
    }

    @Test
    public void noteInvalidNumbers() throws Exception {
        XinityNote note;
        try {
            note = new XinityNote("145468");
        } catch (Exception e) {
            return;
        }
        assertEquals(null, note);
    }

    @Test
    public void noteInvalidNoteASharpFlat4() throws Exception {
        XinityNote note;
        try {
            note = new XinityNote("A#b4");
        } catch (Exception e) {
            return;
        }
        assertEquals(null, note);
    }

    @Test
    public void noteInvalidNoteAFlatSharp4() throws Exception {
        XinityNote note;
        try {
            note = new XinityNote("Ab#4");
        } catch (Exception e) {
            return;
        }
        assertEquals(null, note);
    }
}