package ru.ncedu.java.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * Provide a basic functionality for using the artifact in a shell.
 */
public class ShellHelper {
    private static final Logger log = LoggerFactory.getLogger(ShellHelper.class);

    private static ShellHelper instance;

    private PrintStream errStream = System.err;
    private PrintStream outStream = System.out;
    private InputStream inStream = System.in;

    public static ShellHelper getInstance() {
        if (instance == null) {
            return instance = new ShellHelper();
        }
        return instance;
    }
    public InputStream getInStream() {
        return inStream;
    }

    public PrintStream getErrStream() {
        return errStream;
    }

    public PrintStream getOutStream() {
        return outStream;
    }

    public void terminate() {
        log.info("Exit application with [ZERO] code.");
        System.exit(0);
    }

    public void terminateWithError() {
        log.info("Exit application with [NON ZERO] code.");
        System.exit(1);
    }
}
