package amazonTestSteps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.FileAssert.fail;

/**
 * Created by pfokin on 12.05.2017.
 */

public class AmazonTestSteps {

    private static WebDriver driver;
    private static final String SITE_URL = "https://www.amazon.co.uk/";
    private static final String TITLE = "Amazon.co.uk: Harry Potter and the Cursed Child: Books";

    @Before
    public static void goToAmazon() {

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        }


    @Given("Im opening '([^\"]*)'")
    public void openSite(String siteDomainName) throws Exception {

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

    @And("Search for '([^\"]*)' in section books")
    public void searchForBooks(String searchString) {
        driver.get(SITE_URL);
        driver.findElement(By.id("twotabsearchtextbox")).clear();
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys(searchString);
        driver.findElement(By.id("issDiv1")).click();
        driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);

        assertTrue(driver.getTitle().equalsIgnoreCase(TITLE));
    }

    @And("Verify that the first items has the title: '([^\"]*)'")
    public void checkFirstItemOnThePage(String firstItemTitle) {

        assertTrue(driver.findElement(By.id("result_0")).findElement(By.cssSelector(
                "a.a-link-normal.s-access-detail-page.s-color-twister-title-link.a-text-normal")).getText().contains(firstItemTitle),
                "First element of the page is not same as expected one..Actual: was: " +
                        driver.findElement(By.id("result_0")).findElement(By.cssSelector(
                                "a.a-link-normal.s-access-detail-page.s-color-twister-title-link.a-text-normal")).getText());
    }

    @And("It has a badge '([^\"]*)'")
    public void checkBadge(String badge) {
        assertEquals(driver.findElement(By.id("result_0")).findElement(By.cssSelector(
                "span.sx-badge-text.s-color-white")).getText(),
                badge);
    }

    @And("Selected type is '([^\"]*)'")
    public void checkType(String type) {

        assertEquals(driver.findElement(By.id("result_0")).findElement(By.cssSelector(
                "h3.a-size-small.s-inline.a-text-normal")).getText(),
                type);
    }

    @And("the price is '([^\"]*)'")
    public void checkPrice(String priceData) {

        assertEquals(driver.findElement(By.id("result_0")).findElement(By.cssSelector(
                "span.a-size-base.a-color-price.s-price.a-text-bold")).getText(),
                priceData);
    }

    @Then("Navigate to the book details and verify the '([^\"]*)', the '([^\"]*)' the '([^\"]*)' and the type '([^\"]*)'")
    public void navigateToBookDetails(String title, String badge, String priceData, String type) {

        driver.findElement(By.id("result_0")).findElement(By.cssSelector(
                "a.a-link-normal.s-access-detail-page.s-color-twister-title-link.a-text-normal")).click();
        driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);

        //Checking title
        assertTrue(driver.findElement(By.id("productTitle")).getText().contains(title), "Product title doesnt contain " +
                "expected title: Expected: " + title + " ..But Actual was:" + driver.findElement(
                By.id("productTitle")).getText());
        //Checking badge
        assertTrue(driver.findElement(By.cssSelector("i.a-icon.a-icon-addon.p13n-best-seller-badge")).getText().contains(badge), "Badge does not contain expected badge data..");

        //Checking priceData
        assertEquals(driver.findElement(By.id("a-autoid-3-announce")).findElement(By.cssSelector(
                "span.a-size-base.a-color-price.a-color-price")).getText(), priceData);

        //Checking type
        assertEquals(driver.findElement(By.id("a-autoid-3-announce")).findElement(By.cssSelector(
                "span")).getText(), type);
    }


    @And("Add the book to the basket")
    public void submitToCard() {

        driver.findElement(By.id("submit.add-to-cart")).click();

    }

    @And("Verify that the notification is shown with the title '([^\"]*)'")
    public void checkNotification(String addedToBasket) {

        driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);

        Assert.assertTrue(driver.findElement(By.id("huc-v2-order-row-inner")).isDisplayed());

        Assert.assertEquals(driver.findElement(By.id("huc-v2-order-row-inner")).findElement(By.id("huc-v2-order-row-confirm-text")).
                getText(), addedToBasket);
    }

    @And("There is '([^\"]*)' in the basket")
    public void checkBasketItems(String numberOfItems) {
        Assert.assertTrue("Amount of items in the basket not same as expected..",driver.findElement(By.cssSelector(
                "span.a-size-medium.a-align-center.huc-subtotal")).getText().
                        contains(numberOfItems));

    }

    @Then("Click on edit the basket")
    public void clickEdiBasket() {

        driver.findElement(By.id("hlb-view-cart-announce")).click();
    }

    @And("Verify that the book is shown on the list")
    public void checkBookShown() {

        driver.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
        Assert.assertTrue("Book is not displaed in the basket list..",driver.findElement(By.cssSelector(
                "span.a-size-medium.sc-product-title.a-text-bold")).isDisplayed());
    }

    @And("The title is '([^\"]*)', type of print is '([^\"]*)', price is '([^\"]*)', quantity is '([^\"]*)' and total price is '([^\"]*)'")
    public void checkBasket(String title, String type, String price, String quantity, String totalPrice) {

        //check Title
        Assert.assertTrue("Title of the Book in the basket is not same as expected one....",driver.findElement(
                By.cssSelector("span.a-size-medium.sc-product-title.a-text-bold")).getText().contains(title));

        //check type
        Assert.assertEquals("Type of the basket item is not equal to expected one..", driver.findElement(
                By.cssSelector("span.a-size-small.a-color-secondary.sc-product-binding")).getText(),type);

        //check price
        Assert.assertEquals("Actual price of the basket product is not same as expected",price,driver.
                findElement(By.cssSelector(
                        "span.a-size-medium.a-color-price.sc-price.sc-white-space-nowrap.sc-product-price.sc-price-sign.a-text-bold")).
                getText());

        //check price
        Assert.assertEquals("Quantity of expeced items in the basket is not same as expected one",quantity,
                driver.findElement(By.id("a-autoid-2")).findElement(By.cssSelector("span.a-dropdown-prompt")).
                getText());

        //check total price
        Assert.assertEquals("Quantity of expeced items in the basket is not same as expected one",totalPrice,
                driver.findElement(By.id("sc-subtotal-amount-activecart")).getText());
    }

    @After
    public void closeChrome() {
        driver.close();
        driver.quit();
    }
}