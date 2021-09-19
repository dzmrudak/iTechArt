package baseEntities;

import core.BrowserService;
import core.ReadProperties;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePage {

    protected static final int WAIT_FOR_PAGE_LOAD_IN_SECONDS = 10;
    protected WebDriver driver;
    protected BrowserService browserService;
    public String baseUrl;

    protected abstract void openPage();
    protected abstract boolean isPageOpened();

    public BasePage(BrowserService browserService, boolean openPageByUrl){
        this.browserService = browserService;
        this.driver = browserService.getDriver();
        this.baseUrl = new ReadProperties().getBaseUrl();

        if(openPageByUrl) {
            openPage();
        }

        waitForOpen();
    }

    protected void waitForOpen() {
        int secondsCount = 0;
        boolean isPageOpenedIndicator = isPageOpened();

        while(!isPageOpenedIndicator && secondsCount < WAIT_FOR_PAGE_LOAD_IN_SECONDS) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            secondsCount++;
            isPageOpenedIndicator = isPageOpened();
        }
        if(!isPageOpenedIndicator) {
            throw new AssertionError("Page was not opened");
        }
    }

    public boolean isPresented(WebElement e) {
        boolean flag;
        try {
            e.isDisplayed();
            flag = true;
        }
        catch (Exception ex) {
            flag = false;
        }
        return flag;
    }


}
