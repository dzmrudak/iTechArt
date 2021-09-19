package steps;

import baseEntities.BaseStep;
import core.BrowserService;
import lombok.extern.slf4j.Slf4j;
import pages.Header;
import pages.AboutPage;
import pages.HomePage;
import utils.ScreenMaker;

@Slf4j
public class InstallSteamSteps extends BaseStep {

    private AboutPage aboutPage;
    private Header header = new Header(browserService, false);

    public InstallSteamSteps(BrowserService browserService) {
        super(browserService);
    }

    public InstallSteamSteps openHomePage() {
        new HomePage(browserService, true);
        return this;
    }

    public InstallSteamSteps openInstallSteamPage() {

        ScreenMaker.takeSnapShot(browserService.getDriver());
        header.getInstallSteamButton().click();
        aboutPage = new AboutPage(browserService, false);
        return this;
    }

    public void downloadSteamExe() {

        ScreenMaker.takeSnapShot(browserService.getDriver());
        aboutPage.getDownloadSteamExeButton().click();
        log.info("Downloading of a steam.exe file started");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void downloadSteamDmg() {

        ScreenMaker.takeSnapShot(browserService.getDriver());
        aboutPage.getDownloadSteamDmgButton().click();
        log.info("Downloading of a steam.dmg file started");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void downloadSteamDeb() {

        ScreenMaker.takeSnapShot(browserService.getDriver());
        aboutPage.downloadSteamDebButton().click();
        log.info("Downloading of a steam.deb file started");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
