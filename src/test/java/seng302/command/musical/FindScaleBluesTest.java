package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.util.enumerator.Error;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for blues scales for the FindScale command
 *
 * @author avh17
 */
public class FindScaleBluesTest {
    private Environment env = new Environment();
    private List<String> args;
    private final String noScale = "[ERROR] " +  Error.FINDSCALENOSCALE.getError();

    @Before
    public void setup() throws Exception {
        args = new ArrayList<>();
    }

    @Test
    public void EBlues() {
        args.clear();
        args.add("E4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("E4, G4, A4, Bb4, B4, D5, E5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CBlues() {
        args.clear();
        args.add("C4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("C4, Eb4, F4, Gb4, G4, Bb4, C5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CbBlues() {
        args.clear();
        args.add("Cb4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("Cb4, D4, E4, F4, Gb4, A4, Cb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CSharpBlues() {
        args.clear();
        args.add("C#4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("C#4, E4, F#4, G4, G#4, B4, C#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void DBlues() {
        args.clear();
        args.add("D4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("D4, F4, G4, Ab4, A4, C5, D5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void DbBlues() {
        args.clear();
        args.add("Db4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("Db4, E4, Gb4, G4, Ab4, B4, Db5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void DSharpBlues() {
        args.clear();
        args.add("D#4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("D#4, F#4, G#4, A4, A#4, C#5, D#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void EbBlues() {
        args.clear();
        args.add("Eb4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("Eb4, Gb4, Ab4, A4, Bb4, Db5, Eb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void FBlues() {
        args.clear();
        args.add("F4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("F4, Ab4, Bb4, B4, C5, Eb5, F5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void FSharpBlues() {
        args.clear();
        args.add("F#4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("F#4, A4, B4, C5, C#5, E5, F#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GBlues() {
        args.clear();
        args.add("G4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("G4, Bb4, C5, Db5, D5, F5, G5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GbBlues() {
        args.clear();
        args.add("Gb4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("Gb4, A4, B4, C5, Db5, E5, Gb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GSharpBlues() {
        args.clear();
        args.add("G#4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("G#4, B4, C#5, D5, D#5, F#5, G#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void ABlues() {
        args.clear();
        args.add("A4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("A4, C5, D5, Eb5, E5, G5, A5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void AbBlues() {
        args.clear();
        args.add("Ab4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("Ab4, B4, Db5, D5, Eb5, Gb5, Ab5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void ASharpBlues() {
        args.clear();
        args.add("A#4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("A#4, C#5, D#5, E5, F5, G#5, A#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void BSharpBlues() {
        args.clear();
        args.add("B#4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals(noScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void BBlues() {
        args.clear();
        args.add("B4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("B4, D5, E5, F5, F#5, A5, B5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void BbBlues() {
        args.clear();
        args.add("Bb4");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("Bb4, Db5, Eb5, E5, F5, Ab5, Bb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void G9Blues() {
        args.clear();
        args.add("G9");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals("G9 - The rest of the scale cannot be mapped to midi", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCb() {
        args.clear();
        args.add("Cb");
        args.add("blues");

        new FindScale(args).execute(env);
        assertEquals("Cb, D, E, F, Gb, A, Cb", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testESharp() {
        args.clear();
        args.add("E#");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals(noScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testFb() {
        args.clear();
        args.add("Fb");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals(noScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testCx() {
        args.clear();
        args.add("Cx");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals(noScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testBbb() { // same as A
        args.clear();
        args.add("Bbb");
        args.add("Blues");

        new FindScale(args).execute(env);
        assertEquals(noScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }
}