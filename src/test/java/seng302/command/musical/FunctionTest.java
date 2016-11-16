package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.util.enumerator.Error;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for the Function command.
 *
 * @author avh17.
 */
public class FunctionTest {

    private Environment env = new Environment();
    private List<String> args;

    private String invalidArgs = "[ERROR] " + Error.FUNCTIONINVALIDARGS.getError();
    private String nonFunctional = "Non Functional.";

    @Before
    public void setup() throws Exception {
        args = new ArrayList<>();
    }

    @Test
    public void CMajorI() {
        args.clear();
        args.add("C");
        args.add("Major Seventh");
        args.add("C");

        new Function(args).execute(env);
        assertEquals("I", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CMajorII() {
        args.clear();
        args.add("D");
        args.add("Minor Seventh");
        args.add("C");

        new Function(args).execute(env);
        assertEquals("II", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CMajorIII() {
        args.clear();
        args.add("E");
        args.add("Minor Seventh");
        args.add("C");

        new Function(args).execute(env);
        assertEquals("III", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CMajorIV() {
        args.clear();
        args.add("F");
        args.add("Major Seventh");
        args.add("C");

        new Function(args).execute(env);
        assertEquals("IV", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CMajorV() {
        args.clear();
        args.add("G");
        args.add("Seventh");
        args.add("C");

        new Function(args).execute(env);
        assertEquals("V", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CMajorVI() {
        args.clear();
        args.add("A");
        args.add("Minor Seventh");
        args.add("C");

        new Function(args).execute(env);
        assertEquals("VI", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CMajorVII() {
        args.clear();
        args.add("B");
        args.add("Half Diminished");
        args.add("C");

        new Function(args).execute(env);
        assertEquals("VII", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /* ----- Test Gb Major ----- */

    @Test
    public void GbMajorI() {
        args.clear();
        args.add("Gb");
        args.add("Major Seventh");
        args.add("Gb");

        new Function(args).execute(env);
        assertEquals("I", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GbMajorII() {
        args.clear();
        args.add("Ab");
        args.add("Minor Seventh");
        args.add("Gb");

        new Function(args).execute(env);
        assertEquals("II", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GbMajorIII() {
        args.clear();
        args.add("Bb");
        args.add("Minor Seventh");
        args.add("Gb");

        new Function(args).execute(env);
        assertEquals("III", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GbMajorIV() {
        args.clear();
        args.add("Cb");
        args.add("Major Seventh");
        args.add("Gb");

        new Function(args).execute(env);
        assertEquals("IV", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GbMajorV() {
        args.clear();
        args.add("Db");
        args.add("Seventh");
        args.add("Gb");

        new Function(args).execute(env);
        assertEquals("V", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GbMajorVI() {
        args.clear();
        args.add("Eb");
        args.add("Minor Seventh");
        args.add("Gb");

        new Function(args).execute(env);
        assertEquals("VI", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GbMajorVII() {
        args.clear();
        args.add("F");
        args.add("Half Diminished");
        args.add("Gb");

        new Function(args).execute(env);
        assertEquals("VII", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /* ----- Test A# Major ----- */

    @Test
    public void ASharpMajorI() {
        args.clear();
        args.add("A#");
        args.add("Major Seventh");
        args.add("A#");

        new Function(args).execute(env);
        assertEquals("I", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void ASharpMajorII() {
        args.clear();
        args.add("B#");
        args.add("Minor Seventh");
        args.add("A#");

        new Function(args).execute(env);
        assertEquals("II", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void ASharpMajorIII() {
        args.clear();
        args.add("Cx");
        args.add("Minor Seventh");
        args.add("A#");

        new Function(args).execute(env);
        assertEquals("III", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void ASharpMajorIV() {
        args.clear();
        args.add("D#");
        args.add("Major Seventh");
        args.add("A#");

        new Function(args).execute(env);
        assertEquals("IV", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void ASharpMajorV() {
        args.clear();
        args.add("E#");
        args.add("Seventh");
        args.add("A#");

        new Function(args).execute(env);
        assertEquals("V", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void ASharpMajorVI() {
        args.clear();
        args.add("Fx");
        args.add("Minor Seventh");
        args.add("A#");

        new Function(args).execute(env);
        assertEquals("VI", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void ASharpMajorVII() {
        args.clear();
        args.add("Gx");
        args.add("Half Diminished");
        args.add("A#");

        new Function(args).execute(env);
        assertEquals("VII", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /* ----- Test Non Functional ----- */

    @Test
    public void ASharpMajorINon() {
        args.clear();
        args.add("A#");
        args.add("Seventh");
        args.add("A#");

        new Function(args).execute(env);
        assertEquals(nonFunctional, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void GbMajorVNon() {
        args.clear();
        args.add("Db");
        args.add("Major Seventh");
        args.add("Gb");

        new Function(args).execute(env);
        assertEquals(nonFunctional, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void CMajorVIINon() {
        args.clear();
        args.add("B");
        args.add("Seventh");
        args.add("C");

        new Function(args).execute(env);
        assertEquals(nonFunctional, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /* ----- Test Invalid Args ----- */

    @Test
    public void invalidArgs1() {
        args.clear();
        args.add("A#");
        args.add("Invalid");
        args.add("A#");

        new Function(args).execute(env);
        assertEquals(invalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void invalidArgs2() {
        args.clear();
        args.add("Invalid");
        args.add("Seventh");
        args.add("A#");

        new Function(args).execute(env);
        assertEquals(invalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void invalidArgs3() {
        args.clear();
        args.add("A#");
        args.add("Seventh");
        args.add("Invalid");

        new Function(args).execute(env);
        assertEquals(invalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void invalidArgs4() {
        args.clear();
        args.add("A#");
        args.add("Seventh");
        args.add("A#");
        args.add("C");

        new Function(args).execute(env);
        assertEquals(invalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }
}