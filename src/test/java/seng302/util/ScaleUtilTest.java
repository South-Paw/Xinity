package seng302.util;

import org.junit.Test;
import seng302.util.enumerator.ScaleDirection;
import seng302.util.enumerator.ScaleMode;
import seng302.util.enumerator.ScaleType;
import seng302.util.object.XinityNote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests for ScaleUtil class. Note: All public methods in ScalesUtil class take valid parameters.
 * This removes the need to check for invalid input of these methods, as these are tested in the command tests,
 * eg. PlayScale, FindScale.
 *
 * @author ljm163, plr37
 */

public class ScaleUtilTest {
    //scaleDirection from string

    private List<XinityNote> findScaleHelper(String notes) throws Exception {
        List<XinityNote> xinityNotes = new ArrayList<>();
        notes = notes.replaceAll("\\s+", "");
        for (String s : notes.split(",")) {
            xinityNotes.add(new XinityNote(s));
        }
        return xinityNotes;
    }

    private List<Integer> findScaleMidiHelper(String numbers) throws Exception {
        List<Integer> integers = new ArrayList<>();
        numbers = numbers.replaceAll("\\s+", "");
        for (String s : numbers.split(",")) {
            integers.add(Integer.parseInt(s));
        }
        return integers;
    }

    @Test
    public void directionFromStringLowerUp() throws Exception {
        String direction = "up";

        ScaleDirection scaleDirection = ScaleDirection.fromString(direction);

        assertEquals(ScaleDirection.UP, scaleDirection);
    }

    @Test
    public void directionFromStringUpperUp() throws Exception {
        String direction = "UP";

        ScaleDirection scaleDirection = ScaleDirection.fromString(direction);

        assertEquals(ScaleDirection.UP, scaleDirection);
    }

    @Test
    public void directionFromStringLowerDown() throws Exception {
        String direction = "down";

        ScaleDirection scaleDirection = ScaleDirection.fromString(direction);

        assertEquals(ScaleDirection.DOWN, scaleDirection);
    }

    @Test
    public void directionFromStringUpperDown() throws Exception {
        String direction = "DOWN";

        ScaleDirection scaleDirection = ScaleDirection.fromString(direction);

        assertEquals(ScaleDirection.DOWN, scaleDirection);
    }

    @Test
    public void directionFromStringLowerBoth() throws Exception {
        String direction = "both";

        ScaleDirection scaleDirection = ScaleDirection.fromString(direction);

        assertEquals(ScaleDirection.BOTH, scaleDirection);
    }

    @Test
    public void directionFromStringUpperBoth() throws Exception {
        String direction = "both";

        ScaleDirection scaleDirection = ScaleDirection.fromString(direction);

        assertEquals(ScaleDirection.BOTH, scaleDirection);
    }

    @Test
    public void directionFromStringMixedCase() throws Exception {
        String direction = "bOtH";

        ScaleDirection scaleDirection = ScaleDirection.fromString(direction);

        assertEquals(ScaleDirection.BOTH, scaleDirection);
    }

    @Test
    public void directionFromStringInvalidEmpty() throws Exception {
        String direction = "";

        ScaleDirection scaleDirection = ScaleDirection.fromString(direction);

        assertEquals(null, scaleDirection);
    }

    @Test
    public void directionFromStringInvalidNull() throws Exception {
        assertEquals(null, ScaleDirection.fromString(null));
    }

    @Test
    public void directionFromStringInvalidLeft() throws Exception {
        String direction = "left";

        ScaleDirection scaleDirection = ScaleDirection.fromString(direction);

        assertEquals(null, scaleDirection);
    }

    //scaleType from string
    @Test
    public void typeFromStringLowerMajor() throws Exception {
        String type = "major";

        ScaleType scaleType = ScaleType.fromString(type);

        assertEquals(ScaleType.MAJOR, scaleType);
    }

    @Test
    public void typeFromStringUpperMajor() throws Exception {
        String type = "MAJOR";

        ScaleType scaleType = ScaleType.fromString(type);

        assertEquals(ScaleType.MAJOR, scaleType);
    }

    @Test
    public void typeFromStringLowerMinor() throws Exception {
        String type = "minor";

        ScaleType scaleType = ScaleType.fromString(type);

        assertEquals(ScaleType.MINOR, scaleType);
    }

    @Test
    public void typeFromStringLowerNaturalMinor() throws Exception {
        String type = "natural minor";

        ScaleType scaleType = ScaleType.fromString(type);

        assertEquals(ScaleType.MINOR, scaleType);
    }

