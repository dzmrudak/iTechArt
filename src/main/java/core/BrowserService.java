package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.Waits;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

@Slf4j
public class BrowserService {

    private WebDriver driver;
    private DriverManagerType driverManagerType;
    private Waits waits;
    File folder;

    public BrowserService() {
        ReadProperties readProperties = new ReadProperties();
        folder = new File("target/downloads/");
        driver = new BrowserService(ReadProperties.getBrowser(), folder).getDriver();
        waits = new Waits(driver);
    }


    public BrowserService(String browserName, File folder) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                driverManagerType = DriverManagerType.CHROME;
                WebDriverManager.getInstance(driverManagerType).setup();

                HashMap<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("download.default_directory", folder.getAbsolutePath());
                chromePrefs.put("safebrowsing.enabled", "true");

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--incognito");
                chromeOptions.setExperimentalOption("prefs", chromePrefs);

                DesiredCapabilities cap = DesiredCapabilities.chrome();
                cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                chromeOptions.merge(cap);

                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                driverManagerType = DriverManagerType.FIREFOX;
                WebDriverManager.getInstance(driverManagerType).setup();

                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--disable-gpu");
                firefoxOptions.addArguments("--start-maximized");
                firefoxOptions.addArguments("--incognito");

                driver = new FirefoxDriver(firefoxOptions);
                break;
            default:
                log.error("Wrong browser type selected");
                break;
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public Waits getWait() {
        return waits;
    }

    public File getFolder() {
        return folder;
    }

}
