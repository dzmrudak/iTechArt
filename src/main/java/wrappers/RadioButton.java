package wrappers;

import core.BrowserService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class RadioButton {

    private UIElement webElement;
    private List<UIElement> optionsList = new ArrayList<>();
    private List<String> optionsTextList;
    private WebDriver webDriver;

    public RadioButton(WebDriver webDriver, By by) {
        this.webDriver = webDriver;
        List<WebElement> options = webDriver.findElements(by);
        optionsTextList = getAllOptions(options);
    }

    private List<String> getAllOptions(List<WebElement> radioList) {
        List<String> resultList = new ArrayList<>();
        for (WebElement webElement : radioList) {
            UIElement uiElement = new UIElement(webDriver, webElement);
            optionsList.add(uiElement);
            resultList.add(uiElement.getParent().findElement(By.className("form-check")).getText());
        }
        return resultList;
    }

    public void selectByOption(String optionName) {
        int index = optionsTextList.indexOf(optionName);
        optionsList.get(index).click();
    }

    public void selectByIndex(int index) {
        optionsList.get(index).click();
    }

    public boolean isOptionSelected(String optionName) {
        int index = optionsTextList.indexOf(optionName);
        return optionsList.get(index).isSelected();
    }

    public boolean isOptionDisplayed(String optionName) {
        int index = optionsTextList.indexOf(optionName);
        return optionsList.get(index).isDisplayed();
    }

    public void click() {
        webElement.click();
    }

}