    @Test
    public void typeFromStringUpperNaturalMinor() throws Exception {
        String type = "NATURAL MINOR";

        ScaleType scaleType = ScaleType.fromString(type);

        assertEquals(ScaleType.MINOR, scaleType);
    }

    @Test
    public void typeFromStringMixedCase() throws Exception {
        String type = "natural MINOR";

        ScaleType scaleType = ScaleType.fromString(type);

        assertEquals(ScaleType.MINOR, scaleType);
    }

    @Test
    public void typeFromStringInvalidEmpty() throws Exception {
        String type = "";

        ScaleType scaleType = ScaleType.fromString(type);

        assertEquals(null, scaleType);
    }

    @Test
    public void typeFromStringInvalidNull() throws Exception {
        assertEquals(null, ScaleType.fromString(null));
    }

    @Test
    public void typeFromStringInvalidNatural() throws Exception {
        String type = "natural";

        ScaleType scaleType = ScaleType.fromString(type);

        assertEquals(null, scaleType);
    }

    /*findNaturalScale - Note: findNaturalScale() will never get an input note
    outside of the midi range, or an invalid scale type, so do no need to test.
    The FindScale command will deal with invalid input. */
    @Test
    public void findScaleC4Major() throws Exception {
        XinityNote note = new XinityNote("C4");

        ScaleType scaleType = ScaleType.fromString("major");

        List<XinityNote> expected = findScaleHelper("C4, D4, E4, F4, G4, A4, B4, C5");
        List<XinityNote> result = ScalesUtil.findScale(note, scaleType);

        assertEquals(expected, result);
    }

    @Test
    public void findScaleA4Major() throws Exception {
        XinityNote note = new XinityNote("A");

        ScaleType scaleType = ScaleType.fromString("major");

        List<XinityNote> scaleString = ScalesUtil.findScale(note, scaleType);

        assertEquals(findScaleHelper("A4, B4, C#5, D5, E5, F#5, G#5, A5"), scaleString);
    }

    @Test
    public void findScaleAMelodicMinor() throws Exception {
        XinityNote note = new XinityNote("A");

        ScaleType scaleType = ScaleType.fromString("Melodic Minor");

        List<XinityNote> expected = findScaleHelper("A, B, C5, D5, E5, F#5, G#5, A5");
        List<XinityNote> result = ScalesUtil.findScale(note, scaleType);
        assertEquals(expected, result);
    }

    @Test
    public void findScaleCMelodicMinor() throws Exception {
        XinityNote note = new XinityNote("C");

        ScaleType scaleType = ScaleType.fromString("Melodic Minor");

        List<XinityNote> result = ScalesUtil.findScale(note, scaleType);
        List<XinityNote> expected = findScaleHelper("C, D, Eb, F, G, A, B, C5");
        assertEquals(expected, result);
    }

    @Test
    public void findScaleBSharpMelodicMinor() throws Exception {
        XinityNote note = new XinityNote("B#");

        ScaleType scaleType = ScaleType.fromString("Melodic Minor");

        List<XinityNote> expected = findScaleHelper("B#, Cx5, D#5, E#5, Fx5, Gx5, Ax5, B#5");
        List<XinityNote> result = ScalesUtil.findScale(note, scaleType);
        assertEquals(expected, result);
    }

    @Test
    public void findScaleASharpMelodicMinor() throws Exception {
        XinityNote note = new XinityNote("A#");

        ScaleType scaleType = ScaleType.fromString("Melodic Minor");

        List<XinityNote> expected = findScaleHelper("A#, B#, C#5, D#5, E#5, Fx5, Gx5, A#5");
        List<XinityNote> result = ScalesUtil.findScale(note, scaleType);
        assertEquals(expected, result);
    }

    @Test
    public void findScaleBFlatMelodicMinor() throws Exception {
        XinityNote note = new XinityNote("Bb");

        ScaleType scaleType = ScaleType.fromString("Melodic Minor");

        List<XinityNote> expected = findScaleHelper("Bb, C5, Db5, Eb5, F5, G5, A5, Bb5");
        List<XinityNote> result = ScalesUtil.findScale(note, scaleType);
        assertEquals(expected, result);
    }

    @Test
    public void findScaleBMelodicMinor() throws Exception {
        XinityNote note = new XinityNote("B");

        ScaleType scaleType = ScaleType.fromString("Melodic Minor");

        List<XinityNote> result = findScaleHelper("B, C#5, D5, E5, F#5, G#5, A#5, B5");
        List<XinityNote> expected = ScalesUtil.findScale(note, scaleType);
        assertEquals(expected, result);
    }

