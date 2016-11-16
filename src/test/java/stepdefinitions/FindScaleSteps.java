package stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng302.Environment;
import seng302.command.musical.FindScale;

import org.junit.Assert;
import java.util.Arrays;

/**
 * Cucumber stepdefs for the the findScale command.
 *
 * @author plr37
 */
public class FindScaleSteps {

    private Environment env = new Environment();
    private String note;
    private String scaleType;

    @Given("^I have a noteString (.*) and a scaleType (.*)$")
    public void iHaveANoteAndScaleType(String note, String scaleType) throws  Throwable {
        this.note = note;
        this.scaleType = scaleType;
    }

    @When("^I call the findScale command$")
    public void iCallTheChordCommand() throws Throwable {
        new FindScale(Arrays.asList(note, scaleType)).execute(env);
    }

    @Then("^The output (.*) is displayed$")
    public void theCorrectChordIsDisplayed(String scale) throws Throwable {
        Assert.assertEquals(scale, env.getOutput().replaceAll("\\r|\\n", ""));
    }

}