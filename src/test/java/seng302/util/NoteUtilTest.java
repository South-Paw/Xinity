package seng302.util;

import org.junit.Test;
import seng302.util.object.XinityNote;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class NoteUtilTest {

    @Test
    public void convertNoteToMidi() throws Exception {
        XinityNote inputNote = new XinityNote("C4");

        Integer midiNote = NoteUtil.convertToMidi(inputNote);

        assertEquals((int) midiNote, 60);
    }

    @Test
    public void convertToNote() throws Exception {
        String letterNote = NoteUtil.convertToNote(60);

        assertEquals(letterNote, "C4");
    }

    @Test
    public void sortNotes1() throws Exception {
        List<XinityNote> args =
                NoteUtil.arrayStringAsNoteArray(Arrays.asList("B", "A", "G", "F", "E", "Db", "C#"));
        List<XinityNote> expected =
                NoteUtil.arrayStringAsNoteArray(Arrays.asList("C#", "Db", "E", "F", "G", "A", "B"));

        NoteUtil.sort(args);

        assertEquals(expected, args);
    }

    @Test
    public void sortNotes2() throws Exception {
        List<XinityNote> args =
                NoteUtil.arrayStringAsNoteArray(Arrays.asList("Db", "Bx3","C#"));
        List<XinityNote> expected =
                NoteUtil.arrayStringAsNoteArray(Arrays.asList("Bx3", "C#", "Db"));

        NoteUtil.sort(args);
        assertEquals(expected, args);
    }
}
