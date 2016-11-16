package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.util.enumerator.Error;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for harmonic (minor) scales for the FindScale command
 *
 * @author ljm163
 */
public class FindScaleHarmonicTest {
    private Environment env = new Environment();
    private List<String> args;
    private final String noScale = "[ERROR] " + Error.FINDSCALENOSCALE.getError();
    private final String notNote = "[ERROR] " + Error.INVALIDNOTE.getError();

    @Before
    public void setup() throws Exception {
        args = new ArrayList<>();
    }

    @Test
    public void CHarmonicMinor() {
        args.clear();
        args.add("C");
        args.add("Harmonic Minor");

        new FindScale(args).execute(env);
        assertEquals("C, D, Eb, F, G, Ab, B, C", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void C4SharpHarmonicMinor() {
        args.clear();
        args.add("C#4");
        args.add("Harmonic Minor");

        new FindScale(args).execute(env);
        assertEquals("C#4, D#4, E4, F#4, G#4, A4, B#4, C#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void DbHarmonicMinor() {
        args.clear();
        args.add("Db");
        args.add("Harmonic Minor");

        new FindScale(args).execute(env);
        assertEquals("Db, Eb, Fb, Gb, Ab, Bbb, C, Db", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void DHarmonicMinor() {
        args.clear();
        args.add("D");
        args.add("Harmonic Minor");

        new FindScale(args).execute(env);
        assertEquals("D, E, F, G, A, Bb, C#, D", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void DSharpHarmonicMinor() {
        args.clear();
        args.add("D#");
        args.add("Harmonic Minor");

        new FindScale(args).execute(env);
        assertEquals("D#, E#, F#, G#, A#, B, Cx, D#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void AHarmonicMinor() {
        args.clear();
        args.add("A");
        args.add("Harmonic Minor");

        new FindScale(args).execute(env);
        assertEquals("A, B, C, D, E, F, G#, A", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void BSharpHarmonicMinor() {
        args.clear();
        args.add("B#");
        args.add("Harmonic Minor");

        new FindScale(args).execute(env);
        assertEquals("B#, Cx, D#, E#, Fx, G#, Ax, B#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CbHarmonicMinor() {
        args.clear();
        args.add("Cb");
        args.add("Harmonic Minor");

        new FindScale(args).execute(env);
        assertEquals("Cb, Db, Ebb, Fb, Gb, Abb, Bb, Cb", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void DDoubleSharpHarmonicMinor() {
        args.clear();
        args.add("Dx");
        args.add("Harmonic Minor");

        new FindScale(args).execute(env);
        assertEquals(noScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void AbbHarmonicMinor() {
        args.clear();
        args.add("Abb");
        args.add("Harmonic Minor");

        new FindScale(args).execute(env);
        assertEquals("Abb, Bbb, Cbb, Dbb, Ebb, Fbb, Gb, Abb", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    // mixolydian b2 b6 mode

    @Test
    public void CMixolydianb2b6() throws Exception {
        args.clear();
        args.add("C");
        args.add("Mixolydian b2 b6");
        new FindScale(args).execute(env);
        assertEquals("C, Db, E, F, G, Ab, Bb, C", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void DSharpMixolydianb2b6() {
        args.clear();
        args.add("D#");
        args.add("Mixolydian b2 b6");

        new FindScale(args).execute(env);
        assertEquals("D#, E, Fx, G#, A#, B, C#, D#", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testInvalidNote() {
        args.clear();
        args.add("not a note");
        args.add("Harmonic Minor");

        new FindScale(args).execute(env);
        assertEquals(notNote, env.getOutput().replaceAll("\\r|\\n", ""));
    }
}

