package ru.ncedu.java.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Run application from here.
 */
public class RunnerClass {
    private static final Logger log = LoggerFactory.getLogger(RunnerClass.class);

    /**
     * @param args [url] [filename] [-o]/[--Open] [-help]
     */
    public static void main(String[] args) {
        new Processor(args).run();
    }
}
