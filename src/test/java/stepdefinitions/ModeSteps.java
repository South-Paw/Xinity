package stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.Environment;
import seng302.command.musical.Mode;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Cucumber step definitions for the Midi class.
 */
public class ModeSteps {

    private Environment env = new Environment();
    private Mode modeCommand;
    private String note;
    private String modeType;
    private String degree;

    @Given("^I want to find the (.*) mode from (.*) and a degree of (.*)$")
    public void iHaveThreeParameters(String modeType, String note, String degree)
            throws Throwable {
        this.modeType = modeType;
        this.note = note;
        this.degree = degree;
    }

    @When("^I call the mode command$")
    public void iCallTheModeCommand() throws Throwable {
        modeCommand = new Mode(Arrays.asList(this.note, this.degree, this.modeType));
        modeCommand.execute(env);
    }

    @Then("^The corresponding scale of (.*) is displayed$")
    public void theCorrectMidiNumberIsDisplayed(String scale) throws Throwable {
        assertEquals(scale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

}
