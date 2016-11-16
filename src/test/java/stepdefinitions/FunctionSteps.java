package stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.Environment;
import seng302.command.musical.ChordFunction;
import seng302.command.musical.Function;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Function cucumber steps class.
 */
public class FunctionSteps {

    private Environment environment = new Environment();
    private Function function;
    private String chordNote;
    private String quality;
    private String scaleNote;

    @Given("^I have the note (.*) and the quality (.*) and the note (.*)$")
    public void iHaveTheNoteAndTheQualityAndTheNote(String chordNote, String quality, String scaleNote) throws Throwable {
        this.chordNote = chordNote;
        this.quality = quality;
        this.scaleNote = scaleNote;
    }

    @When("^I call the function command$")
    public void iCallTheFunctionCommand() throws Throwable {
        function = new Function(Arrays.asList(chordNote, quality, scaleNote));
        function.execute(environment);
    }

    @Then("^The correct function (.*) is displayed$")
    public void theCorrectFunctionIsDisplayed(String function) throws Throwable {
        assertEquals(function, environment.getOutput().replaceAll("\\r|\\n", ""));
    }

}
