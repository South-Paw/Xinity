package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.util.enumerator.Error;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author avh17
 */
public class FindScalePentatonicsTest {
    private Environment env = new Environment();
    private List<String> args;
    final private String errorNoScale = "[ERROR] "
            + Error.FINDSCALENOSCALE.getError();

    @Before
    public void setup() throws Exception {
        args = new ArrayList<>();
    }

    /*---Pentatonic Major---*/

    @Test
    public void CMajorPenta() throws Exception {
        args.clear();
        args.add("C4");
        args.add("Pentatonic Major");
        new FindScale(args).execute(env);
        assertEquals("C4, D4, E4, G4, A4, C5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void C5MajorPenta() throws Exception {
        args.clear();
        args.add("C5");
        args.add("Pentatonic Major");
        new FindScale(args).execute(env);
        assertEquals("C5, D5, E5, G5, A5, C6", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GMajorPenta() throws Exception {
        args.clear();
        args.add("G4");
        args.add("Pentatonic Major");
        new FindScale(args).execute(env);
        assertEquals("G4, A4, B4, D5, E5, G5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void DMajorPenta() throws Exception {
        args.clear();
        args.add("D4");
        args.add("Pentatonic Major");
        new FindScale(args).execute(env);
        assertEquals("D4, E4, F#4, A4, B4, D5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void AMajorPenta() throws Exception {
        args.clear();
        args.add("A4");
        args.add("Pentatonic Major");
        new FindScale(args).execute(env);
        assertEquals("A4, B4, C#5, E5, F#5, A5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void EMajorPenta() throws Exception {
        args.clear();
        args.add("E4");
        args.add("Pentatonic Major");
        new FindScale(args).execute(env);
        assertEquals("E4, F#4, G#4, B4, C#5, E5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void BMajorPenta() throws Exception {
        args.clear();
        args.add("B4");
        args.add("Pentatonic Major");
        new FindScale(args).execute(env);
        assertEquals("B4, C#5, D#5, F#5, G#5, B5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void FSharpMajorPenta() throws Exception {
        args.clear();
        args.add("F#4");
        args.add("Pentatonic Major");
        new FindScale(args).execute(env);
        assertEquals("F#4, G#4, A#4, C#5, D#5, F#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CSharpMajorPenta() throws Exception {
        args.clear();
        args.add("C#4");
        args.add("Pentatonic Major");
        new FindScale(args).execute(env);
        assertEquals("C#4, D#4, F4, G#4, A#4, C#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void FMajorPenta() throws Exception {
        args.clear();
        args.add("F4");
        args.add("Pentatonic Major");
        new FindScale(args).execute(env);
        assertEquals("F4, G4, A4, C5, D5, F5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void BbMajorPenta() throws Exception {
        args.clear();
        args.add("Bb4");
        args.add("Pentatonic Major");
        new FindScale(args).execute(env);
        assertEquals("Bb4, C5, D5, F5, G5, Bb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void EbMajorPenta() throws Exception {
        args.clear();
        args.add("Eb4");
        args.add("Pentatonic Major");
        new FindScale(args).execute(env);
        assertEquals("Eb4, F4, G4, Bb4, C5, Eb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void AbMajorPenta() throws Exception {
        args.clear();
        args.add("Ab4");
        args.add("Pentatonic Major");
        new FindScale(args).execute(env);
        assertEquals("Ab4, Bb4, C5, Eb5, F5, Ab5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void DbMajorPenta() throws Exception {
        args.clear();
        args.add("Db4");
        args.add("Pentatonic Major");
        new FindScale(args).execute(env);
        assertEquals("Db4, Eb4, F4, Ab4, Bb4, Db5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void BSharpMajorPenta() throws Exception {
        args.clear();
        args.add("B#");
        args.add("pentatonic major");
        new FindScale(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CbMajorPenta() throws Exception {
        args.clear();
        args.add("Cb4");
        args.add("pentatonic major");
        new FindScale(args).execute(env);
        assertEquals("Cb4, Db4, Eb4, Gb4, Ab4, Cb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void ESharpMajorPenta() throws Exception {
        args.clear();
        args.add("E#");
        args.add("pentatonic major");
        new FindScale(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void Fb5MajorPenta() throws Exception {
        args.clear();
        args.add("Fb5");
        args.add("pentatonic major");
        new FindScale(args).execute(env);
        assertEquals(errorNoScale, env.getOutput().replaceAll("\\r|\\n", ""));
    }


    @Test
    public void GbMajorPenta() throws Exception {
        args.clear();
        args.add("Gb4");
        args.add("Pentatonic Major");
        new FindScale(args).execute(env);
        assertEquals("Gb4, Ab4, Bb4, Db5, Eb5, Gb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void G9MajorPenta() throws Exception {
        args.clear();
        args.add("G9");
        args.add("Pentatonic Major");
        new FindScale(args).execute(env);
        assertEquals("G9 - The rest of the scale cannot be mapped to midi", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /*---Pentatonic Minor---*/

    @Test
    public void AMinorPenta() throws Exception {
        args.clear();
        args.add("A4");
        args.add("Pentatonic Minor");
        new FindScale(args).execute(env);
        assertEquals("A4, C5, D5, E5, G5, A5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void BMinorPenta() throws Exception {
        args.clear();
        args.add("B4");
        args.add("Pentatonic Minor");
        new FindScale(args).execute(env);
        assertEquals("B4, D5, E5, F#5, A5, B5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void FSharpMinorPenta() throws Exception {
        args.clear();
        args.add("F#4");
        args.add("Pentatonic Minor");
        new FindScale(args).execute(env);
        assertEquals("F#4, A4, B4, C#5, E5, F#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CSharpMinorPenta() throws Exception {
        args.clear();
        args.add("C#4");
        args.add("Pentatonic Minor");
        new FindScale(args).execute(env);
        assertEquals("C#4, E4, F#4, G#4, B4, C#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GSharpMinorPenta() throws Exception {
        args.clear();
        args.add("G#4");
        args.add("Pentatonic Minor");
        new FindScale(args).execute(env);
        assertEquals("G#4, B4, C#5, D#5, F#5, G#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void DSharpMinorPenta() throws Exception {
        args.clear();
        args.add("D#4");
        args.add("Pentatonic Minor");
        new FindScale(args).execute(env);
        assertEquals("D#4, F#4, G#4, A#4, C#5, D#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void ASharpMinorPenta() throws Exception {
        args.clear();
        args.add("A#4");
        args.add("Pentatonic Minor");
        new FindScale(args).execute(env);
        assertEquals("A#4, C#5, D#5, F5, G#5, A#5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void DMinorPenta() throws Exception {
        args.clear();
        args.add("D4");
        args.add("Pentatonic Minor");
        new FindScale(args).execute(env);
        assertEquals("D4, F4, G4, A4, C5, D5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GMinorPenta() throws Exception {
        args.clear();
        args.add("G4");
        args.add("Pentatonic Minor");
        new FindScale(args).execute(env);
        assertEquals("G4, Bb4, C5, D5, F5, G5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CMinorPenta() throws Exception {
        args.clear();
        args.add("C4");
        args.add("Pentatonic Minor");
        new FindScale(args).execute(env);
        assertEquals("C4, Eb4, F4, G4, Bb4, C5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void FMinorPenta() throws Exception {
        args.clear();
        args.add("F4");
        args.add("Pentatonic Minor");
        new FindScale(args).execute(env);
        assertEquals("F4, Ab4, Bb4, C5, Eb5, F5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void BbMinorPenta() throws Exception {
        args.clear();
        args.add("Bb4");
        args.add("Pentatonic Minor");
        new FindScale(args).execute(env);
        assertEquals("Bb4, Db5, Eb5, F5, Ab5, Bb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void EbMinorPenta() throws Exception {
        args.clear();
        args.add("Eb4");
        args.add("Pentatonic Minor");
        new FindScale(args).execute(env);
        assertEquals("Eb4, Gb4, Ab4, Bb4, Db5, Eb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void AbMinorPenta() throws Exception {
        args.clear();
        args.add("Ab4");
        args.add("Pentatonic Minor");
        new FindScale(args).execute(env);
        assertEquals("Ab4, B4, Db5, Eb5, Gb5, Ab5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GbMinorPenta() throws Exception {
        args.clear();
        args.add("Gb4");
        args.add("Pentatonic Minor");
        new FindScale(args).execute(env);
        assertEquals("Gb4, A4, B4, Db5, E5, Gb5", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void testE9PentaMinor() throws Exception {
        args.clear();
        args.add("E9");
        args.add("Pentatonic Minor");
        new FindScale(args).execute(env);
        assertEquals("E9, G9 - The rest of the scale cannot be mapped to midi", env.getOutput().replaceAll("\\r|\\n", ""));
    }
}