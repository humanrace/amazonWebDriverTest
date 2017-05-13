package amazonTestSteps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.junit.Assert;
import org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by pfokin on 12.05.2017.
 */

public class AmazonTestSteps {

    private static boolean IF_CHROME_BROWSER_USED = true;
    public static WebDriver driver;

    @Before
    public static void goToAmazon() {

        if (IF_CHROME_BROWSER_USED) {
            System.setProperty("webdriver.chrome.driver", "D:\\Work\\chromedriver.exe");
        }
    }

    @Given("Im opening '([^\"]*)'")
    public void openSite(String siteDomainName){

       // System.setProperty("webdriver.chrome.driver","D:\\Work\\chromedriver.exe");

//        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
//        capabilities.setCapability("marionette", true);

        //WebDriver driver = new ChromeDriver();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(siteDomainName);

        String url driver.getCurrentUrl()
        if (driver.getCurrentUrl().isEmpty()) {

            driver.getCurrentUrl().equals(siteDomainName);

    }

    @And("Verify that the page is correct and opened")
    public void checkPageIsValid()

    {


    }



    @After
    public void closeChrome()

    {
        driver.close();
    }
}

