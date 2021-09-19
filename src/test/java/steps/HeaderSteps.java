package steps;

import baseEntities.BaseStep;
import core.BrowserService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import pages.GameGenrePage;
import pages.Header;
import utils.ScreenMaker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class HeaderSteps extends BaseStep {

    Header header = new Header(browserService, false);

    public HeaderSteps(BrowserService browserService) {
        super(browserService);
    }

    public GameGenrePage selectRandomGenre() {

        JavascriptExecutor jsExe = (JavascriptExecutor) browserService.getDriver();
        jsExe.executeScript("arguments[0].style.display='block';", header.getGenresPopup());

        Random rand = new Random();
        List<List<WebElement>> listOfAllGenres = new ArrayList<>();

        listOfAllGenres.add(header.getListOfActionGenres());
        listOfAllGenres.add(header.getListOfAdventuresAndCasualGenres());
        listOfAllGenres.add(header.getListOfRpgGenres());
        listOfAllGenres.add(header.getListOfSimulationGenres());
        listOfAllGenres.add(header.getListOfStrategyGenres());
        listOfAllGenres.add(header.getListOfSportAndRacingGenres());

        List<WebElement> randomGenreList;
        randomGenreList = listOfAllGenres.get(rand.nextInt(listOfAllGenres.size()));

        WebElement randomGenre;
        randomGenre = randomGenreList.get(rand.nextInt(randomGenreList.size()));

        log.info("Random Genre " + randomGenre.getText() + " selected");
        ScreenMaker.takeSnapShot(browserService.getDriver());
        randomGenre.click();

        return new GameGenrePage(browserService, false);
    }
}
