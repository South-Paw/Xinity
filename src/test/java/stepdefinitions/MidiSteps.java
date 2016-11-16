package stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.Environment;
import seng302.command.musical.Midi;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Cucumber step definitions for the Midi class.
 */
public class MidiSteps {

    private Midi midi;
    private Environment env = new Environment();
    private String note;

    @Given("^I have a note (.*)$")
    public void iHaveANote(String note) throws Throwable {
        this.note = note;
    }

    @When("^I call the midi command$")
    public void iCallTheMidiCommand() throws Throwable {
        midi = new Midi(Arrays.asList(note));
        midi.execute(env);
    }

    @Then("^The correct midi number of (.*) is displayed$")
    public void theCorrectMidiNumberIsDisplayed(String number) throws Throwable {
        assertEquals(number, env.getOutput().replaceAll("\\r|\\n", ""));
    }

}
