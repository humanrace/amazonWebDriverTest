package pages;


import org.openqa.selenium.By;

public class IqOptionMainPage {

    public static final String BASE_URL = "https://iqoption.com";

    public static final String EMAIL_ELEMENT = "email";
    public static final String PASSWORD_ELEMENT = "password";
    public static final String SIDE_BAR_PROFILE_CSS = ".SidebarProfile__UserName";
    public static final String SIDE_BAR_LOGIN_ERROR_CSS = ".css-iesei9";

    public static final String KEYS_CHECK = "check";

    public static class SideBarLocators {
        public static final By sidebarTabLocator = By.className("SidebarTab__text");
        public static final By sidebarProfileLocator = By.cssSelector(SIDE_BAR_PROFILE_CSS);
    }

    public static class SideBarLoginLocators {
        public static final By emailByNameLocator = By.name(EMAIL_ELEMENT);
        public static final By passwordByNameLocator = By.name(PASSWORD_ELEMENT);

        public static final By sidebarLoginInputErrorLocator = By.cssSelector(".iqInput__error");
        public static final By sidebarErrorLocator = By.cssSelector(SIDE_BAR_LOGIN_ERROR_CSS);
    }

    public static class Buttons {
        public static final By buttonGreenLocator = By.cssSelector(".Button_green");
    }
}