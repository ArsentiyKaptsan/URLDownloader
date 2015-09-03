package ru.ncedu.java.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Service is responsible for file creation, which data will be downloaded to.
 */
public class InfrastructureInitializer {
    private static final Logger log = LoggerFactory.getLogger(InfrastructureInitializer.class);

    private URL url;
    /**
     * Can be null. In this case name gets from url.
     */
    private String filePath;

    //Gets while running.
    private Path finalDir;

    public InfrastructureInitializer(URL url, String filePath) {
        this.url = url;
        this.filePath = filePath;
        init();
    }

    public Path getFinalDir() {
        return finalDir;
    }

    /**
     * Parses url string, choose name for file and create it.
     */
    public void init() {
        log.info("Starting infrastructure initializing.");

        String fileName;
        try {
            fileName = getLastPartOfUrl();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Can not get fle name from the url!");
        }

        Path currentDir =  new File(System.getProperty("user.dir")).toPath();
        if (filePath == null) {
           filePath = fileName;
        }
        finalDir = new File(filePath).toPath().resolve(currentDir);

        if (Files.exists(finalDir) && fileName.equals(filePath)) {
            finalDir = new File(fileName).toPath().resolve(finalDir);
        }

        if (Files.exists(finalDir)) {
            if (Files.isDirectory(finalDir)) {
                throw new RuntimeException("Directory with the same name already exists!");
            }

            if (askIfFileShouldBeRewritten()) {
                try {
                    new PrintWriter(finalDir.toFile()).print("");
                    log.info("Rewrite file.");
                    return;
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("Can not rewrite the file!");
                }
            } else {
                String newName = askIfFileShouldBeRenamed();
                if (newName != null) {
                    finalDir = new File(finalDir.getParent().toString(), newName).toPath();
                    log.info("Create file with a new name [{}].", newName);
                }
            }
        }

        try {
            Files.createFile(finalDir);
        } catch (IOException e) {
            throw new RuntimeException("Can not create a file!", e);
        }
    }

    private String getLastPartOfUrl() throws MalformedURLException {
        String name = new File(url.getPath()).toPath().getFileName().toString();
        if (name.isEmpty()) {
            name = "index.html";
        }
        return name;
    }

    /**
     * Asks user, if file should be rewritten.
     */
    private boolean askIfFileShouldBeRewritten() {
        boolean shouldRewrite = false;
        ShellHelper.getInstance().getOutStream()
                .println("There is file with the same name. Do you want to rewrite it? Type yes/no");
        String response;
        Scanner scanner = new Scanner(ShellHelper.getInstance().getInStream());
        response = scanner.nextLine();
        if ("yes".equals(response)) {
            return true;
        }
        if ("no".equals(response)) {
            return false;
        }

        throw new RuntimeException("No such option!");
    }

    /**
     * Asks user, if file should be renamed.
     * @return new file name, if should, or null instead.
     */
    private String askIfFileShouldBeRenamed() {
        String newName = null;
        Scanner scanner = new Scanner(ShellHelper.getInstance().getInStream());
        ShellHelper.getInstance().getOutStream()
                .println("Do you want to rename it? Type yes/no");
        String response = scanner.nextLine();
        if ("yes".equals(response)) {
            ShellHelper.getInstance().getOutStream()
                    .println("Type new name");
            newName = scanner.nextLine();
        } else if ("no".equals(response)) {
            ShellHelper.getInstance().terminate();
        } else {
            throw new RuntimeException("No such option!");
        }

        return newName;
    }
}
