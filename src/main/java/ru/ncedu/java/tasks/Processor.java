package ru.ncedu.java.tasks;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

/**
 * Implement the main goal of URLDownloader artifact.
 */
public class Processor {
    private static final Logger log = LoggerFactory.getLogger(Processor.class);
    private ShellHelper helper = ShellHelper.getInstance();

    private String[] args;

    public Processor(String[] args) {
        this.args = args;
    }

    /**
     * Does the main logic. If some of the internal services throw an exception, it handles it, prints a message to user
     * and exits the app.
     */
    public void run() {
        log.info("Start application with parameters {[]}", args);
        try {
            //parsing arguments
            ArgumentsParser argumentsParser = ArgumentsParser.getInstance(args);
            URL url;
            try {
                url = new URL(argumentsParser.getUrl());
            } catch (MalformedURLException e) {
                log.info("Can not parse url [{}]", argumentsParser.getUrl());
                throw new RuntimeException("Can not parse the url");
            }

            //create file
            InfrastructureInitializer infrastructureInitializer = new InfrastructureInitializer(url,
                    argumentsParser.getFilePath());
            infrastructureInitializer.init();

            Path filePath = infrastructureInitializer.getFinalDir();

            //fetch data to file
            try {
                log.info("Fetching data from the url [{}]", url);
                FileUtils.copyURLToFile(url, filePath.toFile());
            } catch (IOException e) {
                log.info("Can not fetch file from the url [{}], message [{}]", url, e.getMessage());
                throw new RuntimeException("Error while downloading!", e);
            }

            //opening file
            if (argumentsParser.getShouldBeOpened()) {
                try {
                    log.info("Opening file [{}] in default browser", filePath);
                    Desktop.getDesktop().browse(filePath.toFile().toURI());
                } catch (IOException e) {
                    log.info("Can not open file [{}], message [{}]", filePath, e.getMessage());
                    throw new RuntimeException("Error while opening file!", e);
                }
            }
        } catch (RuntimeException exp) {
            helper.getErrStream().println("Error occurred: " + exp.getMessage());
            log.info("Exception is cached [{}], terminating.", exp.getMessage());
            helper.terminateWithError();
        }
    }
}
