package io.github.rainvaporeon.activitychecker;

import com.google.gson.JsonObject;
import io.github.rainvaporeon.activitychecker.connection.HttpClient;
import io.github.rainvaporeon.activitychecker.connection.Response;
import io.github.rainvaporeon.activitychecker.logging.ThreadLogger;
import io.github.rainvaporeon.activitychecker.misc.DataProcessingException;
import io.github.rainvaporeon.activitychecker.misc.Database;
import io.github.rainvaporeon.activitychecker.path.ApiPath;
import io.github.rainvaporeon.activitychecker.process.PlayerCountProcess;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static final long START_TIME = System.currentTimeMillis();

    public static void main(String[] args) {
        PlayerCountProcess processor = new PlayerCountProcess();
        AtomicInteger i = new AtomicInteger(0);
        HttpClient client = HttpClient.mainHttpClient();
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            Response response = client.get(ApiPath.LIST, ApiPath.Headers.DEFAULT);
            try {
                JsonObject o = response.asJson();
                processor.process(o);
            } catch (DataProcessingException e) {
                Database.INSTANCE.addLoss();
                ThreadLogger.get().error("Failed to process data: ", e);
            } catch (Throwable t) {
                Database.INSTANCE.addLoss();
                ThreadLogger.get().error("Uncaught exception: ", t);
            } finally {
                ThreadLogger.get().info(STR."It has been \{i.getAndAdd(30)} seconds since the initial scan.");
            }
        }, 0, 30, TimeUnit.SECONDS);
    }
}