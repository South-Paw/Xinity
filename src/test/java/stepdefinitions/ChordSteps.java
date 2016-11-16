package stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.Environment;
import seng302.command.musical.Chord;
import seng302.command.musical.Midi;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Cucumber step definitions for the Chord class.
 */
public class ChordSteps {

    private Chord chord;
    private Environment env = new Environment();
    private String quality;
    private String note;

    @Given("^I have a quality (.*) and a note (.*)$")
    public void iHaveAQualityAndANote(String quality, String note) throws Throwable {
        this.quality = quality;
        this.note = note;
    }

    @When("^I call the chord command$")
    public void iCallTheChordCommand() throws Throwable {
        chord = new Chord(Arrays.asList(note, quality));
        chord.execute(env);
    }

    @Then("^The correct chord (.*) is displayed$")
    public void theCorrectChordIsDisplayed(String resultingChord) throws Throwable {
        assertEquals(resultingChord, env.getOutput().replaceAll("\\r|\\n", ""));
    }

}
