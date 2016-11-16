package seng302.util;

import org.junit.Ignore;
import org.junit.Test;
import seng302.util.enumerator.ChordQuality;
import seng302.util.enumerator.Inversion;
import seng302.util.map.XinityIntervalMap;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

/**
 * @author mvj14, plr37
 */
public class ChordUtilTest {
    @Test
    public void chordMinorC() throws Exception {
        String quality = "minor triad";
        XinityNote root = new XinityNote("C");
        List<XinityNote> notes = ChordUtil.chord(root, ChordQuality.fromString(quality));
        List<String> output = Arrays.asList(notes.get(0).getNote(),
                notes.get(1).getNote(), notes.get(2).getNote());
        assertEquals(Arrays.asList("C4", "Eb4", "G4"), output);
    }
    @Test
    public void chordMinorCNeg1() throws Exception {
        String quality = "minor triad";
        XinityNote root = new XinityNote("C-1");
        List<XinityNote> notes = ChordUtil.chord(root, ChordQuality.fromString(quality));
        List<String> output = Arrays.asList(notes.get(0).getNote(),
                notes.get(1).getNote(), notes.get(2).getNote());
        assertEquals(Arrays.asList("C-1", "Eb-1", "G-1"), output);
    }
    @Test
    public void chordMajorDSharp() throws Exception {
        String quality = "major triad";
        XinityNote root = new XinityNote("D#");
        List<XinityNote> notes = ChordUtil.chord(root, ChordQuality.fromString(quality));
        List<String> output = Arrays.asList(notes.get(0).getNote(),
                notes.get(1).getNote(), notes.get(2).getNote());
        assertEquals(Arrays.asList("D#4", "Fx4", "A#4"), output);
    }
    @Test
    public void chordMajorG() throws Exception {
        String quality = "major triad";
        XinityNote root = new XinityNote("G");
        List<XinityNote> notes = ChordUtil.chord(root, ChordQuality.fromString(quality));
        List<String> output = Arrays.asList(notes.get(0).getNote(),
                notes.get(1).getNote(), notes.get(2).getNote());
        assertEquals(Arrays.asList("G4", "B4", "D5"), output);
    }
    @Test
    public void chordMajorG4() throws Exception {
        String quality = "major triad";
        XinityNote root = new XinityNote("G4");
        List<XinityNote> notes = ChordUtil.chord(root, ChordQuality.fromString(quality));
        List<String> output = Arrays.asList(notes.get(0).getNote(),
                notes.get(1).getNote(), notes.get(2).getNote());
        assertEquals(Arrays.asList("G4", "B4", "D5"), output);
    }
    @Test
    public void chordMajorG9() throws Exception {
        String quality = "major triad";
        XinityNote root = new XinityNote("Gbb9");
        List<XinityNote> notes = ChordUtil.chord(root, ChordQuality.fromString(quality));
        List<String> output = Arrays.asList(notes.get(0).getNote(),
                notes.get(1).getNote(), notes.get(2).getNote());
        assertEquals(Arrays.asList("Gbb9", "Bbb9", "Dbb10"), output);
    }
    @Test
    public void chordMinorFSharp5() throws Exception {
        String quality = "minor triad";
        XinityNote root = new XinityNote("F#5");
        List<XinityNote> notes = ChordUtil.chord(root, ChordQuality.fromString(quality));
        List<String> output = Arrays.asList(notes.get(0).getNote(),
                notes.get(1).getNote(), notes.get(2).getNote());
        assertEquals(Arrays.asList("F#5", "A5", "C#6"), output);
    }

    // START invertChord tests

