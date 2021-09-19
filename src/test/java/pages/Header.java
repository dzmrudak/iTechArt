package pages;

import Models.GameRowModel;
import Models.SearchableItemModel;
import baseEntities.BasePage;
import core.BrowserService;
import core.ReadProperties;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import wrappers.Button;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Header extends BasePage {

    private static final By genresPopupBy = By.id("genre_flyout");
    private static final By actionGenresLinksBy = By.xpath("//*[@data-genre-group = 'action']/a");
    private static final By adventuresAndCasualGenresLinksBy =
            By.xpath("//*[@data-genre-group = 'adventure_and_casual']/a");
    private static final By rpgGenresLinksBy = By.xpath("//*[@data-genre-group = 'rpg']/a");
    private static final By simulationGenresLinksBy = By.xpath("//*[@data-genre-group = 'simulation']/a");
    private static final By strategyGenresLinksBy = By.xpath("//*[@data-genre-group = 'strategy']/a");
    private static final By sportAndRacingGenresLinksBy = By.xpath("//*[@data-genre-group = 'sports_and_racing']/a");
    private static final By loginButtonBy = By.xpath("//*[@class='global_action_link']");
    private static final By installSteamButtonBy = By.xpath("//a[@class='header_installsteam_btn_content']");
    private static final By searchInputBy = By.id("store_nav_search_term");
    private static final By foundGameNameBy = By.className("match_name");
    private static final By foundGamePriceBy = By.className("match_price");
    private static final By searchOptionsModalBy = By.id("searchterm_options");
    private static final By searchSuggestionContentsBy = By.id("search_suggestion_contents");

    public Header(BrowserService browserService, boolean openPageByUrl) {
        super(browserService, openPageByUrl);
    }

    @Override
    protected void openPage() {

    }

    @Override
    protected boolean isPageOpened() {
        return true;
    }

    public Button getLoginButton() {
        return new Button(browserService.getDriver(), loginButtonBy);
    }

    public Button getInstallSteamButton() {
        return new Button(browserService.getDriver(), installSteamButtonBy);
    }


    public WebElement getGenresPopup() {
        return browserService.getDriver().findElement(genresPopupBy);
    }

    public List<WebElement> getListOfActionGenres() {
        return browserService.getWait().waitForVisibilityOfAllElements(actionGenresLinksBy);
    }

    public List<WebElement> getListOfAdventuresAndCasualGenres() {
        return browserService.getWait().waitForVisibilityOfAllElements(adventuresAndCasualGenresLinksBy);
    }

    public List<WebElement> getListOfRpgGenres() {
        return browserService.getWait().waitForVisibilityOfAllElements(rpgGenresLinksBy);
    }

    public List<WebElement> getListOfSimulationGenres() {
        return browserService.getWait().waitForVisibilityOfAllElements(simulationGenresLinksBy);
    }

    public List<WebElement> getListOfStrategyGenres() {
        return browserService.getWait().waitForVisibilityOfAllElements(strategyGenresLinksBy);
    }

    public List<WebElement> getListOfSportAndRacingGenres() {
        return browserService.getWait().waitForVisibilityOfAllElements(sportAndRacingGenresLinksBy);
    }

    public WebElement getSearchInputBy() {
        return browserService.getWait().waitForVisibility(searchInputBy);
    }

    public WebElement getFoundGameName() {
        return browserService.getDriver().findElement(foundGameNameBy);
    }

    public By getFoundGameNameBy() {
        return foundGameNameBy;
    }

    public WebElement getFoundGamePrice() {
        return browserService.getWait().waitForVisibility(foundGamePriceBy);
    }

    public WebElement getSearchOptionsModal() {
        return browserService.getWait().waitForVisibility(searchOptionsModalBy);
    }

    public By getSearchSuggestionContentsBy() {
        return searchSuggestionContentsBy;
    }

    public List<SearchableItemModel> getListOfSearchableOptions() {
        return browserService
                .getDriver()
                .findElement(searchSuggestionContentsBy)
                .findElements(By.tagName("a"))
                .stream()
                .map(el -> new SearchableItemModel(
                        el.findElement(foundGameNameBy).getText(),
                        el.findElement(foundGamePriceBy).getText()))
                .collect(Collectors.toList());
    }

    public List<WebElement> getListOfSearchableElements() {
        return browserService.getDriver().findElement(searchSuggestionContentsBy).findElements(By.tagName("a"));
    }
}
