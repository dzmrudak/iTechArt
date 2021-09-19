package utils;

import core.BrowserService;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Objects;

@Slf4j
public class FilesCleaner {

    private static final File screenshotFolder = new File("target/screenshots/");
    private static final File allureResultsFolder = new File("target/allure-results/");

    public static void getScreenShotFolderCleared() {
        if (screenshotFolder.listFiles() != null) {
            for (File file : Objects.requireNonNull(screenshotFolder.listFiles())) {
                file.delete();
            }
        }
        log.info("ScreenShots Folder has been cleared");
    }

    public static void getAllureResultsFolderCleared() {
        if (allureResultsFolder.listFiles() != null) {
            for (File file : Objects.requireNonNull(allureResultsFolder.listFiles())) {
                file.delete();
            }
        }
        log.info("Allure Results Folder has been cleared");
    }

    public static void getDownloadsFolderCleared(BrowserService browserService) {
        if (browserService.getFolder().listFiles() != null) {
            for (File file : browserService.getFolder().listFiles()) {
                file.delete();
            }
            log.info("Folder with downloaded files has been deleted successfully");
        }
    }
}
