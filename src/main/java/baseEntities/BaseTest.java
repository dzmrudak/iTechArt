package baseEntities;

import core.BrowserService;
import core.ReadProperties;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.*;
import utils.FilesCleaner;
import utils.TestListener;
import utils.Waits;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Listeners(TestListener.class)
public class BaseTest {

    public BrowserService browserService;
    protected ReadProperties readProperties;
    protected Waits waits;

    @BeforeSuite
    public void setupSuite() {
        System.out.println("BeforeSuite: ");
        FilesCleaner.getScreenShotFolderCleared();
        FilesCleaner.getAllureResultsFolderCleared();
    }

    @BeforeGroups
    public void setupGroups() {
        System.out.println("BeforeGroups: ");
        FilesCleaner.getDownloadsFolderCleared(browserService);
    }

    @BeforeTest
    public void setupTest() {
        System.out.println("BeforeTest: ");
    }

    @BeforeClass
    public void setupClass() {
        System.out.println("BeforeClass: ");
        readProperties = new ReadProperties();
    }

    @BeforeMethod
    public void setupMethod() {

        System.out.println("BeforeMethod: ");

        browserService = new BrowserService();

        browserService.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        browserService.getDriver().get(ReadProperties.getBaseUrl());
        waits = browserService.getWait();

    }

    @AfterMethod
    public void tearDownMethod() {

        System.out.println("AfterMethod: ");
        browserService.getDriver().quit();
        browserService = null;
    }

    @AfterClass
    public void tearDownClass(){
        System.out.println("AfterClass: ");
    }

    @AfterTest
    public void tearDownTest(){
        System.out.println("AfterTest: ");
    }

    @AfterGroups
    public void tearDownGroups(){
        System.out.println("AfterGroups: ");
    }

    @AfterSuite
    public void tearDownSuite(){
        System.out.println("AfterSuite: ");
    }
}
