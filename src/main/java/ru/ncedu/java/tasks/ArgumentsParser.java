package ru.ncedu.java.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service is responsible for command line arguments parsing, is a singletone.
 */
public class ArgumentsParser {
    private static final Logger log = LoggerFactory.getLogger(ArgumentsParser.class);

    private static ArgumentsParser instance;

    private String url;
    private String filePath;
    private Boolean shouldBeOpened;

    public Boolean getShouldBeOpened() {
        return shouldBeOpened;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getUrl() {
        return url;
    }

    /**
     * Gets instance of ArgumentParser.
     * @param args command line arguments
     * @return ArgumentParser instance
     */
    public static ArgumentsParser getInstance(String[] args) {
        if (instance == null) {
            return instance = new ArgumentsParser(args);
        }
        return instance;
    }

    private ArgumentsParser(String[] args) {
        log.info("Starting argument parsing");

        shouldBeOpened = false;
        for (String argument : args) {
            if ("-o".equals(argument) || "--Open".equals(argument)) {
                shouldBeOpened = true;
            } else if ("-help".equals(argument)) {
                writeHelpMessage();
                ShellHelper.getInstance().terminate();
            } else if (url == null) {
                url = argument.trim();
            } else if (filePath == null) {
                filePath = argument;
            } else {
                throw new RuntimeException("Incorrect arguments. Try -help option");
            }
        }
    }

    private void writeHelpMessage() {
        ShellHelper.getInstance().getOutStream().println("URLDownloader manual:");
        ShellHelper.getInstance().getOutStream().println("URLDownloader downloads data from the internet");
        ShellHelper.getInstance().getOutStream().println("Parameters: [url]*essential* [filename] [-o]/[--Open] [-help]");
    }
}
