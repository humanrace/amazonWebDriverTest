package amazonTestSteps;

import cucumber.api.CucumberOptions;
import cucumber.api.java8.En;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.testng.annotations.BeforeClass;
        
/**
 * Created by pfokin on 12.05.2017.
 */

@RunWith(Cucumber.class)
@CucumberOptions(features = "AmazonTestIT.feature",
        glue = {"feature"},
        plugin = {"pretty"})

public class amazonTestIT implements En {

    private static boolean IF_CHROME_BROWSER_USED = true;

    @BeforeClass
    public static void goToAmazon() {

        if (IF_CHROME_BROWSER_USED) {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\pfokin\\Downloads\\chromedriver_win32\\chromedriver.exe");
        }
    }
}
