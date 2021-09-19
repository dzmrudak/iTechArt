package wrappers;

import core.BrowserService;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import utils.Waits;

import java.util.List;

public class UIElement implements WebElement {

    private final WebDriver webDriver;
    private By by;
    private final WebElement webElement;
    private Actions action;
    private JavascriptExecutor jsExecutor;
    private Waits waits;

    public UIElement(WebDriver webDriver, By by) {
        this.webDriver = webDriver;
        this.action = new Actions(webDriver);
        this.jsExecutor = (JavascriptExecutor) webDriver;
        this.waits = new Waits(webDriver);
        this.by = by;
        webElement = webDriver.findElement(by);

    }

    public UIElement(WebDriver webDriver, WebElement webElement) {
        this.webDriver = webDriver;
        this.action = new Actions(webDriver);
        this.jsExecutor = (JavascriptExecutor) webDriver;
        this.waits = new Waits(webDriver);
        this.webElement = webElement;
    }

    @Override
    public void click() {
        try{
            webElement.click();
        } catch (Exception ex) {
            try {
                action
                        .moveToElement(webElement)
                        .click()
                        .build()
                        .perform();
            } catch (Exception ex1) {
                jsExecutor.executeScript("arguments[0].click();", webElement);
            }
        }

    }

    @Override
    public void submit() {
        webElement.submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        webElement.sendKeys();
    }

    @Override
    public void clear() {
        webElement.clear();
    }

    @Override
    public String getTagName() {
        return webElement.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return webElement.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return webElement.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return webElement.isEnabled();
    }

    @Override
    public String getText() {
        return webElement.getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return webElement.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return webElement.findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        return waits.waitForVisibility(webElement).isDisplayed();
    }

    @Override
    public Point getLocation() {
        return webElement.getLocation();
    }

    @Override
    public Dimension getSize() {
        return webElement.getSize();
    }

    @Override
    public Rectangle getRect() {
        return webElement.getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return webElement.getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return webElement.getScreenshotAs(target);
    }

    public void hover() {
        action
                .moveToElement(webElement)
                .build()
                .perform();
    }

    public void dragNDrop(UIElement target) {
        action
                .dragAndDrop(webElement, target.webElement)
                .build()
                .perform();
    }

    public UIElement getParent() {
        WebElement parent = (WebElement) ((JavascriptExecutor) webDriver).executeScript(
                "return arguments[0].parentNode;", webElement);
        return new UIElement(webDriver, parent);
    }


}