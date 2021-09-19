package pages;

import baseEntities.BasePage;
import core.BrowserService;
import core.ReadProperties;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Slf4j
public class HomePage extends BasePage {

    private static final By globalHeaderBy = By.id("global_header");

    public HomePage(BrowserService browserService, boolean openPageByUrl) {
        super(browserService, openPageByUrl);
    }

    @Override
    protected void openPage() {
        browserService.getDriver().get(ReadProperties.getBaseUrl());
    }

    @Override
    protected boolean isPageOpened() {
        try {
            if (getGlobalHeader().isDisplayed()) {
                log.info("Main page opened successfully");
                return true;
            } else return false;
        } catch (Exception ex) {
            log.error("Main page has not been opened.");
            return false;
        }
    }

    public WebElement getGlobalHeader() {
        return browserService.getWait().waitForVisibility(globalHeaderBy);
    }

}
