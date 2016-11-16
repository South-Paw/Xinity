package stepdefinitions;

import cucumber.api.java.en.*;
import static org.junit.Assert.*;

import seng302.Environment;
import seng302.command.tutor.TermTutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TermTutorSteps {

    private TermTutor termTutor;
    private Environment environment;

    @Given("^I want to run a term tutor test$")
    public void iWantToRunATermTutorTest() throws Throwable {
        termTutor = new TermTutor();
    }

    @When("^The command is executed with the invalid parameters (.*)$")
    public void theCommandIsExecutedWithInvalidParameters(String argument) throws Throwable {
        List<String> argumentsList = new ArrayList<>();
        argumentsList.add(argument);
        termTutor = new TermTutor(argumentsList);
        environment = new Environment();
        termTutor.execute(environment);
    }

    @Then("^A sensible error should be displayed$")
    public void aSensibleErrorShouldBeDisplayed() throws Throwable {
        String output = environment.getOutput();
        assertTrue(output.contains("[ERROR]"));
    }

}
