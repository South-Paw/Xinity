package stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.Environment;
import seng302.command.musical.Chord;
import seng302.command.musical.FindChord;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Step definitions class for findChord
 */
public class FindChordSteps {

    private List<String> args;
    private Environment env = new Environment();
    private String noteOne;
    private String noteTwo;
    private String noteThree;
    private String noteFour;


    @Given("^I have the three notes (.*), (.*), (.*)$")
    public void iHaveThreeNotes(String noteOne, String noteTwo, String noteThree)
            throws Throwable {
        this.noteOne = noteOne;
        this.noteTwo = noteTwo;
        this.noteThree = noteThree;
    }

    @Given("^I have the four notes (.*), (.*), (.*), (.*)$")
    public void iHaveFourNotes(String noteOne, String noteTwo, String noteThree, String noteFour)
            throws Throwable {
        this.noteOne = noteOne;
        this.noteTwo = noteTwo;
        this.noteThree = noteThree;
        this.noteFour = noteFour;
    }

    @When("^I call the findChord command with three notes and without the inversion flag$")
    public void iCallTheFindChordCommandThreeNoteWithoutTheInversionFlag() throws Throwable {
        args = Arrays.asList(noteOne, noteTwo, noteThree);
        new FindChord(args).execute(env);
    }

    @When("I call the findChord command with three notes and without the inversion flag "
            + "and with the noEnharmonic flag$")
    public void WithoutTheInversionFlagWithTheNoEnharmonicFlag() throws Throwable {
        args = Arrays.asList(noteOne, noteTwo, noteThree, "noEnharmonic");
        new FindChord(args).execute(env);
    }

    @When("^I call the findChord command with four notes and without the inversion flag$")
    public void iCallTheFindChordCommandFourNoteWithoutTheInversionFlag() throws Throwable {
        args = Arrays.asList(noteOne, noteTwo, noteThree, noteFour);
        new FindChord(args).execute(env);
    }

    @When("^I call the findChord command with three notes and the inversion flag$")
    public void iCallTheFindChordCommandThreeNoteWithTheInversionFlag() throws Throwable {
        args = Arrays.asList(noteOne, noteTwo, noteThree, "inversion");
        new FindChord(args).execute(env);
    }

    @When("^I call the findChord command with three notes and with"
            + " the inversion flag and with the noEnharmonic flag$")
    public void ThreeNotesInversionFlagNoEnharmonicFlag() throws Throwable {
        args = Arrays.asList(noteOne, noteTwo, noteThree, "inversion", "noEnharmonic");
        new FindChord(args).execute(env);
    }

    @When("^I call the findChord command with four notes and with the noEnharmonic flag$")
    public void iCallTheFindChordCommandFourNoteWithTheNoEnharmonicFlag() throws Throwable {
        args = Arrays.asList(noteOne, noteTwo, noteThree, noteFour, "noEnharmonic");
        new FindChord(args).execute(env);
    }

    @When("^I call the findChord command with four notes and the inversion flag$")
    public void iCallTheFindChordCommandFourNoteWithTheInversionFlag() throws Throwable {
        args = Arrays.asList(noteOne, noteTwo, noteThree, noteFour, "inversion");
        new FindChord(args).execute(env);
    }

    @Then("^The correct name for the chord (.*) is displayed$")
    public void theCorrectChordNameIsDisplayed(String resultingChordName) throws Throwable {
        assertEquals(resultingChordName, env.getOutput().replaceAll("\\r|\\n", ""));
    }
}