    @Test
    public void invertChordCMajor1() throws Exception {
        XinityNote note1 = new XinityNote("C4");
        XinityNote note2 = new XinityNote("E4");
        XinityNote note3 = new XinityNote("G4");
        List<XinityNote> chord = new ArrayList<>();

        chord.add(note1);
        chord.add(note2);
        chord.add(note3);

        XinityNote expectedNote1 = new XinityNote("E4");
        XinityNote expectedNote2 = new XinityNote("G4");
        XinityNote expectedNote3 = new XinityNote("C5");

        List<XinityNote> expected = Arrays.asList(expectedNote1, expectedNote2, expectedNote3);
        ChordUtil.invertChord(chord, Inversion.FIRST);
        assertEquals(expected, chord);
    }

    @Test
    public void invertChordCMajor2() throws Exception {
        XinityNote note1 = new XinityNote("C4");
        XinityNote note2 = new XinityNote("E4");
        XinityNote note3 = new XinityNote("G4");
        List<XinityNote> chord = new ArrayList<>();

        chord.add(note1);
        chord.add(note2);
        chord.add(note3);

        XinityNote expectedNote1 = new XinityNote("G4");
        XinityNote expectedNote2 = new XinityNote("C5");
        XinityNote expectedNote3 = new XinityNote("E5");

        List<XinityNote> expected = Arrays.asList(expectedNote1, expectedNote2, expectedNote3);
        ChordUtil.invertChord(chord, Inversion.SECOND);
        assertEquals(expected, chord);
    }

    @Test
    public void invertChordDMajorRoot() throws Exception {
        XinityNote note1 = new XinityNote("D4");
        XinityNote note2 = new XinityNote("F#4");
        XinityNote note3 = new XinityNote("A4");
        List<XinityNote> chord = new ArrayList<>();

        chord.add(note1);
        chord.add(note2);
        chord.add(note3);

        XinityNote expectedNote1 = new XinityNote("D4");
        XinityNote expectedNote2 = new XinityNote("F#4");
        XinityNote expectedNote3 = new XinityNote("A4");

        List<XinityNote> expected = Arrays.asList(expectedNote1, expectedNote2, expectedNote3);
        ChordUtil.invertChord(chord, Inversion.ROOT);
        assertEquals(expected, chord);
    }

    @Test
    public void invertChordDMajor1() throws Exception {
        XinityNote note1 = new XinityNote("D4");
        XinityNote note2 = new XinityNote("F#4");
        XinityNote note3 = new XinityNote("A4");
        List<XinityNote> chord = new ArrayList<>();

        chord.add(note1);
        chord.add(note2);
        chord.add(note3);

        XinityNote expectedNote1 = new XinityNote("F#4");
        XinityNote expectedNote2 = new XinityNote("A4");
        XinityNote expectedNote3 = new XinityNote("D5");

        List<XinityNote> expected = Arrays.asList(expectedNote1, expectedNote2, expectedNote3);
        ChordUtil.invertChord(chord, Inversion.FIRST);
        assertEquals(expected, chord);
    }

    @Test
    public void invertChordBMajor1() throws Exception {
        XinityNote note1 = new XinityNote("B4");
        XinityNote note2 = new XinityNote("D#5");
        XinityNote note3 = new XinityNote("F#5");
        List<XinityNote> chord = new ArrayList<>();

        chord.add(note1);
        chord.add(note2);
        chord.add(note3);

        XinityNote expectedNote1 = new XinityNote("D#5");
        XinityNote expectedNote2 = new XinityNote("F#5");
        XinityNote expectedNote3 = new XinityNote("B5");

        List<XinityNote> expected = Arrays.asList(expectedNote1, expectedNote2, expectedNote3);
        ChordUtil.invertChord(chord, Inversion.FIRST);
        assertEquals(expected, chord);
    }

    @Test
    public void invertChordCMaj71() throws Exception {
        List<XinityNote> chord = new ArrayList<>();
        chord.add(new XinityNote("C"));
        chord.add(new XinityNote("E"));
        chord.add(new XinityNote("G"));
        chord.add(new XinityNote("B"));

        List<XinityNote> expected = new ArrayList<>();
        expected.add(new XinityNote("E4"));
        expected.add(new XinityNote("G4"));
        expected.add(new XinityNote("B4"));
        expected.add(new XinityNote("C5"));

        ChordUtil.invertChord(chord, Inversion.FIRST);
        assertEquals(expected, chord);
    }

