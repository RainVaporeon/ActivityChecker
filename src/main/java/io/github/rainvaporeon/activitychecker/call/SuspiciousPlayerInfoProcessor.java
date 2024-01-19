package io.github.rainvaporeon.activitychecker.call;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.rainvaporeon.activitychecker.annotations.Schema;
import io.github.rainvaporeon.activitychecker.misc.DataProcessor;

public class SuspiciousPlayerInfoProcessor extends DataProcessor<JsonObject, JsonArray, JsonObject> {
    @Override // map to a series of data
    protected JsonObject preprocess(JsonObject data) {
        return null;
    }

    @Schema("[1: {data}, 2: {data}, 3: ...]")
    @Override // filter to only players that are potentially suspicious
    protected JsonArray process(JsonObject data) {
        return null;
    }

    @Schema("{name: {index, value, data}, ...}")
    @Override // index the players that are suspicious
    protected JsonObject postprocess(JsonArray data) {
        return null;
    }
}
