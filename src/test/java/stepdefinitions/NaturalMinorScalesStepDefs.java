package stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import seng302.Environment;
import seng302.command.musical.FindScale;
import seng302.command.musical.FindScaleMidi;
import seng302.command.play.PlayScale;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class NaturalMinorScalesStepDefs {
    private Environment environment;
    private FindScale findScale;
    private FindScaleMidi findScaleMidi;
    private PlayScale playScale;

    @Given("^There is an environment$")
    public void thereIsAnEnvironment() throws Throwable {
        environment = new Environment();
    }

    @When("^I use FindScale with ([^\"]*), ([^\"]*) as the parameter$")
    public void theNoteCIsUsedAsTheParameter(String note, String scaleType) throws Throwable {
        findScale = new FindScale(Arrays.asList(note, scaleType));
        findScale.execute(environment);
    }

    @Then("^The output \"([^\"]*)\" should be displayed$")
    public void theScaleShouldBeDisplayed(String scale) throws Throwable {
        assertEquals(scale, environment.getOutput().replaceAll("\\r|\\n", ""));
    }

    @When("^I use the command FindScaleMidi with parameters ([^\"]*), ([^\"]*)")
    public void iUseTheCommandFindScaleMidiWithParametersCAndMinor(String note, String scaleType) throws Throwable {
        findScaleMidi = new FindScaleMidi(Arrays.asList(note, scaleType));
        findScaleMidi.execute(environment);
    }

    @When("^I use the command PlayScale with parameters ([^\"]*), ([^\"]*)$")
    public void iUseTheCommandPlayScaleWithParametersCMinor(String note, String scaleType) throws Throwable {
        playScale = new PlayScale(Arrays.asList(note, scaleType));
        playScale.execute(environment);
    }
}
