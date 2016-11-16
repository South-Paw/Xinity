package seng302.command.musical;

import org.junit.Before;
import org.junit.Test;
import seng302.Environment;
import seng302.util.enumerator.Error;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for the Quality command.
 *
 * @author avh17.
 */
public class QualityTest {

    private Environment env = new Environment();
    private List<String> args;

    private String invalidArgs = "[ERROR] " + Error.QUALITYINVALIDARGS.getError();

    @Before
    public void setup() throws Exception {
        args = new ArrayList<>();
    }

    @Test
    public void qualityI() {
        args.clear();
        args.add("I");

        new Quality(args).execute(env);
        assertEquals("Major 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void qualityII() {
        args.clear();
        args.add("II");

        new Quality(args).execute(env);
        assertEquals("Minor 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void qualityIII() {
        args.clear();
        args.add("III");

        new Quality(args).execute(env);
        assertEquals("Minor 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void qualityIV() {
        args.clear();
        args.add("IV");

        new Quality(args).execute(env);
        assertEquals("Major 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void qualityV() {
        args.clear();
        args.add("V");

        new Quality(args).execute(env);
        assertEquals("7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void qualityVI() {
        args.clear();
        args.add("VI");

        new Quality(args).execute(env);
        assertEquals("Minor 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void qualityVII() {
        args.clear();
        args.add("VII");

        new Quality(args).execute(env);
        assertEquals("Half Diminished", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /* ----- Testing numbers instead of roman numerals ----- */

    @Test
    public void quality1() {
        args.clear();
        args.add("1");

        new Quality(args).execute(env);
        assertEquals("Major 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void quality2() {
        args.clear();
        args.add("2");

        new Quality(args).execute(env);
        assertEquals("Minor 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void quality3() {
        args.clear();
        args.add("3");

        new Quality(args).execute(env);
        assertEquals("Minor 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void quality4() {
        args.clear();
        args.add("4");

        new Quality(args).execute(env);
        assertEquals("Major 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void quality5() {
        args.clear();
        args.add("5");

        new Quality(args).execute(env);
        assertEquals("7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void quality6() {
        args.clear();
        args.add("6");

        new Quality(args).execute(env);
        assertEquals("Minor 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void quality7() {
        args.clear();
        args.add("7");

        new Quality(args).execute(env);
        assertEquals("Half Diminished", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /* ----- Testing cases ----- */

    @Test
    public void qualityICase() {
        args.clear();
        args.add("i");

        new Quality(args).execute(env);
        assertEquals("Major 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void qualityIICase() {
        args.clear();
        args.add("ii");

        new Quality(args).execute(env);
        assertEquals("Minor 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void qualityIIICase() {
        args.clear();
        args.add("iii");

        new Quality(args).execute(env);
        assertEquals("Minor 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void qualityIVCase() {
        args.clear();
        args.add("iv");

        new Quality(args).execute(env);
        assertEquals("Major 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void qualityVCase() {
        args.clear();
        args.add("v");

        new Quality(args).execute(env);
        assertEquals("7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void qualityVICase() {
        args.clear();
        args.add("vi");

        new Quality(args).execute(env);
        assertEquals("Minor 7th", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void qualityVIICase() {
        args.clear();
        args.add("vii");

        new Quality(args).execute(env);
        assertEquals("Half Diminished", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void qualityVIICase2() {
        args.clear();
        args.add("vIi");

        new Quality(args).execute(env);
        assertEquals("Half Diminished", env.getOutput().replaceAll("\\r|\\n", ""));
    }

    /* ----- Testing Errors ----- */

    @Test
    public void qualityInvalidArgs() {
        args.clear();
        args.add("Invalid");

        new Quality(args).execute(env);
        assertEquals(invalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }

    @Test
    public void qualityInvalidArgs2() {
        args.clear();
        args.add("I");
        args.add("II");

        new Quality(args).execute(env);
        assertEquals(invalidArgs, env.getOutput().replaceAll("\\r|\\n", ""));
    }
}
