package pages;

import Models.GameRowModel;
import baseEntities.BasePage;
import core.BrowserService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GameGenrePage extends BasePage {

    private static final By newReleasesRowsBy = By.id("NewReleasesRows");
    private static final By acceptCookiesButtonBy = By.id("acceptAllButton");

    private static final By gameNameBy = By.className("tab_item_name");
    private static final By gamePriceBy = By.className("discount_final_price");
    private static final By gameDiscountBlockBy = By.className("discount_block");
    private static final By gameDiscountValueBy = By.className("discount_pct");
    private static final By tabItemDetailsBy = By.className("tab_item_details");


    public GameGenrePage(BrowserService browserService, boolean openPageByUrl) {
        super(browserService, openPageByUrl);
    }

    @Override
    protected void openPage() {
    }

    @Override
    protected boolean isPageOpened() {
        try {
            if (getNewReleasesRows().isDisplayed()) {
                log.info("Game Genre page is opened");
                return true;
            } else return false;
        } catch (Exception ex) {
            log.error("Game Genre page has not been opened.");
            return false;
        }
    }

    public List<GameRowModel> getGamesList() {

        List<GameRowModel> list = new ArrayList<>();
        for (WebElement el : browserService
                .getDriver()
                .findElement(newReleasesRowsBy)
                .findElements(By.tagName("a"))) {
            GameRowModel gameRowModel = new GameRowModel(
                    el.findElement(gameNameBy).getText(),
                    el.findElement(gamePriceBy).getText(),
                    getGameDiscountValue(el),
                    getListOfPlatforms(el));
            list.add(gameRowModel);
        }
        return list;
    }

    public List<String> getListOfPlatforms(WebElement element) {
        List<String> listOfPlatforms = new ArrayList<>();
        for (WebElement el1 : element.findElement(tabItemDetailsBy).findElements(By.tagName("span")))
            switch (el1.getAttribute("class").replace("platform_img ", "")) {
                case "win":
                    listOfPlatforms.add(el1.getAttribute("class").replace("platform_img ", ""));
                    break;
                case "mac":
                    listOfPlatforms.add(el1.getAttribute("class").replace("platform_img ", ""));
                    break;
                case "linux":
                    listOfPlatforms.add(el1.getAttribute("class").replace("platform_img ", ""));
                    break;
                default:
                    break;
            }
        return listOfPlatforms;
    }

    public WebElement getNewReleasesRows() {
        return browserService.getDriver().findElement(newReleasesRowsBy);
    }

    public WebElement getAcceptCookiesButton() {
        return browserService.getWait().waitForVisibility(acceptCookiesButtonBy);
    }

    public WebElement getGameName() {
        return browserService.getWait().waitForVisibility(gameNameBy);
    }

    public WebElement getGamePrice() {
        return browserService.getWait().waitForVisibility(gamePriceBy);
    }

    public WebElement getGameDiscountBlock() {
        return browserService.getWait().waitForVisibility(gameDiscountBlockBy);
    }

    public String getGameDiscountValue(WebElement element) {

        try {
            if (isPresented(element.findElement(gameDiscountBlockBy).findElement(gameDiscountValueBy))) {
                return element.findElement(gameDiscountBlockBy).findElement(gameDiscountValueBy).getText();
            } else {
                return "No Discount";
            }
        } catch (NoSuchElementException ex) {
            return "No Discount";
        }
    }
}
