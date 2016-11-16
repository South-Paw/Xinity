package stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.Environment;
import seng302.command.musical.ChordFunction;
import seng302.command.musical.Quality;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Chord Function Cucumber test.
 */
public class ChordFunctionSteps {

    private Environment environment = new Environment();
    private ChordFunction chordFunction;
    private String function;
    private String note;

    @Given("^I have the function and note (.*) and (.*)$")
    public void iHaveTheFunctionAndNote(String function, String note) throws Throwable {
        this.function = function;
        this.note = note;
    }

    @When("^I call the chord function command$")
    public void iCallTheChordFunctionCommand() throws Throwable {
        chordFunction = new ChordFunction(Arrays.asList(function, note));
        chordFunction.execute(environment);
    }

    @Then("^The correct quality with chord (.*) is displayed$")
    public void theCorrectQualityWithChordIsDisplayed(String quality) throws Throwable {
        assertEquals(quality, environment.getOutput().replaceAll("\\r|\\n", ""));
    }
}
