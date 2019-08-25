package iqOptionTestSteps;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

import static org.testng.Assert.assertEquals;
import static org.testng.FileAssert.fail;
import static pages.IqOptionMainPage.*;


public class AuthorizationWebSteps {

    private static WebDriver driver;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    private static final String MAIN_PAGE_TITTLE = "Forex, Stocks, ETFs & Options Trading | IQ Option";
    private static final String WEB_DRIVER_PROPERTY_KEY = "webdriver.chrome.driver";
    private static final String WEB_DRIVER_PROPERTY_VALUE = "C:\\chromedriver.exe";

    private void openPage(String siteDomainName) {
        System.setProperty(WEB_DRIVER_PROPERTY_KEY, WEB_DRIVER_PROPERTY_VALUE);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(siteDomainName);
    }

    @And("enter login '([^\"]*)' and password '([^\"]*)'")
    public void enterLoginAndPassword(String login, String password) throws InterruptedException {
        driver.findElement(SideBarLocators.sidebarTabLocator).click();
        waitForElementByName(EMAIL_ELEMENT);
        driver.findElement(SideBarLoginLocators.emailByNameLocator).sendKeys(KEYS_CHECK);
        driver.findElement(SideBarLoginLocators.emailByNameLocator).clear();
        driver.findElement(SideBarLoginLocators.emailByNameLocator).sendKeys(login);
        waitForElementByName(PASSWORD_ELEMENT);
        driver.findElement(SideBarLoginLocators.passwordByNameLocator).sendKeys(password);
    }

    @And("click login and check profile '([^\"]*)' opened")
    public void checkPofileOpened(String profileName) {
        clickLogin();
        waitForElementByCss(SIDE_BAR_PROFILE_CSS);
        assertEquals(driver.findElement(SideBarLocators.sidebarProfileLocator).getText(),
                profileName);
    }

    @And("click login$")
    public void clickLogin() {
        driver.findElement(Buttons.buttonGreenLocator).click();
    }

    @And("check email error message '([^\"]*)'")
    public void checkEmailErrorMessage(String errorMessage) {
        assertEquals(driver.findElement(SideBarLoginLocators.sidebarLoginInputErrorLocator).getText(),
                errorMessage);
    }

    @And("check login error message '([^\"]*)'")
    public void checkLoginErrorMessage(String errorMessage) {
        waitForElementByCss(SIDE_BAR_LOGIN_ERROR_CSS);
        assertEquals(driver.findElement(SideBarLoginLocators.sidebarErrorLocator).getText(),
                errorMessage);
    }

    @After("@web")
    public void exit() {
        closeBrowserAndExit();
    }

    private void waitForElementByName(String element) {
        WebDriverWait wait = new WebDriverWait(driver, 20); // Wait for 10 seconds.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(element)));
    }

    private void waitForElementByCss(String element) {
        WebDriverWait wait = new WebDriverWait(driver, 20); // Wait for 10 seconds.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(element)));
    }

    public void openSite(String siteDomainName) throws Exception {
        openPage(siteDomainName);
        for (int second = 0; ; second++) {
            if (second >= 20) fail("Unable to open " + siteDomainName + " ..check URL ar internet connection");
            try {
                if (driver.getTitle().contains(MAIN_PAGE_TITTLE)) break;
            } catch (Exception e) {
                logger.info(e.getMessage());
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

    private void closeBrowserAndExit() {
        driver.close();
        driver.quit();
    }
}