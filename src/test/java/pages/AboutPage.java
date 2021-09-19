package pages;

import baseEntities.BasePage;
import core.BrowserService;
import core.ReadProperties;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import wrappers.Button;

@Slf4j
public class AboutPage extends BasePage {

    private static final String ENDPOINT = "/about/";
    private static final By downloadSteamExeButtonBy = By.xpath("//a[@class='about_install_steam_link']");
    private static final By downloadSteamDmgButtonBy =
            By.xpath("//*[@id='about_greeting']//child :: a[substring(@href, string-length(@href) - string-length('.dmg') + 1)  = '.dmg']");

    private static final By downloadSteamDebButtonBy =
            By.xpath("//*[@id='about_greeting']//child :: a[substring(@href, string-length(@href) - string-length('.deb') + 1)  = '.deb']");

    public AboutPage(BrowserService browserService, boolean openPageByUrl) {
        super(browserService, openPageByUrl);
    }


    @Override
    protected void openPage() {
        browserService.getDriver().get(ReadProperties.getBaseUrl() + ENDPOINT);
    }

    @Override
    protected boolean isPageOpened() {
        try {
            if (getDownloadSteamExeButton().isDisplayed()) {
                log.info("About page opened successfully");
                return true;
            } else return false;
        } catch (Exception ex) {
            log.error("About page has not been opened");
            return false;
        }
    }

    public Button getDownloadSteamExeButton() {
        return new Button(browserService.getDriver(), downloadSteamExeButtonBy);
    }

    public Button getDownloadSteamDmgButton() {
        return new Button(browserService.getDriver(), downloadSteamDmgButtonBy);
    }

    public Button downloadSteamDebButton() {
        return new Button(browserService.getDriver(), downloadSteamDebButtonBy);
    }

}
