package io.github.rainvaporeon.activitychecker.misc;

import com.google.gson.stream.JsonWriter;
import io.github.rainvaporeon.activitychecker.Main;
import io.github.rainvaporeon.activitychecker.logging.ThreadLogger;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Database {
    public static final Database INSTANCE = new Database();

    private final AtomicInteger maxPlayers;
    private final AtomicInteger loss;

    private final Map<String, Integer> totalPlayers;
    private final Map<Long, Integer> playerCountMap;
    private final Map<Long, Integer> playerDiffMap;

    private final Object mutex = new Object();

    public Database() {
        this.maxPlayers = new AtomicInteger(0);
        this.loss = new AtomicInteger(0);
        this.totalPlayers = new HashMap<>();
        this.playerCountMap = new HashMap<>();
        this.playerDiffMap = new HashMap<>();
    }

    public void addLoss() {
        loss.getAndIncrement();
    }

    public void register(Collection<String> players) {
        long completionTime = System.currentTimeMillis();
        synchronized (mutex) {
            int size = totalPlayers.size();
            for(String player : players) {
                totalPlayers.compute(player, (_, value) -> {
                    if(value == null) return 1;
                    return ++value;
                });
            }
            int newSize = totalPlayers.size();
            maxPlayers.set(newSize);
            playerCountMap.put(completionTime, newSize);
            playerDiffMap.put(completionTime, newSize - size);
            if(size != newSize) report(size);
        }
    }

    private void report(int from) {
        ThreadLogger.get().info(STR."Database updated: Unique players increased from \{from} to \{maxPlayers}");
        ThreadLogger.get().info(STR."Current average frequency: \{(double) totalPlayers.values().stream().mapToInt(Integer::intValue).sum() / maxPlayers.get()}");
        try {
            write();
        } catch (IOException ex) {
            ThreadLogger.get().warn("Failed to write to analytics: ", ex);
        }
    }

    private void write() throws IOException {
        File file = new File("latestAnalytics.json");
        if(!file.exists()) file.createNewFile();
        try (JsonWriter writer = new JsonWriter(new BufferedWriter(new FileWriter(file)))) {
            writer.beginObject();
            writer.name("start").value(Main.START_TIME);
            writer.name("end").value(System.currentTimeMillis());
            writer.name("loss").value(loss);
            writer.name("diff");
            writer.beginArray();
            for(Map.Entry<Long, Integer> entry : playerDiffMap.entrySet()) {
                writer.beginObject();
                writer.name("time").value(entry.getKey());
                writer.name("difference").value(entry.getValue());
                writer.endObject();
            }
            writer.endArray();
            writer.name("count");
            writer.beginArray();
            for(Map.Entry<Long, Integer> entry : playerCountMap.entrySet()) {
                writer.beginObject();
                writer.name("time").value(entry.getKey());
                writer.name("count").value(entry.getValue());
                writer.endObject();
            }
            writer.endArray();
            writer.name("frequency");
            writer.beginObject();
            for(Map.Entry<String, Integer> entry : totalPlayers.entrySet()) {
                writer.beginObject();
                writer.name(entry.getKey());
                writer.value(entry.getValue());
                writer.endObject();
            }
            writer.endObject();
            writer.endObject();
        }
    }
}
