package utils;

import core.BrowserService;
import core.ReadProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import wrappers.UIElement;

import java.util.List;

public class Waits {

    private WebDriverWait wait;

    public Waits(WebDriver driver, int timeOut){
        wait = new WebDriverWait(driver, timeOut);
    }

    public Waits(WebDriver driver){
        wait = new WebDriverWait(driver, new ReadProperties().getTimeout());
    }

    public WebElement waitForVisibility(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement waitForVisibility(WebElement webElement) {
        return wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public List<WebElement> waitForVisibilityOfAllElements(By by) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    public WebDriver waitForVisibilityOfFrame(By by) {
        return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
    }

    public Boolean waitForInVisibilityOfElement(By by) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementToBeClickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }
}
