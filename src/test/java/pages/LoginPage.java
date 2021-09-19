package pages;

import baseEntities.BasePage;
import core.BrowserService;
import core.ReadProperties;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import wrappers.Button;

@Slf4j
public class LoginPage extends BasePage {

    private static final String ENDPOINT = "login/?redir=&redir_ssl=1&snr=1_4_4__global-header";
    private static final By usernameInputBy = By.id("input_username");
    private static final By passwordInputBy = By.id("input_password");
    private static final By submitLoginButtonBy = By.tagName("button");
    private static final By errorMessageBy = By.id("error_display");
    private static final By languagePulldownBy = By.id("language_pulldown");

    public LoginPage(BrowserService browserService, boolean openPageByUrl) {
        super(browserService, openPageByUrl);
    }

    @Override
    protected void openPage() {
        browserService.getDriver().get(ReadProperties.getBaseUrl() + ENDPOINT);
    }

    @Override
    protected boolean isPageOpened() {
        try {
            if (getSubmitLoginButton().isDisplayed()) {
                log.info("Login Page is opened successfully");
                return true;
            } else return false;
        } catch (Exception ex) {
            log.error("Login page has not been opened. There is no such element or a selector is incorrect");
            return false;
        }
    }

    public Button getSubmitLoginButton() {
        return new Button(browserService.getDriver(), submitLoginButtonBy);
    }

    public WebElement getUsernameInput() {
        return browserService.getWait().waitForVisibility(usernameInputBy);
    }

    public WebElement getPasswordInput() {
        return browserService.getWait().waitForVisibility(passwordInputBy);
    }

    public WebElement getErrorMessage() {
        return browserService.getWait().waitForVisibility(errorMessageBy);
    }

    public WebElement getLanguagePulldown() {
        return browserService.getWait().waitForVisibility(languagePulldownBy);
    }
}
