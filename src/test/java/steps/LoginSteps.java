package steps;

import baseEntities.BaseStep;
import core.BrowserService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import pages.Header;
import pages.LoginPage;
import utils.ScreenMaker;

import java.io.File;

@Slf4j
public class LoginSteps extends BaseStep {

    private LoginPage loginPage;
    private Header header = new Header(browserService, false);

    public LoginSteps(BrowserService browserService) {
        super(browserService);
    }

    public LoginSteps openHomePage() {
        new Header(browserService, true);
        return this;
    }

    public LoginSteps openLoginPage() {

        ScreenMaker.takeSnapShot(browserService.getDriver());
        header.getLoginButton().click();
        loginPage = new LoginPage(browserService, false);
        return this;
    }

    public void loginWithIncorrectCredentials(String username, String password) {

        loginPage.getUsernameInput().sendKeys(username);
        log.info("UserName entered");

        loginPage.getPasswordInput().sendKeys(password);
        log.info("Password entered");

        ScreenMaker.takeSnapShot(browserService.getDriver());

        loginPage.getSubmitLoginButton().click();
        log.info("Submit Button clicked");
    }
}
