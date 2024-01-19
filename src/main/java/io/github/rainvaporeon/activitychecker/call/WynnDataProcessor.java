package io.github.rainvaporeon.activitychecker.call;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.rainvaporeon.activitychecker.misc.DataProcessor;

public class WynnDataProcessor extends DataProcessor<JsonObject, JsonArray, JsonArray> {
    @Override
    protected JsonObject preprocess(JsonObject data) {
        return data;
    }

    @Override
    protected JsonArray process(JsonObject data) {
        JsonArray array = new JsonArray();
        JsonObject object = data.getAsJsonObject("players");
        object.keySet().forEach(array::add);
        return array;
    }

    @Override
    protected JsonArray postprocess(JsonArray data) {
        return data;
    }
}
