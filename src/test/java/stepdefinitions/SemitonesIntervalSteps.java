package stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.Environment;
import seng302.command.musical.Interval;
import seng302.command.musical.SemitonesInterval;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Cucumber step definitions for the Interval class.
 */
public class SemitonesIntervalSteps {

    private SemitonesInterval semitonesInterval;
    private Environment env = new Environment();
    private String interval;

    @Given("^I have a given interval (.*)$")
    public void iHaveAGivenInterval(String interval) throws Throwable {
        this.interval = interval;
    }

    @When("^I call the semitonesInterval command$")
    public void iCallTheSemitonesIntervalCommand() throws Throwable {
        semitonesInterval = new SemitonesInterval(Arrays.asList(interval));
        semitonesInterval.execute(env);
    }

    @Then("^The correct response of (.*) is displayed$")
    public void theCorrectResponseIsDisplayed(String response) throws Throwable {
        assertEquals(response, env.getOutput().replaceAll("\\r|\\n", ""));
    }
}