    @Test
    public void invertChordEAugmented7th1() throws Exception {
        List<XinityNote> chord = new ArrayList<>();
        chord.add(new XinityNote("E4"));
        chord.add(new XinityNote("G4"));
        chord.add(new XinityNote("B#4"));
        chord.add(new XinityNote("D5"));

        List<XinityNote> expected = new ArrayList<>();
        expected.add(new XinityNote("G4"));
        expected.add(new XinityNote("B#4"));
        expected.add(new XinityNote("D5"));
        expected.add(new XinityNote("E5"));

        ChordUtil.invertChord(chord, Inversion.FIRST);
        assertEquals(expected, chord);
    }

    @Test
    public void invertChordEAugmented7th2() throws Exception {
        List<XinityNote> chord = new ArrayList<>();
        chord.add(new XinityNote("E4"));
        chord.add(new XinityNote("G4"));
        chord.add(new XinityNote("B#4"));
        chord.add(new XinityNote("D5"));

        List<XinityNote> expected = new ArrayList<>();
        expected.add(new XinityNote("B#4"));
        expected.add(new XinityNote("D5"));
        expected.add(new XinityNote("E5"));
        expected.add(new XinityNote("G5"));

        ChordUtil.invertChord(chord, Inversion.SECOND);
        assertEquals(expected, chord);
    }

    // END invertChord tests

    //ChordUtil.findChord tests
    @Test
    public void findChordCEG() throws Exception {
        List<String> notes = Arrays.asList("C", "E", "G");

        assertEquals(new LinkedHashSet<>(Arrays.asList("C Major")), ChordUtil.findChord(notes, false));
    }

    @Test
    public void findChordCEGSharp() throws Exception {
        List<String> notes = Arrays.asList("C", "E", "G#");

        assertEquals(new LinkedHashSet<>(Arrays.asList("C Augmented Triad", "E Augmented Triad", "G# Augmented Triad")),
                ChordUtil.findChord(notes, false));
    }

    @Test
    public void findChordEGSharpC() throws Exception {
        List<String> notes = Arrays.asList("E", "G#", "C");

        assertEquals(new LinkedHashSet<>(Arrays.asList("C Augmented Triad", "E Augmented Triad", "G# Augmented Triad")), ChordUtil.findChord(notes, false));
    }

    @Test
    public void findChordGSharpCE() throws Exception {
        List<String> notes = Arrays.asList("G#", "C", "E");

        assertEquals(new LinkedHashSet<>(Arrays.asList("C Augmented Triad", "E Augmented Triad", "G# Augmented Triad")), ChordUtil.findChord(notes, false));
    }

    @Test
    public void findChordABC() throws Exception {
        List<String> notes = Arrays.asList("A", "B", "C");

        assertNull(ChordUtil.findChord(notes, false));
    }

    @Test
    public void findChordGEC() throws Exception {
        List<String> notes = Arrays.asList("G", "E", "C");

        assertEquals(new LinkedHashSet<>(Arrays.asList("C Major")), ChordUtil.findChord(notes, false));
    }

    @Test
    public void findChordDFA() throws Exception {
        List<String> notes = Arrays.asList("D", "F", "A");

        assertEquals(new LinkedHashSet<>(Arrays.asList("D Minor")), ChordUtil.findChord(notes, false));
    }

    @Test
    public void findChordDFSharpA() throws Exception {
        List<String> notes = Arrays.asList("D", "F#", "A");

        assertEquals(new LinkedHashSet<>(Arrays.asList("D Major")), ChordUtil.findChord(notes, false));
    }

