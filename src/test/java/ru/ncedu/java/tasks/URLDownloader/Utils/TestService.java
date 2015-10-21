package ru.ncedu.java.tasks.URLDownloader.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;

/**
 * Provider testing information.
 */
public class TestService {
    private final static Logger LOG = LoggerFactory.getLogger(TestService.class);

    private static TestService ourInstance = new TestService();

    private Path testFolder;

    private boolean isRunningLocally;

    public static TestService getInstance() {
        return ourInstance;
    }

    private TestService() {
        try {
            LOG.info("Starting initialization for integration testing.");
            testFolder = new File(System.getenv("test.folder")).toPath();
            isRunningLocally = "localhost".equals(System.getenv("test.environment"));
        } catch (Throwable e) {
            testFolder = new File("src/test/resources/testFolder").toPath();
            isRunningLocally = true;
        }
        LOG.info("Test folder: [{}]", testFolder.toString());
    }

    public Path getTestFolder() {
        return testFolder;
    }

    public boolean isRunningLocally() {
        return isRunningLocally;
    }
}
