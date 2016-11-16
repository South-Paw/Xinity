package stepdefinitions;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@CucumberOptions(features="src/test/java/resources")
@RunWith(Cucumber.class)
public class RunCucumberTests {
    //Make sure to edit your run configuration for the feature file so that Glue = stepdefinitions
}