    @Test
    public void findChordASharpCDoubleSharpESharp() throws Exception {
        List<String> notes = Arrays.asList("A#", "Cx", "E#");

        assertEquals(new LinkedHashSet<>(Arrays.asList("A# Major")), ChordUtil.findChord(notes, false));
    }

    @Test
    public void findChordACSharpE() throws Exception {
        List<String> notes = Arrays.asList("A", "C#", "E");

        assertEquals(new LinkedHashSet<>(Arrays.asList("A Major")), ChordUtil.findChord(notes, false));
    }

    @Test
    public void findChordESharpGxBSharp() throws Exception {
        List<String> notes = Arrays.asList("E#", "Gx", "B#");

        assertEquals(new LinkedHashSet<>(Arrays.asList("E# Major")), ChordUtil.findChord(notes, false));
    }

    @Test
    public void findChordASharpCxESharp() throws Exception {
        List<String> notes = Arrays.asList("A#", "Cx", "E#");

        assertEquals(new LinkedHashSet<>(Arrays.asList("A# Major")), ChordUtil.findChord(notes, false));
    }

    @Test
    public void findChordESharpGSharpBSharpDDoubleSharp() throws Exception {
        List<String> notes = Arrays.asList("E#", "Gx", "B#", "Dx");

        assertEquals(new LinkedHashSet<>(Arrays.asList("E# Major 7th")), ChordUtil.findChord(notes, false));
    }

    @Test
    public void findChordDSharpFSharpBInversion() throws Exception {
        List<String> notes = Arrays.asList("D#", "F#", "B");

        assertEquals(new LinkedHashSet<>(Arrays.asList("B Major 1st Inversion")), ChordUtil.findChord(notes, true));
    }


    @Test
    public void findChordFACE() throws Exception {
        List<String> notes = Arrays.asList("F", "A", "C", "E");

        assertEquals(new LinkedHashSet<>(Arrays.asList("F Major 7th")), ChordUtil.findChord(notes, false));
    }

    //inversion
    @Test
    public void findChordAECF() throws Exception {
        List<String> notes = Arrays.asList("A", "E", "C", "F");

        assertEquals(new LinkedHashSet<>(Arrays.asList("F Major 7th")), ChordUtil.findChord(notes, false));
    }

    @Test
    public void findChordFSharpACSharpE() throws Exception {
        List<String> notes = Arrays.asList("F#", "A", "C#", "E");

        assertEquals(new LinkedHashSet<>(Arrays.asList("F# Minor 7th", "A Major 6th")),
                ChordUtil.findChord(notes, false));
    }

    @Test
    public void findChordGbBbDbFb() throws Exception {
        List<String> notes = Arrays.asList("Gb", "Bb", "Db", "Fb");

        assertEquals(new LinkedHashSet<>(Arrays.asList("Gb 7th")), ChordUtil.findChord(notes, false));
    }

    @Test
    public void findChordEGBbD() throws Exception {
        List<String> notes = Arrays.asList("E", "G", "Bb", "D");

        assertEquals(new LinkedHashSet<>(Arrays.asList("G Minor 6th", "E Half Diminished")),
                ChordUtil.findChord(notes, false));
    }

    //findChord tests with inversions
    @Test
    public void findChordEGCInversion() throws Exception {
        List<String> notes = Arrays.asList("E", "G", "C");

        assertEquals(new LinkedHashSet<>(Arrays.asList("C Major 1st Inversion")),  ChordUtil.findChord(notes, true));
    }

    @Test
    public void findChordGECInversion() throws Exception {
        List<String> notes = Arrays.asList("G", "E", "C");

        assertEquals(new LinkedHashSet<>(Arrays.asList("C Major 2nd Inversion")), ChordUtil.findChord(notes, true));
    }

    @Test
    public void findChordCEGInversion() throws Exception {
        List<String> notes = Arrays.asList("C", "E", "G");

        assertEquals(new LinkedHashSet<>(Arrays.asList("C Major Root Position")),
                ChordUtil.findChord(notes, true));
    }

