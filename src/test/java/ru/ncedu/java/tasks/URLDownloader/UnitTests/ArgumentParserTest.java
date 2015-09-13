package ru.ncedu.java.tasks.URLDownloader.UnitTests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import ru.ncedu.java.tasks.ArgumentsParser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgumentParserTest {
    private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final static ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeClass
    public static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterClass
    public static void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void testParsing1() {
        String[] args = new String[]{"http://mail.ru", "-o", "balalaika"};
        ArgumentsParser parser = ArgumentsParser.getInstance(args);
        assertThat(parser.getShouldBeOpened()).isTrue();
        assertThat(parser.getUrl()).isEqualTo(args[0]);
        assertThat(parser.getFilePath()).isEqualTo(args[2]);
    }

    @Ignore
    @Test
    public void testParsing2() {
        ArgumentsParser parser = ArgumentsParser.getInstance(new String[] {"-help"});
        //assertThat(outContent.toString()).startsWith("URLDownloader manual");
    }
}
