package ru.ncedu.java.tasks.URLDownloader.IntegrationTests;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ncedu.java.tasks.Processor;
import ru.ncedu.java.tasks.URLDownloader.Utils.TestService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.StrictAssertions.assertThat;

public class WholeDownloaderIT {
    @Rule
    public TestName testName = new TestName();

    @Rule
    public TestWatcher testWatcher = new TestWatcher() {
        @Override
        protected void starting(final Description description) {
            String methodName = description.getMethodName();
            String className = description.getClassName();
            className = className.substring(className.lastIndexOf('.') + 1);
            LOG.info("Starting test: [{}] - [{}]", className, methodName);
        }
    };

    private static final Logger LOG = LoggerFactory.getLogger(WholeDownloaderIT.class);

    private static Path testFolder = TestService.getInstance().getTestFolder();

    private Path pathToFile1 = testFolder.resolve("index.html").normalize();
    private URL url1;
    {
        try {
            url1 = new URL("http://google.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUpForTestInit1() {
        try {
            if (Files.exists(pathToFile1)) {
                Files.delete(pathToFile1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIT() {
        Processor processor = new Processor(new String[] {url1.toString(), pathToFile1.toString()});
        processor.run();
        assertThat(Files.exists(pathToFile1));
        try {
            assertThat(Files.size(pathToFile1)).isNotZero();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
