package stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import seng302.Environment;
import seng302.command.musical.Signature;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Cucumber step definitions for the Signature class.
 *
 * Class is commented out as the formatting of the tests are causing them to fail.
 */

public class SignatureSteps {

    private Signature signature;
    private Environment env = new Environment();
    private String quality;
    private String note;
    private String flag;


    @Given("^I have a chord (.*), (.*)$")
    public void iHaveAChord(String note, String quality) throws Throwable {
        this.note = note;
        this.quality = quality;
    }

    @When("^I call the show signature command$")
    public void iCallTheShowSignatureCommand() throws Throwable {
        signature = new Signature(Arrays.asList(note, quality));
        signature.execute(env);
    }

    @Then("^The correct key signature (.*) is displayed$")
    public void theSignatureIsDisplayed(String resultingSignature) throws Throwable {
        assertEquals(resultingSignature, env.getOutput().replaceAll("\\r|\\n", ""));

    }

    @Given("^I have flag (.*) and a chord (.*), (.*)$")
    public void iHaveAChord(String flag, String note, String quality) throws Throwable {
        this.note = note;
        this.quality = quality;
        this.flag =  flag;
    }

    @When("^I call the show signature command with flag$")
    public void iCallTheShowSignatureCommandWithFlag() throws Throwable {
        signature = new Signature(Arrays.asList(note, quality, flag));
        signature.execute(env);
    }

    @Then("^The correct key signature notes (.*) are displayed$")
    public void theSignatureIsDisplayedAsNotes(String resultingSignature) throws Throwable {
        assertEquals(resultingSignature, env.getOutput().replaceAll("\\r|\\n", ""));

    }

}