    @Test
    public void findChordCEbGInversion() throws Exception {
        List<String> notes = Arrays.asList("C", "Eb", "G");

        assertEquals(new LinkedHashSet<>(Arrays.asList("C Minor Root Position")),
                ChordUtil.findChord(notes, true));
    }

    @Test
    public void findChordEbCGInversion() throws Exception {
        List<String> notes = Arrays.asList("Eb", "C", "G");

        assertEquals(new LinkedHashSet<>(Arrays.asList("C Minor 1st Inversion")),
                ChordUtil.findChord(notes, true));
    }

    @Test
    public void findChordGEbCInversion() throws Exception {
        List<String> notes = Arrays.asList("G", "Eb", "C");

        assertEquals(new LinkedHashSet<>(Arrays.asList("C Minor 2nd Inversion")),
                ChordUtil.findChord(notes, true));
    }

    @Test
    public void findChordGCEbInversion() throws Exception {
        List<String> notes = Arrays.asList("G", "C", "Eb");

        assertEquals(new LinkedHashSet<>(Arrays.asList("C Minor 2nd Inversion")),
                ChordUtil.findChord(notes, true));
    }

    @Test
    public void findChordDSharpFSharpASharpInversion() throws Exception {
        List<String> notes = Arrays.asList("D#", "F#", "A#");

        assertEquals(new LinkedHashSet<>(Arrays.asList("D# Minor Root Position")),
                ChordUtil.findChord(notes, true));
    }

    @Test
    public void findChordFSharpDSharpASharpInversion() throws Exception {
        List<String> notes = Arrays.asList("F#", "D#", "A#");

        assertEquals(new LinkedHashSet<>(Arrays.asList("D# Minor 1st Inversion")),
                ChordUtil.findChord(notes, true));
    }

    @Test
    public void findChordASharpFSharpDSharpInversion() throws Exception {
        List<String> notes = Arrays.asList("A#", "F#", "D#");

        assertEquals(new LinkedHashSet<>(Arrays.asList("D# Minor 2nd Inversion")),
                ChordUtil.findChord(notes, true));
    }

    @Test
    public void findChordCEGBInversion() throws Exception {
        List<String> notes = Arrays.asList("C", "E", "G", "B");

        assertEquals(new LinkedHashSet<>(Arrays.asList("C Major 7th Root Position")),
                ChordUtil.findChord(notes, true));
    }

    @Test
    public void findChordECGBInversion() throws Exception {
        List<String> notes = Arrays.asList("E", "C", "G", "B");

        assertEquals(new LinkedHashSet<>(Arrays.asList("C Major 7th 1st Inversion")),
                ChordUtil.findChord(notes, true));
    }

    @Test
    public void findChordGCEBInversion() throws Exception {
        List<String> notes = Arrays.asList("G", "C", "E", "B");

        assertEquals(new LinkedHashSet<>(Arrays.asList("C Major 7th 2nd Inversion")),
                ChordUtil.findChord(notes, true));
    }

    @Test
    public void findChordBCEGInversion() throws Exception {
        List<String> notes = Arrays.asList("B", "C", "E", "G");

        assertEquals(new LinkedHashSet<>(Arrays.asList("C Major 7th 3rd Inversion")),
                ChordUtil.findChord(notes, true));
    }

    @Test
    public void findChordBECGInversion() throws Exception {
        List<String> notes = Arrays.asList("B", "E", "C", "G");

        assertEquals(new LinkedHashSet<>(Arrays.asList("C Major 7th 3rd Inversion")),
                ChordUtil.findChord(notes, true));
    }

    @Test
    public void findChordFACEInversion() throws Exception {
        List<String> notes = Arrays.asList("F", "A", "C", "E");

        assertEquals(new LinkedHashSet<>(Arrays.asList("F Major 7th Root Position")),
                ChordUtil.findChord(notes, true));
    }
}
