package stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.Environment;
import seng302.command.musical.Interval;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Cucumber step definitions for the Interval class.
 */
public class IntervalSteps {

    private Interval interval;
    private Environment env = new Environment();
    private String givenInterval;
    private String note;

    @Given("^I have note (.*) and an interval (.*)$")
    public void iHaveANoteAndAnInterval(String note, String givenInterval) throws Throwable {
        this.note = note;
        this.givenInterval = givenInterval;
    }

    @When("^I call the interval command$")
    public void iCallTheIntervalCommand() throws Throwable {
        interval = new Interval(Arrays.asList(note, givenInterval));
        interval.execute(env);
    }

    @Then("^The correct note of (.*) is displayed$")
    public void theCorrectNoteIsDisplayed(String note) throws Throwable {
        assertEquals(note, env.getOutput().replaceAll("\\r|\\n", ""));
    }
}
