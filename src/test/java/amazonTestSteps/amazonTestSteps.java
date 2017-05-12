package amazonTestSteps;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by pfokin on 12.05.2017.
 */

public class amazonTestSteps {

    private static boolean IF_CHROME_BROWSER_USED = true;

    @Before
    public static void goToAmazon() {

        if (IF_CHROME_BROWSER_USED) {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\pfokin\\Downloads\\chromedriver_win32\\chromedriver.exe");
        }
    }

    @Given("Im opening '([^\"]*)'")
    public void openSite(String siteDomainName){

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(siteDomainName);
        driver.close();

    }

}
