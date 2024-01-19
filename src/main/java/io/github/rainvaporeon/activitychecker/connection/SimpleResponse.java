package io.github.rainvaporeon.activitychecker.connection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class SimpleResponse implements Response {
    private final int code;
    private final String response;

    SimpleResponse(int code, String response) {
        this.code = code;
        this.response = response;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String response() {
        return response;
    }

    @Override
    public JsonObject asJson() {
        try {
            return (JsonObject) JsonParser.parseString(response);
        } catch (JsonSyntaxException ex) {
            return null;
        }
    }
}
