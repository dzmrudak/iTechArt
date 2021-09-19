package pages;

import Models.GameRowModel;
import Models.SearchableItemModel;
import baseEntities.BasePage;
import core.BrowserService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class GameDetailedPage extends BasePage {

    private static final By gameAreaPurchaseGameBy = By.className("game_area_purchase_game");
    private static final By gameAreaPurchaseGameWrapperBy = By.className("game_area_purchase_game_wrapper");
    private static final By gameNameBy = By.id("appHubAppName");
    private static final By gamePurchasePriceBy = By.className("game_purchase_price");
    private static final By gameDiscountPriceBy = By.className("discount_final_price");
    private static final By gameDiscountBy = By.className("discount_pct");

    public GameDetailedPage(BrowserService browserService, boolean openPageByUrl) {
        super(browserService, openPageByUrl);
    }

    @Override
    protected void openPage() {

    }

    @Override
    protected boolean isPageOpened() {
        try {
            if (getGameAreaPurchaseGame().isDisplayed() || getGameAreaPurchaseGameWrapper().isDisplayed()) {
                log.info("Game Detailed page opened successfully");
                return true;
            } else return false;
        } catch (Exception ex) {
            log.error("Game Detailed page has not been opened.");
            return false;
        }
    }

    public WebElement getGameAreaPurchaseGame() {
        return browserService.getDriver().findElement(gameAreaPurchaseGameBy);
    }

    public WebElement getGameAreaPurchaseGameWrapper() {
        return browserService.getDriver().findElement(gameAreaPurchaseGameWrapperBy);
    }

    public GameRowModel getGameModel() {

        return GameRowModel
                .builder()
                .build()
                .setName(getGameNameByID().getText())
                .setPrice(getGamePrice())
                .setDiscount(getGameDiscountValue())
                .setPlatform(getListOfPlatforms());
    }

    public List<String> getListOfPlatforms() {
        List<String> listOfPlatforms = new ArrayList<>();
        try {
            for (WebElement el1 : getGameAreaPurchaseGame().findElements(By.tagName("span"))) {
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
            }
        } catch (NoSuchElementException ex) {
            for (WebElement el1 : getGameAreaPurchaseGameWrapper().findElements(By.tagName("span"))) {
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
            }
        }
        return listOfPlatforms;
    }

    public String getGamePrice() {

        try {
            if (isPresented(getGameAreaPurchaseGameWrapper())) {
                try {
                    return getGameAreaPurchaseGameWrapper().findElement(gamePurchasePriceBy).getText();
                } catch (NoSuchElementException exception1) {
                    return getGameAreaPurchaseGameWrapper().findElement(gameDiscountPriceBy).getText();
                }
            } else {
                return getGameAreaPurchaseGame().findElement(gamePurchasePriceBy).getText();
            }
        } catch (NoSuchElementException exception) {
            return getGameAreaPurchaseGame().findElement(gamePurchasePriceBy).getText();
        }
    }

    public String getGameDiscountValue() {

        try {
            if (isPresented(getGameAreaPurchaseGameWrapper().findElement(gameDiscountBy))) {
                return getGameAreaPurchaseGameWrapper().findElement(gameDiscountBy).getText();
            } else {
                return "No Discount";
            }
        } catch (NoSuchElementException ex) {
            return "No Discount";
        }
    }

    public WebElement getGameNameByID() {
        return browserService.getWait().waitForVisibility(gameNameBy);
    }


    public String getGameName() {

        try {
            return getGameAreaPurchaseGame().findElement(gameNameBy).getText().replace("Сыграть в ", "");
        } catch (NoSuchElementException ex) {
            return getGameAreaPurchaseGameWrapper().findElement(gameNameBy).getText().replace("Купить ", "");
        }
    }
}
