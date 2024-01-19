package io.github.rainvaporeon.activitychecker.connection;

import com.google.gson.JsonObject;

public interface Response {
    int code();

    String response();

    JsonObject asJson();
}
