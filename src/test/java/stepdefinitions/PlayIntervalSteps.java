package stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.Environment;
import seng302.command.play.PlayInterval;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Cucumber step definitions for the Interval class.
 */
public class PlayIntervalSteps {

    private Environment env = new Environment();
    private List<String> args;
    private String note;
    private String givenInterval;

    @Given("^I have to play a note (.*) and an interval (.*)$")
    public void iHaveANoteAndAnIntervalToPlay(String note, String givenInterval) throws Throwable {
        this.note = note;
        this.givenInterval = givenInterval;
    }

    @When("^I call the playInterval command$")
    public void iCallThePlayIntervalCommand() throws Throwable {
        args = Arrays.asList(note, givenInterval);
        new PlayInterval(args).execute(env);

    }

    @Then("^The correct notes of (.*) and (.*) are displayed$")
    public void theCorrectNotesAreDisplayed(String note1, String note2) throws Throwable {
        assertEquals(note1 + " and " + note2, env.getOutput().replaceAll("\\r|\\n", ""));
    }
}
