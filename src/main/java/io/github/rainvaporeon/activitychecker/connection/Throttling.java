package io.github.rainvaporeon.activitychecker.connection;

import io.github.rainvaporeon.activitychecker.logging.ThreadLogger;

import java.util.function.Supplier;

public class Throttling {
    public static <T> T invokeLater(Supplier<T> supplier, long duration) {
        try {
            ThreadLogger.get().info(STR."Caught ratelimit, sleeping for \{duration}ms..");
            Thread.sleep(duration);
            return supplier.get();
        } catch (InterruptedException ex) {
            ThreadLogger.get().error("Task was interrupted", ex);
            Thread.currentThread().interrupt();
            return null;
        } catch (Exception ex) {
            return null;
        }
    }
}
