package ru.ncedu.java.tasks.URLDownloader.Utils;

import java.io.File;
import java.nio.file.Path;

/**
 * Provider testing information.
 */
public class TestService {
    private static TestService ourInstance = new TestService();

    private Path testFolder;

    private boolean isRunningLocally;

    public static TestService getInstance() {
        return ourInstance;
    }

    private TestService() {
        try {
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