    @Test
    public void findScaleDMelodicMinor() throws Exception {
        XinityNote note = new XinityNote("D");

        ScaleType scaleType = ScaleType.fromString("Melodic Minor");
        List<XinityNote> expected = findScaleHelper("D, E, F, G, A, B, C#5, D5");
        List<XinityNote> result = ScalesUtil.findScale(note, scaleType);
        assertEquals(expected, result);
    }

    @Test
    public void findScaleGMelodicMinor() throws Exception {
        XinityNote note = new XinityNote("G");

        ScaleType scaleType = ScaleType.fromString("Melodic Minor");

        List<XinityNote> expected = findScaleHelper("G, A, Bb, C5, D5, E5, F#5, G5");
        List<XinityNote> result = ScalesUtil.findScale(note, scaleType);
        assertEquals(expected, result);
    }


    @Test
    public void findScaleC4Minor() throws Exception {
        XinityNote note = new XinityNote("C4");
        ScaleType scaleType = ScaleType.fromString("minor");
        List<XinityNote> expected = findScaleHelper("C4, D4, Eb4, F4, G4, Ab4, Bb4, C5");
        List<XinityNote> result = ScalesUtil.findScale(note, scaleType);
        assertEquals(expected, result);
    }

    @Test
    public void findScaleC4NaturalMinor() throws Exception {
        XinityNote note = new XinityNote("C4");
        ScaleType scaleType = ScaleType.fromString("natural minor");
        List<XinityNote> expected = findScaleHelper("C4, D4, Eb4, F4, G4, Ab4, Bb4, C5");
        List<XinityNote> result = ScalesUtil.findScale(note, scaleType);
        assertEquals(expected, result);
    }

    @Test
    public void findScaleC4PentaMajor() throws Exception {
        XinityNote note = new XinityNote("C4");

        ScaleType scaleType = ScaleType.fromString("pentatonic major");

        List<XinityNote> expected = findScaleHelper("C4, D4, E4, G4, A4, C5");
        List<XinityNote> result = ScalesUtil.findScale(note, scaleType);

        assertEquals(expected, result);
    }

    @Test
    public void findScaleC4PentaMinor() throws Exception {
        XinityNote note = new XinityNote("C4");

        ScaleType scaleType = ScaleType.fromString("pentatonic minor");

        List<XinityNote> expected = findScaleHelper("C4, Eb4, F4, G4, Bb4, C5");
        List<XinityNote> result = ScalesUtil.findScale(note, scaleType);

        assertEquals(expected, result);
    }

    /*buildPlayingScale - Note: buildPlayingScale() will never get an input note
    outside of the midi range, invalid scale type, or invalid scale direction so do no need to test.
    The PlayScale command will deal with invalid input. */
    @Test
    public void buildPlayingScaleC4MajorUp1() throws Exception {
        XinityNote note = new XinityNote("C4");
        ScaleType scaleType = ScaleType.fromString("major");
        ScaleDirection scaleDirection = ScaleDirection.fromString("up");
        Integer octaves = 1;

        List<XinityNote> expected = findScaleHelper("C4, D4, E4, F4, G4, A4, B4, C5");
        List<XinityNote> result = ScalesUtil.buildPlayingScale(note, scaleType, scaleDirection, octaves);

        assertEquals(expected, result);
    }

    @Test
    public void buildPlayingScaleC4MinorUp1() throws Exception {
        XinityNote note = new XinityNote("C4");
        ScaleType scaleType = ScaleType.fromString("minor");
        ScaleDirection scaleDirection = ScaleDirection.fromString("up");
        Integer octaves = 1;

        List<XinityNote> expected = findScaleHelper("C4, D4, Eb4, F4, G4, Ab4, Bb4, C5");
        List<XinityNote> result = ScalesUtil.buildPlayingScale(note, scaleType, scaleDirection, octaves);

        assertEquals(expected, result);
    }

    @Test
    public void buildPlayingScaleC4NaturalMinorUp1() throws Exception {
        XinityNote note = new XinityNote("C4");
        ScaleType scaleType = ScaleType.fromString("natural minor");
        ScaleDirection scaleDirection = ScaleDirection.fromString("up");
        Integer octaves = 1;

        List<XinityNote> expected = findScaleHelper("C4, D4, Eb4, F4, G4, Ab4, Bb4, C5");
        List<XinityNote> result = ScalesUtil.buildPlayingScale(note, scaleType, scaleDirection, octaves);

        assertEquals(expected, result);
    }

