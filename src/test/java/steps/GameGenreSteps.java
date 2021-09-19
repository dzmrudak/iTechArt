package steps;

import Models.GameRowModel;
import Models.SearchableItemModel;
import baseEntities.BaseStep;
import core.BrowserService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.*;
import utils.ScreenMaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class GameGenreSteps extends BaseStep {


    List<GameRowModel> listOfAllGamesOnPage = new ArrayList<>();
    List<GameRowModel> listOfThreeRandomGames = new ArrayList<>();
    private static final int COUNT_OF_GAMES = 3;

    public GameGenreSteps(BrowserService browserService) {
        super(browserService);
    }

    public GameGenreSteps openHomePage() {
        new HomePage(browserService, true);
        return this;
    }

    public GameGenreSteps selectRandomGenre() {

        HeaderSteps headerSteps = new HeaderSteps(browserService);
        headerSteps.selectRandomGenre();
        return this;
    }

    public GameGenreSteps acceptCookies() {
        GameGenrePage gameGenrePage = new GameGenrePage(browserService, false);
        gameGenrePage.getAcceptCookiesButton().click();
        return this;
    }

    public List<GameRowModel> getThreeRandomGames() {

        GameGenrePage gameGenrePage = new GameGenrePage(browserService, false);

        listOfAllGamesOnPage = gameGenrePage.getGamesList();
        log.info("List of all games of the genre collected");

        Collections.shuffle(listOfAllGamesOnPage);
        log.info("List of all games of the genre shuffled");

        for (int i = 0; i < COUNT_OF_GAMES; i++) {
            listOfThreeRandomGames.add(listOfAllGamesOnPage.get(i));
        }
        log.info("List of three random games of the genre collected");

        return listOfThreeRandomGames;
    }

    public GameDetailedPage openGameDetailedPage(GameRowModel gameRowModel) {

        Header header = new Header(browserService, false);
        AgeCheckPage ageCheckPage;

        header.getSearchInputBy().sendKeys(gameRowModel.getName());
        log.info("Game name is entered in the search field");

        browserService.getWait().waitForVisibility(header.getSearchOptionsModal());
        log.info("List of 5 found games is shown");

        for (WebElement game : header.getListOfSearchableElements()) {
            if (gameRowModel.getName().equals(game.findElement(header.getFoundGameNameBy()).getText())) {

                ScreenMaker.takeSnapShot(browserService.getDriver());
                game.click();
                break;
            }
        }

        try {
            if (browserService.getDriver().findElement(By.className("agegate_birthday_selector")).isDisplayed()) {
                ageCheckPage = new AgeCheckPage(browserService, false);
                ageCheckPage.getAgeDaySelect().selectByValue("23");
                ageCheckPage.getAgeMonthSelect().selectByValue("June");
                ageCheckPage.getAgeYearSelect().selectByValue("1991");

                ScreenMaker.takeSnapShot(browserService.getDriver());
                ageCheckPage.getOpenPageButton().click();
                log.info("18+ age is confirmed");
            }
            return new GameDetailedPage(browserService, false);
        } catch (org.openqa.selenium.NoSuchElementException ex) {
            return new GameDetailedPage(browserService, false);
        }
    }
}
