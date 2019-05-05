import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/feature",
        glue = {"iqOptionTestSteps"},
        plugin = { "pretty", "json:target/cucumber-reports/Cucumber.json",
                "html:target/cucumber-reports"},
        tags = {"@critical"}
)
public class TestRunner {
}
