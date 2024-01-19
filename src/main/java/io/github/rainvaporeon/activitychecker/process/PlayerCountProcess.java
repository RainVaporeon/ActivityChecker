package io.github.rainvaporeon.activitychecker.process;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.rainvaporeon.activitychecker.call.WynnDataProcessor;
import io.github.rainvaporeon.activitychecker.logging.ThreadLogger;
import io.github.rainvaporeon.activitychecker.misc.DataProcessingException;
import io.github.rainvaporeon.activitychecker.misc.Database;

import java.util.List;

public class PlayerCountProcess {
    private final WynnDataProcessor processor = new WynnDataProcessor();

    public void process(JsonObject object) throws DataProcessingException {
        JsonArray array = processor.handle(object);
        ThreadLogger.get().info(STR."One process has completed: Collected \{array.size()} online players.");
        List<String> coll = array.asList().stream().map(JsonElement::getAsString).toList();
        Database.INSTANCE.register(coll);
    }
}