    @Test
    public void buildPlayingScaleC4MinorUp2() throws Exception {
        XinityNote note = new XinityNote("C4");
        ScaleType scaleType = ScaleType.fromString("minor");
        ScaleDirection scaleDirection = ScaleDirection.fromString("up");
        Integer octaves = 2;

        List<XinityNote> expected = findScaleHelper("C4, D4, Eb4, F4, G4, Ab4, Bb4, C5, D5, Eb5, F5, G5, Ab5, Bb5, C6");
        List<XinityNote> result = ScalesUtil.buildPlayingScale(note, scaleType, scaleDirection, octaves);

        assertEquals(expected, result);
    }

    @Test
    public void buildPlayingScaleC9MajorUp1() throws Exception {
        XinityNote note = new XinityNote("C9");
        ScaleType scaleType = ScaleType.fromString("major");
        ScaleDirection scaleDirection = ScaleDirection.fromString("up");
        Integer octaves = 1;

        List<XinityNote> expected = findScaleHelper("C9, D9, E9, F9, G9, A9, B9, C10");
        List<XinityNote> result = ScalesUtil.buildPlayingScale(note, scaleType, scaleDirection, octaves);

        assertEquals(expected, result);
    }

    @Test
    public void buildPlayingScaleC4PentaMajorUp1() throws Exception {
        XinityNote note = new XinityNote("C4");
        ScaleType scaleType = ScaleType.fromString("pentatonic major");
        ScaleDirection scaleDirection = ScaleDirection.fromString("up");
        Integer octaves = 1;

        List<XinityNote> expected = findScaleHelper("C4, D4, E4, G4, A4, C5");
        List<XinityNote> result = ScalesUtil.buildPlayingScale(note, scaleType, scaleDirection, octaves);

        assertEquals(expected, result);
    }


    @Test
    public void buildPlayingScaleC4PentaMajorUpdown1() throws Exception {
        XinityNote note = new XinityNote("C4");
        ScaleType scaleType = ScaleType.fromString("pentatonic major");
        ScaleDirection scaleDirection = ScaleDirection.fromString("both");
        Integer octaves = 1;

        List<XinityNote> expected = findScaleHelper("C4, D4, E4, G4, A4, C5, A4, G4, E4, D4, C4");
        List<XinityNote> result = ScalesUtil.buildPlayingScale(note, scaleType, scaleDirection, octaves);

        assertEquals(expected, result);
    }

    @Test
    public void buildPlayingScaleDSharpPentaMajorUp() throws Exception {
        XinityNote note = new XinityNote("D#4");
        ScaleType scaleType = ScaleType.fromString("pentatonic major");
        ScaleDirection scaleDirection = ScaleDirection.fromString("up");
        Integer octaves = 1;

        List<XinityNote> expected = findScaleHelper("D#4, F4, G4, A#4, C5, D#5");
        List<XinityNote> result = ScalesUtil.buildPlayingScale(note, scaleType, scaleDirection, octaves);

        assertEquals(expected, result);
    }

    @Test
    public void buildPlayingScaleCBluesScaleCUp() throws Exception {
        XinityNote note = new XinityNote("C");
        ScaleType scaleType = ScaleType.fromString("blues");
        ScaleDirection scaleDirection = ScaleDirection.fromString("up");
        Integer octaves = 1;

        List<XinityNote> expected = findScaleHelper("C4, Eb4, F4, Gb4, G4, Bb4, C5");
        List<XinityNote> result = ScalesUtil.buildPlayingScale(note, scaleType, scaleDirection, octaves);

        assertEquals(expected, result);
    }

    @Test
    public void buildPlayingScaleCBluesScaleDUp() throws Exception {
        XinityNote note = new XinityNote("D4");
        ScaleType scaleType = ScaleType.fromString("blues");
        ScaleDirection scaleDirection = ScaleDirection.fromString("up");
        Integer octaves = 1;

        List<XinityNote> result = ScalesUtil.buildPlayingScale(note, scaleType, scaleDirection, octaves);
        List<XinityNote> expected = findScaleHelper("D4, F4, G4, Ab4, A4, C5, D5");

        assertEquals(expected, result);
    }

    // getParentScale tests
    @Test
    public void testGetParentScaleTestsG4Mixolydian() throws Exception {
        ScaleMode mode = ScaleMode.fromString("Mixolydian");
        XinityNote note = new XinityNote("A4");
        XinityNote expected = new XinityNote("D4");

        XinityNote result = ScalesUtil.getParentNote(note, mode);
        assertEquals(expected, result);
    }

    @Test
    public void testGetParentScaleTestsCSharpLocrianian() throws Exception {
        ScaleMode mode = ScaleMode.fromString("Locrian");
        XinityNote note = new XinityNote("C#5");
        XinityNote expected = new XinityNote("D4");

        XinityNote result = ScalesUtil.getParentNote(note, mode);
        assertEquals(expected, result);
    }
}