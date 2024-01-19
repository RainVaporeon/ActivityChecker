package io.github.rainvaporeon.activitychecker.logging;

import io.github.rainvaporeon.fishutils.logging.Logger;
import io.github.rainvaporeon.fishutils.logging.Loggers;

import java.io.File;

public class ThreadLogger {
    private static final File output;

    static {
        long date = System.currentTimeMillis();
        output = new File(STR."log-\{date}.log");
    }

    public static Logger get() {
        return Loggers.getThreadLogger(output);
    }
}
