package io.github.rainvaporeon.activitychecker;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Dump {

    public static void main(String... args) {
        try {
            test();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static void test() {
        JsonObject object = (JsonObject) JsonParser.parseReader(new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/data.json"))));
        JsonArray array = object.getAsJsonArray("frequency");
        Map<String, Integer> map = new HashMap<>();
        array.forEach(element -> {
            JsonObject o = element.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entry = o.entrySet();
            Map.Entry<String, JsonElement> ent = entry.stream().findFirst().get();
            String key = ent.getKey();
            int value = ent.getValue().getAsInt();
            map.put(key, value);
        });
        List<Integer> coll = map.values().stream().sorted().collect(Collectors.toList());
        coll.sort(Comparator.comparingInt(l -> l));
        int peak = coll.get(coll.size() - 1);
        int lowest = coll.get(0);
        double average = coll.stream().mapToInt(Integer::intValue).average().getAsDouble();
        int count = coll.size();

        int pct5 = coll.get((int) (coll.size() * 0.05));
        int pct25 = coll.get((int) (coll.size() * 0.25));
        int pct50 = coll.get((int) (coll.size() * 0.50));
        int pct75 = coll.get((int) (coll.size() * 0.75));
        int pct95 = coll.get((int) (coll.size() * 0.95));
        int pct99 = coll.get((int) (coll.size() * 0.99));
        System.out.printf("""
                Peak: %d, Median: %d, Lowest: %d, Avg: %.4f, Samples: %d
                5th percentile: %d
                25th percentile: %d
                75th percentile: %d
                95th percentile: %d
                99th percentile: %d
                """, peak, pct50, lowest, average, count, pct5, pct25, pct75, pct95, pct99);
        System.out.printf("\nList: \n\n%s\n", coll);
    }
}
