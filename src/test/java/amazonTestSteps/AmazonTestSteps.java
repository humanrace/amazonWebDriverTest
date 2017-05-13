package amazonTestSteps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.junit.Assert;
import org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.server.SeleniumServer;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.FileAssert.fail;

/**
 * Created by pfokin on 12.05.2017.
 */

public class AmazonTestSteps {

    private static boolean IF_CHROME_BROWSER_USED = true;
    public static WebDriver driver;

    public static final String SITE_URL = "https://www.amazon.co.uk/";

    @Before

    public static void goToAmazon() {

        if (IF_CHROME_BROWSER_USED) {
            System.setProperty("webdriver.chrome.driver", "D:\\Work\\chromedriver.exe");
        }
    }

    @Given("Im opening '([^\"]*)'")
    public void openSite(String siteDomainName) throws Exception {

        // System.setProperty("webdriver.chrome.driver","D:\\Work\\chromedriver.exe");

//        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
//        capabilities.setCapability("marionette", true);

        //WebDriver driver = new ChromeDriver();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(siteDomainName);

        for (int second = 0; ; second++) {
            if (second >= 20) fail("Unable to open " + SITE_URL + " ..check URL ar internet connection");
            try {
                if (driver.getTitle().contains("Amazon.co.uk")) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }

        if (driver.getCurrentUrl() == null) fail("Site URL in Null..");

        Assert.assertTrue("Site URL is not same as expected one..Expected: " + SITE_URL
                        + " But Actual was: " + driver.getCurrentUrl()
                , driver.getCurrentUrl().equals(SITE_URL));

        Assert.assertTrue("Site title does not contain..Expected: Amazon.co.uk"
                        + " But Actual was: " + driver.getTitle()
                , driver.getTitle().contains("Amazon.co.uk"));

    }


    @And("Search for '([^\"]*)' and the '([^\"]*)' in section books")
    public void searchForBooks(String authorName, String bookName) {


        driver.get(SITE_URL);
        driver.findElement(By.id("twotabsearchtextbox")).clear();
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Harry Potter and the Cursed Child");
        driver.findElement(By.id("issDiv1")).click();

        assertTrue(driver.getTitle().equalsIgnoreCase("Amazon.co.uk: Harry Potter and the Cursed Child: Books"));
    }

    @And("Verify that the first items has the title: '([^\"]*)'")
    public void checkFirstItemOnThePage(String firstItemTitle) {

            //driver.findElement(By.id("result_0")).getAttribute();

        assertTrue(driver.findElement(By.xpath("//li[@id='result_0']/div/div/div/div[2]/div[2]/div/a/h2")).getText()
                .contains(firstItemTitle),
                "First element of the page is not same as expected one..Actual: was: "+
                        driver.findElement(By.xpath("//li[@id='result_0']/div/div/div/div[2]/div[2]/div/a/h2")).getText()
        );



    }


    @After
    public void closeChrome()

    {
        driver.close();
    }
}





