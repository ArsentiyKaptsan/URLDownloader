package ru.ncedu.java.tasks.URLDownloader.IntegrationTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
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
    private static Path testFolder = TestService.getInstance().getTestFolder();

    private Path pathToFile1 = testFolder.resolve("zuccerberg.html").normalize();
    private URL url1;
    {
        try {
            url1 = new URL("http://facebook.com/zuccerberg.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
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
     * AND the path to the file is an empty directory
     * WHEN initialization is processed
     * THEN new file is created with the appropriate name
     */
    @Test
    public void testOrdinaryInit() {
        InfrastructureInitializer instance = new InfrastructureInitializer(url1, testFolder.toString());
        instance.init();

        assertThat(Files.exists(pathToFile1));
    }
}
