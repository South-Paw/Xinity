package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.util.enumerator.Error;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for the Chord Function Command.
 *
 * @author avh17.
 */
public class ChordFunctionTest {

    private Environment env = new Environment();
    private List<String> args;

    private String invalidArgs = "[ERROR] " + Error.CHORDFUNCTIONINVALIDARGS.getError();

    @Before
    public void setup() throws Exception {
        args = new ArrayList<>();
    }

    @Test
    public void CMajorI() {
        args.clear();
        args.add("I");
        args.add("C");

        new ChordFunction(args).execute(env);
        assertEquals("C Major 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CMajorII() {
        args.clear();
        args.add("II");
        args.add("C");

        new ChordFunction(args).execute(env);
        assertEquals("D Minor 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CMajorIII() {
        args.clear();
        args.add("III");
        args.add("C");

        new ChordFunction(args).execute(env);
        assertEquals("E Minor 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CMajorIV() {
        args.clear();
        args.add("IV");
        args.add("C");

        new ChordFunction(args).execute(env);
        assertEquals("F Major 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CMajorV() {
        args.clear();
        args.add("V");
        args.add("C");

        new ChordFunction(args).execute(env);
        assertEquals("G 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CMajorVI() {
        args.clear();
        args.add("VI");
        args.add("C");

        new ChordFunction(args).execute(env);
        assertEquals("A Minor 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CMajorVII() {
        args.clear();
        args.add("VII");
        args.add("C");

        new ChordFunction(args).execute(env);
        assertEquals("B Half Diminished", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /* ----- Test G# major ----- */

    @Test
    public void GSharpMajorI() {
        args.clear();
        args.add("I");
        args.add("G#");

        new ChordFunction(args).execute(env);
        assertEquals("G# Major 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GSharpMajorII() {
        args.clear();
        args.add("II");
        args.add("G#");

        new ChordFunction(args).execute(env);
        assertEquals("A# Minor 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GSharpMajorIII() {
        args.clear();
        args.add("III");
        args.add("G#");

        new ChordFunction(args).execute(env);
        assertEquals("B# Minor 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GSharpMajorIV() {
        args.clear();
        args.add("IV");
        args.add("G#");

        new ChordFunction(args).execute(env);
        assertEquals("C# Major 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GSharpMajorV() {
        args.clear();
        args.add("V");
        args.add("G#");

        new ChordFunction(args).execute(env);
        assertEquals("D# 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GSharpMajorVI() {
        args.clear();
        args.add("VI");
        args.add("G#");

        new ChordFunction(args).execute(env);
        assertEquals("E# Minor 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GSharpMajorVII() {
        args.clear();
        args.add("VII");
        args.add("G#");

        new ChordFunction(args).execute(env);
        assertEquals("Fx Half Diminished", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /* ----- Test numbers ----- */

    @Test
    public void DMajorVII() {
        args.clear();
        args.add("7");
        args.add("D");

        new ChordFunction(args).execute(env);
        assertEquals("C# Half Diminished", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void FbMajorI() {
        args.clear();
        args.add("3");
        args.add("Fb");

        new ChordFunction(args).execute(env);
        assertEquals("Ab Minor 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /* ----- Test Invalid Args ----- */

    @Test
    public void InvalidArgs1() {
        args.clear();
        args.add("I");
        args.add("Invalid");

        new ChordFunction(args).execute(env);
        assertEquals(invalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void InvalidArgs2() {
        args.clear();
        args.add("Invalid");
        args.add("C");

        new ChordFunction(args).execute(env);
        assertEquals(invalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void InvalidArgs3() {
        args.clear();
        args.add("I");
        args.add("C");
        args.add("D");

        new ChordFunction(args).execute(env);
        assertEquals(invalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }
}