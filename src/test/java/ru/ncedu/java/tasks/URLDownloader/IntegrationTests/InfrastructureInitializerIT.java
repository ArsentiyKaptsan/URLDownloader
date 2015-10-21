package ru.ncedu.java.tasks.URLDownloader.IntegrationTests;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ncedu.java.tasks.InfrastructureInitializer;
import ru.ncedu.java.tasks.URLDownloader.Utils.TestService;
import ru.ncedu.java.tasks.URLDownloader.categories.LocalTest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.StrictAssertions.assertThat;

@Category( LocalTest.class )
public class InfrastructureInitializerIT {
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

    private static final Logger LOG = LoggerFactory.getLogger(InfrastructureInitializerIT.class);

    private Path testFolder = TestService.getInstance().getTestFolder();

    private Path pathToFile1 = testFolder.resolve("zuccerberg.html").normalize();
    private URL url1;
    {
        try {
            url1 = new URL("http://facebook.com/zuccerberg.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private Path pathToFile2 = testFolder.resolve("fisher.html").normalize();
    private URL url2;
    {
        try {
            url2 = new URL("http://facebook.com/fisher.html?query=34");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private Path pathToFile3 = testFolder.resolve("fireman.html").normalize();
    private URL url3;
    {
        try {
            url3 = new URL("http://facebook.com/fireman.html?query=34");
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

    /**
     * GIVEN the url with a non default file name(index.html)
     * AND the path to the file is unique
     * WHEN initialization is processed
     * THEN new file is created with the appropriate name
     */
    @Test
    public void testInit1() {
        InfrastructureInitializer instance = new InfrastructureInitializer(url1, testFolder.toString());
        instance.init();

        assertThat(Files.exists(pathToFile1));
    }
    //-------------------------------------//
    @Before
    public void setUpForTestInit2() {
        try {
            if (Files.exists(pathToFile2)) {
                Files.delete(pathToFile2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * GIVEN the url with a non default file name(index.html)
     * AND the absolute path to the file is unique
     * WHEN initialization is processed
     * THEN new file is created with the appropriate name
     */
    @Test
    public void testInit2() {
        InfrastructureInitializer instance = new InfrastructureInitializer(url2, pathToFile2.toString());
        instance.init();

        assertThat(Files.exists(pathToFile2));
    }
    //-------------------------------------//
    @Before
    public void setUpForTestInit3() {
        try {
            if (!Files.exists(pathToFile3)) {
                Files.createFile(pathToFile3);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * GIVEN the url with a non default file name(index.html)
     * AND the absolute path to the file is unique
     * WHEN initialization is processed
     * THEN new file is created with the appropriate name
     */
    @Ignore
    @Test
    public void testInit3() {
        InfrastructureInitializer instance = new InfrastructureInitializer(url3, pathToFile3.toString());
        instance.init();
        System.out.println("yes");

        assertThat(Files.exists(pathToFile3));
    }
}
