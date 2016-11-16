package stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.Environment;
import seng302.command.musical.Chord;
import seng302.command.musical.Quality;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Cucumber test for the quality command.
 */
public class QualitySteps {

    private Environment environment = new Environment();
    private Quality quality;
    private String function;

    @Given("^I have only a function (.*)$")
    public void iHaveOnlyAFunction(String function) throws Throwable {
        this.function = function;
    }

    @When("^I call the quality command$")
    public void iCallTheQualityCommand() throws Throwable {
        quality = new Quality(Arrays.asList(function));
        quality.execute(environment);
    }

    @Then("^The correct chordQuality (.*) is displayed$")
    public void theCorrectChordQualityIsDisplayed(String quality) throws Throwable {
        assertEquals(quality, environment.getOutput().replaceAll("\\r|\\n", ""));
    }
}
