package pages;

import baseEntities.BasePage;
import core.BrowserService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import wrappers.Button;

@Slf4j
public class AgeCheckPage extends BasePage {

    private static final By ageGateBirthdayBy = By.className("agegate_birthday_selector");
    private static final By ageDaySelectBy = By.id("ageDay");
    private static final By ageMonthSelectBy = By.id("ageMonth");
    private static final By ageYearSelectBy = By.id("ageYear");
    private static final By openPageButtonBy = By.xpath("//span[contains(text(), 'Открыть страницу')]");

    public AgeCheckPage(BrowserService browserService, boolean openPageByUrl) {
        super(browserService, openPageByUrl);
    }

    @Override
    protected void openPage() {
    }

    @Override
    public boolean isPageOpened() {
        try {
            if (getAgeGateBirthday().isDisplayed()) {
                log.info("Age Check page opened successfully");
                return true;
            } else return false;
        } catch (Exception ex) {
            log.error("Age Check page has not been opened.");
            return false;
        }
    }

    public WebElement getAgeGateBirthday() {
        return browserService.getWait().waitForVisibility(ageGateBirthdayBy);
    }

    public Select getAgeDaySelect() {
        return new Select(browserService.getWait().waitForVisibility(ageDaySelectBy));
    }

    public Select getAgeMonthSelect() {
        return new Select(browserService.getWait().waitForVisibility(ageMonthSelectBy));
    }

    public Select getAgeYearSelect() {
        return new Select(browserService.getWait().waitForVisibility(ageYearSelectBy));
    }

    public Button getOpenPageButton() {
        return new Button(browserService.getDriver(), openPageButtonBy);
    }

}
