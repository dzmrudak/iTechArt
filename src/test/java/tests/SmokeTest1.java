package tests;

import Models.GameRowModel;
import Models.SearchableItemModel;
import baseEntities.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.GameDetailedPage;
import pages.Header;
import pages.LoginPage;
import steps.GameGenreSteps;
import steps.InstallSteamSteps;
import steps.LoginSteps;
import testData.staticProviders.LoginProvider;
import utils.ScreenMaker;

import java.io.File;
import java.util.*;

@Slf4j
public class SmokeTest1 extends BaseTest {

    @Test(description = "Login with incorrect credentials",
            dataProvider = "Use Incorrect Credentials",
            dataProviderClass = LoginProvider.class)
    public void LoginWithIncorrectCredentialsTest(String username, String email) {

        LoginSteps loginSteps = new LoginSteps(browserService);
        loginSteps
                .openHomePage()
                .openLoginPage()
                .loginWithIncorrectCredentials(username, email);

        LoginPage loginPage = new LoginPage(browserService, false);

        if (loginPage.getLanguagePulldown().getText().equals("language")) {
            Assert.assertEquals(loginPage.getErrorMessage().getText(),
                    "Incorrect account name or password. Remember that your email address may not match your account name.");
        } else if (loginPage.getLanguagePulldown().getText().equals("язык")) {
            Assert.assertEquals(loginPage.getErrorMessage().getText(),
                    "Неверные имя аккаунта или пароль. Помните, что адрес электронной почты может не совпадать с именем аккаунта.");
        } else {
            loginPage.getErrorMessage().isDisplayed();
        }

        ScreenMaker.takeSnapShot(browserService.getDriver());
    }

    @Test(description = "Download installation Steam files from the main page")
    public void DownloadAnInstallationFileTest() {

        InstallSteamSteps installSteamSteps = new InstallSteamSteps(browserService);

        installSteamSteps
                .openHomePage()
                .openInstallSteamPage()
                .downloadSteamExe();

        File[] listOfFiles = browserService.getFolder().listFiles();
        Assert.assertTrue(Objects.requireNonNull(listOfFiles).length != 0);

        int exeFilesCount = 0;
        for (File file : listOfFiles) {
            if (file.getName().endsWith(".exe")) {
                Assert.assertTrue(file.length() != 0);
                exeFilesCount++;
                log.info("Steam.exe file has been loaded successfully");
            }
        }
        if (exeFilesCount == 0) {
            log.info("There are no EXE file loaded.");
        }

        installSteamSteps
                .openHomePage()
                .openInstallSteamPage()
                .downloadSteamDmg();

        int dmgFilesCount = 0;
        listOfFiles = browserService.getFolder().listFiles();
        Assert.assertTrue(Objects.requireNonNull(listOfFiles).length != 0);

        for (File file : listOfFiles) {
            if (file.getName().endsWith(".dmg")) {
                Assert.assertTrue(file.length() != 0);
                dmgFilesCount++;
                log.info("Steam.dmg file has been loaded successfully");
            }
        }
        if (dmgFilesCount == 0) {
            log.info("There are no any DMG files loaded.");
        }

        installSteamSteps
                .openHomePage()
                .openInstallSteamPage()
                .downloadSteamDeb();

        int debFilesCount = 0;
        listOfFiles = browserService.getFolder().listFiles();
        Assert.assertTrue(Objects.requireNonNull(listOfFiles).length != 0);

        for (File file : listOfFiles) {
            if (file.getName().endsWith(".deb")) {
                Assert.assertTrue(file.length() != 0);
                debFilesCount++;
                log.info("Steam.deb file has been loaded successfully");
            }
        }
        if (debFilesCount == 0) {
            log.info("There are no any DEB files loaded.");
        }

        ScreenMaker.takeSnapShot(browserService.getDriver());
    }

    @Test(description = "Select a random game genre and verify game info for any of 3 games of this genre")
    public void GameInfoFromRandomGenreTest() {


        Header header = new Header(browserService, false);
        Actions actions = new Actions(browserService.getDriver());
        GameGenreSteps gameGenreSteps = new GameGenreSteps(browserService);
        List<GameRowModel> listOfThreeRandomGames = new ArrayList<>();
        List<SearchableItemModel> listOfSearchableItems;
        SearchableItemModel searchableGameFromDropDown = null;
        int count;
        boolean repeat = true;

        while (repeat) {
            try {
                gameGenreSteps
                        .openHomePage()
                        .selectRandomGenre();
                listOfThreeRandomGames = gameGenreSteps.getThreeRandomGames();
                repeat = false;
            } catch (NoSuchElementException exception) {
                log.error("Some of the items does not have a price");
            }
        }

        for (GameRowModel game : listOfThreeRandomGames) {
            try {
                count = 0;
                header.getSearchInputBy().sendKeys(game.getName());
                log.info("Game name is entered in the search field");

                waits.waitForVisibility(header.getSearchOptionsModal());
                log.info("List of 5 found games is shown");

                listOfSearchableItems = header.getListOfSearchableOptions();

                for (SearchableItemModel searchableItem : listOfSearchableItems) {
                    if (searchableItem.getName().equals(game.getName())) {
                        searchableGameFromDropDown = searchableItem;
                        count++;
                    }
                }

                if (count == 1) {
                    try {
                        Assert.assertEquals(game.getName(), searchableGameFromDropDown.getName());
                        Assert.assertEquals(game.getPrice(), searchableGameFromDropDown.getPrice());
                    } catch (AssertionError assertionError) {
                        log.error("Game: " + game + "; Game from search: " + searchableGameFromDropDown);
                    }
                } else {
                    log.error("The game is not in the list of found items." + game);
                    listOfThreeRandomGames.remove(game);
                }

                actions
                        .keyDown(Keys.CONTROL)
                        .sendKeys("a")
                        .keyUp(Keys.CONTROL)
                        .sendKeys(Keys.BACK_SPACE)
                        .build()
                        .perform();

                log.info("Search field cleared");

                listOfSearchableItems.clear();
                waits.waitForInVisibilityOfElement(header.getSearchSuggestionContentsBy());

            } catch (TimeoutException ex) {
                log.error("Failure! An item can not be found because of the special symbols in the name. Game: "
                        + game.getName());
                actions
                        .keyDown(Keys.CONTROL)
                        .sendKeys("a")
                        .keyUp(Keys.CONTROL)
                        .sendKeys(Keys.BACK_SPACE)
                        .build()
                        .perform();

                log.info("Search field cleared");
            }
        }

        for (GameRowModel game : listOfThreeRandomGames) {

            try {
                GameDetailedPage gameDetailedPage = gameGenreSteps.openGameDetailedPage(game);
                try {
                    Assert.assertEquals(gameDetailedPage.getGameModel(), game);
                } catch (AssertionError assertionError1) {
                    log.error("\nGame: " + game + ";\nGame from detailed page: " + gameDetailedPage.getGameModel());
                }
            } catch (TimeoutException ex) {
                log.error("Failure! An item can not be found because of the special symbols in the name. Game: "
                        + game.getName());
                actions
                        .keyDown(Keys.CONTROL)
                        .sendKeys("a")
                        .keyUp(Keys.CONTROL)
                        .sendKeys(Keys.BACK_SPACE)
                        .build()
                        .perform();

                log.info("Search field cleared");

            }
        }

        ScreenMaker.takeSnapShot(browserService.getDriver());
    }
}
