package iqOptionTestSteps;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.Assert.assertEquals;
import static org.testng.FileAssert.fail;


public class AuthorizationWebSteps {

    private static WebDriver driver;

    private static final String MAIN_PAGE_TITTLE = "Forex, Stocks, ETFs & Options Trading | IQ Option";
    private static final String WEB_DRIVER_PROPERTY_KEY = "webdriver.chrome.driver";
    private static final String WEB_DRIVER_PROPERTY_VALUE = "C:\\chromedriver.exe";

    @Given("Im opening '([^\"]*)'")
    public void openSite(String siteDomainName) throws Exception {
        openPage(siteDomainName);
        for (int second = 0; ; second++) {
            if (second >= 20) fail("Unable to open " + siteDomainName + " ..check URL ar internet connection");
            try {
                if (driver.getTitle().contains(MAIN_PAGE_TITTLE)) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }

        if (driver.getCurrentUrl() == null) fail("Site URL in Null..");

        Assert.assertTrue("Site URL is not same as expected one..Expected: " + siteDomainName
                        + " But Actual was: " + driver.getCurrentUrl()
                , driver.getCurrentUrl().equals(siteDomainName));
        Assert.assertTrue("Site title does not contain..Expected: " + siteDomainName
                        + " But Actual was: " + driver.getTitle()
                , driver.getTitle().contains(MAIN_PAGE_TITTLE));
    }

    private void openPage(String siteDomainName) {
        System.setProperty(WEB_DRIVER_PROPERTY_KEY, WEB_DRIVER_PROPERTY_VALUE);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(siteDomainName);
    }

    @And("enter login '([^\"]*)' and password '([^\"]*)'")
    public void enterLoginAndPassword(String login, String password) throws InterruptedException {
        driver.findElement(By.className("SidebarTab__text")).click();
        waitForElementByName("email");
        driver.findElement(By.name("email")).sendKeys("check");
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("email")).sendKeys(login);
        waitForElementByName("password");
        driver.findElement(By.name("password")).sendKeys(password);
    }

    @And("click login and check profile '([^\"]*)' opened")
    public void checkPofileOpened(String profileName) {
        clickLogin();
        waitForElementByCss(".SidebarProfile__UserName");
        assertEquals(driver.findElement(By.cssSelector(".SidebarProfile__UserName")).getText(),
                profileName);
    }

    @And("click login$")
    public void clickLogin() {
        driver.findElement(By.cssSelector(".Button_green")).click();
    }

    @And("check email error message '([^\"]*)'")
    public void checkEmailErrorMessage(String errorMessage) {
        assertEquals(driver.findElement(By.cssSelector(".iqInput__error")).getText(),
                errorMessage);
    }

    @And("check login error message '([^\"]*)'")
    public void checkLoginErrorMessage(String errorMessage) {
        waitForElementByCss(".css-iesei9");
        assertEquals(driver.findElement(By.cssSelector(".css-iesei9")).getText(),
                errorMessage);
    }

    @After("@web")
    public void exit() {
        closeBrowserAndExit();
    }

    private void waitForElementByName(String element) {
        WebDriverWait wait = new WebDriverWait(driver, 10); // Wait for 10 seconds.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(element)));
    }

    private void waitForElementByCss(String element) {
        WebDriverWait wait = new WebDriverWait(driver, 10); // Wait for 10 seconds.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(element)));
    }

    private void closeBrowserAndExit() {
        driver.close();
        driver.quit();
    }
}