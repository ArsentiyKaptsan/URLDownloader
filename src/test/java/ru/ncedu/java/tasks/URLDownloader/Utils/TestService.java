package ru.ncedu.java.tasks.URLDownloader.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;

/**
 * Provider testing information.
 */
public class TestService {
    private static TestService ourInstance = new TestService();

    private static final Logger LOG = LoggerFactory.getLogger(TestService.class);

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
        } catch (Error e) {
            testFolder = new File("/home/arsentii/").toPath();
            isRunningLocally = true;
        }
    }

    public Path getTestFolder() {
        return testFolder;
    }

    public boolean isRunningLocally() {
        return isRunningLocally;
    }
}
